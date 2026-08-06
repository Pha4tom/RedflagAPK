#!/usr/bin/env python3
# triage.py — entry point for apk-triage

import argparse
import json
import sys
from concurrent.futures import ThreadPoolExecutor
from pathlib import Path

from runners.apktool import run_apktool
from runners.jadx import run_jadx
from checks.permissions import check_permissions
from checks.financial import check_financial
from checks.remote_config import check_remote_config
from checks.anti_debug import check_anti_debug
from checks.obfuscation import check_obfuscation


def log(msg, quiet=False):
    if not quiet:
        print(msg)


def main():
    parser = argparse.ArgumentParser(
        description="APK triage: decompile + scam/fraud pattern checks",
        epilog="Example: triage.py samples/target.apk -o output/target",
    )
    parser.add_argument("apk", help="Path to APK file")
    parser.add_argument("-o", "--output", default=None,
                         help="Output dir (default: ../output/<apk_stem>)")
    parser.add_argument("-q", "--quiet", action="store_true",
                         help="Suppress progress output, only print final summary")
    parser.add_argument("--json-only", action="store_true",
                         help="Print only the final JSON to stdout (for scripting/piping)")
    parser.add_argument("--jadx-timeout", type=int, default=300,
                         help="Timeout in seconds for jadx decompile (default: 300)")
    parser.add_argument("--sequential", action="store_true",
                         help="Run apktool then jadx sequentially instead of parallel. "
                              "Both are JVM processes — on large APKs, running them "
                              "concurrently can hit your 8GB memory ceiling. Use this "
                              "flag if you get OOM/killed processes on big samples.")
    args = parser.parse_args()

    quiet = args.quiet or args.json_only

    apk_path = Path(args.apk).resolve()
    if not apk_path.exists():
        print(f"[!] APK not found: {apk_path}", file=sys.stderr)
        sys.exit(1)

    base_out = Path(args.output) if args.output else Path("../output") / apk_path.stem
    apktool_out = base_out / "apktool"
    jadx_out = base_out / "jadx"

    log(f"[*] Target: {apk_path.name}", quiet)
    log(f"[*] Output: {base_out}", quiet)

    # apktool and jadx don't depend on each other's output — both take the raw APK
    # as input. Running them sequentially was wasted wall-clock time. Parallel by
    # default; fall back to sequential with --sequential if you OOM on big APKs
    # (both are JVM processes, running two at once doubles peak memory).
    if args.sequential:
        log("[*] Running apktool...", quiet)
        apktool_result = run_apktool(str(apk_path), str(apktool_out))
        log("[*] Running jadx...", quiet)
        jadx_result = run_jadx(str(apk_path), str(jadx_out), timeout=args.jadx_timeout)
    else:
        log("[*] Running apktool + jadx in parallel...", quiet)
        with ThreadPoolExecutor(max_workers=2) as pool:
            # apktool progress off — its own spinner just adds noise since jadx
            # almost always takes longer and its file-count progress is more useful
            apktool_future = pool.submit(run_apktool, str(apk_path), str(apktool_out), False)
            jadx_future = pool.submit(run_jadx, str(apk_path), str(jadx_out), args.jadx_timeout, True)
            apktool_result = apktool_future.result()
            jadx_result = jadx_future.result()

    if not apktool_result["success"]:
        print(f"[!] apktool failed: {apktool_result.get('error')}", file=sys.stderr)
        sys.exit(1)
    log("[+] apktool OK — manifest + smali extracted", quiet)

    manifest_path = apktool_result["manifest_path"]
    jadx_sources_dir = jadx_result.get("sources_dir") if jadx_result["success"] in (True, "partial") else None

    if jadx_result["success"] is True:
        log(f"[+] jadx OK — {jadx_result['java_file_count']} Java files", quiet)
    elif jadx_result["success"] == "partial":
        log(f"[!] jadx partial: {jadx_result.get('error')}", quiet)
        log(f"[!] salvaged {jadx_result['java_file_count']} Java files — checks will run against partial coverage", quiet)
    else:
        log(f"[!] jadx failed: {jadx_result.get('error')}", quiet)
        log("[!] continuing with apktool-only data, jadx-dependent checks will be degraded", quiet)

    log("[*] Running checks...", quiet)

    results = {}

    log("    [1/5] permissions...", quiet)
    results["permissions"] = check_permissions(manifest_path, jadx_sources_dir)

    log("    [2/5] financial identifiers...", quiet)
    results["financial"] = check_financial(str(apktool_out), jadx_sources_dir)

    log("    [3/5] remote config / dead-drop...", quiet)
    results["remote_config"] = check_remote_config(str(apktool_out), jadx_sources_dir)

    log("    [4/5] anti-debug / anti-RE...", quiet)
    results["anti_debug"] = check_anti_debug(str(apktool_out), jadx_sources_dir)

    log("    [5/5] obfuscation heuristic...", quiet)
    if jadx_sources_dir:
        results["obfuscation"] = check_obfuscation(str(apktool_out), jadx_sources_dir)
    else:
        results["obfuscation"] = {"flag": False, "severity": "none", "error": "jadx sources unavailable"}

    flagged_checks = [name for name, r in results.items() if r.get("flag")]
    severities = [r.get("severity", "none") for r in results.values()]
    overall_severity = "high" if "high" in severities else ("medium" if "medium" in severities else "none")

    package_name = ""
    try:
        import xml.etree.ElementTree as ET
        package_name = ET.parse(manifest_path).getroot().get("package", "")
    except Exception:
        pass

    raw_result = {
        "apk": apk_path.name,
        "package": package_name,
        "apktool": {"success": apktool_result["success"]},
        "jadx": {
            "success": jadx_result["success"],
            "java_file_count": jadx_result.get("java_file_count", 0),
            "partial": jadx_result.get("partial", False),
        },
        "checks": results,
        "summary": {
            "flagged_checks": flagged_checks,
            "overall_severity": overall_severity,
        },
    }

    out_path = base_out / "result.json"
    out_path.parent.mkdir(parents=True, exist_ok=True)
    with open(out_path, "w") as f:
        json.dump(raw_result, f, indent=2)

    if args.json_only:
        print(json.dumps(raw_result, indent=2))
    else:
        print()
        print(f"[*] Package: {package_name}")
        print(f"[*] {len(flagged_checks)}/5 checks flagged: {', '.join(flagged_checks) if flagged_checks else 'none'}")
        print(f"[*] Overall severity: {overall_severity}")
        if jadx_result.get("partial"):
            print(f"[!] WARNING: jadx only partially decompiled this APK ({jadx_result['java_file_count']} files salvaged) — results may miss additional findings")
        print(f"[*] Full results written to {out_path}")


if __name__ == "__main__":
    main()