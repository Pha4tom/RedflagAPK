package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

/* JADX INFO: loaded from: classes.dex */
class CutoutDrawable extends MaterialShapeDrawable {
    CutoutDrawableState drawableState;

    static CutoutDrawable create(ShapeAppearanceModel shapeAppearanceModel) {
        return create(new CutoutDrawableState(shapeAppearanceModel != null ? shapeAppearanceModel : new ShapeAppearanceModel(), new RectF()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CutoutDrawable create(CutoutDrawableState drawableState) {
        return new ImplApi18(drawableState);
    }

    private CutoutDrawable(CutoutDrawableState drawableState) {
        super(drawableState);
        this.drawableState = drawableState;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public Drawable mutate() {
        this.drawableState = new CutoutDrawableState(this.drawableState);
        return this;
    }

    boolean hasCutout() {
        return !this.drawableState.cutoutBounds.isEmpty();
    }

    void setCutout(float left, float top, float right, float bottom) {
        if (left == this.drawableState.cutoutBounds.left && top == this.drawableState.cutoutBounds.top && right == this.drawableState.cutoutBounds.right && bottom == this.drawableState.cutoutBounds.bottom) {
            return;
        }
        this.drawableState.cutoutBounds.set(left, top, right, bottom);
        invalidateSelf();
    }

    void setCutout(RectF bounds) {
        setCutout(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    void removeCutout() {
        setCutout(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private static class ImplApi18 extends CutoutDrawable {
        ImplApi18(CutoutDrawableState drawableState) {
            super(drawableState);
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable
        protected void drawStrokeShape(Canvas canvas) {
            if (this.drawableState.cutoutBounds.isEmpty()) {
                super.drawStrokeShape(canvas);
                return;
            }
            canvas.save();
            if (Build.VERSION.SDK_INT >= 26) {
                canvas.clipOutRect(this.drawableState.cutoutBounds);
            } else {
                canvas.clipRect(this.drawableState.cutoutBounds, Region.Op.DIFFERENCE);
            }
            super.drawStrokeShape(canvas);
            canvas.restore();
        }
    }

    private static final class CutoutDrawableState extends MaterialShapeDrawable.MaterialShapeDrawableState {
        private final RectF cutoutBounds;

        private CutoutDrawableState(ShapeAppearanceModel shapeAppearanceModel, RectF cutoutBounds) {
            super(shapeAppearanceModel, null);
            this.cutoutBounds = cutoutBounds;
        }

        private CutoutDrawableState(CutoutDrawableState state) {
            super(state);
            this.cutoutBounds = state.cutoutBounds;
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable.MaterialShapeDrawableState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            CutoutDrawable drawable = CutoutDrawable.create(this);
            drawable.invalidateSelf();
            return drawable;
        }
    }
}
