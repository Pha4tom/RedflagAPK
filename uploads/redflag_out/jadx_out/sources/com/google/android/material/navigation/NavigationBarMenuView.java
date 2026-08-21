package com.google.android.material.navigation;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    private static final int DEFAULT_COLLAPSED_MAX_COUNT = 7;
    private static final int NO_PADDING = -1;
    private static final int NO_SELECTED_ITEM = -1;
    private final SparseArray<BadgeDrawable> badgeDrawables;
    private NavigationBarMenuItemView[] buttons;
    private MenuItem checkedItem;
    private int collapsedMaxItemCount;
    private boolean dividersEnabled;
    private boolean expanded;
    private int horizontalItemTextAppearanceActive;
    private int horizontalItemTextAppearanceInactive;
    private int iconLabelHorizontalSpacing;
    private ColorStateList itemActiveIndicatorColor;
    private boolean itemActiveIndicatorEnabled;
    private int itemActiveIndicatorExpandedHeight;
    private int itemActiveIndicatorExpandedMarginHorizontal;
    private final Rect itemActiveIndicatorExpandedPadding;
    private int itemActiveIndicatorExpandedWidth;
    private int itemActiveIndicatorHeight;
    private int itemActiveIndicatorLabelPadding;
    private int itemActiveIndicatorMarginHorizontal;
    private boolean itemActiveIndicatorResizeable;
    private ShapeAppearanceModel itemActiveIndicatorShapeAppearance;
    private int itemActiveIndicatorWidth;
    private Drawable itemBackground;
    private int itemBackgroundRes;
    private int itemGravity;
    private int itemIconGravity;
    private int itemIconSize;
    private ColorStateList itemIconTint;
    private int itemPaddingBottom;
    private int itemPaddingTop;
    private Pools.Pool<NavigationBarItemView> itemPool;
    private int itemPoolSize;
    private ColorStateList itemRippleColor;
    private int itemTextAppearanceActive;
    private boolean itemTextAppearanceActiveBoldEnabled;
    private int itemTextAppearanceInactive;
    private final ColorStateList itemTextColorDefault;
    private ColorStateList itemTextColorFromUser;
    private int labelMaxLines;
    private int labelVisibilityMode;
    private boolean measurePaddingFromLabelBaseline;
    private NavigationBarMenuBuilder menu;
    private final View.OnClickListener onClickListener;
    private final SparseArray<View.OnTouchListener> onTouchListeners;
    private NavigationBarPresenter presenter;
    private boolean scaleLabelWithFont;
    private int selectedItemId;
    private int selectedItemPosition;
    private final TransitionSet set;
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-16842910};

    protected abstract NavigationBarItemView createNavigationBarItemView(Context context);

    public NavigationBarMenuView(Context context) {
        super(context);
        this.onTouchListeners = new SparseArray<>();
        this.selectedItemId = -1;
        this.selectedItemPosition = -1;
        this.badgeDrawables = new SparseArray<>();
        this.itemPaddingTop = -1;
        this.itemPaddingBottom = -1;
        this.itemActiveIndicatorLabelPadding = -1;
        this.iconLabelHorizontalSpacing = -1;
        this.itemGravity = 49;
        this.itemActiveIndicatorResizeable = false;
        this.labelMaxLines = 1;
        this.itemPoolSize = 0;
        this.checkedItem = null;
        this.collapsedMaxItemCount = 7;
        this.dividersEnabled = false;
        this.itemActiveIndicatorExpandedPadding = new Rect();
        this.itemTextColorDefault = createDefaultColorStateList(R.attr.textColorSecondary);
        if (isInEditMode()) {
            this.set = null;
        } else {
            this.set = new AutoTransition();
            this.set.setOrdering(0);
            this.set.excludeTarget(TextView.class, true);
            this.set.setDuration(MotionUtils.resolveThemeDuration(getContext(), com.google.android.material.R.attr.motionDurationMedium4, getResources().getInteger(com.google.android.material.R.integer.material_motion_duration_long_1)));
            this.set.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.google.android.material.R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            this.set.addTransition(new TextScale());
        }
        this.onClickListener = new View.OnClickListener() { // from class: com.google.android.material.navigation.NavigationBarMenuView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavigationBarItemView itemView = (NavigationBarItemView) v;
                MenuItem item = itemView.getItemData();
                boolean result = NavigationBarMenuView.this.menu.performItemAction(item, NavigationBarMenuView.this.presenter, 0);
                if (item == null || !item.isCheckable()) {
                    return;
                }
                if (!result || item.isChecked()) {
                    NavigationBarMenuView.this.setCheckedItem(item);
                }
            }
        };
        setImportantForAccessibility(1);
    }

    public void setCheckedItem(MenuItem checkedItem) {
        if (this.checkedItem == checkedItem || !checkedItem.isCheckable()) {
            return;
        }
        if (this.checkedItem != null && this.checkedItem.isChecked()) {
            this.checkedItem.setChecked(false);
        }
        checkedItem.setChecked(true);
        this.checkedItem = checkedItem;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                item.setExpanded(expanded);
            }
        }
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public void initialize(MenuBuilder menu) {
        this.menu = new NavigationBarMenuBuilder(menu);
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getCurrentVisibleContentItemCount(), false, 1));
    }

    public void setIconTintList(ColorStateList tint) {
        this.itemIconTint = tint;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconTintList(tint);
                }
            }
        }
    }

    public ColorStateList getIconTintList() {
        return this.itemIconTint;
    }

    public void setItemIconSize(int iconSize) {
        this.itemIconSize = iconSize;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconSize(iconSize);
                }
            }
        }
    }

    public int getItemIconSize() {
        return this.itemIconSize;
    }

    public void setItemTextColor(ColorStateList color) {
        this.itemTextColorFromUser = color;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextColor(color);
                }
            }
        }
    }

    public ColorStateList getItemTextColor() {
        return this.itemTextColorFromUser;
    }

    public void setItemTextAppearanceInactive(int textAppearanceRes) {
        this.itemTextAppearanceInactive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceInactive(textAppearanceRes);
                }
            }
        }
    }

    public int getItemTextAppearanceInactive() {
        return this.itemTextAppearanceInactive;
    }

    public void setItemTextAppearanceActive(int textAppearanceRes) {
        this.itemTextAppearanceActive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceActive(textAppearanceRes);
                }
            }
        }
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.itemTextAppearanceActiveBoldEnabled = isBold;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceActiveBoldEnabled(isBold);
                }
            }
        }
    }

    public int getItemTextAppearanceActive() {
        return this.itemTextAppearanceActive;
    }

    public void setHorizontalItemTextAppearanceInactive(int textAppearanceRes) {
        this.horizontalItemTextAppearanceInactive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setHorizontalTextAppearanceInactive(textAppearanceRes);
                }
            }
        }
    }

    public int getHorizontalItemTextAppearanceInactive() {
        return this.horizontalItemTextAppearanceInactive;
    }

    public void setHorizontalItemTextAppearanceActive(int textAppearanceRes) {
        this.horizontalItemTextAppearanceActive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setHorizontalTextAppearanceActive(textAppearanceRes);
                }
            }
        }
    }

    public int getHorizontalItemTextAppearanceActive() {
        return this.horizontalItemTextAppearanceActive;
    }

    public void setItemBackgroundRes(int background) {
        this.itemBackgroundRes = background;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemBackground(background);
                }
            }
        }
    }

    public int getItemPaddingTop() {
        return this.itemPaddingTop;
    }

    public void setItemPaddingTop(int paddingTop) {
        this.itemPaddingTop = paddingTop;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemPaddingTop(paddingTop);
                }
            }
        }
    }

    public int getItemPaddingBottom() {
        return this.itemPaddingBottom;
    }

    public void setItemPaddingBottom(int paddingBottom) {
        this.itemPaddingBottom = paddingBottom;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemPaddingBottom(this.itemPaddingBottom);
                }
            }
        }
    }

    public void setMeasurePaddingFromLabelBaseline(boolean measurePaddingFromLabelBaseline) {
        this.measurePaddingFromLabelBaseline = measurePaddingFromLabelBaseline;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setMeasureBottomPaddingFromLabelBaseline(measurePaddingFromLabelBaseline);
                }
            }
        }
    }

    public void setLabelFontScalingEnabled(boolean scaleLabelWithFont) {
        this.scaleLabelWithFont = scaleLabelWithFont;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setLabelFontScalingEnabled(scaleLabelWithFont);
                }
            }
        }
    }

    public boolean getScaleLabelTextWithFont() {
        return this.scaleLabelWithFont;
    }

    public void setLabelMaxLines(int labelMaxLines) {
        this.labelMaxLines = labelMaxLines;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setLabelMaxLines(labelMaxLines);
                }
            }
        }
    }

    public int getLabelMaxLines() {
        return this.labelMaxLines;
    }

    public int getActiveIndicatorLabelPadding() {
        return this.itemActiveIndicatorLabelPadding;
    }

    public void setActiveIndicatorLabelPadding(int activeIndicatorLabelPadding) {
        this.itemActiveIndicatorLabelPadding = activeIndicatorLabelPadding;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorLabelPadding(activeIndicatorLabelPadding);
                }
            }
        }
    }

    public int getIconLabelHorizontalSpacing() {
        return this.iconLabelHorizontalSpacing;
    }

    public void setIconLabelHorizontalSpacing(int iconLabelHorizontalSpacing) {
        this.iconLabelHorizontalSpacing = iconLabelHorizontalSpacing;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconLabelHorizontalSpacing(iconLabelHorizontalSpacing);
                }
            }
        }
    }

    public boolean getItemActiveIndicatorEnabled() {
        return this.itemActiveIndicatorEnabled;
    }

    public void setItemActiveIndicatorEnabled(boolean enabled) {
        this.itemActiveIndicatorEnabled = enabled;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorEnabled(enabled);
                }
            }
        }
    }

    public int getItemActiveIndicatorWidth() {
        return this.itemActiveIndicatorWidth;
    }

    public void setItemActiveIndicatorWidth(int width) {
        this.itemActiveIndicatorWidth = width;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorWidth(width);
                }
            }
        }
    }

    public int getItemActiveIndicatorHeight() {
        return this.itemActiveIndicatorHeight;
    }

    public void setItemActiveIndicatorHeight(int height) {
        this.itemActiveIndicatorHeight = height;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorHeight(height);
                }
            }
        }
    }

    public void setItemGravity(int itemGravity) {
        this.itemGravity = itemGravity;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemGravity(itemGravity);
                }
            }
        }
    }

    public int getItemGravity() {
        return this.itemGravity;
    }

    public int getItemActiveIndicatorExpandedWidth() {
        return this.itemActiveIndicatorExpandedWidth;
    }

    public void setItemActiveIndicatorExpandedWidth(int width) {
        this.itemActiveIndicatorExpandedWidth = width;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedWidth(width);
                }
            }
        }
    }

    public int getItemActiveIndicatorExpandedHeight() {
        return this.itemActiveIndicatorExpandedHeight;
    }

    public void setItemActiveIndicatorExpandedHeight(int height) {
        this.itemActiveIndicatorExpandedHeight = height;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedHeight(height);
                }
            }
        }
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.itemActiveIndicatorMarginHorizontal;
    }

    public void setItemActiveIndicatorMarginHorizontal(int marginHorizontal) {
        this.itemActiveIndicatorMarginHorizontal = marginHorizontal;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorMarginHorizontal(marginHorizontal);
                }
            }
        }
    }

    public int getItemActiveIndicatorExpandedMarginHorizontal() {
        return this.itemActiveIndicatorExpandedMarginHorizontal;
    }

    public void setItemActiveIndicatorExpandedMarginHorizontal(int marginHorizontal) {
        this.itemActiveIndicatorExpandedMarginHorizontal = marginHorizontal;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedMarginHorizontal(marginHorizontal);
                }
            }
        }
    }

    public void setItemActiveIndicatorExpandedPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.itemActiveIndicatorExpandedPadding.left = paddingLeft;
        this.itemActiveIndicatorExpandedPadding.top = paddingTop;
        this.itemActiveIndicatorExpandedPadding.right = paddingRight;
        this.itemActiveIndicatorExpandedPadding.bottom = paddingBottom;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedPadding(this.itemActiveIndicatorExpandedPadding);
                }
            }
        }
    }

    public ShapeAppearanceModel getItemActiveIndicatorShapeAppearance() {
        return this.itemActiveIndicatorShapeAppearance;
    }

    public void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel shapeAppearance) {
        this.itemActiveIndicatorShapeAppearance = shapeAppearance;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
                }
            }
        }
    }

    protected boolean isItemActiveIndicatorResizeable() {
        return this.itemActiveIndicatorResizeable;
    }

    protected void setItemActiveIndicatorResizeable(boolean resizeable) {
        this.itemActiveIndicatorResizeable = resizeable;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorResizeable(resizeable);
                }
            }
        }
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.itemActiveIndicatorColor;
    }

    public void setItemActiveIndicatorColor(ColorStateList csl) {
        this.itemActiveIndicatorColor = csl;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
                }
            }
        }
    }

    private Drawable createItemActiveIndicatorDrawable() {
        if (this.itemActiveIndicatorShapeAppearance != null && this.itemActiveIndicatorColor != null) {
            MaterialShapeDrawable drawable = new MaterialShapeDrawable(this.itemActiveIndicatorShapeAppearance);
            drawable.setFillColor(this.itemActiveIndicatorColor);
            return drawable;
        }
        return null;
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.itemBackgroundRes;
    }

    public void setItemBackground(Drawable background) {
        this.itemBackground = background;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemBackground(background);
                }
            }
        }
    }

    public void setItemRippleColor(ColorStateList itemRippleColor) {
        this.itemRippleColor = itemRippleColor;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemRippleColor(itemRippleColor);
                }
            }
        }
    }

    public ColorStateList getItemRippleColor() {
        return this.itemRippleColor;
    }

    public Drawable getItemBackground() {
        if (this.buttons != null && this.buttons.length > 0) {
            for (NavigationBarMenuItemView button : this.buttons) {
                if (button instanceof NavigationBarItemView) {
                    return ((NavigationBarItemView) button).getBackground();
                }
            }
        }
        return this.itemBackground;
    }

    public void setLabelVisibilityMode(int labelVisibilityMode) {
        this.labelVisibilityMode = labelVisibilityMode;
    }

    public int getLabelVisibilityMode() {
        return this.labelVisibilityMode;
    }

    public void setItemIconGravity(int itemIconGravity) {
        this.itemIconGravity = itemIconGravity;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemIconGravity(itemIconGravity);
                }
            }
        }
    }

    public int getItemIconGravity() {
        return this.itemIconGravity;
    }

    public void setItemOnTouchListener(int menuItemId, View.OnTouchListener onTouchListener) {
        if (onTouchListener == null) {
            this.onTouchListeners.remove(menuItemId);
        } else {
            this.onTouchListeners.put(menuItemId, onTouchListener);
        }
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if ((item instanceof NavigationBarItemView) && item.getItemData() != null && item.getItemData().getItemId() == menuItemId) {
                    ((NavigationBarItemView) item).setOnTouchListener(onTouchListener);
                }
            }
        }
    }

    public ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
    }

    public void setPresenter(NavigationBarPresenter presenter) {
        this.presenter = presenter;
    }

    private void releaseItemPool() {
        if (this.buttons != null && this.itemPool != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    this.itemPool.release((NavigationBarItemView) item);
                    ((NavigationBarItemView) item).clear();
                }
            }
        }
    }

    private NavigationBarItemView createMenuItem(int index, MenuItemImpl item, boolean shifting, boolean hideWhenCollapsed) {
        this.presenter.setUpdateSuspended(true);
        item.setCheckable(true);
        this.presenter.setUpdateSuspended(false);
        NavigationBarItemView child = getNewItem();
        child.setShifting(shifting);
        child.setLabelMaxLines(this.labelMaxLines);
        child.setIconTintList(this.itemIconTint);
        child.setIconSize(this.itemIconSize);
        child.setTextColor(this.itemTextColorDefault);
        child.setTextAppearanceInactive(this.itemTextAppearanceInactive);
        child.setTextAppearanceActive(this.itemTextAppearanceActive);
        child.setHorizontalTextAppearanceInactive(this.horizontalItemTextAppearanceInactive);
        child.setHorizontalTextAppearanceActive(this.horizontalItemTextAppearanceActive);
        child.setTextAppearanceActiveBoldEnabled(this.itemTextAppearanceActiveBoldEnabled);
        child.setTextColor(this.itemTextColorFromUser);
        if (this.itemPaddingTop != -1) {
            child.setItemPaddingTop(this.itemPaddingTop);
        }
        if (this.itemPaddingBottom != -1) {
            child.setItemPaddingBottom(this.itemPaddingBottom);
        }
        child.setMeasureBottomPaddingFromLabelBaseline(this.measurePaddingFromLabelBaseline);
        child.setLabelFontScalingEnabled(this.scaleLabelWithFont);
        if (this.itemActiveIndicatorLabelPadding != -1) {
            child.setActiveIndicatorLabelPadding(this.itemActiveIndicatorLabelPadding);
        }
        if (this.iconLabelHorizontalSpacing != -1) {
            child.setIconLabelHorizontalSpacing(this.iconLabelHorizontalSpacing);
        }
        child.setActiveIndicatorWidth(this.itemActiveIndicatorWidth);
        child.setActiveIndicatorHeight(this.itemActiveIndicatorHeight);
        child.setActiveIndicatorExpandedWidth(this.itemActiveIndicatorExpandedWidth);
        child.setActiveIndicatorExpandedHeight(this.itemActiveIndicatorExpandedHeight);
        child.setActiveIndicatorMarginHorizontal(this.itemActiveIndicatorMarginHorizontal);
        child.setItemGravity(this.itemGravity);
        child.setActiveIndicatorExpandedPadding(this.itemActiveIndicatorExpandedPadding);
        child.setActiveIndicatorExpandedMarginHorizontal(this.itemActiveIndicatorExpandedMarginHorizontal);
        child.setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
        child.setActiveIndicatorResizeable(this.itemActiveIndicatorResizeable);
        child.setActiveIndicatorEnabled(this.itemActiveIndicatorEnabled);
        if (this.itemBackground != null) {
            child.setItemBackground(this.itemBackground);
        } else {
            child.setItemBackground(this.itemBackgroundRes);
        }
        child.setItemRippleColor(this.itemRippleColor);
        child.setLabelVisibilityMode(this.labelVisibilityMode);
        child.setItemIconGravity(this.itemIconGravity);
        child.setOnlyShowWhenExpanded(hideWhenCollapsed);
        child.setExpanded(this.expanded);
        child.initialize(item, 0);
        child.setItemPosition(index);
        int itemId = item.getItemId();
        child.setOnTouchListener(this.onTouchListeners.get(itemId));
        child.setOnClickListener(this.onClickListener);
        if (this.selectedItemId != 0 && itemId == this.selectedItemId) {
            this.selectedItemPosition = index;
        }
        setBadgeIfNeeded(child);
        return child;
    }

    public void buildMenuView() {
        NavigationBarMenuItemView child;
        removeAllViews();
        releaseItemPool();
        this.presenter.setUpdateSuspended(true);
        this.menu.refreshItems();
        this.presenter.setUpdateSuspended(false);
        int contentItemCount = this.menu.getContentItemCount();
        if (contentItemCount == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            this.itemPool = null;
            return;
        }
        if (this.itemPool == null || this.itemPoolSize != contentItemCount) {
            this.itemPoolSize = contentItemCount;
            this.itemPool = new Pools.SynchronizedPool(contentItemCount);
        }
        removeUnusedBadges();
        int menuSize = this.menu.size();
        this.buttons = new NavigationBarMenuItemView[menuSize];
        int collapsedItemsSoFar = 0;
        int nextSubheaderItemCount = 0;
        boolean shifting = isShifting(this.labelVisibilityMode, getCurrentVisibleContentItemCount());
        for (int i = 0; i < menuSize; i++) {
            MenuItem menuItem = this.menu.getItemAt(i);
            if (menuItem instanceof DividerMenuItem) {
                child = new NavigationBarDividerView(getContext());
                child.setOnlyShowWhenExpanded(true);
                ((NavigationBarDividerView) child).setDividersEnabled(this.dividersEnabled);
            } else if (menuItem.hasSubMenu()) {
                if (nextSubheaderItemCount > 0) {
                    throw new IllegalArgumentException("Only one layer of submenu is supported; a submenu inside a submenu is not supported by the Navigation Bar.");
                }
                child = new NavigationBarSubheaderView(getContext());
                ((NavigationBarSubheaderView) child).setTextAppearance(this.horizontalItemTextAppearanceActive != 0 ? this.horizontalItemTextAppearanceActive : this.itemTextAppearanceActive);
                ((NavigationBarSubheaderView) child).setTextColor(this.itemTextColorFromUser);
                child.setOnlyShowWhenExpanded(true);
                child.initialize((MenuItemImpl) menuItem, 0);
                nextSubheaderItemCount = menuItem.getSubMenu().size();
            } else if (nextSubheaderItemCount > 0) {
                child = createMenuItem(i, (MenuItemImpl) menuItem, shifting, true);
                nextSubheaderItemCount--;
            } else {
                child = createMenuItem(i, (MenuItemImpl) menuItem, shifting, collapsedItemsSoFar >= this.collapsedMaxItemCount);
                collapsedItemsSoFar++;
            }
            if (!(menuItem instanceof DividerMenuItem) && menuItem.isCheckable() && this.selectedItemPosition == -1) {
                this.selectedItemPosition = i;
            }
            this.buttons[i] = child;
            addView((View) child);
        }
        this.selectedItemPosition = Math.min(menuSize - 1, this.selectedItemPosition);
        setCheckedItem(this.buttons[this.selectedItemPosition].getItemData());
    }

    private boolean isMenuStructureSame() {
        if (this.buttons == null || this.menu == null || this.menu.size() != this.buttons.length) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= this.buttons.length) {
                return true;
            }
            if ((this.menu.getItemAt(i) instanceof DividerMenuItem) && !(this.buttons[i] instanceof NavigationBarDividerView)) {
                return false;
            }
            boolean incorrectSubheaderType = this.menu.getItemAt(i).hasSubMenu() && !(this.buttons[i] instanceof NavigationBarSubheaderView);
            boolean incorrectItemType = (this.menu.getItemAt(i).hasSubMenu() || (this.buttons[i] instanceof NavigationBarItemView)) ? false : true;
            if (!(this.menu.getItemAt(i) instanceof DividerMenuItem) && (incorrectSubheaderType || incorrectItemType)) {
                break;
            }
            i++;
        }
        return false;
    }

    public void updateMenuView() {
        if (this.menu == null || this.buttons == null) {
            return;
        }
        this.presenter.setUpdateSuspended(true);
        this.menu.refreshItems();
        this.presenter.setUpdateSuspended(false);
        if (!isMenuStructureSame()) {
            buildMenuView();
            return;
        }
        int previousSelectedId = this.selectedItemId;
        int menuSize = this.menu.size();
        for (int i = 0; i < menuSize; i++) {
            MenuItem item = this.menu.getItemAt(i);
            if (item.isChecked()) {
                setCheckedItem(item);
                this.selectedItemId = item.getItemId();
                this.selectedItemPosition = i;
            }
        }
        int i2 = this.selectedItemId;
        if (previousSelectedId != i2 && this.set != null) {
            TransitionManager.beginDelayedTransition(this, this.set);
        }
        boolean shifting = isShifting(this.labelVisibilityMode, getCurrentVisibleContentItemCount());
        for (int i3 = 0; i3 < menuSize; i3++) {
            this.presenter.setUpdateSuspended(true);
            this.buttons[i3].setExpanded(this.expanded);
            if (this.buttons[i3] instanceof NavigationBarItemView) {
                NavigationBarItemView itemView = (NavigationBarItemView) this.buttons[i3];
                itemView.setLabelVisibilityMode(this.labelVisibilityMode);
                itemView.setItemIconGravity(this.itemIconGravity);
                itemView.setItemGravity(this.itemGravity);
                itemView.setShifting(shifting);
            }
            if (this.menu.getItemAt(i3) instanceof MenuItemImpl) {
                this.buttons[i3].initialize((MenuItemImpl) this.menu.getItemAt(i3), 0);
            }
            this.presenter.setUpdateSuspended(false);
        }
    }

    private NavigationBarItemView getNewItem() {
        NavigationBarItemView item = this.itemPool != null ? this.itemPool.acquire() : null;
        if (item == null) {
            return createNavigationBarItemView(getContext());
        }
        return item;
    }

    public void setSubmenuDividersEnabled(boolean dividersEnabled) {
        if (this.dividersEnabled == dividersEnabled) {
            return;
        }
        this.dividersEnabled = dividersEnabled;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView itemView : this.buttons) {
                if (itemView instanceof NavigationBarDividerView) {
                    ((NavigationBarDividerView) itemView).setDividersEnabled(dividersEnabled);
                }
            }
        }
    }

    public void setCollapsedMaxItemCount(int collapsedMaxCount) {
        this.collapsedMaxItemCount = collapsedMaxCount;
    }

    private int getCollapsedVisibleItemCount() {
        return Math.min(this.collapsedMaxItemCount, this.menu.getVisibleMainContentItemCount());
    }

    public int getCurrentVisibleContentItemCount() {
        return this.expanded ? this.menu.getVisibleContentItemCount() : getCollapsedVisibleItemCount();
    }

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    protected boolean isShifting(int labelVisibilityMode, int childCount) {
        return labelVisibilityMode == -1 ? childCount > 3 : labelVisibilityMode == 0;
    }

    void tryRestoreSelectedItemId(int itemId) {
        int size = this.menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = this.menu.getItemAt(i);
            if (itemId == item.getItemId()) {
                this.selectedItemId = itemId;
                this.selectedItemPosition = i;
                setCheckedItem(item);
                return;
            }
        }
    }

    SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.badgeDrawables;
    }

    void restoreBadgeDrawables(SparseArray<BadgeDrawable> badgeDrawables) {
        BadgeDrawable badge;
        for (int i = 0; i < badgeDrawables.size(); i++) {
            int key = badgeDrawables.keyAt(i);
            if (this.badgeDrawables.indexOfKey(key) < 0) {
                this.badgeDrawables.append(key, badgeDrawables.get(key));
            }
        }
        if (this.buttons != null) {
            for (NavigationBarMenuItemView itemView : this.buttons) {
                if ((itemView instanceof NavigationBarItemView) && (badge = this.badgeDrawables.get(((NavigationBarItemView) itemView).getId())) != null) {
                    ((NavigationBarItemView) itemView).setBadge(badge);
                }
            }
        }
    }

    public BadgeDrawable getBadge(int menuItemId) {
        return this.badgeDrawables.get(menuItemId);
    }

    BadgeDrawable getOrCreateBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(menuItemId);
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(getContext());
            this.badgeDrawables.put(menuItemId, badgeDrawable);
        }
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.setBadge(badgeDrawable);
        }
        return badgeDrawable;
    }

    void removeBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.removeBadge();
        }
        this.badgeDrawables.put(menuItemId, null);
    }

    private void setBadgeIfNeeded(NavigationBarItemView child) {
        BadgeDrawable badgeDrawable;
        int childId = child.getId();
        if (isValidId(childId) && (badgeDrawable = this.badgeDrawables.get(childId)) != null) {
            child.setBadge(badgeDrawable);
        }
    }

    private void removeUnusedBadges() {
        HashSet<Integer> activeKeys = new HashSet<>();
        for (int i = 0; i < this.menu.size(); i++) {
            activeKeys.add(Integer.valueOf(this.menu.getItemAt(i).getItemId()));
        }
        for (int i2 = 0; i2 < this.badgeDrawables.size(); i2++) {
            int key = this.badgeDrawables.keyAt(i2);
            if (!activeKeys.contains(Integer.valueOf(key))) {
                this.badgeDrawables.delete(key);
            }
        }
    }

    public NavigationBarItemView findItemView(int menuItemId) {
        validateMenuItemId(menuItemId);
        if (this.buttons != null) {
            for (NavigationBarMenuItemView itemView : this.buttons) {
                if ((itemView instanceof NavigationBarItemView) && ((NavigationBarItemView) itemView).getId() == menuItemId) {
                    return (NavigationBarItemView) itemView;
                }
            }
            return null;
        }
        return null;
    }

    protected int getSelectedItemPosition() {
        return this.selectedItemPosition;
    }

    protected NavigationBarMenuBuilder getMenu() {
        return this.menu;
    }

    private boolean isValidId(int viewId) {
        return viewId != -1;
    }

    private void validateMenuItemId(int viewId) {
        if (!isValidId(viewId)) {
            throw new IllegalArgumentException(viewId + " is not a valid view id");
        }
    }

    public void updateActiveIndicator(int availableWidth) {
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).updateActiveIndicatorLayoutParams(availableWidth);
                }
            }
        }
    }
}
