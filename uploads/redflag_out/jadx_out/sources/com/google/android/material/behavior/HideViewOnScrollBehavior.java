package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public class HideViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int DEFAULT_ENTER_ANIMATION_DURATION_MS = 225;
    private static final int DEFAULT_EXIT_ANIMATION_DURATION_MS = 175;
    public static final int EDGE_BOTTOM = 1;
    public static final int EDGE_LEFT = 2;
    public static final int EDGE_RIGHT = 0;
    public static final int STATE_SCROLLED_IN = 2;
    public static final int STATE_SCROLLED_OUT = 1;
    private AccessibilityManager accessibilityManager;
    private int additionalHiddenOffset;
    private ViewPropertyAnimator currentAnimator;
    private int currentState;
    private boolean disableOnTouchExploration;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private HideViewOnScrollDelegate hideOnScrollViewDelegate;
    private final LinkedHashSet<OnScrollStateChangedListener> onScrollStateChangedListeners;
    private int size;
    private AccessibilityManager.TouchExplorationStateChangeListener touchExplorationListener;
    private boolean viewEdgeOverride;
    private static final int ENTER_ANIM_DURATION_ATTR = R.attr.motionDurationLong2;
    private static final int EXIT_ANIM_DURATION_ATTR = R.attr.motionDurationMedium4;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;

    public interface OnScrollStateChangedListener {
        void onStateChanged(View view, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollState {
    }

    public HideViewOnScrollBehavior() {
        this.disableOnTouchExploration = true;
        this.onScrollStateChangedListeners = new LinkedHashSet<>();
        this.size = 0;
        this.currentState = 2;
        this.additionalHiddenOffset = 0;
        this.viewEdgeOverride = false;
    }

    public HideViewOnScrollBehavior(int viewEdge) {
        this();
        setViewEdge(viewEdge);
    }

    public HideViewOnScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.disableOnTouchExploration = true;
        this.onScrollStateChangedListeners = new LinkedHashSet<>();
        this.size = 0;
        this.currentState = 2;
        this.additionalHiddenOffset = 0;
        this.viewEdgeOverride = false;
    }

    private void setViewEdge(V view, int layoutDirection) {
        if (this.viewEdgeOverride) {
            return;
        }
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        int viewGravity = params.gravity;
        if (isGravityBottom(viewGravity)) {
            setViewEdgeInternal(1);
        } else {
            setViewEdgeInternal(isGravityLeft(Gravity.getAbsoluteGravity(viewGravity, layoutDirection)) ? 2 : 0);
        }
    }

    public void setViewEdge(int viewEdge) {
        this.viewEdgeOverride = true;
        setViewEdgeInternal(viewEdge);
    }

    private void setViewEdgeInternal(int viewEdge) {
        if (this.hideOnScrollViewDelegate == null || this.hideOnScrollViewDelegate.getViewEdge() != viewEdge) {
            switch (viewEdge) {
                case 0:
                    this.hideOnScrollViewDelegate = new HideRightViewOnScrollDelegate();
                    return;
                case 1:
                    this.hideOnScrollViewDelegate = new HideBottomViewOnScrollDelegate();
                    return;
                case 2:
                    this.hideOnScrollViewDelegate = new HideLeftViewOnScrollDelegate();
                    return;
                default:
                    throw new IllegalArgumentException("Invalid view edge position value: " + viewEdge + ". Must be 0, 1 or 2.");
            }
        }
    }

    private boolean isGravityBottom(int viewGravity) {
        return viewGravity == 80 || viewGravity == 81;
    }

    private boolean isGravityLeft(int viewGravity) {
        return viewGravity == 3 || viewGravity == 19;
    }

    private void disableIfTouchExplorationEnabled(final V child) {
        if (this.accessibilityManager == null) {
            this.accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(child.getContext(), AccessibilityManager.class);
        }
        if (this.accessibilityManager != null && this.touchExplorationListener == null) {
            this.touchExplorationListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.google.android.material.behavior.HideViewOnScrollBehavior$$ExternalSyntheticLambda0
                @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                public final void onTouchExplorationStateChanged(boolean z) {
                    this.f$0.m155xa484d69d(child, z);
                }
            };
            this.accessibilityManager.addTouchExplorationStateChangeListener(this.touchExplorationListener);
            child.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.behavior.HideViewOnScrollBehavior.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                    if (HideViewOnScrollBehavior.this.touchExplorationListener != null && HideViewOnScrollBehavior.this.accessibilityManager != null) {
                        HideViewOnScrollBehavior.this.accessibilityManager.removeTouchExplorationStateChangeListener(HideViewOnScrollBehavior.this.touchExplorationListener);
                        HideViewOnScrollBehavior.this.touchExplorationListener = null;
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$disableIfTouchExplorationEnabled$0$com-google-android-material-behavior-HideViewOnScrollBehavior, reason: not valid java name */
    /* synthetic */ void m155xa484d69d(View child, boolean enabled) {
        if (this.disableOnTouchExploration && enabled && isScrolledOut()) {
            slideIn(child);
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        disableIfTouchExplorationEnabled(child);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        setViewEdge(child, layoutDirection);
        this.size = this.hideOnScrollViewDelegate.getSize(child, marginParams);
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(child.getContext(), ENTER_ANIM_DURATION_ATTR, DEFAULT_ENTER_ANIMATION_DURATION_MS);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(child.getContext(), EXIT_ANIM_DURATION_ATTR, DEFAULT_EXIT_ANIMATION_DURATION_MS);
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(child.getContext(), ENTER_EXIT_ANIM_EASING_ATTR, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(child.getContext(), ENTER_EXIT_ANIM_EASING_ATTR, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    public void setAdditionalHiddenOffset(V child, int offset) {
        this.additionalHiddenOffset = offset;
        if (this.currentState == 1) {
            this.hideOnScrollViewDelegate.setAdditionalHiddenOffset(child, this.size, this.additionalHiddenOffset);
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes == 2;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
        if (dyConsumed > 0) {
            slideOut(child);
        } else if (dyConsumed < 0) {
            slideIn(child);
        }
    }

    public boolean isScrolledIn() {
        return this.currentState == 2;
    }

    public void slideIn(V child) {
        slideIn(child, true);
    }

    public void slideIn(V child, boolean animate) {
        if (isScrolledIn()) {
            return;
        }
        if (this.currentAnimator != null) {
            this.currentAnimator.cancel();
            child.clearAnimation();
        }
        updateCurrentState(child, 2);
        int targetTranslation = this.hideOnScrollViewDelegate.getTargetTranslation();
        if (animate) {
            animateChildTo(child, targetTranslation, this.enterAnimDuration, this.enterAnimInterpolator);
        } else {
            this.hideOnScrollViewDelegate.setViewTranslation(child, targetTranslation);
        }
    }

    public boolean isScrolledOut() {
        return this.currentState == 1;
    }

    public void slideOut(V child) {
        slideOut(child, true);
    }

    public void slideOut(V child, boolean animate) {
        if (isScrolledOut()) {
            return;
        }
        if (this.disableOnTouchExploration && this.accessibilityManager != null && this.accessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        if (this.currentAnimator != null) {
            this.currentAnimator.cancel();
            child.clearAnimation();
        }
        updateCurrentState(child, 1);
        int targetTranslation = this.size + this.additionalHiddenOffset;
        if (animate) {
            animateChildTo(child, targetTranslation, this.exitAnimDuration, this.exitAnimInterpolator);
        } else {
            this.hideOnScrollViewDelegate.setViewTranslation(child, targetTranslation);
        }
    }

    private void updateCurrentState(V child, int state) {
        this.currentState = state;
        for (OnScrollStateChangedListener listener : this.onScrollStateChangedListeners) {
            listener.onStateChanged(child, this.currentState);
        }
    }

    private void animateChildTo(V child, int targetTranslation, long duration, TimeInterpolator interpolator) {
        this.currentAnimator = this.hideOnScrollViewDelegate.getViewTranslationAnimator(child, targetTranslation).setInterpolator(interpolator).setDuration(duration).setListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.behavior.HideViewOnScrollBehavior.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                HideViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }

    public void addOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.onScrollStateChangedListeners.add(listener);
    }

    public void removeOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.onScrollStateChangedListeners.remove(listener);
    }

    public void clearOnScrollStateChangedListeners() {
        this.onScrollStateChangedListeners.clear();
    }

    public void disableOnTouchExploration(boolean disableOnTouchExploration) {
        this.disableOnTouchExploration = disableOnTouchExploration;
    }

    public boolean isDisabledOnTouchExploration() {
        return this.disableOnTouchExploration;
    }

    public static <V extends View> HideViewOnScrollBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof HideViewOnScrollBehavior)) {
            throw new IllegalArgumentException("The view is not associated with HideViewOnScrollBehavior");
        }
        return (HideViewOnScrollBehavior) behavior;
    }
}
