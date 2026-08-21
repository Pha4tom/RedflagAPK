package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import androidx.activity.BackEventCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: classes.dex */
public class MaterialMainContainerBackHelper extends MaterialBackAnimationHelper<View> {
    private static final float MIN_SCALE = 0.9f;
    private float[] expandedCornerRadii;
    private Rect initialHideFromClipBounds;
    private Rect initialHideToClipBounds;
    private float initialTouchY;
    private final float maxTranslationY;
    private final float minEdgeGap;

    public MaterialMainContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.minEdgeGap = resources.getDimension(R.dimen.m3_back_progress_main_container_min_edge_gap);
        this.maxTranslationY = resources.getDimension(R.dimen.m3_back_progress_main_container_max_translation_y);
    }

    public Rect getInitialHideToClipBounds() {
        return this.initialHideToClipBounds;
    }

    public Rect getInitialHideFromClipBounds() {
        return this.initialHideFromClipBounds;
    }

    public void startBackProgress(BackEventCompat backEvent, View collapsedView) {
        super.onStartBackProgress(backEvent);
        startBackProgress(backEvent.getTouchY(), collapsedView);
    }

    public void startBackProgress(float touchY, View collapsedView) {
        this.initialHideToClipBounds = ViewUtils.calculateRectFromBounds(this.view);
        if (collapsedView != null) {
            this.initialHideFromClipBounds = ViewUtils.calculateOffsetRectFromBounds(this.view, collapsedView);
        }
        this.initialTouchY = touchY;
    }

    public void updateBackProgress(BackEventCompat backEvent, View collapsedView, float collapsedCornerSize) {
        if (super.onUpdateBackProgress(backEvent) == null) {
            return;
        }
        if (collapsedView != null && collapsedView.getVisibility() != 4) {
            collapsedView.setVisibility(4);
        }
        boolean leftSwipeEdge = backEvent.getSwipeEdge() == 0;
        updateBackProgress(backEvent.getProgress(), leftSwipeEdge, backEvent.getTouchY(), collapsedCornerSize);
    }

    public void updateBackProgress(float progress, boolean leftSwipeEdge, float touchY, float collapsedCornerSize) {
        float progress2 = interpolateProgress(progress);
        float width = this.view.getWidth();
        float height = this.view.getHeight();
        if (width > 0.0f && height > 0.0f) {
            float scale = AnimationUtils.lerp(1.0f, MIN_SCALE, progress2);
            float availableHorizontalSpace = Math.max(0.0f, ((width - (MIN_SCALE * width)) / 2.0f) - this.minEdgeGap);
            float translationX = AnimationUtils.lerp(0.0f, availableHorizontalSpace, progress2) * (leftSwipeEdge ? 1 : -1);
            float availableVerticalSpace = Math.max(0.0f, ((height - (scale * height)) / 2.0f) - this.minEdgeGap);
            float maxTranslationY = Math.min(availableVerticalSpace, this.maxTranslationY);
            float yDelta = touchY - this.initialTouchY;
            float yProgress = Math.abs(yDelta) / height;
            float translationYDirection = Math.signum(yDelta);
            float translationY = AnimationUtils.lerp(0.0f, maxTranslationY, yProgress) * translationYDirection;
            if (Float.isNaN(scale) || Float.isNaN(translationX)) {
                return;
            }
            if (Float.isNaN(translationY)) {
                return;
            }
            this.view.setScaleX(scale);
            this.view.setScaleY(scale);
            this.view.setTranslationX(translationX);
            this.view.setTranslationY(translationY);
            if (this.view instanceof ClippableRoundedCornerLayout) {
                ((ClippableRoundedCornerLayout) this.view).updateCornerRadii(lerpCornerRadii(getExpandedCornerRadii(), collapsedCornerSize, progress2));
            }
        }
    }

    public void finishBackProgress(long duration, View collapsedView) {
        AnimatorSet resetAnimator = createResetScaleAndTranslationAnimator(collapsedView);
        resetAnimator.setDuration(duration);
        resetAnimator.start();
        resetInitialValues();
    }

    public void cancelBackProgress(View collapsedView) {
        if (super.onCancelBackProgress() == null) {
            return;
        }
        AnimatorSet cancelAnimatorSet = createResetScaleAndTranslationAnimator(collapsedView);
        if (this.view instanceof ClippableRoundedCornerLayout) {
            cancelAnimatorSet.playTogether(createCornerAnimator((ClippableRoundedCornerLayout) this.view));
        }
        cancelAnimatorSet.setDuration(this.cancelDuration);
        cancelAnimatorSet.start();
        resetInitialValues();
    }

    private void resetInitialValues() {
        this.initialTouchY = 0.0f;
        this.initialHideToClipBounds = null;
        this.initialHideFromClipBounds = null;
    }

    private AnimatorSet createResetScaleAndTranslationAnimator(final View collapsedView) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.SCALE_Y, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.TRANSLATION_X, 0.0f), ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.TRANSLATION_Y, 0.0f));
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.motion.MaterialMainContainerBackHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (collapsedView != null) {
                    collapsedView.setVisibility(0);
                }
            }
        });
        return animatorSet;
    }

    private ValueAnimator createCornerAnimator(final ClippableRoundedCornerLayout clippableRoundedCornerLayout) {
        ValueAnimator cornerAnimator = ValueAnimator.ofObject(new TypeEvaluator() { // from class: com.google.android.material.motion.MaterialMainContainerBackHelper$$ExternalSyntheticLambda0
            @Override // android.animation.TypeEvaluator
            public final Object evaluate(float f, Object obj, Object obj2) {
                return MaterialMainContainerBackHelper.lerpCornerRadii((float[]) obj, (float[]) obj2, f);
            }
        }, clippableRoundedCornerLayout.getCornerRadii(), getExpandedCornerRadii());
        cornerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.motion.MaterialMainContainerBackHelper$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                clippableRoundedCornerLayout.updateCornerRadii((float[]) valueAnimator.getAnimatedValue());
            }
        });
        return cornerAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] lerpCornerRadii(float[] startValue, float[] endValue, float fraction) {
        return new float[]{AnimationUtils.lerp(startValue[0], endValue[0], fraction), AnimationUtils.lerp(startValue[1], endValue[1], fraction), AnimationUtils.lerp(startValue[2], endValue[2], fraction), AnimationUtils.lerp(startValue[3], endValue[3], fraction), AnimationUtils.lerp(startValue[4], endValue[4], fraction), AnimationUtils.lerp(startValue[5], endValue[5], fraction), AnimationUtils.lerp(startValue[6], endValue[6], fraction), AnimationUtils.lerp(startValue[7], endValue[7], fraction)};
    }

    private static float[] lerpCornerRadii(float[] startValue, float endValue, float fraction) {
        return new float[]{AnimationUtils.lerp(startValue[0], endValue, fraction), AnimationUtils.lerp(startValue[1], endValue, fraction), AnimationUtils.lerp(startValue[2], endValue, fraction), AnimationUtils.lerp(startValue[3], endValue, fraction), AnimationUtils.lerp(startValue[4], endValue, fraction), AnimationUtils.lerp(startValue[5], endValue, fraction), AnimationUtils.lerp(startValue[6], endValue, fraction), AnimationUtils.lerp(startValue[7], endValue, fraction)};
    }

    public float[] getExpandedCornerRadii() {
        if (this.expandedCornerRadii == null) {
            this.expandedCornerRadii = calculateExpandedCornerRadii();
        }
        return this.expandedCornerRadii;
    }

    public void clearExpandedCornerRadii() {
        this.expandedCornerRadii = null;
    }

    private float[] calculateExpandedCornerRadii() {
        WindowInsets insets;
        int topLeft;
        int topRight;
        int bottomRight;
        char c;
        int bottomLeft;
        if (Build.VERSION.SDK_INT >= 31 && (insets = this.view.getRootWindowInsets()) != null) {
            DisplayMetrics displayMetrics = this.view.getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int[] location = new int[2];
            this.view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            int width = this.view.getWidth();
            int height = this.view.getHeight();
            if (x == 0 && y == 0) {
                topLeft = getRoundedCornerRadius(insets, 0);
            } else {
                topLeft = 0;
            }
            if (x + width >= screenWidth && y == 0) {
                topRight = getRoundedCornerRadius(insets, 1);
            } else {
                topRight = 0;
            }
            if (x + width >= screenWidth && y + height >= screenHeight) {
                bottomRight = getRoundedCornerRadius(insets, 2);
            } else {
                bottomRight = 0;
            }
            if (x == 0) {
                c = 1;
                if (y + height >= screenHeight) {
                    bottomLeft = getRoundedCornerRadius(insets, 3);
                }
                float[] fArr = new float[8];
                fArr[0] = topLeft;
                fArr[c] = topLeft;
                fArr[2] = topRight;
                fArr[3] = topRight;
                fArr[4] = bottomRight;
                fArr[5] = bottomRight;
                fArr[6] = bottomLeft;
                fArr[7] = bottomLeft;
                return fArr;
            }
            c = 1;
            bottomLeft = 0;
            float[] fArr2 = new float[8];
            fArr2[0] = topLeft;
            fArr2[c] = topLeft;
            fArr2[2] = topRight;
            fArr2[3] = topRight;
            fArr2[4] = bottomRight;
            fArr2[5] = bottomRight;
            fArr2[6] = bottomLeft;
            fArr2[7] = bottomLeft;
            return fArr2;
        }
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    private int getRoundedCornerRadius(WindowInsets insets, int position) {
        RoundedCorner roundedCorner = insets.getRoundedCorner(position);
        if (roundedCorner != null) {
            return roundedCorner.getRadius();
        }
        return 0;
    }
}
