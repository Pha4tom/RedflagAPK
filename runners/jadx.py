import subprocess
import sys
import os

def run_jadx(apk_path: str, output_dir: str, threads: int = 4) -> str:
    """Decompile DEX bytecode with live class processing feedback."""
    target_dir = os.path.join(output_dir, "jadx_out")
    cmd = [
        "jadx",
        "-d", target_dir,
        "--no-res",
        "-j", str(threads),
        apk_path
    ]
    try:
        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1
        )

        processed_count = 0
        for line in process.stdout:
            clean_line = line.strip()
            if clean_line:
                processed_count += 1
                display_line = clean_line.split(" ")[-1] if " " in clean_line else clean_line
                sys.stdout.write(f"\r[*] [JADX] Decompiling... ({processed_count} tasks) {display_line[-35:]:<35s}")
                sys.stdout.flush()

        process.wait()
        sys.stdout.write(f"\r[*] [JADX] Decompilation complete!                     \n")
        sys.stdout.flush()

        return target_dir if process.returncode == 0 else ""
    except Exception as e:
        print(f"\n[!] JADX decompilation failed: {e}")
        return ""
