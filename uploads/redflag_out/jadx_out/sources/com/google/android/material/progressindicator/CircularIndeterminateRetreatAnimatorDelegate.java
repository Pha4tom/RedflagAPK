package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.progressindicator.DrawingDelegate;

/* JADX INFO: loaded from: classes.dex */
final class CircularIndeterminateRetreatAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    private static final int CONSTANT_ROTATION_DEGREES = 1080;
    private static final int DELAY_GROW_ACTIVE_IN_MS = 0;
    private static final int DELAY_SHRINK_ACTIVE_IN_MS = 3000;
    private static final int DURATION_GROW_ACTIVE_IN_MS = 3000;
    private static final int DURATION_SHRINK_ACTIVE_IN_MS = 3000;
    private static final int DURATION_SPIN_IN_MS = 500;
    private static final int DURATION_TO_COMPLETE_END_IN_MS = 500;
    private static final int DURATION_TO_FADE_IN_MS = 100;
    private static final int SPIN_ROTATION_DEGREES = 90;
    private static final float START_FRACTION = 0.0f;
    private static final int TOTAL_DURATION_IN_MS = 6000;
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat.AnimationCallback animatorCompleteCallback;
    private final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    private float completeEndFraction;
    private int indicatorColorIndexOffset;
    private final TimeInterpolator standardInterpolator;
    private static final TimeInterpolator DEFAULT_INTERPOLATOR = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    private static final int[] DELAY_SPINS_IN_MS = {0, 1500, PathInterpolatorCompat.MAX_NUM_POINTS, 4500};
    private static final float[] END_FRACTION_RANGE = {0.1f, 0.87f};
    private static final Property<CircularIndeterminateRetreatAnimatorDelegate, Float> ANIMATION_FRACTION = new Property<CircularIndeterminateRetreatAnimatorDelegate, Float>(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.CircularIndeterminateRetreatAnimatorDelegate.3
        @Override // android.util.Property
        public Float get(CircularIndeterminateRetreatAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getAnimationFraction());
        }

        @Override // android.util.Property
        public void set(CircularIndeterminateRetreatAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    };
    private static final Property<CircularIndeterminateRetreatAnimatorDelegate, Float> COMPLETE_END_FRACTION = new Property<CircularIndeterminateRetreatAnimatorDelegate, Float>(Float.class, "completeEndFraction") { // from class: com.google.android.material.progressindicator.CircularIndeterminateRetreatAnimatorDelegate.4
        @Override // android.util.Property
        public Float get(CircularIndeterminateRetreatAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getCompleteEndFraction());
        }

        @Override // android.util.Property
        public void set(CircularIndeterminateRetreatAnimatorDelegate delegate, Float value) {
            delegate.setCompleteEndFraction(value.floatValue());
        }
    };

    public CircularIndeterminateRetreatAnimatorDelegate(Context context, CircularProgressIndicatorSpec spec) {
        super(1);
        this.indicatorColorIndexOffset = 0;
        this.animatorCompleteCallback = null;
        this.baseSpec = spec;
        this.standardInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingStandardInterpolator, DEFAULT_INTERPOLATOR);
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            this.animator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, 0.0f, 1.0f);
            this.animator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 6000.0f));
            this.animator.setInterpolator(null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.CircularIndeterminateRetreatAnimatorDelegate.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    CircularIndeterminateRetreatAnimatorDelegate.this.indicatorColorIndexOffset = (CircularIndeterminateRetreatAnimatorDelegate.this.indicatorColorIndexOffset + CircularIndeterminateRetreatAnimatorDelegate.DELAY_SPINS_IN_MS.length) % CircularIndeterminateRetreatAnimatorDelegate.this.baseSpec.indicatorColors.length;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            this.completeEndAnimator = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, 0.0f, 1.0f);
            this.completeEndAnimator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 500.0f));
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.CircularIndeterminateRetreatAnimatorDelegate.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    CircularIndeterminateRetreatAnimatorDelegate.this.cancelAnimatorImmediately();
                    if (CircularIndeterminateRetreatAnimatorDelegate.this.animatorCompleteCallback != null) {
                        CircularIndeterminateRetreatAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(CircularIndeterminateRetreatAnimatorDelegate.this.drawable);
                    }
                }
            });
        }
    }

    private void updateAnimatorsDuration() {
        maybeInitializeAnimators();
        this.animator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 6000.0f));
        this.completeEndAnimator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 500.0f));
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    void cancelAnimatorImmediately() {
        if (this.animator != null) {
            this.animator.cancel();
        }
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    void requestCancelAnimatorAfterCurrentCycle() {
        if (this.completeEndAnimator == null || this.completeEndAnimator.isRunning()) {
            return;
        }
        if (this.drawable.isVisible()) {
            this.completeEndAnimator.start();
        } else {
            cancelAnimatorImmediately();
        }
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void invalidateSpecValues() {
        updateAnimatorsDuration();
        resetPropertiesForNewStart();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
        this.animatorCompleteCallback = callback;
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private void updateSegmentPositions(int playtimeInMs) {
        DrawingDelegate.ActiveIndicator indicator = this.activeIndicators.get(0);
        float constantRotation = this.animationFraction * 1080.0f;
        float spinRotation = 0.0f;
        for (int spinDelay : DELAY_SPINS_IN_MS) {
            spinRotation += this.standardInterpolator.getInterpolation(getFractionInRange(playtimeInMs, spinDelay, 500)) * 90.0f;
        }
        indicator.rotationDegree = constantRotation + spinRotation;
        float fraction = this.standardInterpolator.getInterpolation(getFractionInRange(playtimeInMs, 0, PathInterpolatorCompat.MAX_NUM_POINTS));
        float fraction2 = fraction - this.standardInterpolator.getInterpolation(getFractionInRange(playtimeInMs, PathInterpolatorCompat.MAX_NUM_POINTS, PathInterpolatorCompat.MAX_NUM_POINTS));
        indicator.startFraction = 0.0f;
        indicator.endFraction = MathUtils.lerp(END_FRACTION_RANGE[0], END_FRACTION_RANGE[1], fraction2);
        if (this.completeEndFraction > 0.0f) {
            indicator.endFraction *= 1.0f - this.completeEndFraction;
        }
    }

    private void maybeUpdateSegmentColors(int playtimeInMs) {
        for (int cycleIndex = 0; cycleIndex < DELAY_SPINS_IN_MS.length; cycleIndex++) {
            float timeFraction = getFractionInRange(playtimeInMs, DELAY_SPINS_IN_MS[cycleIndex], 100);
            if (timeFraction >= 0.0f && timeFraction <= 1.0f) {
                int startColorIndex = (this.indicatorColorIndexOffset + cycleIndex) % this.baseSpec.indicatorColors.length;
                int endColorIndex = (startColorIndex + 1) % this.baseSpec.indicatorColors.length;
                int startColor = this.baseSpec.indicatorColors[startColorIndex];
                int endColor = this.baseSpec.indicatorColors[endColorIndex];
                float colorFraction = this.standardInterpolator.getInterpolation(timeFraction);
                this.activeIndicators.get(0).color = ArgbEvaluatorCompat.getInstance().evaluate(colorFraction, Integer.valueOf(startColor), Integer.valueOf(endColor)).intValue();
                return;
            }
        }
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        this.activeIndicators.get(0).color = this.baseSpec.indicatorColors[0];
        this.completeEndFraction = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        int playtimeInMs = (int) (this.animationFraction * 6000.0f);
        updateSegmentPositions(playtimeInMs);
        maybeUpdateSegmentColors(playtimeInMs);
        this.drawable.invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getCompleteEndFraction() {
        return this.completeEndFraction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCompleteEndFraction(float fraction) {
        this.completeEndFraction = fraction;
    }
}
