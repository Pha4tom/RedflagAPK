package androidx.graphics.shapes;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;

/* JADX INFO: compiled from: Cubic.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003*\n\u0010\u000b\"\u00020\f2\u00020\f¨\u0006\r"}, d2 = {"Cubic", "Landroidx/graphics/shapes/Cubic;", "anchor0X", "", "anchor0Y", "control0X", "control0Y", "control1X", "control1Y", "anchor1X", "anchor1Y", "TransformResult", "Landroidx/collection/FloatFloatPair;", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CubicKt {
    public static final Cubic Cubic(float anchor0X, float anchor0Y, float control0X, float control0Y, float control1X, float control1Y, float anchor1X, float anchor1Y) {
        return new Cubic(new float[]{anchor0X, anchor0Y, control0X, control0Y, control1X, control1Y, anchor1X, anchor1Y});
    }
}
