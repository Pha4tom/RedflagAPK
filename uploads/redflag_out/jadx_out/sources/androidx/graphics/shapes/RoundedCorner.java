package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RoundedPolygon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B5\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0006\u001a\u00060\u0003j\u0002`\u0004\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0011H\u0002Jf\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00112\n\u0010-\u001a\u00060\u0003j\u0002`\u00042\n\u0010.\u001a\u00060\u0003j\u0002`\u00042\n\u0010/\u001a\u00060\u0003j\u0002`\u00042\n\u00100\u001a\u00060\u0003j\u0002`\u00042\n\u00101\u001a\u00060\u0003j\u0002`\u00042\u0006\u00102\u001a\u00020\u0011H\u0002ø\u0001\u0000¢\u0006\u0004\b3\u00104J \u00105\u001a\b\u0012\u0004\u0012\u00020*062\u0006\u00107\u001a\u00020\u00112\b\b\u0002\u00108\u001a\u00020\u0011H\u0007JJ\u00109\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u00042\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\n\u0010:\u001a\u00060\u0003j\u0002`\u00042\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u00042\n\u0010\u0016\u001a\u00060\u0003j\u0002`\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b;\u0010<R&\u0010\n\u001a\u00060\u0003j\u0002`\u0004X\u0086\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u001d\u0010\u0016\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0017\u0010\fR\u001d\u0010\u0018\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0019\u0010\fR\u0011\u0010\u001a\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\u001c\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u001d\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u001e\u0010\fR\u001d\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u001f\u0010\fR\u001d\u0010\u0006\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b \u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0013R\u0011\u0010%\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0013\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006="}, d2 = {"Landroidx/graphics/shapes/RoundedCorner;", "", "p0", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "p1", "p2", "rounding", "Landroidx/graphics/shapes/CornerRounding;", "(JJJLandroidx/graphics/shapes/CornerRounding;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "center", "getCenter-1ufDz9w", "()J", "setCenter-DnnuFBc", "(J)V", "J", "cornerRadius", "", "getCornerRadius", "()F", "cosAngle", "getCosAngle", "d1", "getD1-1ufDz9w", "d2", "getD2-1ufDz9w", "expectedCut", "getExpectedCut", "expectedRoundCut", "getExpectedRoundCut", "getP0-1ufDz9w", "getP1-1ufDz9w", "getP2-1ufDz9w", "getRounding", "()Landroidx/graphics/shapes/CornerRounding;", "sinAngle", "getSinAngle", "smoothing", "getSmoothing", "calculateActualSmoothingValue", "allowedCut", "computeFlankingCurve", "Landroidx/graphics/shapes/Cubic;", "actualRoundCut", "actualSmoothingValues", "corner", "sideStart", "circleSegmentIntersection", "otherCircleSegmentIntersection", "circleCenter", "actualR", "computeFlankingCurve-oAJzIJU", "(FFJJJJJF)Landroidx/graphics/shapes/Cubic;", "getCubics", "", "allowedCut0", "allowedCut1", "lineIntersection", "d0", "lineIntersection-CBFvKDc", "(JJJJ)Landroidx/collection/FloatFloatPair;", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
final class RoundedCorner {
    private long center;
    private final float cornerRadius;
    private final float cosAngle;
    private final long d1;
    private final long d2;
    private final float expectedRoundCut;
    private final long p0;
    private final long p1;
    private final long p2;
    private final CornerRounding rounding;
    private final float sinAngle;
    private final float smoothing;

    public /* synthetic */ RoundedCorner(long j, long j2, long j3, CornerRounding cornerRounding, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, cornerRounding);
    }

    public final List<Cubic> getCubics(float f) {
        return getCubics$default(this, f, 0.0f, 2, null);
    }

    private RoundedCorner(long p0, long p1, long p2, CornerRounding rounding) {
        float f;
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.rounding = rounding;
        this.d1 = PointKt.m104getDirectionDnnuFBc(PointKt.m110minusybeJwSQ(this.p0, this.p1));
        this.d2 = PointKt.m104getDirectionDnnuFBc(PointKt.m110minusybeJwSQ(this.p2, this.p1));
        CornerRounding cornerRounding = this.rounding;
        this.cornerRadius = cornerRounding != null ? cornerRounding.getRadius() : 0.0f;
        CornerRounding cornerRounding2 = this.rounding;
        this.smoothing = cornerRounding2 != null ? cornerRounding2.getSmoothing() : 0.0f;
        this.cosAngle = PointKt.m103dotProductybeJwSQ(this.d1, this.d2);
        float f2 = 1;
        this.sinAngle = (float) Math.sqrt(f2 - Utils.square(this.cosAngle));
        if (this.sinAngle > 0.001d) {
            f = (this.cornerRadius * (this.cosAngle + f2)) / this.sinAngle;
        } else {
            f = 0.0f;
        }
        this.expectedRoundCut = f;
        this.center = FloatFloatPair.m15constructorimpl(0.0f, 0.0f);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ RoundedCorner(long j, long j2, long j3, CornerRounding cornerRounding, int i, DefaultConstructorMarker defaultConstructorMarker) {
        CornerRounding cornerRounding2;
        if ((i & 8) == 0) {
            cornerRounding2 = cornerRounding;
        } else {
            cornerRounding2 = null;
        }
        this(j, j2, j3, cornerRounding2, null);
    }

    /* JADX INFO: renamed from: getP0-1ufDz9w, reason: not valid java name and from getter */
    public final long getP0() {
        return this.p0;
    }

    /* JADX INFO: renamed from: getP1-1ufDz9w, reason: not valid java name and from getter */
    public final long getP1() {
        return this.p1;
    }

    /* JADX INFO: renamed from: getP2-1ufDz9w, reason: not valid java name and from getter */
    public final long getP2() {
        return this.p2;
    }

    public final CornerRounding getRounding() {
        return this.rounding;
    }

    /* JADX INFO: renamed from: getD1-1ufDz9w, reason: not valid java name and from getter */
    public final long getD1() {
        return this.d1;
    }

    /* JADX INFO: renamed from: getD2-1ufDz9w, reason: not valid java name and from getter */
    public final long getD2() {
        return this.d2;
    }

    public final float getCornerRadius() {
        return this.cornerRadius;
    }

    public final float getSmoothing() {
        return this.smoothing;
    }

    public final float getCosAngle() {
        return this.cosAngle;
    }

    public final float getSinAngle() {
        return this.sinAngle;
    }

    public final float getExpectedRoundCut() {
        return this.expectedRoundCut;
    }

    public final float getExpectedCut() {
        return (1 + this.smoothing) * this.expectedRoundCut;
    }

    /* JADX INFO: renamed from: getCenter-1ufDz9w, reason: not valid java name and from getter */
    public final long getCenter() {
        return this.center;
    }

    /* JADX INFO: renamed from: setCenter-DnnuFBc, reason: not valid java name */
    public final void m125setCenterDnnuFBc(long j) {
        this.center = j;
    }

    public static /* synthetic */ List getCubics$default(RoundedCorner roundedCorner, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            f2 = f;
        }
        return roundedCorner.getCubics(f, f2);
    }

    public final List<Cubic> getCubics(float allowedCut0, float allowedCut1) {
        float allowedCut = Math.min(allowedCut0, allowedCut1);
        if (this.expectedRoundCut < 1.0E-4f || allowedCut < 1.0E-4f || this.cornerRadius < 1.0E-4f) {
            this.center = this.p1;
            return CollectionsKt.listOf(Cubic.INSTANCE.straightLine(PointKt.m107getXDnnuFBc(this.p1), PointKt.m108getYDnnuFBc(this.p1), PointKt.m107getXDnnuFBc(this.p1), PointKt.m108getYDnnuFBc(this.p1)));
        }
        float actualRoundCut = Math.min(allowedCut, this.expectedRoundCut);
        float actualSmoothing0 = calculateActualSmoothingValue(allowedCut0);
        float actualSmoothing1 = calculateActualSmoothingValue(allowedCut1);
        float actualR = (this.cornerRadius * actualRoundCut) / this.expectedRoundCut;
        float centerDistance = (float) Math.sqrt(Utils.square(actualR) + Utils.square(actualRoundCut));
        this.center = PointKt.m111plusybeJwSQ(this.p1, PointKt.m113timesso9K2fw(PointKt.m104getDirectionDnnuFBc(PointKt.m101divso9K2fw(PointKt.m111plusybeJwSQ(this.d1, this.d2), 2.0f)), centerDistance));
        long circleIntersection0 = PointKt.m111plusybeJwSQ(this.p1, PointKt.m113timesso9K2fw(this.d1, actualRoundCut));
        long circleIntersection2 = PointKt.m111plusybeJwSQ(this.p1, PointKt.m113timesso9K2fw(this.d2, actualRoundCut));
        Cubic flanking0 = m117computeFlankingCurveoAJzIJU(actualRoundCut, actualSmoothing0, this.p1, this.p0, circleIntersection0, circleIntersection2, this.center, actualR);
        Cubic flanking2 = m117computeFlankingCurveoAJzIJU(actualRoundCut, actualSmoothing1, this.p1, this.p2, circleIntersection2, circleIntersection0, this.center, actualR).reverse();
        return CollectionsKt.listOf((Object[]) new Cubic[]{flanking0, Cubic.INSTANCE.circularArc(PointKt.m107getXDnnuFBc(this.center), PointKt.m108getYDnnuFBc(this.center), flanking0.getAnchor1X(), flanking0.getAnchor1Y(), flanking2.getAnchor0X(), flanking2.getAnchor0Y()), flanking2});
    }

    private final float calculateActualSmoothingValue(float allowedCut) {
        if (allowedCut > getExpectedCut()) {
            return this.smoothing;
        }
        if (allowedCut > this.expectedRoundCut) {
            return (this.smoothing * (allowedCut - this.expectedRoundCut)) / (getExpectedCut() - this.expectedRoundCut);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: computeFlankingCurve-oAJzIJU, reason: not valid java name */
    private final Cubic m117computeFlankingCurveoAJzIJU(float actualRoundCut, float actualSmoothingValues, long corner, long sideStart, long circleSegmentIntersection, long otherCircleSegmentIntersection, long circleCenter, float actualR) {
        long sideDirection = PointKt.m104getDirectionDnnuFBc(PointKt.m110minusybeJwSQ(sideStart, corner));
        long curveStart = PointKt.m111plusybeJwSQ(corner, PointKt.m113timesso9K2fw(PointKt.m113timesso9K2fw(sideDirection, actualRoundCut), 1 + actualSmoothingValues));
        long p = PointKt.m109interpolatedLqxh1s(circleSegmentIntersection, PointKt.m101divso9K2fw(PointKt.m111plusybeJwSQ(circleSegmentIntersection, otherCircleSegmentIntersection), 2.0f), actualSmoothingValues);
        long curveEnd = PointKt.m111plusybeJwSQ(circleCenter, PointKt.m113timesso9K2fw(Utils.directionVector(PointKt.m107getXDnnuFBc(p) - PointKt.m107getXDnnuFBc(circleCenter), PointKt.m108getYDnnuFBc(p) - PointKt.m108getYDnnuFBc(circleCenter)), actualR));
        long circleTangent = Utils.m128rotate90DnnuFBc(PointKt.m110minusybeJwSQ(curveEnd, circleCenter));
        FloatFloatPair floatFloatPairM118lineIntersectionCBFvKDc = m118lineIntersectionCBFvKDc(sideStart, sideDirection, curveEnd, circleTangent);
        long anchorEnd = floatFloatPairM118lineIntersectionCBFvKDc != null ? floatFloatPairM118lineIntersectionCBFvKDc.getPackedValue() : circleSegmentIntersection;
        long anchorStart = PointKt.m101divso9K2fw(PointKt.m111plusybeJwSQ(curveStart, PointKt.m113timesso9K2fw(anchorEnd, 2.0f)), 3.0f);
        return new Cubic(curveStart, anchorStart, anchorEnd, curveEnd, null);
    }

    /* JADX INFO: renamed from: lineIntersection-CBFvKDc, reason: not valid java name */
    private final FloatFloatPair m118lineIntersectionCBFvKDc(long p0, long d0, long p1, long d1) {
        long rotatedD1 = Utils.m128rotate90DnnuFBc(d1);
        float den = PointKt.m103dotProductybeJwSQ(d0, rotatedD1);
        if (Math.abs(den) < 1.0E-4f) {
            return null;
        }
        float num = PointKt.m103dotProductybeJwSQ(PointKt.m110minusybeJwSQ(p1, p0), rotatedD1);
        if (Math.abs(den) < Math.abs(num) * 1.0E-4f) {
            return null;
        }
        float k = num / den;
        return FloatFloatPair.m12boximpl(PointKt.m111plusybeJwSQ(p0, PointKt.m113timesso9K2fw(d0, k)));
    }
}
