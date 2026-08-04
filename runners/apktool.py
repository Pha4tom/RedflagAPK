# runners/apktool.py
import subprocess
from pathlib import Path

def run_apktool(apk_path: str, output_dir: str) -> dict:
    """Decompile APK with apktool -> manifest, resources, smali."""
    apk_path = Path(apk_path)
    output_dir = Path(output_dir)

    if not apk_path.exists():
        return {"success": False, "error": f"APK not found: {apk_path}"}

    cmd = ["apktool", "d", str(apk_path), "-o", str(output_dir), "-f"]  # -f = force overwrite

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=300  # apktool's usually fast, 5 min ceiling is generous
        )
    except subprocess.TimeoutExpired:
        return {"success": False, "error": "apktool timed out after 300s"}

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
