package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.graphics.shapes.RoundedPolygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Shapes.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a0\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a4\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\b\b\u0003\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001a>\u0010\u0012\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001a\u0084\u0001\u0010\u0014\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0003\u0010\b\u001a\u00020\u00052\b\b\u0003\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001aN\u0010\u001b\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u001ad\u0010\u001c\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007¨\u0006\u001d"}, d2 = {"pillStarVerticesFromNumVerts", "", "numVerticesPerRadius", "", "width", "", "height", "innerRadius", "vertexSpacing", "startLocation", "centerX", "centerY", "starVerticesFromNumVerts", "radius", "circle", "Landroidx/graphics/shapes/RoundedPolygon;", "Landroidx/graphics/shapes/RoundedPolygon$Companion;", "numVertices", "pill", "smoothing", "pillStar", "innerRadiusRatio", "rounding", "Landroidx/graphics/shapes/CornerRounding;", "innerRounding", "perVertexRounding", "", "rectangle", "star", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ShapesKt {
    public static final RoundedPolygon circle(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, 0, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, 0.0f, 0.0f, 0.0f, 14, null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, f, 0.0f, 0.0f, 12, null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, f, f2, 0.0f, 8, null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 31, null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, 0.0f, 0.0f, 0.0f, 0.0f, 30, null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, 0.0f, 0.0f, 0.0f, 28, null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, f3, 0.0f, 0.0f, 24, null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2, float f3, float f4) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, f3, f4, 0.0f, 16, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, 0.0f, 0.0f, 0, 0.0f, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2047, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, 0.0f, 0, 0.0f, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2046, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, 0, 0.0f, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2044, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, i, 0.0f, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2040, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, i, f3, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2032, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 2016, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, cornerRounding, null, 0.0f, 0.0f, 0.0f, 0.0f, 1984, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, cornerRounding, list, 0.0f, 0.0f, 0.0f, 0.0f, 1920, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list, float f4) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, cornerRounding, list, f4, 0.0f, 0.0f, 0.0f, 1792, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list, float f4, float f5) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, cornerRounding, list, f4, f5, 0.0f, 0.0f, 1536, null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list, float f4, float f5, float f6) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return pillStar$default(companion, f, f2, i, f3, rounding, cornerRounding, list, f4, f5, f6, 0.0f, 1024, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, 0.0f, 0.0f, null, null, null, 0.0f, 0.0f, 254, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, f, 0.0f, null, null, null, 0.0f, 0.0f, 252, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, f, f2, null, null, null, 0.0f, 0.0f, 248, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding rounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return star$default(companion, i, f, f2, rounding, null, null, 0.0f, 0.0f, 240, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding rounding, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return star$default(companion, i, f, f2, rounding, cornerRounding, null, 0.0f, 0.0f, 224, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return star$default(companion, i, f, f2, rounding, cornerRounding, list, 0.0f, 0.0f, 192, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding rounding, CornerRounding cornerRounding, List<CornerRounding> list, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return star$default(companion, i, f, f2, rounding, cornerRounding, list, f3, 0.0f, 128, null);
    }

    public static /* synthetic */ RoundedPolygon circle$default(RoundedPolygon.Companion companion, int i, float f, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8;
        }
        if ((i2 & 2) != 0) {
            f = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        }
        return circle(companion, i, f, f2, f3);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion $this$circle, int numVertices, float radius, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$circle, "<this>");
        if (numVertices < 3) {
            throw new IllegalArgumentException("Circle must have at least three vertices");
        }
        float theta = Utils.getFloatPi() / numVertices;
        float polygonRadius = radius / ((float) Math.cos(theta));
        return RoundedPolygonKt.RoundedPolygon$default(numVertices, polygonRadius, centerX, centerY, new CornerRounding(radius, 0.0f, 2, null), null, 32, null);
    }

    public static /* synthetic */ RoundedPolygon rectangle$default(RoundedPolygon.Companion companion, float f, float f2, CornerRounding cornerRounding, List list, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 2.0f;
        }
        if ((i & 2) != 0) {
            f2 = 2.0f;
        }
        if ((i & 4) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i & 8) != 0) {
            list = null;
        }
        if ((i & 16) != 0) {
            f3 = 0.0f;
        }
        if ((i & 32) != 0) {
            f4 = 0.0f;
        }
        return rectangle(companion, f, f2, cornerRounding, list, f3, f4);
    }

    public static final RoundedPolygon rectangle(RoundedPolygon.Companion $this$rectangle, float width, float height, CornerRounding rounding, List<CornerRounding> list, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$rectangle, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        float f = 2;
        float left = centerX - (width / f);
        float top = centerY - (height / f);
        float right = (width / f) + centerX;
        float bottom = (height / f) + centerY;
        return RoundedPolygonKt.RoundedPolygon(new float[]{right, bottom, left, bottom, left, top, right, top}, rounding, list, centerX, centerY);
    }

    public static /* synthetic */ RoundedPolygon star$default(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f3, float f4, int i2, Object obj) {
        return star(companion, i, (i2 & 2) != 0 ? 1.0f : f, (i2 & 4) != 0 ? 0.5f : f2, (i2 & 8) != 0 ? CornerRounding.Unrounded : cornerRounding, (i2 & 16) != 0 ? null : cornerRounding2, (i2 & 32) == 0 ? list : null, (i2 & 64) != 0 ? 0.0f : f3, (i2 & 128) == 0 ? f4 : 0.0f);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion $this$star, int numVerticesPerRadius, float radius, float innerRadius, CornerRounding rounding, CornerRounding innerRounding, List<CornerRounding> list, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$star, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        if (radius <= 0.0f || innerRadius <= 0.0f) {
            throw new IllegalArgumentException("Star radii must both be greater than 0");
        }
        if (innerRadius >= radius) {
            throw new IllegalArgumentException("innerRadius must be less than radius");
        }
        List<CornerRounding> list2 = list;
        if (list2 == null && innerRounding != null) {
            char c = 0;
            Iterable $this$flatMap$iv = RangesKt.until(0, numVerticesPerRadius);
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$flatMap$iv.iterator();
            while (it.hasNext()) {
                ((IntIterator) it).nextInt();
                char c2 = c;
                CornerRounding[] cornerRoundingArr = new CornerRounding[2];
                cornerRoundingArr[c2] = rounding;
                cornerRoundingArr[1] = innerRounding;
                Iterable list$iv$iv = CollectionsKt.listOf((Object[]) cornerRoundingArr);
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                c = c2;
            }
            list2 = (List) destination$iv$iv;
        }
        return RoundedPolygonKt.RoundedPolygon(starVerticesFromNumVerts(numVerticesPerRadius, radius, innerRadius, centerX, centerY), rounding, list2, centerX, centerY);
    }

    public static /* synthetic */ RoundedPolygon pill$default(RoundedPolygon.Companion companion, float f, float f2, float f3, float f4, float f5, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 2.0f;
        }
        if ((i & 2) != 0) {
            f2 = 1.0f;
        }
        if ((i & 4) != 0) {
            f3 = 0.0f;
        }
        if ((i & 8) != 0) {
            f4 = 0.0f;
        }
        return pill(companion, f, f2, f3, f4, (i & 16) != 0 ? 0.0f : f5);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion $this$pill, float width, float height, float smoothing, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$pill, "<this>");
        if (!(width > 0.0f && height > 0.0f)) {
            throw new IllegalArgumentException("Pill shapes must have positive width and height");
        }
        float f = 2;
        float wHalf = width / f;
        float hHalf = height / f;
        return RoundedPolygonKt.RoundedPolygon$default(new float[]{wHalf + centerX, hHalf + centerY, (-wHalf) + centerX, hHalf + centerY, (-wHalf) + centerX, (-hHalf) + centerY, wHalf + centerX, (-hHalf) + centerY}, new CornerRounding(Math.min(wHalf, hHalf), smoothing), null, centerX, centerY, 4, null);
    }

    public static /* synthetic */ RoundedPolygon pillStar$default(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f4, float f5, float f6, float f7, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f = 2.0f;
        }
        if ((i2 & 2) != 0) {
            f2 = 1.0f;
        }
        if ((i2 & 4) != 0) {
            i = 8;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.5f;
        }
        if ((i2 & 16) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i2 & 32) != 0) {
            cornerRounding2 = null;
        }
        if ((i2 & 64) != 0) {
            list = null;
        }
        if ((i2 & 128) != 0) {
            f4 = 0.5f;
        }
        if ((i2 & 256) != 0) {
            f5 = 0.0f;
        }
        if ((i2 & 512) != 0) {
            f6 = 0.0f;
        }
        float f8 = (i2 & 1024) != 0 ? 0.0f : f7;
        float f9 = f5;
        float f10 = f6;
        return pillStar(companion, f, f2, i, f3, cornerRounding, cornerRounding2, list, f4, f9, f10, f8);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion $this$pillStar, float width, float height, int numVerticesPerRadius, float innerRadiusRatio, CornerRounding rounding, CornerRounding innerRounding, List<CornerRounding> list, float vertexSpacing, float startLocation, float centerX, float centerY) {
        int i;
        Intrinsics.checkNotNullParameter($this$pillStar, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        if (!(width > 0.0f && height > 0.0f)) {
            throw new IllegalArgumentException("Pill shapes must have positive width and height");
        }
        if (!(innerRadiusRatio > 0.0f && innerRadiusRatio <= 1.0f)) {
            throw new IllegalArgumentException("innerRadius must be between 0 and 1");
        }
        List<CornerRounding> list2 = list;
        if (list2 != null || innerRounding == null) {
            i = numVerticesPerRadius;
        } else {
            i = numVerticesPerRadius;
            Iterable $this$flatMap$iv = RangesKt.until(0, i);
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$flatMap$iv.iterator();
            while (it.hasNext()) {
                ((IntIterator) it).nextInt();
                Iterable list$iv$iv = CollectionsKt.listOf((Object[]) new CornerRounding[]{rounding, innerRounding});
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            list2 = (List) destination$iv$iv;
        }
        return RoundedPolygonKt.RoundedPolygon(pillStarVerticesFromNumVerts(i, width, height, innerRadiusRatio, vertexSpacing, startLocation, centerX, centerY), rounding, list2, centerX, centerY);
    }

    private static final float[] pillStarVerticesFromNumVerts(int numVerticesPerRadius, float width, float height, float innerRadius, float vertexSpacing, float startLocation, float centerX, float centerY) {
        int i;
        float perimeter;
        long vertex;
        float endcapRadius = Math.min(width, height);
        float vSegLen = RangesKt.coerceAtLeast(height - width, 0.0f);
        float hSegLen = RangesKt.coerceAtLeast(width - height, 0.0f);
        float currRadius = 2;
        float vSegHalf = vSegLen / currRadius;
        float hSegHalf = hSegLen / currRadius;
        float circlePerimeter = Utils.getTwoPi() * endcapRadius * Utils.interpolate(innerRadius, 1.0f, vertexSpacing);
        float perimeter2 = (currRadius * hSegLen) + (currRadius * vSegLen) + circlePerimeter;
        float[] sections = new float[11];
        sections[0] = 0.0f;
        sections[1] = vSegLen / currRadius;
        float f = 4;
        sections[2] = sections[1] + (circlePerimeter / f);
        sections[3] = sections[2] + hSegLen;
        sections[4] = sections[3] + (circlePerimeter / f);
        sections[5] = sections[4] + vSegLen;
        sections[6] = sections[5] + (circlePerimeter / f);
        sections[7] = sections[6] + hSegLen;
        sections[8] = sections[7] + (circlePerimeter / f);
        sections[9] = sections[8] + (vSegLen / currRadius);
        sections[10] = perimeter2;
        float tPerVertex = perimeter2 / (numVerticesPerRadius * 2);
        float secStart = 0.0f;
        float secEnd = sections[1];
        float t = startLocation * perimeter2;
        float[] result = new float[numVerticesPerRadius * 4];
        int arrayIndex = 0;
        int currSecIndex = 0;
        long rectBR = FloatFloatPair.m15constructorimpl(hSegHalf, vSegHalf);
        long rectBL = FloatFloatPair.m15constructorimpl(-hSegHalf, vSegHalf);
        float tPerVertex2 = -vSegHalf;
        long rectTL = FloatFloatPair.m15constructorimpl(-hSegHalf, tPerVertex2);
        boolean inner = false;
        long rectTR = FloatFloatPair.m15constructorimpl(hSegHalf, -vSegHalf);
        int i2 = numVerticesPerRadius * 2;
        int i3 = 0;
        while (i3 < i2) {
            float boundedT = t % perimeter2;
            if (boundedT < secStart) {
                currSecIndex = 0;
            }
            while (true) {
                int i4 = i2;
                if (boundedT >= sections[(currSecIndex + 1) % sections.length]) {
                    currSecIndex = (currSecIndex + 1) % sections.length;
                    secStart = sections[currSecIndex];
                    secEnd = sections[(currSecIndex + 1) % sections.length];
                    i2 = i4;
                } else {
                    float tInSection = boundedT - secStart;
                    float tProportion = tInSection / (secEnd - secStart);
                    float currRadius2 = inner ? endcapRadius * innerRadius : endcapRadius;
                    switch (currSecIndex) {
                        case 0:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m15constructorimpl(currRadius2, tProportion * vSegHalf);
                            break;
                        case 1:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = PointKt.m111plusybeJwSQ(Utils.m127radialToCartesianL6JJ3z0$default(currRadius2, (Utils.getFloatPi() * tProportion) / currRadius, 0L, 4, null), rectBR);
                            break;
                        case 2:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m15constructorimpl(hSegHalf - (tProportion * hSegLen), currRadius2);
                            break;
                        case 3:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = PointKt.m111plusybeJwSQ(Utils.m127radialToCartesianL6JJ3z0$default(currRadius2, (Utils.getFloatPi() / currRadius) + ((Utils.getFloatPi() * tProportion) / currRadius), 0L, 4, null), rectBL);
                            break;
                        case 4:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m15constructorimpl(-currRadius2, vSegHalf - (tProportion * vSegLen));
                            break;
                        case 5:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = PointKt.m111plusybeJwSQ(Utils.m127radialToCartesianL6JJ3z0$default(currRadius2, Utils.getFloatPi() + ((Utils.getFloatPi() * tProportion) / currRadius), 0L, 4, null), rectTL);
                            break;
                        case 6:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m15constructorimpl((-hSegHalf) + (tProportion * hSegLen), -currRadius2);
                            break;
                        case 7:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = PointKt.m111plusybeJwSQ(Utils.m127radialToCartesianL6JJ3z0$default(currRadius2, (Utils.getFloatPi() * 1.5f) + ((Utils.getFloatPi() * tProportion) / currRadius), 0L, 4, null), rectTR);
                            break;
                        default:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m15constructorimpl(currRadius2, (-vSegHalf) + (tProportion * vSegHalf));
                            break;
                    }
                    int arrayIndex2 = arrayIndex + 1;
                    result[arrayIndex] = PointKt.m107getXDnnuFBc(vertex) + centerX;
                    arrayIndex = arrayIndex2 + 1;
                    result[arrayIndex2] = PointKt.m108getYDnnuFBc(vertex) + centerY;
                    t += tPerVertex;
                    inner = !inner;
                    i3 = i + 1;
                    i2 = i4;
                    perimeter2 = perimeter;
                }
            }
        }
        return result;
    }

    private static final float[] starVerticesFromNumVerts(int numVerticesPerRadius, float radius, float innerRadius, float centerX, float centerY) {
        float[] result = new float[numVerticesPerRadius * 4];
        int arrayIndex = 0;
        for (int i = 0; i < numVerticesPerRadius; i++) {
            long vertex = Utils.m127radialToCartesianL6JJ3z0$default(radius, (Utils.getFloatPi() / numVerticesPerRadius) * 2 * i, 0L, 4, null);
            int arrayIndex2 = arrayIndex + 1;
            result[arrayIndex] = PointKt.m107getXDnnuFBc(vertex) + centerX;
            int arrayIndex3 = arrayIndex2 + 1;
            result[arrayIndex2] = PointKt.m108getYDnnuFBc(vertex) + centerY;
            long vertex2 = Utils.m127radialToCartesianL6JJ3z0$default(innerRadius, (Utils.getFloatPi() / numVerticesPerRadius) * ((i * 2) + 1), 0L, 4, null);
            int arrayIndex4 = arrayIndex3 + 1;
            result[arrayIndex3] = PointKt.m107getXDnnuFBc(vertex2) + centerX;
            arrayIndex = arrayIndex4 + 1;
            result[arrayIndex4] = PointKt.m108getYDnnuFBc(vertex2) + centerY;
        }
        return result;
    }
}
