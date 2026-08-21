package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Cubic.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 ?2\u00020\u0001:\u0001?B7\b\u0010\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0006\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0007\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0002\u0010\bB\u0011\b\u0000\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ!\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020\n2\b\b\u0002\u0010#\u001a\u00020$H\u0000¢\u0006\u0002\b%J\u0011\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\rH\u0086\u0002J\u0011\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(H\u0086\u0002J\u0013\u0010)\u001a\u00020$2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010+\u001a\u00020(H\u0016J\u0011\u0010,\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\u0000H\u0086\u0002J!\u0010.\u001a\u00060\u0003j\u0002`\u00042\u0006\u0010/\u001a\u00020\rH\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b0\u00101J\u0006\u00102\u001a\u00020\u0000J\u001a\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0000042\u0006\u0010/\u001a\u00020\rJ\u0011\u00105\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\rH\u0086\u0002J\u0011\u00105\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(H\u0086\u0002J\b\u00106\u001a\u000207H\u0016J\u000e\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020:J\u0010\u0010;\u001a\u00020$2\u0006\u0010<\u001a\u00020\rH\u0002J\r\u0010=\u001a\u00020$H\u0000¢\u0006\u0002\b>R\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0014\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\u0016\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\u0018\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u000fR\u0011\u0010\u001a\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000fR\u0011\u0010\u001c\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000fR\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006@"}, d2 = {"Landroidx/graphics/shapes/Cubic;", "", "anchor0", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "control0", "control1", "anchor1", "(JJJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "points", "", "([F)V", "anchor0X", "", "getAnchor0X", "()F", "anchor0Y", "getAnchor0Y", "anchor1X", "getAnchor1X", "anchor1Y", "getAnchor1Y", "control0X", "getControl0X", "control0Y", "getControl0Y", "control1X", "getControl1X", "control1Y", "getControl1Y", "getPoints$graphics_shapes_release", "()[F", "calculateBounds", "", "bounds", "approximate", "", "calculateBounds$graphics_shapes_release", "div", "x", "", "equals", "other", "hashCode", "plus", "o", "pointOnCurve", "t", "pointOnCurve-OOQOV4g$graphics_shapes_release", "(F)J", "reverse", "split", "Lkotlin/Pair;", "times", "toString", "", "transformed", "f", "Landroidx/graphics/shapes/PointTransformer;", "zeroIsh", "value", "zeroLength", "zeroLength$graphics_shapes_release", "Companion", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class Cubic {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final float[] points;

    /* JADX WARN: Multi-variable type inference failed */
    public Cubic() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ Cubic(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    @JvmStatic
    public static final Cubic circularArc(float f, float f2, float f3, float f4, float f5, float f6) {
        return INSTANCE.circularArc(f, f2, f3, f4, f5, f6);
    }

    @JvmStatic
    public static final Cubic straightLine(float f, float f2, float f3, float f4) {
        return INSTANCE.straightLine(f, f2, f3, f4);
    }

    public Cubic(float[] points) {
        Intrinsics.checkNotNullParameter(points, "points");
        this.points = points;
        if (!(this.points.length == 8)) {
            throw new IllegalArgumentException("Points array size should be 8".toString());
        }
    }

    public /* synthetic */ Cubic(float[] fArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new float[8] : fArr);
    }

    /* JADX INFO: renamed from: getPoints$graphics_shapes_release, reason: from getter */
    public final float[] getPoints() {
        return this.points;
    }

    public final float getAnchor0X() {
        return this.points[0];
    }

    public final float getAnchor0Y() {
        return this.points[1];
    }

    public final float getControl0X() {
        return this.points[2];
    }

    public final float getControl0Y() {
        return this.points[3];
    }

    public final float getControl1X() {
        return this.points[4];
    }

    public final float getControl1Y() {
        return this.points[5];
    }

    public final float getAnchor1X() {
        return this.points[6];
    }

    public final float getAnchor1Y() {
        return this.points[7];
    }

    private Cubic(long anchor0, long control0, long control1, long anchor1) {
        this(new float[]{PointKt.m107getXDnnuFBc(anchor0), PointKt.m108getYDnnuFBc(anchor0), PointKt.m107getXDnnuFBc(control0), PointKt.m108getYDnnuFBc(control0), PointKt.m107getXDnnuFBc(control1), PointKt.m108getYDnnuFBc(control1), PointKt.m107getXDnnuFBc(anchor1), PointKt.m108getYDnnuFBc(anchor1)});
    }

    /* JADX INFO: renamed from: pointOnCurve-OOQOV4g$graphics_shapes_release, reason: not valid java name */
    public final long m95pointOnCurveOOQOV4g$graphics_shapes_release(float t) {
        float u = 1 - t;
        float f = 3;
        return FloatFloatPair.m15constructorimpl((getAnchor0X() * u * u * u) + (getControl0X() * f * t * u * u) + (getControl1X() * f * t * t * u) + (getAnchor1X() * t * t * t), (getAnchor0Y() * u * u * u) + (getControl0Y() * f * t * u * u) + (getControl1Y() * f * t * t * u) + (getAnchor1Y() * t * t * t));
    }

    public final boolean zeroLength$graphics_shapes_release() {
        return Math.abs(getAnchor0X() - getAnchor1X()) < 1.0E-4f && Math.abs(getAnchor0Y() - getAnchor1Y()) < 1.0E-4f;
    }

    private final boolean zeroIsh(float value) {
        return Math.abs(value) < 1.0E-4f;
    }

    public static /* synthetic */ void calculateBounds$graphics_shapes_release$default(Cubic cubic, float[] fArr, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: calculateBounds");
        }
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        if ((i & 2) != 0) {
            z = false;
        }
        cubic.calculateBounds$graphics_shapes_release(fArr, z);
    }

    public final void calculateBounds$graphics_shapes_release(float[] bounds, boolean approximate) {
        char c;
        char c2;
        char c3;
        float minY;
        float minY2;
        float minY3;
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        if (zeroLength$graphics_shapes_release()) {
            bounds[0] = getAnchor0X();
            bounds[1] = getAnchor0Y();
            bounds[2] = getAnchor0X();
            bounds[3] = getAnchor0Y();
            return;
        }
        float minX = Math.min(getAnchor0X(), getAnchor1X());
        float minY4 = Math.min(getAnchor0Y(), getAnchor1Y());
        float maxX = Math.max(getAnchor0X(), getAnchor1X());
        float maxY = Math.max(getAnchor0Y(), getAnchor1Y());
        if (approximate) {
            bounds[0] = Math.min(minX, Math.min(getControl0X(), getControl1X()));
            bounds[1] = Math.min(minY4, Math.min(getControl0Y(), getControl1Y()));
            bounds[2] = Math.max(maxX, Math.max(getControl0X(), getControl1X()));
            bounds[3] = Math.max(maxY, Math.max(getControl0Y(), getControl1Y()));
            return;
        }
        float f = 3;
        float xa = (((-getAnchor0X()) + (getControl0X() * f)) - (getControl1X() * f)) + getAnchor1X();
        float f2 = 2;
        float f3 = 4;
        float xb = ((getAnchor0X() * f2) - (getControl0X() * f3)) + (getControl1X() * f2);
        float xc = (-getAnchor0X()) + getControl0X();
        if (zeroIsh(xa)) {
            if (xb == 0.0f) {
                c = 2;
                c2 = 1;
                c3 = 0;
                minY = minY4;
            } else {
                c = 2;
                float t = (f2 * xc) / ((-2) * xb);
                if (!(0.0f <= t && t <= 1.0f)) {
                    c2 = 1;
                    c3 = 0;
                    minY = minY4;
                } else {
                    float it = PointKt.m107getXDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t));
                    if (it < minX) {
                        minX = it;
                    }
                    if (it > maxX) {
                        maxX = it;
                    }
                    c2 = 1;
                    c3 = 0;
                    minY = minY4;
                }
            }
        } else {
            c = 2;
            float xs = (xb * xb) - ((f3 * xa) * xc);
            if (xs < 0.0f) {
                c2 = 1;
                c3 = 0;
                minY = minY4;
            } else {
                c2 = 1;
                c3 = 0;
                minY = minY4;
                float t1 = ((-xb) + ((float) Math.sqrt(xs))) / (f2 * xa);
                if (0.0f <= t1 && t1 <= 1.0f) {
                    float it2 = PointKt.m107getXDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t1));
                    if (it2 < minX) {
                        minX = it2;
                    }
                    if (it2 > maxX) {
                        maxX = it2;
                    }
                }
                float minX2 = minX;
                float t2 = ((-xb) - ((float) Math.sqrt(xs))) / (f2 * xa);
                if (!(0.0f <= t2 && t2 <= 1.0f)) {
                    minX = minX2;
                } else {
                    float it3 = PointKt.m107getXDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t2));
                    if (it3 < minX2) {
                        minX2 = it3;
                    }
                    if (it3 > maxX) {
                        maxX = it3;
                    }
                    minX = minX2;
                }
            }
        }
        float ya = (((-getAnchor0Y()) + (getControl0Y() * f)) - (f * getControl1Y())) + getAnchor1Y();
        float yb = ((getAnchor0Y() * f2) - (getControl0Y() * f3)) + (getControl1Y() * f2);
        float yc = (-getAnchor0Y()) + getControl0Y();
        if (!zeroIsh(ya)) {
            float ys = (yb * yb) - ((f3 * ya) * yc);
            if (ys >= 0.0f) {
                minY2 = minX;
                float t12 = ((-yb) + ((float) Math.sqrt(ys))) / (f2 * ya);
                if (((0.0f > t12 || t12 > 1.0f) ? c3 : c2) == 0) {
                    minY3 = minY;
                } else {
                    float it4 = PointKt.m108getYDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t12));
                    minY3 = it4 < minY ? it4 : minY;
                    if (it4 > maxY) {
                        maxY = it4;
                    }
                }
                float t22 = ((-yb) - ((float) Math.sqrt(ys))) / (f2 * ya);
                if (((0.0f > t22 || t22 > 1.0f) ? c3 : c2) != 0) {
                    float it5 = PointKt.m108getYDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t22));
                    if (it5 < minY3) {
                        minY3 = it5;
                    }
                    if (it5 > maxY) {
                        maxY = it5;
                    }
                }
            } else {
                minY2 = minX;
                minY3 = minY;
            }
        } else {
            if ((yb == 0.0f ? c2 : c3) == 0) {
                float t3 = (f2 * yc) / ((-2) * yb);
                if (((0.0f > t3 || t3 > 1.0f) ? c3 : c2) != 0) {
                    float it6 = PointKt.m108getYDnnuFBc(m95pointOnCurveOOQOV4g$graphics_shapes_release(t3));
                    float minY5 = it6 < minY ? it6 : minY;
                    if (it6 > maxY) {
                        maxY = it6;
                    }
                    minY3 = minY5;
                    minY2 = minX;
                } else {
                    minY2 = minX;
                }
            } else {
                minY2 = minX;
            }
            minY3 = minY;
        }
        bounds[c3] = minY2;
        bounds[c2] = minY3;
        bounds[c] = maxX;
        bounds[3] = maxY;
    }

    public final Pair<Cubic, Cubic> split(float t) {
        float u = 1 - t;
        long pointOnCurve = m95pointOnCurveOOQOV4g$graphics_shapes_release(t);
        float f = 2;
        return TuplesKt.to(CubicKt.Cubic(getAnchor0X(), getAnchor0Y(), (getAnchor0X() * u) + (getControl0X() * t), (getAnchor0Y() * u) + (getControl0Y() * t), (getAnchor0X() * u * u) + (getControl0X() * f * u * t) + (getControl1X() * t * t), (getAnchor0Y() * u * u) + (getControl0Y() * f * u * t) + (getControl1Y() * t * t), PointKt.m107getXDnnuFBc(pointOnCurve), PointKt.m108getYDnnuFBc(pointOnCurve)), CubicKt.Cubic(PointKt.m107getXDnnuFBc(pointOnCurve), PointKt.m108getYDnnuFBc(pointOnCurve), (getControl0X() * u * u) + (getControl1X() * f * u * t) + (getAnchor1X() * t * t), (getControl0Y() * u * u) + (getControl1Y() * f * u * t) + (getAnchor1Y() * t * t), (getControl1X() * u) + (getAnchor1X() * t), (getControl1Y() * u) + (getAnchor1Y() * t), getAnchor1X(), getAnchor1Y()));
    }

    public final Cubic reverse() {
        return CubicKt.Cubic(getAnchor1X(), getAnchor1Y(), getControl1X(), getControl1Y(), getControl0X(), getControl0Y(), getAnchor0X(), getAnchor0Y());
    }

    public final Cubic plus(Cubic o) {
        Intrinsics.checkNotNullParameter(o, "o");
        float[] fArr = new float[8];
        for (int i = 0; i < 8; i++) {
            fArr[i] = this.points[i] + o.points[i];
        }
        return new Cubic(fArr);
    }

    public final Cubic times(float x) {
        float[] fArr = new float[8];
        for (int i = 0; i < 8; i++) {
            fArr[i] = this.points[i] * x;
        }
        return new Cubic(fArr);
    }

    public final Cubic times(int x) {
        return times(x);
    }

    public final Cubic div(float x) {
        return times(1.0f / x);
    }

    public final Cubic div(int x) {
        return div(x);
    }

    public String toString() {
        return "anchor0: (" + getAnchor0X() + ", " + getAnchor0Y() + ") control0: (" + getControl0X() + ", " + getControl0Y() + "), control1: (" + getControl1X() + ", " + getControl1Y() + "), anchor1: (" + getAnchor1X() + ", " + getAnchor1Y() + ')';
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Cubic)) {
            return false;
        }
        return Arrays.equals(this.points, ((Cubic) other).points);
    }

    public final Cubic transformed(PointTransformer f) {
        Intrinsics.checkNotNullParameter(f, "f");
        MutableCubic newCubic = new MutableCubic();
        ArraysKt.copyInto$default(this.points, newCubic.getPoints(), 0, 0, 0, 14, (Object) null);
        newCubic.transform(f);
        return newCubic;
    }

    public int hashCode() {
        return Arrays.hashCode(this.points);
    }

    /* JADX INFO: compiled from: Cubic.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J(\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0007¨\u0006\r"}, d2 = {"Landroidx/graphics/shapes/Cubic$Companion;", "", "()V", "circularArc", "Landroidx/graphics/shapes/Cubic;", "centerX", "", "centerY", "x0", "y0", "x1", "y1", "straightLine", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Cubic straightLine(float x0, float y0, float x1, float y1) {
            return CubicKt.Cubic(x0, y0, Utils.interpolate(x0, x1, 0.33333334f), Utils.interpolate(y0, y1, 0.33333334f), Utils.interpolate(x0, x1, 0.6666667f), Utils.interpolate(y0, y1, 0.6666667f), x1, y1);
        }

        @JvmStatic
        public final Cubic circularArc(float centerX, float centerY, float x0, float y0, float x1, float y1) {
            long p0d = Utils.directionVector(x0 - centerX, y0 - centerY);
            long p1d = Utils.directionVector(x1 - centerX, y1 - centerY);
            long rotatedP0 = Utils.m128rotate90DnnuFBc(p0d);
            long rotatedP1 = Utils.m128rotate90DnnuFBc(p1d);
            boolean clockwise = PointKt.m102dotProduct5P9i7ZU(rotatedP0, x1 - centerX, y1 - centerY) >= 0.0f;
            float cosa = PointKt.m103dotProductybeJwSQ(p0d, p1d);
            if (cosa > 0.999f) {
                return straightLine(x0, y0, x1, y1);
            }
            float k = ((((Utils.distance(x0 - centerX, y0 - centerY) * 4.0f) / 3.0f) * (((float) Math.sqrt(2 * (r3 - cosa))) - ((float) Math.sqrt(r3 - (cosa * cosa))))) / (1 - cosa)) * (clockwise ? 1.0f : -1.0f);
            return CubicKt.Cubic(x0, y0, x0 + (PointKt.m107getXDnnuFBc(rotatedP0) * k), y0 + (PointKt.m108getYDnnuFBc(rotatedP0) * k), x1 - (PointKt.m107getXDnnuFBc(rotatedP1) * k), y1 - (PointKt.m108getYDnnuFBc(rotatedP1) * k), x1, y1);
        }
    }
}
