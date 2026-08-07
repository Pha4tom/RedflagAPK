import os
import json
import subprocess


# Signal categories produced by unflutter.
# These are actual analyzer signals, not random strings
# recovered from the Flutter AOT snapshot.
SIGNAL_RULES = {
    "auth": "flutter_aot_authorization",
    "net": "flutter_aot_network",
    "camera": "flutter_aot_camera",
    "location": "flutter_aot_location",
    "webview": "flutter_aot_webview",
    "device": "flutter_aot_device",
    "cloaking": "flutter_aot_cloaking",
    "encryption": "flutter_aot_encryption",
    "blockchain": "flutter_aot_blockchain",
    "gambling": "flutter_aot_gambling",
    "file": "flutter_aot_file",
    "host": "flutter_aot_host",
    "data": "flutter_aot_data",
    "sim": "flutter_aot_sim",
    "attribution": "flutter_aot_attribution",
    "url": "flutter_aot_url",
}


def find_libapp(apktool_dir):
    """Find Flutter's libapp.so inside Apktool output."""

    if not apktool_dir:
        return None

    lib_dir = os.path.join(apktool_dir, "lib")

    if not os.path.isdir(lib_dir):
        return None

    arm64 = []
    others = []

    for root, _, files in os.walk(lib_dir):
        if "libapp.so" not in files:
            continue

        path = os.path.join(root, "libapp.so")

        if "arm64-v8a" in root:
            arm64.append(path)
        else:
            others.append(path)

    # Prefer ARM64 because that is the architecture
    # currently supported by unflutter.
    if arm64:
        return arm64[0]

    if others:
        return others[0]

    return None


def run_unflutter(libapp_path, output_dir):
    """
    Run unflutter's complete analysis pipeline.

    Installed syntax:

        unflutter <libapp.so> --out <directory>
    """

    if not libapp_path:
        return {
            "available": False,
            "reason": "libapp.so not found",
            "findings": [],
        }

    os.makedirs(output_dir, exist_ok=True)

    try:
        result = subprocess.run(
            [
                "unflutter",
                libapp_path,
                "--out",
                output_dir,
            ],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            timeout=300,
        )

    except FileNotFoundError:
        return {
            "available": False,
            "reason": (
                "unflutter is not installed "
                "or not in PATH"
            ),
            "findings": [],
        }

    except subprocess.TimeoutExpired:
        return {
            "available": False,
            "reason": (
                "unflutter timed out after "
                "300 seconds"
            ),
            "findings": [],
        }

    except Exception as exc:
        return {
            "available": False,
            "reason": str(exc),
            "findings": [],
        }

    # unflutter can successfully generate useful
    # artifacts even if an optional step fails.
    signal_graph = os.path.join(
        output_dir,
        "signal_graph.json",
    )

    flutter_meta = os.path.join(
        output_dir,
        "flutter_meta.json",
    )

    asm_dir = os.path.join(
        output_dir,
        "asm",
    )

    has_output = (
        os.path.isfile(signal_graph)
        or os.path.isfile(flutter_meta)
        or os.path.isdir(asm_dir)
    )

    if not has_output:
        reason = (
            result.stderr.strip()
            or result.stdout.strip()
            or "unflutter produced no analysis output"
        )

        return {
            "available": False,
            "reason": reason[-4000:],
            "findings": [],
        }

    findings = extract_unflutter_findings(
        output_dir,
        libapp_path,
    )

    return {
        "available": True,
        "reason": None,
        "findings": findings,
    }


def load_json(path):
    """Safely load a JSON file."""

    if not os.path.isfile(path):
        return None

    try:
        with open(
            path,
            "r",
            encoding="utf-8",
        ) as f:
            return json.load(f)

    except Exception:
        return None


def clean_text(value):
    """
    Clean text returned by unflutter.

    This is intentionally conservative. We do not attempt
    to turn arbitrary binary-derived strings into findings.
    """

    if not isinstance(value, str):
        return ""

    # Remove ASCII control characters.
    result = []

    for char in value:
        code = ord(char)

        if code in (9, 10, 13) or code >= 32:
            result.append(char)

    value = "".join(result)

    # Collapse whitespace.
    return " ".join(value.split()).strip()


def classify_signal(signal_name):
    """Map an unflutter signal category to a RedflagAPK rule."""

    name = str(signal_name).lower()

    for key, rule_id in SIGNAL_RULES.items():
        if key == name or key in name:
            return rule_id

    return None


def extract_signal_names(data):
    """
    Extract signal category names from unflutter's
    structured signal graph.

    This only looks at structural signal information.
    It does NOT recursively scan every string.
    """

    signals = []

    def add(value):
        if isinstance(value, (str, int, float)):
            value = str(value).strip()

            if value:
                signals.append(value)

    def walk(value):

        if isinstance(value, dict):

            for key, child in value.items():

                key_lower = str(key).lower()

                # Known signal containers.
                if (
                    key_lower in SIGNAL_RULES
                    or key_lower.endswith("_signals")
                    or key_lower in (
                        "signals",
                        "signal_counts",
                        "categories",
                    )
                ):

                    if isinstance(child, dict):

                        for signal_name in child.keys():
                            add(signal_name)

                    elif isinstance(child, list):

                        for item in child:

                            if isinstance(item, dict):
                                # Some structures may contain:
                                # {"name": "auth", ...}
                                for field in (
                                    "name",
                                    "signal",
                                    "category",
                                    "type",
                                ):
                                    if field in item:
                                        add(item[field])

                            else:
                                add(item)

                # Continue through dictionaries so nested
                # signal structures can still be found.
                walk(child)

        elif isinstance(value, list):

            for child in value:
                walk(child)

    walk(data)

    return signals


def extract_signal_counts(data):
    """
    Extract numeric signal counts when unflutter exposes them.

    Returns:
        {
            "auth": 186,
            "webview": 154,
            ...
        }
    """

    counts = {}

    def walk(value):

        if isinstance(value, dict):

            for key, child in value.items():

                key_lower = str(key).lower()

                if key_lower in SIGNAL_RULES:

                    if isinstance(child, (int, float)):
                        counts[key_lower] = int(child)

                    elif isinstance(child, dict):

                        # Handle structures such as:
                        # {"auth": {"count": 186}}
                        for count_key in (
                            "count",
                            "matches",
                            "signals",
                            "total",
                        ):
                            count = child.get(count_key)

                            if isinstance(
                                count,
                                (int, float),
                            ):
                                counts[key_lower] = int(
                                    count
                                )
                                break

                walk(child)

        elif isinstance(value, list):

            for child in value:
                walk(child)

    walk(data)

    return counts


def extract_unflutter_findings(
    output_dir,
    libapp_path,
):
    """
    Convert unflutter's structured output into
    RedflagAPK findings.

    IMPORTANT:

    We do NOT scan:

        - signal.html
        - asm files
        - every string in flutter_meta.json
        - arbitrary URLs inside libapp.so

    Those contain enormous amounts of legitimate Flutter
    framework/dependency data and create false positives.
    """

    findings = []
    seen = set()

    def add_finding(
        rule_id,
        match,
        file_path=None,
    ):

        match = clean_text(match)

        if not match:
            return

        identifier = (
            rule_id,
            match,
        )

        if identifier in seen:
            return

        seen.add(identifier)

        findings.append({
            "rule_id": rule_id,
            "file": file_path or libapp_path,
            "match": match[:1000],
        })

    # --------------------------------------------------
    # Successful Flutter AOT analysis
    # --------------------------------------------------

    add_finding(
        "flutter_aot_analyzed",
        "Flutter AOT snapshot analyzed with unflutter",
    )

    # --------------------------------------------------
    # Signal graph
    # --------------------------------------------------

    signal_graph_path = os.path.join(
        output_dir,
        "signal_graph.json",
    )

    signal_data = load_json(
        signal_graph_path
    )

    if signal_data:

        # Prefer actual signal counts when available.
        counts = extract_signal_counts(
            signal_data
        )

        for signal_name, count in counts.items():

            rule_id = classify_signal(
                signal_name
            )

            if not rule_id:
                continue

            add_finding(
                rule_id,
                (
                    f"unflutter signal: "
                    f"{signal_name} "
                    f"({count} matches)"
                ),
                signal_graph_path,
            )

        # Also handle signal names if counts are not
        # directly exposed by the JSON structure.
        signal_names = extract_signal_names(
            signal_data
        )

        for signal_name in signal_names:

            rule_id = classify_signal(
                signal_name
            )

            if not rule_id:
                continue

            # If we already generated a count finding,
            # don't create a duplicate generic finding.
            if signal_name.lower() in counts:
                continue

            add_finding(
                rule_id,
                f"unflutter signal: {signal_name}",
                signal_graph_path,
            )

    # --------------------------------------------------
    # Flutter metadata
    # --------------------------------------------------

    meta_path = os.path.join(
        output_dir,
        "flutter_meta.json",
    )

    meta = load_json(
        meta_path
    )

    if meta:

        dart_version = meta.get("dart")

        if dart_version:
            add_finding(
                "flutter_aot_analyzed",
                f"Dart SDK {dart_version}",
                meta_path,
            )

        function_count = meta.get(
            "functions"
        )

        if isinstance(
            function_count,
            (int, float),
        ):
            add_finding(
                "flutter_aot_analyzed",
                (
                    f"{int(function_count)} "
                    f"Dart AOT functions analyzed"
                ),
                meta_path,
            )

        class_count = meta.get(
            "classes"
        )

        if isinstance(
            class_count,
            (int, float),
        ):
            add_finding(
                "flutter_aot_analyzed",
                (
                    f"{int(class_count)} "
                    f"Dart classes recovered"
                ),
                meta_path,
            )

    return findings


def check_flutter_aot(
    apktool_dir,
    output_dir,
):
    """
    Complete Flutter AOT analysis.

    output_dir should be the dedicated:

        flutter_aot/

    directory.

    Returns:

        {
            "detected": bool,
            "libapp": path or None,
            "available": bool,
            "reason": str or None,
            "findings": [...]
        }
    """

    libapp_path = find_libapp(
        apktool_dir
    )

    if not libapp_path:

        return {
            "detected": False,
            "libapp": None,
            "available": False,
            "reason": "libapp.so not found",
            "findings": [],
        }

    result = run_unflutter(
        libapp_path,
        output_dir,
    )

    return {
        "detected": True,
        "libapp": libapp_path,
        "available": result["available"],
        "reason": result.get("reason"),
        "findings": result.get(
            "findings",
            [],
        ),
    }