import json
import os
import subprocess
import sys
import threading
import time
import uuid
from pathlib import Path

from flask import Flask, jsonify, request, send_from_directory

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
WEBVIEW_DIR = Path(__file__).resolve().parent
REDFLAG_DIR = WEBVIEW_DIR.parent
APK_LAB_DIR = REDFLAG_DIR.parent
STATIC_DIR = WEBVIEW_DIR / "static"

TRIAGE_SCRIPT = REDFLAG_DIR / "triage.py"
OUTPUT_BASE_DIR = APK_LAB_DIR / "output"
SAMPLES_DIR = APK_LAB_DIR / "samples"

OUTPUT_BASE_DIR.mkdir(parents=True, exist_ok=True)

app = Flask(__name__, static_folder=str(STATIC_DIR), static_url_path="")

# ---------------------------------------------------------------------------
# Scan state
# ---------------------------------------------------------------------------
# There is intentionally only one active scan. A lock protects the state from
# the Flask request thread and the worker thread racing each other.
STATE_LOCK = threading.RLock()
SCAN_STATE = {
    "running": False,
    "job_id": None,
    "logs": [],
    "report": None,
    "error": None,
    "exit_code": None,
    "started_at": None,
    "finished_at": None,
    "output_dir": None,
}

MAX_LOG_LINES = 2000
MIN_THREADS = 1
MAX_THREADS = 16

# The file browser is intentionally limited. Scanning may accept an explicit
# APK path, but the interactive browser must not become an arbitrary
# filesystem explorer.
BROWSE_ROOTS = {
    "sdcard": "/sdcard",
    "downloads": "/sdcard/Download",
    "samples": str(SAMPLES_DIR),
}


def _real(path: str | Path) -> Path:
    return Path(os.path.realpath(os.path.expanduser(str(path))))


def _is_within(child: Path, parent: Path) -> bool:
    try:
        child.relative_to(parent)
        return True
    except ValueError:
        return False


def _allowed_browse_roots() -> list[tuple[str, Path]]:
    roots = []
    for name, raw in BROWSE_ROOTS.items():
        root = _real(raw)
        if root.is_dir():
            roots.append((name, root))
    return roots


def _allowed_browse_path(raw_path: str) -> Path | None:
    target = _real(raw_path or "/sdcard")
    for _, root in _allowed_browse_roots():
        if _is_within(target, root):
            return target
    return None


def _append_log(message: str) -> None:
    message = message.strip()
    if not message:
        return
    with STATE_LOCK:
        SCAN_STATE["logs"].append(message)
        if len(SCAN_STATE["logs"]) > MAX_LOG_LINES:
            SCAN_STATE["logs"] = SCAN_STATE["logs"][-MAX_LOG_LINES:]


def _snapshot_state() -> dict:
    with STATE_LOCK:
        return {
            "running": SCAN_STATE["running"],
            "job_id": SCAN_STATE["job_id"],
            "logs": list(SCAN_STATE["logs"]),
            "report": SCAN_STATE["report"],
            "error": SCAN_STATE["error"],
            "exit_code": SCAN_STATE["exit_code"],
            "started_at": SCAN_STATE["started_at"],
            "finished_at": SCAN_STATE["finished_at"],
            "output_dir": SCAN_STATE["output_dir"],
        }


def resolve_target_path(raw_path: str) -> str:
    """Resolve an explicit APK path without restricting legitimate scans."""
    raw_path = str(raw_path or "").strip()
    if not raw_path:
        return ""

    expanded = os.path.expanduser(raw_path)
    if os.path.isabs(expanded):
        return os.path.abspath(expanded)

    candidates = [
        APK_LAB_DIR / expanded,
        SAMPLES_DIR / expanded,
    ]
    for candidate in candidates:
        if candidate.exists():
            return str(candidate.resolve())

    return str(candidates[0].absolute())


def _make_output_dir(apk_path: str, job_id: str) -> Path:
    """Use a unique directory so repeated scans can never overwrite results."""
    stem = Path(apk_path).stem or "scan"
    safe_stem = "".join(c if c.isalnum() or c in "._-" else "_" for c in stem)
    return OUTPUT_BASE_DIR / f"{safe_stem}_{job_id}"


def run_triage_process(apk_path: str, output_dir: Path, threads: int, job_id: str) -> None:
    cmd = [
        sys.executable,
        str(TRIAGE_SCRIPT),
        "--apk", apk_path,
        "-o", str(output_dir),
        "--threads", str(threads),
    ]

    process = None
    exit_code = None
    error = None

    try:
        with STATE_LOCK:
            SCAN_STATE["running"] = True
            SCAN_STATE["job_id"] = job_id
            SCAN_STATE["started_at"] = time.time()
            SCAN_STATE["finished_at"] = None
            SCAN_STATE["output_dir"] = str(output_dir)

        _append_log(f"[*] Job: {job_id}")
        _append_log(f"[*] Target: {apk_path}")
        _append_log(f"[*] Output: {output_dir}")

        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
        )

        assert process.stdout is not None
        for line in process.stdout:
            _append_log(line)

        exit_code = process.wait()

        report_file = output_dir / "report.json"
        if report_file.is_file():
            try:
                with report_file.open("r", encoding="utf-8") as fh:
                    report = json.load(fh)
                report.setdefault("scan_id", job_id)
                with STATE_LOCK:
                    SCAN_STATE["report"] = report
                if exit_code == 0:
                    _append_log("[+] Report generated successfully!")
                else:
                    _append_log(f"[!] Triage exited with code {exit_code}; report was still generated.")
            except (OSError, json.JSONDecodeError) as exc:
                error = f"Could not read report.json: {exc}"
                _append_log(f"[!] {error}")
        else:
            error = f"Triage finished without report.json (exit code {exit_code})."
            _append_log(f"[!] {error}")

    except FileNotFoundError as exc:
        error = f"Required executable or Python script was not found: {exc}"
        _append_log(f"[!] {error}")
    except Exception as exc:
        error = f"Execution error: {exc}"
        _append_log(f"[!] {error}")
    finally:
        if process is not None and process.poll() is None:
            try:
                process.kill()
            except OSError:
                pass

        with STATE_LOCK:
            SCAN_STATE["running"] = False
            SCAN_STATE["exit_code"] = exit_code
            SCAN_STATE["error"] = error
            SCAN_STATE["finished_at"] = time.time()


@app.route("/")
def index():
    return send_from_directory(str(STATIC_DIR), "index.html")


@app.route("/api/browse")
def api_browse():
    raw_path = request.args.get("path", "/sdcard")
    target_dir = _allowed_browse_path(raw_path)

    if target_dir is None:
        return jsonify({
            "error": "That location is outside the allowed APK browser roots.",
            "allowed_roots": BROWSE_ROOTS,
        }), 403

    if not target_dir.is_dir():
        return jsonify({"error": f"Not a directory: {target_dir}"}), 400

    try:
        entries = list(target_dir.iterdir())
    except PermissionError:
        return jsonify({"error": f"Permission denied: {target_dir}"}), 403
    except OSError as exc:
        return jsonify({"error": f"Could not read directory: {exc}"}), 500

    dirs = []
    apks = []

    for entry in sorted(entries, key=lambda p: p.name.lower()):
        if entry.name.startswith("."):
            continue

        try:
            resolved = _real(entry)
            # Never return a directory or APK that escapes the browser roots
            # through a symlink.
            if _allowed_browse_path(str(resolved)) is None:
                continue

            if entry.is_dir():
                dirs.append({"name": entry.name, "path": str(resolved)})
            elif entry.is_file() and entry.name.lower().endswith(".apk"):
                apks.append({
                    "name": entry.name,
                    "path": str(resolved),
                    "size": entry.stat().st_size,
                })
        except OSError:
            continue

    # Keep parent navigation inside the same allowed root.
    parent_candidate = target_dir.parent
    parent = None
    if parent_candidate != target_dir and _allowed_browse_path(str(parent_candidate)) is not None:
        parent = str(parent_candidate)

    roots = {
        name: str(root)
        for name, root in _allowed_browse_roots()
    }

    return jsonify({
        "current_path": str(target_dir),
        "parent": parent,
        "roots": roots,
        "dirs": dirs,
        "apks": apks,
    })


@app.route("/api/scan", methods=["POST"])
def api_scan():
    data = request.get_json(silent=True) or {}
    raw_path = data.get("apk_path", "")
    threads_raw = data.get("threads", 4)

    try:
        threads = int(threads_raw)
    except (TypeError, ValueError):
        return jsonify({"status": "error", "message": "Threads must be a whole number."}), 400

    if not MIN_THREADS <= threads <= MAX_THREADS:
        return jsonify({
            "status": "error",
            "message": f"Threads must be between {MIN_THREADS} and {MAX_THREADS}.",
        }), 400

    apk_path = resolve_target_path(raw_path)
    apk_file = Path(apk_path)

    if not apk_file.exists():
        return jsonify({"status": "error", "message": f"File not found: {apk_path}"}), 404
    if not apk_file.is_file():
        return jsonify({"status": "error", "message": "The selected target is not a file."}), 400
    if apk_file.suffix.lower() != ".apk":
        return jsonify({"status": "error", "message": "Target must be an .apk file."}), 400
    if not os.access(apk_file, os.R_OK):
        return jsonify({"status": "error", "message": "The APK is not readable."}), 403

    with STATE_LOCK:
        if SCAN_STATE["running"]:
            return jsonify({
                "status": "error",
                "message": "A scan is already in progress.",
                "job_id": SCAN_STATE["job_id"],
            }), 409

        job_id = f"scan_{time.strftime('%Y%m%d_%H%M%S')}_{uuid.uuid4().hex[:8]}"
        output_dir = _make_output_dir(str(apk_file), job_id)
        output_dir.mkdir(parents=True, exist_ok=False)

        SCAN_STATE.update({
            "running": True,
            "job_id": job_id,
            "logs": [],
            "report": None,
            "error": None,
            "exit_code": None,
            "started_at": time.time(),
            "finished_at": None,
            "output_dir": str(output_dir),
        })

    thread = threading.Thread(
        target=run_triage_process,
        args=(str(apk_file), output_dir, threads, job_id),
        name=f"redflag-{job_id}",
        daemon=True,
    )
    thread.start()

    return jsonify({
        "status": "started",
        "job_id": job_id,
        "output_dir": str(output_dir),
    })


@app.route("/api/status")
def api_status():
    requested_job = request.args.get("job_id")
    state = _snapshot_state()

    if requested_job and state["job_id"] not in (None, requested_job):
        return jsonify({
            "status": "stale",
            "message": "That scan is no longer the active job.",
            **state,
        })

    return jsonify({"status": "ok", **state})


if __name__ == "__main__":
    print(f"[*] Serving files from: {STATIC_DIR}")
    print("[*] Dashboard Online: http://127.0.0.1:5000")
    app.run(host="127.0.0.1", port=5000, debug=False, threaded=True)
