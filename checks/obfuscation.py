# checks/obfuscation.py — heuristic: ratio of single/double-letter class/method names
# scoped to the app's own package (pulled from manifest), skips library noise

import re
import xml.etree.ElementTree as ET
from pathlib import Path

# matches class declarations and method declarations with short names
CLASS_PATTERN = re.compile(r"\bclass\s+([A-Za-z_$][A-Za-z0-9_$]*)")
METHOD_PATTERN = re.compile(
    r"\b(?:public|private|protected|static|final|\s)+[\w<>\[\],\s]+?\s+([a-zA-Z_$][a-zA-Z0-9_$]*)\s*\("
)

# names 1-2 chars long, common obfuscation output (a, b, c, aa, ab, m1, etc)
def is_short_name(name: str) -> bool:
    return len(name) <= 2 and name not in ("if", "in", "do")  # dodge keyword false positives


def get_package_name(manifest_path: str) -> str:
    try:
        tree = ET.parse(manifest_path)
        root = tree.getroot()
        return root.get("package", "")
    except Exception:
        return ""


def scan_file_for_short_names(filepath: Path) -> tuple:
    """Returns (total_classes, short_classes, total_methods, short_methods)."""
    try:
        content = filepath.read_text(errors="ignore")
    except Exception:
        return (0, 0, 0, 0)

    classes = CLASS_PATTERN.findall(content)
    methods = METHOD_PATTERN.findall(content)

    short_classes = sum(1 for c in classes if is_short_name(c))
    short_methods = sum(1 for m in methods if is_short_name(m))

    return (len(classes), short_classes, len(methods), short_methods)


def check_obfuscation(apktool_output_dir: str, jadx_sources_dir: str) -> dict:
    manifest_path = Path(apktool_output_dir) / "AndroidManifest.xml"
    package_name = get_package_name(str(manifest_path))

    if not package_name:
        return {
            "flag": False,
            "severity": "none",
            "error": "could not determine package name from manifest, skipping scoped scan",
        }

    package_path = package_name.replace(".", "/")
    sources_path = Path(jadx_sources_dir)

    if not sources_path.exists():
        return {
            "flag": False,
            "severity": "none",
            "error": "jadx sources not available, cannot run obfuscation heuristic",
        }

    # scope: only files under the app's own package path
    app_files = [
        f for f in sources_path.rglob("*.java")
        if package_path in str(f).replace("\\", "/")
    ]

    total_classes = total_short_classes = 0
    total_methods = total_short_methods = 0

    for f in app_files:
        tc, sc, tm, sm = scan_file_for_short_names(f)
        total_classes += tc
        total_short_classes += sc
        total_methods += tm
        total_short_methods += sm

    class_ratio = (total_short_classes / total_classes) if total_classes else 0
    method_ratio = (total_short_methods / total_methods) if total_methods else 0

    # thresholds are rough — real ProGuard/R8 obfuscated apps typically show >40-50% short names
    severity = "none"
    if class_ratio > 0.4 or method_ratio > 0.4:
        severity = "high"
    elif class_ratio > 0.15 or method_ratio > 0.15:
        severity = "medium"

    return {
        "flag": severity != "none",
        "severity": severity,
        "package_scanned": package_name,
        "app_file_count": len(app_files),
        "total_classes": total_classes,
        "short_classes": total_short_classes,
        "class_short_name_ratio": round(class_ratio, 3),
        "total_methods": total_methods,
        "short_methods": total_short_methods,
        "method_short_name_ratio": round(method_ratio, 3),
    }
