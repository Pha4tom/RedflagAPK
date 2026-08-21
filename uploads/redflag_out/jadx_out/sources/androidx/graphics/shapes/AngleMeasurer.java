package androidx.graphics.shapes;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PolygonMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u000e"}, d2 = {"Landroidx/graphics/shapes/AngleMeasurer;", "Landroidx/graphics/shapes/Measurer;", "centerX", "", "centerY", "(FF)V", "getCenterX", "()F", "getCenterY", "findCubicCutPoint", "c", "Landroidx/graphics/shapes/Cubic;", "m", "measureCubic", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AngleMeasurer implements Measurer {
    private final float centerX;
    private final float centerY;

    public AngleMeasurer(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public final float getCenterX() {
        return this.centerX;
    }

    public final float getCenterY() {
        return this.centerY;
    }

    @Override // androidx.graphics.shapes.Measurer
    public float measureCubic(Cubic c) {
        Intrinsics.checkNotNullParameter(c, "c");
        float it = Utils.positiveModulo(Utils.angle(c.getAnchor1X() - this.centerX, c.getAnchor1Y() - this.centerY) - Utils.angle(c.getAnchor0X() - this.centerX, c.getAnchor0Y() - this.centerY), Utils.getTwoPi());
        if (it > Utils.getTwoPi() - 1.0E-4f) {
            return 0.0f;
        }
        return it;
    }

    @Override // androidx.graphics.shapes.Measurer
    public float findCubicCutPoint(final Cubic c, final float m) {
        Intrinsics.checkNotNullParameter(c, "c");
        final float angle0 = Utils.angle(c.getAnchor0X() - this.centerX, c.getAnchor0Y() - this.centerY);
        return Utils.findMinimum(0.0f, 1.0f, 1.0E-5f, new FindMinimumFunction() { // from class: androidx.graphics.shapes.AngleMeasurer$$ExternalSyntheticLambda0
            @Override // androidx.graphics.shapes.FindMinimumFunction
            public final float invoke(float f) {
                return AngleMeasurer.findCubicCutPoint$lambda$1(c, this, angle0, m, f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float findCubicCutPoint$lambda$1(Cubic c, AngleMeasurer this$0, float $angle0, float $m, float t) {
        Intrinsics.checkNotNullParameter(c, "$c");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        long curvePoint = c.m95pointOnCurveOOQOV4g$graphics_shapes_release(t);
        float angle = Utils.angle(PointKt.m107getXDnnuFBc(curvePoint) - this$0.centerX, PointKt.m108getYDnnuFBc(curvePoint) - this$0.centerY);
        return Math.abs(Utils.positiveModulo(angle - $angle0, Utils.getTwoPi()) - $m);
    }
}
