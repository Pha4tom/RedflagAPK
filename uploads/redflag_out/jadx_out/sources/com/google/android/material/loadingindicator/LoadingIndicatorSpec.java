package com.google.android.material.loadingindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;

/* JADX INFO: loaded from: classes.dex */
public final class LoadingIndicatorSpec {
    int containerColor;
    int containerHeight;
    int containerWidth;
    int[] indicatorColors;
    int indicatorSize;
    boolean scaleToFit;

    public LoadingIndicatorSpec(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.loadingIndicatorStyle);
    }

    public LoadingIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, LoadingIndicator.DEF_STYLE_RES);
    }

    public LoadingIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.scaleToFit = false;
        this.indicatorColors = new int[0];
        int defaultShapeSize = context.getResources().getDimensionPixelSize(R.dimen.m3_loading_indicator_shape_size);
        int defaultContainerSize = context.getResources().getDimensionPixelSize(R.dimen.m3_loading_indicator_container_size);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.LoadingIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.indicatorSize = a.getDimensionPixelSize(R.styleable.LoadingIndicator_indicatorSize, defaultShapeSize);
        this.containerWidth = a.getDimensionPixelSize(R.styleable.LoadingIndicator_containerWidth, defaultContainerSize);
        this.containerHeight = a.getDimensionPixelSize(R.styleable.LoadingIndicator_containerHeight, defaultContainerSize);
        loadIndicatorColors(context, a);
        this.containerColor = a.getColor(R.styleable.LoadingIndicator_containerColor, 0);
        a.recycle();
    }

    private void loadIndicatorColors(Context context, TypedArray typedArray) {
        if (!typedArray.hasValue(R.styleable.LoadingIndicator_indicatorColor)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, androidx.appcompat.R.attr.colorPrimary, -1)};
            return;
        }
        TypedValue indicatorColorValue = typedArray.peekValue(R.styleable.LoadingIndicator_indicatorColor);
        if (indicatorColorValue.type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(R.styleable.LoadingIndicator_indicatorColor, -1)};
            return;
        }
        this.indicatorColors = context.getResources().getIntArray(typedArray.getResourceId(R.styleable.LoadingIndicator_indicatorColor, -1));
        if (this.indicatorColors.length == 0) {
            throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
        }
    }

    public void setScaleToFit(boolean scaleToFit) {
        this.scaleToFit = scaleToFit;
    }
}
