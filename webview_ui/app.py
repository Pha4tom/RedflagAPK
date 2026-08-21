import os
import sys
import json
import subprocess
import threading
from flask import Flask, jsonify, request, send_from_directory

# Absolute path resolution
WEBVIEW_DIR = os.path.dirname(os.path.abspath(__file__))
REDFLAG_DIR = os.path.dirname(WEBVIEW_DIR)
APK_LAB_DIR = os.path.dirname(REDFLAG_DIR)
STATIC_DIR = os.path.join(WEBVIEW_DIR, "static")

TRIAGE_SCRIPT = os.path.join(REDFLAG_DIR, "triage.py")
OUTPUT_BASE_DIR = os.path.join(APK_LAB_DIR, "output")
SAMPLES_DIR = os.path.join(APK_LAB_DIR, "samples")

# static_url_path='' routes /style.css directly to static/style.css
app = Flask(__name__, static_folder=STATIC_DIR, static_url_path='')

SCAN_STATE = {"running": False, "logs": [], "report": None}

def resolve_target_path(raw_path):
    raw_path = raw_path.strip()
    
    # Handle home shortcut
    if raw_path.startswith("~"):
        return os.path.expanduser(raw_path)
    
    # Handle absolute system paths (/sdcard/, /storage/, /data/)
    if raw_path.startswith("/") or os.path.isabs(raw_path):
        return raw_path

    # Check relative to root apk-lab directory
    lab_relative = os.path.abspath(os.path.join(APK_LAB_DIR, raw_path))
    if os.path.exists(lab_relative):
        return lab_relative

    # Check relative to samples directory
    sample_relative = os.path.abspath(os.path.join(SAMPLES_DIR, raw_path))
    if os.path.exists(sample_relative):
        return sample_relative

    return lab_relative


def run_triage_process(apk_path, output_dir, threads):
    global SCAN_STATE
    SCAN_STATE["running"] = True
    SCAN_STATE["logs"] = [f"[*] Target: {apk_path}", f"[*] Output: {output_dir}"]

    cmd = [
        sys.executable, TRIAGE_SCRIPT,
        "--apk", apk_path,
        "-o", output_dir,
        "--threads", str(threads)
    ]

    try:
        process = subprocess.Popen(
            cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True, bufsize=1
        )
        for line in iter(process.stdout.readline, ''):
            clean = line.strip()
            if clean:
                SCAN_STATE["logs"].append(clean)
        process.wait()

        report_file = os.path.join(output_dir, "report.json")
        if os.path.exists(report_file):
            with open(report_file, 'r', encoding='utf-8') as f:
                SCAN_STATE["report"] = json.load(f)
            SCAN_STATE["logs"].append("[+] Report generated successfully!")
        else:
            SCAN_STATE["logs"].append("[!] Triage finished but report.json missing.")

    except Exception as e:
        SCAN_STATE["logs"].append(f"[!] Execution error: {e}")

    SCAN_STATE["running"] = False

@app.route('/')
def index():
    return send_from_directory(STATIC_DIR, 'index.html')

@app.route('/api/scan', methods=['POST'])
def api_scan():
    global SCAN_STATE
    if SCAN_STATE["running"]:
        return jsonify({"status": "error", "message": "A scan is already in progress"}), 400

    data = request.get_json() or {}
    raw_path = data.get("apk_path", "")
    threads = data.get("threads", 4)

    apk_path = resolve_target_path(raw_path)

    if not os.path.exists(apk_path):
        return jsonify({"status": "error", "message": f"File not found: {apk_path}"}), 404

    apk_name = os.path.splitext(os.path.basename(apk_path))[0]
    scan_output_dir = os.path.join(OUTPUT_BASE_DIR, f"{apk_name}_report")
    os.makedirs(scan_output_dir, exist_ok=True)

    thread = threading.Thread(
        target=run_triage_process, 
        args=(apk_path, scan_output_dir, threads), 
        daemon=True
    )
    thread.start()

    return jsonify({"status": "started", "output_dir": scan_output_dir})

@app.route('/api/status')
def api_status():
    return jsonify(SCAN_STATE)

if __name__ == '__main__':
    print(f"[*] Serving files from: {STATIC_DIR}")
    print(f"[*] Dashboard Online: http://127.0.0.1:5000")
    app.run(host='127.0.0.1', port=5000, debug=False)
