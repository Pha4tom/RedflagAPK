import os
import re


PATTERNS = {
    "flutter_google_api_key": r"\bAIza[0-9A-Za-z\-_]{35}\b",
    "flutter_jwt": r"\beyJ[A-Za-z0-9_-]{10,}\.[A-Za-z0-9_-]{10,}\.[A-Za-z0-9_-]{10,}\b",
    "flutter_bearer_token": r"\bBearer\s+[A-Za-z0-9._~+/=-]{20,}",
    "flutter_supabase": r"https?://[A-Za-z0-9.-]+\.supabase\.co[^\s\"'<>]*",
    "flutter_firebase": r"https?://[A-Za-z0-9.-]+\.firebaseio\.com[^\s\"'<>]*",
    "flutter_aws": r"https?://[A-Za-z0-9.-]+\.amazonaws\.com[^\s\"'<>]*",
    "flutter_websocket": r"wss?://[^\s\"'<>]+",
    "flutter_http": r"https?://[^\s\"'<>]+",
}


# Documentation / framework domains commonly embedded in Flutter itself.
IGNORED_DOMAINS = (
    "w3.org",
    "unicode.org",
    "googlesource.com",
    "github.com",
    "dart-lang.org",
    "gcc.gnu.org",
    "android.com",
)


ASSET_EXTENSIONS = (
    ".json",
    ".txt",
    ".env",
    ".yaml",
    ".yml",
    ".xml",
)


def is_ignored_url(value):
    value_lower = value.lower()

    return any(
        domain in value_lower
        for domain in IGNORED_DOMAINS
    )


def scan_data(data, file_path):
    findings = []

    for rule_id, pattern in PATTERNS.items():

        try:
            matches = set(re.findall(pattern, data))
        except re.error:
            continue

        for match in matches:

            if rule_id == "flutter_http":
                if is_ignored_url(match):
                    continue

            findings.append({
                "rule_id": rule_id,
                "file": file_path,
                "match": match,
            })

    return findings


def scan_binary(path):
    try:
        with open(path, "rb") as f:
            data = f.read()

        text = data.decode(
            "latin1",
            errors="ignore"
        )

        return scan_data(text, path)

    except (OSError, IOError):
        return []


def scan_text_file(path):
    try:
        with open(
            path,
            "r",
            encoding="utf-8",
            errors="ignore",
        ) as f:
            data = f.read()

        return scan_data(data, path)

    except (OSError, IOError):
        return []


def check_flutter(apktool_dir):
    findings = []
    flutter_found = False

    if not apktool_dir:
        return findings

    lib_dir = os.path.join(
        apktool_dir,
        "lib"
    )

    assets_dir = os.path.join(
        apktool_dir,
        "assets",
        "flutter_assets"
    )

    # Detect Flutter native libraries.
    if os.path.isdir(lib_dir):

        for root, _, files in os.walk(lib_dir):

            if "libflutter.so" in files:
                flutter_found = True

            if "libapp.so" in files:
                flutter_found = True

                path = os.path.join(
                    root,
                    "libapp.so"
                )

                findings.extend(
                    scan_binary(path)
                )

    # Scan Flutter assets.
    if os.path.isdir(assets_dir):

        flutter_found = True

        for root, _, files in os.walk(assets_dir):

            for file in files:

                if (
                    file.endswith(ASSET_EXTENSIONS)
                    or file in (
                        "AssetManifest.json",
                        "FontManifest.json",
                        "NOTICES.Z",
                    )
                ):
                    path = os.path.join(
                        root,
                        file
                    )

                    findings.extend(
                        scan_text_file(path)
                    )

    # Add framework detection once.
    if flutter_found:

        findings.insert(
            0,
            {
                "rule_id": "flutter_detected",
                "file": "Flutter",
                "match": "Flutter framework detected",
            },
        )

    return findings