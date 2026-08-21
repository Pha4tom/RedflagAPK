import os
import re
import subprocess
from pathlib import Path


RULES = {
    "flutter_http": r"https?://[^\s\"'<>]+",
}


# URLs that are normally just Flutter/Dart/framework metadata.
IGNORED_DOMAINS = {
    "api.flutter.dev",
    "flutter.dev",
    "dartbug.com",
    "example.com",
    "play.google.com",
    "raw.githubusercontent.com",
    "github.com",
}


def _extract_strings(filepath, min_length=8):
    """
    Extract printable ASCII strings from a binary.

    This is much safer than decoding the entire .so file because
    Flutter AOT binaries contain huge amounts of binary data mixed
    with legitimate strings.
    """
    try:
        result = subprocess.run(
            ["strings", "-n", str(min_length), filepath],
            capture_output=True,
            text=True,
            errors="ignore",
            timeout=30,
        )

        if result.returncode == 0:
            return result.stdout.splitlines()

    except (OSError, subprocess.TimeoutExpired):
        pass

    # Fallback if the `strings` command isn't available.
    try:
        data = Path(filepath).read_bytes()

        matches = re.findall(
            rb"[\x20-\x7e]{%d,}" % min_length,
            data,
        )

        return [
            item.decode("ascii", errors="ignore")
            for item in matches
        ]

    except Exception:
        return []


def _clean_url(url):
    """
    Remove characters that commonly get attached to URLs when
    extracting strings from binaries.
    """
    url = url.strip()

    # Common punctuation that isn't part of the URL.
    url = url.rstrip(".,;:!?)]}\"'")

    return url


def _domain(url):
    match = re.match(
        r"https?://([^/:?#]+)",
        url,
        re.IGNORECASE,
    )

    if not match:
        return ""

    return match.group(1).lower()


def _is_ignored_url(url):
    domain = _domain(url)

    if not domain:
        return True

    # Ignore known framework/documentation domains.
    for ignored in IGNORED_DOMAINS:
        if domain == ignored or domain.endswith("." + ignored):
            return True

    return False


def scan_flutter(apktool_dir, output_directory=None):
    """
    Scan Flutter native libraries for useful network indicators.

    Returns a list of findings compatible with triage.py.
    """

    findings = []

    if not os.path.isdir(apktool_dir):
        return findings

    lib_dir = os.path.join(apktool_dir, "lib")

    if not os.path.isdir(lib_dir):
        return findings

    # Flutter APKs normally contain libapp.so and libflutter.so.
    flutter_files = []

    for root, _, files in os.walk(lib_dir):
        for filename in files:
            if filename in ("libapp.so", "libflutter.so"):
                flutter_files.append(
                    os.path.join(root, filename)
                )

    if not flutter_files:
        return findings

    # One framework detection finding.
    findings.append({
        "rule_id": "flutter_detected",
        "file": "Flutter",
        "match": "Flutter framework detected",
    })

    seen_urls = set()

    for filepath in flutter_files:
        strings = _extract_strings(filepath)

        for string in strings:
            for match in re.findall(
                RULES["flutter_http"],
                string,
                re.IGNORECASE,
            ):
                url = _clean_url(match)

                if not url:
                    continue

                if _is_ignored_url(url):
                    continue

                # Avoid the same endpoint being reported hundreds
                # of times across different architectures.
                normalized = url.lower()

                if normalized in seen_urls:
                    continue

                seen_urls.add(normalized)

                findings.append({
                    "rule_id": "flutter_http",
                    "file": filepath,
                    "match": url,
                })

    return findings


# Compatibility aliases in case triage.py uses another function name.
scan = scan_flutter
check = scan_flutter
check_flutter = scan_flutter