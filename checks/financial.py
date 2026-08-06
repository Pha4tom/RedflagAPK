import re

PATTERNS = [
    # existing patterns — kept as-is
    ("financial_accessibility_binding", re.compile(b"AccessibilityService")),
    ("financial_overlay_trigger", re.compile(b"TYPE_APPLICATION_OVERLAY")),
    ("financial_sms_interceptor", re.compile(b"android.provider.Telephony.SMS_RECEIVED")),

    # added — actual financial/payment indicators (crypto wallets, IBANs, phone-adjacent payment strings)
    ("financial_btc_address", re.compile(rb"\b(bc1[a-zA-HJ-NP-Z0-9]{25,90}|[13][a-km-zA-HJ-NP-Z1-9]{25,34})\b")),
    ("financial_eth_address", re.compile(rb"\b0x[a-fA-F0-9]{40}\b")),
    ("financial_iban", re.compile(rb"\b[A-Z]{2}[0-9]{2}[A-Z0-9]{10,30}\b")),
    ("financial_usdt_trc20", re.compile(rb"\bT[A-Za-z1-9]{33}\b")),  # Tron/USDT-TRC20 addresses, common in scam APKs
    ("financial_egypt_phone_payment_context", re.compile(rb"(01[0-2,5][0-9]{8})")),  # Egyptian mobile numbers, common in Vodafone Cash / wallet scam context
]

def check_financial(file_path: str) -> list:
    from checks.common import scan_file_with_regex
    return scan_file_with_regex(file_path, PATTERNS)