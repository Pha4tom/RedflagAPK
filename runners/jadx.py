# runners/jadx.py
import os
import subprocess
import threading
import time
from pathlib import Path

from checks.common import progress_print


def _watch_progress(output_dir: Path, stop_event: threading.Event, label: str = "jadx"):
    sources_dir = output_dir / "sources"
    start = time.time()
    while not stop_event.is_set():
        count = len(list(sources_dir.rglob("*.java"))) if sources_dir.exists() else 0
        elapsed = int(time.time() - start)
        progress_print(f"    [{label}] {elapsed}s — {count} java files written")
        stop_event.wait(3)  # every 3s, not every 1s — less spam


def run_jadx(apk_path: str, output_dir: str, timeout: int = 300, show_progress: bool = True) -> dict:
    """Decompile APK to Java with jadx. Timeout matters here —
    you already hit the $$ExternalSyntheticLambda hang bug, don't let it eat the whole run."""
    apk_path = Path(apk_path)
    output_dir = Path(output_dir)

    if not apk_path.exists():
        return {"success": False, "error": f"APK not found: {apk_path}"}

    threads = str(os.cpu_count() or 4)

    cmd = [
        "jadx",
        "-d", str(output_dir),
        "-j", threads,           # use all cores instead of jadx's hardcoded default of 4
        "-r",                    # skip resource decoding — apktool already extracted resources separately
        "--no-debug-info",       # skip debug metadata generation, pure overhead for static grep checks
        "--no-inline-anonymous", # skip anonymous-class-inline pass, cosmetic-only for human readability
        "--show-bad-code",       # dump best-effort output on malformed code instead of stalling/skipping
        str(apk_path),
    ]

    stop_event = threading.Event()
    watcher = None
    if show_progress:
        watcher = threading.Thread(target=_watch_progress, args=(output_dir, stop_event), daemon=True)
        watcher.start()

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=timeout
        )
    except subprocess.TimeoutExpired:
        if watcher:
            stop_event.set()
            watcher.join()
        sources_dir = output_dir / "sources"
        java_count = len(list(sources_dir.rglob("*.java"))) if sources_dir.exists() else 0

        if java_count > 0:
            return {
                "success": "partial",
                "error": f"jadx hung past {timeout}s — likely the known lambda-desugaring bug, not a config issue",
                "output_dir": str(output_dir),
                "sources_dir": str(sources_dir),
                "java_file_count": java_count,
                "partial": True,
            }

        return {
            "success": False,
            "error": f"jadx hung past {timeout}s with zero files decompiled — likely OOM or true hang, not just slow",
            "output_dir": str(output_dir),
            "partial": False,
        }
    finally:
        if watcher:
            stop_event.set()
            watcher.join()

    sources_dir = output_dir / "sources"
    java_count = len(list(sources_dir.rglob("*.java"))) if sources_dir.exists() else 0

    return {
        "success": java_count > 0,
        "output_dir": str(output_dir),
        "sources_dir": str(sources_dir),
        "java_file_count": java_count,
        "error_count": result.stdout.count("ERROR"),
        "stdout": result.stdout if java_count == 0 else "",
        "partial": False,
    }