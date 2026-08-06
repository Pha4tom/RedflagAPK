#  RedflagAPK

[![Python](https://img.shields.io/badge/Python-3.8%2B-blue.svg)](https://www.python.org/)
[![Platform](https://img.shields.io/badge/Platform-Termux%20%7C%20Linux-green.svg)]
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Static APK triage for Android.**
>
> Automate the boring first stage of APK reverse engineering so you can spend more time investigating the interesting parts.

---

##  What is RedflagAPK?

RedflagAPK is a Python CLI that combines **apktool** and **JADX** with several static analysis checks to quickly identify suspicious indicators inside Android applications.

Instead of manually digging through thousands of files immediately after decompiling an APK, RedflagAPK highlights things that deserve attention first.

> [!NOTE]
> **RedflagAPK is NOT:**
>
> ❌ A malware scanner
>
> ❌ A sandbox
>
> ❌ A dynamic analysis framework
>
> ❌ A replacement for reverse engineering
>
> Every finding should be manually verified.

---

# 🔍 Checks

###  Permission Analysis

- Detects dangerous permissions declared in the manifest
- Flags permissions that are never referenced in application code

---

###  Financial Artifacts

Looks for:

- Cryptocurrency wallets
- IBANs
- Phone numbers near payment-related strings

Useful for spotting suspicious payment implementations.

---

###  Remote Configuration

Detects references to services commonly used for remote configuration or dead-drop infrastructure.

Examples include:

- GitHub Raw
- Pastebin
- GitHub Gists

---

###  Anti-Analysis

Detects common anti-analysis techniques including:

- Root detection
- Debugger checks
- Emulator detection
- Frida detection
- Debuggable manifests

---

### 🔒 Obfuscation

Estimates application obfuscation by measuring short class and method names within the application's own package.

---

### 📄 JSON Reports

Produces a structured `result.json` containing:

- Findings
- Evidence
- Severity
- Summary

---

# ⚙️ How It Works

```text
APK
 │
 ├── apktool
 │
 ├── JADX
 │
 └── Static Checks
      ├── Permissions
      ├── Financial
      ├── Remote Config
      ├── Anti Analysis
      └── Obfuscation

↓

Console Summary
+
result.json
```

---

#  Requirements

- Python 3.8+
- apktool
- JADX

Both tools must be available in your `PATH`.

---

#  Installation

## Termux

```bash
pkg update
pkg install python git apktool jadx

git clone https://github.com/Pha4tom/RedflagAPK.git

cd RedflagAPK
```

## Linux / macOS

```bash
git clone https://github.com/Pha4tom/RedflagAPK.git

cd RedflagAPK
```

---

#  Usage

```bash
python triage.py app.apk
```

### Options

| Option | Description |
|---------|-------------|
| `-o`, `--output` | Output directory |
| `-q`, `--quiet` | Suppress console output |
| `--json-only` | Output JSON only |
| `--jadx-timeout` | Set JADX timeout |

---

#  Example Output

```text
Scanning: sample.apk

Severity: HIGH

[!] Hardcoded phone number found near payment code

[!] Remote configuration endpoint
    raw.githubusercontent.com

[!] Frida detection found

[+] Obfuscation ratio: 42%

Report written to:
output/result.json
```

---

#  Limitations

Static analysis cannot detect everything.

RedflagAPK does **not** detect:

- Runtime-loaded payloads
- Native library behavior
- Dynamic network traffic
- Reflection-heavy code
- Encrypted payloads

Treat findings as indicators, **not proof** of malicious intent.

---

#  Why i made this?

I do almost all of my reverse engineering on Android using **Termux**.

Most Android APK tools either stop at decompilation or are filled with ads. I kept repeating the same manual checks every time I investigated an APK, so I automated them into a single CLI.

RedflagAPK isn't meant to replace reverse engineering.

It's meant to get you to the interesting parts faster.

---

# 📜 License

Licensed under the **MIT License**.
### JSON Reporting

Exports a structured JSON report containing:

- Individual check results
- Evidence
- Severity
- Overall summary



---

## License

This project is licensed under the MIT License.
