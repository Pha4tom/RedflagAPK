import re

PATTERNS = [
    ("financial_accessibility_binding", re.compile(b"AccessibilityService")),
    ("financial_overlay_trigger", re.compile(b"TYPE_APPLICATION_OVERLAY")),
    ("financial_sms_interceptor", re.compile(b"android.provider.Telephony.SMS_RECEIVED"))
]

def check_financial(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)
