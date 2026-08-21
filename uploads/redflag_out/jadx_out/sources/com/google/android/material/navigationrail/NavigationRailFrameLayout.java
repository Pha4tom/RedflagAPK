package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public class NavigationRailFrameLayout extends FrameLayout {
    int paddingTop;
    boolean scrollingEnabled;

    public NavigationRailFrameLayout(Context context) {
        super(context);
        this.paddingTop = 0;
        this.scrollingEnabled = false;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        this.scrollingEnabled = scrollingEnabled;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int totalHeaderHeight = 0;
        View menuView = getChildAt(0);
        int menuHeightSpec = heightMeasureSpec;
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (childCount > 1) {
            View headerView = getChildAt(0);
            measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
            FrameLayout.LayoutParams headerLp = (FrameLayout.LayoutParams) headerView.getLayoutParams();
            totalHeaderHeight = headerView.getMeasuredHeight() + headerLp.bottomMargin + headerLp.topMargin;
            int maxMenuHeight = (height - totalHeaderHeight) - this.paddingTop;
            menuView = getChildAt(1);
            if (!this.scrollingEnabled) {
                menuHeightSpec = View.MeasureSpec.makeMeasureSpec(maxMenuHeight, Integer.MIN_VALUE);
            }
        }
        FrameLayout.LayoutParams menuLp = (FrameLayout.LayoutParams) menuView.getLayoutParams();
        measureChild(menuView, widthMeasureSpec, menuHeightSpec);
        int totalMenuHeight = menuView.getMeasuredHeight() + menuLp.bottomMargin + menuLp.topMargin;
        int totalHeight = Math.max(height, this.paddingTop + totalHeaderHeight + totalMenuHeight);
        setMeasuredDimension(getMeasuredWidth(), totalHeight);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        int y = this.paddingTop;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
            int y2 = Math.max(y, child.getTop()) + lp.topMargin;
            child.layout(child.getLeft(), y2, child.getRight(), child.getMeasuredHeight() + y2);
            y = y2 + child.getMeasuredHeight() + lp.bottomMargin;
        }
    }
}
