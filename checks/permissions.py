import os
import xml.etree.ElementTree as ET

# Expanded list of highly sensitive/dangerous permissions
DANGEROUS_PERMS = {
    "android.permission.SYSTEM_ALERT_WINDOW": "Overlay/System Alert Window",
    "android.permission.ACCESS_SUPERUSER": "Root Access (SU)",
    "android.permission.REQUEST_INSTALL_PACKAGES": "Can Install Other APKs",
    "android.permission.MANAGE_EXTERNAL_STORAGE": "Full File System Access",
    "android.permission.READ_PHONE_STATE": "Read Device Identifiers (IMEI/IMSI)",
    "android.permission.RECORD_AUDIO": "Microphone Access",
    "android.permission.CAMERA": "Camera Access",
    "android.permission.READ_SMS": "Read SMS Messages",
    "android.permission.SEND_SMS": "Send SMS Messages",
    "android.permission.RECEIVE_BOOT_COMPLETED": "Auto-Start on Boot"
}

def analyze_manifest(manifest_path: str) -> list:
    """Parses AndroidManifest.xml for dangerous permissions."""
    findings = []
    try:
        tree = ET.parse(manifest_path)
        root = tree.getroot()
        
        # XML namespaces are required to read 'android:name'
        ns = {'android': 'http://schemas.android.com/apk/res/android'}
        
        for perm in root.findall('uses-permission', ns):
            perm_name = perm.get(f"{{{ns['android']}}}name")
            
            if perm_name in DANGEROUS_PERMS:
                findings.append({
                    "type": "dangerous_permission",
                    "description": DANGEROUS_PERMS[perm_name],
                    "permission": perm_name
                })
                
    except Exception as e:
        print(f"\n[!] Error parsing manifest: {e}")
        
    return findings
