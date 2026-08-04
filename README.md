# APK Triage

[![Python](https://img.shields.io/badge/python-3.8+-blue.svg)](https://www.python.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A lightweight Python CLI for quickly triaging Android APKs.

`apk-triage` wraps `apktool` and `jadx`, then performs a series of static checks to highlight suspicious behavior before you start manual reverse engineering.

It is designed to speed up the first stage of APK analysis, not replace it.

> [!NOTE]
> **This tool is not:**
>
> - A malware scanner
> - A sandbox or dynamic analysis tool
> - A custom decompiler
> - Proof that an APK is malicious
>
> Every finding should be manually verified.

---

## Features

- Detect unused dangerous permissions
- Find hardcoded crypto wallets, phone numbers, and payment-related strings
- Detect remote configuration endpoints (GitHub Raw, Pastebin, Gists, etc.)
- Identify common anti-analysis techniques
  - Root detection
  - Debugger checks
  - Emulator detection
  - Frida detection
- Estimate code obfuscation within the application's package
- Export results as JSON
- Uses only the Python standard library

Library packages such as `androidx`, `kotlin`, `firebase`, `retrofit`, `okhttp`, and other common frameworks are ignored where possible to reduce false positives.

---

## Requirements

- Python 3.8+
- Apktool
- JADX

Both `apktool` and `jadx` must be available in your `PATH`.

---

## Installation

### Termux

```bash
pkg update
pkg install python git apktool jadx

git clone https://github.com/Pha4tom/apk-triage.git
cd apk-triage
```

### Linux / macOS

```bash
git clone https://github.com/Pha4tom/apk-triage.git
cd apk-triage
```

Make sure `python3`, `apktool`, and `jadx` are installed and accessible from your terminal.

---

## Usage

```bash
python triage.py app.apk
```

### Options

| Option | Description |
|---------|-------------|
| `-o`, `--output` | Output directory |
| `-q`, `--quiet` | Suppress console output |
| `--json-only` | Print JSON results only |
| `--jadx-timeout` | Set JADX timeout (default: 300 seconds) |

### Examples

Run a normal scan:

```bash
python triage.py sample.apk
```

Save output somewhere else:

```bash
python triage.py sample.apk -o results/
```

Quiet mode:

```bash
python triage.py sample.apk -q
```

Output only JSON:

```bash
python triage.py sample.apk --json-only
```

---

## Example Output

```text
Scanning: sample.apk

Severity: HIGH

[!] Unused dangerous permission
    android.permission.RECEIVE_SMS

[!] Remote configuration endpoint
    raw.githubusercontent.com

[!] Frida detection found

[+] Obfuscation ratio: 42%

Report written to:
output/report.json
```

---

## Limitations

This is a static analysis tool.

It will not detect:

- Runtime-loaded or encrypted payloads
- Native library behavior
- Dynamic network traffic
- Reflection-heavy code that hides behavior

Treat findings as indicators, not evidence of malicious intent.

---

## License

This project is licensed under the MIT License.

See the [LICENSE](LICENSE) file for details.
