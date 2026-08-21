# launcher.py
import sys
import time
import threading
import urllib.request

try:
    import termuxgui as tg
except ImportError:
    print("[!] Run: pip install termuxgui")
    sys.exit(1)

from app import app  # your Flask app object from app.py

SERVER_URL = "http://127.0.0.1:5000/"


def start_flask():
    app.run(host="127.0.0.1", port=5000, debug=False, use_reloader=False)


def wait_for_server(url, timeout=15):
    start = time.time()
    while time.time() - start < timeout:
        try:
            urllib.request.urlopen(url, timeout=1)
            return True
        except Exception:
            time.sleep(0.3)
    return False


def main():
    # Start Flask in the background so the WebView has something to load
    flask_thread = threading.Thread(target=start_flask, daemon=True)
    flask_thread.start()

    if not wait_for_server(SERVER_URL):
        print("[!] Flask server never came up, aborting.")
        sys.exit(1)

    try:
        with tg.Connection() as conn:
            activity = tg.Activity(conn)
            webview = tg.WebView(activity)

            if hasattr(webview, "loadurl"):
                webview.loadurl(SERVER_URL)
            elif hasattr(webview, "setdata"):
                webview.setdata(SERVER_URL)

            print(f"[+] WebView loaded {SERVER_URL}")

            for event in conn.events():
                if (getattr(event, "type", None) or str(event)) == "destroy":
                    break
    except Exception as e:
        print(f"[!] Error: {e}")


if __name__ == "__main__":
    main()