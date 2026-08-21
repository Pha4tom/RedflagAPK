package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.StaticLayoutBuilderCompat;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TypefaceUtils;

/* JADX INFO: loaded from: classes.dex */
public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT = null;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final float FADE_MODE_THRESHOLD_FRACTION_RELATIVE = 0.5f;
    private static final int ONE_LINE = 1;
    public static final int SEMITRANSPARENT_MAGENTA = 1090453759;
    private static final String TAG = "CollapsingTextHelper";
    private boolean alignBaselineAtBottom;
    private boolean boundsChanged;
    private Rect collapsedBoundsForPlacement;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private float collapsedLetterSpacing;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private float collapsedTextWidth;
    private Typeface collapsedTypeface;
    private Typeface collapsedTypefaceBold;
    private Typeface collapsedTypefaceDefault;
    private float currentDrawX;
    private float currentDrawY;
    private float currentLetterSpacing;
    private int currentMaxLines;
    private int currentOffsetY;
    private int currentShadowColor;
    private float currentShadowDx;
    private float currentShadowDy;
    private float currentShadowRadius;
    private float currentTextSize;
    private Typeface currentTypeface;
    private float expandedDrawX;
    private float expandedDrawY;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private float expandedLetterSpacing;
    private int expandedLineCount;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;
    private Typeface expandedTypeface;
    private Typeface expandedTypefaceBold;
    private Typeface expandedTypefaceDefault;
    private boolean fadeModeEnabled;
    private float fadeModeStartFraction;
    private boolean isRtl;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    private StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;
    private CharSequence text;
    private StaticLayout textLayout;
    private TimeInterpolator textSizeInterpolator;
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private final View view;
    private int expandedTextGravity = 16;
    private int collapsedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private float collapsedTextSize = 15.0f;
    private TextUtils.TruncateAt titleTextEllipsize = TextUtils.TruncateAt.END;
    private boolean isRtlTextDirectionHeuristicsEnabled = true;
    private int expandedMaxLines = 1;
    private int collapsedMaxLines = 1;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;
    private int collapsedHeight = -1;
    private int expandedHeight = -1;
    private final TextPaint textPaint = new TextPaint(129);
    private final TextPaint tmpPaint = new TextPaint(this.textPaint);
    private final Rect collapsedBounds = new Rect();
    private final Rect expandedBounds = new Rect();
    private final RectF currentBounds = new RectF();
    private float fadeModeThresholdFraction = calculateFadeModeThresholdFraction();

    static {
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(SEMITRANSPARENT_MAGENTA);
        }
    }

    public CollapsingTextHelper(View view) {
        this.view = view;
        maybeUpdateFontWeightAdjustment(view.getContext().getResources().getConfiguration());
    }

    public void setCollapsedMaxLines(int collapsedMaxLines) {
        if (collapsedMaxLines != this.collapsedMaxLines) {
            this.collapsedMaxLines = collapsedMaxLines;
            recalculate();
        }
    }

    public void setTextSizeInterpolator(TimeInterpolator interpolator) {
        this.textSizeInterpolator = interpolator;
        recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator interpolator) {
        this.positionInterpolator = interpolator;
        recalculate();
    }

    public TimeInterpolator getPositionInterpolator() {
        return this.positionInterpolator;
    }

    public void setExpandedTextSize(float textSize) {
        if (this.expandedTextSize != textSize) {
            this.expandedTextSize = textSize;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float textSize) {
        if (this.collapsedTextSize != textSize) {
            this.collapsedTextSize = textSize;
            recalculate();
        }
    }

    public void setCollapsedTextColor(ColorStateList textColor) {
        if (this.collapsedTextColor != textColor) {
            this.collapsedTextColor = textColor;
            recalculate();
        }
    }

    public void setExpandedTextColor(ColorStateList textColor) {
        if (this.expandedTextColor != textColor) {
            this.expandedTextColor = textColor;
            recalculate();
        }
    }

    public void setCollapsedAndExpandedTextColor(ColorStateList textColor) {
        if (this.collapsedTextColor != textColor || this.expandedTextColor != textColor) {
            this.collapsedTextColor = textColor;
            this.expandedTextColor = textColor;
            recalculate();
        }
    }

    public void setExpandedLetterSpacing(float letterSpacing) {
        if (this.expandedLetterSpacing != letterSpacing) {
            this.expandedLetterSpacing = letterSpacing;
            recalculate();
        }
    }

    public void setExpandedBounds(int left, int top, int right, int bottom, boolean alignBaselineAtBottom) {
        if (!rectEquals(this.expandedBounds, left, top, right, bottom) || alignBaselineAtBottom != this.alignBaselineAtBottom) {
            this.expandedBounds.set(left, top, right, bottom);
            this.boundsChanged = true;
            this.alignBaselineAtBottom = alignBaselineAtBottom;
        }
    }

    public void setExpandedBounds(int left, int top, int right, int bottom) {
        setExpandedBounds(left, top, right, bottom, true);
    }

    public void setExpandedBounds(Rect bounds) {
        setExpandedBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void setCollapsedBounds(int left, int top, int right, int bottom) {
        if (!rectEquals(this.collapsedBounds, left, top, right, bottom)) {
            this.collapsedBounds.set(left, top, right, bottom);
            this.boundsChanged = true;
        }
    }

    public void setCollapsedBounds(Rect bounds) {
        setCollapsedBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void setCollapsedBoundsForOffsets(int left, int top, int right, int bottom) {
        if (this.collapsedBoundsForPlacement == null) {
            this.collapsedBoundsForPlacement = new Rect(left, top, right, bottom);
            this.boundsChanged = true;
        }
        if (!rectEquals(this.collapsedBoundsForPlacement, left, top, right, bottom)) {
            this.collapsedBoundsForPlacement.set(left, top, right, bottom);
            this.boundsChanged = true;
        }
    }

    public void getCollapsedTextBottomTextBounds(RectF bounds, int labelWidth, int textGravity) {
        this.isRtl = calculateIsRtl(this.text);
        bounds.left = Math.max(getCollapsedTextLeftBound(labelWidth, textGravity), this.collapsedBounds.left);
        bounds.top = this.collapsedBounds.top;
        bounds.right = Math.min(getCollapsedTextRightBound(bounds, labelWidth, textGravity), this.collapsedBounds.right);
        bounds.bottom = this.collapsedBounds.top + getCollapsedTextHeight();
        if (this.textLayout != null && !shouldTruncateCollapsedToSingleLine()) {
            float lineWidth = this.textLayout.getLineWidth(this.textLayout.getLineCount() - 1) * (this.collapsedTextSize / this.expandedTextSize);
            if (this.isRtl) {
                bounds.left = bounds.right - lineWidth;
            } else {
                bounds.right = bounds.left + lineWidth;
            }
        }
    }

    private float getCollapsedTextLeftBound(int width, int gravity) {
        if (gravity == 17 || (gravity & 7) == 1) {
            return (width / 2.0f) - (this.collapsedTextWidth / 2.0f);
        }
        return ((gravity & GravityCompat.END) == 8388613 || (gravity & 5) == 5) ? this.isRtl ? this.collapsedBounds.left : this.collapsedBounds.right - this.collapsedTextWidth : this.isRtl ? this.collapsedBounds.right - this.collapsedTextWidth : this.collapsedBounds.left;
    }

    private float getCollapsedTextRightBound(RectF bounds, int width, int gravity) {
        if (gravity == 17 || (gravity & 7) == 1) {
            return (width / 2.0f) + (this.collapsedTextWidth / 2.0f);
        }
        return ((gravity & GravityCompat.END) == 8388613 || (gravity & 5) == 5) ? this.isRtl ? bounds.left + this.collapsedTextWidth : this.collapsedBounds.right : this.isRtl ? this.collapsedBounds.right : bounds.left + this.collapsedTextWidth;
    }

    public float getExpandedTextSingleLineHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextFullSingleLineHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    public void updateTextHeights(int availableWidth) {
        getTextPaintCollapsed(this.tmpPaint);
        StaticLayout textLayout = createStaticLayout(this.collapsedMaxLines, this.tmpPaint, this.text, availableWidth * (this.collapsedTextSize / this.expandedTextSize), this.isRtl);
        this.collapsedHeight = textLayout.getHeight();
        getTextPaintExpanded(this.tmpPaint);
        StaticLayout textLayout2 = createStaticLayout(this.expandedMaxLines, this.tmpPaint, this.text, availableWidth, this.isRtl);
        this.expandedHeight = textLayout2.getHeight();
    }

    public float getCollapsedTextHeight() {
        return this.collapsedHeight != -1 ? this.collapsedHeight : getCollapsedSingleLineHeight();
    }

    public float getExpandedTextHeight() {
        return this.expandedHeight != -1 ? this.expandedHeight : getExpandedTextSingleLineHeight();
    }

    public float getCollapsedSingleLineHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getCollapsedFullSingleLineHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    public void setCurrentOffsetY(int currentOffsetY) {
        this.currentOffsetY = currentOffsetY;
    }

    public void setFadeModeStartFraction(float fadeModeStartFraction) {
        this.fadeModeStartFraction = fadeModeStartFraction;
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    }

    private float calculateFadeModeThresholdFraction() {
        return this.fadeModeStartFraction + ((1.0f - this.fadeModeStartFraction) * 0.5f);
    }

    public void setFadeModeEnabled(boolean fadeModeEnabled) {
        this.fadeModeEnabled = fadeModeEnabled;
    }

    private void getTextPaintExpanded(TextPaint textPaint) {
        textPaint.setTextSize(this.expandedTextSize);
        textPaint.setTypeface(this.expandedTypeface);
        textPaint.setLetterSpacing(this.expandedLetterSpacing);
    }

    private void getTextPaintCollapsed(TextPaint textPaint) {
        textPaint.setTextSize(this.collapsedTextSize);
        textPaint.setTypeface(this.collapsedTypeface);
        textPaint.setLetterSpacing(this.collapsedLetterSpacing);
    }

    public void setExpandedTextGravity(int gravity) {
        if (this.expandedTextGravity != gravity) {
            this.expandedTextGravity = gravity;
            recalculate();
        }
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public void setCollapsedTextGravity(int gravity) {
        if (this.collapsedTextGravity != gravity) {
            this.collapsedTextGravity = gravity;
            recalculate();
        }
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public void setCollapsedTextAppearance(int resId) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), resId);
        if (textAppearance.getTextColor() != null) {
            this.collapsedTextColor = textAppearance.getTextColor();
        }
        if (textAppearance.getTextSize() != 0.0f) {
            this.collapsedTextSize = textAppearance.getTextSize();
        }
        if (textAppearance.shadowColor != null) {
            this.collapsedShadowColor = textAppearance.shadowColor;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        this.collapsedLetterSpacing = textAppearance.letterSpacing;
        if (this.collapsedFontCallback != null) {
            this.collapsedFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.1
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface font) {
                CollapsingTextHelper.this.setCollapsedTypeface(font);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        recalculate();
    }

    public void setExpandedTextAppearance(int resId) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), resId);
        if (textAppearance.getTextColor() != null) {
            this.expandedTextColor = textAppearance.getTextColor();
        }
        if (textAppearance.getTextSize() != 0.0f) {
            this.expandedTextSize = textAppearance.getTextSize();
        }
        if (textAppearance.shadowColor != null) {
            this.expandedShadowColor = textAppearance.shadowColor;
        }
        this.expandedShadowDx = textAppearance.shadowDx;
        this.expandedShadowDy = textAppearance.shadowDy;
        this.expandedShadowRadius = textAppearance.shadowRadius;
        this.expandedLetterSpacing = textAppearance.letterSpacing;
        if (this.expandedFontCallback != null) {
            this.expandedFontCallback.cancel();
        }
        this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.2
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface font) {
                CollapsingTextHelper.this.setExpandedTypeface(font);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
        recalculate();
    }

    public void setTitleTextEllipsize(TextUtils.TruncateAt ellipsize) {
        this.titleTextEllipsize = ellipsize;
        recalculate();
    }

    public TextUtils.TruncateAt getTitleTextEllipsize() {
        return this.titleTextEllipsize;
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (setCollapsedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (setExpandedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setTypefaces(Typeface typeface) {
        boolean collapsedFontChanged = setCollapsedTypefaceInternal(typeface);
        boolean expandedFontChanged = setExpandedTypefaceInternal(typeface);
        if (collapsedFontChanged || expandedFontChanged) {
            recalculate();
        }
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        if (this.collapsedFontCallback != null) {
            this.collapsedFontCallback.cancel();
        }
        if (this.collapsedTypefaceDefault != typeface) {
            this.collapsedTypefaceDefault = typeface;
            this.collapsedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
            this.collapsedTypeface = this.collapsedTypefaceBold == null ? this.collapsedTypefaceDefault : this.collapsedTypefaceBold;
            return true;
        }
        return false;
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        if (this.expandedFontCallback != null) {
            this.expandedFontCallback.cancel();
        }
        if (this.expandedTypefaceDefault != typeface) {
            this.expandedTypefaceDefault = typeface;
            this.expandedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
            this.expandedTypeface = this.expandedTypefaceBold == null ? this.expandedTypefaceDefault : this.expandedTypefaceBold;
            return true;
        }
        return false;
    }

    public Typeface getCollapsedTypeface() {
        return this.collapsedTypeface != null ? this.collapsedTypeface : Typeface.DEFAULT;
    }

    public Typeface getExpandedTypeface() {
        return this.expandedTypeface != null ? this.expandedTypeface : Typeface.DEFAULT;
    }

    public void maybeUpdateFontWeightAdjustment(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 31) {
            if (this.collapsedTypefaceDefault != null) {
                this.collapsedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, this.collapsedTypefaceDefault);
            }
            if (this.expandedTypefaceDefault != null) {
                this.expandedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, this.expandedTypefaceDefault);
            }
            this.collapsedTypeface = this.collapsedTypefaceBold != null ? this.collapsedTypefaceBold : this.collapsedTypefaceDefault;
            this.expandedTypeface = this.expandedTypefaceBold != null ? this.expandedTypefaceBold : this.expandedTypefaceDefault;
            recalculate(true);
        }
    }

    public void setExpansionFraction(float fraction) {
        float fraction2 = MathUtils.clamp(fraction, 0.0f, 1.0f);
        if (fraction2 != this.expandedFraction) {
            this.expandedFraction = fraction2;
            calculateCurrentOffsets();
        }
    }

    public final boolean setState(int[] state) {
        this.state = state;
        if (isStateful()) {
            recalculate();
            return true;
        }
        return false;
    }

    public final boolean isStateful() {
        return (this.collapsedTextColor != null && this.collapsedTextColor.isStateful()) || (this.expandedTextColor != null && this.expandedTextColor.isStateful());
    }

    public float getFadeModeThresholdFraction() {
        return this.fadeModeThresholdFraction;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean rtlTextDirectionHeuristicsEnabled) {
        this.isRtlTextDirectionHeuristicsEnabled = rtlTextDirectionHeuristicsEnabled;
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.isRtlTextDirectionHeuristicsEnabled;
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private void calculateOffsets(float fraction) {
        float textBlendFraction;
        interpolateBounds(fraction);
        if (!this.fadeModeEnabled) {
            textBlendFraction = fraction;
            this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, fraction, this.positionInterpolator);
            this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, fraction, this.positionInterpolator);
            setInterpolatedTextSize(fraction);
        } else if (fraction < this.fadeModeThresholdFraction) {
            textBlendFraction = 0.0f;
            this.currentDrawX = this.expandedDrawX;
            this.currentDrawY = this.expandedDrawY;
            setInterpolatedTextSize(0.0f);
        } else {
            textBlendFraction = 1.0f;
            this.currentDrawX = this.collapsedDrawX;
            this.currentDrawY = this.collapsedDrawY - Math.max(0, this.currentOffsetY);
            setInterpolatedTextSize(1.0f);
        }
        setCollapsedTextBlend(1.0f - lerp(0.0f, 1.0f, 1.0f - fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        setExpandedTextBlend(lerp(1.0f, 0.0f, fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendARGB(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), textBlendFraction));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        if (this.collapsedLetterSpacing != this.expandedLetterSpacing) {
            this.textPaint.setLetterSpacing(lerp(this.expandedLetterSpacing, this.collapsedLetterSpacing, fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        } else {
            this.textPaint.setLetterSpacing(this.collapsedLetterSpacing);
        }
        this.currentShadowRadius = lerp(this.expandedShadowRadius, this.collapsedShadowRadius, fraction, null);
        this.currentShadowDx = lerp(this.expandedShadowDx, this.collapsedShadowDx, fraction, null);
        this.currentShadowDy = lerp(this.expandedShadowDy, this.collapsedShadowDy, fraction, null);
        this.currentShadowColor = blendARGB(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), fraction);
        this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, this.currentShadowColor);
        if (this.fadeModeEnabled) {
            int originalAlpha = this.textPaint.getAlpha();
            int textAlpha = (int) (calculateFadeModeTextAlpha(fraction) * originalAlpha);
            this.textPaint.setAlpha(textAlpha);
            if (Build.VERSION.SDK_INT >= 31) {
                this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, this.textPaint.getAlpha()));
            }
        }
        this.view.postInvalidateOnAnimation();
    }

    private float calculateFadeModeTextAlpha(float fraction) {
        if (fraction <= this.fadeModeThresholdFraction) {
            return AnimationUtils.lerp(1.0f, 0.0f, this.fadeModeStartFraction, this.fadeModeThresholdFraction, fraction);
        }
        return AnimationUtils.lerp(0.0f, 1.0f, this.fadeModeThresholdFraction, 1.0f, fraction);
    }

    private int getCurrentExpandedTextColor() {
        return getCurrentColor(this.expandedTextColor);
    }

    public int getCurrentCollapsedTextColor() {
        return getCurrentColor(this.collapsedTextColor);
    }

    private int getCurrentColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        if (this.state != null) {
            return colorStateList.getColorForState(this.state, 0);
        }
        return colorStateList.getDefaultColor();
    }

    private boolean shouldTruncateCollapsedToSingleLine() {
        return this.collapsedMaxLines == 1;
    }

    private void calculateBaseOffsets(boolean z) {
        CharSequence charSequenceEllipsize;
        calculateUsingTextSize(1.0f, z);
        if (this.textToDraw != null && this.textLayout != null) {
            if (shouldTruncateCollapsedToSingleLine()) {
                charSequenceEllipsize = TextUtils.ellipsize(this.textToDraw, this.textPaint, this.textLayout.getWidth(), this.titleTextEllipsize);
            } else {
                charSequenceEllipsize = this.textToDraw;
            }
            this.textToDrawCollapsed = charSequenceEllipsize;
        }
        if (this.textToDrawCollapsed != null) {
            this.collapsedTextWidth = measureTextWidth(this.textPaint, this.textToDrawCollapsed);
        } else {
            this.collapsedTextWidth = 0.0f;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        Rect rect = this.collapsedBoundsForPlacement != null ? this.collapsedBoundsForPlacement : this.collapsedBounds;
        switch (absoluteGravity & 112) {
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                this.collapsedDrawY = rect.top;
                break;
            case 80:
                this.collapsedDrawY = rect.bottom + this.textPaint.ascent();
                break;
            default:
                this.collapsedDrawY = rect.centerY() - ((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f);
                break;
        }
        switch (absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                this.collapsedDrawX = rect.centerX() - (this.collapsedTextWidth / 2.0f);
                break;
            case 5:
                this.collapsedDrawX = rect.right - this.collapsedTextWidth;
                break;
            default:
                this.collapsedDrawX = rect.left;
                break;
        }
        if (this.collapsedTextWidth <= this.collapsedBounds.width()) {
            this.collapsedDrawX += Math.max(0.0f, this.collapsedBounds.left - this.collapsedDrawX);
            this.collapsedDrawX += Math.min(0.0f, this.collapsedBounds.right - (this.collapsedDrawX + this.collapsedTextWidth));
        }
        if (getCollapsedFullSingleLineHeight() <= this.collapsedBounds.height()) {
            this.collapsedDrawY += Math.max(0.0f, this.collapsedBounds.top - this.collapsedDrawY);
            this.collapsedDrawY += Math.min(0.0f, this.collapsedBounds.bottom - (this.collapsedDrawY + getCollapsedTextHeight()));
        }
        calculateUsingTextSize(0.0f, z);
        float height = this.textLayout != null ? this.textLayout.getHeight() : 0.0f;
        float fMeasureTextWidth = 0.0f;
        if (this.textLayout != null && this.expandedMaxLines > 1) {
            fMeasureTextWidth = this.textLayout.getWidth();
        } else if (this.textToDraw != null) {
            fMeasureTextWidth = measureTextWidth(this.textPaint, this.textToDraw);
        }
        this.expandedLineCount = this.textLayout != null ? this.textLayout.getLineCount() : 0;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        switch (absoluteGravity2 & 112) {
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                this.expandedDrawY = this.expandedBounds.top;
                break;
            case 80:
                this.expandedDrawY = (this.expandedBounds.bottom - height) + (this.alignBaselineAtBottom ? this.textPaint.descent() : 0.0f);
                break;
            default:
                this.expandedDrawY = this.expandedBounds.centerY() - (height / 2.0f);
                break;
        }
        switch (absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                this.expandedDrawX = this.expandedBounds.centerX() - (fMeasureTextWidth / 2.0f);
                break;
            case 5:
                this.expandedDrawX = this.expandedBounds.right - fMeasureTextWidth;
                break;
            default:
                this.expandedDrawX = this.expandedBounds.left;
                break;
        }
        setInterpolatedTextSize(this.expandedFraction);
    }

    private float measureTextWidth(TextPaint textPaint, CharSequence textToDraw) {
        return textPaint.measureText(textToDraw, 0, textToDraw.length());
    }

    private void interpolateBounds(float fraction) {
        if (this.fadeModeEnabled) {
            this.currentBounds.set(fraction < this.fadeModeThresholdFraction ? this.expandedBounds : this.collapsedBounds);
            return;
        }
        this.currentBounds.left = lerp(this.expandedBounds.left, this.collapsedBounds.left, fraction, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, fraction, this.positionInterpolator);
        this.currentBounds.right = lerp(this.expandedBounds.right, this.collapsedBounds.right, fraction, this.positionInterpolator);
        this.currentBounds.bottom = lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, fraction, this.positionInterpolator);
    }

    private void setCollapsedTextBlend(float blend) {
        this.collapsedTextBlend = blend;
        this.view.postInvalidateOnAnimation();
    }

    private void setExpandedTextBlend(float blend) {
        this.expandedTextBlend = blend;
        this.view.postInvalidateOnAnimation();
    }

    public void draw(Canvas canvas) {
        int saveCount = canvas.save();
        if (this.textToDraw != null && this.currentBounds.width() > 0.0f && this.currentBounds.height() > 0.0f) {
            this.textPaint.setTextSize(this.currentTextSize);
            float x = this.currentDrawX;
            float y = this.currentDrawY;
            if (this.scale != 1.0f && !this.fadeModeEnabled) {
                canvas.scale(this.scale, this.scale, x, y);
            }
            if (shouldDrawMultiline() && shouldTruncateCollapsedToSingleLine() && (!this.fadeModeEnabled || this.expandedFraction > this.fadeModeThresholdFraction)) {
                drawMultilineTransition(canvas, this.currentDrawX - this.textLayout.getLineStart(0), y);
            } else {
                canvas.translate(x, y);
                this.textLayout.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        }
    }

    private boolean shouldDrawMultiline() {
        return (this.expandedMaxLines > 1 || this.collapsedMaxLines > 1) && (!this.isRtl || this.fadeModeEnabled);
    }

    private void drawMultilineTransition(Canvas canvas, float currentExpandedX, float y) {
        String tmp;
        int originalAlpha = this.textPaint.getAlpha();
        canvas.translate(currentExpandedX, y);
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.expandedTextBlend * originalAlpha));
            if (Build.VERSION.SDK_INT >= 31) {
                this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, this.textPaint.getAlpha()));
            }
            this.textLayout.draw(canvas);
        }
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.collapsedTextBlend * originalAlpha));
        }
        if (Build.VERSION.SDK_INT >= 31) {
            this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, this.textPaint.getAlpha()));
        }
        int lineBaseline = this.textLayout.getLineBaseline(0);
        canvas.drawText(this.textToDrawCollapsed, 0, this.textToDrawCollapsed.length(), 0.0f, lineBaseline, this.textPaint);
        if (Build.VERSION.SDK_INT >= 31) {
            this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, this.currentShadowColor);
        }
        if (!this.fadeModeEnabled) {
            String tmp2 = this.textToDrawCollapsed.toString().trim();
            if (!tmp2.endsWith(ELLIPSIS_NORMAL)) {
                tmp = tmp2;
            } else {
                tmp = tmp2.substring(0, tmp2.length() - 1);
            }
            this.textPaint.setAlpha(originalAlpha);
            canvas.drawText(tmp, 0, Math.min(this.textLayout.getLineEnd(0), tmp.length()), 0.0f, lineBaseline, (Paint) this.textPaint);
        }
    }

    private boolean calculateIsRtl(CharSequence text) {
        boolean defaultIsRtl = isDefaultIsRtl();
        if (this.isRtlTextDirectionHeuristicsEnabled) {
            return isTextDirectionHeuristicsIsRtl(text, defaultIsRtl);
        }
        return defaultIsRtl;
    }

    private boolean isDefaultIsRtl() {
        return this.view.getLayoutDirection() == 1;
    }

    private boolean isTextDirectionHeuristicsIsRtl(CharSequence text, boolean defaultIsRtl) {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat;
        if (defaultIsRtl) {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
        } else {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        }
        return textDirectionHeuristicCompat.isRtl(text, 0, text.length());
    }

    private void setInterpolatedTextSize(float fraction) {
        calculateUsingTextSize(fraction);
        this.view.postInvalidateOnAnimation();
    }

    private void calculateUsingTextSize(float fraction) {
        calculateUsingTextSize(fraction, false);
    }

    private void calculateUsingTextSize(float fraction, boolean forceRecalculate) {
        float availableWidth;
        float scaledDownWidth;
        Typeface newTypeface;
        float newTextSize;
        float f;
        boolean typefaceChanged;
        float fLerp;
        if (this.text == null) {
            return;
        }
        float collapsedWidth = this.collapsedBounds.width();
        float expandedWidth = this.expandedBounds.width();
        if (isClose(fraction, 1.0f)) {
            float newTextSize2 = shouldTruncateCollapsedToSingleLine() ? this.collapsedTextSize : this.expandedTextSize;
            float newLetterSpacing = shouldTruncateCollapsedToSingleLine() ? this.collapsedLetterSpacing : this.expandedLetterSpacing;
            if (!shouldTruncateCollapsedToSingleLine()) {
                fLerp = lerp(this.expandedTextSize, this.collapsedTextSize, fraction, this.textSizeInterpolator) / this.expandedTextSize;
            } else {
                fLerp = 1.0f;
            }
            this.scale = fLerp;
            float availableWidth2 = shouldTruncateCollapsedToSingleLine() ? collapsedWidth : expandedWidth;
            scaledDownWidth = newLetterSpacing;
            availableWidth = availableWidth2;
            newTypeface = this.collapsedTypeface;
            newTextSize = newTextSize2;
        } else {
            float newTextSize3 = this.expandedTextSize;
            float newLetterSpacing2 = this.expandedLetterSpacing;
            Typeface newTypeface2 = this.expandedTypeface;
            if (!isClose(fraction, 0.0f)) {
                this.scale = lerp(this.expandedTextSize, this.collapsedTextSize, fraction, this.textSizeInterpolator) / this.expandedTextSize;
            } else {
                this.scale = 1.0f;
            }
            float textSizeRatio = this.collapsedTextSize / this.expandedTextSize;
            float scaledDownWidth2 = expandedWidth * textSizeRatio;
            if (!forceRecalculate && !this.fadeModeEnabled) {
                if (scaledDownWidth2 > collapsedWidth && shouldTruncateCollapsedToSingleLine()) {
                    availableWidth = Math.min(collapsedWidth / textSizeRatio, expandedWidth);
                } else {
                    availableWidth = expandedWidth;
                }
                scaledDownWidth = newLetterSpacing2;
                newTypeface = newTypeface2;
                newTextSize = newTextSize3;
            } else {
                availableWidth = expandedWidth;
                scaledDownWidth = newLetterSpacing2;
                newTypeface = newTypeface2;
                newTextSize = newTextSize3;
            }
        }
        int maxLines = fraction < 0.5f ? this.expandedMaxLines : this.collapsedMaxLines;
        if (availableWidth > 0.0f) {
            boolean textSizeChanged = this.currentTextSize != newTextSize;
            boolean letterSpacingChanged = this.currentLetterSpacing != scaledDownWidth;
            boolean typefaceChanged2 = this.currentTypeface != newTypeface;
            boolean availableWidthChanged = (this.textLayout == null || availableWidth == ((float) this.textLayout.getWidth())) ? false : true;
            f = 1.0f;
            boolean maxLinesChanged = this.currentMaxLines != maxLines;
            boolean updateDrawText = textSizeChanged || letterSpacingChanged || availableWidthChanged || typefaceChanged2 || maxLinesChanged || this.boundsChanged;
            this.currentTextSize = newTextSize;
            this.currentLetterSpacing = scaledDownWidth;
            this.currentTypeface = newTypeface;
            this.boundsChanged = false;
            this.currentMaxLines = maxLines;
            this.textPaint.setLinearText(this.scale != 1.0f);
            typefaceChanged = updateDrawText;
        } else {
            f = 1.0f;
            typefaceChanged = false;
        }
        if (this.textToDraw == null || typefaceChanged) {
            this.textPaint.setTextSize(this.currentTextSize);
            this.textPaint.setTypeface(this.currentTypeface);
            this.textPaint.setLetterSpacing(this.currentLetterSpacing);
            this.isRtl = calculateIsRtl(this.text);
            int i = shouldDrawMultiline() ? maxLines : 1;
            TextPaint textPaint = this.textPaint;
            CharSequence charSequence = this.text;
            if (!shouldTruncateCollapsedToSingleLine()) {
                f = this.scale;
            }
            this.textLayout = createStaticLayout(i, textPaint, charSequence, availableWidth * f, this.isRtl);
            this.textToDraw = this.textLayout.getText();
        }
    }

    private StaticLayout createStaticLayout(int maxLines, TextPaint textPaint, CharSequence text, float availableWidth, boolean isRtl) throws StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException {
        StaticLayout textLayout = null;
        try {
            Layout.Alignment textAlignment = maxLines == 1 ? Layout.Alignment.ALIGN_NORMAL : getMultilineTextLayoutAlignment();
            textLayout = StaticLayoutBuilderCompat.obtain(text, textPaint, (int) availableWidth).setEllipsize(this.titleTextEllipsize).setIsRtl(isRtl).setAlignment(textAlignment).setIncludePad(false).setMaxLines(maxLines).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).setStaticLayoutBuilderConfigurer(this.staticLayoutBuilderConfigurer).build();
        } catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException e) {
            Log.e(TAG, e.getCause().getMessage(), e);
        }
        return (StaticLayout) Preconditions.checkNotNull(textLayout);
    }

    private Layout.Alignment getMultilineTextLayoutAlignment() {
        switch (Gravity.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0) & 7) {
            case 1:
                return Layout.Alignment.ALIGN_CENTER;
            case 5:
                return this.isRtl ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_OPPOSITE;
            default:
                return this.isRtl ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
        }
    }

    public void recalculate() {
        recalculate(false);
    }

    public void recalculate(boolean forceRecalculate) {
        if ((this.view.getHeight() > 0 && this.view.getWidth() > 0) || forceRecalculate) {
            calculateBaseOffsets(forceRecalculate);
            calculateCurrentOffsets();
        }
    }

    public void setText(CharSequence text) {
        if (text == null || !TextUtils.equals(this.text, text)) {
            this.text = text;
            this.textToDraw = null;
            recalculate();
        }
    }

    public CharSequence getText() {
        return this.text;
    }

    public void setExpandedMaxLines(int expandedMaxLines) {
        if (expandedMaxLines != this.expandedMaxLines) {
            this.expandedMaxLines = expandedMaxLines;
            recalculate();
        }
    }

    public int getExpandedMaxLines() {
        return this.expandedMaxLines;
    }

    public int getLineCount() {
        if (this.textLayout != null) {
            return this.textLayout.getLineCount();
        }
        return 0;
    }

    public int getExpandedLineCount() {
        return this.expandedLineCount;
    }

    public void setLineSpacingAdd(float spacingAdd) {
        this.lineSpacingAdd = spacingAdd;
    }

    public float getLineSpacingAdd() {
        return this.textLayout.getSpacingAdd();
    }

    public void setLineSpacingMultiplier(float spacingMultiplier) {
        this.lineSpacingMultiplier = spacingMultiplier;
    }

    public float getLineSpacingMultiplier() {
        return this.textLayout.getSpacingMultiplier();
    }

    public void setHyphenationFrequency(int hyphenationFrequency) {
        this.hyphenationFrequency = hyphenationFrequency;
    }

    public int getHyphenationFrequency() {
        return this.hyphenationFrequency;
    }

    public void setStaticLayoutBuilderConfigurer(StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer) {
        if (this.staticLayoutBuilderConfigurer != staticLayoutBuilderConfigurer) {
            this.staticLayoutBuilderConfigurer = staticLayoutBuilderConfigurer;
            recalculate(true);
        }
    }

    private static boolean isClose(float value, float targetValue) {
        return Math.abs(value - targetValue) < 1.0E-5f;
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    private static int blendARGB(int color1, int color2, float ratio) {
        float inverseRatio = 1.0f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb(Math.round(a), Math.round(r), Math.round(g), Math.round(b));
    }

    private static float lerp(float startValue, float endValue, float fraction, TimeInterpolator interpolator) {
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }
        return AnimationUtils.lerp(startValue, endValue, fraction);
    }

    private static boolean rectEquals(Rect r, int left, int top, int right, int bottom) {
        return r.left == left && r.top == top && r.right == right && r.bottom == bottom;
    }
}
