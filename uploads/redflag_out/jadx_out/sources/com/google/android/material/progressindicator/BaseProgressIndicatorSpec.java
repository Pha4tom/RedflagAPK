package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseProgressIndicatorSpec {
    public int hideAnimationBehavior;
    public float indeterminateAnimatorDurationScale;
    public int[] indicatorColors = new int[0];
    public int indicatorTrackGapSize;
    public int showAnimationBehavior;
    public int trackColor;
    public int trackCornerRadius;
    public float trackCornerRadiusFraction;
    public int trackThickness;
    public boolean useRelativeTrackCornerRadius;
    public int waveAmplitude;
    public int waveSpeed;
    public int wavelengthDeterminate;
    public int wavelengthIndeterminate;

    protected BaseProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int defaultIndicatorSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_track_thickness);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.trackThickness = MaterialResources.getDimensionPixelSize(context, a, R.styleable.BaseProgressIndicator_trackThickness, defaultIndicatorSize);
        TypedValue trackCornerRadiusValue = a.peekValue(R.styleable.BaseProgressIndicator_trackCornerRadius);
        if (trackCornerRadiusValue != null) {
            if (trackCornerRadiusValue.type == 5) {
                this.trackCornerRadius = Math.min(TypedValue.complexToDimensionPixelSize(trackCornerRadiusValue.data, a.getResources().getDisplayMetrics()), this.trackThickness / 2);
                this.useRelativeTrackCornerRadius = false;
            } else if (trackCornerRadiusValue.type == 6) {
                this.trackCornerRadiusFraction = Math.min(trackCornerRadiusValue.getFraction(1.0f, 1.0f), 0.5f);
                this.useRelativeTrackCornerRadius = true;
            }
        }
        this.showAnimationBehavior = a.getInt(R.styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.hideAnimationBehavior = a.getInt(R.styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        this.indicatorTrackGapSize = a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_indicatorTrackGapSize, 0);
        int wavelength = Math.abs(a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_wavelength, 0));
        this.wavelengthDeterminate = Math.abs(a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_wavelengthDeterminate, wavelength));
        this.wavelengthIndeterminate = Math.abs(a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_wavelengthIndeterminate, wavelength));
        this.waveAmplitude = Math.abs(a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_waveAmplitude, 0));
        this.waveSpeed = a.getDimensionPixelSize(R.styleable.BaseProgressIndicator_waveSpeed, 0);
        this.indeterminateAnimatorDurationScale = a.getFloat(R.styleable.BaseProgressIndicator_indeterminateAnimatorDurationScale, 1.0f);
        loadIndicatorColors(context, a);
        loadTrackColor(context, a);
        a.recycle();
    }

    private void loadIndicatorColors(Context context, TypedArray typedArray) {
        if (!typedArray.hasValue(R.styleable.BaseProgressIndicator_indicatorColor)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, androidx.appcompat.R.attr.colorPrimary, -1)};
            return;
        }
        TypedValue indicatorColorValue = typedArray.peekValue(R.styleable.BaseProgressIndicator_indicatorColor);
        if (indicatorColorValue.type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(R.styleable.BaseProgressIndicator_indicatorColor, -1)};
            return;
        }
        this.indicatorColors = context.getResources().getIntArray(typedArray.getResourceId(R.styleable.BaseProgressIndicator_indicatorColor, -1));
        if (this.indicatorColors.length == 0) {
            throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
        }
    }

    private void loadTrackColor(Context context, TypedArray typedArray) {
        if (typedArray.hasValue(R.styleable.BaseProgressIndicator_trackColor)) {
            this.trackColor = typedArray.getColor(R.styleable.BaseProgressIndicator_trackColor, -1);
            return;
        }
        this.trackColor = this.indicatorColors[0];
        TypedArray disabledAlphaArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
        float defaultOpacity = disabledAlphaArray.getFloat(0, 0.2f);
        disabledAlphaArray.recycle();
        int trackAlpha = (int) (255.0f * defaultOpacity);
        this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, trackAlpha);
    }

    public boolean isShowAnimationEnabled() {
        return this.showAnimationBehavior != 0;
    }

    public boolean isHideAnimationEnabled() {
        return this.hideAnimationBehavior != 0;
    }

    public boolean hasWavyEffect(boolean isDeterminate) {
        return this.waveAmplitude > 0 && ((!isDeterminate && this.wavelengthIndeterminate > 0) || (isDeterminate && this.wavelengthDeterminate > 0));
    }

    public int getTrackCornerRadiusInPx() {
        if (this.useRelativeTrackCornerRadius) {
            return (int) (this.trackThickness * this.trackCornerRadiusFraction);
        }
        return this.trackCornerRadius;
    }

    public boolean useStrokeCap() {
        return this.useRelativeTrackCornerRadius && this.trackCornerRadiusFraction == 0.5f;
    }

    void validateSpec() {
        if (this.indicatorTrackGapSize < 0) {
            throw new IllegalArgumentException("indicatorTrackGapSize must be >= 0.");
        }
    }
}
