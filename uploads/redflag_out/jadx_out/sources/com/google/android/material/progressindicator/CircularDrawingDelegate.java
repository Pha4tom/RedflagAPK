package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.Pair;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.math.MathUtils;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    private static final float QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH = 0.5522848f;
    private static final float ROUND_CAP_RAMP_DOWN_THRESHHOLD = 0.01f;
    private float adjustedRadius;
    private float adjustedWavelength;
    private final RectF arcBounds;
    private float cachedAmplitude;
    private float cachedRadius;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    private final Pair<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> endPoints;
    private float totalTrackLengthFraction;

    CircularDrawingDelegate(CircularProgressIndicatorSpec spec) {
        super(spec);
        this.arcBounds = new RectF();
        this.endPoints = new Pair<>(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredWidth() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredHeight() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void adjustCanvas(Canvas canvas, Rect bounds, float trackThicknessFraction, boolean isShowing, boolean isHiding) {
        float scaleX = bounds.width() / getPreferredWidth();
        float scaleY = bounds.height() / getPreferredHeight();
        float outerRadiusWithInset = (((CircularProgressIndicatorSpec) this.spec).indicatorSize / 2.0f) + ((CircularProgressIndicatorSpec) this.spec).indicatorInset;
        float scaledOuterRadiusWithInsetX = outerRadiusWithInset * scaleX;
        float scaledOuterRadiusWithInsetY = outerRadiusWithInset * scaleY;
        canvas.translate(bounds.left + scaledOuterRadiusWithInsetX, bounds.top + scaledOuterRadiusWithInsetY);
        canvas.rotate(-90.0f);
        canvas.scale(scaleX, scaleY);
        if (((CircularProgressIndicatorSpec) this.spec).indicatorDirection != 0) {
            canvas.scale(1.0f, -1.0f);
            if (Build.VERSION.SDK_INT == 29) {
                canvas.rotate(0.1f);
            }
        }
        canvas.clipRect(-outerRadiusWithInset, -outerRadiusWithInset, outerRadiusWithInset, outerRadiusWithInset);
        this.displayedTrackThickness = ((CircularProgressIndicatorSpec) this.spec).trackThickness * trackThicknessFraction;
        this.displayedCornerRadius = Math.min(((CircularProgressIndicatorSpec) this.spec).trackThickness / 2, ((CircularProgressIndicatorSpec) this.spec).getTrackCornerRadiusInPx()) * trackThicknessFraction;
        this.displayedAmplitude = ((CircularProgressIndicatorSpec) this.spec).waveAmplitude * trackThicknessFraction;
        this.adjustedRadius = (((CircularProgressIndicatorSpec) this.spec).indicatorSize - ((CircularProgressIndicatorSpec) this.spec).trackThickness) / 2.0f;
        if (isShowing || isHiding) {
            float deltaRadius = ((1.0f - trackThicknessFraction) * ((CircularProgressIndicatorSpec) this.spec).trackThickness) / 2.0f;
            if ((isShowing && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (isHiding && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
                this.adjustedRadius += deltaRadius;
            } else if ((isShowing && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 1) || (isHiding && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 2)) {
                this.adjustedRadius -= deltaRadius;
            }
        }
        if (isHiding && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = trackThicknessFraction;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int drawableAlpha) {
        int color = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, drawableAlpha);
        canvas.save();
        canvas.rotate(activeIndicator.rotationDegree);
        this.drawingDeterminateIndicator = activeIndicator.isDeterminate;
        drawArc(canvas, paint, activeIndicator.startFraction, activeIndicator.endFraction, color, activeIndicator.gapSize, activeIndicator.gapSize, activeIndicator.amplitudeFraction, activeIndicator.phaseFraction, true);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillTrack(Canvas canvas, Paint paint, float startFraction, float endFraction, int color, int drawableAlpha, int gapSize) {
        int color2 = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        drawArc(canvas, paint, startFraction, endFraction, color2, gapSize, gapSize, 0.0f, 0.0f, false);
    }

    private void drawArc(Canvas canvas, Paint paint, float startFraction, float endFraction, int paintColor, int startGapSize, int endGapSize, float amplitudeFraction, float phaseFraction, boolean shouldDrawActiveIndicator) {
        float f;
        float startFraction2;
        float f2;
        Paint paint2;
        CircularDrawingDelegate circularDrawingDelegate;
        Canvas canvas2;
        float blockWidth;
        float shrinkRatio;
        if (endFraction >= startFraction) {
            f = endFraction - startFraction;
        } else {
            f = (endFraction + 1.0f) - startFraction;
        }
        float arcFraction = f;
        float startFraction3 = startFraction % 1.0f;
        if (startFraction3 >= 0.0f) {
            startFraction2 = startFraction3;
        } else {
            startFraction2 = startFraction3 + 1.0f;
        }
        if (this.totalTrackLengthFraction >= 1.0f || startFraction2 + arcFraction <= 1.0f) {
            float startFraction4 = startFraction2;
            float displayedCornerRadiusInDegree = (float) Math.toDegrees(this.displayedCornerRadius / this.adjustedRadius);
            float arcFractionOverRoundCapThreshold = arcFraction - 0.99f;
            if (arcFractionOverRoundCapThreshold >= 0.0f) {
                float increasedArcFraction = ((arcFractionOverRoundCapThreshold * displayedCornerRadiusInDegree) / 180.0f) / ROUND_CAP_RAMP_DOWN_THRESHHOLD;
                arcFraction += increasedArcFraction;
                if (!shouldDrawActiveIndicator) {
                    startFraction4 -= increasedArcFraction / 2.0f;
                }
            }
            float startFraction5 = MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, startFraction4);
            float arcFraction2 = MathUtils.lerp(0.0f, this.totalTrackLengthFraction, arcFraction);
            float startGapSizeInDegrees = (float) Math.toDegrees(startGapSize / this.adjustedRadius);
            float endGapSizeInDegrees = (float) Math.toDegrees(endGapSize / this.adjustedRadius);
            float arcDegree = ((arcFraction2 * 360.0f) - startGapSizeInDegrees) - endGapSizeInDegrees;
            float startDegree = (startFraction5 * 360.0f) + startGapSizeInDegrees;
            if (arcDegree <= 0.0f) {
                return;
            }
            boolean shouldDrawWavyPath = ((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && shouldDrawActiveIndicator && amplitudeFraction > 0.0f;
            paint.setAntiAlias(true);
            paint.setColor(paintColor);
            paint.setStrokeWidth(this.displayedTrackThickness);
            float blockWidth2 = this.displayedCornerRadius * 2.0f;
            if (arcDegree >= displayedCornerRadiusInDegree * 2.0f) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(((CircularProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                float startDegreeWithoutCorners = startDegree + displayedCornerRadiusInDegree;
                float arcDegreeWithoutCorners = arcDegree - (displayedCornerRadiusInDegree * 2.0f);
                ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
                ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
                if (shouldDrawWavyPath) {
                    f2 = 0.0f;
                    paint2 = paint;
                    circularDrawingDelegate = this;
                    circularDrawingDelegate.calculateDisplayedPath(this.activePathMeasure, this.displayedActivePath, this.endPoints, startDegreeWithoutCorners / 360.0f, arcDegreeWithoutCorners / 360.0f, amplitudeFraction, phaseFraction);
                    canvas2 = canvas;
                    canvas2.drawPath(circularDrawingDelegate.displayedActivePath, paint2);
                } else {
                    ((DrawingDelegate.PathPoint) this.endPoints.first).rotate(startDegreeWithoutCorners + 90.0f);
                    ((DrawingDelegate.PathPoint) this.endPoints.first).moveAcross(-this.adjustedRadius);
                    ((DrawingDelegate.PathPoint) this.endPoints.second).rotate(startDegreeWithoutCorners + arcDegreeWithoutCorners + 90.0f);
                    ((DrawingDelegate.PathPoint) this.endPoints.second).moveAcross(-this.adjustedRadius);
                    f2 = 0.0f;
                    this.arcBounds.set(-this.adjustedRadius, -this.adjustedRadius, this.adjustedRadius, this.adjustedRadius);
                    canvas.drawArc(this.arcBounds, startDegreeWithoutCorners, arcDegreeWithoutCorners, false, paint);
                    paint2 = paint;
                    canvas2 = canvas;
                    circularDrawingDelegate = this;
                }
                if (!((CircularProgressIndicatorSpec) circularDrawingDelegate.spec).useStrokeCap() && circularDrawingDelegate.displayedCornerRadius > f2) {
                    paint2.setStyle(Paint.Style.FILL);
                    circularDrawingDelegate.drawRoundedBlock(canvas2, paint2, (DrawingDelegate.PathPoint) circularDrawingDelegate.endPoints.first, blockWidth2, circularDrawingDelegate.displayedTrackThickness);
                    circularDrawingDelegate.drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) circularDrawingDelegate.endPoints.second, blockWidth2, circularDrawingDelegate.displayedTrackThickness);
                    return;
                }
                return;
            }
            float shrinkRatio2 = arcDegree / (displayedCornerRadiusInDegree * 2.0f);
            float centerDegree = startDegree + (displayedCornerRadiusInDegree * shrinkRatio2);
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center = new DrawingDelegate.PathPoint();
            if (shouldDrawWavyPath) {
                float centerDistance = ((centerDegree / 360.0f) * this.activePathMeasure.getLength()) / 2.0f;
                float amplitude = this.displayedAmplitude * amplitudeFraction;
                blockWidth = blockWidth2;
                if (this.adjustedRadius != this.cachedRadius || amplitude != this.cachedAmplitude) {
                    this.cachedAmplitude = amplitude;
                    this.cachedRadius = this.adjustedRadius;
                    invalidateCachedPaths();
                }
                shrinkRatio = shrinkRatio2;
                this.activePathMeasure.getPosTan(centerDistance, center.posVec, center.tanVec);
            } else {
                center.rotate(centerDegree + 90.0f);
                center.moveAcross(-this.adjustedRadius);
                blockWidth = blockWidth2;
                shrinkRatio = shrinkRatio2;
            }
            paint.setStyle(Paint.Style.FILL);
            drawRoundedBlock(canvas, paint, center, blockWidth, this.displayedTrackThickness, shrinkRatio);
            return;
        }
        drawArc(canvas, paint, startFraction2, 1.0f, paintColor, startGapSize, 0, amplitudeFraction, phaseFraction, shouldDrawActiveIndicator);
        drawArc(canvas, paint, 1.0f, startFraction2 + arcFraction, paintColor, 0, endGapSize, amplitudeFraction, phaseFraction, shouldDrawActiveIndicator);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void drawStopIndicator(Canvas canvas, Paint paint, int color, int drawableAlpha) {
    }

    private int getSize() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorSize + (((CircularProgressIndicatorSpec) this.spec).indicatorInset * 2);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center, float markWidth, float markHeight) {
        drawRoundedBlock(canvas, paint, center, markWidth, markHeight, 1.0f);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center, float markWidth, float markHeight, float scale) {
        float markHeight2 = Math.min(markHeight, this.displayedTrackThickness);
        float markCornerSize = Math.min(markWidth / 2.0f, (this.displayedCornerRadius * markHeight2) / this.displayedTrackThickness);
        RectF roundedBlock = new RectF((-markWidth) / 2.0f, (-markHeight2) / 2.0f, markWidth / 2.0f, markHeight2 / 2.0f);
        canvas.save();
        canvas.translate(center.posVec[0], center.posVec[1]);
        canvas.rotate(vectorToCanvasRotation(center.tanVec));
        canvas.scale(scale, scale);
        canvas.drawRoundRect(roundedBlock, markCornerSize, markCornerSize, paint);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        this.cachedActivePath.moveTo(1.0f, 0.0f);
        for (int i = 0; i < 2; i++) {
            this.cachedActivePath.cubicTo(1.0f, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, 1.0f, 0.0f, 1.0f);
            this.cachedActivePath.cubicTo(-0.5522848f, 1.0f, -1.0f, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, -1.0f, 0.0f);
            this.cachedActivePath.cubicTo(-1.0f, -0.5522848f, -0.5522848f, -1.0f, 0.0f, -1.0f);
            this.cachedActivePath.cubicTo(QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, -1.0f, 1.0f, -0.5522848f, 1.0f, 0.0f);
        }
        this.transform.reset();
        this.transform.setScale(this.adjustedRadius, this.adjustedRadius);
        this.cachedActivePath.transform(this.transform);
        if (((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            this.activePathMeasure.setPath(this.cachedActivePath, false);
            createWavyPath(this.activePathMeasure, this.cachedActivePath, this.cachedAmplitude);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void createWavyPath(PathMeasure basePathMeasure, Path outPath, float amplitude) {
        outPath.rewind();
        float basePathLength = basePathMeasure.getLength();
        int wavelength = this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        int cycleCountInPath = Math.max(3, (int) ((basePathLength / wavelength) / 2.0f)) * 2;
        this.adjustedWavelength = basePathLength / cycleCountInPath;
        List<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> anchors = new ArrayList<>();
        for (int i = 0; i < cycleCountInPath; i++) {
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startAnchor = new DrawingDelegate.PathPoint();
            basePathMeasure.getPosTan(this.adjustedWavelength * i, startAnchor.posVec, startAnchor.tanVec);
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint midAnchor = new DrawingDelegate.PathPoint();
            basePathMeasure.getPosTan((this.adjustedWavelength * i) + (this.adjustedWavelength / 2.0f), midAnchor.posVec, midAnchor.tanVec);
            anchors.add(startAnchor);
            midAnchor.moveAcross(amplitude * 2.0f);
            anchors.add(midAnchor);
        }
        anchors.add(anchors.get(0));
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startAnchor2 = anchors.get(0);
        outPath.moveTo(startAnchor2.posVec[0], startAnchor2.posVec[1]);
        for (int i2 = 1; i2 < anchors.size(); i2++) {
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint endAnchor = anchors.get(i2);
            appendCubicPerHalfCycle(outPath, startAnchor2, endAnchor);
            startAnchor2 = endAnchor;
        }
    }

    private void appendCubicPerHalfCycle(Path outPath, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint anchor1, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint anchor2) {
        float controlLength = (this.adjustedWavelength / 2.0f) * 0.48f;
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint control1 = new DrawingDelegate.PathPoint(this, anchor1);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint control2 = new DrawingDelegate.PathPoint(this, anchor2);
        control1.moveAlong(controlLength);
        control2.moveAlong(-controlLength);
        outPath.cubicTo(control1.posVec[0], control1.posVec[1], control2.posVec[0], control2.posVec[1], anchor2.posVec[0], anchor2.posVec[1]);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path displayedPath, Pair<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> endPoints, float start, float span, float amplitudeFraction, float phaseFraction) {
        float start2;
        float amplitude = this.displayedAmplitude * amplitudeFraction;
        int wavelength = this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (this.adjustedRadius != this.cachedRadius || (pathMeasure == this.activePathMeasure && (amplitude != this.cachedAmplitude || wavelength != this.cachedWavelength))) {
            this.cachedAmplitude = amplitude;
            this.cachedWavelength = wavelength;
            this.cachedRadius = this.adjustedRadius;
            invalidateCachedPaths();
        }
        displayedPath.rewind();
        float span2 = androidx.core.math.MathUtils.clamp(span, 0.0f, 1.0f);
        float resultRotation = 0.0f;
        if (!((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            start2 = start;
        } else {
            float cycleCount = (float) ((((double) this.adjustedRadius) * 6.283185307179586d) / ((double) this.adjustedWavelength));
            float phaseFractionInOneCycle = phaseFraction / cycleCount;
            start2 = start + phaseFractionInOneCycle;
            resultRotation = 0.0f - (360.0f * phaseFractionInOneCycle);
        }
        float start3 = start2 % 1.0f;
        float startDistance = (pathMeasure.getLength() * start3) / 2.0f;
        float endDistance = ((start3 + span2) * pathMeasure.getLength()) / 2.0f;
        pathMeasure.getSegment(startDistance, endDistance, displayedPath, true);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startPoint = (DrawingDelegate.PathPoint) endPoints.first;
        startPoint.reset();
        pathMeasure.getPosTan(startDistance, startPoint.posVec, startPoint.tanVec);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint endPoint = (DrawingDelegate.PathPoint) endPoints.second;
        endPoint.reset();
        pathMeasure.getPosTan(endDistance, endPoint.posVec, endPoint.tanVec);
        this.transform.reset();
        this.transform.setRotate(resultRotation);
        startPoint.rotate(resultRotation);
        endPoint.rotate(resultRotation);
        displayedPath.transform(this.transform);
    }
}
