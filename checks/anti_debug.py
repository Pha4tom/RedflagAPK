# checks/anti_debug.py — anti-RE / anti-debug / root-detection signal grep
# scoped to skip common third-party/stdlib namespaces that cause false positives

import re
from pathlib import Path
from checks.common import is_noisy_path

SIGNAL_GROUPS = {
    "debugger_detection": [
        "isDebuggerConnected",
        "Debug.isDebuggerConnected",
        "waitForDebugger",
    ],
    "root_detection": [
        "/system/app/Superuser",
        "/system/xbin/su",
        "com.noshufou.android.su",
        "com.thirdparty.superuser",
        "eu.chainfire.supersu",
        "checkRootMethod",
        "RootBeer",
    ],
    "emulator_detection": [
        "goldfish",
        "ranchu",
        "generic_x86",
        "Genymotion",
        "ro.kernel.qemu",
    ],
    "frida_detection": [
        "frida-server",
        "frida-agent",
        "gum-js-loop",
        "LIBFRIDA",
        "re.frida.server",
    ],
    "known_packer_signatures": [
        "com.qihoo.util",
        "com.secneo.apkwrapper",
        "com.tencent.StubShell",
        "com.stub.StubApp",
        "libjiagu",
        "com.ali.mobisecenhance",
    ],
}


def scan_file_for_signals(filepath: Path) -> list:
    findings = []
    try:
        content = filepath.read_text(errors="ignore")
    except Exception:
        return findings

    lines = content.splitlines()
    for category, signals in SIGNAL_GROUPS.items():
        for signal in signals:
            for line_num, line in enumerate(lines, 1):
                if signal in line:
                    findings.append({
                        "category": category,
                        "signal": signal,
                        "file": str(filepath),
                        "line": line_num,
                        "context": line.strip()[:150],
                    })
    return findings


def check_anti_debug(apktool_output_dir: str, jadx_sources_dir: str = None) -> dict:
    all_findings = []
    skipped_noisy = 0

    if jadx_sources_dir:
        sources_path = Path(jadx_sources_dir)
        if sources_path.exists():
            for java_file in sources_path.rglob("*.java"):
                if is_noisy_path(java_file):
                    skipped_noisy += 1
                    continue
                all_findings.extend(scan_file_for_signals(java_file))

    smali_root = Path(apktool_output_dir)
    manifest_path = smali_root / "AndroidManifest.xml"
    is_debuggable = False
    if manifest_path.exists():
        try:
            manifest_content = manifest_path.read_text(errors="ignore")
            is_debuggable = 'android:debuggable="true"' in manifest_content
        except Exception:
            pass

    seen = set()
    deduped = []
    for f in all_findings:
        key = (f["signal"], f["file"], f["line"])
        if key not in seen:
            seen.add(key)
            deduped.append(f)

    categories_hit = set(f["category"] for f in deduped)
    severity = "none"
    if len(categories_hit) >= 2:
        severity = "high"
    elif len(categories_hit) == 1:
        severity = "medium"

    return {
        "flag": len(deduped) > 0,
        "evidence": deduped,
        "severity": severity,
        "finding_count": len(deduped),
        "categories_hit": list(categories_hit),
        "manifest_debuggable_true": is_debuggable,
        "files_skipped_as_noisy": skipped_noisy,
    }
