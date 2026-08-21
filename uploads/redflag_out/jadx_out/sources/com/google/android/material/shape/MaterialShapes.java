package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import androidx.graphics.shapes.CornerRounding;
import androidx.graphics.shapes.RoundedPolygon;
import androidx.graphics.shapes.RoundedPolygonKt;
import androidx.graphics.shapes.ShapesKt;
import androidx.graphics.shapes.Shapes_androidKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class MaterialShapes {
    private static final CornerRounding CORNER_ROUND_15 = new CornerRounding(0.15f, 0.0f);
    private static final CornerRounding CORNER_ROUND_20 = new CornerRounding(0.2f, 0.0f);
    private static final CornerRounding CORNER_ROUND_30 = new CornerRounding(0.3f, 0.0f);
    private static final CornerRounding CORNER_ROUND_50 = new CornerRounding(0.5f, 0.0f);
    private static final CornerRounding CORNER_ROUND_100 = new CornerRounding(1.0f, 0.0f);
    public static final RoundedPolygon CIRCLE = normalize(getCircle(), true);
    public static final RoundedPolygon SQUARE = normalize(getSquare(), true);
    public static final RoundedPolygon SLANTED_SQUARE = normalize(getSlantedSquare(), true);
    public static final RoundedPolygon ARCH = normalize(getArch(), true);
    public static final RoundedPolygon FAN = normalize(getFan(), true);
    public static final RoundedPolygon ARROW = normalize(getArrow(), true);
    public static final RoundedPolygon SEMI_CIRCLE = normalize(getSemiCircle(), true);
    public static final RoundedPolygon OVAL = normalize(getOval(-45.0f), true);
    public static final RoundedPolygon PILL = normalize(getPill(), true);
    public static final RoundedPolygon TRIANGLE = normalize(getTriangle(-90.0f), true);
    public static final RoundedPolygon DIAMOND = normalize(getDiamond(), true);
    public static final RoundedPolygon CLAM_SHELL = normalize(getClamShell(), true);
    public static final RoundedPolygon PENTAGON = normalize(getPentagon(), true);
    public static final RoundedPolygon GEM = normalize(getGem(-90.0f), true);
    public static final RoundedPolygon SUNNY = normalize(getSunny(), true);
    public static final RoundedPolygon VERY_SUNNY = normalize(getVerySunny(), true);
    public static final RoundedPolygon COOKIE_4 = normalize(getCookie4(), true);
    public static final RoundedPolygon COOKIE_6 = normalize(getCookie6(), true);
    public static final RoundedPolygon COOKIE_7 = normalize(getCookie7(), true);
    public static final RoundedPolygon COOKIE_9 = normalize(getCookie9(), true);
    public static final RoundedPolygon COOKIE_12 = normalize(getCookie12(), true);
    public static final RoundedPolygon GHOSTISH = normalize(getGhostish(), true);
    public static final RoundedPolygon CLOVER_4 = normalize(getClover4(), true);
    public static final RoundedPolygon CLOVER_8 = normalize(getClover8(), true);
    public static final RoundedPolygon BURST = normalize(getBurst(), true);
    public static final RoundedPolygon SOFT_BURST = normalize(getSoftBurst(), true);
    public static final RoundedPolygon BOOM = normalize(getBoom(), true);
    public static final RoundedPolygon SOFT_BOOM = normalize(getSoftBoom(), true);
    public static final RoundedPolygon FLOWER = normalize(getFlower(), true);
    public static final RoundedPolygon PUFFY = normalize(getPuffy(), true);
    public static final RoundedPolygon PUFFY_DIAMOND = normalize(getPuffyDiamond(), true);
    public static final RoundedPolygon PIXEL_CIRCLE = normalize(getPixelCircle(), true);
    public static final RoundedPolygon PIXEL_TRIANGLE = normalize(getPixelTriangle(), true);
    public static final RoundedPolygon BUN = normalize(getBun(), true);
    public static final RoundedPolygon HEART = normalize(getHeart(), true);

    private static RoundedPolygon getCircle() {
        return ShapesKt.circle(RoundedPolygon.INSTANCE, 10);
    }

    private static RoundedPolygon getSquare() {
        return ShapesKt.rectangle(RoundedPolygon.INSTANCE, 1.0f, 1.0f, CORNER_ROUND_30, null, 0.0f, 0.0f);
    }

    private static RoundedPolygon getSlantedSquare() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.926f, 0.97f), new CornerRounding(0.189f, 0.811f)));
        points.add(new VertexAndRounding(new PointF(-0.021f, 0.967f), new CornerRounding(0.187f, 0.057f)));
        return customPolygon(points, 2, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getArch() {
        return Shapes_androidKt.transformed(RoundedPolygonKt.RoundedPolygon(4, 1.0f, 0.0f, 0.0f, CornerRounding.Unrounded, Arrays.asList(CORNER_ROUND_100, CORNER_ROUND_100, CORNER_ROUND_20, CORNER_ROUND_20)), createRotationMatrix(-135.0f));
    }

    private static RoundedPolygon getFan() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(1.0f, 1.0f), new CornerRounding(0.148f, 0.417f)));
        points.add(new VertexAndRounding(new PointF(0.0f, 1.0f), new CornerRounding(0.151f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.0f, 0.0f), new CornerRounding(0.148f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.978f, 0.02f), new CornerRounding(0.803f, 0.0f)));
        return customPolygon(points, 1, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getArrow() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.892f), new CornerRounding(0.313f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(-0.216f, 1.05f), new CornerRounding(0.207f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.499f, -0.16f), new CornerRounding(0.215f, 1.0f)));
        points.add(new VertexAndRounding(new PointF(1.225f, 1.06f), new CornerRounding(0.211f, 0.0f)));
        return customPolygon(points, 1, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getSemiCircle() {
        return ShapesKt.rectangle(RoundedPolygon.INSTANCE, 1.6f, 1.0f, CornerRounding.Unrounded, Arrays.asList(CORNER_ROUND_20, CORNER_ROUND_20, CORNER_ROUND_100, CORNER_ROUND_100), 0.0f, 0.0f);
    }

    private static RoundedPolygon getOval() {
        return Shapes_androidKt.transformed(ShapesKt.circle(RoundedPolygon.INSTANCE), createScaleMatrix(1.0f, 0.64f));
    }

    private static RoundedPolygon getOval(float rotateDegrees) {
        return Shapes_androidKt.transformed(getOval(), createRotationMatrix(rotateDegrees));
    }

    private static RoundedPolygon getPill() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.961f, 0.039f), new CornerRounding(0.426f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(1.001f, 0.428f)));
        points.add(new VertexAndRounding(new PointF(1.0f, 0.609f), CORNER_ROUND_100));
        return customPolygon(points, 2, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getTriangle() {
        return RoundedPolygonKt.RoundedPolygon(3, 1.0f, 0.0f, 0.0f, CORNER_ROUND_20);
    }

    private static RoundedPolygon getTriangle(float rotateDegrees) {
        return Shapes_androidKt.transformed(getTriangle(), createRotationMatrix(rotateDegrees));
    }

    private static RoundedPolygon getDiamond() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 1.096f), new CornerRounding(0.151f, 0.524f)));
        points.add(new VertexAndRounding(new PointF(0.04f, 0.5f), new CornerRounding(0.159f, 0.0f)));
        return customPolygon(points, 2, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getClamShell() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.171f, 0.841f), new CornerRounding(0.159f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(-0.02f, 0.5f), new CornerRounding(0.14f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.17f, 0.159f), new CornerRounding(0.159f, 0.0f)));
        return customPolygon(points, 2, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getPentagon() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, -0.009f), new CornerRounding(0.172f, 0.0f)));
        return customPolygon(points, 5, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getGem() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.499f, 1.023f), new CornerRounding(0.241f, 0.778f)));
        points.add(new VertexAndRounding(new PointF(-0.005f, 0.792f), new CornerRounding(0.208f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.073f, 0.258f), new CornerRounding(0.228f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.433f, -0.0f), new CornerRounding(0.491f, 0.0f)));
        return customPolygon(points, 1, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getGem(float rotateDegrees) {
        return Shapes_androidKt.transformed(getGem(), createRotationMatrix(rotateDegrees));
    }

    private static RoundedPolygon getSunny() {
        return ShapesKt.star(RoundedPolygon.INSTANCE, 8, 1.0f, 0.8f, CORNER_ROUND_15);
    }

    private static RoundedPolygon getVerySunny() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 1.08f), new CornerRounding(0.085f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.358f, 0.843f), new CornerRounding(0.085f, 0.0f)));
        return customPolygon(points, 8, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getCookie4() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(1.237f, 1.236f), new CornerRounding(0.258f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.5f, 0.918f), new CornerRounding(0.233f, 0.0f)));
        return customPolygon(points, 4, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getCookie6() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.723f, 0.884f), new CornerRounding(0.394f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.5f, 1.099f), new CornerRounding(0.398f, 0.0f)));
        return customPolygon(points, 6, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getCookie7() {
        return Shapes_androidKt.transformed(ShapesKt.star(RoundedPolygon.INSTANCE, 7, 1.0f, 0.75f, CORNER_ROUND_50), createRotationMatrix(-90.0f));
    }

    private static RoundedPolygon getCookie9() {
        return Shapes_androidKt.transformed(ShapesKt.star(RoundedPolygon.INSTANCE, 9, 1.0f, 0.8f, CORNER_ROUND_50), createRotationMatrix(-90.0f));
    }

    private static RoundedPolygon getCookie12() {
        return Shapes_androidKt.transformed(ShapesKt.star(RoundedPolygon.INSTANCE, 12, 1.0f, 0.8f, CORNER_ROUND_50), createRotationMatrix(-90.0f));
    }

    private static RoundedPolygon getGhostish() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.0f), CORNER_ROUND_100));
        points.add(new VertexAndRounding(new PointF(1.0f, 0.0f), CORNER_ROUND_100));
        points.add(new VertexAndRounding(new PointF(1.0f, 1.14f), new CornerRounding(0.254f, 0.106f)));
        points.add(new VertexAndRounding(new PointF(0.575f, 0.906f), new CornerRounding(0.253f, 0.0f)));
        return customPolygon(points, 1, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getClover4() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.074f)));
        points.add(new VertexAndRounding(new PointF(0.725f, -0.099f), new CornerRounding(0.476f, 0.0f)));
        return customPolygon(points, 4, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getClover8() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.036f)));
        points.add(new VertexAndRounding(new PointF(0.758f, -0.101f), new CornerRounding(0.209f, 0.0f)));
        return customPolygon(points, 8, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getBurst() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, -0.006f), new CornerRounding(0.006f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.592f, 0.158f), new CornerRounding(0.006f, 0.0f)));
        return customPolygon(points, 12, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getSoftBurst() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.193f, 0.277f), new CornerRounding(0.053f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.176f, 0.055f), new CornerRounding(0.053f, 0.0f)));
        return customPolygon(points, 10, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getBoom() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.457f, 0.296f), new CornerRounding(0.007f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.5f, -0.051f), new CornerRounding(0.007f, 0.0f)));
        return customPolygon(points, 15, 0.5f, 0.5f, false);
    }

    private static RoundedPolygon getSoftBoom() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.733f, 0.454f)));
        points.add(new VertexAndRounding(new PointF(0.839f, 0.437f), new CornerRounding(0.532f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.949f, 0.449f), new CornerRounding(0.439f, 1.0f)));
        points.add(new VertexAndRounding(new PointF(0.998f, 0.478f), new CornerRounding(0.174f, 0.0f)));
        return customPolygon(points, 16, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getFlower() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.37f, 0.187f)));
        points.add(new VertexAndRounding(new PointF(0.416f, 0.049f), new CornerRounding(0.381f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.479f, 0.0f), new CornerRounding(0.095f, 0.0f)));
        return customPolygon(points, 8, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getPuffy() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.053f)));
        points.add(new VertexAndRounding(new PointF(0.545f, -0.04f), new CornerRounding(0.405f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.67f, -0.035f), new CornerRounding(0.426f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.717f, 0.066f), new CornerRounding(0.574f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.722f, 0.128f)));
        points.add(new VertexAndRounding(new PointF(0.777f, 0.002f), new CornerRounding(0.36f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.914f, 0.149f), new CornerRounding(0.66f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.926f, 0.289f), new CornerRounding(0.66f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.881f, 0.346f)));
        points.add(new VertexAndRounding(new PointF(0.94f, 0.344f), new CornerRounding(0.126f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(1.003f, 0.437f), new CornerRounding(0.255f, 0.0f)));
        return Shapes_androidKt.transformed(customPolygon(points, 2, 0.5f, 0.5f, true), createScaleMatrix(1.0f, 0.742f));
    }

    private static RoundedPolygon getPuffyDiamond() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.87f, 0.13f), new CornerRounding(0.146f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.818f, 0.357f)));
        points.add(new VertexAndRounding(new PointF(1.0f, 0.332f), new CornerRounding(0.853f, 0.0f)));
        return customPolygon(points, 4, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getPixelCircle() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.704f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.704f, 0.065f)));
        points.add(new VertexAndRounding(new PointF(0.843f, 0.065f)));
        points.add(new VertexAndRounding(new PointF(0.843f, 0.148f)));
        points.add(new VertexAndRounding(new PointF(0.926f, 0.148f)));
        points.add(new VertexAndRounding(new PointF(0.926f, 0.296f)));
        points.add(new VertexAndRounding(new PointF(1.0f, 0.296f)));
        return customPolygon(points, 2, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getPixelTriangle() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.11f, 0.5f)));
        points.add(new VertexAndRounding(new PointF(0.113f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.287f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.287f, 0.087f)));
        points.add(new VertexAndRounding(new PointF(0.421f, 0.087f)));
        points.add(new VertexAndRounding(new PointF(0.421f, 0.17f)));
        points.add(new VertexAndRounding(new PointF(0.56f, 0.17f)));
        points.add(new VertexAndRounding(new PointF(0.56f, 0.265f)));
        points.add(new VertexAndRounding(new PointF(0.674f, 0.265f)));
        points.add(new VertexAndRounding(new PointF(0.675f, 0.344f)));
        points.add(new VertexAndRounding(new PointF(0.789f, 0.344f)));
        points.add(new VertexAndRounding(new PointF(0.789f, 0.439f)));
        points.add(new VertexAndRounding(new PointF(0.888f, 0.439f)));
        return customPolygon(points, 1, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getBun() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.796f, 0.5f)));
        points.add(new VertexAndRounding(new PointF(0.853f, 0.518f), CORNER_ROUND_100));
        points.add(new VertexAndRounding(new PointF(0.992f, 0.631f), CORNER_ROUND_100));
        points.add(new VertexAndRounding(new PointF(0.968f, 1.0f), CORNER_ROUND_100));
        return customPolygon(points, 2, 0.5f, 0.5f, true);
    }

    private static RoundedPolygon getHeart() {
        List<VertexAndRounding> points = new ArrayList<>();
        points.add(new VertexAndRounding(new PointF(0.5f, 0.268f), new CornerRounding(0.016f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(0.792f, -0.066f), new CornerRounding(0.958f, 0.0f)));
        points.add(new VertexAndRounding(new PointF(1.064f, 0.276f), CORNER_ROUND_100));
        points.add(new VertexAndRounding(new PointF(0.501f, 0.946f), new CornerRounding(0.129f, 0.0f)));
        return customPolygon(points, 1, 0.5f, 0.5f, true);
    }

    private static void repeatAroundCenter(List<VertexAndRounding> template, List<VertexAndRounding> outList, int repeatCount, float centerX, float centerY, boolean mirroring) {
        float f;
        float f2;
        List<VertexAndRounding> list = template;
        outList.clear();
        toRadial(list, centerX, centerY);
        float spanPerRepeat = (float) (6.283185307179586d / ((double) repeatCount));
        if (mirroring) {
            int mirroredRepeatCount = repeatCount * 2;
            float f3 = 2.0f;
            float spanPerRepeat2 = spanPerRepeat / 2.0f;
            int i = 0;
            while (i < mirroredRepeatCount) {
                int j = 0;
                while (j < list.size()) {
                    boolean reverse = i % 2 != 0;
                    int indexInTemplate = reverse ? (list.size() - 1) - j : j;
                    VertexAndRounding templatePoint = list.get(indexInTemplate);
                    if (indexInTemplate > 0 || !reverse) {
                        float f4 = i * spanPerRepeat2;
                        if (reverse) {
                            f = f3;
                            f2 = (spanPerRepeat2 - templatePoint.vertex.x) + (list.get(0).vertex.x * f);
                        } else {
                            f = f3;
                            f2 = templatePoint.vertex.x;
                        }
                        float angle = f4 + f2;
                        PointF outVertex = new PointF(angle, templatePoint.vertex.y);
                        outList.add(new VertexAndRounding(outVertex, templatePoint.rounding));
                    } else {
                        f = f3;
                    }
                    j++;
                    list = template;
                    f3 = f;
                }
                i++;
                list = template;
            }
        } else {
            for (int i2 = 0; i2 < repeatCount; i2++) {
                for (VertexAndRounding templatePoint2 : template) {
                    float angle2 = (i2 * spanPerRepeat) + templatePoint2.vertex.x;
                    PointF outVertex2 = new PointF(angle2, templatePoint2.vertex.y);
                    outList.add(new VertexAndRounding(outVertex2, templatePoint2.rounding));
                }
            }
        }
        toCartesian(outList, centerX, centerY);
    }

    private static RoundedPolygon customPolygon(List<VertexAndRounding> template, int repeat, float centerX, float centerY, boolean mirroring) {
        List<VertexAndRounding> vertexAndRoundings = new ArrayList<>();
        repeatAroundCenter(template, vertexAndRoundings, repeat, centerX, centerY, mirroring);
        float[] verticesXy = toVerticesXyArray(vertexAndRoundings);
        List<CornerRounding> roundings = toRoundingsList(vertexAndRoundings);
        return RoundedPolygonKt.RoundedPolygon(verticesXy, CornerRounding.Unrounded, roundings, centerX, centerY);
    }

    private MaterialShapes() {
    }

    public static ShapeDrawable createShapeDrawable(RoundedPolygon shape) {
        PathShape pathShape = new PathShape(Shapes_androidKt.toPath(shape), 1.0f, 1.0f);
        return new ShapeDrawable(pathShape);
    }

    public static RoundedPolygon normalize(RoundedPolygon shape, boolean radial, RectF dstBounds) {
        float[] srcBoundsArray = new float[4];
        if (radial) {
            shape.calculateMaxBounds(srcBoundsArray);
        } else {
            shape.calculateBounds(srcBoundsArray);
        }
        RectF srcBounds = new RectF(srcBoundsArray[0], srcBoundsArray[1], srcBoundsArray[2], srcBoundsArray[3]);
        float scale = Math.min(dstBounds.width() / srcBounds.width(), dstBounds.height() / srcBounds.height());
        Matrix transform = createScaleMatrix(scale, scale);
        transform.preTranslate(-srcBounds.centerX(), -srcBounds.centerY());
        transform.postTranslate(dstBounds.centerX(), dstBounds.centerY());
        return Shapes_androidKt.transformed(shape, transform);
    }

    public static RoundedPolygon normalize(RoundedPolygon shape, boolean radial) {
        return normalize(shape, radial, new RectF(0.0f, 0.0f, 1.0f, 1.0f));
    }

    static Matrix createScaleMatrix(float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        return matrix;
    }

    static Matrix createRotationMatrix(float degrees) {
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        return matrix;
    }

    static Matrix createSkewMatrix(float kx, float ky) {
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky);
        return matrix;
    }

    private static void toRadial(List<VertexAndRounding> vertexAndRoundings, float centerX, float centerY) {
        for (VertexAndRounding vertexAndRounding : vertexAndRoundings) {
            vertexAndRounding.toRadial(centerX, centerY);
        }
    }

    private static void toCartesian(List<VertexAndRounding> vertexAndRoundings, float centerX, float centerY) {
        for (VertexAndRounding vertexAndRounding : vertexAndRoundings) {
            vertexAndRounding.toCartesian(centerX, centerY);
        }
    }

    private static float[] toVerticesXyArray(List<VertexAndRounding> vertexAndRoundings) {
        float[] verticesXy = new float[vertexAndRoundings.size() * 2];
        for (int i = 0; i < vertexAndRoundings.size(); i++) {
            verticesXy[i * 2] = vertexAndRoundings.get(i).vertex.x;
            verticesXy[(i * 2) + 1] = vertexAndRoundings.get(i).vertex.y;
        }
        return verticesXy;
    }

    private static List<CornerRounding> toRoundingsList(List<VertexAndRounding> vertexAndRoundings) {
        List<CornerRounding> roundings = new ArrayList<>();
        for (int i = 0; i < vertexAndRoundings.size(); i++) {
            roundings.add(vertexAndRoundings.get(i).rounding);
        }
        return roundings;
    }

    static class VertexAndRounding {
        private CornerRounding rounding;
        private PointF vertex;

        private VertexAndRounding(PointF vertex) {
            this(vertex, CornerRounding.Unrounded);
        }

        private VertexAndRounding(PointF vertex, CornerRounding rounding) {
            this.vertex = vertex;
            this.rounding = rounding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toRadial(float centerX, float centerY) {
            this.vertex.offset(-centerX, -centerY);
            float angle = (float) Math.atan2(this.vertex.y, this.vertex.x);
            float distance = (float) Math.hypot(this.vertex.x, this.vertex.y);
            this.vertex.x = angle;
            this.vertex.y = distance;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toCartesian(float centerX, float centerY) {
            float x = (float) ((((double) this.vertex.y) * Math.cos(this.vertex.x)) + ((double) centerX));
            float y = (float) ((((double) this.vertex.y) * Math.sin(this.vertex.x)) + ((double) centerY));
            this.vertex.x = x;
            this.vertex.y = y;
        }
    }
}
