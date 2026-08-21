package com.google.android.material.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;

/* JADX INFO: loaded from: classes.dex */
public class ScaledDrawableWrapper extends DrawableWrapperCompat {
    private boolean mutated;
    private ScaledDrawableWrapperState state;

    public ScaledDrawableWrapper(Drawable drawable, int width, int height) {
        super(drawable);
        this.state = new ScaledDrawableWrapperState(getConstantStateFrom(drawable), width, height);
    }

    private Drawable.ConstantState getConstantStateFrom(Drawable drawable) {
        if (drawable != null) {
            return drawable.getConstantState();
        }
        return null;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.state.width;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.state.height;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat
    public void setDrawable(Drawable drawable) {
        super.setDrawable(drawable);
        if (this.state != null) {
            this.state.wrappedDrawableState = getConstantStateFrom(drawable);
            this.mutated = false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.state.canConstantState()) {
            return this.state;
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mutated && super.mutate() == this) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                drawable.mutate();
            }
            this.state = new ScaledDrawableWrapperState(getConstantStateFrom(drawable), this.state.width, this.state.height);
            this.mutated = true;
        }
        return this;
    }

    private static final class ScaledDrawableWrapperState extends Drawable.ConstantState {
        private final int height;
        private final int width;
        private Drawable.ConstantState wrappedDrawableState;

        ScaledDrawableWrapperState(Drawable.ConstantState wrappedDrawableState, int width, int height) {
            this.wrappedDrawableState = wrappedDrawableState;
            this.width = width;
            this.height = height;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            Drawable newWrappedDrawable = this.wrappedDrawableState.newDrawable();
            return new ScaledDrawableWrapper(newWrappedDrawable, this.width, this.height);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources res) {
            Drawable newWrappedDrawable = this.wrappedDrawableState.newDrawable(res);
            return new ScaledDrawableWrapper(newWrappedDrawable, this.width, this.height);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources res, Resources.Theme theme) {
            Drawable newWrappedDrawable = this.wrappedDrawableState.newDrawable(res, theme);
            return new ScaledDrawableWrapper(newWrappedDrawable, this.width, this.height);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            if (this.wrappedDrawableState != null) {
                return this.wrappedDrawableState.getChangingConfigurations();
            }
            return 0;
        }

        boolean canConstantState() {
            return this.wrappedDrawableState != null;
        }
    }
}
