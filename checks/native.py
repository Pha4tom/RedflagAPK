import os
import re


PATTERNS = {
    "native_google_api_key":
        r"\bAIza[0-9A-Za-z\-_]{35}\b",

    "native_jwt":
        r"\beyJ[A-Za-z0-9_-]{10,}\.[A-Za-z0-9_-]{10,}\.[A-Za-z0-9_-]{10,}\b",

    "native_bearer_token":
        r"\bBearer\s+[A-Za-z0-9._~+/=-]{20,}",

    "native_websocket":
        r"wss?://[^\s\"'<>]+",

    "native_http_url":
        r"https?://[^\s\"'<>]+",

    "native_ipv4":
        r"\b(?:\d{1,3}\.){3}\d{1,3}\b",

    "native_email":
        r"\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b",
}


IGNORED_DOMAINS = (
    "w3.org",
    "unicode.org",
    "googlesource.com",
    "github.com",
    "dart-lang.org",
    "gcc.gnu.org",
    "android.com",
)


# Only indicators that are actually useful during triage.
INTERESTING_STRINGS = {
    "native_root": (
        "/system/bin/su",
        "/system/xbin/su",
        "/sbin/su",
        "magisk",
        "busybox",
    ),

    "native_debug": (
        "frida-agent",
        "frida-gadget",
        "gum-js-loop",
        "gdbserver",
        "lldb-server",
    ),

    "native_jni": (
        "JNI_OnLoad",
        "RegisterNatives",
    ),
}


def extract_strings(data):
    return re.findall(
        rb"[\x20-\x7e]{5,}",
        data,
    )


def is_ignored_url(value):
    value_lower = value.lower()

    return any(
        domain in value_lower
        for domain in IGNORED_DOMAINS
    )


def valid_ipv4(value):
    try:
        parts = value.split(".")

        if len(parts) != 4:
            return False

        return all(
            0 <= int(part) <= 255
            for part in parts
        )

    except ValueError:
        return False


def scan_patterns(text, file_path):
    findings = []

    for rule_id, pattern in PATTERNS.items():

        try:
            matches = set(
                re.findall(pattern, text)
            )
        except re.error:
            continue

        for match in matches:

            if rule_id in (
                "native_http_url",
                "native_websocket",
            ):
                if is_ignored_url(match):
                    continue

            if rule_id == "native_ipv4":
                if not valid_ipv4(match):
                    continue

            findings.append({
                "rule_id": rule_id,
                "file": file_path,
                "match": match,
            })

    return findings


def scan_interesting_strings(strings, file_path):
    findings = []

    # Keep only one finding per category per library.
    detected_categories = set()

    for raw_string in strings:

        try:
            value = raw_string.decode(
                "utf-8",
                errors="ignore",
            )
        except Exception:
            continue

        value_lower = value.lower()

        for rule_id, keywords in INTERESTING_STRINGS.items():

            if rule_id in detected_categories:
                continue

            for keyword in keywords:

                if keyword.lower() in value_lower:

                    findings.append({
                        "rule_id": rule_id,
                        "file": file_path,
                        "match": value[:500],
                    })

                    detected_categories.add(rule_id)
                    break

    return findings


def scan_native_library(file_path):
    findings = []

    try:
        with open(file_path, "rb") as f:
            data = f.read()
    except (OSError, IOError):
        return findings

    if not data:
        return findings

    strings = extract_strings(data)

    text = "\n".join(
        s.decode(
            "utf-8",
            errors="ignore",
        )
        for s in strings
    )

    findings.extend(
        scan_patterns(
            text,
            file_path,
        )
    )

    findings.extend(
        scan_interesting_strings(
            strings,
            file_path,
        )
    )

    return findings


def check_native(apktool_dir):
    findings = []

    if not apktool_dir:
        return findings

    lib_dir = os.path.join(
        apktool_dir,
        "lib",
    )

    if not os.path.isdir(lib_dir):
        return findings

    for root, _, files in os.walk(lib_dir):

        for file in files:

            if not file.endswith(".so"):
                continue

            path = os.path.join(
                root,
                file,
            )

            findings.extend(
                scan_native_library(path)
            )

    return findings