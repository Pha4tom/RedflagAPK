import re

PATTERNS = [
    ("obfuscation_dex_class_loader", re.compile(b"DexClassLoader")),
    ("obfuscation_reflection_invoke", re.compile(b"Ljava/lang/reflect/Method;->invoke")),
    ("obfuscation_base64_decode", re.compile(b"android/util/Base64;->decode"))
]

def check_obfuscation(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)
