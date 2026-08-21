package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class CollapsingToolbarLayout extends FrameLayout {
    private static final int COLLAPSED_TITLE_GRAVITY_AVAILABLE_SPACE = 1;
    private static final int COLLAPSED_TITLE_GRAVITY_ENTIRE_SPACE = 0;
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_CollapsingToolbar;
    public static final int TITLE_COLLAPSE_MODE_FADE = 1;
    public static final int TITLE_COLLAPSE_MODE_SCALE = 0;
    private final int collapsedTitleGravityMode;
    final CollapsingTextHelper collapsingSubtitleHelper;
    private boolean collapsingTitleEnabled;
    final CollapsingTextHelper collapsingTitleHelper;
    private Drawable contentScrim;
    int currentOffset;
    private boolean drawCollapsingTitle;
    private View dummyView;
    final ElevationOverlayProvider elevationOverlayProvider;
    private int expandedMarginBottom;
    private int expandedMarginEnd;
    private int expandedMarginStart;
    private int expandedMarginTop;
    private int expandedTitleSpacing;
    private int extraHeightForTitles;
    private boolean extraMultilineHeightEnabled;
    private int extraMultilineSubtitleHeight;
    private int extraMultilineTitleHeight;
    private boolean forceApplySystemWindowInsetTop;
    WindowInsetsCompat lastInsets;
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
    private boolean refreshToolbar;
    private int screenOrientation;
    private int scrimAlpha;
    private long scrimAnimationDuration;
    private final TimeInterpolator scrimAnimationFadeInInterpolator;
    private final TimeInterpolator scrimAnimationFadeOutInterpolator;
    private ValueAnimator scrimAnimator;
    private int scrimVisibleHeightTrigger;
    private boolean scrimsAreShown;
    Drawable statusBarScrim;
    private int titleCollapseMode;
    private final Rect tmpRect;
    private ViewGroup toolbar;
    private View toolbarDirectChild;
    private int toolbarId;
    private int topInsetApplied;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CollapsedTitleGravityMode {
    }

    public interface StaticLayoutBuilderConfigurer extends com.google.android.material.internal.StaticLayoutBuilderConfigurer {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TitleCollapseMode {
    }

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.collapsingToolbarLayoutStyle);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.refreshToolbar = true;
        this.tmpRect = new Rect();
        this.scrimVisibleHeightTrigger = -1;
        this.topInsetApplied = 0;
        this.extraMultilineTitleHeight = 0;
        this.extraMultilineSubtitleHeight = 0;
        this.extraHeightForTitles = 0;
        Context context2 = getContext();
        this.screenOrientation = getResources().getConfiguration().orientation;
        this.collapsingTitleHelper = new CollapsingTextHelper(this);
        this.collapsingTitleHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        this.collapsingTitleHelper.setRtlTextDirectionHeuristicsEnabled(false);
        this.elevationOverlayProvider = new ElevationOverlayProvider(context2);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.CollapsingToolbarLayout, defStyleAttr, DEF_STYLE_RES, new int[0]);
        int titleExpandedGravity = a.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691);
        int titleCollapsedGravity = a.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, NavigationBarView.ITEM_GRAVITY_START_CENTER);
        this.collapsedTitleGravityMode = a.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravityMode, 1);
        this.collapsingTitleHelper.setExpandedTextGravity(titleExpandedGravity);
        this.collapsingTitleHelper.setCollapsedTextGravity(titleCollapsedGravity);
        int dimensionPixelSize = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.expandedMarginBottom = dimensionPixelSize;
        this.expandedMarginEnd = dimensionPixelSize;
        this.expandedMarginTop = dimensionPixelSize;
        this.expandedMarginStart = dimensionPixelSize;
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.expandedMarginStart = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.expandedMarginEnd = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.expandedMarginTop = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.expandedMarginBottom = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleSpacing)) {
            this.expandedTitleSpacing = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleSpacing, 0);
        }
        this.collapsingTitleEnabled = a.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        setTitle(a.getText(R.styleable.CollapsingToolbarLayout_title));
        this.collapsingTitleHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.collapsingTitleHelper.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.collapsingTitleHelper.setExpandedTextAppearance(a.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.collapsingTitleHelper.setCollapsedTextAppearance(a.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_titleTextEllipsize)) {
            setTitleEllipsize(convertEllipsizeToTruncateAt(a.getInt(R.styleable.CollapsingToolbarLayout_titleTextEllipsize, -1)));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextColor)) {
            this.collapsingTitleHelper.setExpandedTextColor(MaterialResources.getColorStateList(context2, a, R.styleable.CollapsingToolbarLayout_expandedTitleTextColor));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextColor)) {
            this.collapsingTitleHelper.setCollapsedTextColor(MaterialResources.getColorStateList(context2, a, R.styleable.CollapsingToolbarLayout_collapsedTitleTextColor));
        }
        this.scrimVisibleHeightTrigger = a.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_titleMaxLines)) {
            this.collapsingTitleHelper.setExpandedMaxLines(a.getInt(R.styleable.CollapsingToolbarLayout_titleMaxLines, 1));
        } else if (a.hasValue(R.styleable.CollapsingToolbarLayout_maxLines)) {
            this.collapsingTitleHelper.setExpandedMaxLines(a.getInt(R.styleable.CollapsingToolbarLayout_maxLines, 1));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator)) {
            this.collapsingTitleHelper.setPositionInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context2, a.getResourceId(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator, 0)));
        }
        this.collapsingSubtitleHelper = new CollapsingTextHelper(this);
        this.collapsingSubtitleHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        this.collapsingSubtitleHelper.setRtlTextDirectionHeuristicsEnabled(false);
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_subtitle)) {
            setSubtitle(a.getText(R.styleable.CollapsingToolbarLayout_subtitle));
        }
        this.collapsingSubtitleHelper.setExpandedTextGravity(titleExpandedGravity);
        this.collapsingSubtitleHelper.setCollapsedTextGravity(titleCollapsedGravity);
        this.collapsingSubtitleHelper.setExpandedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Headline);
        this.collapsingSubtitleHelper.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Subtitle);
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedSubtitleTextAppearance)) {
            this.collapsingSubtitleHelper.setExpandedTextAppearance(a.getResourceId(R.styleable.CollapsingToolbarLayout_expandedSubtitleTextAppearance, 0));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_collapsedSubtitleTextAppearance)) {
            this.collapsingSubtitleHelper.setCollapsedTextAppearance(a.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedSubtitleTextAppearance, 0));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_expandedSubtitleTextColor)) {
            this.collapsingSubtitleHelper.setExpandedTextColor(MaterialResources.getColorStateList(context2, a, R.styleable.CollapsingToolbarLayout_expandedSubtitleTextColor));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_collapsedSubtitleTextColor)) {
            this.collapsingSubtitleHelper.setCollapsedTextColor(MaterialResources.getColorStateList(context2, a, R.styleable.CollapsingToolbarLayout_collapsedSubtitleTextColor));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_subtitleMaxLines)) {
            this.collapsingSubtitleHelper.setExpandedMaxLines(a.getInt(R.styleable.CollapsingToolbarLayout_subtitleMaxLines, 1));
        }
        if (a.hasValue(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator)) {
            this.collapsingSubtitleHelper.setPositionInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context2, a.getResourceId(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator, 0)));
        }
        this.scrimAnimationDuration = a.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        this.scrimAnimationFadeInInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        this.scrimAnimationFadeOutInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        setContentScrim(a.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(a.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        setTitleCollapseMode(a.getInt(R.styleable.CollapsingToolbarLayout_titleCollapseMode, 0));
        this.toolbarId = a.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        this.forceApplySystemWindowInsetTop = a.getBoolean(R.styleable.CollapsingToolbarLayout_forceApplySystemWindowInsetTop, false);
        this.extraMultilineHeightEnabled = a.getBoolean(R.styleable.CollapsingToolbarLayout_extraMultilineHeightEnabled, false);
        a.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return CollapsingToolbarLayout.this.onWindowInsetChanged(insets);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            disableLiftOnScrollIfNeeded(appBarLayout);
            setFitsSystemWindows(appBarLayout.getFitsSystemWindows());
            if (this.onOffsetChangedListener == null) {
                this.onOffsetChangedListener = new OffsetUpdateListener();
            }
            appBarLayout.addOnOffsetChangedListener(this.onOffsetChangedListener);
            requestApplyInsets();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        ViewParent parent = getParent();
        if (this.onOffsetChangedListener != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(this.onOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (getFitsSystemWindows()) {
            newInsets = insets;
        }
        if (!ObjectsCompat.equals(this.lastInsets, newInsets)) {
            this.lastInsets = newInsets;
            requestLayout();
        }
        return insets.consumeSystemWindowInsets();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        ensureToolbar();
        if (this.toolbar == null && this.contentScrim != null && this.scrimAlpha > 0) {
            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
        }
        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
            if (this.toolbar != null && this.contentScrim != null && this.scrimAlpha > 0 && isTitleCollapseFadeMode() && this.collapsingTitleHelper.getExpansionFraction() < this.collapsingTitleHelper.getFadeModeThresholdFraction()) {
                int save = canvas.save();
                canvas.clipRect(this.contentScrim.getBounds(), Region.Op.DIFFERENCE);
                this.collapsingTitleHelper.draw(canvas);
                this.collapsingSubtitleHelper.draw(canvas);
                canvas.restoreToCount(save);
            } else {
                this.collapsingTitleHelper.draw(canvas);
                this.collapsingSubtitleHelper.draw(canvas);
            }
        }
        if (this.statusBarScrim != null && this.scrimAlpha > 0) {
            int topInset = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
            if (topInset > 0) {
                this.statusBarScrim.setBounds(0, -this.currentOffset, getWidth(), topInset - this.currentOffset);
                this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
                this.statusBarScrim.draw(canvas);
            }
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.collapsingTitleHelper.maybeUpdateFontWeightAdjustment(newConfig);
        if (this.screenOrientation != newConfig.orientation && this.extraMultilineHeightEnabled && this.collapsingTitleHelper.getExpansionFraction() == 1.0f) {
            ViewParent parent = getParent();
            if (parent instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) parent;
                if (appBarLayout.getPendingAction() == 0) {
                    appBarLayout.setPendingAction(2);
                }
            }
        }
        this.screenOrientation = newConfig.orientation;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean invalidated = false;
        if (this.contentScrim != null && this.scrimAlpha > 0 && isToolbarChild(child)) {
            updateContentScrimBounds(this.contentScrim, child, getWidth(), getHeight());
            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
            invalidated = true;
        }
        return super.drawChild(canvas, child, drawingTime) || invalidated;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.contentScrim != null) {
            updateContentScrimBounds(this.contentScrim, w, h);
        }
    }

    private boolean isTitleCollapseFadeMode() {
        return this.titleCollapseMode == 1;
    }

    private void disableLiftOnScrollIfNeeded(AppBarLayout appBarLayout) {
        if (isTitleCollapseFadeMode()) {
            appBarLayout.setLiftOnScroll(false);
        }
    }

    private void updateContentScrimBounds(Drawable contentScrim, int width, int height) {
        updateContentScrimBounds(contentScrim, this.toolbar, width, height);
    }

    private void updateContentScrimBounds(Drawable contentScrim, View toolbar, int width, int height) {
        int bottom;
        if (isTitleCollapseFadeMode() && toolbar != null && this.collapsingTitleEnabled) {
            bottom = toolbar.getBottom();
        } else {
            bottom = height;
        }
        contentScrim.setBounds(0, 0, width, bottom);
    }

    private void ensureToolbar() {
        if (!this.refreshToolbar) {
            return;
        }
        this.toolbar = null;
        this.toolbarDirectChild = null;
        if (this.toolbarId != -1) {
            this.toolbar = (ViewGroup) findViewById(this.toolbarId);
            if (this.toolbar != null) {
                this.toolbarDirectChild = findDirectChild(this.toolbar);
            }
        }
        if (this.toolbar == null) {
            ViewGroup toolbar = null;
            int i = 0;
            int count = getChildCount();
            while (true) {
                if (i >= count) {
                    break;
                }
                View child = getChildAt(i);
                if (!isToolbar(child)) {
                    i++;
                } else {
                    toolbar = (ViewGroup) child;
                    break;
                }
            }
            this.toolbar = toolbar;
        }
        updateDummyView();
        this.refreshToolbar = false;
    }

    private static boolean isToolbar(View view) {
        return (view instanceof Toolbar) || (view instanceof android.widget.Toolbar);
    }

    private boolean isToolbarChild(View child) {
        return (this.toolbarDirectChild == null || this.toolbarDirectChild == this) ? child == this.toolbar : child == this.toolbarDirectChild;
    }

    private View findDirectChild(View descendant) {
        View directChild = descendant;
        for (ViewParent p = descendant.getParent(); p != this && p != null; p = p.getParent()) {
            if (p instanceof View) {
                directChild = (View) p;
            }
        }
        return directChild;
    }

    private void updateDummyView() {
        if (!this.collapsingTitleEnabled && this.dummyView != null) {
            ViewParent parent = this.dummyView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.dummyView);
            }
        }
        if (this.collapsingTitleEnabled && this.toolbar != null) {
            if (this.dummyView == null) {
                this.dummyView = new View(getContext());
            }
            if (this.dummyView.getParent() == null) {
                this.toolbar.addView(this.dummyView, -1, -1);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CollapsingToolbarLayout collapsingToolbarLayout;
        float expandedTextFullSingleLineHeight;
        ensureToolbar();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int topInset = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
        if ((mode == 0 || this.forceApplySystemWindowInsetTop) && topInset > 0) {
            this.topInsetApplied = topInset;
            int newHeight = getMeasuredHeight() + topInset;
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(newHeight, BasicMeasure.EXACTLY));
        }
        updateTitleFromToolbarIfNeeded();
        if (!this.collapsingTitleEnabled || TextUtils.isEmpty(this.collapsingTitleHelper.getText())) {
            collapsingToolbarLayout = this;
        } else {
            int originalHeight = getMeasuredHeight();
            collapsingToolbarLayout = this;
            collapsingToolbarLayout.updateTextBounds(0, 0, getMeasuredWidth(), originalHeight, true);
            float expandedTextFullSingleLineHeight2 = collapsingToolbarLayout.topInsetApplied + collapsingToolbarLayout.expandedMarginTop + collapsingToolbarLayout.collapsingTitleHelper.getExpandedTextFullSingleLineHeight();
            if (TextUtils.isEmpty(collapsingToolbarLayout.collapsingSubtitleHelper.getText())) {
                expandedTextFullSingleLineHeight = 0.0f;
            } else {
                expandedTextFullSingleLineHeight = collapsingToolbarLayout.expandedTitleSpacing + collapsingToolbarLayout.collapsingSubtitleHelper.getExpandedTextFullSingleLineHeight();
            }
            int expectedHeight = (int) (expandedTextFullSingleLineHeight2 + expandedTextFullSingleLineHeight + collapsingToolbarLayout.expandedMarginBottom);
            if (expectedHeight > originalHeight) {
                collapsingToolbarLayout.extraHeightForTitles = expectedHeight - originalHeight;
            } else {
                collapsingToolbarLayout.extraHeightForTitles = 0;
            }
            if (collapsingToolbarLayout.extraMultilineHeightEnabled) {
                if (collapsingToolbarLayout.collapsingTitleHelper.getExpandedMaxLines() > 1) {
                    int lineCount = collapsingToolbarLayout.collapsingTitleHelper.getExpandedLineCount();
                    if (lineCount > 1) {
                        int expandedTextHeight = Math.round(collapsingToolbarLayout.collapsingTitleHelper.getExpandedTextFullSingleLineHeight());
                        collapsingToolbarLayout.extraMultilineTitleHeight = (lineCount - 1) * expandedTextHeight;
                    } else {
                        collapsingToolbarLayout.extraMultilineTitleHeight = 0;
                    }
                }
                if (collapsingToolbarLayout.collapsingSubtitleHelper.getExpandedMaxLines() > 1) {
                    int lineCount2 = collapsingToolbarLayout.collapsingSubtitleHelper.getExpandedLineCount();
                    if (lineCount2 > 1) {
                        int expandedTextHeight2 = Math.round(collapsingToolbarLayout.collapsingSubtitleHelper.getExpandedTextFullSingleLineHeight());
                        collapsingToolbarLayout.extraMultilineSubtitleHeight = (lineCount2 - 1) * expandedTextHeight2;
                    } else {
                        collapsingToolbarLayout.extraMultilineSubtitleHeight = 0;
                    }
                }
            }
            if (collapsingToolbarLayout.extraHeightForTitles + collapsingToolbarLayout.extraMultilineTitleHeight + collapsingToolbarLayout.extraMultilineSubtitleHeight > 0) {
                super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(collapsingToolbarLayout.extraHeightForTitles + originalHeight + collapsingToolbarLayout.extraMultilineTitleHeight + collapsingToolbarLayout.extraMultilineSubtitleHeight, BasicMeasure.EXACTLY));
            }
        }
        if (collapsingToolbarLayout.toolbar != null) {
            if (collapsingToolbarLayout.toolbarDirectChild == null || collapsingToolbarLayout.toolbarDirectChild == collapsingToolbarLayout) {
                setMinimumHeight(getHeightWithMargins(collapsingToolbarLayout.toolbar));
            } else {
                setMinimumHeight(getHeightWithMargins(collapsingToolbarLayout.toolbarDirectChild));
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.lastInsets != null) {
            int insetTop = this.lastInsets.getSystemWindowInsetTop();
            int z = getChildCount();
            for (int i = 0; i < z; i++) {
                View child = getChildAt(i);
                if (!child.getFitsSystemWindows() && child.getTop() < insetTop) {
                    ViewCompat.offsetTopAndBottom(child, insetTop);
                }
            }
        }
        int z2 = getChildCount();
        for (int i2 = 0; i2 < z2; i2++) {
            getViewOffsetHelper(getChildAt(i2)).onViewLayout();
        }
        updateTextBounds(left, top, right, bottom, false);
        updateTitleFromToolbarIfNeeded();
        updateScrimVisibility();
        int z3 = getChildCount();
        for (int i3 = 0; i3 < z3; i3++) {
            getViewOffsetHelper(getChildAt(i3)).applyOffsets();
        }
    }

    private void updateTextBounds(int left, int top, int right, int bottom, boolean forceRecalculate) {
        if (this.collapsingTitleEnabled && this.dummyView != null) {
            this.drawCollapsingTitle = this.dummyView.isAttachedToWindow() && this.dummyView.getVisibility() == 0;
            if (this.drawCollapsingTitle || forceRecalculate) {
                boolean isRtl = getLayoutDirection() == 1;
                updateCollapsedBounds(isRtl);
                int titleBoundsLeft = isRtl ? this.expandedMarginEnd : this.expandedMarginStart;
                int titleBoundsTop = this.tmpRect.top + this.expandedMarginTop;
                int titleBoundsRight = (right - left) - (isRtl ? this.expandedMarginStart : this.expandedMarginEnd);
                int titleBoundsBottom = (bottom - top) - this.expandedMarginBottom;
                if (TextUtils.isEmpty(this.collapsingSubtitleHelper.getText())) {
                    this.collapsingTitleHelper.setExpandedBounds(titleBoundsLeft, titleBoundsTop, titleBoundsRight, titleBoundsBottom);
                    this.collapsingTitleHelper.recalculate(forceRecalculate);
                } else {
                    this.collapsingTitleHelper.setExpandedBounds(titleBoundsLeft, titleBoundsTop, titleBoundsRight, (int) ((titleBoundsBottom - (this.collapsingSubtitleHelper.getExpandedTextFullSingleLineHeight() + this.extraMultilineSubtitleHeight)) - this.expandedTitleSpacing), false);
                    this.collapsingSubtitleHelper.setExpandedBounds(titleBoundsLeft, (int) (titleBoundsTop + this.collapsingTitleHelper.getExpandedTextFullSingleLineHeight() + this.extraMultilineTitleHeight + this.expandedTitleSpacing), titleBoundsRight, titleBoundsBottom, false);
                    this.collapsingTitleHelper.recalculate(forceRecalculate);
                    this.collapsingSubtitleHelper.recalculate(forceRecalculate);
                }
            }
        }
    }

    private void updateTitleFromToolbarIfNeeded() {
        if (this.toolbar != null && this.collapsingTitleEnabled) {
            CharSequence title = getToolbarTitle(this.toolbar);
            if (TextUtils.isEmpty(this.collapsingTitleHelper.getText()) && !TextUtils.isEmpty(title)) {
                setTitle(title);
            }
            CharSequence subtitle = getToolbarSubtitle(this.toolbar);
            if (TextUtils.isEmpty(this.collapsingSubtitleHelper.getText()) && !TextUtils.isEmpty(subtitle)) {
                setSubtitle(subtitle);
            }
        }
    }

    private void updateCollapsedBounds(boolean isRtl) {
        int titleMarginStart;
        int titleMarginEnd;
        int titleMarginTop;
        int titleMarginBottom;
        int maxOffset = getMaxOffsetForPinChild(this.toolbarDirectChild != null ? this.toolbarDirectChild : this.toolbar);
        DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
        if (this.toolbar instanceof Toolbar) {
            Toolbar compatToolbar = (Toolbar) this.toolbar;
            titleMarginStart = compatToolbar.getTitleMarginStart();
            titleMarginEnd = compatToolbar.getTitleMarginEnd();
            titleMarginTop = compatToolbar.getTitleMarginTop();
            titleMarginBottom = compatToolbar.getTitleMarginBottom();
        } else if (this.toolbar instanceof android.widget.Toolbar) {
            android.widget.Toolbar frameworkToolbar = (android.widget.Toolbar) this.toolbar;
            titleMarginStart = frameworkToolbar.getTitleMarginStart();
            titleMarginEnd = frameworkToolbar.getTitleMarginEnd();
            titleMarginTop = frameworkToolbar.getTitleMarginTop();
            titleMarginBottom = frameworkToolbar.getTitleMarginBottom();
        } else {
            titleMarginStart = 0;
            titleMarginEnd = 0;
            titleMarginTop = 0;
            titleMarginBottom = 0;
        }
        int titleBoundsLeft = this.tmpRect.left + (isRtl ? titleMarginEnd : titleMarginStart);
        int titleBoundsRight = this.tmpRect.right - (isRtl ? titleMarginStart : titleMarginEnd);
        int titleBoundsTop = this.tmpRect.top + maxOffset + titleMarginTop;
        int titleBoundsBottom = (this.tmpRect.bottom + maxOffset) - titleMarginBottom;
        int titleBoundsBottomWithSubtitle = (int) (titleBoundsBottom - this.collapsingSubtitleHelper.getCollapsedFullSingleLineHeight());
        int subtitleBoundsTop = (int) (titleBoundsTop + this.collapsingTitleHelper.getCollapsedFullSingleLineHeight());
        if (TextUtils.isEmpty(this.collapsingSubtitleHelper.getText())) {
            this.collapsingTitleHelper.setCollapsedBounds(titleBoundsLeft, titleBoundsTop, titleBoundsRight, titleBoundsBottom);
        } else {
            this.collapsingTitleHelper.setCollapsedBounds(titleBoundsLeft, titleBoundsTop, titleBoundsRight, titleBoundsBottomWithSubtitle);
            this.collapsingSubtitleHelper.setCollapsedBounds(titleBoundsLeft, subtitleBoundsTop, titleBoundsRight, titleBoundsBottom);
        }
        if (this.collapsedTitleGravityMode == 0) {
            DescendantOffsetUtils.getDescendantRect(this, this, this.tmpRect);
            int validTitleBoundsLeft = this.tmpRect.left + (isRtl ? titleMarginEnd : titleMarginStart);
            int validTitleBoundsRight = this.tmpRect.right - (isRtl ? titleMarginStart : titleMarginEnd);
            if (TextUtils.isEmpty(this.collapsingSubtitleHelper.getText())) {
                this.collapsingTitleHelper.setCollapsedBoundsForOffsets(validTitleBoundsLeft, titleBoundsTop, validTitleBoundsRight, titleBoundsBottom);
            } else {
                this.collapsingTitleHelper.setCollapsedBoundsForOffsets(validTitleBoundsLeft, titleBoundsTop, validTitleBoundsRight, titleBoundsBottomWithSubtitle);
                this.collapsingSubtitleHelper.setCollapsedBoundsForOffsets(validTitleBoundsLeft, subtitleBoundsTop, validTitleBoundsRight, titleBoundsBottom);
            }
        }
    }

    private static CharSequence getToolbarTitle(View view) {
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getTitle();
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar) view).getTitle();
        }
        return null;
    }

    private static CharSequence getToolbarSubtitle(View view) {
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getSubtitle();
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar) view).getSubtitle();
        }
        return null;
    }

    private static int getHeightWithMargins(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            return view.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
        }
        return view.getMeasuredHeight();
    }

    static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper offsetHelper = (ViewOffsetHelper) view.getTag(R.id.view_offset_helper);
        if (offsetHelper == null) {
            ViewOffsetHelper offsetHelper2 = new ViewOffsetHelper(view);
            view.setTag(R.id.view_offset_helper, offsetHelper2);
            return offsetHelper2;
        }
        return offsetHelper;
    }

    public void setTitle(CharSequence title) {
        this.collapsingTitleHelper.setText(title);
        updateContentDescriptionFromTitle();
    }

    public CharSequence getTitle() {
        if (this.collapsingTitleEnabled) {
            return this.collapsingTitleHelper.getText();
        }
        return null;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.collapsingSubtitleHelper.setText(subtitle);
    }

    public CharSequence getSubtitle() {
        if (this.collapsingTitleEnabled) {
            return this.collapsingSubtitleHelper.getText();
        }
        return null;
    }

    public void setTitleCollapseMode(int titleCollapseMode) {
        this.titleCollapseMode = titleCollapseMode;
        boolean fadeModeEnabled = isTitleCollapseFadeMode();
        this.collapsingTitleHelper.setFadeModeEnabled(fadeModeEnabled);
        this.collapsingSubtitleHelper.setFadeModeEnabled(fadeModeEnabled);
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            disableLiftOnScrollIfNeeded((AppBarLayout) parent);
        }
        if (fadeModeEnabled && this.contentScrim == null) {
            setContentScrimColor(getDefaultContentScrimColorForTitleCollapseFadeMode());
        }
    }

    private int getDefaultContentScrimColorForTitleCollapseFadeMode() {
        ColorStateList colorSurfaceContainer = MaterialColors.getColorStateListOrNull(getContext(), R.attr.colorSurfaceContainer);
        if (colorSurfaceContainer != null) {
            return colorSurfaceContainer.getDefaultColor();
        }
        float appBarElevation = getResources().getDimension(R.dimen.design_appbar_elevation);
        return this.elevationOverlayProvider.compositeOverlayWithThemeSurfaceColorIfNeeded(appBarElevation);
    }

    public int getTitleCollapseMode() {
        return this.titleCollapseMode;
    }

    public void setTitleEnabled(boolean enabled) {
        if (enabled != this.collapsingTitleEnabled) {
            this.collapsingTitleEnabled = enabled;
            updateContentDescriptionFromTitle();
            updateDummyView();
            requestLayout();
        }
    }

    public boolean isTitleEnabled() {
        return this.collapsingTitleEnabled;
    }

    public void setTitleEllipsize(TextUtils.TruncateAt ellipsize) {
        this.collapsingTitleHelper.setTitleTextEllipsize(ellipsize);
    }

    public TextUtils.TruncateAt getTitleTextEllipsize() {
        return this.collapsingTitleHelper.getTitleTextEllipsize();
    }

    private TextUtils.TruncateAt convertEllipsizeToTruncateAt(int ellipsize) {
        switch (ellipsize) {
            case 0:
                return TextUtils.TruncateAt.START;
            case 1:
                return TextUtils.TruncateAt.MIDDLE;
            case 2:
            default:
                return TextUtils.TruncateAt.END;
            case 3:
                return TextUtils.TruncateAt.MARQUEE;
        }
    }

    public void setScrimsShown(boolean shown) {
        setScrimsShown(shown, isLaidOut() && !isInEditMode());
    }

    public void setScrimsShown(boolean shown, boolean animate) {
        if (this.scrimsAreShown != shown) {
            if (animate) {
                animateScrim(shown ? 255 : 0);
            } else {
                setScrimAlpha(shown ? 255 : 0);
            }
            this.scrimsAreShown = shown;
        }
    }

    private void animateScrim(int targetAlpha) {
        TimeInterpolator timeInterpolator;
        ensureToolbar();
        if (this.scrimAnimator == null) {
            this.scrimAnimator = new ValueAnimator();
            ValueAnimator valueAnimator = this.scrimAnimator;
            if (targetAlpha > this.scrimAlpha) {
                timeInterpolator = this.scrimAnimationFadeInInterpolator;
            } else {
                timeInterpolator = this.scrimAnimationFadeOutInterpolator;
            }
            valueAnimator.setInterpolator(timeInterpolator);
            this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animator) {
                    CollapsingToolbarLayout.this.setScrimAlpha(((Integer) animator.getAnimatedValue()).intValue());
                }
            });
        } else if (this.scrimAnimator.isRunning()) {
            this.scrimAnimator.cancel();
        }
        this.scrimAnimator.setDuration(this.scrimAnimationDuration);
        this.scrimAnimator.setIntValues(this.scrimAlpha, targetAlpha);
        this.scrimAnimator.start();
    }

    void setScrimAlpha(int alpha) {
        if (alpha != this.scrimAlpha) {
            Drawable contentScrim = this.contentScrim;
            if (contentScrim != null && this.toolbar != null) {
                this.toolbar.postInvalidateOnAnimation();
            }
            this.scrimAlpha = alpha;
            postInvalidateOnAnimation();
        }
    }

    int getScrimAlpha() {
        return this.scrimAlpha;
    }

    public void setContentScrim(Drawable drawable) {
        if (this.contentScrim != drawable) {
            if (this.contentScrim != null) {
                this.contentScrim.setCallback(null);
            }
            this.contentScrim = drawable != null ? drawable.mutate() : null;
            if (this.contentScrim != null) {
                updateContentScrimBounds(this.contentScrim, getWidth(), getHeight());
                this.contentScrim.setCallback(this);
                this.contentScrim.setAlpha(this.scrimAlpha);
            }
            postInvalidateOnAnimation();
        }
    }

    public void setContentScrimColor(int color) {
        setContentScrim(new ColorDrawable(color));
    }

    public void setContentScrimResource(int resId) {
        setContentScrim(getContext().getDrawable(resId));
    }

    public Drawable getContentScrim() {
        return this.contentScrim;
    }

    public void setStatusBarScrim(Drawable drawable) {
        if (this.statusBarScrim != drawable) {
            if (this.statusBarScrim != null) {
                this.statusBarScrim.setCallback(null);
            }
            this.statusBarScrim = drawable != null ? drawable.mutate() : null;
            if (this.statusBarScrim != null) {
                if (this.statusBarScrim.isStateful()) {
                    this.statusBarScrim.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.statusBarScrim, getLayoutDirection());
                this.statusBarScrim.setVisible(getVisibility() == 0, false);
                this.statusBarScrim.setCallback(this);
                this.statusBarScrim.setAlpha(this.scrimAlpha);
            }
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable d = this.statusBarScrim;
        if (d != null && d.isStateful()) {
            changed = false | d.setState(state);
        }
        Drawable d2 = this.contentScrim;
        if (d2 != null && d2.isStateful()) {
            changed |= d2.setState(state);
        }
        if (this.collapsingTitleHelper != null) {
            changed |= this.collapsingTitleHelper.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.contentScrim || who == this.statusBarScrim;
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean visible = visibility == 0;
        if (this.statusBarScrim != null && this.statusBarScrim.isVisible() != visible) {
            this.statusBarScrim.setVisible(visible, false);
        }
        if (this.contentScrim != null && this.contentScrim.isVisible() != visible) {
            this.contentScrim.setVisible(visible, false);
        }
    }

    public void setStatusBarScrimColor(int color) {
        setStatusBarScrim(new ColorDrawable(color));
    }

    public void setStatusBarScrimResource(int resId) {
        setStatusBarScrim(getContext().getDrawable(resId));
    }

    public Drawable getStatusBarScrim() {
        return this.statusBarScrim;
    }

    public void setCollapsedTitleTextAppearance(int resId) {
        this.collapsingTitleHelper.setCollapsedTextAppearance(resId);
    }

    public void setCollapsedSubtitleTextAppearance(int resId) {
        this.collapsingSubtitleHelper.setCollapsedTextAppearance(resId);
    }

    public void setCollapsedTitleTextColor(int color) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(color));
    }

    public void setCollapsedTitleTextColor(ColorStateList colors) {
        this.collapsingTitleHelper.setCollapsedTextColor(colors);
    }

    public void setCollapsedSubtitleTextColor(int color) {
        setCollapsedSubtitleTextColor(ColorStateList.valueOf(color));
    }

    public void setCollapsedSubtitleTextColor(ColorStateList colors) {
        this.collapsingSubtitleHelper.setCollapsedTextColor(colors);
    }

    public void setCollapsedTitleGravity(int gravity) {
        this.collapsingTitleHelper.setCollapsedTextGravity(gravity);
        this.collapsingSubtitleHelper.setCollapsedTextGravity(gravity);
    }

    public int getCollapsedTitleGravity() {
        return this.collapsingTitleHelper.getCollapsedTextGravity();
    }

    public void setExpandedTitleTextAppearance(int resId) {
        this.collapsingTitleHelper.setExpandedTextAppearance(resId);
    }

    public void setExpandedSubtitleTextAppearance(int resId) {
        this.collapsingSubtitleHelper.setExpandedTextAppearance(resId);
    }

    public void setExpandedTitleColor(int color) {
        setExpandedTitleTextColor(ColorStateList.valueOf(color));
    }

    public void setExpandedTitleTextColor(ColorStateList colors) {
        this.collapsingTitleHelper.setExpandedTextColor(colors);
    }

    public void setExpandedSubtitleColor(int color) {
        setExpandedSubtitleTextColor(ColorStateList.valueOf(color));
    }

    public void setExpandedSubtitleTextColor(ColorStateList colors) {
        this.collapsingSubtitleHelper.setExpandedTextColor(colors);
    }

    public void setExpandedTitleGravity(int gravity) {
        this.collapsingTitleHelper.setExpandedTextGravity(gravity);
        this.collapsingSubtitleHelper.setExpandedTextGravity(gravity);
    }

    public int getExpandedTitleGravity() {
        return this.collapsingTitleHelper.getExpandedTextGravity();
    }

    public void setExpandedTitleTextSize(float textSize) {
        this.collapsingTitleHelper.setExpandedTextSize(textSize);
    }

    public void setExpandedSubtitleTextSize(float textSize) {
        this.collapsingSubtitleHelper.setExpandedTextSize(textSize);
    }

    public float getExpandedTitleTextSize() {
        return this.collapsingTitleHelper.getExpandedTextSize();
    }

    public float getExpandedSubtitleTextSize() {
        return this.collapsingSubtitleHelper.getExpandedTextSize();
    }

    public void setCollapsedTitleTextSize(float textSize) {
        this.collapsingTitleHelper.setCollapsedTextSize(textSize);
    }

    public void setCollapsedSubtitleTextSize(float textSize) {
        this.collapsingSubtitleHelper.setCollapsedTextSize(textSize);
    }

    public float getCollapsedTitleTextSize() {
        return this.collapsingTitleHelper.getCollapsedTextSize();
    }

    public float getCollapsedSubtitleTextSize() {
        return this.collapsingSubtitleHelper.getCollapsedTextSize();
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        this.collapsingTitleHelper.setCollapsedTypeface(typeface);
    }

    public void setCollapsedSubtitleTypeface(Typeface typeface) {
        this.collapsingSubtitleHelper.setCollapsedTypeface(typeface);
    }

    public Typeface getCollapsedTitleTypeface() {
        return this.collapsingTitleHelper.getCollapsedTypeface();
    }

    public Typeface getCollapsedSubtitleTypeface() {
        return this.collapsingSubtitleHelper.getCollapsedTypeface();
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        this.collapsingTitleHelper.setExpandedTypeface(typeface);
    }

    public void setExpandedSubtitleTypeface(Typeface typeface) {
        this.collapsingSubtitleHelper.setExpandedTypeface(typeface);
    }

    public Typeface getExpandedTitleTypeface() {
        return this.collapsingTitleHelper.getExpandedTypeface();
    }

    public Typeface getExpandedSubtitleTypeface() {
        return this.collapsingSubtitleHelper.getExpandedTypeface();
    }

    public void setExpandedTitleMargin(int start, int top, int end, int bottom) {
        this.expandedMarginStart = start;
        this.expandedMarginTop = top;
        this.expandedMarginEnd = end;
        this.expandedMarginBottom = bottom;
        requestLayout();
    }

    public int getExpandedTitleMarginStart() {
        return this.expandedMarginStart;
    }

    public void setExpandedTitleMarginStart(int margin) {
        this.expandedMarginStart = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginTop() {
        return this.expandedMarginTop;
    }

    public void setExpandedTitleMarginTop(int margin) {
        this.expandedMarginTop = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginEnd() {
        return this.expandedMarginEnd;
    }

    public void setExpandedTitleMarginEnd(int margin) {
        this.expandedMarginEnd = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginBottom() {
        return this.expandedMarginBottom;
    }

    public void setExpandedTitleMarginBottom(int margin) {
        this.expandedMarginBottom = margin;
        requestLayout();
    }

    public int getExpandedTitleSpacing() {
        return this.expandedTitleSpacing;
    }

    public void setExpandedTitleSpacing(int titleSpacing) {
        this.expandedTitleSpacing = titleSpacing;
        requestLayout();
    }

    public void setMaxLines(int maxLines) {
        this.collapsingTitleHelper.setExpandedMaxLines(maxLines);
        this.collapsingSubtitleHelper.setExpandedMaxLines(maxLines);
    }

    public int getMaxLines() {
        return this.collapsingTitleHelper.getExpandedMaxLines();
    }

    public int getLineCount() {
        return this.collapsingTitleHelper.getLineCount();
    }

    public void setLineSpacingAdd(float spacingAdd) {
        this.collapsingTitleHelper.setLineSpacingAdd(spacingAdd);
    }

    public float getLineSpacingAdd() {
        return this.collapsingTitleHelper.getLineSpacingAdd();
    }

    public void setLineSpacingMultiplier(float spacingMultiplier) {
        this.collapsingTitleHelper.setLineSpacingMultiplier(spacingMultiplier);
    }

    public float getLineSpacingMultiplier() {
        return this.collapsingTitleHelper.getLineSpacingMultiplier();
    }

    public void setHyphenationFrequency(int hyphenationFrequency) {
        this.collapsingTitleHelper.setHyphenationFrequency(hyphenationFrequency);
    }

    public int getHyphenationFrequency() {
        return this.collapsingTitleHelper.getHyphenationFrequency();
    }

    public void setStaticLayoutBuilderConfigurer(StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer) {
        this.collapsingTitleHelper.setStaticLayoutBuilderConfigurer(staticLayoutBuilderConfigurer);
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean rtlTextDirectionHeuristicsEnabled) {
        this.collapsingTitleHelper.setRtlTextDirectionHeuristicsEnabled(rtlTextDirectionHeuristicsEnabled);
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.collapsingTitleHelper.isRtlTextDirectionHeuristicsEnabled();
    }

    public void setForceApplySystemWindowInsetTop(boolean forceApplySystemWindowInsetTop) {
        this.forceApplySystemWindowInsetTop = forceApplySystemWindowInsetTop;
    }

    public boolean isForceApplySystemWindowInsetTop() {
        return this.forceApplySystemWindowInsetTop;
    }

    public void setExtraMultilineHeightEnabled(boolean extraMultilineHeightEnabled) {
        this.extraMultilineHeightEnabled = extraMultilineHeightEnabled;
    }

    public boolean isExtraMultilineHeightEnabled() {
        return this.extraMultilineHeightEnabled;
    }

    public void setScrimVisibleHeightTrigger(int height) {
        if (this.scrimVisibleHeightTrigger != height) {
            this.scrimVisibleHeightTrigger = height;
            updateScrimVisibility();
        }
    }

    public int getScrimVisibleHeightTrigger() {
        if (this.scrimVisibleHeightTrigger >= 0) {
            return this.scrimVisibleHeightTrigger + this.topInsetApplied + this.extraMultilineTitleHeight + this.extraMultilineSubtitleHeight + this.extraHeightForTitles;
        }
        int insetTop = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
        int minHeight = getMinimumHeight();
        if (minHeight > 0) {
            return Math.min((minHeight * 2) + insetTop, getHeight());
        }
        return getHeight() / 3;
    }

    public void setTitlePositionInterpolator(TimeInterpolator interpolator) {
        this.collapsingTitleHelper.setPositionInterpolator(interpolator);
    }

    public TimeInterpolator getTitlePositionInterpolator() {
        return this.collapsingTitleHelper.getPositionInterpolator();
    }

    public void setScrimAnimationDuration(long duration) {
        this.scrimAnimationDuration = duration;
    }

    public long getScrimAnimationDuration() {
        return this.scrimAnimationDuration;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int collapseMode;
        float parallaxMult;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CollapsingToolbarLayout_Layout);
            this.collapseMode = a.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            setParallaxMultiplier(a.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(FrameLayout.LayoutParams source) {
            super(source);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(LayoutParams source) {
            super((FrameLayout.LayoutParams) source);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
            this.collapseMode = source.collapseMode;
            this.parallaxMult = source.parallaxMult;
        }

        public void setCollapseMode(int collapseMode) {
            this.collapseMode = collapseMode;
        }

        public int getCollapseMode() {
            return this.collapseMode;
        }

        public void setParallaxMultiplier(float multiplier) {
            this.parallaxMult = multiplier;
        }

        public float getParallaxMultiplier() {
            return this.parallaxMult;
        }
    }

    final void updateScrimVisibility() {
        if (this.contentScrim != null || this.statusBarScrim != null) {
            setScrimsShown(getHeight() + this.currentOffset < getScrimVisibleHeightTrigger());
        }
    }

    final int getMaxOffsetForPinChild(View child) {
        ViewOffsetHelper offsetHelper = getViewOffsetHelper(child);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return ((getHeight() - offsetHelper.getLayoutTop()) - child.getHeight()) - lp.bottomMargin;
    }

    private void updateContentDescriptionFromTitle() {
        setContentDescription(getTitle());
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            CollapsingToolbarLayout.this.currentOffset = verticalOffset;
            int insetTop = CollapsingToolbarLayout.this.lastInsets != null ? CollapsingToolbarLayout.this.lastInsets.getSystemWindowInsetTop() : 0;
            int z = CollapsingToolbarLayout.this.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = CollapsingToolbarLayout.this.getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                ViewOffsetHelper offsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(child);
                switch (lp.collapseMode) {
                    case 1:
                        offsetHelper.setTopAndBottomOffset(MathUtils.clamp(-verticalOffset, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild(child)));
                        break;
                    case 2:
                        offsetHelper.setTopAndBottomOffset(Math.round((-verticalOffset) * lp.parallaxMult));
                        break;
                }
            }
            CollapsingToolbarLayout.this.updateScrimVisibility();
            if (CollapsingToolbarLayout.this.statusBarScrim != null && insetTop > 0) {
                CollapsingToolbarLayout.this.postInvalidateOnAnimation();
            }
            int height = CollapsingToolbarLayout.this.getHeight();
            int expandRange = (height - CollapsingToolbarLayout.this.getMinimumHeight()) - insetTop;
            int scrimRange = height - CollapsingToolbarLayout.this.getScrimVisibleHeightTrigger();
            int currentOffsetY = CollapsingToolbarLayout.this.currentOffset + expandRange;
            float expansionFraction = Math.abs(verticalOffset) / expandRange;
            CollapsingToolbarLayout.this.collapsingTitleHelper.setFadeModeStartFraction(Math.min(1.0f, scrimRange / expandRange));
            CollapsingToolbarLayout.this.collapsingTitleHelper.setCurrentOffsetY(currentOffsetY);
            CollapsingToolbarLayout.this.collapsingTitleHelper.setExpansionFraction(expansionFraction);
            CollapsingToolbarLayout.this.collapsingSubtitleHelper.setFadeModeStartFraction(Math.min(1.0f, scrimRange / expandRange));
            CollapsingToolbarLayout.this.collapsingSubtitleHelper.setCurrentOffsetY(currentOffsetY);
            CollapsingToolbarLayout.this.collapsingSubtitleHelper.setExpansionFraction(expansionFraction);
        }
    }
}
