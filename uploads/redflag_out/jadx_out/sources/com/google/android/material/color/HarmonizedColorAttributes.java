package com.google.android.material.color;

import androidx.appcompat.R;

/* JADX INFO: loaded from: classes.dex */
public final class HarmonizedColorAttributes {
    private static final int[] HARMONIZED_MATERIAL_ATTRIBUTES = {R.attr.colorError, com.google.android.material.R.attr.colorOnError, com.google.android.material.R.attr.colorErrorContainer, com.google.android.material.R.attr.colorOnErrorContainer};
    private final int[] attributes;
    private final int themeOverlay;

    public static HarmonizedColorAttributes create(int[] attributes) {
        return new HarmonizedColorAttributes(attributes, 0);
    }

    public static HarmonizedColorAttributes create(int[] attributes, int themeOverlay) {
        return new HarmonizedColorAttributes(attributes, themeOverlay);
    }

    public static HarmonizedColorAttributes createMaterialDefaults() {
        return create(HARMONIZED_MATERIAL_ATTRIBUTES, com.google.android.material.R.style.ThemeOverlay_Material3_HarmonizedColors);
    }

    private HarmonizedColorAttributes(int[] attributes, int themeOverlay) {
        if (themeOverlay != 0 && attributes.length == 0) {
            throw new IllegalArgumentException("Theme overlay should be used with the accompanying int[] attributes.");
        }
        this.attributes = attributes;
        this.themeOverlay = themeOverlay;
    }

    public int[] getAttributes() {
        return this.attributes;
    }

    public int getThemeOverlay() {
        return this.themeOverlay;
    }
}
