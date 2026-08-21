package com.google.android.material.navigationrail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

/* JADX INFO: loaded from: classes.dex */
class LabelMoveTransition extends Transition {
    private static final float HORIZONTAL_DISTANCE = -30.0f;
    private static final String LABEL_VISIBILITY = "NavigationRailLabelVisibility";

    LabelMoveTransition() {
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(LABEL_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(LABEL_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues != null && endValues != null && startValues.values.get(LABEL_VISIBILITY) != null && endValues.values.get(LABEL_VISIBILITY) != null) {
            if (((Integer) startValues.values.get(LABEL_VISIBILITY)).intValue() != 8 || ((Integer) endValues.values.get(LABEL_VISIBILITY)).intValue() != 0) {
                return super.createAnimator(sceneRoot, startValues, endValues);
            }
            final View view = endValues.view;
            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.navigationrail.LabelMoveTransition$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LabelMoveTransition.lambda$createAnimator$0(view, valueAnimator);
                }
            });
            return animator;
        }
        return super.createAnimator(sceneRoot, startValues, endValues);
    }

    static /* synthetic */ void lambda$createAnimator$0(View view, ValueAnimator animation) {
        float progress = animation.getAnimatedFraction();
        view.setTranslationX((1.0f - progress) * HORIZONTAL_DISTANCE);
    }
}
