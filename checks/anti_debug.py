import re

PATTERNS = [
    ("anti_debug_isdebuggerconnected", re.compile(b"isDebuggerConnected")),
    ("anti_debug_ptrace", re.compile(b"ptrace")),
    ("root_detect_test_keys", re.compile(b"test-keys")),
    ("root_detect_su_binary", re.compile(b"/system/xbin/su|/system/bin/su"))
]

def check_anti_debug(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)
