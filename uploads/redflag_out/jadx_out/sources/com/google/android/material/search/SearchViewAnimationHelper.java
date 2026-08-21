package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.BackEventCompat;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.FadeThroughUpdateListener;
import com.google.android.material.internal.MultiViewUpdateListener;
import com.google.android.material.internal.RectEvaluator;
import com.google.android.material.internal.ReversableAnimatedValueInterpolator;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialMainContainerBackHelper;
import com.google.android.material.search.SearchView;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class SearchViewAnimationHelper {
    private static final float CONTENT_FROM_SCALE = 0.95f;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS = 42;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_ALPHA_DURATION_MS = 83;
    private static final long HIDE_CONTENT_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_SCALE_DURATION_MS = 250;
    private static final long HIDE_DURATION_MS = 250;
    private static final long HIDE_TRANSLATE_DURATION_MS = 300;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS = 50;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 250;
    private static final long SHOW_CONTENT_ALPHA_DURATION_MS = 150;
    private static final long SHOW_CONTENT_ALPHA_START_DELAY_MS = 75;
    private static final long SHOW_CONTENT_SCALE_DURATION_MS = 300;
    private static final long SHOW_DURATION_MS = 300;
    private static final long SHOW_SCRIM_ALPHA_DURATION_MS = 100;
    private static final long SHOW_TRANSLATE_DURATION_MS = 350;
    private static final long SHOW_TRANSLATE_KEYBOARD_START_DELAY_MS = 150;
    private final MaterialMainContainerBackHelper backHelper;
    private AnimatorSet backProgressAnimatorSet;
    private final ImageButton clearButton;
    private final TouchObserverFrameLayout contentContainer;
    private final View divider;
    private final Toolbar dummyToolbar;
    private final EditText editText;
    private final FrameLayout headerContainer;
    private final ClippableRoundedCornerLayout rootView;
    private final View scrim;
    private SearchBar searchBar;
    private final TextView searchPrefix;
    private final SearchView searchView;
    private final LinearLayout textContainer;
    private final Toolbar toolbar;
    private final FrameLayout toolbarContainer;

    SearchViewAnimationHelper(SearchView searchView) {
        this.searchView = searchView;
        this.scrim = searchView.scrim;
        this.rootView = searchView.rootView;
        this.headerContainer = searchView.headerContainer;
        this.toolbarContainer = searchView.toolbarContainer;
        this.toolbar = searchView.toolbar;
        this.dummyToolbar = searchView.dummyToolbar;
        this.searchPrefix = searchView.searchPrefix;
        this.editText = searchView.editText;
        this.clearButton = searchView.clearButton;
        this.divider = searchView.divider;
        this.contentContainer = searchView.contentContainer;
        this.textContainer = searchView.textContainer;
        this.backHelper = new MaterialMainContainerBackHelper(this.rootView);
    }

    void setSearchBar(SearchBar searchBar) {
        this.searchBar = searchBar;
    }

    void show() {
        if (this.searchBar != null) {
            startShowAnimationExpand();
        } else {
            startShowAnimationTranslate();
        }
    }

    AnimatorSet hide() {
        if (this.searchBar != null) {
            return startHideAnimationCollapse();
        }
        return startHideAnimationTranslate();
    }

    private void startShowAnimationExpand() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.requestFocusAndShowKeyboardIfNeeded();
        }
        this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
        setUpDummyToolbarIfNeeded();
        this.editText.setText(this.searchBar.getText());
        this.editText.setSelection(this.editText.getText().length());
        this.rootView.setVisibility(4);
        this.rootView.post(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m232x94743afc();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startShowAnimationExpand$0$com-google-android-material-search-SearchViewAnimationHelper, reason: not valid java name */
    /* synthetic */ void m232x94743afc() {
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchBar.stopOnLoadAnimation();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet startHideAnimationCollapse() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    private void startShowAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            SearchView searchView = this.searchView;
            final SearchView searchView2 = this.searchView;
            Objects.requireNonNull(searchView2);
            searchView.postDelayed(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    searchView2.requestFocusAndShowKeyboardIfNeeded();
                }
            }, 150L);
        }
        this.rootView.setVisibility(4);
        this.rootView.post(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m233x4df249eb();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startShowAnimationTranslate$1$com-google-android-material-search-SearchViewAnimationHelper, reason: not valid java name */
    /* synthetic */ void m233x4df249eb() {
        this.rootView.setTranslationY(this.rootView.getHeight());
        AnimatorSet animatorSet = getTranslateAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet startHideAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getTranslateAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    private AnimatorSet getTranslateAnimatorSet(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(getTranslationYAnimator());
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorSet.setDuration(show ? SHOW_TRANSLATE_DURATION_MS : 300L);
        return animatorSet;
    }

    private Animator getTranslationYAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(this.rootView.getHeight(), 0.0f);
        animator.addUpdateListener(MultiViewUpdateListener.translationYListener(this.rootView));
        return animator;
    }

    private AnimatorSet getExpandCollapseAnimatorSet(final boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        boolean backProgress = this.backProgressAnimatorSet != null;
        if (!backProgress) {
            animatorSet.playTogether(getButtonsProgressAnimator(show), getButtonsTranslationAnimator(show));
        }
        animatorSet.playTogether(getScrimAlphaAnimator(show), getRootViewAnimator(show), getClearButtonAnimator(show), getContentAnimator(show), getHeaderContainerAnimator(show), getDummyToolbarAnimator(show), getActionMenuViewsAlphaAnimator(show), getEditTextAnimator(show), getSearchPrefixAnimator(show), getTextAnimator(show));
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 0.0f : 1.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 1.0f : 0.0f);
                SearchViewAnimationHelper.this.editText.setAlpha(1.0f);
                if (SearchViewAnimationHelper.this.searchBar != null) {
                    SearchViewAnimationHelper.this.searchBar.getTextView().setAlpha(1.0f);
                }
                SearchViewAnimationHelper.this.editText.setClipBounds(null);
                SearchViewAnimationHelper.this.rootView.resetClipBoundsAndCornerRadii();
                if (!show) {
                    SearchViewAnimationHelper.this.backHelper.clearExpandedCornerRadii();
                }
            }
        });
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentViewsAlpha(float alpha) {
        this.clearButton.setAlpha(alpha);
        this.divider.setAlpha(alpha);
        this.contentContainer.setAlpha(alpha);
        setActionMenuViewAlphaIfNeeded(alpha);
    }

    private void setActionMenuViewAlphaIfNeeded(float alpha) {
        ActionMenuView actionMenuView;
        if (this.searchView.isMenuItemsAnimated() && (actionMenuView = ToolbarUtils.getActionMenuView(this.toolbar)) != null) {
            actionMenuView.setAlpha(alpha);
        }
    }

    private Animator getScrimAlphaAnimator(boolean show) {
        TimeInterpolator interpolator = show ? AnimationUtils.LINEAR_INTERPOLATOR : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(show ? 300L : 250L);
        animator.setStartDelay(show ? SHOW_SCRIM_ALPHA_DURATION_MS : 0L);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, interpolator));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.scrim));
        return animator;
    }

    private Animator getRootViewAnimator(boolean show) {
        Rect toClipBounds;
        Rect fromClipBounds;
        Rect initialHideToClipBounds = this.backHelper.getInitialHideToClipBounds();
        Rect initialHideFromClipBounds = this.backHelper.getInitialHideFromClipBounds();
        if (initialHideToClipBounds != null) {
            toClipBounds = initialHideToClipBounds;
        } else {
            toClipBounds = ViewUtils.calculateRectFromBounds(this.searchView);
        }
        if (initialHideFromClipBounds != null) {
            fromClipBounds = initialHideFromClipBounds;
        } else {
            fromClipBounds = ViewUtils.calculateOffsetRectFromBounds(this.rootView, this.searchBar);
        }
        final Rect clipBounds = new Rect(fromClipBounds);
        final float fromCornerRadius = this.searchBar.getCornerSize();
        final float[] toCornerRadius = maxCornerRadii(this.rootView.getCornerRadii(), this.backHelper.getExpandedCornerRadii());
        ValueAnimator animator = ValueAnimator.ofObject(new RectEvaluator(clipBounds), fromClipBounds, toClipBounds);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m231xa183b80f(fromCornerRadius, toCornerRadius, clipBounds, valueAnimator);
            }
        });
        animator.setDuration(show ? 300L : 250L);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animator;
    }

    /* JADX INFO: renamed from: lambda$getRootViewAnimator$2$com-google-android-material-search-SearchViewAnimationHelper, reason: not valid java name */
    /* synthetic */ void m231xa183b80f(float fromCornerRadius, float[] toCornerRadius, Rect clipBounds, ValueAnimator valueAnimator) {
        float[] cornerRadii = lerpCornerRadii(fromCornerRadius, toCornerRadius, valueAnimator.getAnimatedFraction());
        this.rootView.updateClipBoundsAndCornerRadii(clipBounds, cornerRadii);
    }

    private static float[] maxCornerRadii(float[] startValue, float[] endValue) {
        return new float[]{Math.max(startValue[0], endValue[0]), Math.max(startValue[1], endValue[1]), Math.max(startValue[2], endValue[2]), Math.max(startValue[3], endValue[3]), Math.max(startValue[4], endValue[4]), Math.max(startValue[5], endValue[5]), Math.max(startValue[6], endValue[6]), Math.max(startValue[7], endValue[7])};
    }

    private static float[] lerpCornerRadii(float startValue, float[] endValue, float fraction) {
        return new float[]{AnimationUtils.lerp(startValue, endValue[0], fraction), AnimationUtils.lerp(startValue, endValue[1], fraction), AnimationUtils.lerp(startValue, endValue[2], fraction), AnimationUtils.lerp(startValue, endValue[3], fraction), AnimationUtils.lerp(startValue, endValue[4], fraction), AnimationUtils.lerp(startValue, endValue[5], fraction), AnimationUtils.lerp(startValue, endValue[6], fraction), AnimationUtils.lerp(startValue, endValue[7], fraction)};
    }

    private Animator getClearButtonAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(show ? SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS : HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS);
        animator.setStartDelay(show ? 250L : 0L);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.clearButton));
        return animator;
    }

    private AnimatorSet getButtonsProgressAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setDuration(show ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private AnimatorSet getButtonsTranslationAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addBackButtonTranslationAnimatorIfNeeded(animatorSet);
        addActionMenuViewAnimatorIfNeeded(animatorSet);
        animatorSet.setDuration(show ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private void addBackButtonTranslationAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton searchViewBackButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (searchViewBackButton == null) {
            return;
        }
        ImageButton searchBarBackButton = ToolbarUtils.getNavigationIconButton(this.searchBar);
        ValueAnimator backButtonAnimatorX = ValueAnimator.ofFloat(getTranslationXBetweenViews(searchBarBackButton, searchViewBackButton), 0.0f);
        backButtonAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(searchViewBackButton));
        ValueAnimator backButtonAnimatorY = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
        backButtonAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(searchViewBackButton));
        animatorSet.playTogether(backButtonAnimatorX, backButtonAnimatorY);
    }

    private void addBackButtonProgressAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton backButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (backButton == null) {
            return;
        }
        Drawable drawable = DrawableCompat.unwrap(backButton.getDrawable());
        if (this.searchView.isAnimatedNavigationIcon()) {
            addDrawerArrowDrawableAnimatorIfNeeded(animatorSet, drawable);
            addFadeThroughDrawableAnimatorIfNeeded(animatorSet, drawable);
            addBackButtonAnimatorIfNeeded(animatorSet, backButton);
            return;
        }
        setFullDrawableProgressIfNeeded(drawable);
    }

    private void addBackButtonAnimatorIfNeeded(AnimatorSet animatorSet, final ImageButton backButton) {
        if (this.searchBar == null || this.searchBar.getNavigationIcon() != null) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                backButton.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        animatorSet.playTogether(animator);
    }

    private void addDrawerArrowDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            final DrawerArrowDrawable drawerArrowDrawable = (DrawerArrowDrawable) drawable;
            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    drawerArrowDrawable.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            animatorSet.playTogether(animator);
        }
    }

    private void addFadeThroughDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof FadeThroughDrawable) {
            final FadeThroughDrawable fadeThroughDrawable = (FadeThroughDrawable) drawable;
            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    fadeThroughDrawable.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            animatorSet.playTogether(animator);
        }
    }

    private void setFullDrawableProgressIfNeeded(Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            ((DrawerArrowDrawable) drawable).setProgress(1.0f);
        }
        if (drawable instanceof FadeThroughDrawable) {
            ((FadeThroughDrawable) drawable).setProgress(1.0f);
        }
    }

    private void addActionMenuViewAnimatorIfNeeded(AnimatorSet animatorSet) {
        ActionMenuView searchViewActionMenuView = ToolbarUtils.getActionMenuView(this.toolbar);
        if (searchViewActionMenuView == null) {
            return;
        }
        ActionMenuView searchBarActionMenuView = ToolbarUtils.getActionMenuView(this.searchBar);
        ValueAnimator actionMenuViewAnimatorX = ValueAnimator.ofFloat(getTranslationXBetweenViews(searchBarActionMenuView, searchViewActionMenuView), 0.0f);
        actionMenuViewAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(searchViewActionMenuView));
        ValueAnimator actionMenuViewAnimatorY = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
        actionMenuViewAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(searchViewActionMenuView));
        animatorSet.playTogether(actionMenuViewAnimatorX, actionMenuViewAnimatorY);
    }

    private Animator getDummyToolbarAnimator(boolean show) {
        return getTranslationAnimator(show, this.dummyToolbar, getFromTranslationXEnd(this.dummyToolbar), getFromTranslationY());
    }

    private Animator getHeaderContainerAnimator(boolean show) {
        return getTranslationAnimator(show, this.headerContainer, getFromTranslationXEnd(this.headerContainer), getFromTranslationY());
    }

    private Animator getActionMenuViewsAlphaAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(show ? 300L : 250L);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.searchView.isMenuItemsAnimated()) {
            ActionMenuView dummyActionMenuView = ToolbarUtils.getActionMenuView(this.dummyToolbar);
            ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(this.toolbar);
            animator.addUpdateListener(new FadeThroughUpdateListener(dummyActionMenuView, actionMenuView));
        }
        return animator;
    }

    private Animator getSearchPrefixAnimator(boolean show) {
        return getTranslationAnimatorForText(show, this.searchPrefix);
    }

    private Animator getEditTextAnimator(boolean show) {
        return getTranslationAnimatorForText(show, this.editText);
    }

    private AnimatorSet getTextAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addTextFadeAnimatorIfNeeded(animatorSet);
        addEditTextClipAnimator(animatorSet);
        animatorSet.setDuration(show ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        return animatorSet;
    }

    private void addEditTextClipAnimator(AnimatorSet animatorSet) {
        if (this.searchBar == null || !TextUtils.equals(this.editText.getText(), this.searchBar.getText())) {
            return;
        }
        final Rect editTextClipBounds = new Rect(0, 0, this.editText.getWidth(), this.editText.getHeight());
        ValueAnimator animator = ValueAnimator.ofInt(this.searchBar.getTextView().getWidth(), this.editText.getWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m229xc2294c40(editTextClipBounds, valueAnimator);
            }
        });
        animatorSet.playTogether(animator);
    }

    /* JADX INFO: renamed from: lambda$addEditTextClipAnimator$6$com-google-android-material-search-SearchViewAnimationHelper, reason: not valid java name */
    /* synthetic */ void m229xc2294c40(Rect editTextClipBounds, ValueAnimator animation) {
        editTextClipBounds.right = ((Integer) animation.getAnimatedValue()).intValue();
        this.editText.setClipBounds(editTextClipBounds);
    }

    private void addTextFadeAnimatorIfNeeded(AnimatorSet animatorSet) {
        if (this.searchBar == null || TextUtils.equals(this.editText.getText(), this.searchBar.getText())) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m230xbfb6df0b(valueAnimator);
            }
        });
        animatorSet.playTogether(animator);
    }

    /* JADX INFO: renamed from: lambda$addTextFadeAnimatorIfNeeded$7$com-google-android-material-search-SearchViewAnimationHelper, reason: not valid java name */
    /* synthetic */ void m230xbfb6df0b(ValueAnimator animation) {
        this.editText.setAlpha(((Float) animation.getAnimatedValue()).floatValue());
        this.searchBar.getTextView().setAlpha(1.0f - ((Float) animation.getAnimatedValue()).floatValue());
    }

    private Animator getTranslationAnimatorForText(boolean show, View v) {
        TextView textView = this.searchBar.getPlaceholderTextView();
        if (TextUtils.isEmpty(textView.getText()) || show) {
            textView = this.searchBar.getTextView();
        }
        int startX = getViewLeftFromSearchViewParent(textView) - (v.getLeft() + this.textContainer.getLeft());
        return getTranslationAnimator(show, v, startX, getFromTranslationY());
    }

    private int getViewLeftFromSearchViewParent(View v) {
        int left = v.getLeft();
        for (ViewParent viewParent = v.getParent(); (viewParent instanceof View) && viewParent != this.searchView.getParent(); viewParent = viewParent.getParent()) {
            left += ((View) viewParent).getLeft();
        }
        return left;
    }

    private int getViewTopFromSearchViewParent(View v) {
        int top = v.getTop();
        for (ViewParent viewParent = v.getParent(); (viewParent instanceof View) && viewParent != this.searchView.getParent(); viewParent = viewParent.getParent()) {
            top += ((View) viewParent).getTop();
        }
        return top;
    }

    private Animator getContentAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(getContentAlphaAnimator(show), getDividerAnimator(show), getContentScaleAnimator(show));
        return animatorSet;
    }

    private Animator getContentAlphaAnimator(boolean show) {
        ValueAnimator animatorAlpha = ValueAnimator.ofFloat(0.0f, 1.0f);
        animatorAlpha.setDuration(show ? 150L : HIDE_CONTENT_ALPHA_DURATION_MS);
        animatorAlpha.setStartDelay(show ? 75L : 0L);
        animatorAlpha.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animatorAlpha.addUpdateListener(MultiViewUpdateListener.alphaListener(this.divider, this.contentContainer));
        return animatorAlpha;
    }

    private Animator getDividerAnimator(boolean show) {
        float dividerTranslationY = (this.contentContainer.getHeight() * 0.050000012f) / 2.0f;
        ValueAnimator animatorDivider = ValueAnimator.ofFloat(dividerTranslationY, 0.0f);
        animatorDivider.setDuration(show ? 300L : 250L);
        animatorDivider.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorDivider.addUpdateListener(MultiViewUpdateListener.translationYListener(this.divider));
        return animatorDivider;
    }

    private Animator getContentScaleAnimator(boolean show) {
        ValueAnimator animatorScale = ValueAnimator.ofFloat(CONTENT_FROM_SCALE, 1.0f);
        animatorScale.setDuration(show ? 300L : 250L);
        animatorScale.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorScale.addUpdateListener(MultiViewUpdateListener.scaleListener(this.contentContainer));
        return animatorScale;
    }

    private Animator getTranslationAnimator(boolean show, View view, int startX, int startY) {
        ValueAnimator animatorX = ValueAnimator.ofFloat(startX, 0.0f);
        animatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(view));
        ValueAnimator animatorY = ValueAnimator.ofFloat(startY, 0.0f);
        animatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(show ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private int getTranslationXBetweenViews(View searchBarSubView, View searchViewSubView) {
        if (searchBarSubView == null) {
            int marginStart = ((ViewGroup.MarginLayoutParams) searchViewSubView.getLayoutParams()).getMarginStart();
            int paddingStart = this.searchBar.getPaddingStart();
            int searchBarLeft = getViewLeftFromSearchViewParent(this.searchBar);
            if (ViewUtils.isLayoutRtl(this.searchBar)) {
                return (((this.searchBar.getWidth() + searchBarLeft) + marginStart) - paddingStart) - this.searchView.getRight();
            }
            return (searchBarLeft - marginStart) + paddingStart;
        }
        return getViewLeftFromSearchViewParent(searchBarSubView) - getViewLeftFromSearchViewParent(searchViewSubView);
    }

    private int getFromTranslationXEnd(View view) {
        int marginEnd = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).getMarginEnd();
        int viewLeft = getViewLeftFromSearchViewParent(this.searchBar);
        if (ViewUtils.isLayoutRtl(this.searchBar)) {
            return viewLeft - marginEnd;
        }
        return ((this.searchBar.getWidth() + viewLeft) + marginEnd) - this.searchView.getWidth();
    }

    private int getFromTranslationY() {
        int toolbarMiddleY = this.toolbarContainer.getTop() + (this.toolbarContainer.getHeight() / 2);
        int searchBarMiddleY = getViewTopFromSearchViewParent(this.searchBar) + (this.searchBar.getHeight() / 2);
        return searchBarMiddleY - toolbarMiddleY;
    }

    private void setUpDummyToolbarIfNeeded() {
        Menu menu = this.dummyToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (this.searchBar.getMenuResId() != -1 && this.searchView.isMenuItemsAnimated()) {
            this.dummyToolbar.inflateMenu(this.searchBar.getMenuResId());
            setMenuItemsNotClickable(this.dummyToolbar);
            this.dummyToolbar.setVisibility(0);
            return;
        }
        this.dummyToolbar.setVisibility(8);
    }

    private void setMenuItemsNotClickable(Toolbar toolbar) {
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(toolbar);
        if (actionMenuView != null) {
            for (int i = 0; i < actionMenuView.getChildCount(); i++) {
                View menuItem = actionMenuView.getChildAt(i);
                menuItem.setClickable(false);
                menuItem.setFocusable(false);
                menuItem.setFocusableInTouchMode(false);
            }
        }
    }

    void startBackProgress(BackEventCompat backEvent) {
        this.backHelper.startBackProgress(backEvent, this.searchBar);
    }

    public void updateBackProgress(BackEventCompat backEvent) {
        if (backEvent.getProgress() <= 0.0f) {
            return;
        }
        this.backHelper.updateBackProgress(backEvent, this.searchBar, this.searchBar.getCornerSize());
        if (this.backProgressAnimatorSet == null) {
            if (this.searchView.isAdjustNothingSoftInputMode()) {
                this.searchView.clearFocusAndHideKeyboard();
            }
            if (!this.searchView.isAnimatedNavigationIcon()) {
                return;
            }
            this.backProgressAnimatorSet = getButtonsProgressAnimator(false);
            this.backProgressAnimatorSet.start();
            this.backProgressAnimatorSet.pause();
            return;
        }
        this.backProgressAnimatorSet.setCurrentPlayTime((long) (backEvent.getProgress() * this.backProgressAnimatorSet.getDuration()));
    }

    public BackEventCompat onHandleBackInvoked() {
        return this.backHelper.onHandleBackInvoked();
    }

    public void finishBackProgress() {
        AnimatorSet hideAnimatorSet = hide();
        long totalDuration = hideAnimatorSet.getTotalDuration();
        this.backHelper.finishBackProgress(totalDuration, this.searchBar);
        if (this.backProgressAnimatorSet != null) {
            getButtonsTranslationAnimator(false).start();
            this.backProgressAnimatorSet.resume();
        }
        this.backProgressAnimatorSet = null;
    }

    public void cancelBackProgress() {
        this.backHelper.cancelBackProgress(this.searchBar);
        if (this.backProgressAnimatorSet != null) {
            this.backProgressAnimatorSet.reverse();
        }
        this.backProgressAnimatorSet = null;
    }

    MaterialMainContainerBackHelper getBackHelper() {
        return this.backHelper;
    }
}
