package com.google.android.material.navigation;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.BaselineLayout;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class NavigationBarItemView extends FrameLayout implements NavigationBarMenuItemView {
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM;
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int INVALID_ITEM_POSITION = -1;
    private ValueAnimator activeIndicatorAnimator;
    private int activeIndicatorDesiredHeight;
    private int activeIndicatorDesiredWidth;
    private boolean activeIndicatorEnabled;
    private int activeIndicatorExpandedDesiredHeight;
    private int activeIndicatorExpandedDesiredWidth;
    private int activeIndicatorExpandedMarginHorizontal;
    private int activeIndicatorLabelPadding;
    private int activeIndicatorMarginHorizontal;
    private float activeIndicatorProgress;
    private boolean activeIndicatorResizeable;
    private ActiveIndicatorTransform activeIndicatorTransform;
    private final View activeIndicatorView;
    private BadgeDrawable badgeDrawable;
    private int badgeFixedEdge;
    private boolean boldText;
    private final LinearLayout contentContainer;
    private BaselineLayout currentLabelGroup;
    private boolean expanded;
    private BaselineLayout expandedLabelGroup;
    private float expandedLabelScaleDownFactor;
    private float expandedLabelScaleUpFactor;
    private float expandedLabelShiftAmountY;
    private TextView expandedLargeLabel;
    private TextView expandedSmallLabel;
    private int horizontalTextAppearanceActive;
    private int horizontalTextAppearanceInactive;
    private final ImageView icon;
    private final FrameLayout iconContainer;
    private int iconLabelHorizontalSpacing;
    private ColorStateList iconTint;
    private boolean initialized;
    private final LinearLayout innerContentContainer;
    private boolean isShifting;
    private Rect itemActiveIndicatorExpandedPadding;
    Drawable itemBackground;
    private MenuItemImpl itemData;
    private int itemGravity;
    private int itemIconGravity;
    private int itemPaddingBottom;
    private int itemPaddingTop;
    private int itemPosition;
    private ColorStateList itemRippleColor;
    private final BaselineLayout labelGroup;
    private int labelVisibilityMode;
    private final TextView largeLabel;
    private boolean measurePaddingFromBaseline;
    private boolean onlyShowWhenExpanded;
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private boolean scaleLabelSizeWithFont;
    private float scaleUpFactor;
    private float shiftAmountY;
    private final TextView smallLabel;
    private int textAppearanceActive;
    private int textAppearanceInactive;
    private ColorStateList textColor;
    private Drawable wrappedIconDrawable;

    protected abstract int getItemLayoutResId();

    static {
        ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform();
        ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform();
    }

    public NavigationBarItemView(Context context) {
        super(context);
        this.initialized = false;
        this.itemPosition = -1;
        this.textAppearanceActive = 0;
        this.textAppearanceInactive = 0;
        this.horizontalTextAppearanceActive = 0;
        this.horizontalTextAppearanceInactive = 0;
        this.boldText = false;
        this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        this.activeIndicatorProgress = 0.0f;
        this.activeIndicatorEnabled = false;
        this.activeIndicatorDesiredWidth = 0;
        this.activeIndicatorDesiredHeight = 0;
        this.activeIndicatorExpandedDesiredWidth = -2;
        this.activeIndicatorExpandedDesiredHeight = 0;
        this.activeIndicatorResizeable = false;
        this.activeIndicatorMarginHorizontal = 0;
        this.activeIndicatorExpandedMarginHorizontal = 0;
        this.badgeFixedEdge = 0;
        this.itemGravity = 49;
        this.expanded = false;
        this.onlyShowWhenExpanded = false;
        this.measurePaddingFromBaseline = false;
        this.scaleLabelSizeWithFont = false;
        this.itemActiveIndicatorExpandedPadding = new Rect();
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        this.contentContainer = (LinearLayout) findViewById(com.google.android.material.R.id.navigation_bar_item_content_container);
        this.innerContentContainer = (LinearLayout) findViewById(com.google.android.material.R.id.navigation_bar_item_inner_content_container);
        this.activeIndicatorView = findViewById(com.google.android.material.R.id.navigation_bar_item_active_indicator_view);
        this.iconContainer = (FrameLayout) findViewById(com.google.android.material.R.id.navigation_bar_item_icon_container);
        this.icon = (ImageView) findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view);
        this.labelGroup = (BaselineLayout) findViewById(com.google.android.material.R.id.navigation_bar_item_labels_group);
        this.smallLabel = (TextView) findViewById(com.google.android.material.R.id.navigation_bar_item_small_label_view);
        this.largeLabel = (TextView) findViewById(com.google.android.material.R.id.navigation_bar_item_large_label_view);
        initializeDefaultExpandedLabelGroupViews();
        this.currentLabelGroup = this.labelGroup;
        setBackgroundResource(getItemBackgroundResId());
        this.itemPaddingTop = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.itemPaddingBottom = this.labelGroup.getPaddingBottom();
        this.activeIndicatorLabelPadding = 0;
        this.iconLabelHorizontalSpacing = 0;
        this.smallLabel.setImportantForAccessibility(2);
        this.largeLabel.setImportantForAccessibility(2);
        this.expandedSmallLabel.setImportantForAccessibility(2);
        this.expandedLargeLabel.setImportantForAccessibility(2);
        setFocusable(true);
        calculateTextScaleFactors();
        this.activeIndicatorExpandedDesiredHeight = getResources().getDimensionPixelSize(com.google.android.material.R.dimen.m3_navigation_item_expanded_active_indicator_height_default);
        this.innerContentContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.navigation.NavigationBarItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.m215xe6e25373(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-google-android-material-navigation-NavigationBarItemView, reason: not valid java name */
    /* synthetic */ void m215xe6e25373(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (this.icon.getVisibility() == 0) {
            tryUpdateBadgeBounds(this.icon);
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.innerContentContainer.getLayoutParams();
        int newWidth = (right - left) + lp.rightMargin + lp.leftMargin;
        int newHeight = (bottom - top) + lp.topMargin + lp.bottomMargin;
        if (this.itemIconGravity == 1 && this.activeIndicatorExpandedDesiredWidth == -2) {
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
            boolean layoutParamsChanged = false;
            if (this.activeIndicatorExpandedDesiredWidth == -2 && this.activeIndicatorView.getMeasuredWidth() != newWidth) {
                int minWidth = Math.min(this.activeIndicatorDesiredWidth, getMeasuredWidth() - (this.activeIndicatorMarginHorizontal * 2));
                indicatorParams.width = Math.max(newWidth, minWidth);
                layoutParamsChanged = true;
            }
            if (this.activeIndicatorView.getMeasuredHeight() < newHeight) {
                indicatorParams.height = newHeight;
                layoutParamsChanged = true;
            }
            if (layoutParamsChanged) {
                this.activeIndicatorView.setLayoutParams(indicatorParams);
            }
        }
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        if (this.itemIconGravity == 1) {
            FrameLayout.LayoutParams innerContentParams = (FrameLayout.LayoutParams) this.innerContentContainer.getLayoutParams();
            return this.innerContentContainer.getMeasuredWidth() + innerContentParams.leftMargin + innerContentParams.rightMargin;
        }
        LinearLayout.LayoutParams labelGroupParams = (LinearLayout.LayoutParams) this.labelGroup.getLayoutParams();
        int labelWidth = labelGroupParams.leftMargin + this.labelGroup.getMeasuredWidth() + labelGroupParams.rightMargin;
        return Math.max(getSuggestedIconWidth(), labelWidth);
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams contentContainerParams = (FrameLayout.LayoutParams) this.contentContainer.getLayoutParams();
        return this.contentContainer.getMeasuredHeight() + contentContainerParams.topMargin + contentContainerParams.bottomMargin;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl itemData, int menuType) {
        CharSequence tooltipText;
        this.itemData = itemData;
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
        if (!TextUtils.isEmpty(itemData.getContentDescription())) {
            setContentDescription(itemData.getContentDescription());
        }
        if (!TextUtils.isEmpty(itemData.getTooltipText())) {
            tooltipText = itemData.getTooltipText();
        } else {
            tooltipText = itemData.getTitle();
        }
        TooltipCompat.setTooltipText(this, tooltipText);
        updateVisibility();
        this.initialized = true;
    }

    private void updateVisibility() {
        if (this.itemData != null) {
            setVisibility((!this.itemData.isVisible() || (!this.expanded && this.onlyShowWhenExpanded)) ? 8 : 0);
        }
    }

    void clear() {
        removeBadge();
        this.itemData = null;
        this.activeIndicatorProgress = 0.0f;
        this.initialized = false;
    }

    public void setItemPosition(int position) {
        this.itemPosition = position;
    }

    public int getItemPosition() {
        return this.itemPosition;
    }

    public BaselineLayout getLabelGroup() {
        return this.labelGroup;
    }

    public BaselineLayout getExpandedLabelGroup() {
        return this.expandedLabelGroup;
    }

    public void setShifting(boolean shifting) {
        if (this.isShifting != shifting) {
            this.isShifting = shifting;
            refreshChecked();
        }
    }

    public void setLabelVisibilityMode(int mode) {
        if (this.labelVisibilityMode != mode) {
            this.labelVisibilityMode = mode;
            updateActiveIndicatorTransform();
            updateActiveIndicatorLayoutParams(getWidth());
            refreshChecked();
        }
    }

    private void initializeDefaultExpandedLabelGroupViews() {
        float defaultInactiveTextSize = getResources().getDimension(com.google.android.material.R.dimen.default_navigation_text_size);
        float defaultActiveTextSize = getResources().getDimension(com.google.android.material.R.dimen.default_navigation_active_text_size);
        this.expandedLabelGroup = new BaselineLayout(getContext());
        this.expandedLabelGroup.setVisibility(8);
        this.expandedLabelGroup.setDuplicateParentStateEnabled(true);
        this.expandedLabelGroup.setMeasurePaddingFromBaseline(this.measurePaddingFromBaseline);
        this.expandedSmallLabel = new TextView(getContext());
        this.expandedSmallLabel.setMaxLines(1);
        this.expandedSmallLabel.setEllipsize(TextUtils.TruncateAt.END);
        this.expandedSmallLabel.setDuplicateParentStateEnabled(true);
        this.expandedSmallLabel.setIncludeFontPadding(false);
        this.expandedSmallLabel.setGravity(16);
        this.expandedSmallLabel.setTextSize(defaultInactiveTextSize);
        this.expandedLargeLabel = new TextView(getContext());
        this.expandedLargeLabel.setMaxLines(1);
        this.expandedLargeLabel.setEllipsize(TextUtils.TruncateAt.END);
        this.expandedLargeLabel.setDuplicateParentStateEnabled(true);
        this.expandedLargeLabel.setVisibility(4);
        this.expandedLargeLabel.setIncludeFontPadding(false);
        this.expandedLargeLabel.setGravity(16);
        this.expandedLargeLabel.setTextSize(defaultActiveTextSize);
        this.expandedLabelGroup.addView(this.expandedSmallLabel);
        this.expandedLabelGroup.addView(this.expandedLargeLabel);
    }

    private void addDefaultExpandedLabelGroupViews() {
        LinearLayout.LayoutParams expandedLabelGroupLp = new LinearLayout.LayoutParams(-2, -2);
        expandedLabelGroupLp.gravity = 17;
        this.innerContentContainer.addView(this.expandedLabelGroup, expandedLabelGroupLp);
        setExpandedLabelGroupMargins();
    }

    private void updateItemIconGravity() {
        int leftMargin = 0;
        int rightMargin = 0;
        int topMargin = 0;
        int bottomMargin = 0;
        int sidePadding = 0;
        this.badgeFixedEdge = 0;
        int verticalLabelGroupVisibility = 0;
        int horizontalLabelGroupVisibility = 8;
        this.currentLabelGroup = this.labelGroup;
        if (this.itemIconGravity == 1) {
            if (this.expandedLabelGroup.getParent() == null) {
                addDefaultExpandedLabelGroupViews();
            }
            leftMargin = this.itemActiveIndicatorExpandedPadding.left;
            rightMargin = this.itemActiveIndicatorExpandedPadding.right;
            topMargin = this.itemActiveIndicatorExpandedPadding.top;
            bottomMargin = this.itemActiveIndicatorExpandedPadding.bottom;
            this.badgeFixedEdge = 1;
            sidePadding = this.activeIndicatorExpandedMarginHorizontal;
            verticalLabelGroupVisibility = 8;
            horizontalLabelGroupVisibility = 0;
            this.currentLabelGroup = this.expandedLabelGroup;
        }
        this.labelGroup.setVisibility(verticalLabelGroupVisibility);
        this.expandedLabelGroup.setVisibility(horizontalLabelGroupVisibility);
        FrameLayout.LayoutParams contentContainerLp = (FrameLayout.LayoutParams) this.contentContainer.getLayoutParams();
        contentContainerLp.gravity = this.itemGravity;
        FrameLayout.LayoutParams innerContentLp = (FrameLayout.LayoutParams) this.innerContentContainer.getLayoutParams();
        innerContentLp.leftMargin = leftMargin;
        innerContentLp.rightMargin = rightMargin;
        innerContentLp.topMargin = topMargin;
        innerContentLp.bottomMargin = bottomMargin;
        setPadding(sidePadding, 0, sidePadding, 0);
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setItemIconGravity(int iconGravity) {
        if (this.itemIconGravity != iconGravity) {
            this.itemIconGravity = iconGravity;
            updateItemIconGravity();
            refreshItemBackground();
        }
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuItemView
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        updateVisibility();
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuItemView
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuItemView
    public void setOnlyShowWhenExpanded(boolean onlyShowWhenExpanded) {
        this.onlyShowWhenExpanded = onlyShowWhenExpanded;
        updateVisibility();
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuItemView
    public boolean isOnlyVisibleWhenExpanded() {
        return this.onlyShowWhenExpanded;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(CharSequence title) {
        CharSequence tooltipText;
        this.smallLabel.setText(title);
        this.largeLabel.setText(title);
        this.expandedSmallLabel.setText(title);
        this.expandedLargeLabel.setText(title);
        if (this.itemData == null || TextUtils.isEmpty(this.itemData.getContentDescription())) {
            setContentDescription(title);
        }
        if (this.itemData == null || TextUtils.isEmpty(this.itemData.getTooltipText())) {
            tooltipText = title;
        } else {
            tooltipText = this.itemData.getTooltipText();
        }
        TooltipCompat.setTooltipText(this, tooltipText);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    @Override // android.view.View
    protected void onSizeChanged(final int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new Runnable() { // from class: com.google.android.material.navigation.NavigationBarItemView.1
            @Override // java.lang.Runnable
            public void run() {
                NavigationBarItemView.this.updateActiveIndicatorLayoutParams(w);
            }
        });
    }

    private void updateActiveIndicatorTransform() {
        if (isActiveIndicatorResizeableAndUnlabeled()) {
            this.activeIndicatorTransform = ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
        } else {
            this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActiveIndicatorProgress(float progress, float target) {
        this.activeIndicatorTransform.updateForProgress(progress, target, this.activeIndicatorView);
        this.activeIndicatorProgress = progress;
    }

    private void maybeAnimateActiveIndicatorToProgress(final float newProgress) {
        if (!this.activeIndicatorEnabled || !this.initialized || !isAttachedToWindow()) {
            setActiveIndicatorProgress(newProgress, newProgress);
            return;
        }
        if (this.activeIndicatorAnimator != null) {
            this.activeIndicatorAnimator.cancel();
            this.activeIndicatorAnimator = null;
        }
        this.activeIndicatorAnimator = ValueAnimator.ofFloat(this.activeIndicatorProgress, newProgress);
        this.activeIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = ((Float) animation.getAnimatedValue()).floatValue();
                NavigationBarItemView.this.setActiveIndicatorProgress(progress, newProgress);
            }
        });
        this.activeIndicatorAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.google.android.material.R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        this.activeIndicatorAnimator.setDuration(MotionUtils.resolveThemeDuration(getContext(), com.google.android.material.R.attr.motionDurationLong2, getResources().getInteger(com.google.android.material.R.integer.material_motion_duration_long_1)));
        this.activeIndicatorAnimator.start();
    }

    private void refreshChecked() {
        if (this.itemData != null) {
            setChecked(this.itemData.isChecked());
        }
    }

    private void setLayoutConfigurationIconAndLabel(View visibleLabel, View invisibleLabel, float scaleFactor, float topMarginShift) {
        int i;
        setViewMarginAndGravity(this.contentContainer, this.itemIconGravity == 0 ? (int) (this.itemPaddingTop + topMarginShift) : 0, 0, this.itemGravity);
        LinearLayout linearLayout = this.innerContentContainer;
        int i2 = this.itemIconGravity == 0 ? 0 : this.itemActiveIndicatorExpandedPadding.top;
        int i3 = this.itemIconGravity == 0 ? 0 : this.itemActiveIndicatorExpandedPadding.bottom;
        if (this.itemIconGravity == 0) {
            i = 17;
        } else {
            i = NavigationBarView.ITEM_GRAVITY_START_CENTER;
        }
        setViewMarginAndGravity(linearLayout, i2, i3, i);
        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
        this.currentLabelGroup.setVisibility(0);
        setViewScaleValues(visibleLabel, 1.0f, 1.0f, 0);
        setViewScaleValues(invisibleLabel, scaleFactor, scaleFactor, 4);
    }

    private void setLayoutConfigurationIconOnly() {
        setViewMarginAndGravity(this.contentContainer, this.itemPaddingTop, this.itemPaddingTop, this.itemIconGravity == 0 ? 17 : this.itemGravity);
        setViewMarginAndGravity(this.innerContentContainer, 0, 0, 17);
        updateViewPaddingBottom(this.labelGroup, 0);
        this.currentLabelGroup.setVisibility(8);
    }

    private void setLabelPivots(TextView label) {
        label.setPivotX(label.getWidth() / 2);
        label.setPivotY(label.getBaseline());
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean checked) {
        setLabelPivots(this.largeLabel);
        setLabelPivots(this.smallLabel);
        setLabelPivots(this.expandedLargeLabel);
        setLabelPivots(this.expandedSmallLabel);
        float newIndicatorProgress = checked ? 1.0f : 0.0f;
        maybeAnimateActiveIndicatorToProgress(newIndicatorProgress);
        View selectedLabel = this.largeLabel;
        View unselectedLabel = this.smallLabel;
        float shiftAmount = this.shiftAmountY;
        float scaleUpFactor = this.scaleUpFactor;
        float scaleDownFactor = this.scaleDownFactor;
        if (this.itemIconGravity == 1) {
            selectedLabel = this.expandedLargeLabel;
            unselectedLabel = this.expandedSmallLabel;
            shiftAmount = this.expandedLabelShiftAmountY;
            scaleUpFactor = this.expandedLabelScaleUpFactor;
            scaleDownFactor = this.expandedLabelScaleDownFactor;
        }
        switch (this.labelVisibilityMode) {
            case -1:
                if (this.isShifting) {
                    if (checked) {
                        setLayoutConfigurationIconAndLabel(selectedLabel, unselectedLabel, scaleUpFactor, 0.0f);
                    } else {
                        setLayoutConfigurationIconOnly();
                    }
                } else if (checked) {
                    setLayoutConfigurationIconAndLabel(selectedLabel, unselectedLabel, scaleUpFactor, shiftAmount);
                } else {
                    setLayoutConfigurationIconAndLabel(unselectedLabel, selectedLabel, scaleDownFactor, 0.0f);
                }
                break;
            case 0:
                if (checked) {
                    setLayoutConfigurationIconAndLabel(selectedLabel, unselectedLabel, scaleUpFactor, 0.0f);
                } else {
                    setLayoutConfigurationIconOnly();
                }
                break;
            case 1:
                if (checked) {
                    setLayoutConfigurationIconAndLabel(selectedLabel, unselectedLabel, scaleUpFactor, shiftAmount);
                } else {
                    setLayoutConfigurationIconAndLabel(unselectedLabel, selectedLabel, scaleDownFactor, 0.0f);
                }
                break;
            case 2:
                setLayoutConfigurationIconOnly();
                break;
        }
        refreshDrawableState();
        setSelected(checked);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.badgeDrawable != null && this.badgeDrawable.isVisible()) {
            CharSequence customContentDescription = this.itemData.getTitle();
            if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
                customContentDescription = this.itemData.getContentDescription();
            }
            info.setContentDescription(((Object) customContentDescription) + ", " + ((Object) this.badgeDrawable.getContentDescription()));
        }
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            infoCompat.setClickable(false);
            infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        infoCompat.setRoleDescription(getResources().getString(com.google.android.material.R.string.item_view_role_description));
    }

    private int getItemVisiblePosition() {
        ViewGroup parent = (ViewGroup) getParent();
        int index = parent.indexOfChild(this);
        int visiblePosition = 0;
        for (int i = 0; i < index; i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof NavigationBarItemView) && child.getVisibility() == 0) {
                visiblePosition++;
            }
        }
        return visiblePosition;
    }

    private static void setViewMarginAndGravity(View view, int topMargin, int bottomMargin, int gravity) {
        FrameLayout.LayoutParams viewParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        viewParams.topMargin = topMargin;
        viewParams.bottomMargin = bottomMargin;
        viewParams.gravity = gravity;
        view.setLayoutParams(viewParams);
    }

    private static void setViewScaleValues(View view, float scaleX, float scaleY, int visibility) {
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setVisibility(visibility);
    }

    private static void updateViewPaddingBottom(View view, int paddingBottom) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
    }

    @Override // android.view.View, androidx.appcompat.view.menu.MenuView.ItemView
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.smallLabel.setEnabled(enabled);
        this.largeLabel.setEnabled(enabled);
        this.expandedSmallLabel.setEnabled(enabled);
        this.expandedLargeLabel.setEnabled(enabled);
        this.icon.setEnabled(enabled);
    }

    @Override // android.view.ViewGroup, android.view.View
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.itemData != null && this.itemData.isCheckable() && this.itemData.isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(Drawable iconDrawable) {
        if (iconDrawable == this.originalIconDrawable) {
            return;
        }
        this.originalIconDrawable = iconDrawable;
        if (iconDrawable != null) {
            Drawable.ConstantState state = iconDrawable.getConstantState();
            iconDrawable = DrawableCompat.wrap(state == null ? iconDrawable : state.newDrawable()).mutate();
            this.wrappedIconDrawable = iconDrawable;
            if (this.iconTint != null) {
                this.wrappedIconDrawable.setTintList(this.iconTint);
            }
        }
        this.icon.setImageDrawable(iconDrawable);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(ColorStateList tint) {
        this.iconTint = tint;
        if (this.itemData != null && this.wrappedIconDrawable != null) {
            this.wrappedIconDrawable.setTintList(this.iconTint);
            this.wrappedIconDrawable.invalidateSelf();
        }
    }

    public void setIconSize(int iconSize) {
        LinearLayout.LayoutParams iconParams = (LinearLayout.LayoutParams) this.icon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        this.icon.setLayoutParams(iconParams);
        setExpandedLabelGroupMargins();
    }

    private void setExpandedLabelGroupMargins() {
        int margin = this.icon.getLayoutParams().width > 0 ? this.iconLabelHorizontalSpacing : 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.expandedLabelGroup.getLayoutParams();
        if (lp != null) {
            lp.rightMargin = getLayoutDirection() == 1 ? margin : 0;
            lp.leftMargin = getLayoutDirection() != 1 ? margin : 0;
        }
    }

    public void setMeasureBottomPaddingFromLabelBaseline(boolean measurePaddingFromBaseline) {
        this.measurePaddingFromBaseline = measurePaddingFromBaseline;
        this.labelGroup.setMeasurePaddingFromBaseline(measurePaddingFromBaseline);
        this.smallLabel.setIncludeFontPadding(measurePaddingFromBaseline);
        this.largeLabel.setIncludeFontPadding(measurePaddingFromBaseline);
        this.expandedLabelGroup.setMeasurePaddingFromBaseline(measurePaddingFromBaseline);
        this.expandedSmallLabel.setIncludeFontPadding(measurePaddingFromBaseline);
        this.expandedLargeLabel.setIncludeFontPadding(measurePaddingFromBaseline);
        requestLayout();
    }

    public void setLabelFontScalingEnabled(boolean scaleLabelSizeWithFont) {
        this.scaleLabelSizeWithFont = scaleLabelSizeWithFont;
        setTextAppearanceActive(this.textAppearanceActive);
        setTextAppearanceInactive(this.textAppearanceInactive);
        setHorizontalTextAppearanceActive(this.horizontalTextAppearanceActive);
        setHorizontalTextAppearanceInactive(this.horizontalTextAppearanceInactive);
    }

    private void setTextAppearanceForLabel(TextView label, int textAppearance) {
        if (this.scaleLabelSizeWithFont) {
            TextViewCompat.setTextAppearance(label, textAppearance);
        } else {
            setTextAppearanceWithoutFontScaling(label, textAppearance);
        }
    }

    private void updateInactiveLabelTextAppearance(TextView smallLabel, int textAppearanceInactive) {
        if (smallLabel == null) {
            return;
        }
        setTextAppearanceForLabel(smallLabel, textAppearanceInactive);
        calculateTextScaleFactors();
        smallLabel.setMinimumHeight(MaterialResources.getUnscaledLineHeight(smallLabel.getContext(), textAppearanceInactive, 0));
        if (this.textColor != null) {
            smallLabel.setTextColor(this.textColor);
        }
    }

    private void updateActiveLabelTextAppearance(TextView largeLabel, int textAppearanceActive) {
        if (largeLabel == null) {
            return;
        }
        setTextAppearanceForLabel(largeLabel, textAppearanceActive);
        calculateTextScaleFactors();
        largeLabel.setMinimumHeight(MaterialResources.getUnscaledLineHeight(largeLabel.getContext(), textAppearanceActive, 0));
        if (this.textColor != null) {
            largeLabel.setTextColor(this.textColor);
        }
        updateActiveLabelBoldness();
    }

    public void setTextAppearanceInactive(int inactiveTextAppearance) {
        this.textAppearanceInactive = inactiveTextAppearance;
        updateInactiveLabelTextAppearance(this.smallLabel, this.textAppearanceInactive);
    }

    public void setTextAppearanceActive(int activeTextAppearance) {
        this.textAppearanceActive = activeTextAppearance;
        updateActiveLabelTextAppearance(this.largeLabel, this.textAppearanceActive);
    }

    public void setHorizontalTextAppearanceInactive(int inactiveTextAppearance) {
        this.horizontalTextAppearanceInactive = inactiveTextAppearance;
        updateInactiveLabelTextAppearance(this.expandedSmallLabel, this.horizontalTextAppearanceInactive != 0 ? this.horizontalTextAppearanceInactive : this.textAppearanceInactive);
    }

    public void setHorizontalTextAppearanceActive(int activeTextAppearance) {
        this.horizontalTextAppearanceActive = activeTextAppearance;
        updateActiveLabelTextAppearance(this.expandedLargeLabel, this.horizontalTextAppearanceActive != 0 ? this.horizontalTextAppearanceActive : this.textAppearanceActive);
    }

    public void setTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.boldText = isBold;
        setTextAppearanceActive(this.textAppearanceActive);
        setHorizontalTextAppearanceActive(this.horizontalTextAppearanceActive);
        updateActiveLabelBoldness();
    }

    private void updateActiveLabelBoldness() {
        this.largeLabel.setTypeface(this.largeLabel.getTypeface(), this.boldText ? 1 : 0);
        this.expandedLargeLabel.setTypeface(this.expandedLargeLabel.getTypeface(), this.boldText ? 1 : 0);
    }

    private static void setTextAppearanceWithoutFontScaling(TextView textView, int textAppearance) {
        TextViewCompat.setTextAppearance(textView, textAppearance);
        int unscaledSize = MaterialResources.getUnscaledTextSize(textView.getContext(), textAppearance, 0);
        if (unscaledSize != 0) {
            textView.setTextSize(0, unscaledSize);
        }
    }

    public void setLabelMaxLines(int labelMaxLines) {
        this.smallLabel.setMaxLines(labelMaxLines);
        this.largeLabel.setMaxLines(labelMaxLines);
        this.expandedSmallLabel.setMaxLines(labelMaxLines);
        this.expandedLargeLabel.setMaxLines(labelMaxLines);
        if (Build.VERSION.SDK_INT > 34) {
            this.smallLabel.setGravity(17);
            this.largeLabel.setGravity(17);
        } else if (labelMaxLines > 1) {
            this.smallLabel.setEllipsize(null);
            this.largeLabel.setEllipsize(null);
            this.smallLabel.setGravity(17);
            this.largeLabel.setGravity(17);
        } else {
            this.smallLabel.setGravity(16);
            this.largeLabel.setGravity(16);
        }
        requestLayout();
    }

    public void setTextColor(ColorStateList color) {
        this.textColor = color;
        if (color != null) {
            this.smallLabel.setTextColor(color);
            this.largeLabel.setTextColor(color);
            this.expandedSmallLabel.setTextColor(color);
            this.expandedLargeLabel.setTextColor(color);
        }
    }

    private void calculateTextScaleFactors() {
        float smallLabelSize = this.smallLabel.getTextSize();
        float largeLabelSize = this.largeLabel.getTextSize();
        this.shiftAmountY = smallLabelSize - largeLabelSize;
        this.scaleUpFactor = (largeLabelSize * 1.0f) / smallLabelSize;
        this.scaleDownFactor = (smallLabelSize * 1.0f) / largeLabelSize;
        float expandedSmallLabelSize = this.expandedSmallLabel.getTextSize();
        float expandedLargeLabelSize = this.expandedLargeLabel.getTextSize();
        this.expandedLabelShiftAmountY = expandedSmallLabelSize - expandedLargeLabelSize;
        this.expandedLabelScaleUpFactor = (expandedLargeLabelSize * 1.0f) / expandedSmallLabelSize;
        this.expandedLabelScaleDownFactor = (1.0f * expandedSmallLabelSize) / expandedLargeLabelSize;
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable = background == 0 ? null : getContext().getDrawable(background);
        setItemBackground(backgroundDrawable);
    }

    public void setItemBackground(Drawable background) {
        if (background != null && background.getConstantState() != null) {
            background = background.getConstantState().newDrawable().mutate();
        }
        this.itemBackground = background;
        refreshItemBackground();
    }

    public void setItemRippleColor(ColorStateList itemRippleColor) {
        this.itemRippleColor = itemRippleColor;
        refreshItemBackground();
    }

    private void refreshItemBackground() {
        Drawable iconContainerRippleDrawable = null;
        Drawable itemBackgroundDrawable = this.itemBackground;
        boolean defaultHighlightEnabled = true;
        if (this.itemRippleColor != null) {
            Drawable maskDrawable = getActiveIndicatorDrawable();
            if (this.activeIndicatorEnabled && getActiveIndicatorDrawable() != null && maskDrawable != null) {
                defaultHighlightEnabled = false;
                iconContainerRippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.itemRippleColor), null, maskDrawable);
            } else if (itemBackgroundDrawable == null) {
                itemBackgroundDrawable = createItemBackgroundCompat(this.itemRippleColor);
            }
        }
        this.iconContainer.setPadding(0, 0, 0, 0);
        this.iconContainer.setForeground(iconContainerRippleDrawable);
        setBackground(itemBackgroundDrawable);
        if (Build.VERSION.SDK_INT >= 26) {
            setDefaultFocusHighlightEnabled(defaultHighlightEnabled);
        }
    }

    private static Drawable createItemBackgroundCompat(ColorStateList rippleColor) {
        ColorStateList rippleDrawableColor = RippleUtils.convertToRippleDrawableColor(rippleColor);
        return new RippleDrawable(rippleDrawableColor, null, null);
    }

    public void setItemPaddingTop(int paddingTop) {
        if (this.itemPaddingTop != paddingTop) {
            this.itemPaddingTop = paddingTop;
            refreshChecked();
        }
    }

    public void setItemPaddingBottom(int paddingBottom) {
        if (this.itemPaddingBottom != paddingBottom) {
            this.itemPaddingBottom = paddingBottom;
            refreshChecked();
        }
    }

    public void setActiveIndicatorLabelPadding(int activeIndicatorLabelPadding) {
        if (this.activeIndicatorLabelPadding != activeIndicatorLabelPadding) {
            this.activeIndicatorLabelPadding = activeIndicatorLabelPadding;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.labelGroup.getLayoutParams();
            lp.topMargin = activeIndicatorLabelPadding;
            if (this.expandedLabelGroup.getLayoutParams() != null) {
                LinearLayout.LayoutParams expandedLp = (LinearLayout.LayoutParams) this.expandedLabelGroup.getLayoutParams();
                expandedLp.rightMargin = getLayoutDirection() == 1 ? activeIndicatorLabelPadding : 0;
                expandedLp.leftMargin = getLayoutDirection() != 1 ? activeIndicatorLabelPadding : 0;
                requestLayout();
            }
        }
    }

    public void setIconLabelHorizontalSpacing(int iconLabelHorizontalSpacing) {
        if (this.iconLabelHorizontalSpacing != iconLabelHorizontalSpacing) {
            this.iconLabelHorizontalSpacing = iconLabelHorizontalSpacing;
            setExpandedLabelGroupMargins();
            requestLayout();
        }
    }

    public void setActiveIndicatorEnabled(boolean enabled) {
        this.activeIndicatorEnabled = enabled;
        refreshItemBackground();
        this.activeIndicatorView.setVisibility(enabled ? 0 : 8);
        requestLayout();
    }

    public void setItemGravity(int itemGravity) {
        this.itemGravity = itemGravity;
        requestLayout();
    }

    public void setActiveIndicatorWidth(int width) {
        this.activeIndicatorDesiredWidth = width;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorExpandedWidth(int width) {
        this.activeIndicatorExpandedDesiredWidth = width;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorExpandedHeight(int height) {
        this.activeIndicatorExpandedDesiredHeight = height;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorExpandedPadding(Rect itemActiveIndicatorExpandedPadding) {
        this.itemActiveIndicatorExpandedPadding = itemActiveIndicatorExpandedPadding;
    }

    public void updateActiveIndicatorLayoutParams(int availableWidth) {
        if (availableWidth <= 0 && getVisibility() == 0) {
            return;
        }
        int newWidth = Math.min(this.activeIndicatorDesiredWidth, availableWidth - (this.activeIndicatorMarginHorizontal * 2));
        int newHeight = this.activeIndicatorDesiredHeight;
        if (this.itemIconGravity == 1) {
            int adjustedAvailableWidth = availableWidth - (this.activeIndicatorExpandedMarginHorizontal * 2);
            if (this.activeIndicatorExpandedDesiredWidth == -1) {
                newWidth = adjustedAvailableWidth;
            } else if (this.activeIndicatorExpandedDesiredWidth == -2) {
                newWidth = this.contentContainer.getMeasuredWidth();
            } else {
                newWidth = Math.min(this.activeIndicatorExpandedDesiredWidth, adjustedAvailableWidth);
            }
            newHeight = Math.max(this.activeIndicatorExpandedDesiredHeight, this.innerContentContainer.getMeasuredHeight());
        }
        FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
        indicatorParams.height = isActiveIndicatorResizeableAndUnlabeled() ? newWidth : newHeight;
        indicatorParams.width = Math.max(0, newWidth);
        this.activeIndicatorView.setLayoutParams(indicatorParams);
    }

    private boolean isActiveIndicatorResizeableAndUnlabeled() {
        return this.activeIndicatorResizeable && this.labelVisibilityMode == 2;
    }

    public void setActiveIndicatorHeight(int height) {
        this.activeIndicatorDesiredHeight = height;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorMarginHorizontal(int marginHorizontal) {
        this.activeIndicatorMarginHorizontal = marginHorizontal;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorExpandedMarginHorizontal(int marginHorizontal) {
        this.activeIndicatorExpandedMarginHorizontal = marginHorizontal;
        if (this.itemIconGravity == 1) {
            setPadding(marginHorizontal, 0, marginHorizontal, 0);
        }
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public Drawable getActiveIndicatorDrawable() {
        return this.activeIndicatorView.getBackground();
    }

    public void setActiveIndicatorDrawable(Drawable activeIndicatorDrawable) {
        this.activeIndicatorView.setBackground(activeIndicatorDrawable);
        refreshItemBackground();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.activeIndicatorEnabled) {
            this.iconContainer.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setActiveIndicatorResizeable(boolean resizeable) {
        this.activeIndicatorResizeable = resizeable;
    }

    void setBadge(BadgeDrawable badgeDrawable) {
        if (this.badgeDrawable == badgeDrawable) {
            return;
        }
        if (hasBadge() && this.icon != null) {
            Log.w("NavigationBar", "Multiple badges shouldn't be attached to one item.");
            tryRemoveBadgeFromAnchor(this.icon);
        }
        this.badgeDrawable = badgeDrawable;
        badgeDrawable.setBadgeFixedEdge(this.badgeFixedEdge);
        if (this.icon != null) {
            tryAttachBadgeToAnchor(this.icon);
        }
    }

    public BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    void removeBadge() {
        tryRemoveBadgeFromAnchor(this.icon);
    }

    private boolean hasBadge() {
        return this.badgeDrawable != null;
    }

    private void tryUpdateBadgeBounds(View anchorView) {
        if (!hasBadge()) {
            return;
        }
        BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchorView, null);
    }

    private void tryAttachBadgeToAnchor(View anchorView) {
        if (hasBadge() && anchorView != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView);
        }
    }

    private void tryRemoveBadgeFromAnchor(View anchorView) {
        if (!hasBadge()) {
            return;
        }
        if (anchorView != null) {
            setClipChildren(true);
            setClipToPadding(true);
            BadgeUtils.detachBadgeDrawable(this.badgeDrawable, anchorView);
        }
        this.badgeDrawable = null;
    }

    private int getSuggestedIconWidth() {
        int badgeWidth;
        if (this.badgeDrawable == null) {
            badgeWidth = 0;
        } else {
            badgeWidth = this.badgeDrawable.getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
        }
        LinearLayout.LayoutParams iconContainerParams = (LinearLayout.LayoutParams) this.iconContainer.getLayoutParams();
        return Math.max(badgeWidth, iconContainerParams.leftMargin) + this.icon.getMeasuredWidth() + Math.max(badgeWidth, iconContainerParams.rightMargin);
    }

    protected int getItemBackgroundResId() {
        return com.google.android.material.R.drawable.mtrl_navigation_bar_item_background;
    }

    protected int getItemDefaultMarginResId() {
        return com.google.android.material.R.dimen.mtrl_navigation_bar_item_default_margin;
    }

    private static class ActiveIndicatorTransform {
        private static final float ALPHA_FRACTION = 0.2f;
        private static final float SCALE_X_HIDDEN = 0.4f;
        private static final float SCALE_X_SHOWN = 1.0f;

        private ActiveIndicatorTransform() {
        }

        protected float calculateAlpha(float progress, float targetValue) {
            float startAlphaFraction = targetValue == 0.0f ? 0.8f : 0.0f;
            float endAlphaFraction = targetValue == 0.0f ? 1.0f : 0.2f;
            return AnimationUtils.lerp(0.0f, 1.0f, startAlphaFraction, endAlphaFraction, progress);
        }

        protected float calculateScaleX(float progress) {
            return AnimationUtils.lerp(SCALE_X_HIDDEN, 1.0f, progress);
        }

        protected float calculateScaleY(float progress) {
            return 1.0f;
        }

        public void updateForProgress(float progress, float targetValue, View indicator) {
            indicator.setScaleX(calculateScaleX(progress));
            indicator.setScaleY(calculateScaleY(progress));
            indicator.setAlpha(calculateAlpha(progress, targetValue));
        }
    }

    private static class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {
        private ActiveIndicatorUnlabeledTransform() {
            super();
        }

        @Override // com.google.android.material.navigation.NavigationBarItemView.ActiveIndicatorTransform
        protected float calculateScaleY(float progress) {
            return calculateScaleX(progress);
        }
    }
}
