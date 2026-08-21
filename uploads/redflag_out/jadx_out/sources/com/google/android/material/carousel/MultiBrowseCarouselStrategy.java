package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private int keylineCount = 0;
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {1, 0};

    @Override // com.google.android.material.carousel.CarouselStrategy
    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        int[] smallCounts;
        boolean refreshArrangement;
        int carouselSize = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            carouselSize = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins = childLayoutParams.topMargin + childLayoutParams.bottomMargin;
        float measuredChildSize = child.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            childMargins = childLayoutParams.leftMargin + childLayoutParams.rightMargin;
            measuredChildSize = child.getMeasuredWidth();
        }
        float smallChildSizeMin = getSmallItemSizeMin() + childMargins;
        float smallChildSizeMax = Math.max(getSmallItemSizeMax() + childMargins, smallChildSizeMin);
        float targetLargeChildSize = Math.min(measuredChildSize + childMargins, carouselSize);
        float targetSmallChildSize = MathUtils.clamp((measuredChildSize / 3.0f) + childMargins, smallChildSizeMin + childMargins, smallChildSizeMax + childMargins);
        float targetMediumChildSize = (targetLargeChildSize + targetSmallChildSize) / 2.0f;
        int[] smallCounts2 = SMALL_COUNTS;
        if (carouselSize <= smallChildSizeMin * 2.0f) {
            smallCounts2 = new int[]{0};
        }
        int[] mediumCounts = MEDIUM_COUNTS;
        if (carousel.getCarouselAlignment() != 1) {
            smallCounts = smallCounts2;
        } else {
            int[] smallCounts3 = doubleCounts(smallCounts2);
            mediumCounts = doubleCounts(mediumCounts);
            smallCounts = smallCounts3;
        }
        float minAvailableLargeSpace = (carouselSize - (CarouselStrategyHelper.maxValue(mediumCounts) * targetMediumChildSize)) - (CarouselStrategyHelper.maxValue(smallCounts) * smallChildSizeMax);
        int largeCountMin = (int) Math.max(1.0d, Math.floor(minAvailableLargeSpace / targetLargeChildSize));
        int largeCountMax = (int) Math.ceil(carouselSize / targetLargeChildSize);
        int[] largeCounts = new int[(largeCountMax - largeCountMin) + 1];
        for (int i = 0; i < largeCounts.length; i++) {
            largeCounts[i] = largeCountMax - i;
        }
        Arrangement arrangement = Arrangement.findLowestCostArrangement(carouselSize, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, smallCounts, targetMediumChildSize, mediumCounts, targetLargeChildSize, largeCounts);
        this.keylineCount = arrangement.getItemCount();
        boolean refreshArrangement2 = ensureArrangementFitsItemCount(arrangement, carousel.getItemCount());
        if (arrangement.mediumCount == 0 && arrangement.smallCount == 0 && carouselSize > smallChildSizeMin * 2.0f) {
            arrangement.smallCount = 1;
            refreshArrangement = true;
        } else {
            refreshArrangement = refreshArrangement2;
        }
        if (refreshArrangement) {
            arrangement = Arrangement.findLowestCostArrangement(carouselSize, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, new int[]{arrangement.smallCount}, targetMediumChildSize, new int[]{arrangement.mediumCount}, targetLargeChildSize, new int[]{arrangement.largeCount});
        }
        return CarouselStrategyHelper.createKeylineState(child.getContext(), childMargins, carouselSize, arrangement, carousel.getCarouselAlignment());
    }

    boolean ensureArrangementFitsItemCount(Arrangement arrangement, int carouselItemCount) {
        int keylineSurplus = arrangement.getItemCount() - carouselItemCount;
        boolean changed = keylineSurplus > 0 && (arrangement.smallCount > 0 || arrangement.mediumCount > 1);
        while (keylineSurplus > 0) {
            if (arrangement.smallCount > 0) {
                arrangement.smallCount--;
            } else if (arrangement.mediumCount > 1) {
                arrangement.mediumCount--;
            }
            keylineSurplus--;
        }
        return changed;
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    public boolean shouldRefreshKeylineState(Carousel carousel, int oldItemCount) {
        return (oldItemCount < this.keylineCount && carousel.getItemCount() >= this.keylineCount) || (oldItemCount >= this.keylineCount && carousel.getItemCount() < this.keylineCount);
    }
}
