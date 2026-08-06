import re

PATTERNS = [
    # existing patterns — kept as-is
    ("anti_debug_isdebuggerconnected", re.compile(b"isDebuggerConnected")),
    ("anti_debug_ptrace", re.compile(b"ptrace")),
    ("root_detect_test_keys", re.compile(b"test-keys")),
    ("root_detect_su_binary", re.compile(b"/system/xbin/su|/system/bin/su")),

    # added — known packer/protector signatures (seen in your Hamo Tunnel Plus teardown)
    ("packer_jiagu", re.compile(rb"com/qihoo/util/StubApp|libjiagu")),
    ("packer_bangcle", re.compile(rb"com/secneo/apkwrapper|libsecexe")),
    ("packer_tencent_legu", re.compile(rb"com/tencent/StubShell|libshell")),
    ("packer_ijiami", re.compile(rb"com/ijm/library|libexecmain")),
]

def check_anti_debug(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)