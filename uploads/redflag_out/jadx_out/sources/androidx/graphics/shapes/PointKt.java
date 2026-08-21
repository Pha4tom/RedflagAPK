package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Point.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a6\u0010\b\u001a\u00060\u0002j\u0002`\u00032\n\u0010\t\u001a\u00060\u0002j\u0002`\u00032\n\u0010\n\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u000b\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a&\u0010\u000e\u001a\u00020\u000f*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001a2\u0010\u0013\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\b\b\u0002\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0016\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019\u001a&\u0010\u001a\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a*\u0010\u001a\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a\u001e\u0010!\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u001a\u001a\u0010$\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b%\u0010\u0005\u001a\u001a\u0010&\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b'\u0010\u0005\u001a+\u0010(\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a+\u0010+\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b,\u0010*\u001a'\u0010-\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010\u0019\u001a'\u0010/\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b0\u0010\u0019\u001a&\u00101\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u00102\u001a\u000203H\u0000ø\u0001\u0000¢\u0006\u0004\b4\u00105\u001a\u001f\u00106\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u0003H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b7\u0010#\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001c\u0010\u0006\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005*\f\b\u0000\u00108\"\u00020\u00022\u00020\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u00069"}, d2 = {"x", "", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "getX-DnnuFBc", "(J)F", "y", "getY-DnnuFBc", "interpolate", "start", "stop", "fraction", "interpolate-dLqxh1s", "(JJF)J", "clockwise", "", "other", "clockwise-ybeJwSQ", "(JJ)Z", "copy", "copy-5P9i7ZU", "(JFF)J", "div", "operand", "div-so9K2fw", "(JF)J", "dotProduct", "dotProduct-ybeJwSQ", "(JJ)F", "otherX", "otherY", "dotProduct-5P9i7ZU", "(JFF)F", "getDirection", "getDirection-DnnuFBc", "(J)J", "getDistance", "getDistance-DnnuFBc", "getDistanceSquared", "getDistanceSquared-DnnuFBc", "minus", "minus-ybeJwSQ", "(JJ)J", "plus", "plus-ybeJwSQ", "rem", "rem-so9K2fw", "times", "times-so9K2fw", "transformed", "f", "Landroidx/graphics/shapes/PointTransformer;", "transformed-so9K2fw", "(JLandroidx/graphics/shapes/PointTransformer;)J", "unaryMinus", "unaryMinus-DnnuFBc", "Point", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class PointKt {
    /* JADX INFO: renamed from: getX-DnnuFBc, reason: not valid java name */
    public static final float m107getXDnnuFBc(long $this$x) {
        int bits$iv$iv = (int) ($this$x >> 32);
        return Float.intBitsToFloat(bits$iv$iv);
    }

    /* JADX INFO: renamed from: getY-DnnuFBc, reason: not valid java name */
    public static final float m108getYDnnuFBc(long $this$y) {
        int bits$iv$iv = (int) (4294967295L & $this$y);
        return Float.intBitsToFloat(bits$iv$iv);
    }

    /* JADX INFO: renamed from: copy-5P9i7ZU, reason: not valid java name */
    public static final long m99copy5P9i7ZU(long $this$copy_u2d5P9i7ZU, float x, float y) {
        return FloatFloatPair.m15constructorimpl(x, y);
    }

    /* JADX INFO: renamed from: copy-5P9i7ZU$default, reason: not valid java name */
    public static /* synthetic */ long m100copy5P9i7ZU$default(long j, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            int bits$iv$iv = (int) (j >> 32);
            f = Float.intBitsToFloat(bits$iv$iv);
        }
        if ((i & 2) != 0) {
            int bits$iv$iv2 = (int) (4294967295L & j);
            f2 = Float.intBitsToFloat(bits$iv$iv2);
        }
        return m99copy5P9i7ZU(j, f, f2);
    }

    /* JADX INFO: renamed from: getDistance-DnnuFBc, reason: not valid java name */
    public static final float m105getDistanceDnnuFBc(long $this$getDistance_u2dDnnuFBc) {
        return (float) Math.sqrt((m107getXDnnuFBc($this$getDistance_u2dDnnuFBc) * m107getXDnnuFBc($this$getDistance_u2dDnnuFBc)) + (m108getYDnnuFBc($this$getDistance_u2dDnnuFBc) * m108getYDnnuFBc($this$getDistance_u2dDnnuFBc)));
    }

    /* JADX INFO: renamed from: getDistanceSquared-DnnuFBc, reason: not valid java name */
    public static final float m106getDistanceSquaredDnnuFBc(long $this$getDistanceSquared_u2dDnnuFBc) {
        return (m107getXDnnuFBc($this$getDistanceSquared_u2dDnnuFBc) * m107getXDnnuFBc($this$getDistanceSquared_u2dDnnuFBc)) + (m108getYDnnuFBc($this$getDistanceSquared_u2dDnnuFBc) * m108getYDnnuFBc($this$getDistanceSquared_u2dDnnuFBc));
    }

    /* JADX INFO: renamed from: dotProduct-ybeJwSQ, reason: not valid java name */
    public static final float m103dotProductybeJwSQ(long $this$dotProduct_u2dybeJwSQ, long other) {
        return (m107getXDnnuFBc($this$dotProduct_u2dybeJwSQ) * m107getXDnnuFBc(other)) + (m108getYDnnuFBc($this$dotProduct_u2dybeJwSQ) * m108getYDnnuFBc(other));
    }

    /* JADX INFO: renamed from: dotProduct-5P9i7ZU, reason: not valid java name */
    public static final float m102dotProduct5P9i7ZU(long $this$dotProduct_u2d5P9i7ZU, float otherX, float otherY) {
        return (m107getXDnnuFBc($this$dotProduct_u2d5P9i7ZU) * otherX) + (m108getYDnnuFBc($this$dotProduct_u2d5P9i7ZU) * otherY);
    }

    /* JADX INFO: renamed from: clockwise-ybeJwSQ, reason: not valid java name */
    public static final boolean m98clockwiseybeJwSQ(long $this$clockwise_u2dybeJwSQ, long other) {
        return (m107getXDnnuFBc($this$clockwise_u2dybeJwSQ) * m108getYDnnuFBc(other)) - (m108getYDnnuFBc($this$clockwise_u2dybeJwSQ) * m107getXDnnuFBc(other)) > 0.0f;
    }

    /* JADX INFO: renamed from: getDirection-DnnuFBc, reason: not valid java name */
    public static final long m104getDirectionDnnuFBc(long $this$getDirection_u2dDnnuFBc) {
        float d = m105getDistanceDnnuFBc($this$getDirection_u2dDnnuFBc);
        if (!(d > 0.0f)) {
            throw new IllegalArgumentException("Can't get the direction of a 0-length vector".toString());
        }
        long $this$getDirection_DnnuFBc_u24lambda_u241 = m101divso9K2fw($this$getDirection_u2dDnnuFBc, d);
        return $this$getDirection_DnnuFBc_u24lambda_u241;
    }

    /* JADX INFO: renamed from: unaryMinus-DnnuFBc, reason: not valid java name */
    public static final long m115unaryMinusDnnuFBc(long $this$unaryMinus_u2dDnnuFBc) {
        return FloatFloatPair.m15constructorimpl(-m107getXDnnuFBc($this$unaryMinus_u2dDnnuFBc), -m108getYDnnuFBc($this$unaryMinus_u2dDnnuFBc));
    }

    /* JADX INFO: renamed from: minus-ybeJwSQ, reason: not valid java name */
    public static final long m110minusybeJwSQ(long $this$minus_u2dybeJwSQ, long other) {
        return FloatFloatPair.m15constructorimpl(m107getXDnnuFBc($this$minus_u2dybeJwSQ) - m107getXDnnuFBc(other), m108getYDnnuFBc($this$minus_u2dybeJwSQ) - m108getYDnnuFBc(other));
    }

    /* JADX INFO: renamed from: plus-ybeJwSQ, reason: not valid java name */
    public static final long m111plusybeJwSQ(long $this$plus_u2dybeJwSQ, long other) {
        return FloatFloatPair.m15constructorimpl(m107getXDnnuFBc($this$plus_u2dybeJwSQ) + m107getXDnnuFBc(other), m108getYDnnuFBc($this$plus_u2dybeJwSQ) + m108getYDnnuFBc(other));
    }

    /* JADX INFO: renamed from: times-so9K2fw, reason: not valid java name */
    public static final long m113timesso9K2fw(long $this$times_u2dso9K2fw, float operand) {
        return FloatFloatPair.m15constructorimpl(m107getXDnnuFBc($this$times_u2dso9K2fw) * operand, m108getYDnnuFBc($this$times_u2dso9K2fw) * operand);
    }

    /* JADX INFO: renamed from: div-so9K2fw, reason: not valid java name */
    public static final long m101divso9K2fw(long $this$div_u2dso9K2fw, float operand) {
        return FloatFloatPair.m15constructorimpl(m107getXDnnuFBc($this$div_u2dso9K2fw) / operand, m108getYDnnuFBc($this$div_u2dso9K2fw) / operand);
    }

    /* JADX INFO: renamed from: rem-so9K2fw, reason: not valid java name */
    public static final long m112remso9K2fw(long $this$rem_u2dso9K2fw, float operand) {
        return FloatFloatPair.m15constructorimpl(m107getXDnnuFBc($this$rem_u2dso9K2fw) % operand, m108getYDnnuFBc($this$rem_u2dso9K2fw) % operand);
    }

    /* JADX INFO: renamed from: interpolate-dLqxh1s, reason: not valid java name */
    public static final long m109interpolatedLqxh1s(long start, long stop, float fraction) {
        return FloatFloatPair.m15constructorimpl(Utils.interpolate(m107getXDnnuFBc(start), m107getXDnnuFBc(stop), fraction), Utils.interpolate(m108getYDnnuFBc(start), m108getYDnnuFBc(stop), fraction));
    }

    /* JADX INFO: renamed from: transformed-so9K2fw, reason: not valid java name */
    public static final long m114transformedso9K2fw(long $this$transformed_u2dso9K2fw, PointTransformer f) {
        Intrinsics.checkNotNullParameter(f, "f");
        long result = f.mo116transformXgqJiTY(m107getXDnnuFBc($this$transformed_u2dso9K2fw), m108getYDnnuFBc($this$transformed_u2dso9K2fw));
        int bits$iv$iv = (int) (result >> 32);
        int bits$iv$iv2 = (int) (4294967295L & result);
        return FloatFloatPair.m15constructorimpl(Float.intBitsToFloat(bits$iv$iv), Float.intBitsToFloat(bits$iv$iv2));
    }
}
