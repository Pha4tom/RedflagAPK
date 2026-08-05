# runners/jadx.py
import subprocess
from pathlib import Path

def run_jadx(apk_path: str, output_dir: str, timeout: int = 300) -> dict:
    """Decompile APK to Java with jadx. Timeout matters here —
    you already hit the $$ExternalSyntheticLambda hang bug, don't let it eat the whole run."""
    apk_path = Path(apk_path)
    output_dir = Path(output_dir)

    if not apk_path.exists():
        return {"success": False, "error": f"APK not found: {apk_path}"}

    cmd = ["jadx", "-d", str(output_dir), str(apk_path)]

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=timeout
        )
    except subprocess.TimeoutExpired:
        # jadx writes files as it decompiles, not just at the end —
        # check what actually landed on disk before giving up entirely
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
            "error": f"jadx hung past {timeout}s — likely the known lambda-desugaring bug, not a config issue",
            "output_dir": str(output_dir)  # partial output may still exisst
        }

    sources_dir = output_dir / "sources"
    java_count = len(list(sources_dir.rglob("*.java"))) if sources_dir.exists() else 0

    return {
        "success": java_count > 0,
        "output_dir": str(output_dir),
        "sources_dir": str(sources_dir),
        "java_file_count": java_count,
        "error_count": result.stdout.count("ERROR"),  # jadx logs errors inline, rough count
        "stdout": result.stdout if java_count == 0 else "",  # don't dump 3000 lines if it worked
        "partial": False,
    }
