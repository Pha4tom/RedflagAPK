package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/* JADX INFO: loaded from: classes.dex */
final class HideLeftViewOnScrollDelegate extends HideViewOnScrollDelegate {
    HideLeftViewOnScrollDelegate() {
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    int getViewEdge() {
        return 2;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    <V extends View> int getSize(V child, ViewGroup.MarginLayoutParams paramsCompat) {
        return child.getMeasuredWidth() + paramsCompat.leftMargin;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    <V extends View> void setAdditionalHiddenOffset(V child, int size, int additionalHiddenOffset) {
        child.setTranslationX(size - additionalHiddenOffset);
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    int getTargetTranslation() {
        return 0;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    <V extends View> void setViewTranslation(V child, int targetTranslation) {
        child.setTranslationX(-targetTranslation);
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    <V extends View> ViewPropertyAnimator getViewTranslationAnimator(V child, int targetTranslation) {
        return child.animate().translationX(-targetTranslation);
    }
}
