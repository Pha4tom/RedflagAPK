package okio;

import com.amz.apps.df2;
import com.amz.apps.ff2;
import com.amz.apps.gf2;
import com.amz.apps.hs;
import com.amz.apps.if2;
import com.amz.apps.iu1;
import com.amz.apps.kh0;
import com.amz.apps.le;
import com.amz.apps.x8;
import com.unity3d.services.core.device.reader.JsonStorageKeyNames;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: ByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final a d = new a(null);
    public static final ByteString o = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    public final byte[] a;
    public transient int b;
    public transient String c;

    /* JADX INFO: compiled from: ByteString.kt */
    public static final class a {
        public a(hs hsVar) {
        }

        public static /* synthetic */ ByteString of$default(a aVar, byte[] bArr, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = bArr.length;
            }
            return aVar.of(bArr, i, i2);
        }

        public final ByteString decodeBase64(String str) {
            kh0.checkNotNullParameter(str, "<this>");
            byte[] bArrDecodeBase64ToArray = df2.decodeBase64ToArray(str);
            if (bArrDecodeBase64ToArray != null) {
                return new ByteString(bArrDecodeBase64ToArray);
            }
            return null;
        }

        public final ByteString decodeHex(String str) {
            kh0.checkNotNullParameter(str, "<this>");
            int i = 0;
            if (!(str.length() % 2 == 0)) {
                throw new IllegalArgumentException(kh0.stringPlus("Unexpected hex string: ", str).toString());
            }
            int length = str.length() / 2;
            byte[] bArr = new byte[length];
            int i2 = length - 1;
            if (i2 >= 0) {
                while (true) {
                    int i3 = i + 1;
                    int i4 = i * 2;
                    bArr[i] = (byte) (ff2.access$decodeHexDigit(str.charAt(i4 + 1)) + (ff2.access$decodeHexDigit(str.charAt(i4)) << 4));
                    if (i3 > i2) {
                        break;
                    }
                    i = i3;
                }
            }
            return new ByteString(bArr);
        }

        public final ByteString encodeString(String str, Charset charset) {
            kh0.checkNotNullParameter(str, "<this>");
            kh0.checkNotNullParameter(charset, "charset");
            byte[] bytes = str.getBytes(charset);
            kh0.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
            return new ByteString(bytes);
        }

        public final ByteString encodeUtf8(String str) {
            kh0.checkNotNullParameter(str, "<this>");
            ByteString byteString = new ByteString(gf2.asUtf8ToByteArray(str));
            byteString.setUtf8$okio(str);
            return byteString;
        }

        public final ByteString of(byte[] bArr, int i, int i2) {
            kh0.checkNotNullParameter(bArr, "<this>");
            if2.checkOffsetAndCount(bArr.length, i, i2);
            return new ByteString(x8.copyOfRange(bArr, i, i2 + i));
        }
    }

    public ByteString(byte[] bArr) {
        kh0.checkNotNullParameter(bArr, JsonStorageKeyNames.DATA_KEY);
        this.a = bArr;
    }

    public static final ByteString encodeUtf8(String str) {
        return d.encodeUtf8(str);
    }

    public String base64() {
        return df2.encodeBase64$default(getData$okio(), null, 1, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        if (r0 < r1) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0028, code lost:
    
        if (r7 < r8) goto L13;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int compareTo(okio.ByteString r10) {
        /*
            r9 = this;
            java.lang.String r0 = "other"
            com.amz.apps.kh0.checkNotNullParameter(r10, r0)
            int r0 = r9.size()
            int r1 = r10.size()
            int r2 = java.lang.Math.min(r0, r1)
            r3 = 0
            r4 = r3
        L13:
            r5 = -1
            r6 = 1
            if (r4 >= r2) goto L2b
            byte r7 = r9.getByte(r4)
            r7 = r7 & 255(0xff, float:3.57E-43)
            byte r8 = r10.getByte(r4)
            r8 = r8 & 255(0xff, float:3.57E-43)
            if (r7 != r8) goto L28
            int r4 = r4 + 1
            goto L13
        L28:
            if (r7 >= r8) goto L32
            goto L30
        L2b:
            if (r0 != r1) goto L2e
            goto L33
        L2e:
            if (r0 >= r1) goto L32
        L30:
            r3 = r5
            goto L33
        L32:
            r3 = r6
        L33:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.compareTo(okio.ByteString):int");
    }

    public ByteString digest$okio(String str) throws NoSuchAlgorithmException {
        kh0.checkNotNullParameter(str, "algorithm");
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(getData$okio(), 0, size());
        byte[] bArrDigest = messageDigest.digest();
        kh0.checkNotNullExpressionValue(bArrDigest, "digestBytes");
        return new ByteString(bArrDigest);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == getData$okio().length && byteString.rangeEquals(0, getData$okio(), 0, getData$okio().length)) {
                return true;
            }
        }
        return false;
    }

    public final byte getByte(int i) {
        return internalGet$okio(i);
    }

    public final byte[] getData$okio() {
        return this.a;
    }

    public final int getHashCode$okio() {
        return this.b;
    }

    public int getSize$okio() {
        return getData$okio().length;
    }

    public final String getUtf8$okio() {
        return this.c;
    }

    public int hashCode() {
        int hashCode$okio = getHashCode$okio();
        if (hashCode$okio != 0) {
            return hashCode$okio;
        }
        int iHashCode = Arrays.hashCode(getData$okio());
        setHashCode$okio(iHashCode);
        return iHashCode;
    }

    public String hex() {
        char[] cArr = new char[getData$okio().length * 2];
        byte[] data$okio = getData$okio();
        int length = data$okio.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            byte b = data$okio[i];
            i++;
            int i3 = i2 + 1;
            cArr[i2] = ff2.getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = ff2.getHEX_DIGIT_CHARS()[b & 15];
        }
        return iu1.concatToString(cArr);
    }

    public byte[] internalArray$okio() {
        return getData$okio();
    }

    public byte internalGet$okio(int i) {
        return getData$okio()[i];
    }

    public final ByteString md5() {
        return digest$okio("MD5");
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        kh0.checkNotNullParameter(byteString, "other");
        return byteString.rangeEquals(i2, getData$okio(), i, i3);
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        kh0.checkNotNullParameter(bArr, "other");
        return i >= 0 && i <= getData$okio().length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && if2.arrayRangeEquals(getData$okio(), i, bArr, i2, i3);
    }

    public final void setHashCode$okio(int i) {
        this.b = i;
    }

    public final void setUtf8$okio(String str) {
        this.c = str;
    }

    public final ByteString sha1() {
        return digest$okio("SHA-1");
    }

    public final ByteString sha256() {
        return digest$okio("SHA-256");
    }

    public final int size() {
        return getSize$okio();
    }

    public final boolean startsWith(ByteString byteString) {
        kh0.checkNotNullParameter(byteString, "prefix");
        return rangeEquals(0, byteString, 0, byteString.size());
    }

    public ByteString toAsciiLowercase() {
        byte b;
        for (int i = 0; i < getData$okio().length; i++) {
            byte b2 = getData$okio()[i];
            byte b3 = (byte) 65;
            if (b2 >= b3 && b2 <= (b = (byte) 90)) {
                byte[] data$okio = getData$okio();
                byte[] bArrCopyOf = Arrays.copyOf(data$okio, data$okio.length);
                kh0.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
                bArrCopyOf[i] = (byte) (b2 + 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b4 = bArrCopyOf[i2];
                    if (b4 >= b3 && b4 <= b) {
                        bArrCopyOf[i2] = (byte) (b4 + 32);
                    }
                }
                return new ByteString(bArrCopyOf);
            }
        }
        return this;
    }

    public String toString() {
        String str;
        if (getData$okio().length == 0) {
            str = "[size=0]";
        } else {
            int iAccess$codePointIndexToCharIndex = ff2.access$codePointIndexToCharIndex(getData$okio(), 64);
            if (iAccess$codePointIndexToCharIndex != -1) {
                String strUtf8 = utf8();
                if (strUtf8 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring = strUtf8.substring(0, iAccess$codePointIndexToCharIndex);
                kh0.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String strReplace$default = iu1.replace$default(iu1.replace$default(iu1.replace$default(strSubstring, "\\", "\\\\", false, 4, (Object) null), StringUtils.LF, "\\n", false, 4, (Object) null), StringUtils.CR, "\\r", false, 4, (Object) null);
                if (iAccess$codePointIndexToCharIndex >= strUtf8.length()) {
                    return "[text=" + strReplace$default + ']';
                }
                return "[size=" + getData$okio().length + " text=" + strReplace$default + "…]";
            }
            if (getData$okio().length > 64) {
                StringBuilder sb = new StringBuilder("[size=");
                sb.append(getData$okio().length);
                sb.append(" hex=");
                int iResolveDefaultParameter = if2.resolveDefaultParameter(this, 64);
                if (!(iResolveDefaultParameter <= getData$okio().length)) {
                    throw new IllegalArgumentException(("endIndex > length(" + getData$okio().length + ')').toString());
                }
                if (!(iResolveDefaultParameter + 0 >= 0)) {
                    throw new IllegalArgumentException("endIndex < beginIndex".toString());
                }
                sb.append((iResolveDefaultParameter == getData$okio().length ? this : new ByteString(x8.copyOfRange(getData$okio(), 0, iResolveDefaultParameter))).hex());
                sb.append("…]");
                return sb.toString();
            }
            str = "[hex=" + hex() + ']';
        }
        return str;
    }

    public String utf8() {
        String utf8$okio = getUtf8$okio();
        if (utf8$okio != null) {
            return utf8$okio;
        }
        String utf8String = gf2.toUtf8String(internalArray$okio());
        setUtf8$okio(utf8String);
        return utf8String;
    }

    public void write$okio(le leVar, int i, int i2) {
        kh0.checkNotNullParameter(leVar, "buffer");
        ff2.commonWrite(this, leVar, i, i2);
    }
}
