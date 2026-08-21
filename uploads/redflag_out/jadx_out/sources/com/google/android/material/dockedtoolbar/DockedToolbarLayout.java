package com.google.android.material.dockedtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* JADX INFO: loaded from: classes.dex */
public class DockedToolbarLayout extends FrameLayout {
    private Boolean paddingBottomSystemWindowInsets;
    private Boolean paddingTopSystemWindowInsets;
    private static final String TAG = DockedToolbarLayout.class.getSimpleName();
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_DockedToolbar;

    public DockedToolbarLayout(Context context) {
        this(context, null);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dockedToolbarStyle);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, DEF_STYLE_RES);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, defStyleRes), attrs, defStyleAttr);
        Context context2 = getContext();
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, R.styleable.DockedToolbar, defStyleAttr, defStyleRes, new int[0]);
        if (attributes.hasValue(R.styleable.DockedToolbar_backgroundTint)) {
            int backgroundColor = attributes.getColor(R.styleable.DockedToolbar_backgroundTint, 0);
            ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attrs, defStyleAttr, defStyleRes).build();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(backgroundColor));
            setBackground(materialShapeDrawable);
        }
        int backgroundColor2 = R.styleable.DockedToolbar_paddingTopSystemWindowInsets;
        if (attributes.hasValue(backgroundColor2)) {
            this.paddingTopSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.DockedToolbar_paddingTopSystemWindowInsets, true));
        }
        if (attributes.hasValue(R.styleable.DockedToolbar_paddingBottomSystemWindowInsets)) {
            this.paddingBottomSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.DockedToolbar_paddingBottomSystemWindowInsets, true));
        }
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.dockedtoolbar.DockedToolbarLayout.1
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                if (DockedToolbarLayout.this.paddingTopSystemWindowInsets != null && DockedToolbarLayout.this.paddingBottomSystemWindowInsets != null && !DockedToolbarLayout.this.paddingTopSystemWindowInsets.booleanValue() && !DockedToolbarLayout.this.paddingBottomSystemWindowInsets.booleanValue()) {
                    return insets;
                }
                Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
                int bottomInset = systemBarInsets.bottom;
                int topInset = systemBarInsets.top;
                int bottomPadding = 0;
                int topPadding = 0;
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (DockedToolbarLayout.this.hasGravity(lp, 48) && DockedToolbarLayout.this.paddingTopSystemWindowInsets == null && DockedToolbarLayout.this.getFitsSystemWindows()) {
                    topPadding = topInset;
                }
                if (DockedToolbarLayout.this.hasGravity(lp, 80) && DockedToolbarLayout.this.paddingBottomSystemWindowInsets == null && DockedToolbarLayout.this.getFitsSystemWindows()) {
                    bottomPadding = bottomInset;
                }
                if (DockedToolbarLayout.this.paddingBottomSystemWindowInsets != null) {
                    bottomPadding = DockedToolbarLayout.this.paddingBottomSystemWindowInsets.booleanValue() ? bottomInset : 0;
                }
                if (DockedToolbarLayout.this.paddingTopSystemWindowInsets != null) {
                    topPadding = DockedToolbarLayout.this.paddingTopSystemWindowInsets.booleanValue() ? topInset : 0;
                }
                initialPadding.top += topPadding;
                initialPadding.bottom += bottomPadding;
                initialPadding.applyToView(view);
                return insets;
            }
        });
        setImportantForAccessibility(1);
        attributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasGravity(ViewGroup.LayoutParams lp, int gravity) {
        return lp instanceof CoordinatorLayout.LayoutParams ? (((CoordinatorLayout.LayoutParams) lp).gravity & gravity) == gravity : (lp instanceof FrameLayout.LayoutParams) && (((FrameLayout.LayoutParams) lp).gravity & gravity) == gravity;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(heightMeasureSpec) != 1073741824) {
            int childCount = getChildCount();
            int newHeight = Math.max(getMeasuredHeight(), getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom());
            for (int i = 0; i < childCount; i++) {
                measureChild(getChildAt(i), widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(newHeight, BasicMeasure.EXACTLY));
            }
            setMeasuredDimension(getMeasuredWidth(), newHeight);
        }
    }
}
