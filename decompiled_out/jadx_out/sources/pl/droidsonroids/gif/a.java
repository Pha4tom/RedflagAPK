package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.util.TypedValue;
import android.widget.MediaController;
import com.amz.apps.bt0;
import com.amz.apps.n4;
import com.amz.apps.oh0;
import com.amz.apps.va0;
import com.amz.apps.vi1;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: GifDrawable.java */
/* JADX INFO: loaded from: classes2.dex */
public final class a extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    public final int A;
    public final int B;
    public final ScheduledThreadPoolExecutor a;
    public volatile boolean b;
    public long c;
    public final Rect d;
    public final Paint o;
    public final Bitmap p;
    public final GifInfoHandle q;
    public final ConcurrentLinkedQueue<n4> r;
    public ColorStateList s;
    public PorterDuffColorFilter t;
    public PorterDuff.Mode u;
    public final boolean v;
    public final oh0 w;
    public final d x;
    public final Rect y;
    public ScheduledFuture<?> z;

    /* JADX INFO: renamed from: pl.droidsonroids.gif.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: GifDrawable.java */
    public class C0153a extends vi1 {
        public C0153a(a aVar) {
            super(aVar);
        }

        @Override // com.amz.apps.vi1
        public void doWork() {
            a aVar = a.this;
            if (aVar.q.o()) {
                aVar.start();
            }
        }
    }

    /* JADX INFO: compiled from: GifDrawable.java */
    public class b extends vi1 {
        public final /* synthetic */ int b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(a aVar, int i) {
            super(aVar);
            this.b = i;
        }

        @Override // com.amz.apps.vi1
        public void doWork() {
            a aVar = a.this;
            aVar.q.s(aVar.p, this.b);
            this.a.w.sendEmptyMessageAtTime(-1, 0L);
        }
    }

    public a(ContentResolver contentResolver, Uri uri) throws IOException {
        GifInfoHandle gifInfoHandle;
        int i = GifInfoHandle.b;
        if ("file".equals(uri.getScheme())) {
            gifInfoHandle = new GifInfoHandle(uri.getPath());
        } else {
            AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            if (assetFileDescriptorOpenAssetFileDescriptor == null) {
                throw new IOException(bt0.d("Could not open AssetFileDescriptor for ", uri));
            }
            gifInfoHandle = new GifInfoHandle(assetFileDescriptorOpenAssetFileDescriptor);
        }
        this(gifInfoHandle);
    }

    public a(AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor));
    }

    public a(Resources resources, int i) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
        List<String> list = c.a;
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.density;
        if (i2 == 0) {
            i2 = 160;
        } else if (i2 == 65535) {
            i2 = 0;
        }
        int i3 = resources.getDisplayMetrics().densityDpi;
        float f = (i2 <= 0 || i3 <= 0) ? 1.0f : i3 / i2;
        this.B = (int) (this.q.e() * f);
        this.A = (int) (this.q.j() * f);
    }

    public a(GifInfoHandle gifInfoHandle) {
        this.b = true;
        this.c = Long.MIN_VALUE;
        this.d = new Rect();
        this.o = new Paint(6);
        this.r = new ConcurrentLinkedQueue<>();
        d dVar = new d(this);
        this.x = dVar;
        this.v = true;
        int i = va0.a;
        this.a = va0.a.a;
        this.q = gifInfoHandle;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(gifInfoHandle.j(), gifInfoHandle.e(), Bitmap.Config.ARGB_8888);
        this.p = bitmapCreateBitmap;
        bitmapCreateBitmap.setHasAlpha(true ^ gifInfoHandle.k());
        this.y = new Rect(0, 0, gifInfoHandle.j(), gifInfoHandle.e());
        this.w = new oh0(this);
        dVar.doWork();
        this.A = gifInfoHandle.j();
        this.B = gifInfoHandle.e();
    }

    public final void a(long j) {
        oh0 oh0Var = this.w;
        if (this.v) {
            this.c = 0L;
            oh0Var.sendEmptyMessageAtTime(-1, 0L);
            return;
        }
        ScheduledFuture<?> scheduledFuture = this.z;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        oh0Var.removeMessages(-1);
        this.z = this.a.schedule(this.x, Math.max(j, 0L), TimeUnit.MILLISECONDS);
    }

    public final PorterDuffColorFilter b(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        PorterDuffColorFilter porterDuffColorFilter = this.t;
        Paint paint = this.o;
        if (porterDuffColorFilter == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.t);
            z = true;
        }
        canvas.drawBitmap(this.p, this.y, this.d, paint);
        if (z) {
            paint.setColorFilter(null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.o.getAlpha();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return 100;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.o.getColorFilter();
    }

    public int getCurrentFrameIndex() {
        return this.q.a();
    }

    public int getCurrentLoop() {
        GifInfoHandle gifInfoHandle = this.q;
        int iB = gifInfoHandle.b();
        return (iB == 0 || iB < gifInfoHandle.f()) ? iB : iB - 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.q.c();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.q.d();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.B;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.A;
    }

    public int getNumberOfFrames() {
        return this.q.h();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (!this.q.k() || this.o.getAlpha() < 255) ? -2 : -1;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        if (this.v && this.b) {
            long j = this.c;
            if (j != Long.MIN_VALUE) {
                long jMax = Math.max(0L, j - SystemClock.uptimeMillis());
                this.c = Long.MIN_VALUE;
                this.a.remove(this.x);
                this.z = this.a.schedule(this.x, jMax, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return this.b;
    }

    public boolean isRecycled() {
        return this.q.l();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.b;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList;
        return super.isStateful() || ((colorStateList = this.s) != null && colorStateList.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.d.set(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.s;
        if (colorStateList == null || (mode = this.u) == null) {
            return false;
        }
        this.t = b(colorStateList, mode);
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        stop();
    }

    public void reset() {
        this.a.execute(new C0153a(this));
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        this.a.execute(new b(this, i));
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.o.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.o.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.o.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.o.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setLoopCount(int i) {
        this.q.t(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.s = colorStateList;
        this.t = b(colorStateList, this.u);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.u = mode;
        this.t = b(this.s, mode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!this.v) {
            if (z) {
                if (z2) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Animatable, android.widget.MediaController.MediaPlayerControl
    public void start() {
        synchronized (this) {
            if (this.b) {
                return;
            }
            this.b = true;
            a(this.q.p());
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        synchronized (this) {
            if (this.b) {
                this.b = false;
                ScheduledFuture<?> scheduledFuture = this.z;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                this.w.removeMessages(-1);
                this.q.r();
            }
        }
    }

    public String toString() {
        Locale locale = Locale.ENGLISH;
        GifInfoHandle gifInfoHandle = this.q;
        return String.format(locale, "GIF: size: %dx%d, frames: %d, error: %d", Integer.valueOf(gifInfoHandle.j()), Integer.valueOf(gifInfoHandle.e()), Integer.valueOf(gifInfoHandle.h()), Integer.valueOf(gifInfoHandle.g()));
    }
}
