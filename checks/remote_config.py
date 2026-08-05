# checks/remote_config.py — dead-drop / remote-config URL detection
# skips common third-party/stdlib namespaces (false-positive source)

import re
from pathlib import Path
from checks.common import is_noisy_path

DEAD_DROP_HOSTS = [
    r"pastebin\.com",
    r"raw\.githubusercontent\.com",
    r"gist\.github(?:usercontent)?\.com",
    r"hastebin\.com",
    r"paste\.ee",
    r"controlc\.com",
    r"rentry\.co",
    r"telegra\.ph",
    r"ghostbin\.\w+",
    r"dpaste\.\w+",
]

URL_PATTERN = re.compile(
    r"https?://(?:" + "|".join(DEAD_DROP_HOSTS) + r")[^\s\"'<>]*",
    re.IGNORECASE,
)


def scan_file_for_dead_drops(filepath: Path) -> list:
    findings = []
    try:
        content = filepath.read_text(errors="ignore")
    except Exception:
        return findings

    lines = content.splitlines()
    for line_num, line in enumerate(lines, 1):
        for match in URL_PATTERN.finditer(line):
            findings.append({
                "url": match.group(),
                "file": str(filepath),
                "line": line_num,
                "context": line.strip()[:150],
            })
    return findings


def check_remote_config(apktool_output_dir: str, jadx_sources_dir: str = None) -> dict:
    all_findings = []
    skipped_noisy = 0

    strings_path = Path(apktool_output_dir) / "res" / "values" / "strings.xml"
    if strings_path.exists():
        all_findings.extend(scan_file_for_dead_drops(strings_path))

    if jadx_sources_dir:
        sources_path = Path(jadx_sources_dir)
        if sources_path.exists():
            for java_file in sources_path.rglob("*.java"):
                if is_noisy_path(java_file):
                    skipped_noisy += 1
                    continue
                all_findings.extend(scan_file_for_dead_drops(java_file))

    seen = set()
    deduped = []
    for f in all_findings:
        key = (f["url"], f["file"], f["line"])
        if key not in seen:
            seen.add(key)
            deduped.append(f)

    severity = "high" if deduped else "none"

    return {
        "flag": len(deduped) > 0,
        "evidence": deduped,
        "severity": severity,
        "finding_count": len(deduped),
        "files_skipped_as_noisy": skipped_noisy,
    }
