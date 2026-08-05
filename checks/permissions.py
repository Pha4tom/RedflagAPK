# checks/permissions.py — flags permissions declared but not used, and dangerous perms in general

import re
import xml.etree.ElementTree as ET
from pathlib import Path

DANGEROUS_PERMS = {
    "android.permission.READ_SMS",
    "android.permission.SEND_SMS",
    "android.permission.RECEIVE_SMS",
    "android.permission.READ_CONTACTS",
    "android.permission.WRITE_CONTACTS",
    "android.permission.CAMERA",
    "android.permission.RECORD_AUDIO",
    "android.permission.ACCESS_FINE_LOCATION",
    "android.permission.READ_CALL_LOG",
    "android.permission.READ_PHONE_STATE",
    "android.permission.SYSTEM_ALERT_WINDOW",
    "android.permission.REQUEST_INSTALL_PACKAGES",
    "android.permission.BIND_ACCESSIBILITY_SERVICE",
}

PERM_CODE_HINTS = {
    "android.permission.READ_SMS": ["Telephony.Sms", "SmsManager", "content://sms"],
    "android.permission.SEND_SMS": ["SmsManager.sendTextMessage", "SmsManager"],
    "android.permission.RECEIVE_SMS": ["SMS_RECEIVED", "SmsMessage"],
    "android.permission.READ_CONTACTS": ["ContactsContract", "content://contacts"],
    "android.permission.WRITE_CONTACTS": ["ContactsContract"],
    "android.permission.CAMERA": ["Camera.open", "CameraManager", "camera2"],
    "android.permission.RECORD_AUDIO": ["MediaRecorder", "AudioRecord"],
    "android.permission.ACCESS_FINE_LOCATION": ["LocationManager", "FusedLocationProviderClient"],
    "android.permission.READ_CALL_LOG": ["CallLog.Calls"],
    "android.permission.READ_PHONE_STATE": ["TelephonyManager"],
    "android.permission.SYSTEM_ALERT_WINDOW": ["TYPE_APPLICATION_OVERLAY", "WindowManager.LayoutParams"],
    "android.permission.REQUEST_INSTALL_PACKAGES": ["PackageInstaller"],
    "android.permission.BIND_ACCESSIBILITY_SERVICE": ["AccessibilityService"],
}


def get_declared_permissions(manifest_path: str) -> list:
    try:
        tree = ET.parse(manifest_path)
    except ET.ParseError:
        return []

    root = tree.getroot()
    ns = {"android": "http://schemas.android.com/apk/res/android"}
    perms = []
    for elem in root.findall(".//uses-permission", ns):
        name = elem.get("{http://schemas.android.com/apk/res/android}name")
        if name:
            perms.append(name)
    return list(set(perms))


def search_code_for_hints(sources_dir: str, hints: list):
    if sources_dir is None:
        return None

    sources_path = Path(sources_dir)
    if not sources_path.exists():
        return None

    pattern = re.compile("|".join(re.escape(h) for h in hints))
    for java_file in sources_path.rglob("*.java"):
        try:
            content = java_file.read_text(errors="ignore")
            if pattern.search(content):
                return True
        except Exception:
            continue
    return False


def check_permissions(manifest_path: str, jadx_sources_dir: str) -> dict:
    declared = get_declared_permissions(manifest_path)
    dangerous_declared = [p for p in declared if p in DANGEROUS_PERMS]

    evidence = []
    unused_dangerous = []

    for perm in dangerous_declared:
        hints = PERM_CODE_HINTS.get(perm, [])
        used = search_code_for_hints(jadx_sources_dir, hints) if hints else None

        if used is False:
            unused_dangerous.append(perm)
            evidence.append(f"{perm} declared but no matching code usage found")
        elif used is None:
            evidence.append(f"{perm} declared, jadx sources unavailable to verify usage")

    severity = "none"
    if len(unused_dangerous) >= 3:
        severity = "high"
    elif len(unused_dangerous) >= 1:
        severity = "medium"

    return {
        "flag": len(unused_dangerous) > 0,
        "evidence": evidence,
        "severity": severity,
        "declared_dangerous_perms": dangerous_declared,
        "unused_dangerous_perms": unused_dangerous,
    }
