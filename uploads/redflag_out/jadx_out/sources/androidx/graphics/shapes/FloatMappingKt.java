package androidx.graphics.shapes;

import androidx.collection.FloatList;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: FloatMapping.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001H\u0000\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0000¨\u0006\u000e"}, d2 = {"linearMap", "", "xValues", "Landroidx/collection/FloatList;", "yValues", "x", "progressInRange", "", "progress", "progressFrom", "progressTo", "validateProgress", "", "p", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class FloatMappingKt {
    public static final boolean progressInRange(float progress, float progressFrom, float progressTo) {
        return progressTo >= progressFrom ? progressFrom <= progress && progress <= progressTo : progress >= progressFrom || progress <= progressTo;
    }

    public static final float linearMap(FloatList xValues, FloatList yValues, float x) {
        Intrinsics.checkNotNullParameter(xValues, "xValues");
        Intrinsics.checkNotNullParameter(yValues, "yValues");
        if (!(0.0f <= x && x <= 1.0f)) {
            throw new IllegalArgumentException(("Invalid progress: " + x).toString());
        }
        Iterable $this$first$iv = RangesKt.until(0, xValues._size);
        Iterator<Integer> it = $this$first$iv.iterator();
        while (it.hasNext()) {
            int segmentStartIndex = ((IntIterator) it).nextInt();
            if (progressInRange(x, xValues.get(segmentStartIndex), xValues.get((segmentStartIndex + 1) % xValues.getSize()))) {
                int segmentEndIndex = (segmentStartIndex + 1) % xValues.getSize();
                float segmentSizeX = Utils.positiveModulo(xValues.get(segmentEndIndex) - xValues.get(segmentStartIndex), 1.0f);
                float segmentSizeY = Utils.positiveModulo(yValues.get(segmentEndIndex) - yValues.get(segmentStartIndex), 1.0f);
                float positionInSegment = segmentSizeX < 0.001f ? 0.5f : Utils.positiveModulo(x - xValues.get(segmentStartIndex), 1.0f) / segmentSizeX;
                return Utils.positiveModulo(yValues.get(segmentStartIndex) + (segmentSizeY * positionInSegment), 1.0f);
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static final void validateProgress(FloatList p) {
        int count$iv;
        Intrinsics.checkNotNullParameter(p, "p");
        Boolean boolValueOf = true;
        float[] content$iv$iv = p.content;
        int i$iv$iv = 0;
        int i = p._size;
        while (true) {
            boolean z = false;
            if (i$iv$iv >= i) {
                break;
            }
            float item$iv = content$iv$iv[i$iv$iv];
            boolean res = boolValueOf.booleanValue();
            if (res) {
                if (0.0f <= item$iv && item$iv <= 1.0f) {
                    z = true;
                }
            }
            boolValueOf = Boolean.valueOf(z);
            i$iv$iv++;
        }
        if (!boolValueOf.booleanValue()) {
            throw new IllegalArgumentException(("FloatMapping - Progress outside of range: " + FloatList.joinToString$default(p, null, null, null, 0, null, 31, null)).toString());
        }
        Iterable $this$count$iv = RangesKt.until(1, p.getSize());
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            Iterator it = $this$count$iv.iterator();
            while (it.hasNext()) {
                int element$iv = ((IntIterator) it).nextInt();
                int it2 = p.get(element$iv) < p.get(element$iv + (-1)) ? 1 : 0;
                if (it2 != 0 && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        int wraps = count$iv;
        if (!(wraps <= 1)) {
            throw new IllegalArgumentException(("FloatMapping - Progress wraps more than once: " + FloatList.joinToString$default(p, null, null, null, 0, null, 31, null)).toString());
        }
    }
}
