package com.google.android.material.theme.overlay;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.view.ContextThemeWrapper;

/* JADX INFO: loaded from: classes.dex */
public class MaterialThemeOverlay {
    private static final int[] ANDROID_THEME_OVERLAY_ATTRS = {R.attr.theme, androidx.appcompat.R.attr.theme};
    private static final int[] MATERIAL_THEME_OVERLAY_ATTR = {com.google.android.material.R.attr.materialThemeOverlay};

    private MaterialThemeOverlay() {
    }

    public static Context wrap(Context context, AttributeSet set, int defStyleAttr, int defStyleRes) {
        return wrap(context, set, defStyleAttr, defStyleRes, new int[0]);
    }

    public static Context wrap(Context context, AttributeSet set, int defStyleAttr, int defStyleRes, int[] optionalAttrs) {
        int materialThemeOverlayId = obtainMaterialThemeOverlayId(context, set, defStyleAttr, defStyleRes);
        boolean contextHasOverlay = (context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == materialThemeOverlayId;
        if (materialThemeOverlayId == 0 || contextHasOverlay) {
            return context;
        }
        Context contextThemeWrapper = new ContextThemeWrapper(context, materialThemeOverlayId);
        int[] optionalOverlayIds = obtainMaterialOverlayIds(context, set, optionalAttrs, defStyleAttr, defStyleRes);
        for (int optionalOverlayId : optionalOverlayIds) {
            if (optionalOverlayId != 0) {
                contextThemeWrapper.getTheme().applyStyle(optionalOverlayId, true);
            }
        }
        int androidThemeOverlayId = obtainAndroidThemeOverlayId(context, set);
        if (androidThemeOverlayId != 0) {
            contextThemeWrapper.getTheme().applyStyle(androidThemeOverlayId, true);
        }
        return contextThemeWrapper;
    }

    private static int obtainAndroidThemeOverlayId(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, ANDROID_THEME_OVERLAY_ATTRS);
        int androidThemeId = a.getResourceId(0, 0);
        int appThemeId = a.getResourceId(1, 0);
        a.recycle();
        return androidThemeId != 0 ? androidThemeId : appThemeId;
    }

    private static int obtainMaterialThemeOverlayId(Context context, AttributeSet set, int defStyleAttr, int defStyleRes) {
        return obtainMaterialOverlayIds(context, set, MATERIAL_THEME_OVERLAY_ATTR, defStyleAttr, defStyleRes)[0];
    }

    private static int[] obtainMaterialOverlayIds(Context context, AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
        int[] overlayIds = new int[attrs.length];
        if (attrs.length > 0) {
            TypedArray a = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
            for (int i = 0; i < attrs.length; i++) {
                overlayIds[i] = a.getResourceId(i, 0);
            }
            a.recycle();
        }
        return overlayIds;
    }
}
