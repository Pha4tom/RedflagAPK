package androidx.graphics.shapes;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.graphics.shapes.MeasuredPolygon;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Morph.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u0010\u001a\u00020\u0011J\u001c\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013J2\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00190\u001dH\u0087\bø\u0001\u0000R \u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u00078@X\u0081\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001f"}, d2 = {"Landroidx/graphics/shapes/Morph;", "", "start", "Landroidx/graphics/shapes/RoundedPolygon;", "end", "(Landroidx/graphics/shapes/RoundedPolygon;Landroidx/graphics/shapes/RoundedPolygon;)V", "_morphMatch", "", "Lkotlin/Pair;", "Landroidx/graphics/shapes/Cubic;", "morphMatch", "getMorphMatch$annotations", "()V", "getMorphMatch", "()Ljava/util/List;", "asCubics", "progress", "", "calculateBounds", "", "bounds", "approximate", "", "calculateMaxBounds", "forEachCubic", "", "mutableCubic", "Landroidx/graphics/shapes/MutableCubic;", "callback", "Lkotlin/Function1;", "Companion", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class Morph {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<Pair<Cubic, Cubic>> _morphMatch;
    private final RoundedPolygon end;
    private final RoundedPolygon start;

    public static /* synthetic */ void getMorphMatch$annotations() {
    }

    public final float[] calculateBounds() {
        return calculateBounds$default(this, null, false, 3, null);
    }

    public final float[] calculateBounds(float[] bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        return calculateBounds$default(this, bounds, false, 2, null);
    }

    public Morph(RoundedPolygon start, RoundedPolygon end) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        this.start = start;
        this.end = end;
        this._morphMatch = INSTANCE.match$graphics_shapes_release(this.start, this.end);
    }

    public final List<Pair<Cubic, Cubic>> getMorphMatch() {
        return this._morphMatch;
    }

    public static /* synthetic */ float[] calculateBounds$default(Morph morph, float[] fArr, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return morph.calculateBounds(fArr, z);
    }

    public final float[] calculateBounds(float[] bounds, boolean approximate) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.start.calculateBounds(bounds, approximate);
        float minX = bounds[0];
        float minY = bounds[1];
        float maxX = bounds[2];
        float maxY = bounds[3];
        this.end.calculateBounds(bounds, approximate);
        bounds[0] = Math.min(minX, bounds[0]);
        bounds[1] = Math.min(minY, bounds[1]);
        bounds[2] = Math.max(maxX, bounds[2]);
        bounds[3] = Math.max(maxY, bounds[3]);
        return bounds;
    }

    public static /* synthetic */ float[] calculateMaxBounds$default(Morph morph, float[] fArr, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        return morph.calculateMaxBounds(fArr);
    }

    public final float[] calculateMaxBounds(float[] bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.start.calculateMaxBounds(bounds);
        float minX = bounds[0];
        float minY = bounds[1];
        float maxX = bounds[2];
        float maxY = bounds[3];
        this.end.calculateMaxBounds(bounds);
        bounds[0] = Math.min(minX, bounds[0]);
        bounds[1] = Math.min(minY, bounds[1]);
        bounds[2] = Math.max(maxX, bounds[2]);
        bounds[3] = Math.max(maxY, bounds[3]);
        return bounds;
    }

    public final List<Cubic> asCubics(float progress) {
        List $this$asCubics_u24lambda_u240 = CollectionsKt.createListBuilder();
        Cubic firstCubic = null;
        Cubic lastCubic = null;
        int size = this._morphMatch.size();
        for (int i = 0; i < size; i++) {
            float[] fArr = new float[8];
            for (int i2 = 0; i2 < 8; i2++) {
                fArr[i2] = Utils.interpolate(this._morphMatch.get(i).getFirst().getPoints()[i2], this._morphMatch.get(i).getSecond().getPoints()[i2], progress);
            }
            Cubic cubic = new Cubic(fArr);
            if (firstCubic == null) {
                firstCubic = cubic;
            }
            if (lastCubic != null) {
                $this$asCubics_u24lambda_u240.add(lastCubic);
            }
            lastCubic = cubic;
        }
        if (lastCubic != null && firstCubic != null) {
            $this$asCubics_u24lambda_u240.add(CubicKt.Cubic(lastCubic.getAnchor0X(), lastCubic.getAnchor0Y(), lastCubic.getControl0X(), lastCubic.getControl0Y(), lastCubic.getControl1X(), lastCubic.getControl1Y(), firstCubic.getAnchor0X(), firstCubic.getAnchor0Y()));
        }
        return CollectionsKt.build($this$asCubics_u24lambda_u240);
    }

    public static /* synthetic */ void forEachCubic$default(Morph $this, float progress, MutableCubic mutableCubic, Function1 callback, int i, Object obj) {
        if ((i & 2) != 0) {
            mutableCubic = new MutableCubic();
        }
        Intrinsics.checkNotNullParameter(mutableCubic, "mutableCubic");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = $this.getMorphMatch().size();
        for (int i2 = 0; i2 < size; i2++) {
            mutableCubic.interpolate($this.getMorphMatch().get(i2).getFirst(), $this.getMorphMatch().get(i2).getSecond(), progress);
            callback.invoke(mutableCubic);
        }
    }

    public final void forEachCubic(float progress, MutableCubic mutableCubic, Function1<? super MutableCubic, Unit> callback) {
        Intrinsics.checkNotNullParameter(mutableCubic, "mutableCubic");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = getMorphMatch().size();
        for (int i = 0; i < size; i++) {
            mutableCubic.interpolate(getMorphMatch().get(i).getFirst(), getMorphMatch().get(i).getSecond(), progress);
            callback.invoke(mutableCubic);
        }
    }

    /* JADX INFO: compiled from: Morph.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0001¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"Landroidx/graphics/shapes/Morph$Companion;", "", "()V", "match", "", "Lkotlin/Pair;", "Landroidx/graphics/shapes/Cubic;", "p1", "Landroidx/graphics/shapes/RoundedPolygon;", "p2", "match$graphics_shapes_release", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final List<Pair<Cubic, Cubic>> match$graphics_shapes_release(RoundedPolygon p1, RoundedPolygon p2) {
            float b2a;
            Pair<MeasuredPolygon.MeasuredCubic, MeasuredPolygon.MeasuredCubic> pairCutAtProgress;
            MeasuredPolygon.MeasuredCubic newb1;
            Pair<MeasuredPolygon.MeasuredCubic, MeasuredPolygon.MeasuredCubic> pairCutAtProgress2;
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            MeasuredPolygon measuredPolygon1 = MeasuredPolygon.INSTANCE.measurePolygon$graphics_shapes_release(new AngleMeasurer(p1.getCenterX(), p1.getCenterY()), p1);
            MeasuredPolygon measuredPolygon2 = MeasuredPolygon.INSTANCE.measurePolygon$graphics_shapes_release(new AngleMeasurer(p2.getCenterX(), p2.getCenterY()), p2);
            DoubleMapper doubleMapper = FeatureMappingKt.featureMapper(measuredPolygon1.getFeatures(), measuredPolygon2.getFeatures());
            float polygon2CutPoint = doubleMapper.map(0.0f);
            String unused = MorphKt.LOG_TAG;
            MeasuredPolygon bs2 = measuredPolygon2.cutAndShift(polygon2CutPoint);
            List ret = new ArrayList();
            int i1 = 0 + 1;
            MeasuredPolygon.MeasuredCubic b1 = (MeasuredPolygon.MeasuredCubic) CollectionsKt.getOrNull(measuredPolygon1, 0);
            int i2 = 0 + 1;
            MeasuredPolygon.MeasuredCubic b2 = (MeasuredPolygon.MeasuredCubic) CollectionsKt.getOrNull(bs2, 0);
            while (b1 != null && b2 != null) {
                float b1a = i1 == measuredPolygon1.size() ? 1.0f : b1.getEndOutlineProgress();
                float b2a2 = i2 == bs2.size() ? 1.0f : doubleMapper.mapBack(Utils.positiveModulo(b2.getEndOutlineProgress() + polygon2CutPoint, 1.0f));
                float minb = Math.min(b1a, b2a2);
                String unused2 = MorphKt.LOG_TAG;
                if (b1a > minb + 1.0E-6f) {
                    String unused3 = MorphKt.LOG_TAG;
                    b2a = b2a2;
                    pairCutAtProgress = b1.cutAtProgress(minb);
                } else {
                    b2a = b2a2;
                    pairCutAtProgress = TuplesKt.to(b1, CollectionsKt.getOrNull(measuredPolygon1, i1));
                    i1++;
                }
                MeasuredPolygon.MeasuredCubic seg1 = pairCutAtProgress.component1();
                MeasuredPolygon.MeasuredCubic newb12 = pairCutAtProgress.component2();
                if (b2a > minb + 1.0E-6f) {
                    String unused4 = MorphKt.LOG_TAG;
                    newb1 = newb12;
                    pairCutAtProgress2 = b2.cutAtProgress(Utils.positiveModulo(doubleMapper.map(minb) - polygon2CutPoint, 1.0f));
                } else {
                    newb1 = newb12;
                    pairCutAtProgress2 = TuplesKt.to(b2, CollectionsKt.getOrNull(bs2, i2));
                    i2++;
                }
                MeasuredPolygon.MeasuredCubic seg2 = pairCutAtProgress2.component1();
                MeasuredPolygon.MeasuredCubic newb2 = pairCutAtProgress2.component2();
                String unused5 = MorphKt.LOG_TAG;
                ret.add(TuplesKt.to(seg1.getCubic(), seg2.getCubic()));
                b1 = newb1;
                b2 = newb2;
            }
            if (!(b1 == null && b2 == null)) {
                throw new IllegalArgumentException("Expected both Polygon's Cubic to be fully matched".toString());
            }
            return ret;
        }
    }

    public final void forEachCubic(float progress, Function1<? super MutableCubic, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        MutableCubic mutableCubic$iv = new MutableCubic();
        int size = getMorphMatch().size();
        for (int i$iv = 0; i$iv < size; i$iv++) {
            mutableCubic$iv.interpolate(getMorphMatch().get(i$iv).getFirst(), getMorphMatch().get(i$iv).getSecond(), progress);
            callback.invoke(mutableCubic$iv);
        }
    }
}
