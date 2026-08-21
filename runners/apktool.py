import os
import shutil
import subprocess
import sys


def run_apktool(apk_path: str, output_dir: str) -> str:
    """Extract Android resources with live feedback and reliable failure handling."""
    target_dir = os.path.join(output_dir, "apktool_out")
    os.makedirs(output_dir, exist_ok=True)

    if not os.path.isfile(apk_path):
        print(f"[!] Apktool target is not a file: {apk_path}")
        return ""

    if shutil.which("apktool") is None:
        print("[!] Apktool executable was not found in PATH.")
        return ""

    cmd = ["apktool", "d", "-f", "-s", "-o", target_dir, apk_path]

    try:
        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
        )

        assert process.stdout is not None
        for line in process.stdout:
            clean_line = line.strip()
            if clean_line:
                if clean_line.startswith("I:"):
                    status_msg = clean_line[2:].strip()
                    sys.stdout.write(f"\r[*] [Apktool] {status_msg[:70]:<70s}")
                    sys.stdout.flush()
                elif clean_line.startswith(("W:", "E:")):
                    print(f"\n[Apktool] {clean_line}")

        process.wait()
        print()

        if process.returncode != 0:
            print(f"[!] Apktool failed with exit code {process.returncode}.")
            return ""

        if not os.path.isfile(os.path.join(target_dir, "AndroidManifest.xml")):
            print("[!] Apktool reported success but AndroidManifest.xml is missing.")
            return ""

        print("[+] [Apktool] Unpacking complete!")
        return target_dir

    except FileNotFoundError:
        print("[!] Apktool executable was not found in PATH.")
        return ""
    except OSError as exc:
        print(f"[!] Apktool execution failed: {exc}")
        return ""
