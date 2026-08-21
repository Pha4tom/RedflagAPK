import os
import sys
import json
import time
import hashlib
import argparse
import concurrent.futures
import xml.etree.ElementTree as ET

from runners.apktool import run_apktool
from runners.jadx import run_jadx

from checks.permissions import analyze_manifest
from checks.anti_debug import check_anti_debug
from checks.obfuscation import check_obfuscation
from checks.remote_config import check_remote_config
from checks.financial import check_financial
from checks.flutter import check_flutter
from checks.native import check_native
from checks.flutter_aot import check_flutter_aot


# ============================================================
# SEVERITY MAP
# ============================================================

SEVERITY_MAP = {

    # Financial
    "financial_btc_address": "high",
    "financial_eth_address": "high",
    "financial_iban": "high",
    "financial_usdt_trc20": "high",
    "financial_egypt_phone_payment_context": "medium",
    "financial_accessibility_binding": "medium",
    "financial_overlay_trigger": "medium",
    "financial_sms_interceptor": "high",

    # Remote config
    "remote_config_pastebin": "high",
    "remote_config_gist": "high",
    "remote_config_raw_github": "medium",
    "remote_config_firebase_rtdb": "medium",
    "remote_config_ip_literal": "medium",
    "remote_config_dynamic_load": "low",

    # Anti-debug / packers
    "anti_debug_isdebuggerconnected": "low",
    "anti_debug_ptrace": "low",
    "root_detect_test_keys": "low",
    "root_detect_su_binary": "medium",

    "packer_jiagu": "medium",
    "packer_bangcle": "medium",
    "packer_tencent_legu": "medium",
    "packer_ijiami": "medium",

    # Obfuscation
    "obfuscation_dex_class_loader": "medium",
    "obfuscation_reflection_invoke": "low",
    "obfuscation_base64_decode": "low",

    # Permissions
    "dangerous_permission": "medium",

    # Flutter
    "flutter_detected": "low",
    "flutter_google_api_key": "high",
    "flutter_jwt": "high",
    "flutter_bearer_token": "high",
    "flutter_firebase": "medium",
    "flutter_supabase": "medium",
    "flutter_aws": "medium",
    "flutter_websocket": "medium",
    "flutter_http": "low",

    # Flutter AOT
    "flutter_aot_analyzed": "low",
    "flutter_aot_firebase": "medium",
    "flutter_aot_supabase": "medium",
    "flutter_aot_aws": "medium",
    "flutter_aot_api_key": "high",
    "flutter_aot_authorization": "medium",

    # Native
    "native_google_api_key": "high",
    "native_jwt": "high",
    "native_bearer_token": "high",
    "native_websocket": "medium",
    "native_http_url": "low",
    "native_ipv4": "low",
    "native_email": "low",
    "native_root": "medium",
    "native_debug": "medium",
    "native_jni": "low",
}


# ============================================================
# SEVERITY
# ============================================================

def compute_severity(findings: list) -> str:
    """Calculate the highest severity found."""

    levels_present = set()

    for finding in findings:

        rule_id = (
            finding.get("rule_id")
            or finding.get("type")
        )

        levels_present.add(
            SEVERITY_MAP.get(
                rule_id,
                "low",
            )
        )

    if "high" in levels_present:
        return "high"

    if "medium" in levels_present:
        return "medium"

    if "low" in levels_present:
        return "low"

    return "none"


# ============================================================
# METADATA
# ============================================================

def extract_apk_metadata(apk_path, apktool_dir):
    """Pull package name, version, sha256, and file size for the dashboard."""

    metadata = {
        "package": None,
        "version": None,
        "sha256": None,
        "size": None,
    }

    try:
        metadata["size"] = os.path.getsize(apk_path)
    except OSError:
        pass

    try:
        h = hashlib.sha256()
        with open(apk_path, "rb") as f:
            for chunk in iter(lambda: f.read(8192), b""):
                h.update(chunk)
        metadata["sha256"] = h.hexdigest()
    except OSError:
        pass

    if apktool_dir:
        manifest_path = os.path.join(apktool_dir, "AndroidManifest.xml")
        if os.path.exists(manifest_path):
            try:
                tree = ET.parse(manifest_path)
                root = tree.getroot()
                metadata["package"] = root.attrib.get("package")
                ns = "{http://schemas.android.com/apk/res/android}"
                metadata["version"] = root.attrib.get(f"{ns}versionName")
            except Exception:
                pass

    return metadata


# ============================================================
# PROGRESS BAR
# ============================================================

def draw_progress_bar(
    iteration: int,
    total: int,
    prefix="Scanning",
    length=30,
):
    """Render a CLI progress bar."""

    if total == 0:
        return

    percent = f"{100 * (iteration / float(total)):.1f}"

    filled_length = int(
        length * iteration // total
    )

    bar = (
        "█" * filled_length
        + "-" * (length - filled_length)
    )

    sys.stdout.write(
        f"\r[*] {prefix} |{bar}| "
        f"{percent}% ({iteration}/{total} files)"
    )

    sys.stdout.flush()

    if iteration == total:
        sys.stdout.write("\n")


# ============================================================
# JAVA/KOTLIN WORKER
# ============================================================

def worker_task(file_path: str) -> list:
    """Run all Java/Kotlin checks on one source file."""

    results = []

    results.extend(
        check_anti_debug(file_path)
    )

    results.extend(
        check_obfuscation(file_path)
    )

    results.extend(
        check_remote_config(file_path)
    )

    results.extend(
        check_financial(file_path)
    )

    return results


# ============================================================
# MAIN
# ============================================================

def main():

    parser = argparse.ArgumentParser(
        description=(
            "RedflagAPK - Static APK Triaging"
        )
    )

    parser.add_argument(
        "--apk",
        required=True,
        help="Path to target APK",
    )

    parser.add_argument(
        "-o",
        "--output",
        required=True,
        help="Output directory",
    )

    parser.add_argument(
        "--threads",
        type=int,
        default=os.cpu_count() or 4,
        help="CPU cores to use",
    )

    args = parser.parse_args()

    # Validate CLI inputs early so malformed requests fail cleanly instead of
    # starting expensive JADX/Apktool work with unusable arguments.
    if not 1 <= args.threads <= 16:
        parser.error("--threads must be between 1 and 16")

    # --------------------------------------------------------
    # Paths
    # --------------------------------------------------------

    start_time = time.time()

    apk_path = os.path.abspath(
        os.path.expanduser(args.apk)
    )

    if not os.path.isfile(apk_path):
        parser.error(f"APK does not exist or is not a file: {apk_path}")

    if not apk_path.lower().endswith(".apk"):
        parser.error("Target must be an .apk file")

    output_dir = os.path.abspath(
        os.path.expanduser(args.output)
    )

    os.makedirs(
        output_dir,
        exist_ok=True,
    )

    report_file = os.path.join(
        output_dir,
        "report.json",
    )

    print(
        f"[*] Starting triage for: "
        f"{os.path.basename(apk_path)}"
    )

    print(
        f"[*] Output directory set to: "
        f"{output_dir}\n"
    )

    findings = []

    # ========================================================
    # 1. APKTOOL
    # ========================================================

    apktool_start = time.time()

    print(
        "[*] [1/4] Unpacking resources with Apktool..."
    )

    apktool_dir = run_apktool(
        apk_path,
        output_dir,
    )

    apktool_time = (
        time.time() - apktool_start
    )

    # ========================================================
    # 2. JADX
    # ========================================================

    jadx_start = time.time()

    print(
        f"[*] [2/4] Decompiling Java source "
        f"with JADX ({args.threads} cores)..."
    )

    jadx_dir = run_jadx(
        apk_path,
        output_dir,
        threads=args.threads,
    )

    jadx_time = (
        time.time() - jadx_start
    )

    # ========================================================
    # 3. MANIFEST
    # ========================================================

    manifest_start = time.time()

    if apktool_dir:

        manifest_path = os.path.join(
            apktool_dir,
            "AndroidManifest.xml",
        )

        if os.path.exists(
            manifest_path
        ):

            print(
                "[*] [3/4] Analyzing permissions "
                "in AndroidManifest.xml..."
            )

            findings.extend(
                analyze_manifest(
                    manifest_path
                )
            )

    manifest_time = (
        time.time() - manifest_start
    )

    # ========================================================
    # 4. JAVA / KOTLIN
    # ========================================================

    java_scan_start = time.time()

    files_to_scan = []

    if (
        jadx_dir
        and os.path.exists(jadx_dir)
    ):

        print(
            "[*] [4/4] Collecting Java/Kotlin "
            "files for scanning..."
        )

        for root, _, files in os.walk(
            jadx_dir
        ):

            for file in files:

                if file.endswith(
                    (".java", ".kt")
                ):

                    files_to_scan.append(
                        os.path.join(
                            root,
                            file,
                        )
                    )

    total_files = len(
        files_to_scan
    )

    if total_files > 0:

        completed = 0

        draw_progress_bar(
            0,
            total_files,
            prefix="Java/Kotlin",
        )

        with concurrent.futures.ProcessPoolExecutor(
            max_workers=args.threads
        ) as executor:

            futures = {
                executor.submit(worker_task, file_path): file_path
                for file_path in files_to_scan
            }

            for future in concurrent.futures.as_completed(
                futures
            ):

                completed += 1

                try:
                    result = future.result()

                    if result:
                        findings.extend(result)

                except Exception as error:

                    print(
                        f"\n[!] Worker error for {futures[future]}: "
                        f"{error}"
                    )

                draw_progress_bar(
                    completed,
                    total_files,
                    prefix="Java/Kotlin",
                )

    java_scan_time = (
        time.time()
        - java_scan_start
    )

    # ========================================================
    # 5. FLUTTER
    # ========================================================

    flutter_start = time.time()

    print(
        "[*] Scanning Flutter framework/assets..."
    )

    flutter_info = {
        "detected": False,
        "libapp": None,
        "available": False,
        "reason": "Flutter not detected",
        "findings": [],
    }

    if apktool_dir:

        findings.extend(
            check_flutter(
                apktool_dir
            )
        )

        flutter_aot_dir = os.path.join(
            output_dir,
            "flutter_aot",
        )

        flutter_info = check_flutter_aot(
            apktool_dir,
            flutter_aot_dir,
        )

        if not isinstance(
            flutter_info,
            dict
        ):

            flutter_info = {
                "detected": False,
                "libapp": None,
                "available": False,
                "reason": (
                    "Invalid flutter_aot result"
                ),
                "findings": [],
            }

        findings.extend(
            flutter_info.get(
                "findings",
                [],
            )
        )

    flutter_time = (
        time.time()
        - flutter_start
    )

    # ========================================================
    # 6. NATIVE
    # ========================================================

    native_start = time.time()

    print(
        "[*] Scanning native libraries..."
    )

    if apktool_dir:

        findings.extend(
            check_native(
                apktool_dir
            )
        )

    native_time = (
        time.time()
        - native_start
    )

    # ========================================================
    # OUTPUT DIRECTORY STATUS
    # ========================================================

    flutter_aot_dir = os.path.join(
        output_dir,
        "flutter_aot",
    )

    flutter_aot_exists = os.path.isdir(
        flutter_aot_dir
    )

    # ========================================================
    # TOTAL TIME
    # ========================================================

    total_time = (
        time.time()
        - start_time
    )

    overall_severity = compute_severity(
        findings
    )

    # Stamp per-flag severity so the dashboard can color/sort
    # individual findings, not just show one overall rating.
    for finding in findings:
        rule_id = finding.get("rule_id") or finding.get("type")
        finding["severity"] = SEVERITY_MAP.get(rule_id, "low")

    metadata = extract_apk_metadata(apk_path, apktool_dir)

    # ========================================================
    # REPORT
    # ========================================================

    report = {

        "target": os.path.basename(
            apk_path
        ),

        "metadata": metadata,

        "execution_time_seconds": round(
            total_time,
            2,
        ),

        "timing": {

            "apktool_seconds": round(
                apktool_time,
                2,
            ),

            "jadx_seconds": round(
                jadx_time,
                2,
            ),

            "manifest_seconds": round(
                manifest_time,
                2,
            ),

            "java_kotlin_scan_seconds": round(
                java_scan_time,
                2,
            ),

            "flutter_scan_seconds": round(
                flutter_time,
                2,
            ),

            "native_scan_seconds": round(
                native_time,
                2,
            ),
        },

        "files_scanned": {

            "java_kotlin": total_files,

        },

        "flutter": {

            "detected": flutter_info.get(
                "detected",
                False,
            ),

            "libapp": flutter_info.get(
                "libapp",
            ),

            "aot_analyzer": {

                "available": flutter_info.get(
                    "available",
                    False,
                ),

                "reason": flutter_info.get(
                    "reason",
                ),

                "output_directory": (
                    flutter_aot_dir
                    if flutter_aot_exists
                    else None
                ),
            },
        },

        "total_flags": len(
            findings
        ),

        "overall_severity": (
            overall_severity
        ),

        "flags": findings,
    }

    with open(
        report_file,
        "w",
        encoding="utf-8",
    ) as file:

        json.dump(
            report,
            file,
            indent=4,
        )

    # ========================================================
    # SUMMARY
    # ========================================================

    print(
        f"\n[+] Analysis complete "
        f"in {total_time:.2f}s!"
    )

    print(
        f"    ├── Apktool       : "
        f"{apktool_time:.2f}s"
    )

    print(
        f"    ├── JADX          : "
        f"{jadx_time:.2f}s"
    )

    print(
        f"    ├── Manifest      : "
        f"{manifest_time:.2f}s"
    )

    print(
        f"    ├── Java/Kotlin   : "
        f"{java_scan_time:.2f}s "
        f"({total_files} files)"
    )

    print(
        f"    ├── Flutter       : "
        f"{flutter_time:.2f}s"
    )

    print(
        f"    ├── Native        : "
        f"{native_time:.2f}s"
    )

    print(
        f"    ├── Flags         : "
        f"{len(findings)}"
    )

    print(
        f"    ├── Severity      : "
        f"{overall_severity}"
    )

    print(
        f"    └── Output        : "
        f"{output_dir}"
    )

    print(
        "        ├── report.json"
    )

    print(
        "        ├── apktool_out/"
    )

    print(
        "        ├── jadx_out/"
    )

    if flutter_aot_exists:

        print(
            "        └── flutter_aot/"
        )

    else:

        print(
            "        └── flutter_aot/ "
            "(not generated)"
        )


if __name__ == "__main__":
    main()