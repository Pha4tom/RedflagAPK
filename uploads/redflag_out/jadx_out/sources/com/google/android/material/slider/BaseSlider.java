package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
    private static final int DEFAULT_LABEL_ANIMATION_ENTER_DURATION = 83;
    private static final int DEFAULT_LABEL_ANIMATION_EXIT_DURATION = 117;
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION = "minSeparation(%s) must be greater or equal to 0";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE = "minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT = "minSeparation(%s) cannot be set as a dimension when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
    private static final int HALO_ALPHA = 63;
    private static final float LEFT_LABEL_PIVOT_X = 1.2f;
    private static final float LEFT_LABEL_PIVOT_Y = 0.5f;
    private static final int MAX_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY = 120000;
    private static final int MIN_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY = 10000;
    private static final float RIGHT_LABEL_PIVOT_X = -0.2f;
    private static final float RIGHT_LABEL_PIVOT_Y = 0.5f;
    private static final double THRESHOLD = 1.0E-4d;
    private static final float THUMB_WIDTH_PRESSED_RATIO = 0.5f;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    private static final float TOP_LABEL_PIVOT_X = 0.5f;
    private static final float TOP_LABEL_PIVOT_Y = 1.2f;
    private static final float TOUCH_SLOP_RATIO = 0.8f;
    private static final int TRACK_CORNER_SIZE_UNSET = -1;
    static final int UNIT_PX = 0;
    static final int UNIT_VALUE = 1;
    private static final String WARNING_FLOATING_POINT_ERROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.";
    private static final String WARNING_PARSE_ERROR = "Error parsing value(%s), valueFrom(%s), and valueTo(%s) into a float.";
    private BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
    private final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final RectF activeTrackRect;
    private boolean centered;
    private final List<L> changeListeners;
    private final RectF cornerRect;
    private Drawable customThumbDrawable;
    private List<Drawable> customThumbDrawablesForValues;
    private final MaterialShapeDrawable defaultThumbDrawable;
    private int defaultThumbRadius;
    private int defaultThumbTrackGapSize;
    private int defaultThumbWidth;
    private int defaultTickActiveRadius;
    private int defaultTickInactiveRadius;
    private int defaultTrackThickness;
    private boolean dirtyConfig;
    private int focusedThumbIdx;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Rect iconRect;
    private final RectF iconRectF;
    private final Paint inactiveTicksPaint;
    private final RectF inactiveTrackLeftRect;
    private final Paint inactiveTrackPaint;
    private final RectF inactiveTrackRightRect;
    private boolean isLongPress;
    private int labelBehavior;
    private int labelPadding;
    private final Rect labelRect;
    private int labelStyle;
    private final List<TooltipDrawable> labels;
    private boolean labelsAreAnimatedIn;
    private ValueAnimator labelsInAnimator;
    private ValueAnimator labelsOutAnimator;
    private MotionEvent lastEvent;
    private int minTickSpacing;
    private int minTouchTargetSize;
    private int minTrackSidePadding;
    private int minWidgetThickness;
    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;
    private final Runnable resetActiveThumbIndex;
    private final Matrix rotationMatrix;
    private final int scaledTouchSlop;
    private int separationUnit;
    private float stepSize;
    private final Paint stopIndicatorPaint;
    private boolean thisAndAncestorsVisible;
    private int thumbHeight;
    private boolean thumbIsPressed;
    private final Paint thumbPaint;
    private int thumbTrackGapSize;
    private int thumbWidth;
    private int tickActiveRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private int tickInactiveRadius;
    private int tickVisibilityMode;
    private float[] ticksCoordinates;
    private final int tooltipTimeoutMillis;
    private float touchDownAxis1;
    private float touchDownAxis2;
    private final List<T> touchListeners;
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackCornerSize;
    private ColorStateList trackIconActiveColor;
    private Drawable trackIconActiveEnd;
    private boolean trackIconActiveEndMutated;
    private Drawable trackIconActiveStart;
    private boolean trackIconActiveStartMutated;
    private ColorStateList trackIconInactiveColor;
    private Drawable trackIconInactiveEnd;
    private boolean trackIconInactiveEndMutated;
    private Drawable trackIconInactiveStart;
    private boolean trackIconInactiveStartMutated;
    private int trackIconPadding;
    private int trackIconSize;
    private int trackInsideCornerSize;
    private final Path trackPath;
    private int trackSidePadding;
    private int trackStopIndicatorSize;
    private int trackThickness;
    private int trackWidth;
    private float valueFrom;
    private float valueTo;
    private ArrayList<Float> values;
    private int widgetOrientation;
    private int widgetThickness;
    private static final String TAG = BaseSlider.class.getSimpleName();
    static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;
    private static final int LABEL_ANIMATION_ENTER_DURATION_ATTR = R.attr.motionDurationMedium4;
    private static final int LABEL_ANIMATION_EXIT_DURATION_ATTR = R.attr.motionDurationShort3;
    private static final int LABEL_ANIMATION_ENTER_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;
    private static final int LABEL_ANIMATION_EXIT_EASING_ATTR = R.attr.motionEasingEmphasizedAccelerateInterpolator;

    private enum FullCornerDirection {
        BOTH,
        LEFT,
        RIGHT,
        NONE
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    /* JADX INFO: renamed from: lambda$new$0$com-google-android-material-slider-BaseSlider, reason: not valid java name */
    /* synthetic */ void m241lambda$new$0$comgoogleandroidmaterialsliderBaseSlider() {
        setActiveThumbIndex(-1);
        invalidate();
    }

    public BaseSlider(Context context) {
        this(context, null);
    }

    public BaseSlider(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.sliderStyle);
    }

    public BaseSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.defaultThumbWidth = -1;
        this.defaultThumbTrackGapSize = -1;
        this.centered = false;
        this.trackIconActiveStartMutated = false;
        this.trackIconActiveEndMutated = false;
        this.trackIconInactiveStartMutated = false;
        this.trackIconInactiveEndMutated = false;
        this.thumbIsPressed = false;
        this.values = new ArrayList<>();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.isLongPress = false;
        this.trackPath = new Path();
        this.activeTrackRect = new RectF();
        this.inactiveTrackLeftRect = new RectF();
        this.inactiveTrackRightRect = new RectF();
        this.cornerRect = new RectF();
        this.labelRect = new Rect();
        this.iconRectF = new RectF();
        this.iconRect = new Rect();
        this.rotationMatrix = new Matrix();
        this.defaultThumbDrawable = new MaterialShapeDrawable();
        this.customThumbDrawablesForValues = Collections.emptyList();
        this.separationUnit = 0;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                this.f$0.updateLabels();
            }
        };
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                this.f$0.updateLabels();
            }
        };
        this.resetActiveThumbIndex = new Runnable() { // from class: com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m241lambda$new$0$comgoogleandroidmaterialsliderBaseSlider();
            }
        };
        Context context2 = getContext();
        this.thisAndAncestorsVisible = isShown();
        this.inactiveTrackPaint = new Paint();
        this.activeTrackPaint = new Paint();
        this.thumbPaint = new Paint(1);
        this.thumbPaint.setStyle(Paint.Style.FILL);
        this.thumbPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.haloPaint = new Paint(1);
        this.haloPaint.setStyle(Paint.Style.FILL);
        this.inactiveTicksPaint = new Paint();
        this.inactiveTicksPaint.setStyle(Paint.Style.STROKE);
        this.inactiveTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.activeTicksPaint = new Paint();
        this.activeTicksPaint.setStyle(Paint.Style.STROKE);
        this.activeTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.stopIndicatorPaint = new Paint();
        this.stopIndicatorPaint.setStyle(Paint.Style.FILL);
        this.stopIndicatorPaint.setStrokeCap(Paint.Cap.ROUND);
        loadResources(context2.getResources());
        processAttributes(context2, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        this.defaultThumbDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.accessibilityHelper = new AccessibilityHelper(this);
        ViewCompat.setAccessibilityDelegate(this, this.accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (Build.VERSION.SDK_INT >= 29) {
            this.tooltipTimeoutMillis = this.accessibilityManager.getRecommendedTimeoutMillis(MIN_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY, 6);
        } else {
            this.tooltipTimeoutMillis = MAX_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY;
        }
    }

    private void loadResources(Resources resources) {
        this.minWidgetThickness = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        this.minTrackSidePadding = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.trackSidePadding = this.minTrackSidePadding;
        this.defaultThumbRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.defaultTrackThickness = resources.getDimensionPixelSize(R.dimen.mtrl_slider_track_height);
        this.defaultTickActiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.defaultTickInactiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.minTickSpacing = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_min_spacing);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
        this.trackIconPadding = resources.getDimensionPixelOffset(R.dimen.m3_slider_track_icon_padding);
    }

    private void processAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        int iConvertToTickVisibilityMode;
        ColorStateList colorStateList4;
        ColorStateList colorStateList5;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Slider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        setOrientation(a.getInt(R.styleable.Slider_android_orientation, 0));
        this.labelStyle = a.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip);
        this.valueFrom = a.getFloat(R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = a.getFloat(R.styleable.Slider_android_valueTo, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        setCentered(a.getBoolean(R.styleable.Slider_centered, false));
        this.stepSize = a.getFloat(R.styleable.Slider_android_stepSize, 0.0f);
        float defaultMinTouchTargetSize = MaterialAttributes.resolveMinimumAccessibleTouchTarget(context);
        this.minTouchTargetSize = (int) Math.ceil(a.getDimension(R.styleable.Slider_minTouchTargetSize, defaultMinTouchTargetSize));
        boolean hasTrackColor = a.hasValue(R.styleable.Slider_trackColor);
        int trackColorInactiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorInactive;
        int trackColorActiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorActive;
        ColorStateList trackColorInactive = MaterialResources.getColorStateList(context, a, trackColorInactiveRes);
        if (trackColorInactive == null) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_track_color);
        } else {
            colorStateList = trackColorInactive;
        }
        setTrackInactiveTintList(colorStateList);
        ColorStateList trackColorActive = MaterialResources.getColorStateList(context, a, trackColorActiveRes);
        if (trackColorActive == null) {
            colorStateList2 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_track_color);
        } else {
            colorStateList2 = trackColorActive;
        }
        setTrackActiveTintList(colorStateList2);
        ColorStateList thumbColor = MaterialResources.getColorStateList(context, a, R.styleable.Slider_thumbColor);
        this.defaultThumbDrawable.setFillColor(thumbColor);
        if (a.hasValue(R.styleable.Slider_thumbStrokeColor)) {
            setThumbStrokeColor(MaterialResources.getColorStateList(context, a, R.styleable.Slider_thumbStrokeColor));
        }
        setThumbStrokeWidth(a.getDimension(R.styleable.Slider_thumbStrokeWidth, 0.0f));
        ColorStateList haloColor = MaterialResources.getColorStateList(context, a, R.styleable.Slider_haloColor);
        if (haloColor == null) {
            colorStateList3 = AppCompatResources.getColorStateList(context, R.color.material_slider_halo_color);
        } else {
            colorStateList3 = haloColor;
        }
        setHaloTintList(colorStateList3);
        if (!a.hasValue(R.styleable.Slider_tickVisibilityMode)) {
            iConvertToTickVisibilityMode = convertToTickVisibilityMode(a.getBoolean(R.styleable.Slider_tickVisible, true));
        } else {
            iConvertToTickVisibilityMode = a.getInt(R.styleable.Slider_tickVisibilityMode, -1);
        }
        this.tickVisibilityMode = iConvertToTickVisibilityMode;
        boolean hasTickColor = a.hasValue(R.styleable.Slider_tickColor);
        int tickColorInactiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorInactive;
        int tickColorActiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorActive;
        ColorStateList tickColorInactive = MaterialResources.getColorStateList(context, a, tickColorInactiveRes);
        if (tickColorInactive == null) {
            colorStateList4 = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_tick_marks_color);
        } else {
            colorStateList4 = tickColorInactive;
        }
        setTickInactiveTintList(colorStateList4);
        ColorStateList tickColorActive = MaterialResources.getColorStateList(context, a, tickColorActiveRes);
        if (tickColorActive == null) {
            colorStateList5 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_tick_marks_color);
        } else {
            colorStateList5 = tickColorActive;
        }
        setTickActiveTintList(colorStateList5);
        setThumbTrackGapSize(a.getDimensionPixelSize(R.styleable.Slider_thumbTrackGapSize, 0));
        setTrackStopIndicatorSize(a.getDimensionPixelSize(R.styleable.Slider_trackStopIndicatorSize, 0));
        setTrackCornerSize(a.getDimensionPixelSize(R.styleable.Slider_trackCornerSize, -1));
        setTrackInsideCornerSize(a.getDimensionPixelSize(R.styleable.Slider_trackInsideCornerSize, 0));
        setTrackIconActiveStart(MaterialResources.getDrawable(context, a, R.styleable.Slider_trackIconActiveStart));
        setTrackIconActiveEnd(MaterialResources.getDrawable(context, a, R.styleable.Slider_trackIconActiveEnd));
        setTrackIconActiveColor(MaterialResources.getColorStateList(context, a, R.styleable.Slider_trackIconActiveColor));
        setTrackIconInactiveStart(MaterialResources.getDrawable(context, a, R.styleable.Slider_trackIconInactiveStart));
        setTrackIconInactiveEnd(MaterialResources.getDrawable(context, a, R.styleable.Slider_trackIconInactiveEnd));
        setTrackIconInactiveColor(MaterialResources.getColorStateList(context, a, R.styleable.Slider_trackIconInactiveColor));
        setTrackIconSize(a.getDimensionPixelSize(R.styleable.Slider_trackIconSize, 0));
        int radius = a.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0);
        int thumbWidth = a.getDimensionPixelSize(R.styleable.Slider_thumbWidth, radius * 2);
        int tickColorInactiveRes2 = radius * 2;
        int thumbHeight = a.getDimensionPixelSize(R.styleable.Slider_thumbHeight, tickColorInactiveRes2);
        setThumbWidth(thumbWidth);
        setThumbHeight(thumbHeight);
        setHaloRadius(a.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
        setThumbElevation(a.getDimension(R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(a.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
        setTickActiveRadius(a.getDimensionPixelSize(R.styleable.Slider_tickRadiusActive, this.trackStopIndicatorSize / 2));
        setTickInactiveRadius(a.getDimensionPixelSize(R.styleable.Slider_tickRadiusInactive, this.trackStopIndicatorSize / 2));
        setLabelBehavior(a.getInt(R.styleable.Slider_labelBehavior, 0));
        if (!a.getBoolean(R.styleable.Slider_android_enabled, true)) {
            setEnabled(false);
        }
        a.recycle();
    }

    private boolean maybeIncreaseTrackSidePadding() {
        int increasedSidePaddingByThumb = Math.max((this.thumbWidth / 2) - this.defaultThumbRadius, 0);
        int increasedSidePaddingByTrack = Math.max((this.trackThickness - this.defaultTrackThickness) / 2, 0);
        int increasedSidePaddingByActiveTick = Math.max(this.tickActiveRadius - this.defaultTickActiveRadius, 0);
        int increasedSidePaddingByInactiveTick = Math.max(this.tickInactiveRadius - this.defaultTickInactiveRadius, 0);
        int newTrackSidePadding = this.minTrackSidePadding + Math.max(Math.max(increasedSidePaddingByThumb, increasedSidePaddingByTrack), Math.max(increasedSidePaddingByActiveTick, increasedSidePaddingByInactiveTick));
        if (this.trackSidePadding == newTrackSidePadding) {
            return false;
        }
        this.trackSidePadding = newTrackSidePadding;
        if (isLaidOut()) {
            updateTrackWidth(isVertical() ? getHeight() : getWidth());
            return true;
        }
        return true;
    }

    private boolean valueLandsOnTick(float value) {
        double result = new BigDecimal(Float.toString(value)).subtract(new BigDecimal(Float.toString(this.valueFrom)), MathContext.DECIMAL64).doubleValue();
        return isMultipleOfStepSize(result);
    }

    private boolean isMultipleOfStepSize(double value) {
        double result = new BigDecimal(Double.toString(value)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        return Math.abs(((double) Math.round(result)) - result) < THRESHOLD;
    }

    private void validateStepSize() {
        if (this.stepSize > 0.0f && !valueLandsOnTick(this.valueTo)) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
        }
    }

    private void validateValues() {
        if (this.valueFrom >= this.valueTo) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_FROM, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
        }
        for (Float value : this.values) {
            if (value.floatValue() < this.valueFrom || value.floatValue() > this.valueTo) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE, value, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
            }
            if (this.stepSize > 0.0f && !valueLandsOnTick(value.floatValue())) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_DISCRETE_VALUE, value, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)));
            }
        }
    }

    private void validateMinSeparation() {
        float minSeparation = getMinSeparation();
        if (minSeparation < 0.0f) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION, Float.valueOf(minSeparation)));
        }
        if (this.stepSize > 0.0f && minSeparation > 0.0f) {
            if (this.separationUnit != 1) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT, Float.valueOf(minSeparation), Float.valueOf(this.stepSize)));
            }
            if (minSeparation < this.stepSize || !isMultipleOfStepSize(minSeparation)) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE, Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)));
            }
        }
    }

    private void warnAboutFloatingPointError() {
        if (this.stepSize == 0.0f) {
            return;
        }
        if (((int) this.stepSize) != this.stepSize) {
            Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, "stepSize", Float.valueOf(this.stepSize)));
        }
        if (((int) this.valueFrom) != this.valueFrom) {
            Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, "valueFrom", Float.valueOf(this.valueFrom)));
        }
        if (((int) this.valueTo) != this.valueTo) {
            Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, "valueTo", Float.valueOf(this.valueTo)));
        }
    }

    private void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            validateValues();
            validateStepSize();
            validateMinSeparation();
            warnAboutFloatingPointError();
            this.dirtyConfig = false;
        }
    }

    public void scheduleTooltipTimeout() {
        removeCallbacks(this.resetActiveThumbIndex);
        postDelayed(this.resetActiveThumbIndex, this.tooltipTimeoutMillis);
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public void setValueFrom(float valueFrom) {
        this.valueFrom = valueFrom;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.valueTo;
    }

    public void setValueTo(float valueTo) {
        this.valueTo = valueTo;
        this.dirtyConfig = true;
        postInvalidate();
    }

    List<Float> getValues() {
        return new ArrayList(this.values);
    }

    void setValues(Float... values) {
        ArrayList<Float> list = new ArrayList<>();
        Collections.addAll(list, values);
        setValuesInternal(list);
    }

    void setValues(List<Float> values) {
        setValuesInternal(new ArrayList<>(values));
    }

    private void setValuesInternal(ArrayList<Float> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("At least one value must be set");
        }
        Collections.sort(values);
        if (this.values.size() == values.size() && this.values.equals(values)) {
            return;
        }
        this.values = values;
        this.dirtyConfig = true;
        this.focusedThumbIdx = 0;
        updateHaloHotspot();
        createLabelPool();
        dispatchOnChangedProgrammatically();
        postInvalidate();
    }

    private void createLabelPool() {
        if (this.labels.size() > this.values.size()) {
            List<TooltipDrawable> tooltipDrawables = this.labels.subList(this.values.size(), this.labels.size());
            for (TooltipDrawable label : tooltipDrawables) {
                if (isAttachedToWindow()) {
                    detachLabelFromContentView(label);
                }
            }
            tooltipDrawables.clear();
        }
        while (true) {
            if (this.labels.size() >= this.values.size()) {
                break;
            }
            TooltipDrawable tooltipDrawable = TooltipDrawable.createFromAttributes(getContext(), null, 0, this.labelStyle);
            this.labels.add(tooltipDrawable);
            if (isAttachedToWindow()) {
                attachLabelToContentView(tooltipDrawable);
            }
        }
        int strokeWidth = this.labels.size() != 1 ? 1 : 0;
        for (TooltipDrawable label2 : this.labels) {
            label2.setStrokeWidth(strokeWidth);
        }
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(float stepSize) {
        if (stepSize < 0.0f) {
            throw new IllegalArgumentException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, Float.valueOf(stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
        }
        if (this.stepSize != stepSize) {
            this.stepSize = stepSize;
            this.dirtyConfig = true;
            postInvalidate();
        }
    }

    void setCustomThumbDrawable(int drawableResId) {
        setCustomThumbDrawable(getResources().getDrawable(drawableResId));
    }

    void setCustomThumbDrawable(Drawable drawable) {
        this.customThumbDrawable = initializeCustomThumbDrawable(drawable);
        this.customThumbDrawablesForValues.clear();
        postInvalidate();
    }

    void setCustomThumbDrawablesForValues(int... customThumbDrawableResIds) {
        Drawable[] customThumbDrawables = new Drawable[customThumbDrawableResIds.length];
        for (int i = 0; i < customThumbDrawableResIds.length; i++) {
            customThumbDrawables[i] = getResources().getDrawable(customThumbDrawableResIds[i]);
        }
        setCustomThumbDrawablesForValues(customThumbDrawables);
    }

    void setCustomThumbDrawablesForValues(Drawable... customThumbDrawables) {
        this.customThumbDrawable = null;
        this.customThumbDrawablesForValues = new ArrayList();
        for (Drawable originalDrawable : customThumbDrawables) {
            this.customThumbDrawablesForValues.add(initializeCustomThumbDrawable(originalDrawable));
        }
        postInvalidate();
    }

    private Drawable initializeCustomThumbDrawable(Drawable originalDrawable) {
        Drawable drawable = originalDrawable.mutate().getConstantState().newDrawable();
        adjustCustomThumbDrawableBounds(drawable);
        return drawable;
    }

    private void adjustCustomThumbDrawableBounds(Drawable drawable) {
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        if (originalWidth == -1 && originalHeight == -1) {
            drawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
        } else {
            float scaleRatio = Math.max(this.thumbWidth, this.thumbHeight) / Math.max(originalWidth, originalHeight);
            drawable.setBounds(0, 0, (int) (originalWidth * scaleRatio), (int) (originalHeight * scaleRatio));
        }
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public void setFocusedThumbIndex(int index) {
        if (index < 0 || index >= this.values.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.focusedThumbIdx = index;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
        postInvalidate();
    }

    protected void setActiveThumbIndex(int index) {
        this.activeThumbIdx = index;
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public void addOnChangeListener(L listener) {
        this.changeListeners.add(listener);
    }

    public void removeOnChangeListener(L listener) {
        this.changeListeners.remove(listener);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void addOnSliderTouchListener(T listener) {
        this.touchListeners.add(listener);
    }

    public void removeOnSliderTouchListener(T listener) {
        this.touchListeners.remove(listener);
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean hasLabelFormatter() {
        return this.formatter != null;
    }

    public void setLabelFormatter(LabelFormatter formatter) {
        this.formatter = formatter;
    }

    public float getThumbElevation() {
        return this.defaultThumbDrawable.getElevation();
    }

    public void setThumbElevation(float elevation) {
        this.defaultThumbDrawable.setElevation(elevation);
    }

    public void setThumbElevationResource(int elevation) {
        setThumbElevation(getResources().getDimension(elevation));
    }

    public int getThumbRadius() {
        return this.thumbWidth / 2;
    }

    public void setThumbRadius(int radius) {
        setThumbWidth(radius * 2);
        setThumbHeight(radius * 2);
    }

    public void setThumbRadiusResource(int radius) {
        setThumbRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getThumbWidth() {
        return this.thumbWidth;
    }

    public void setThumbWidth(int width) {
        if (width == this.thumbWidth) {
            return;
        }
        this.thumbWidth = width;
        this.defaultThumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, this.thumbWidth / 2.0f).build());
        this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
        if (this.customThumbDrawable != null) {
            adjustCustomThumbDrawableBounds(this.customThumbDrawable);
        }
        for (Drawable customDrawable : this.customThumbDrawablesForValues) {
            adjustCustomThumbDrawableBounds(customDrawable);
        }
        updateWidgetLayout(false);
    }

    public void setThumbWidthResource(int width) {
        setThumbWidth(getResources().getDimensionPixelSize(width));
    }

    public int getThumbHeight() {
        return this.thumbHeight;
    }

    public void setThumbHeight(int height) {
        if (height == this.thumbHeight) {
            return;
        }
        this.thumbHeight = height;
        this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
        if (this.customThumbDrawable != null) {
            adjustCustomThumbDrawableBounds(this.customThumbDrawable);
        }
        for (Drawable customDrawable : this.customThumbDrawablesForValues) {
            adjustCustomThumbDrawableBounds(customDrawable);
        }
        updateWidgetLayout(false);
    }

    public void setThumbHeightResource(int height) {
        setThumbHeight(getResources().getDimensionPixelSize(height));
    }

    public void setThumbStrokeColor(ColorStateList thumbStrokeColor) {
        this.defaultThumbDrawable.setStrokeColor(thumbStrokeColor);
        postInvalidate();
    }

    public void setThumbStrokeColorResource(int thumbStrokeColorResourceId) {
        if (thumbStrokeColorResourceId != 0) {
            setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), thumbStrokeColorResourceId));
        }
    }

    public ColorStateList getThumbStrokeColor() {
        return this.defaultThumbDrawable.getStrokeColor();
    }

    public void setThumbStrokeWidth(float thumbStrokeWidth) {
        this.defaultThumbDrawable.setStrokeWidth(thumbStrokeWidth);
        postInvalidate();
    }

    public void setThumbStrokeWidthResource(int thumbStrokeWidthResourceId) {
        if (thumbStrokeWidthResourceId != 0) {
            setThumbStrokeWidth(getResources().getDimension(thumbStrokeWidthResourceId));
        }
    }

    public float getThumbStrokeWidth() {
        return this.defaultThumbDrawable.getStrokeWidth();
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public void setHaloRadius(int radius) {
        if (radius == this.haloRadius) {
            return;
        }
        this.haloRadius = radius;
        Drawable background = getBackground();
        if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
            DrawableUtils.setRippleDrawableRadius((RippleDrawable) background, this.haloRadius);
        } else {
            postInvalidate();
        }
    }

    public void setHaloRadiusResource(int radius) {
        setHaloRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public void setLabelBehavior(int labelBehavior) {
        if (this.labelBehavior != labelBehavior) {
            this.labelBehavior = labelBehavior;
            updateWidgetLayout(true);
        }
    }

    private boolean shouldAlwaysShowLabel() {
        return this.labelBehavior == 3;
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public int getTrackHeight() {
        return this.trackThickness;
    }

    public void setTrackHeight(int trackHeight) {
        if (this.trackThickness != trackHeight) {
            this.trackThickness = trackHeight;
            invalidateTrack();
            updateWidgetLayout(false);
        }
    }

    public int getTickActiveRadius() {
        return this.tickActiveRadius;
    }

    public void setTickActiveRadius(int tickActiveRadius) {
        if (this.tickActiveRadius != tickActiveRadius) {
            this.tickActiveRadius = tickActiveRadius;
            this.activeTicksPaint.setStrokeWidth(tickActiveRadius * 2);
            updateWidgetLayout(false);
        }
    }

    public int getTickInactiveRadius() {
        return this.tickInactiveRadius;
    }

    public void setTickInactiveRadius(int tickInactiveRadius) {
        if (this.tickInactiveRadius != tickInactiveRadius) {
            this.tickInactiveRadius = tickInactiveRadius;
            this.inactiveTicksPaint.setStrokeWidth(tickInactiveRadius * 2);
            updateWidgetLayout(false);
        }
    }

    private void updateWidgetLayout(boolean forceRefresh) {
        boolean sizeChanged = maybeIncreaseWidgetThickness();
        boolean sidePaddingChanged = maybeIncreaseTrackSidePadding();
        if (isVertical()) {
            updateRotationMatrix();
        }
        if (sizeChanged || forceRefresh) {
            requestLayout();
        } else if (sidePaddingChanged) {
            postInvalidate();
        }
    }

    private boolean maybeIncreaseWidgetThickness() {
        int paddings;
        if (isVertical()) {
            paddings = getPaddingLeft() + getPaddingRight();
        } else {
            int paddings2 = getPaddingTop();
            paddings = paddings2 + getPaddingBottom();
        }
        int minHeightRequiredByTrack = this.trackThickness + paddings;
        int minHeightRequiredByThumb = this.thumbHeight + paddings;
        int newWidgetHeight = Math.max(this.minWidgetThickness, Math.max(minHeightRequiredByTrack, minHeightRequiredByThumb));
        if (newWidgetHeight == this.widgetThickness) {
            return false;
        }
        this.widgetThickness = newWidgetHeight;
        return true;
    }

    private void updateRotationMatrix() {
        float pivot = calculateTrackCenter();
        this.rotationMatrix.reset();
        this.rotationMatrix.setRotate(90.0f, pivot, pivot);
    }

    public ColorStateList getHaloTintList() {
        return this.haloColor;
    }

    public void setHaloTintList(ColorStateList haloColor) {
        if (haloColor.equals(this.haloColor)) {
            return;
        }
        this.haloColor = haloColor;
        Drawable background = getBackground();
        if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
            ((RippleDrawable) background).setColor(haloColor);
            return;
        }
        this.haloPaint.setColor(getColorForState(haloColor));
        this.haloPaint.setAlpha(63);
        invalidate();
    }

    public ColorStateList getThumbTintList() {
        return this.defaultThumbDrawable.getFillColor();
    }

    public void setThumbTintList(ColorStateList thumbColor) {
        if (thumbColor.equals(this.defaultThumbDrawable.getFillColor())) {
            return;
        }
        this.defaultThumbDrawable.setFillColor(thumbColor);
        invalidate();
    }

    public ColorStateList getTickTintList() {
        if (!this.tickColorInactive.equals(this.tickColorActive)) {
            throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
        }
        return this.tickColorActive;
    }

    public void setTickTintList(ColorStateList tickColor) {
        setTickInactiveTintList(tickColor);
        setTickActiveTintList(tickColor);
    }

    public ColorStateList getTickActiveTintList() {
        return this.tickColorActive;
    }

    public void setTickActiveTintList(ColorStateList tickColor) {
        if (tickColor.equals(this.tickColorActive)) {
            return;
        }
        this.tickColorActive = tickColor;
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        invalidate();
    }

    public ColorStateList getTickInactiveTintList() {
        return this.tickColorInactive;
    }

    public void setTickInactiveTintList(ColorStateList tickColor) {
        if (tickColor.equals(this.tickColorInactive)) {
            return;
        }
        this.tickColorInactive = tickColor;
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        invalidate();
    }

    public boolean isTickVisible() {
        switch (this.tickVisibilityMode) {
            case 0:
                return true;
            case 1:
                return getDesiredTickCount() <= getMaxTickCount();
            case 2:
                return false;
            default:
                throw new IllegalStateException("Unexpected tickVisibilityMode: " + this.tickVisibilityMode);
        }
    }

    @Deprecated
    public void setTickVisible(boolean tickVisible) {
        setTickVisibilityMode(convertToTickVisibilityMode(tickVisible));
    }

    private int convertToTickVisibilityMode(boolean tickVisible) {
        return tickVisible ? 0 : 2;
    }

    public int getTickVisibilityMode() {
        return this.tickVisibilityMode;
    }

    public void setTickVisibilityMode(int tickVisibilityMode) {
        if (this.tickVisibilityMode != tickVisibilityMode) {
            this.tickVisibilityMode = tickVisibilityMode;
            postInvalidate();
        }
    }

    public ColorStateList getTrackTintList() {
        if (!this.trackColorInactive.equals(this.trackColorActive)) {
            throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
        }
        return this.trackColorActive;
    }

    public void setTrackTintList(ColorStateList trackColor) {
        setTrackInactiveTintList(trackColor);
        setTrackActiveTintList(trackColor);
    }

    public ColorStateList getTrackActiveTintList() {
        return this.trackColorActive;
    }

    public void setTrackActiveTintList(ColorStateList trackColor) {
        if (trackColor.equals(this.trackColorActive)) {
            return;
        }
        this.trackColorActive = trackColor;
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        invalidate();
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.trackColorInactive;
    }

    public void setTrackInactiveTintList(ColorStateList trackColor) {
        if (trackColor.equals(this.trackColorInactive)) {
            return;
        }
        this.trackColorInactive = trackColor;
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        invalidate();
    }

    public int getThumbTrackGapSize() {
        return this.thumbTrackGapSize;
    }

    public void setThumbTrackGapSize(int thumbTrackGapSize) {
        if (this.thumbTrackGapSize == thumbTrackGapSize) {
            return;
        }
        this.thumbTrackGapSize = thumbTrackGapSize;
        invalidate();
    }

    public int getTrackStopIndicatorSize() {
        return this.trackStopIndicatorSize;
    }

    public void setTrackStopIndicatorSize(int trackStopIndicatorSize) {
        if (this.trackStopIndicatorSize == trackStopIndicatorSize) {
            return;
        }
        this.trackStopIndicatorSize = trackStopIndicatorSize;
        this.stopIndicatorPaint.setStrokeWidth(trackStopIndicatorSize);
        invalidate();
    }

    public int getTrackCornerSize() {
        if (this.trackCornerSize == -1) {
            return this.trackThickness / 2;
        }
        return this.trackCornerSize;
    }

    public void setTrackCornerSize(int cornerSize) {
        if (this.trackCornerSize == cornerSize) {
            return;
        }
        this.trackCornerSize = cornerSize;
        invalidate();
    }

    public int getTrackInsideCornerSize() {
        return this.trackInsideCornerSize;
    }

    public void setTrackInsideCornerSize(int cornerSize) {
        if (this.trackInsideCornerSize == cornerSize) {
            return;
        }
        this.trackInsideCornerSize = cornerSize;
        invalidate();
    }

    public void setTrackIconActiveStart(Drawable icon) {
        if (icon == this.trackIconActiveStart) {
            return;
        }
        this.trackIconActiveStart = icon;
        this.trackIconActiveStartMutated = false;
        updateTrackIconActiveStart();
        invalidate();
    }

    private void updateTrackIconActiveStart() {
        if (this.trackIconActiveStart != null) {
            if (!this.trackIconActiveStartMutated && this.trackIconActiveColor != null) {
                this.trackIconActiveStart = DrawableCompat.wrap(this.trackIconActiveStart).mutate();
                this.trackIconActiveStartMutated = true;
            }
            if (this.trackIconActiveStartMutated) {
                this.trackIconActiveStart.setTintList(this.trackIconActiveColor);
            }
        }
    }

    public void setTrackIconActiveStart(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconActiveStart(icon);
    }

    public Drawable getTrackIconActiveStart() {
        return this.trackIconActiveStart;
    }

    public void setTrackIconActiveEnd(Drawable icon) {
        if (icon == this.trackIconActiveEnd) {
            return;
        }
        this.trackIconActiveEnd = icon;
        this.trackIconActiveEndMutated = false;
        updateTrackIconActiveEnd();
        invalidate();
    }

    private void updateTrackIconActiveEnd() {
        if (this.trackIconActiveEnd != null) {
            if (!this.trackIconActiveEndMutated && this.trackIconActiveColor != null) {
                this.trackIconActiveEnd = DrawableCompat.wrap(this.trackIconActiveEnd).mutate();
                this.trackIconActiveEndMutated = true;
            }
            if (this.trackIconActiveEndMutated) {
                this.trackIconActiveEnd.setTintList(this.trackIconActiveColor);
            }
        }
    }

    public void setTrackIconActiveEnd(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconActiveEnd(icon);
    }

    public Drawable getTrackIconActiveEnd() {
        return this.trackIconActiveEnd;
    }

    public void setTrackIconSize(int size) {
        if (this.trackIconSize == size) {
            return;
        }
        this.trackIconSize = size;
        invalidate();
    }

    public int getTrackIconSize() {
        return this.trackIconSize;
    }

    public void setTrackIconActiveColor(ColorStateList color) {
        if (color == this.trackIconActiveColor) {
            return;
        }
        this.trackIconActiveColor = color;
        updateTrackIconActiveStart();
        updateTrackIconActiveEnd();
        invalidate();
    }

    public ColorStateList getTrackIconActiveColor() {
        return this.trackIconActiveColor;
    }

    public void setTrackIconInactiveStart(Drawable icon) {
        if (icon == this.trackIconInactiveStart) {
            return;
        }
        this.trackIconInactiveStart = icon;
        this.trackIconInactiveStartMutated = false;
        updateTrackIconInactiveStart();
        invalidate();
    }

    private void updateTrackIconInactiveStart() {
        if (this.trackIconInactiveStart != null) {
            if (!this.trackIconInactiveStartMutated && this.trackIconInactiveColor != null) {
                this.trackIconInactiveStart = DrawableCompat.wrap(this.trackIconInactiveStart).mutate();
                this.trackIconInactiveStartMutated = true;
            }
            if (this.trackIconInactiveStartMutated) {
                this.trackIconInactiveStart.setTintList(this.trackIconInactiveColor);
            }
        }
    }

    public void setTrackIconInactiveStart(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconInactiveStart(icon);
    }

    public Drawable getTrackIconInactiveStart() {
        return this.trackIconInactiveStart;
    }

    public void setTrackIconInactiveEnd(Drawable icon) {
        if (icon == this.trackIconInactiveEnd) {
            return;
        }
        this.trackIconInactiveEnd = icon;
        this.trackIconInactiveEndMutated = false;
        updateTrackIconInactiveEnd();
        invalidate();
    }

    private void updateTrackIconInactiveEnd() {
        if (this.trackIconInactiveEnd != null) {
            if (!this.trackIconInactiveEndMutated && this.trackIconInactiveColor != null) {
                this.trackIconInactiveEnd = DrawableCompat.wrap(this.trackIconInactiveEnd).mutate();
                this.trackIconInactiveEndMutated = true;
            }
            if (this.trackIconInactiveEndMutated) {
                this.trackIconInactiveEnd.setTintList(this.trackIconInactiveColor);
            }
        }
    }

    public void setTrackIconInactiveEnd(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconInactiveEnd(icon);
    }

    public Drawable getTrackIconInactiveEnd() {
        return this.trackIconInactiveEnd;
    }

    public void setTrackIconInactiveColor(ColorStateList color) {
        if (color == this.trackIconInactiveColor) {
            return;
        }
        this.trackIconInactiveColor = color;
        updateTrackIconInactiveStart();
        updateTrackIconInactiveEnd();
        invalidate();
    }

    public ColorStateList getTrackIconInactiveColor() {
        return this.trackIconInactiveColor;
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        ViewOverlay contentViewOverlay;
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0 || (contentViewOverlay = getContentViewOverlay()) == null) {
            return;
        }
        for (TooltipDrawable label : this.labels) {
            contentViewOverlay.remove(label);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ViewOverlay getContentViewOverlay() {
        View contentView = ViewUtils.getContentView(this);
        if (contentView == null) {
            return null;
        }
        return contentView.getOverlay();
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setLayerType(enabled ? 0 : 2, null);
    }

    public void setOrientation(int orientation) {
        if (this.widgetOrientation == orientation) {
            return;
        }
        this.widgetOrientation = orientation;
        updateWidgetLayout(true);
    }

    public void setCentered(boolean isCentered) {
        if (this.centered == isCentered) {
            return;
        }
        this.centered = isCentered;
        if (isCentered) {
            setValues(Float.valueOf((this.valueFrom + this.valueTo) / 2.0f));
        } else {
            setValues(Float.valueOf(this.valueFrom));
        }
        updateWidgetLayout(true);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.thisAndAncestorsVisible = isShown();
        getViewTreeObserver().addOnScrollChangedListener(this.onScrollChangedListener);
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
        for (TooltipDrawable label : this.labels) {
            attachLabelToContentView(label);
        }
    }

    private void attachLabelToContentView(TooltipDrawable label) {
        label.setRelativeToView(ViewUtils.getContentView(this));
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.accessibilityEventSender != null) {
            removeCallbacks(this.accessibilityEventSender);
        }
        this.labelsAreAnimatedIn = false;
        for (TooltipDrawable label : this.labels) {
            detachLabelFromContentView(label);
        }
        getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        super.onDetachedFromWindow();
    }

    private void detachLabelFromContentView(TooltipDrawable label) {
        View contentView = ViewUtils.getContentView(this);
        if (contentView == null) {
            return;
        }
        contentView.getOverlay().remove(label);
        label.detachView(contentView);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int labelSize = 0;
        if (this.labelBehavior == 1 || shouldAlwaysShowLabel()) {
            labelSize = this.labels.get(0).getIntrinsicHeight();
        }
        int spec = View.MeasureSpec.makeMeasureSpec(this.widgetThickness + labelSize, BasicMeasure.EXACTLY);
        if (isVertical()) {
            super.onMeasure(spec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, spec);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateTrackWidth(isVertical() ? h : w);
        updateHaloHotspot();
    }

    private void updateTicksCoordinates() {
        int tickCount;
        validateConfigurationIfDirty();
        if (this.stepSize <= 0.0f) {
            updateTicksCoordinates(0);
            return;
        }
        switch (this.tickVisibilityMode) {
            case 0:
                tickCount = Math.min(getDesiredTickCount(), getMaxTickCount());
                break;
            case 1:
                int desiredTickCount = getDesiredTickCount();
                int tickCount2 = desiredTickCount <= getMaxTickCount() ? desiredTickCount : 0;
                tickCount = tickCount2;
                break;
            case 2:
                tickCount = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected tickVisibilityMode: " + this.tickVisibilityMode);
        }
        updateTicksCoordinates(tickCount);
    }

    private void updateTicksCoordinates(int tickCount) {
        if (tickCount == 0) {
            this.ticksCoordinates = null;
            return;
        }
        if (this.ticksCoordinates == null || this.ticksCoordinates.length != tickCount * 2) {
            this.ticksCoordinates = new float[tickCount * 2];
        }
        float interval = this.trackWidth / (tickCount - 1);
        float trackCenterY = calculateTrackCenter();
        for (int i = 0; i < tickCount * 2; i += 2) {
            this.ticksCoordinates[i] = this.trackSidePadding + ((i / 2.0f) * interval);
            this.ticksCoordinates[i + 1] = trackCenterY;
        }
        if (isVertical()) {
            this.rotationMatrix.mapPoints(this.ticksCoordinates);
        }
    }

    private int getDesiredTickCount() {
        return (int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f);
    }

    private int getMaxTickCount() {
        return (this.trackWidth / this.minTickSpacing) + 1;
    }

    private void updateTrackWidth(int width) {
        this.trackWidth = Math.max(width - (this.trackSidePadding * 2), 0);
        updateTicksCoordinates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                float x = (normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * this.trackWidth) + this.trackSidePadding;
                int y = calculateTrackCenter();
                float[] haloBounds = {x - this.haloRadius, y - this.haloRadius, this.haloRadius + x, this.haloRadius + y};
                if (isVertical()) {
                    this.rotationMatrix.mapPoints(haloBounds);
                }
                background.setHotspotBounds((int) haloBounds[0], (int) haloBounds[1], (int) haloBounds[2], (int) haloBounds[3]);
            }
        }
    }

    private int calculateTrackCenter() {
        return (this.widgetThickness / 2) + ((this.labelBehavior == 1 || shouldAlwaysShowLabel()) ? this.labels.get(0).getIntrinsicHeight() : 0);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            updateTicksCoordinates();
        }
        super.onDraw(canvas);
        int yCenter = calculateTrackCenter();
        drawInactiveTracks(canvas, this.trackWidth, yCenter);
        drawActiveTracks(canvas, this.trackWidth, yCenter);
        if (isRtl() || isVertical()) {
            drawTrackIcons(canvas, this.activeTrackRect, this.inactiveTrackLeftRect);
        } else {
            drawTrackIcons(canvas, this.activeTrackRect, this.inactiveTrackRightRect);
        }
        maybeDrawTicks(canvas);
        maybeDrawStopIndicator(canvas, yCenter);
        if ((this.thumbIsPressed || isFocused()) && isEnabled()) {
            maybeDrawCompatHalo(canvas, this.trackWidth, yCenter);
        }
        updateLabels();
        drawThumbs(canvas, this.trackWidth, yCenter);
    }

    private float[] getActiveRange() {
        float min = this.values.get(0).floatValue();
        float max = this.values.get(this.values.size() - 1).floatValue();
        float left = normalizeValue(this.values.size() == 1 ? this.valueFrom : min);
        float right = normalizeValue(max);
        if (isCentered()) {
            left = Math.min(0.5f, right);
            right = Math.max(0.5f, right);
        }
        if (!isCentered() && (isRtl() || isVertical())) {
            return new float[]{right, left};
        }
        return new float[]{left, right};
    }

    private void drawInactiveTracks(Canvas canvas, int width, int yCenter) {
        float[] activeRange = getActiveRange();
        float top = yCenter - (this.trackThickness / 2.0f);
        float bottom = yCenter + (this.trackThickness / 2.0f);
        drawInactiveTrackSection(this.trackSidePadding - getTrackCornerSize(), (this.trackSidePadding + (activeRange[0] * width)) - this.thumbTrackGapSize, top, bottom, canvas, this.inactiveTrackLeftRect, FullCornerDirection.LEFT);
        drawInactiveTrackSection(this.trackSidePadding + (activeRange[1] * width) + this.thumbTrackGapSize, this.trackSidePadding + width + getTrackCornerSize(), top, bottom, canvas, this.inactiveTrackRightRect, FullCornerDirection.RIGHT);
    }

    private void drawInactiveTrackSection(float from, float to, float top, float bottom, Canvas canvas, RectF rect, FullCornerDirection direction) {
        if (to - from > getTrackCornerSize() - this.thumbTrackGapSize) {
            rect.set(from, top, to, bottom);
        } else {
            rect.setEmpty();
        }
        updateTrack(canvas, this.inactiveTrackPaint, rect, getTrackCornerSize(), direction);
    }

    private float normalizeValue(float value) {
        float normalized = (value - this.valueFrom) / (this.valueTo - this.valueFrom);
        if (isRtl() || isVertical()) {
            return 1.0f - normalized;
        }
        return normalized;
    }

    private void drawActiveTracks(Canvas canvas, int width, int yCenter) {
        FullCornerDirection direction;
        float right;
        float left;
        BaseSlider<S, L, T> baseSlider = this;
        float[] activeRange = baseSlider.getActiveRange();
        float right2 = baseSlider.trackSidePadding + (activeRange[1] * width);
        float left2 = baseSlider.trackSidePadding + (activeRange[0] * width);
        if (left2 >= right2) {
            baseSlider.activeTrackRect.setEmpty();
            return;
        }
        FullCornerDirection direction2 = FullCornerDirection.NONE;
        if (baseSlider.values.size() == 1 && !baseSlider.isCentered()) {
            FullCornerDirection direction3 = (baseSlider.isRtl() || baseSlider.isVertical()) ? FullCornerDirection.RIGHT : FullCornerDirection.LEFT;
            direction = direction3;
        } else {
            direction = direction2;
        }
        int i = 0;
        while (i < baseSlider.values.size()) {
            if (baseSlider.values.size() > 1) {
                if (i > 0) {
                    left2 = baseSlider.valueToX(baseSlider.values.get(i - 1).floatValue());
                }
                right2 = baseSlider.valueToX(baseSlider.values.get(i).floatValue());
                if (baseSlider.isRtl() || baseSlider.isVertical()) {
                    float temp = left2;
                    left2 = right2;
                    right2 = temp;
                }
            }
            int trackCornerSize = baseSlider.getTrackCornerSize();
            switch (direction.ordinal()) {
                case 1:
                    right = right2 - baseSlider.thumbTrackGapSize;
                    left = left2 - trackCornerSize;
                    break;
                case 2:
                    right = right2 + trackCornerSize;
                    left = left2 + baseSlider.thumbTrackGapSize;
                    break;
                case 3:
                    if (baseSlider.isCentered()) {
                        if (activeRange[1] == 0.5f) {
                            right = right2;
                            left = left2 + baseSlider.thumbTrackGapSize;
                        } else if (activeRange[0] == 0.5f) {
                            right = right2 - baseSlider.thumbTrackGapSize;
                            left = left2;
                        }
                        break;
                    } else {
                        right = right2 - baseSlider.thumbTrackGapSize;
                        left = left2 + baseSlider.thumbTrackGapSize;
                        break;
                    }
                default:
                    right = right2;
                    left = left2;
                    break;
            }
            if (left >= right) {
                baseSlider.activeTrackRect.setEmpty();
            } else {
                baseSlider.activeTrackRect.set(left, yCenter - (baseSlider.trackThickness / 2.0f), right, yCenter + (baseSlider.trackThickness / 2.0f));
                baseSlider.updateTrack(canvas, baseSlider.activeTrackPaint, baseSlider.activeTrackRect, trackCornerSize, direction);
            }
            i++;
            baseSlider = this;
            right2 = right;
            left2 = left;
        }
    }

    private float calculateStartTrackCornerSize(float trackCornerSize) {
        if (this.values.isEmpty() || !hasGapBetweenThumbAndTrack()) {
            return trackCornerSize;
        }
        int firstIdx = (isRtl() || isVertical()) ? this.values.size() - 1 : 0;
        float currentX = valueToX(this.values.get(firstIdx).floatValue()) - this.trackSidePadding;
        if (currentX < trackCornerSize) {
            return Math.max(currentX, this.trackInsideCornerSize);
        }
        return trackCornerSize;
    }

    private float calculateEndTrackCornerSize(float trackCornerSize) {
        if (this.values.isEmpty() || !hasGapBetweenThumbAndTrack()) {
            return trackCornerSize;
        }
        int lastIdx = (isRtl() || isVertical()) ? 0 : this.values.size() - 1;
        float currentX = valueToX(this.values.get(lastIdx).floatValue()) - this.trackSidePadding;
        if (currentX > this.trackWidth - trackCornerSize) {
            return Math.max(this.trackWidth - currentX, this.trackInsideCornerSize);
        }
        return trackCornerSize;
    }

    private void drawTrackIcons(Canvas canvas, RectF activeTrackBounds, RectF inactiveTrackBounds) {
        if (!hasTrackIcons()) {
            return;
        }
        if (this.values.size() > 1) {
            Log.w(TAG, "Track icons can only be used when only 1 thumb is present.");
        }
        calculateBoundsAndDrawTrackIcon(canvas, activeTrackBounds, this.trackIconActiveStart, true);
        calculateBoundsAndDrawTrackIcon(canvas, inactiveTrackBounds, this.trackIconInactiveStart, true);
        calculateBoundsAndDrawTrackIcon(canvas, activeTrackBounds, this.trackIconActiveEnd, false);
        calculateBoundsAndDrawTrackIcon(canvas, inactiveTrackBounds, this.trackIconInactiveEnd, false);
    }

    private boolean hasTrackIcons() {
        return (this.trackIconActiveStart == null && this.trackIconActiveEnd == null && this.trackIconInactiveStart == null && this.trackIconInactiveEnd == null) ? false : true;
    }

    private void calculateBoundsAndDrawTrackIcon(Canvas canvas, RectF trackBounds, Drawable icon, boolean isStart) {
        if (icon != null) {
            calculateTrackIconBounds(trackBounds, this.iconRectF, this.trackIconSize, this.trackIconPadding, isStart);
            if (!this.iconRectF.isEmpty()) {
                drawTrackIcon(canvas, this.iconRectF, icon);
            }
        }
    }

    private void drawTrackIcon(Canvas canvas, RectF iconBounds, Drawable icon) {
        if (isVertical()) {
            this.rotationMatrix.mapRect(iconBounds);
        }
        iconBounds.round(this.iconRect);
        icon.setBounds(this.iconRect);
        icon.draw(canvas);
    }

    private void calculateTrackIconBounds(RectF trackBounds, RectF iconBounds, int iconSize, int iconPadding, boolean isStart) {
        float iconLeft;
        if (trackBounds.right - trackBounds.left >= (iconPadding * 2) + iconSize) {
            if ((isRtl() || isVertical()) ^ isStart) {
                iconLeft = trackBounds.left + iconPadding;
            } else {
                iconLeft = (trackBounds.right - iconPadding) - iconSize;
            }
            float iconTop = calculateTrackCenter() - (iconSize / 2.0f);
            float iconRight = iconSize + iconLeft;
            float iconBottom = iconSize + iconTop;
            iconBounds.set(iconLeft, iconTop, iconRight, iconBottom);
            return;
        }
        iconBounds.setEmpty();
    }

    private boolean hasGapBetweenThumbAndTrack() {
        return this.thumbTrackGapSize > 0;
    }

    private void updateTrack(Canvas canvas, Paint paint, RectF bounds, float cornerSize, FullCornerDirection direction) {
        if (bounds.isEmpty()) {
            return;
        }
        float leftCornerSize = calculateStartTrackCornerSize(cornerSize);
        float rightCornerSize = calculateEndTrackCornerSize(cornerSize);
        switch (direction) {
            case LEFT:
                rightCornerSize = this.trackInsideCornerSize;
                break;
            case RIGHT:
                leftCornerSize = this.trackInsideCornerSize;
                break;
            case NONE:
                leftCornerSize = this.trackInsideCornerSize;
                rightCornerSize = this.trackInsideCornerSize;
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.BUTT);
        if (hasGapBetweenThumbAndTrack()) {
            paint.setAntiAlias(true);
        }
        RectF rotated = new RectF(bounds);
        if (isVertical()) {
            this.rotationMatrix.mapRect(rotated);
        }
        this.trackPath.reset();
        if (bounds.width() >= leftCornerSize + rightCornerSize) {
            this.trackPath.addRoundRect(rotated, getCornerRadii(leftCornerSize, rightCornerSize), Path.Direction.CW);
            canvas.drawPath(this.trackPath, paint);
            return;
        }
        float minCornerSize = Math.min(leftCornerSize, rightCornerSize);
        float maxCornerSize = Math.max(leftCornerSize, rightCornerSize);
        canvas.save();
        this.trackPath.addRoundRect(rotated, minCornerSize, minCornerSize, Path.Direction.CW);
        canvas.clipPath(this.trackPath);
        switch (direction.ordinal()) {
            case 1:
                this.cornerRect.set(bounds.left, bounds.top, bounds.left + (2.0f * maxCornerSize), bounds.bottom);
                break;
            case 2:
                this.cornerRect.set(bounds.right - (2.0f * maxCornerSize), bounds.top, bounds.right, bounds.bottom);
                break;
            default:
                this.cornerRect.set(bounds.centerX() - maxCornerSize, bounds.top, bounds.centerX() + maxCornerSize, bounds.bottom);
                break;
        }
        if (isVertical()) {
            this.rotationMatrix.mapRect(this.cornerRect);
        }
        canvas.drawRoundRect(this.cornerRect, maxCornerSize, maxCornerSize, paint);
        canvas.restore();
    }

    private float[] getCornerRadii(float leftSide, float rightSide) {
        if (isVertical()) {
            return new float[]{leftSide, leftSide, leftSide, leftSide, rightSide, rightSide, rightSide, rightSide};
        }
        return new float[]{leftSide, leftSide, rightSide, rightSide, rightSide, rightSide, leftSide, leftSide};
    }

    private void maybeDrawTicks(Canvas canvas) {
        if (this.ticksCoordinates == null || this.ticksCoordinates.length == 0) {
            return;
        }
        float[] activeRange = getActiveRange();
        int leftActiveTickIndex = (int) Math.ceil(activeRange[0] * ((this.ticksCoordinates.length / 2.0f) - 1.0f));
        int rightActiveTickIndex = (int) Math.floor(activeRange[1] * ((this.ticksCoordinates.length / 2.0f) - 1.0f));
        if (leftActiveTickIndex > 0) {
            drawTicks(0, leftActiveTickIndex * 2, canvas, this.inactiveTicksPaint);
        }
        if (leftActiveTickIndex <= rightActiveTickIndex) {
            drawTicks(leftActiveTickIndex * 2, (rightActiveTickIndex + 1) * 2, canvas, this.activeTicksPaint);
        }
        if ((rightActiveTickIndex + 1) * 2 < this.ticksCoordinates.length) {
            drawTicks((rightActiveTickIndex + 1) * 2, this.ticksCoordinates.length, canvas, this.inactiveTicksPaint);
        }
    }

    private void drawTicks(int from, int to, Canvas canvas, Paint paint) {
        for (int i = from; i < to; i += 2) {
            float coordinateToCheck = isVertical() ? this.ticksCoordinates[i + 1] : this.ticksCoordinates[i];
            if (!isOverlappingThumb(coordinateToCheck) && (!isCentered() || !isOverlappingCenterGap(coordinateToCheck))) {
                canvas.drawPoint(this.ticksCoordinates[i], this.ticksCoordinates[i + 1], paint);
            }
        }
    }

    private boolean isOverlappingThumb(float tickCoordinate) {
        float threshold = this.thumbTrackGapSize + (this.thumbWidth / 2.0f);
        Iterator<Float> it = this.values.iterator();
        if (!it.hasNext()) {
            return false;
        }
        float value = it.next().floatValue();
        float valueToX = valueToX(value);
        return tickCoordinate >= valueToX - threshold && tickCoordinate <= valueToX + threshold;
    }

    private boolean isOverlappingCenterGap(float tickCoordinate) {
        float threshold = this.thumbTrackGapSize + (this.thumbWidth / 2.0f);
        float trackCenter = (this.trackWidth + (this.trackSidePadding * 2)) / 2.0f;
        return tickCoordinate >= trackCenter - threshold && tickCoordinate <= trackCenter + threshold;
    }

    private void maybeDrawStopIndicator(Canvas canvas, int yCenter) {
        if (this.trackStopIndicatorSize <= 0 || this.values.isEmpty()) {
            return;
        }
        if (this.values.get(this.values.size() - 1).floatValue() < this.valueTo) {
            drawStopIndicator(canvas, valueToX(this.valueTo), yCenter);
        }
        if (isCentered() || (this.values.size() > 1 && this.values.get(0).floatValue() > this.valueFrom)) {
            drawStopIndicator(canvas, valueToX(this.valueFrom), yCenter);
        }
    }

    private void drawStopIndicator(Canvas canvas, float x, float y) {
        Iterator<Float> it = this.values.iterator();
        while (it.hasNext()) {
            float value = it.next().floatValue();
            float valueToX = valueToX(value);
            float threshold = this.thumbTrackGapSize + (this.thumbWidth / 2.0f);
            if (x >= valueToX - threshold && x <= valueToX + threshold) {
                return;
            }
        }
        if (isVertical()) {
            canvas.drawPoint(y, x, this.stopIndicatorPaint);
        } else {
            canvas.drawPoint(x, y, this.stopIndicatorPaint);
        }
    }

    private void drawThumbs(Canvas canvas, int width, int yCenter) {
        Canvas canvas2;
        int width2;
        int yCenter2;
        int i = 0;
        while (i < this.values.size()) {
            float value = this.values.get(i).floatValue();
            if (this.customThumbDrawable != null) {
                canvas2 = canvas;
                width2 = width;
                yCenter2 = yCenter;
                drawThumbDrawable(canvas2, width2, yCenter2, value, this.customThumbDrawable);
            } else {
                canvas2 = canvas;
                width2 = width;
                yCenter2 = yCenter;
                if (i < this.customThumbDrawablesForValues.size()) {
                    drawThumbDrawable(canvas2, width2, yCenter2, value, this.customThumbDrawablesForValues.get(i));
                } else {
                    if (!isEnabled()) {
                        canvas2.drawCircle(this.trackSidePadding + (normalizeValue(value) * width2), yCenter2, getThumbRadius(), this.thumbPaint);
                    }
                    drawThumbDrawable(canvas2, width2, yCenter2, value, this.defaultThumbDrawable);
                }
            }
            i++;
            canvas = canvas2;
            width = width2;
            yCenter = yCenter2;
        }
    }

    private void drawThumbDrawable(Canvas canvas, int width, int top, float value, Drawable thumbDrawable) {
        canvas.save();
        if (isVertical()) {
            canvas.concat(this.rotationMatrix);
        }
        canvas.translate((this.trackSidePadding + ((int) (normalizeValue(value) * width))) - (thumbDrawable.getBounds().width() / 2.0f), top - (thumbDrawable.getBounds().height() / 2.0f));
        thumbDrawable.draw(canvas);
        canvas.restore();
    }

    private void maybeDrawCompatHalo(Canvas canvas, int width, int top) {
        Canvas canvas2;
        if (shouldDrawCompatHalo()) {
            float centerX = this.trackSidePadding + (normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * width);
            float[] bounds = {centerX, top};
            if (isVertical()) {
                this.rotationMatrix.mapPoints(bounds);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                canvas2 = canvas;
            } else {
                canvas2 = canvas;
                canvas2.clipRect(bounds[0] - this.haloRadius, bounds[1] - this.haloRadius, bounds[0] + this.haloRadius, bounds[1] + this.haloRadius, Region.Op.UNION);
            }
            canvas2.drawCircle(bounds[0], bounds[1], this.haloRadius, this.haloPaint);
        }
    }

    private boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || !(getBackground() instanceof RippleDrawable);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        float eventCoordinateAxis1 = isVertical() ? event.getY() : event.getX();
        float eventCoordinateAxis2 = isVertical() ? event.getX() : event.getY();
        this.touchPosition = (eventCoordinateAxis1 - this.trackSidePadding) / this.trackWidth;
        this.touchPosition = Math.max(0.0f, this.touchPosition);
        this.touchPosition = Math.min(1.0f, this.touchPosition);
        switch (event.getActionMasked()) {
            case 0:
                this.touchDownAxis1 = eventCoordinateAxis1;
                this.touchDownAxis2 = eventCoordinateAxis2;
                if ((isVertical() || !isPotentialVerticalScroll(event)) && (!isVertical() || !isPotentialHorizontalScroll(event))) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        requestFocus();
                        this.thumbIsPressed = true;
                        updateThumbWidthWhenPressed();
                        onStartTrackingTouch();
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                    }
                }
                break;
            case 1:
            case 3:
                this.thumbIsPressed = false;
                if (this.lastEvent != null && this.lastEvent.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - event.getX()) <= this.scaledTouchSlop && Math.abs(this.lastEvent.getY() - event.getY()) <= this.scaledTouchSlop && pickActiveThumb()) {
                    onStartTrackingTouch();
                }
                if (this.activeThumbIdx != -1) {
                    snapTouchPosition();
                    updateHaloHotspot();
                    if (hasGapBetweenThumbAndTrack() && this.defaultThumbWidth != -1 && this.defaultThumbTrackGapSize != -1) {
                        setThumbWidth(this.defaultThumbWidth);
                        setThumbTrackGapSize(this.defaultThumbTrackGapSize);
                    }
                    this.activeThumbIdx = -1;
                    onStopTrackingTouch();
                }
                invalidate();
                break;
            case 2:
                if (!this.thumbIsPressed) {
                    if (!isVertical() && isPotentialVerticalScroll(event) && Math.abs(eventCoordinateAxis1 - this.touchDownAxis1) < this.scaledTouchSlop) {
                        return false;
                    }
                    if (isVertical() && isPotentialHorizontalScroll(event) && Math.abs(eventCoordinateAxis2 - this.touchDownAxis2) < this.scaledTouchSlop * TOUCH_SLOP_RATIO) {
                        return false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        this.thumbIsPressed = true;
                        updateThumbWidthWhenPressed();
                        onStartTrackingTouch();
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                    }
                } else {
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                }
                break;
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(event);
        return true;
    }

    private void updateThumbWidthWhenPressed() {
        if (hasGapBetweenThumbAndTrack()) {
            this.defaultThumbWidth = this.thumbWidth;
            this.defaultThumbTrackGapSize = this.thumbTrackGapSize;
            int pressedThumbWidth = Math.round(this.thumbWidth * 0.5f);
            int delta = this.thumbWidth - pressedThumbWidth;
            setThumbWidth(pressedThumbWidth);
            setThumbTrackGapSize(this.thumbTrackGapSize - (delta / 2));
        }
    }

    private double snapPosition(float position) {
        if (this.stepSize > 0.0f) {
            int stepCount = (int) ((this.valueTo - this.valueFrom) / this.stepSize);
            return ((double) Math.round(stepCount * position)) / ((double) stepCount);
        }
        return position;
    }

    protected boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float touchValue = getValueOfTouchPositionAbsolute();
        float touchX = valueToX(touchValue);
        this.activeThumbIdx = 0;
        float activeThumbDiff = Math.abs(this.values.get(this.activeThumbIdx).floatValue() - touchValue);
        for (int i = 1; i < this.values.size(); i++) {
            float valueDiff = Math.abs(this.values.get(i).floatValue() - touchValue);
            float valueX = valueToX(this.values.get(i).floatValue());
            if (Float.compare(valueDiff, activeThumbDiff) > 0) {
                break;
            }
            boolean movingForward = isRtl() || isVertical() ? valueX - touchX > 0.0f : valueX - touchX < 0.0f;
            if (Float.compare(valueDiff, activeThumbDiff) < 0) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            } else if (Float.compare(valueDiff, activeThumbDiff) != 0) {
                continue;
            } else {
                if (Math.abs(valueX - touchX) < this.scaledTouchSlop) {
                    this.activeThumbIdx = -1;
                    return false;
                }
                if (movingForward) {
                    activeThumbDiff = valueDiff;
                    this.activeThumbIdx = i;
                }
            }
        }
        int i2 = this.activeThumbIdx;
        return i2 != -1;
    }

    private float getValueOfTouchPositionAbsolute() {
        float position = this.touchPosition;
        if (isRtl() || isVertical()) {
            position = 1.0f - position;
        }
        return ((this.valueTo - this.valueFrom) * position) + this.valueFrom;
    }

    private boolean snapTouchPosition() {
        return snapActiveThumbToValue(getValueOfTouchPosition());
    }

    private boolean snapActiveThumbToValue(float value) {
        return snapThumbToValue(this.activeThumbIdx, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean snapThumbToValue(int idx, float value) {
        this.focusedThumbIdx = idx;
        if (Math.abs(value - this.values.get(idx).floatValue()) < THRESHOLD) {
            return false;
        }
        float newValue = getClampedValue(idx, value);
        this.values.set(idx, Float.valueOf(newValue));
        dispatchOnChangedFromUser(idx);
        return true;
    }

    private float getClampedValue(int idx, float value) {
        float minSeparation = getMinSeparation();
        float minSeparation2 = this.separationUnit == 0 ? dimenToValue(minSeparation) : minSeparation;
        if (isRtl() || isVertical()) {
            minSeparation2 = -minSeparation2;
        }
        float upperBound = idx + 1 >= this.values.size() ? this.valueTo : this.values.get(idx + 1).floatValue() - minSeparation2;
        float lowerBound = idx + (-1) < 0 ? this.valueFrom : this.values.get(idx - 1).floatValue() + minSeparation2;
        return MathUtils.clamp(value, lowerBound, upperBound);
    }

    private float dimenToValue(float dimen) {
        if (dimen == 0.0f) {
            return 0.0f;
        }
        return (((dimen - this.trackSidePadding) / this.trackWidth) * (this.valueFrom - this.valueTo)) + this.valueFrom;
    }

    protected void setSeparationUnit(int separationUnit) {
        this.separationUnit = separationUnit;
        this.dirtyConfig = true;
        postInvalidate();
    }

    protected float getMinSeparation() {
        return 0.0f;
    }

    private float getValueOfTouchPosition() {
        double position = snapPosition(this.touchPosition);
        if (isRtl() || isVertical()) {
            position = 1.0d - position;
        }
        return (float) ((((double) (this.valueTo - this.valueFrom)) * position) + ((double) this.valueFrom));
    }

    private float valueToX(float value) {
        return (normalizeValue(value) * this.trackWidth) + this.trackSidePadding;
    }

    private static float getAnimatorCurrentValueOrDefault(ValueAnimator animator, float defaultValue) {
        if (animator != null && animator.isRunning()) {
            float value = ((Float) animator.getAnimatedValue()).floatValue();
            animator.cancel();
            return value;
        }
        return defaultValue;
    }

    private ValueAnimator createLabelAnimator(boolean enter) {
        int duration;
        TimeInterpolator interpolator;
        float startFraction = enter ? 0.0f : 1.0f;
        float startFraction2 = getAnimatorCurrentValueOrDefault(enter ? this.labelsOutAnimator : this.labelsInAnimator, startFraction);
        float endFraction = enter ? 1.0f : 0.0f;
        ValueAnimator animator = ValueAnimator.ofFloat(startFraction2, endFraction);
        if (enter) {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_ENTER_DURATION_ATTR, DEFAULT_LABEL_ANIMATION_ENTER_DURATION);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_ENTER_EASING_ATTR, AnimationUtils.DECELERATE_INTERPOLATOR);
        } else {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_EXIT_DURATION_ATTR, DEFAULT_LABEL_ANIMATION_EXIT_DURATION);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_EXIT_EASING_ATTR, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        }
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m240x2eeddb89(valueAnimator);
            }
        });
        return animator;
    }

    /* JADX INFO: renamed from: lambda$createLabelAnimator$1$com-google-android-material-slider-BaseSlider, reason: not valid java name */
    /* synthetic */ void m240x2eeddb89(ValueAnimator animation) {
        float fraction = ((Float) animation.getAnimatedValue()).floatValue();
        for (TooltipDrawable label : this.labels) {
            label.setRevealFraction(fraction);
        }
        postInvalidateOnAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLabels() {
        updateLabelPivots();
        switch (this.labelBehavior) {
            case 0:
            case 1:
                if (this.activeThumbIdx != -1 && isEnabled()) {
                    ensureLabelsAdded();
                    return;
                } else {
                    ensureLabelsRemoved();
                    return;
                }
            case 2:
                ensureLabelsRemoved();
                return;
            case 3:
                if (isEnabled() && isSliderVisibleOnScreen()) {
                    ensureLabelsAdded();
                    return;
                } else {
                    ensureLabelsRemoved();
                    return;
                }
            default:
                throw new IllegalArgumentException("Unexpected labelBehavior: " + this.labelBehavior);
        }
    }

    private void updateLabelPivots() {
        float labelPivotX;
        float labelPivotY;
        boolean isVertical = isVertical();
        boolean isRtl = isRtl();
        if (isVertical && isRtl) {
            labelPivotX = RIGHT_LABEL_PIVOT_X;
            labelPivotY = 0.5f;
        } else if (isVertical) {
            labelPivotX = 1.2f;
            labelPivotY = 0.5f;
        } else {
            labelPivotX = 0.5f;
            labelPivotY = 1.2f;
        }
        for (TooltipDrawable label : this.labels) {
            label.setPivots(labelPivotX, labelPivotY);
        }
    }

    private boolean isSliderVisibleOnScreen() {
        Rect contentViewBounds = new Rect();
        ViewUtils.getContentView(this).getHitRect(contentViewBounds);
        return getLocalVisibleRect(contentViewBounds) && isThisAndAncestorsVisible();
    }

    private boolean isThisAndAncestorsVisible() {
        return this.thisAndAncestorsVisible;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setVisibleToUser(false);
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        this.thisAndAncestorsVisible = isVisible;
    }

    private void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            this.labelsOutAnimator = createLabelAnimator(false);
            this.labelsInAnimator = null;
            this.labelsOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.slider.BaseSlider.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ViewOverlay contentViewOverlay = BaseSlider.this.getContentViewOverlay();
                    if (contentViewOverlay != null) {
                        for (TooltipDrawable label : BaseSlider.this.labels) {
                            contentViewOverlay.remove(label);
                        }
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    private void ensureLabelsAdded() {
        if (!this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = true;
            this.labelsInAnimator = createLabelAnimator(true);
            this.labelsOutAnimator = null;
            this.labelsInAnimator.start();
        }
        Iterator<TooltipDrawable> labelItr = this.labels.iterator();
        for (int i = 0; i < this.values.size() && labelItr.hasNext(); i++) {
            if (i != this.focusedThumbIdx) {
                setValueForLabel(labelItr.next(), this.values.get(i).floatValue());
            }
        }
        if (!labelItr.hasNext()) {
            throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size())));
        }
        setValueForLabel(labelItr.next(), this.values.get(this.focusedThumbIdx).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatValue(float value) {
        if (hasLabelFormatter()) {
            return this.formatter.getFormattedValue(value);
        }
        return String.format(((float) ((int) value)) == value ? "%.0f" : "%.2f", Float.valueOf(value));
    }

    private void setValueForLabel(TooltipDrawable label, float value) {
        label.setText(formatValue(value));
        positionLabel(label, value);
        ViewOverlay contentViewOverlay = getContentViewOverlay();
        if (contentViewOverlay == null) {
            return;
        }
        contentViewOverlay.add(label);
    }

    private void positionLabel(TooltipDrawable label, float value) {
        calculateLabelBounds(label, value);
        if (isVertical()) {
            RectF labelBounds = new RectF(this.labelRect);
            this.rotationMatrix.mapRect(labelBounds);
            labelBounds.round(this.labelRect);
        }
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, this.labelRect);
        label.setBounds(this.labelRect);
    }

    private void calculateLabelBounds(TooltipDrawable label, float value) {
        int left;
        int right;
        int bottom;
        int top;
        if (isVertical()) {
            left = (this.trackSidePadding + ((int) (normalizeValue(value) * this.trackWidth))) - (label.getIntrinsicHeight() / 2);
            right = label.getIntrinsicHeight() + left;
            if (isRtl()) {
                bottom = calculateTrackCenter() - (this.labelPadding + (this.thumbHeight / 2));
                top = bottom - label.getIntrinsicWidth();
            } else {
                top = this.labelPadding + (this.thumbHeight / 2) + calculateTrackCenter();
                bottom = label.getIntrinsicWidth() + top;
            }
        } else {
            left = (this.trackSidePadding + ((int) (normalizeValue(value) * this.trackWidth))) - (label.getIntrinsicWidth() / 2);
            right = label.getIntrinsicWidth() + left;
            bottom = calculateTrackCenter() - (this.labelPadding + (this.thumbHeight / 2));
            top = bottom - label.getIntrinsicHeight();
        }
        this.labelRect.set(left, top, right, bottom);
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth(this.trackThickness);
        this.activeTrackPaint.setStrokeWidth(this.trackThickness);
    }

    private boolean isInVerticalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            boolean canScrollVertically = parent.canScrollVertically(1) || parent.canScrollVertically(-1);
            if (canScrollVertically && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private boolean isInHorizontalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            boolean canScrollHorizontally = parent.canScrollHorizontally(1) || parent.canScrollHorizontally(-1);
            if (canScrollHorizontally && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private static boolean isMouseEvent(MotionEvent event) {
        return event.getToolType(0) == 3;
    }

    private boolean isPotentialVerticalScroll(MotionEvent event) {
        return !isMouseEvent(event) && isInVerticalScrollingContainer();
    }

    private boolean isPotentialHorizontalScroll(MotionEvent event) {
        return !isMouseEvent(event) && isInHorizontalScrollingContainer();
    }

    private void dispatchOnChangedProgrammatically() {
        for (L listener : this.changeListeners) {
            for (Float value : this.values) {
                listener.onValueChange(this, value.floatValue(), false);
            }
        }
    }

    private void dispatchOnChangedFromUser(int idx) {
        for (L listener : this.changeListeners) {
            listener.onValueChange(this, this.values.get(idx).floatValue(), true);
        }
        if (this.accessibilityManager != null && this.accessibilityManager.isEnabled()) {
            scheduleAccessibilityEventSender(idx);
        }
    }

    private void onStartTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStopTrackingTouch(this);
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        this.stopIndicatorPaint.setColor(getColorForState(this.tickColorInactive));
        for (TooltipDrawable label : this.labels) {
            if (label.isStateful()) {
                label.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    void forceDrawCompatHalo(boolean force) {
        this.forceDrawCompatHalo = force;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isEnabled()) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        if (this.activeThumbIdx == -1) {
            Boolean handled = onKeyDownNoActiveThumb(keyCode, event);
            return handled != null ? handled.booleanValue() : super.onKeyDown(keyCode, event);
        }
        this.isLongPress |= event.isLongPress();
        Float increment = calculateIncrementForKey(keyCode);
        if (increment != null) {
            if (snapActiveThumbToValue(this.values.get(this.activeThumbIdx).floatValue() + increment.floatValue())) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        switch (keyCode) {
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                this.activeThumbIdx = -1;
                postInvalidate();
                break;
            case LockFreeTaskQueueCore.CLOSED_SHIFT /* 61 */:
                if (!event.hasNoModifiers()) {
                    if (event.isShiftPressed()) {
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Boolean onKeyDownNoActiveThumb(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                moveFocusInAbsoluteDirection(-1);
                break;
            case 22:
                moveFocusInAbsoluteDirection(1);
                break;
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                break;
            case LockFreeTaskQueueCore.CLOSED_SHIFT /* 61 */:
                if (!event.hasNoModifiers()) {
                    if (!event.isShiftPressed()) {
                    }
                }
                break;
            case 69:
                moveFocus(-1);
                break;
            case 70:
            case 81:
                moveFocus(1);
                break;
        }
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        this.isLongPress = false;
        return super.onKeyUp(keyCode, event);
    }

    final boolean isRtl() {
        return getLayoutDirection() == 1;
    }

    public boolean isVertical() {
        return this.widgetOrientation == 1;
    }

    public boolean isCentered() {
        return this.centered;
    }

    private boolean moveFocus(int direction) {
        int oldFocusedThumbIdx = this.focusedThumbIdx;
        long newFocusedThumbIdx = ((long) oldFocusedThumbIdx) + ((long) direction);
        this.focusedThumbIdx = (int) MathUtils.clamp(newFocusedThumbIdx, 0L, this.values.size() - 1);
        if (this.focusedThumbIdx == oldFocusedThumbIdx) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = this.focusedThumbIdx;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    private boolean moveFocusInAbsoluteDirection(int direction) {
        if (isRtl() || isVertical()) {
            direction = direction == Integer.MIN_VALUE ? Integer.MAX_VALUE : -direction;
        }
        return moveFocus(direction);
    }

    private Float calculateIncrementForKey(int keyCode) {
        float increment = this.isLongPress ? calculateStepIncrement(20) : calculateStepIncrement();
        switch (keyCode) {
            case 19:
                if (isVertical()) {
                    return Float.valueOf(increment);
                }
                return null;
            case 20:
                if (isVertical()) {
                    return Float.valueOf(-increment);
                }
                return null;
            case 21:
                return Float.valueOf(isRtl() ? increment : -increment);
            case 22:
                return Float.valueOf(isRtl() ? -increment : increment);
            case 69:
                return Float.valueOf(-increment);
            case 70:
            case 81:
                return Float.valueOf(increment);
            default:
                return null;
        }
    }

    private float calculateStepIncrement() {
        if (this.stepSize == 0.0f) {
            return 1.0f;
        }
        return this.stepSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateStepIncrement(int stepFactor) {
        float increment = calculateStepIncrement();
        float numSteps = (this.valueTo - this.valueFrom) / increment;
        if (numSteps <= stepFactor) {
            return increment;
        }
        return Math.round(numSteps / stepFactor) * increment;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!gainFocus) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
        } else {
            focusThumbOnFocusGained(direction);
            this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
        }
    }

    private void focusThumbOnFocusGained(int direction) {
        switch (direction) {
            case 1:
                moveFocus(Integer.MAX_VALUE);
                break;
            case 2:
                moveFocus(Integer.MIN_VALUE);
                break;
            case 17:
                moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
                break;
        }
    }

    final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.accessibilityHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void scheduleAccessibilityEventSender(int idx) {
        if (this.accessibilityEventSender == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            removeCallbacks(this.accessibilityEventSender);
        }
        this.accessibilityEventSender.setVirtualViewId(idx);
        postDelayed(this.accessibilityEventSender, 200L);
    }

    private class AccessibilityEventSender implements Runnable {
        int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        void setVirtualViewId(int virtualViewId) {
            this.virtualViewId = virtualViewId;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SliderState sliderState = new SliderState(superState);
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable state) {
        SliderState sliderState = (SliderState) state;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    static class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>() { // from class: com.google.android.material.slider.BaseSlider.SliderState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SliderState createFromParcel(Parcel source) {
                return new SliderState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SliderState[] newArray(int size) {
                return new SliderState[size];
            }
        };
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList<Float> values;

        SliderState(Parcelable superState) {
            super(superState);
        }

        private SliderState(Parcel source) {
            super(source);
            this.valueFrom = source.readFloat();
            this.valueTo = source.readFloat();
            this.values = new ArrayList<>();
            source.readList(this.values, Float.class.getClassLoader());
            this.stepSize = source.readFloat();
            this.hasFocus = source.createBooleanArray()[0];
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(this.valueFrom);
            dest.writeFloat(this.valueTo);
            dest.writeList(this.values);
            dest.writeFloat(this.stepSize);
            boolean[] booleans = {this.hasFocus};
            dest.writeBooleanArray(booleans);
        }
    }

    void updateBoundsForVirtualViewId(int virtualViewId, Rect virtualViewBounds) {
        int x = this.trackSidePadding + ((int) (normalizeValue(getValues().get(virtualViewId).floatValue()) * this.trackWidth));
        int y = calculateTrackCenter();
        int touchTargetOffsetX = Math.max(this.thumbWidth / 2, this.minTouchTargetSize / 2);
        int touchTargetOffsetY = Math.max(this.thumbHeight / 2, this.minTouchTargetSize / 2);
        RectF rect = new RectF(x - touchTargetOffsetX, y - touchTargetOffsetY, x + touchTargetOffsetX, y + touchTargetOffsetY);
        if (isVertical()) {
            this.rotationMatrix.mapRect(rect);
        }
        virtualViewBounds.set((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
    }

    public static class AccessibilityHelper extends ExploreByTouchHelper {
        private final BaseSlider<?, ?, ?> slider;
        final Rect virtualViewBounds;

        AccessibilityHelper(BaseSlider<?, ?, ?> slider) {
            super(slider);
            this.virtualViewBounds = new Rect();
            this.slider = slider;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float x, float y) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                this.slider.updateBoundsForVirtualViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) x, (int) y)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                virtualViewIds.add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat info) {
            info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> values = this.slider.getValues();
            float value = values.get(virtualViewId).floatValue();
            float valueFrom = this.slider.getValueFrom();
            float valueTo = this.slider.getValueTo();
            if (this.slider.isEnabled()) {
                if (value > valueFrom) {
                    info.addAction(8192);
                }
                if (value < valueTo) {
                    info.addAction(4096);
                }
            }
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            try {
                valueFrom = nf.parse(nf.format(valueFrom)).floatValue();
                valueTo = nf.parse(nf.format(valueTo)).floatValue();
                value = nf.parse(nf.format(value)).floatValue();
            } catch (ParseException e) {
                Log.w(BaseSlider.TAG, String.format(BaseSlider.WARNING_PARSE_ERROR, Float.valueOf(value), Float.valueOf(valueFrom), Float.valueOf(valueTo)));
            }
            info.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, valueFrom, valueTo, value));
            info.setClassName(SeekBar.class.getName());
            StringBuilder contentDescription = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                contentDescription.append(this.slider.getContentDescription()).append(",");
            }
            String verbalValue = this.slider.formatValue(value);
            String verbalValueType = this.slider.getContext().getString(R.string.material_slider_value);
            if (values.size() > 1) {
                verbalValueType = startOrEndDescription(virtualViewId);
            }
            CharSequence stateDescription = ViewCompat.getStateDescription(this.slider);
            if (!TextUtils.isEmpty(stateDescription)) {
                info.setStateDescription(stateDescription);
            } else {
                contentDescription.append(String.format(Locale.getDefault(), "%s, %s", verbalValueType, verbalValue));
            }
            info.setContentDescription(contentDescription.toString());
            this.slider.updateBoundsForVirtualViewId(virtualViewId, this.virtualViewBounds);
            info.setBoundsInParent(this.virtualViewBounds);
        }

        private String startOrEndDescription(int virtualViewId) {
            List<Float> values = this.slider.getValues();
            if (virtualViewId == values.size() - 1) {
                return this.slider.getContext().getString(R.string.material_slider_range_end);
            }
            if (virtualViewId == 0) {
                return this.slider.getContext().getString(R.string.material_slider_range_start);
            }
            return "";
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (!this.slider.isEnabled()) {
                return false;
            }
            switch (action) {
                case 4096:
                case 8192:
                    float increment = this.slider.calculateStepIncrement(20);
                    if (action == 8192) {
                        increment = -increment;
                    }
                    if (this.slider.isRtl()) {
                        increment = -increment;
                    }
                    List<Float> values = this.slider.getValues();
                    float clamped = MathUtils.clamp(values.get(virtualViewId).floatValue() + increment, this.slider.getValueFrom(), this.slider.getValueTo());
                    if (this.slider.snapThumbToValue(virtualViewId, clamped)) {
                        this.slider.setActiveThumbIndex(virtualViewId);
                        this.slider.scheduleTooltipTimeout();
                        this.slider.updateHaloHotspot();
                        this.slider.postInvalidate();
                        invalidateVirtualView(virtualViewId);
                    }
                    break;
                case android.R.id.accessibilityActionSetProgress:
                    if (arguments != null && arguments.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                        float value = arguments.getFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE);
                        if (this.slider.snapThumbToValue(virtualViewId, value)) {
                            this.slider.updateHaloHotspot();
                            this.slider.postInvalidate();
                            invalidateVirtualView(virtualViewId);
                        }
                        break;
                    }
                    break;
            }
            return false;
        }
    }
}
