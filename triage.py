import os
import sys
import json
import time
import argparse
import concurrent.futures

from runners.apktool import run_apktool
from runners.jadx import run_jadx
from checks.permissions import analyze_manifest
from checks.anti_debug import check_anti_debug
from checks.obfuscation import check_obfuscation
from checks.remote_config import check_remote_config
from checks.financial import check_financial

def draw_progress_bar(iteration: int, total: int, prefix='Scanning', length=30):
    """Render a clean CLI progress bar natively in the terminal."""
    if total == 0:
        return
    percent = f"{100 * (iteration / float(total)):.1f}"
    filled_length = int(length * iteration // total)
    bar = '█' * filled_length + '-' * (length - filled_length)
    sys.stdout.write(f'\r[*] {prefix} |{bar}| {percent}% ({iteration}/{total} files)')
    sys.stdout.flush()
    if iteration == total:
        sys.stdout.write('\n')

def worker_task(file_path: str) -> list:
    """Execute analysis checks on a single source file."""
    results = []
    results.extend(check_anti_debug(file_path))
    results.extend(check_obfuscation(file_path))
    results.extend(check_remote_config(file_path))
    results.extend(check_financial(file_path))
    return results

def main():
    parser = argparse.ArgumentParser(description="RedflagAPK - High Performance Static APK Triaging")
    parser.add_argument("--apk", required=True, help="Path to target APK file")
    parser.add_argument("-o", "--output", required=True, help="Output directory path for report and decompiled assets")
    parser.add_argument("--threads", type=int, default=os.cpu_count() or 4, help="CPU cores to use")
    args = parser.parse_args()

    start_time = time.time()

    apk_path = os.path.abspath(os.path.expanduser(args.apk))
    output_dir = os.path.abspath(os.path.expanduser(args.output))

    os.makedirs(output_dir, exist_ok=True)
    report_file = os.path.join(output_dir, "report.json")

    print(f"[*] Starting triage for: {os.path.basename(apk_path)}")
    print(f"[*] Output directory set to: {output_dir}\n")

    # 1. Unpack & Decompile
    decompile_start = time.time()
    print("[*] [1/4] Unpacking resources with Apktool...")
    apktool_dir = run_apktool(apk_path, output_dir)
    
    print(f"[*] [2/4] Decompiling Java source with JADX ({args.threads} cores)...")
    jadx_dir = run_jadx(apk_path, output_dir, threads=args.threads)
    decompile_time = time.time() - decompile_start

    findings = []

    # 2. Analyze AndroidManifest.xml
    if apktool_dir:
        manifest_path = os.path.join(apktool_dir, "AndroidManifest.xml")
        if os.path.exists(manifest_path):
            print("[*] [3/4] Analyzing permissions in AndroidManifest.xml...")
            findings.extend(analyze_manifest(manifest_path))

    # 3. Parallel scan decompiled source files
    scan_start = time.time()
    if jadx_dir and os.path.exists(jadx_dir):
        print("[*] [4/4] Collecting decompiled files for scanning...")
        files_to_scan = []
        for root, _, files in os.walk(jadx_dir):
            for file in files:
                if file.endswith((".java", ".kt")):
                    files_to_scan.append(os.path.join(root, file))

        total_files = len(files_to_scan)
        
        if total_files > 0:
            completed = 0
            draw_progress_bar(0, total_files)
            
            with concurrent.futures.ProcessPoolExecutor(max_workers=args.threads) as executor:
                futures = [executor.submit(worker_task, f) for f in files_to_scan]
                for future in concurrent.futures.as_completed(futures):
                    completed += 1
                    res = future.result()
                    if res:
                        findings.extend(res)
                    draw_progress_bar(completed, total_files)
    
    scan_time = time.time() - scan_start
    total_time = time.time() - start_time

    # 4. Save results to report.json inside output_dir
    report = {
        "target": os.path.basename(apk_path),
        "execution_time_seconds": round(total_time, 2),
        "total_flags": len(findings),
        "flags": findings
    }

    with open(report_file, "w") as f:
        json.dump(report, f, indent=4)

    print(f"\n[+] Analysis complete in {total_time:.2f}s!")
    print(f"    ├── Decompilation: {decompile_time:.2f}s")
    print(f"    ├── Code Scan    : {scan_time:.2f}s")
    print(f"    └── Output Directory: {output_dir}")
    print(f"        ├── report.json")
    print(f"        ├── apktool_out/")
    print(f"        └── jadx_out/")

if __name__ == "__main__":
    main()
