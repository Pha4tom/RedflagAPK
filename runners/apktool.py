import subprocess
import sys
import os

def run_apktool(apk_path: str, output_dir: str) -> str:
    """Extract AndroidManifest.xml and resources with live terminal feedback."""
    target_dir = os.path.join(output_dir, "apktool_out")
    cmd = [
        "apktool", "d", "-f", "-s",
        "-o", target_dir,
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
        
        for line in process.stdout:
            clean_line = line.strip()
            if clean_line.startswith("I:"):
                # Display current Apktool action on a single updating line
                status_msg = clean_line[3:].strip()
                sys.stdout.write(f"\r[*] [Apktool] {status_msg[:50]:<50s}")
                sys.stdout.flush()

        process.wait()
        sys.stdout.write("\r[*] [Apktool] Unpacking complete!                      \n")
        sys.stdout.flush()

        return target_dir if process.returncode == 0 else ""
    except Exception as e:
        print(f"\n[!] Apktool failed: {e}")
        return ""
