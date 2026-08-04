# checks/financial.py — hardcoded wallet/payment identifier sweep
# scoped to skip common third-party/stdlib namespaces that cause false positives

import re
from pathlib import Path
from checks.common import is_noisy_path

PATTERNS = {
    "btc_address": re.compile(r"\b(bc1[a-zA-HJ-NP-Z0-9]{25,39}|[13][a-km-zA-HJ-NP-Z1-9]{25,34})\b"),
    "eth_address": re.compile(r"\b0x[a-fA-F0-9]{40}\b"),
    "iban": re.compile(r"\b[A-Z]{2}\d{2}[A-Z0-9]{10,30}\b"),
    "phone_number": re.compile(r"\b(01[0125]\d{8}|\+201[0125]\d{8})\b"),
}

PAYMENT_CONTEXT_WORDS = [
    "wallet", "deposit", "withdraw", "vodafone cash", "instapay",
    "fawry", "payment", "transfer", "recipient", "account number"
]


def scan_file_for_financial_patterns(filepath: Path) -> list:
    findings = []
    try:
        content = filepath.read_text(errors="ignore")
    except Exception:
        return findings

    lines = content.splitlines()

    for line_num, line in enumerate(lines, 1):
        for pattern_name, pattern in PATTERNS.items():
            for match in pattern.finditer(line):
                matched_str = match.group()

                if pattern_name == "phone_number":
                    nearby = " ".join(lines[max(0, line_num - 3):line_num + 2]).lower()
                    if not any(word in nearby for word in PAYMENT_CONTEXT_WORDS):
                        continue

                findings.append({
                    "type": pattern_name,
                    "match": matched_str,
                    "file": str(filepath),
                    "line": line_num,
                    "context": line.strip()[:150],
                })

    return findings


def check_financial(apktool_output_dir: str, jadx_sources_dir: str = None) -> dict:
    all_findings = []
    skipped_noisy = 0

    strings_path = Path(apktool_output_dir) / "res" / "values" / "strings.xml"
    if strings_path.exists():
        all_findings.extend(scan_file_for_financial_patterns(strings_path))

    if jadx_sources_dir:
        sources_path = Path(jadx_sources_dir)
        if sources_path.exists():
            for java_file in sources_path.rglob("*.java"):
                if is_noisy_path(java_file):
                    skipped_noisy += 1
                    continue
                all_findings.extend(scan_file_for_financial_patterns(java_file))

    severity = "none"
    if any(f["type"] in ("btc_address", "eth_address", "iban") for f in all_findings):
        severity = "high"
    elif any(f["type"] == "phone_number" for f in all_findings):
        severity = "medium"

    return {
        "flag": len(all_findings) > 0,
        "evidence": all_findings,
        "severity": severity,
        "finding_count": len(all_findings),
        "files_skipped_as_noisy": skipped_noisy,
    }
