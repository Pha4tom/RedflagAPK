import sys
import base64

try:
    import termuxgui as tg
except ImportError:
    print("[!] Run: pip install termuxgui")
    sys.exit(1)

HTML_PAYLOAD = """<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { background: #121212; color: #e0e0e0; font-family: sans-serif; padding: 20px; }
        .card { background: #1e1e1e; border: 1px solid #333; border-radius: 12px; padding: 20px; }
        .badge { background: #00e676; color: #000; font-weight: bold; padding: 4px 10px; border-radius: 4px; }
    </style>
</head>
<body>
    <div class="card">
        <h2>🚩 RedflagAPK Engine</h2>
        <p>Termux:GUI WebView Engine Online!</p>
        <p>Status: <span class="badge">READY</span></p>
    </div>
</body>
</html>"""

def main():
    # Encode HTML string to Base64 Data URI to prevent white-screen parsing bugs
    b64_data = base64.b64encode(HTML_PAYLOAD.encode("utf-8")).decode("utf-8")
    data_uri = f"data:text/html;charset=utf-8;base64,{b64_data}"

    try:
        with tg.Connection() as conn:
            activity = tg.Activity(conn)
            webview = tg.WebView(activity)

            if hasattr(webview, "loadurl"):
                webview.loadurl(data_uri)
            elif hasattr(webview, "setdata"):
                webview.setdata(data_uri)

            print("[+] WebView rendered via Base64 Data-URI!")

            for event in conn.events():
                if (getattr(event, "type", None) or str(event)) == "destroy":
                    break
    except Exception as e:
        print(f"[!] Error: {e}")

if __name__ == "__main__":
    main()
