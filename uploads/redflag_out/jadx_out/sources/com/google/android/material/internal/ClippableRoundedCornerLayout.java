package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public class ClippableRoundedCornerLayout extends FrameLayout {
    private float[] cornerRadii;
    private Path path;

    public ClippableRoundedCornerLayout(Context context) {
        super(context);
        this.cornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.cornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.path == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public void resetClipBoundsAndCornerRadii() {
        this.path = null;
        this.cornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        invalidate();
    }

    public float[] getCornerRadii() {
        return this.cornerRadii;
    }

    public void updateCornerRadii(float[] cornerRadii) {
        updateClipBoundsAndCornerRadii(getLeft(), getTop(), getRight(), getBottom(), cornerRadii);
    }

    public void updateClipBoundsAndCornerRadii(Rect rect, float[] cornerRadii) {
        updateClipBoundsAndCornerRadii(rect.left, rect.top, rect.right, rect.bottom, cornerRadii);
    }

    public void updateClipBoundsAndCornerRadii(float left, float top, float right, float bottom, float[] cornerRadii) {
        updateClipBoundsAndCornerRadii(new RectF(left, top, right, bottom), cornerRadii);
    }

    public void updateClipBoundsAndCornerRadii(RectF rectF, float[] cornerRadii) {
        if (this.path == null) {
            this.path = new Path();
        }
        this.cornerRadii = cornerRadii;
        this.path.reset();
        this.path.addRoundRect(rectF, cornerRadii, Path.Direction.CW);
        this.path.close();
        invalidate();
    }
}
