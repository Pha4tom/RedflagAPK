package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0011\u001a\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0000\u001a\"\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0080\bø\u0001\u0000\u001a\u0019\u0010\u0019\u001a\u00060\u000bj\u0002`\f2\u0006\u0010\u001a\u001a\u00020\u0001H\u0000¢\u0006\u0002\u0010\u001b\u001a!\u0010\u0019\u001a\u00060\u000bj\u0002`\f2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0000¢\u0006\u0002\u0010\u001c\u001a\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0000\u001a\u0018\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0000\u001a*\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u00012\b\b\u0002\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0000\u001a \u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0000\u001a\u0018\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0001H\u0000\u001a4\u0010,\u001a\u00060\u000bj\u0002`\f2\u0006\u0010-\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00012\f\b\u0002\u0010.\u001a\u00060\u000bj\u0002`\fH\u0000ø\u0001\u0001¢\u0006\u0004\b/\u00100\u001a\u0010\u00101\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001H\u0000\u001a\u001e\u00102\u001a\u00060\u000bj\u0002`\f*\u00060\u000bj\u0002`\fH\u0000ø\u0001\u0001¢\u0006\u0004\b3\u00104\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0005\u001a\u00020\u0001X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0014\u0010\b\u001a\u00020\u0001X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\"\u001a\u0010\n\u001a\u00060\u000bj\u0002`\fX\u0080\u0004¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u000e\n\u0005\b\u009920\u0001\n\u0005\b¡\u001e0\u0001¨\u00065"}, d2 = {"AngleEpsilon", "", "DEBUG", "", "DistanceEpsilon", "FloatPi", "getFloatPi", "()F", "TwoPi", "getTwoPi", "Zero", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "getZero", "()J", "J", "angle", "x", "y", "debugLog", "", "tag", "", "messageFactory", "Lkotlin/Function0;", "directionVector", "angleRadians", "(F)J", "(FF)J", "distance", "distanceSquared", "findMinimum", "v0", "v1", "tolerance", "f", "Landroidx/graphics/shapes/FindMinimumFunction;", "interpolate", "start", "stop", "fraction", "positiveModulo", "num", "mod", "radialToCartesian", "radius", "center", "radialToCartesian-L6JJ3z0", "(FFJ)J", "square", "rotate90", "rotate90-DnnuFBc", "(J)J", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class Utils {
    public static final float AngleEpsilon = 1.0E-6f;
    public static final boolean DEBUG = false;
    public static final float DistanceEpsilon = 1.0E-4f;
    private static final long Zero = FloatFloatPair.m15constructorimpl(0.0f, 0.0f);
    private static final float FloatPi = 3.1415927f;
    private static final float TwoPi = 6.2831855f;

    public static final float distance(float x, float y) {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public static final float distanceSquared(float x, float y) {
        return (x * x) + (y * y);
    }

    public static final long directionVector(float x, float y) {
        float d = distance(x, y);
        if (d > 0.0f) {
            return FloatFloatPair.m15constructorimpl(x / d, y / d);
        }
        throw new IllegalArgumentException("Required distance greater than zero".toString());
    }

    public static final long directionVector(float angleRadians) {
        return FloatFloatPair.m15constructorimpl((float) Math.cos(angleRadians), (float) Math.sin(angleRadians));
    }

    public static final float angle(float x, float y) {
        return (((float) Math.atan2(y, x)) + TwoPi) % TwoPi;
    }

    /* JADX INFO: renamed from: radialToCartesian-L6JJ3z0$default, reason: not valid java name */
    public static /* synthetic */ long m127radialToCartesianL6JJ3z0$default(float f, float f2, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = Zero;
        }
        return m126radialToCartesianL6JJ3z0(f, f2, j);
    }

    /* JADX INFO: renamed from: radialToCartesian-L6JJ3z0, reason: not valid java name */
    public static final long m126radialToCartesianL6JJ3z0(float radius, float angleRadians, long center) {
        return PointKt.m111plusybeJwSQ(PointKt.m113timesso9K2fw(directionVector(angleRadians), radius), center);
    }

    /* JADX INFO: renamed from: rotate90-DnnuFBc, reason: not valid java name */
    public static final long m128rotate90DnnuFBc(long $this$rotate90_u2dDnnuFBc) {
        return FloatFloatPair.m15constructorimpl(-PointKt.m108getYDnnuFBc($this$rotate90_u2dDnnuFBc), PointKt.m107getXDnnuFBc($this$rotate90_u2dDnnuFBc));
    }

    public static final long getZero() {
        return Zero;
    }

    public static final float getFloatPi() {
        return FloatPi;
    }

    public static final float getTwoPi() {
        return TwoPi;
    }

    public static final float square(float x) {
        return x * x;
    }

    public static final float interpolate(float start, float stop, float fraction) {
        return ((1 - fraction) * start) + (fraction * stop);
    }

    public static final float positiveModulo(float num, float mod) {
        return ((num % mod) + mod) % mod;
    }

    public static /* synthetic */ float findMinimum$default(float f, float f2, float f3, FindMinimumFunction findMinimumFunction, int i, Object obj) {
        if ((i & 4) != 0) {
            f3 = 0.001f;
        }
        return findMinimum(f, f2, f3, findMinimumFunction);
    }

    public static final float findMinimum(float v0, float v1, float tolerance, FindMinimumFunction f) {
        Intrinsics.checkNotNullParameter(f, "f");
        float a = v0;
        float b = v1;
        while (b - a > tolerance) {
            float f2 = 2;
            float f3 = 3;
            float c1 = ((f2 * a) + b) / f3;
            float c2 = ((f2 * b) + a) / f3;
            if (f.invoke(c1) < f.invoke(c2)) {
                b = c2;
            } else {
                a = c1;
            }
        }
        return (a + b) / 2;
    }

    public static final void debugLog(String tag, Function0<String> messageFactory) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(messageFactory, "messageFactory");
    }
}
