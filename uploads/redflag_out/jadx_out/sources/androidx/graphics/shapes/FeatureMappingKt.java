package androidx.graphics.shapes;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.graphics.shapes.Feature;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: FeatureMapping.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a6\u0010\u0002\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u0006\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u0005H\u0000\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\nH\u0000\u001a,\u0010\u000b\u001a\u00020\f2\u0010\u0010\r\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u000e\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u0005H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000*\u0018\b\u0000\u0010\u000f\"\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0010"}, d2 = {"LOG_TAG", "", "doMapping", "", "Landroidx/graphics/shapes/ProgressableFeature;", "Landroidx/graphics/shapes/MeasuredFeatures;", "f1", "f2", "featureDistSquared", "", "Landroidx/graphics/shapes/Feature;", "featureMapper", "Landroidx/graphics/shapes/DoubleMapper;", "features1", "features2", "MeasuredFeatures", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class FeatureMappingKt {
    private static final String LOG_TAG = "FeatureMapping";

    public static final DoubleMapper featureMapper(List<ProgressableFeature> features1, List<ProgressableFeature> features2) {
        Pair pair;
        Intrinsics.checkNotNullParameter(features1, "features1");
        Intrinsics.checkNotNullParameter(features2, "features2");
        List $this$featureMapper_u24lambda_u240 = CollectionsKt.createListBuilder();
        int size = features1.size();
        for (int i = 0; i < size; i++) {
            if (features1.get(i).getFeature() instanceof Feature.Corner) {
                $this$featureMapper_u24lambda_u240.add(features1.get(i));
            }
        }
        List filteredFeatures1 = CollectionsKt.build($this$featureMapper_u24lambda_u240);
        List $this$featureMapper_u24lambda_u241 = CollectionsKt.createListBuilder();
        int size2 = features2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (features2.get(i2).getFeature() instanceof Feature.Corner) {
                $this$featureMapper_u24lambda_u241.add(features2.get(i2));
            }
        }
        List filteredFeatures2 = CollectionsKt.build($this$featureMapper_u24lambda_u241);
        if (filteredFeatures1.size() > filteredFeatures2.size()) {
            pair = TuplesKt.to(doMapping(filteredFeatures2, filteredFeatures1), filteredFeatures2);
        } else {
            pair = TuplesKt.to(filteredFeatures1, doMapping(filteredFeatures1, filteredFeatures2));
        }
        List m1 = (List) pair.component1();
        List m2 = (List) pair.component2();
        List $this$featureMapper_u24lambda_u242 = CollectionsKt.createListBuilder();
        int size3 = m1.size();
        for (int i3 = 0; i3 < size3 && i3 != m2.size(); i3++) {
            $this$featureMapper_u24lambda_u242.add(TuplesKt.to(Float.valueOf(((ProgressableFeature) m1.get(i3)).getProgress()), Float.valueOf(((ProgressableFeature) m2.get(i3)).getProgress())));
        }
        Collection mm = CollectionsKt.build($this$featureMapper_u24lambda_u242);
        String str = LOG_TAG;
        Collection $this$toTypedArray$iv = mm;
        Pair[] pairArr = (Pair[]) $this$toTypedArray$iv.toArray(new Pair[0]);
        DoubleMapper doubleMapper = new DoubleMapper((Pair[]) Arrays.copyOf(pairArr, pairArr.length));
        String str2 = LOG_TAG;
        return doubleMapper;
    }

    public static final float featureDistSquared(Feature f1, Feature f2) {
        Intrinsics.checkNotNullParameter(f1, "f1");
        Intrinsics.checkNotNullParameter(f2, "f2");
        if ((f1 instanceof Feature.Corner) && (f2 instanceof Feature.Corner) && ((Feature.Corner) f1).getConvex() != ((Feature.Corner) f2).getConvex()) {
            String str = LOG_TAG;
            return Float.MAX_VALUE;
        }
        float c1x = (((Cubic) CollectionsKt.first((List) f1.getCubics())).getAnchor0X() + ((Cubic) CollectionsKt.last((List) f1.getCubics())).getAnchor1X()) / 2.0f;
        float c1y = (((Cubic) CollectionsKt.first((List) f1.getCubics())).getAnchor0Y() + ((Cubic) CollectionsKt.last((List) f1.getCubics())).getAnchor1Y()) / 2.0f;
        float c2x = (((Cubic) CollectionsKt.first((List) f2.getCubics())).getAnchor0X() + ((Cubic) CollectionsKt.last((List) f2.getCubics())).getAnchor1X()) / 2.0f;
        float c2y = (((Cubic) CollectionsKt.first((List) f2.getCubics())).getAnchor0Y() + ((Cubic) CollectionsKt.last((List) f2.getCubics())).getAnchor1Y()) / 2.0f;
        float dx = c1x - c2x;
        float dy = c1y - c2y;
        return (dx * dx) + (dy * dy);
    }

    public static final List<ProgressableFeature> doMapping(List<ProgressableFeature> list, List<ProgressableFeature> f2) {
        int m;
        int n;
        List<ProgressableFeature> f1 = list;
        Intrinsics.checkNotNullParameter(f1, "f1");
        Intrinsics.checkNotNullParameter(f2, "f2");
        Iterable $this$minBy$iv = CollectionsKt.getIndices(f2);
        Iterator<Integer> it = $this$minBy$iv.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        int minElem$iv = ((IntIterator) it).nextInt();
        if (it.hasNext()) {
            float minValue$iv = featureDistSquared(f1.get(0).getFeature(), f2.get(minElem$iv).getFeature());
            while (true) {
                int e$iv = ((IntIterator) it).nextInt();
                float v$iv = featureDistSquared(f1.get(0).getFeature(), f2.get(e$iv).getFeature());
                if (Float.compare(minValue$iv, v$iv) > 0) {
                    minElem$iv = e$iv;
                    minValue$iv = v$iv;
                }
                if (!it.hasNext()) {
                    break;
                }
                f1 = list;
            }
        }
        int m2 = f1.size();
        int n2 = f2.size();
        List<ProgressableFeature> listMutableListOf = CollectionsKt.mutableListOf(f2.get(minElem$iv));
        int lastPicked = minElem$iv;
        int i = 1;
        while (i < m2) {
            int it2 = minElem$iv - (m2 - i);
            if (it2 <= lastPicked) {
                it2 += n2;
            }
            Iterable $this$minBy$iv2 = new IntRange(lastPicked + 1, it2);
            Iterator<Integer> it3 = $this$minBy$iv2.iterator();
            if (!it3.hasNext()) {
                throw new NoSuchElementException();
            }
            int best = ((IntIterator) it3).nextInt();
            if (it3.hasNext()) {
                m = m2;
                int m3 = best % n2;
                float minValue$iv2 = featureDistSquared(f1.get(i).getFeature(), f2.get(m3).getFeature());
                while (true) {
                    int e$iv2 = ((IntIterator) it3).nextInt();
                    n = n2;
                    float v$iv2 = featureDistSquared(f1.get(i).getFeature(), f2.get(e$iv2 % n).getFeature());
                    if (Float.compare(minValue$iv2, v$iv2) > 0) {
                        minValue$iv2 = v$iv2;
                        best = e$iv2;
                    }
                    if (!it3.hasNext()) {
                        break;
                    }
                    f1 = list;
                    n2 = n;
                }
            } else {
                m = m2;
                n = n2;
            }
            listMutableListOf.add(f2.get(best % n));
            lastPicked = best;
            i++;
            f1 = list;
            m2 = m;
            n2 = n;
        }
        return listMutableListOf;
    }
}
