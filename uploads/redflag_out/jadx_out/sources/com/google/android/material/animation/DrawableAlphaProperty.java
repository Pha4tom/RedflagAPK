package com.google.android.material.animation;

import android.graphics.drawable.Drawable;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
public class DrawableAlphaProperty extends Property<Drawable, Integer> {
    public static final Property<Drawable, Integer> DRAWABLE_ALPHA_COMPAT = new DrawableAlphaProperty();

    private DrawableAlphaProperty() {
        super(Integer.class, "drawableAlphaCompat");
    }

    @Override // android.util.Property
    public Integer get(Drawable object) {
        return Integer.valueOf(object.getAlpha());
    }

    @Override // android.util.Property
    public void set(Drawable object, Integer value) {
        object.setAlpha(value.intValue());
    }
}
