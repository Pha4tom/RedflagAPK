import re

PATTERNS = [
    # existing patterns — kept as-is
    ("remote_config_ip_literal", re.compile(b"https?://[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")),
    ("remote_config_dynamic_load", re.compile(b"loadClass|findClass")),

    # added — dead-drop / C2 remote config hosting patterns (Hamo Tunnel Plus style)
    ("remote_config_pastebin", re.compile(rb"pastebin\.com/(raw/)?[A-Za-z0-9]+")),
    ("remote_config_gist", re.compile(rb"gist\.githubusercontent\.com")),
    ("remote_config_raw_github", re.compile(rb"raw\.githubusercontent\.com")),
    ("remote_config_firebase_rtdb", re.compile(rb"[a-zA-Z0-9-]+\.firebaseio\.com")),  # remote-config abuse via Firebase RTDB is common too
]

def check_remote_config(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)