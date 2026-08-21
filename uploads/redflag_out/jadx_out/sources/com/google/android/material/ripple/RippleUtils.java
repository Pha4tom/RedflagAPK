package com.google.android.material.ripple;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.color.MaterialColors;

/* JADX INFO: loaded from: classes.dex */
public class RippleUtils {
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";

    @Deprecated
    public static final boolean USE_FRAMEWORK_RIPPLE = true;
    private static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    private static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    private static final int[] SELECTED_PRESSED_STATE_SET = {R.attr.state_selected, R.attr.state_pressed};
    private static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    private static final int[] ENABLED_PRESSED_STATE_SET = {R.attr.state_enabled, R.attr.state_pressed};
    static final String LOG_TAG = RippleUtils.class.getSimpleName();

    private RippleUtils() {
    }

    public static ColorStateList convertToRippleDrawableColor(ColorStateList rippleColor) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = SELECTED_STATE_SET;
        colors[0] = getColorForState(rippleColor, SELECTED_PRESSED_STATE_SET);
        int i = 0 + 1;
        states[i] = FOCUSED_STATE_SET;
        colors[i] = getColorForState(rippleColor, FOCUSED_STATE_SET);
        int i2 = i + 1;
        states[i2] = StateSet.NOTHING;
        colors[i2] = getColorForState(rippleColor, PRESSED_STATE_SET);
        int i3 = i2 + 1;
        return new ColorStateList(states, colors);
    }

    public static ColorStateList sanitizeRippleDrawableColor(ColorStateList rippleColor) {
        if (rippleColor != null) {
            if (Build.VERSION.SDK_INT <= 27 && Color.alpha(rippleColor.getDefaultColor()) == 0 && Color.alpha(rippleColor.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) != 0) {
                Log.w(LOG_TAG, TRANSPARENT_DEFAULT_COLOR_WARNING);
            }
            return rippleColor;
        }
        return ColorStateList.valueOf(0);
    }

    public static boolean shouldDrawRippleCompat(int[] stateSet) {
        boolean enabled = false;
        boolean interactedState = false;
        for (int state : stateSet) {
            if (state == 16842910) {
                enabled = true;
            } else if (state == 16842908) {
                interactedState = true;
            } else if (state == 16842919) {
                interactedState = true;
            } else if (state == 16843623) {
                interactedState = true;
            }
        }
        return enabled && interactedState;
    }

    public static Drawable createOvalRippleLollipop(Context context, int padding) {
        return RippleUtilsLollipop.createOvalRipple(context, padding);
    }

    private static int getColorForState(ColorStateList rippleColor, int[] state) {
        int color;
        if (rippleColor != null) {
            color = rippleColor.getColorForState(state, rippleColor.getDefaultColor());
        } else {
            color = 0;
        }
        return doubleAlpha(color);
    }

    private static int doubleAlpha(int color) {
        int alpha = Math.min(Color.alpha(color) * 2, 255);
        return ColorUtils.setAlphaComponent(color, alpha);
    }

    private static class RippleUtilsLollipop {
        private RippleUtilsLollipop() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Drawable createOvalRipple(Context context, int padding) {
            GradientDrawable maskDrawable = new GradientDrawable();
            maskDrawable.setColor(-1);
            maskDrawable.setShape(1);
            InsetDrawable maskWithPaddings = new InsetDrawable((Drawable) maskDrawable, padding, padding, padding, padding);
            return new RippleDrawable(MaterialColors.getColorStateList(context, androidx.appcompat.R.attr.colorControlHighlight, ColorStateList.valueOf(0)), null, maskWithPaddings);
        }
    }
}
