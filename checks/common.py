import os
import re
import mmap

# Pre-compile noisy third-party libraries to ignore during static scan
IGNORED_DIRS = {
    'androidx', 'android/support', 'com/google', 'kotlin',
    'kotlinx', 'com/facebook', 'io/reactivex', 'com/squareup'
}

def is_third_party(file_path: str) -> bool:
    """Check if the file belongs to common third-party libraries."""
    normalized = file_path.replace('\\', '/')
    return any(ignored in normalized for ignored in IGNORED_DIRS)

def scan_file_with_regex(file_path: str, compiled_regexes: list) -> list:
    """Scan a single file using memory-mapped reading and pre-compiled regexes."""
    matches = []
    if is_third_party(file_path):
        return matches

    try:
        with open(file_path, "rb") as f:
            if os.path.getsize(file_path) == 0:
                return matches
            with mmap.mmap(f.fileno(), 0, access=mmap.ACCESS_READ) as mm:
                for rule_id, pattern in compiled_regexes:
                    if pattern.search(mm):
                        matches.append({
                            "rule_id": rule_id,
                            "file": file_path
                        })
    except Exception:
        pass
    return matches
