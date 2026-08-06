# checks/common.py — shared constants used across multiple checks

# namespaces that generate false positives — stdlib, androidx, kotlin, common third-party libs
# these get skipped by default in checks that scan decompiled Java source
# checks/common.py
import threading

_print_lock = threading.Lock()

def progress_print(msg: str):
    """Thread-safe progress line. Don't use \r overwrite — Termux terminal
    handling of carriage returns across threads is unreliable and garbles output."""
    with _print_lock:
        print(msg, flush=True)

NOISY_NAMESPACES = [
    "kotlin/",
    "kotlinx/",
    "androidx/",
    "com/google/android/",
    "com/google/gson/",
    "com/google/firebase/",
    "okhttp3/",
    "okio/",
    "retrofit2/",
    "com/squareup/",
    "com/mikepenz/",       # material icon font libs, seen false-positive on license URLs
    "com/bumptech/glide/", # common image loading lib
    "io/reactivex/",       # RxJava
    "androidx/lifecycle/",
]


def is_noisy_path(filepath) -> bool:
    path_str = str(filepath).replace("\\", "/")
    return any(ns in path_str for ns in NOISY_NAMESPACES)
