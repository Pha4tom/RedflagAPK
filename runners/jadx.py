import os
import shutil
import subprocess
import sys


def run_jadx(apk_path: str, output_dir: str, threads: int = 4) -> str:
    """Decompile DEX bytecode with live feedback and reliable failure handling."""
    target_dir = os.path.join(output_dir, "jadx_out")
    os.makedirs(output_dir, exist_ok=True)

    if not os.path.isfile(apk_path):
        print(f"[!] JADX target is not a file: {apk_path}")
        return ""

    if shutil.which("jadx") is None:
        print("[!] JADX executable was not found in PATH.")
        return ""

    try:
        threads = max(1, min(16, int(threads)))
    except (TypeError, ValueError):
        threads = 4

    cmd = [
        "jadx",
        "-d", target_dir,
        "--no-res",
        "-j", str(threads),
        apk_path,
    ]

    try:
        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
        )

        processed_count = 0
        assert process.stdout is not None
        for line in process.stdout:
            clean_line = line.strip()
            if not clean_line:
                continue

            if clean_line.startswith(("ERROR", "WARN")):
                print(f"\n[JADX] {clean_line}")
            else:
                processed_count += 1
                display_line = clean_line.split(" ")[-1]
                sys.stdout.write(
                    f"\r[*] [JADX] Decompiling... ({processed_count} tasks) "
                    f"{display_line[-35:]:<35s}"
                )
                sys.stdout.flush()

        process.wait()
        print()

        if process.returncode != 0:
            print(f"[!] JADX failed with exit code {process.returncode}.")
            return ""

        if not os.path.isdir(target_dir):
            print("[!] JADX reported success but its output directory is missing.")
            return ""

        print("[+] [JADX] Decompilation complete!")
        return target_dir

    except FileNotFoundError:
        print("[!] JADX executable was not found in PATH.")
        return ""
    except OSError as exc:
        print(f"[!] JADX execution failed: {exc}")
        return ""
