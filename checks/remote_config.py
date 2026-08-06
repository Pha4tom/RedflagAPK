import re

PATTERNS = [
    ("remote_config_ip_literal", re.compile(b"https?://[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")),
    ("remote_config_dynamic_load", re.compile(b"loadClass|findClass"))
]

def check_remote_config(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)
