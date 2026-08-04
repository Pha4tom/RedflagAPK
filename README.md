# apk-triage

A Termux-native CLI tool that decompiles Android APKs and runs static pattern checks for common fraud/scam indicators.

## What this is NOT

- Not a malware scanner
- Not a decompiler (it wraps apktool + jadx, doesn't replace them)
- Not proof of malice — a flag means "worth a human look," not "confirmed scam"

This tool exists to speed up manual APK triage, not replace it.

## What it checks

1. **Permissions** — dangerous permissions (SMS, contacts, camera, mic, location, call log, phone state, overlay, install-packages, accessibility) declared but never referenced in decompiled code
2. **Financial indicators** — hardcoded crypto addresses, IBANs, or phone numbers sitting near payment-context strings (wallet/deposit/withdraw/vodafone cash/instapay/fawry)
3. **Remote config / dead-drop hosting** — URLs pointing at pastebin, raw.githubusercontent, gist, hastebin, and similar services often used for remote C2/config
4. **Anti-debug / anti-analysis** — debugger detection, root detection, emulator detection, frida detection, known packer signatures
5. **Obfuscation ratio** — proportion of suspiciously short (≤2 char) class/method names within the app's actual package, as a rough obfuscation signal

Each check is scoped to exclude common noisy namespaces (kotlin, androidx, Firebase, OkHttp, Retrofit, Glide, RxJava, etc.) to cut down false positives from standard SDK behavior.

## Requirements

- Python 3
- [apktool](https://ibotpeaches.github.io/Apktool/)
- [jadx](https://github.com/skylot/jadx)

No third-party Python packages — everything is stdlib.

## Usage

```bash
python triage.py <path-to-apk> [-o OUTPUT_DIR] [-q] [--json-only] [--jadx-timeout SECONDS]
-o/--output — output directory (default: alongside the APK)
-q/--quiet — suppress progress output
--json-only — print only the final JSON result
--jadx-timeout — seconds before jadx decompilation is killed (default: 300)
Output: <output_dir>/result.json plus a console summary of flagged checks and overall severity (high / medium / none).
Known limitations
Static analysis only — won't catch runtime-decrypted strings, injected native libs, or dynamically-loaded code
Phone number regex is Egypt-format specific
Crypto address regex can false-positive on random hex/UUIDs
Obfuscation check uses regex, not real AST parsing
Doesn't detect timing (e.g. remote config fetched late vs early in app lifecycle)
A clean result is not a safety guarantee — always cross-check with something like MobSF or a manual cert diff before trusting an APK
Roadmap
[ ] Human-readable report formatting (currently JSON-only)
[ ] Android GUI wrapper (separate future project)
License
MIT
