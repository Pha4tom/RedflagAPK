package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RoundedPolygon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B%\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u001c\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012J\u0013\u0010\u0017\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0006\u0010\u001b\u001a\u00020\u0000J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\""}, d2 = {"Landroidx/graphics/shapes/RoundedPolygon;", "", "features", "", "Landroidx/graphics/shapes/Feature;", "centerX", "", "centerY", "(Ljava/util/List;FF)V", "getCenterX", "()F", "getCenterY", "cubics", "Landroidx/graphics/shapes/Cubic;", "getCubics", "()Ljava/util/List;", "getFeatures$graphics_shapes_release", "calculateBounds", "", "bounds", "approximate", "", "calculateMaxBounds", "equals", "other", "hashCode", "", "normalized", "toString", "", "transformed", "f", "Landroidx/graphics/shapes/PointTransformer;", "Companion", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class RoundedPolygon {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final float centerX;
    private final float centerY;
    private final List<Cubic> cubics;
    private final List<Feature> features;

    public final float[] calculateBounds() {
        return calculateBounds$default(this, null, false, 3, null);
    }

    public final float[] calculateBounds(float[] bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        return calculateBounds$default(this, bounds, false, 2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RoundedPolygon(List<? extends Feature> features, float centerX, float centerY) {
        List<Cubic> cubics;
        Intrinsics.checkNotNullParameter(features, "features");
        this.features = features;
        this.centerX = centerX;
        this.centerY = centerY;
        List $this$cubics_u24lambda_u240 = CollectionsKt.createListBuilder();
        Cubic firstCubic = null;
        Cubic lastCubic = null;
        List<Cubic> listMutableListOf = null;
        List<Cubic> listMutableListOf2 = null;
        if (this.features.size() > 0 && this.features.get(0).getCubics().size() == 3) {
            Cubic centerCubic = this.features.get(0).getCubics().get(1);
            Pair<Cubic, Cubic> pairSplit = centerCubic.split(0.5f);
            Cubic start = pairSplit.component1();
            Cubic end = pairSplit.component2();
            listMutableListOf = CollectionsKt.mutableListOf(this.features.get(0).getCubics().get(0), start);
            listMutableListOf2 = CollectionsKt.mutableListOf(end, this.features.get(0).getCubics().get(2));
        }
        int i = 0;
        int size = this.features.size();
        if (0 <= size) {
            while (true) {
                if (i == 0 && listMutableListOf2 != null) {
                    cubics = listMutableListOf2;
                } else if (i == this.features.size()) {
                    if (listMutableListOf == null) {
                        break;
                    } else {
                        cubics = listMutableListOf;
                    }
                } else {
                    cubics = this.features.get(i).getCubics();
                }
                int size2 = cubics.size();
                for (int j = 0; j < size2; j++) {
                    Cubic cubic = cubics.get(j);
                    if (!cubic.zeroLength$graphics_shapes_release()) {
                        if (lastCubic != null) {
                            $this$cubics_u24lambda_u240.add(lastCubic);
                        }
                        lastCubic = cubic;
                        if (firstCubic == null) {
                            firstCubic = cubic;
                        }
                    } else if (lastCubic != null) {
                        lastCubic.getPoints()[6] = cubic.getAnchor1X();
                        lastCubic.getPoints()[7] = cubic.getAnchor1Y();
                    }
                }
                if (i == size) {
                    break;
                } else {
                    i++;
                }
            }
        }
        if (lastCubic != null && firstCubic != null) {
            $this$cubics_u24lambda_u240.add(CubicKt.Cubic(lastCubic.getAnchor0X(), lastCubic.getAnchor0Y(), lastCubic.getControl0X(), lastCubic.getControl0Y(), lastCubic.getControl1X(), lastCubic.getControl1Y(), firstCubic.getAnchor0X(), firstCubic.getAnchor0Y()));
        }
        this.cubics = CollectionsKt.build($this$cubics_u24lambda_u240);
        Object prevCubic = this.cubics.get(this.cubics.size() - 1);
        int size3 = this.cubics.size();
        for (int index = 0; index < size3; index++) {
            Cubic cubic2 = this.cubics.get(index);
            if (Math.abs(cubic2.getAnchor0X() - ((Cubic) prevCubic).getAnchor1X()) > 1.0E-4f || Math.abs(cubic2.getAnchor0Y() - ((Cubic) prevCubic).getAnchor1Y()) > 1.0E-4f) {
                throw new IllegalArgumentException("RoundedPolygon must be contiguous, with the anchor points of all curves matching the anchor points of the preceding and succeeding cubics");
            }
            prevCubic = cubic2;
        }
    }

    public final float getCenterX() {
        return this.centerX;
    }

    public final float getCenterY() {
        return this.centerY;
    }

    public final List<Feature> getFeatures$graphics_shapes_release() {
        return this.features;
    }

    public final List<Cubic> getCubics() {
        return this.cubics;
    }

    public final RoundedPolygon transformed(PointTransformer f) {
        Intrinsics.checkNotNullParameter(f, "f");
        long center = PointKt.m114transformedso9K2fw(FloatFloatPair.m15constructorimpl(this.centerX, this.centerY), f);
        List $this$transformed_u24lambda_u244 = CollectionsKt.createListBuilder();
        int size = this.features.size();
        for (int i = 0; i < size; i++) {
            $this$transformed_u24lambda_u244.add(this.features.get(i).transformed$graphics_shapes_release(f));
        }
        return new RoundedPolygon(CollectionsKt.build($this$transformed_u24lambda_u244), PointKt.m107getXDnnuFBc(center), PointKt.m108getYDnnuFBc(center));
    }

    public final RoundedPolygon normalized() {
        float[] bounds = calculateBounds$default(this, null, false, 3, null);
        float width = bounds[2] - bounds[0];
        float height = bounds[3] - bounds[1];
        final float side = Math.max(width, height);
        float f = 2;
        final float offsetX = ((side - width) / f) - bounds[0];
        final float offsetY = ((side - height) / f) - bounds[1];
        return transformed(new PointTransformer() { // from class: androidx.graphics.shapes.RoundedPolygon.normalized.1
            @Override // androidx.graphics.shapes.PointTransformer
            /* JADX INFO: renamed from: transform-XgqJiTY */
            public final long mo116transformXgqJiTY(float x, float y) {
                return FloatFloatPair.m15constructorimpl((offsetX + x) / side, (offsetY + y) / side);
            }
        });
    }

    public String toString() {
        return "[RoundedPolygon. Cubics = " + CollectionsKt.joinToString$default(this.cubics, null, null, null, 0, null, null, 63, null) + " || Features = " + CollectionsKt.joinToString$default(this.features, null, null, null, 0, null, null, 63, null) + " || Center = (" + this.centerX + ", " + this.centerY + ")]";
    }

    public static /* synthetic */ float[] calculateMaxBounds$default(RoundedPolygon roundedPolygon, float[] fArr, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        return roundedPolygon.calculateMaxBounds(fArr);
    }

    public final float[] calculateMaxBounds(float[] bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        if (!(bounds.length >= 4)) {
            throw new IllegalArgumentException("Required bounds size of 4".toString());
        }
        float maxDistSquared = 0.0f;
        int size = this.cubics.size();
        for (int i = 0; i < size; i++) {
            Cubic cubic = this.cubics.get(i);
            float anchorDistance = Utils.distanceSquared(cubic.getAnchor0X() - this.centerX, cubic.getAnchor0Y() - this.centerY);
            long middlePoint = cubic.m95pointOnCurveOOQOV4g$graphics_shapes_release(0.5f);
            float middleDistance = Utils.distanceSquared(PointKt.m107getXDnnuFBc(middlePoint) - this.centerX, PointKt.m108getYDnnuFBc(middlePoint) - this.centerY);
            maxDistSquared = Math.max(maxDistSquared, Math.max(anchorDistance, middleDistance));
        }
        float distance = (float) Math.sqrt(maxDistSquared);
        bounds[0] = this.centerX - distance;
        bounds[1] = this.centerY - distance;
        bounds[2] = this.centerX + distance;
        bounds[3] = this.centerY + distance;
        return bounds;
    }

    public static /* synthetic */ float[] calculateBounds$default(RoundedPolygon roundedPolygon, float[] fArr, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return roundedPolygon.calculateBounds(fArr, z);
    }

    public final float[] calculateBounds(float[] bounds, boolean approximate) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        if (!(bounds.length >= 4)) {
            throw new IllegalArgumentException("Required bounds size of 4".toString());
        }
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        int size = this.cubics.size();
        for (int i = 0; i < size; i++) {
            Cubic cubic = this.cubics.get(i);
            cubic.calculateBounds$graphics_shapes_release(bounds, approximate);
            minX = Math.min(minX, bounds[0]);
            minY = Math.min(minY, bounds[1]);
            maxX = Math.max(maxX, bounds[2]);
            maxY = Math.max(maxY, bounds[3]);
        }
        bounds[0] = minX;
        bounds[1] = minY;
        bounds[2] = maxX;
        bounds[3] = maxY;
        return bounds;
    }

    /* JADX INFO: compiled from: RoundedPolygon.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/graphics/shapes/RoundedPolygon$Companion;", "", "()V", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof RoundedPolygon) {
            return Intrinsics.areEqual(this.features, ((RoundedPolygon) other).features);
        }
        return false;
    }

    public int hashCode() {
        return this.features.hashCode();
    }
}
