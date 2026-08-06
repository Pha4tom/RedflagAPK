package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Build;
import android.system.Os;
import com.amz.apps.vd1;
import com.amz.apps.zz;
import java.io.FileDescriptor;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
final class GifInfoHandle {
    public static final /* synthetic */ int b = 0;
    public volatile long a;

    static {
        try {
            System.loadLibrary("pl_droidsonroids_gif");
        } catch (UnsatisfiedLinkError unused) {
            if (zz.a == null) {
                try {
                    zz.a = (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
                } catch (Exception e) {
                    throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
                }
            }
            vd1.loadLibrary(zz.a, "pl_droidsonroids_gif");
        }
    }

    public GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        int iCreateTempNativeFileDescriptor;
        try {
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            long startOffset = assetFileDescriptor.getStartOffset();
            if (Build.VERSION.SDK_INT > 27) {
                try {
                    iCreateTempNativeFileDescriptor = createTempNativeFileDescriptor();
                    Os.dup2(fileDescriptor, iCreateTempNativeFileDescriptor);
                } finally {
                }
            } else {
                iCreateTempNativeFileDescriptor = extractNativeFileDescriptor(fileDescriptor, false);
            }
            this.a = openNativeFileDescriptor(iCreateTempNativeFileDescriptor, startOffset);
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th) {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused2) {
            }
            throw th;
        }
    }

    public GifInfoHandle(String str) throws GifIOException {
        this.a = openFile(str);
    }

    public static native int createTempNativeFileDescriptor() throws GifIOException;

    public static native int extractNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException;

    private static native void free(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native int getNativeErrorCode(long j);

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native int getWidth(long j);

    private static native boolean isOpaque(long j);

    public static native long openFile(String str) throws GifIOException;

    public static native long openNativeFileDescriptor(int i, long j) throws GifIOException;

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    private static native void setLoopCount(long j, char c);

    public final synchronized int a() {
        return getCurrentFrameIndex(this.a);
    }

    public final synchronized int b() {
        return getCurrentLoop(this.a);
    }

    public final synchronized int c() {
        return getCurrentPosition(this.a);
    }

    public final synchronized int d() {
        return getDuration(this.a);
    }

    public final synchronized int e() {
        return getHeight(this.a);
    }

    public final synchronized int f() {
        return getLoopCount(this.a);
    }

    public void finalize() throws Throwable {
        try {
            m();
        } finally {
            super.finalize();
        }
    }

    public final synchronized int g() {
        return getNativeErrorCode(this.a);
    }

    public final synchronized int h() {
        return getNumberOfFrames(this.a);
    }

    public final synchronized long[] i() {
        return getSavedState(this.a);
    }

    public final synchronized int j() {
        return getWidth(this.a);
    }

    public final synchronized boolean k() {
        return isOpaque(this.a);
    }

    public final synchronized boolean l() {
        return this.a == 0;
    }

    public final synchronized void m() {
        free(this.a);
        this.a = 0L;
    }

    public final synchronized long n(Bitmap bitmap) {
        return renderFrame(this.a, bitmap);
    }

    public final synchronized boolean o() {
        return reset(this.a);
    }

    public final synchronized long p() {
        return restoreRemainder(this.a);
    }

    public final synchronized int q(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.a, jArr, bitmap);
    }

    public final synchronized void r() {
        saveRemainder(this.a);
    }

    public final synchronized void s(Bitmap bitmap, int i) {
        seekToTime(this.a, i, bitmap);
    }

    public final void t(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.a, (char) i);
        }
    }
}
