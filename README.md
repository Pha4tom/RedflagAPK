# RedflagAPK

Static APK triage for Android.

RedflagAPK is a Python CLI that automates the first stage of Android APK analysis. It extracts an APK using **apktool**, decompiles it using **JADX**, then runs a set of static checks to highlight suspicious indicators before manual reverse engineering.

It is designed to speed up analysis, **not** determine whether an application is malicious.

---

## Features

### Permission Analysis

- Detects dangerous permissions declared in the manifest
- Flags dangerous permissions that are never referenced by the application's code

### Financial Artifact Detection

Searches the decompiled source for:

- Cryptocurrency wallet addresses
- IBANs
- Phone numbers found near payment-related code

### Remote Configuration Detection

Detects references to common remote configuration or dead-drop services, including:

- GitHub Raw
- Pastebin
- GitHub Gists

### Anti-Analysis Detection

Looks for common anti-analysis techniques including:

- Root detection
- Debugger detection
- Emulator detection
- Frida detection
- `android:debuggable="true"` in the manifest

### Obfuscation Estimation

Estimates application obfuscation by calculating the ratio of suspiciously short class and method names inside the application's own package.

### JSON Reporting

Exports a structured JSON report containing:

- Individual check results
- Evidence
- Severity
- Overall summary

---

## Analysis Pipeline

```
APK
 │
 ├── apktool
 │      └── Extract manifest & smali
 │
 ├── JADX
 │      └── Decompile Java source
 │
 └── Static Checks
        ├── Permissions
        ├── Financial artifacts
        ├── Remote configuration
        ├── Anti-analysis
        └── Obfuscation

↓

Console Summary
+
result.json
```

---

## Requirements

- Python 3.8+
- apktool
- JADX

Both `apktool` and `jadx` must be installed and available in your `PATH`.

---

## Installation

### Termux

```bash
pkg update
pkg install python git apktool jadx

git clone https://github.com/Pha4tom/RedflagAPK.git
cd RedflagAPK
```

### Linux / macOS

```bash
git clone https://github.com/Pha4tom/RedflagAPK.git
cd RedflagAPK
```

Install `apktool` and `jadx` using your system's package manager.

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
| `--json-only` | Output only JSON |
| `--jadx-timeout` | Set the JADX timeout (seconds) |

Examples:

```bash
python triage.py sample.apk

python triage.py sample.apk -o reports/

python triage.py sample.apk --json-only
```

---

## Example Report

```json
{
  "summary": {
    "flagged_checks": [
      "financial"
    ],
    "overall_severity": "medium"
  }
}
```

Example findings include:

- Hardcoded payment-related phone numbers
- Unused dangerous permissions
- Remote configuration endpoints
- Anti-analysis techniques
- Obfuscation statistics

---

## Limitations

RedflagAPK performs **static analysis only**.

It does not analyze:

- Runtime behavior
- Native libraries
- Network traffic
- Dynamically downloaded code
- Encrypted payloads

Findings should be treated as indicators that require manual verification.

---

## Project Structure

```
RedflagAPK
├── checks/
│   ├── permissions.py
│   ├── financial.py
│   ├── remote_config.py
│   ├── anti_debug.py
│   ├── obfuscation.py
│   └── common.py
├── runners/
│   ├── apktool.py
│   └── jadx.py
├── triage.py
└── example-result.json
```

---

## License

This project is licensed under the MIT License.
