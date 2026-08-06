package okio;

import com.amz.apps.hf2;
import com.amz.apps.if2;
import com.amz.apps.kh0;
import com.amz.apps.le;
import com.amz.apps.ul1;
import com.amz.apps.x8;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: SegmentedByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SegmentedByteString extends ByteString {
    public final transient byte[][] p;
    public final transient int[] q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SegmentedByteString(byte[][] bArr, int[] iArr) {
        super(ByteString.o.getData$okio());
        kh0.checkNotNullParameter(bArr, "segments");
        kh0.checkNotNullParameter(iArr, "directory");
        this.p = bArr;
        this.q = iArr;
    }

    @Override // okio.ByteString
    public String base64() {
        return new ByteString(toByteArray()).base64();
    }

    @Override // okio.ByteString
    public ByteString digest$okio(String str) throws NoSuchAlgorithmException {
        kh0.checkNotNullParameter(str, "algorithm");
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        int length = getSegments$okio().length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = getDirectory$okio()[length + i];
            int i4 = getDirectory$okio()[i];
            messageDigest.update(getSegments$okio()[i], i3, i4 - i2);
            i++;
            i2 = i4;
        }
        byte[] bArrDigest = messageDigest.digest();
        kh0.checkNotNullExpressionValue(bArrDigest, "digestBytes");
        return new ByteString(bArrDigest);
    }

    @Override // okio.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(0, byteString, 0, size())) {
                return true;
            }
        }
        return false;
    }

    public final int[] getDirectory$okio() {
        return this.q;
    }

    public final byte[][] getSegments$okio() {
        return this.p;
    }

    @Override // okio.ByteString
    public int getSize$okio() {
        return getDirectory$okio()[getSegments$okio().length - 1];
    }

    @Override // okio.ByteString
    public int hashCode() {
        int hashCode$okio = getHashCode$okio();
        if (hashCode$okio != 0) {
            return hashCode$okio;
        }
        int length = getSegments$okio().length;
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        while (i < length) {
            int i4 = getDirectory$okio()[length + i];
            int i5 = getDirectory$okio()[i];
            byte[] bArr = getSegments$okio()[i];
            int i6 = (i5 - i3) + i4;
            while (i4 < i6) {
                i2 = (i2 * 31) + bArr[i4];
                i4++;
            }
            i++;
            i3 = i5;
        }
        setHashCode$okio(i2);
        return i2;
    }

    @Override // okio.ByteString
    public String hex() {
        return new ByteString(toByteArray()).hex();
    }

    @Override // okio.ByteString
    public byte[] internalArray$okio() {
        return toByteArray();
    }

    @Override // okio.ByteString
    public byte internalGet$okio(int i) {
        if2.checkOffsetAndCount(getDirectory$okio()[getSegments$okio().length - 1], i, 1L);
        int iSegment = hf2.segment(this, i);
        return getSegments$okio()[iSegment][(i - (iSegment == 0 ? 0 : getDirectory$okio()[iSegment - 1])) + getDirectory$okio()[getSegments$okio().length + iSegment]];
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        kh0.checkNotNullParameter(byteString, "other");
        if (i < 0 || i > size() - i3) {
            return false;
        }
        int i4 = i3 + i;
        int iSegment = hf2.segment(this, i);
        while (i < i4) {
            int i5 = iSegment == 0 ? 0 : getDirectory$okio()[iSegment - 1];
            int i6 = getDirectory$okio()[iSegment] - i5;
            int i7 = getDirectory$okio()[getSegments$okio().length + iSegment];
            int iMin = Math.min(i4, i6 + i5) - i;
            if (!byteString.rangeEquals(i2, getSegments$okio()[iSegment], (i - i5) + i7, iMin)) {
                return false;
            }
            i2 += iMin;
            i += iMin;
            iSegment++;
        }
        return true;
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        kh0.checkNotNullParameter(bArr, "other");
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i4 = i3 + i;
        int iSegment = hf2.segment(this, i);
        while (i < i4) {
            int i5 = iSegment == 0 ? 0 : getDirectory$okio()[iSegment - 1];
            int i6 = getDirectory$okio()[iSegment] - i5;
            int i7 = getDirectory$okio()[getSegments$okio().length + iSegment];
            int iMin = Math.min(i4, i6 + i5) - i;
            if (!if2.arrayRangeEquals(getSegments$okio()[iSegment], (i - i5) + i7, bArr, i2, iMin)) {
                return false;
            }
            i2 += iMin;
            i += iMin;
            iSegment++;
        }
        return true;
    }

    @Override // okio.ByteString
    public ByteString toAsciiLowercase() {
        return new ByteString(toByteArray()).toAsciiLowercase();
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[size()];
        int length = getSegments$okio().length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int i4 = getDirectory$okio()[length + i];
            int i5 = getDirectory$okio()[i];
            int i6 = i5 - i2;
            x8.copyInto(getSegments$okio()[i], bArr, i3, i4, i4 + i6);
            i3 += i6;
            i++;
            i2 = i5;
        }
        return bArr;
    }

    @Override // okio.ByteString
    public String toString() {
        return new ByteString(toByteArray()).toString();
    }

    @Override // okio.ByteString
    public void write$okio(le leVar, int i, int i2) {
        kh0.checkNotNullParameter(leVar, "buffer");
        int i3 = i + i2;
        int iSegment = hf2.segment(this, i);
        while (i < i3) {
            int i4 = iSegment == 0 ? 0 : getDirectory$okio()[iSegment - 1];
            int i5 = getDirectory$okio()[iSegment] - i4;
            int i6 = getDirectory$okio()[getSegments$okio().length + iSegment];
            int iMin = Math.min(i3, i5 + i4) - i;
            int i7 = (i - i4) + i6;
            ul1 ul1Var = new ul1(getSegments$okio()[iSegment], i7, i7 + iMin, true, false);
            ul1 ul1Var2 = leVar.a;
            if (ul1Var2 == null) {
                ul1Var.g = ul1Var;
                ul1Var.f = ul1Var;
                leVar.a = ul1Var;
            } else {
                kh0.checkNotNull(ul1Var2);
                ul1 ul1Var3 = ul1Var2.g;
                kh0.checkNotNull(ul1Var3);
                ul1Var3.push(ul1Var);
            }
            i += iMin;
            iSegment++;
        }
        leVar.setSize$okio(leVar.size() + ((long) i2));
    }
}
