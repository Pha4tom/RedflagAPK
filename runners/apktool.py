# runners/apktool.py
import subprocess
import threading
import time
from pathlib import Path

from checks.common import progress_print


def _watch_elapsed(stop_event: threading.Event, label: str = "apktool"):
    start = time.time()
    while not stop_event.is_set():
        elapsed = int(time.time() - start)
        progress_print(f"    [{label}] {elapsed}s elapsed...")
        stop_event.wait(3)  # every 3s, not every 1s — less spam


def run_apktool(apk_path: str, output_dir: str, show_progress: bool = True) -> dict:
    """Decompile APK with apktool -> manifest, resources, smali."""
    apk_path = Path(apk_path)
    output_dir = Path(output_dir)

    if not apk_path.exists():
        return {"success": False, "error": f"APK not found: {apk_path}"}

    cmd = ["apktool", "d", str(apk_path), "-o", str(output_dir), "-f"]  # -f = force overwrite

    stop_event = threading.Event()
    watcher = None
    if show_progress:
        watcher = threading.Thread(target=_watch_elapsed, args=(stop_event,), daemon=True)
        watcher.start()

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=300  # apktool's usually fast, 5 min ceiling is generous
        )
    except subprocess.TimeoutExpired:
        return {"success": False, "error": "apktool timed out after 300s"}
    finally:
        if watcher:
            stop_event.set()
            watcher.join()

    manifest = output_dir / "AndroidManifest.xml"
    smali_exists = (output_dir / "smali").exists()

    if result.returncode != 0 or not manifest.exists():
        return {
            "success": False,
            "error": f"apktool failed (rc={result.returncode})",
            "stdout": result.stdout,
            "stderr": result.stderr,
        }

    return {
        "success": True,
        "output_dir": str(output_dir),
        "manifest_path": str(manifest),
        "smali_present": smali_exists,
        "stdout": result.stdout,
    }