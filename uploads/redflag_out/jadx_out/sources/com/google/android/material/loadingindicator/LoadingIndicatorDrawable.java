package com.google.android.material.loadingindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.google.android.material.R;
import com.google.android.material.progressindicator.AnimatorDurationScaleProvider;

/* JADX INFO: loaded from: classes.dex */
public final class LoadingIndicatorDrawable extends Drawable implements Drawable.Callback {
    int alpha;
    private LoadingIndicatorAnimatorDelegate animatorDelegate;
    private final Context context;
    private LoadingIndicatorDrawingDelegate drawingDelegate;
    private final LoadingIndicatorSpec specs;
    private Drawable staticDummyDrawable;
    AnimatorDurationScaleProvider animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
    Paint paint = new Paint();

    public static LoadingIndicatorDrawable create(Context context, LoadingIndicatorSpec specs) {
        LoadingIndicatorDrawable loadingIndicatorDrawable = new LoadingIndicatorDrawable(context, specs, new LoadingIndicatorDrawingDelegate(specs), new LoadingIndicatorAnimatorDelegate(specs));
        loadingIndicatorDrawable.setStaticDummyDrawable(VectorDrawableCompat.create(context.getResources(), R.drawable.ic_mtrl_arrow_circle, null));
        return loadingIndicatorDrawable;
    }

    LoadingIndicatorDrawable(Context context, LoadingIndicatorSpec specs, LoadingIndicatorDrawingDelegate drawingDelegate, LoadingIndicatorAnimatorDelegate animatorDelegate) {
        this.context = context;
        this.specs = specs;
        this.drawingDelegate = drawingDelegate;
        this.animatorDelegate = animatorDelegate;
        animatorDelegate.registerDrawable(this);
        setAlpha(255);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect clipBounds = new Rect();
        Rect bounds = getBounds();
        if (bounds.isEmpty() || !isVisible() || !canvas.getClipBounds(clipBounds)) {
            return;
        }
        if (isSystemAnimatorDisabled() && this.staticDummyDrawable != null) {
            this.staticDummyDrawable.setBounds(bounds);
            this.staticDummyDrawable.setTint(this.specs.indicatorColors[0]);
            this.staticDummyDrawable.draw(canvas);
        } else {
            canvas.save();
            this.drawingDelegate.adjustCanvas(canvas, bounds);
            this.drawingDelegate.drawContainer(canvas, this.paint, this.specs.containerColor, getAlpha());
            this.drawingDelegate.drawIndicator(canvas, this.paint, this.animatorDelegate.indicatorState, getAlpha());
            canvas.restore();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        return setVisible(visible, restart, visible);
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animate) {
        boolean changed = super.setVisible(visible, restart);
        this.animatorDelegate.cancelAnimatorImmediately();
        if (visible && animate && !isSystemAnimatorDisabled()) {
            this.animatorDelegate.startAnimator();
        }
        return changed;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        if (this.alpha != alpha) {
            this.alpha = alpha;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private boolean isSystemAnimatorDisabled() {
        if (this.animatorDurationScaleProvider == null) {
            return false;
        }
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        return systemAnimatorDurationScale == 0.0f;
    }

    public Drawable getStaticDummyDrawable() {
        return this.staticDummyDrawable;
    }

    public void setStaticDummyDrawable(Drawable staticDummyDrawable) {
        this.staticDummyDrawable = staticDummyDrawable;
    }

    LoadingIndicatorAnimatorDelegate getAnimatorDelegate() {
        return this.animatorDelegate;
    }

    void setAnimatorDelegate(LoadingIndicatorAnimatorDelegate animatorDelegate) {
        this.animatorDelegate = animatorDelegate;
        animatorDelegate.registerDrawable(this);
    }

    LoadingIndicatorDrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }

    void setDrawingDelegate(LoadingIndicatorDrawingDelegate drawingDelegate) {
        this.drawingDelegate = drawingDelegate;
    }
}
