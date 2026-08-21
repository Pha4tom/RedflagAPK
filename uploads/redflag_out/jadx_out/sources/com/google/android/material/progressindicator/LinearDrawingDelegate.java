package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import androidx.core.math.MathUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.DrawingDelegate;

/* JADX INFO: loaded from: classes.dex */
final class LinearDrawingDelegate extends DrawingDelegate<LinearProgressIndicatorSpec> {
    private float adjustedWavelength;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedInnerCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    Pair<DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint> endPoints;
    private float totalTrackLengthFraction;
    private float trackLength;

    LinearDrawingDelegate(LinearProgressIndicatorSpec spec) {
        super(spec);
        this.trackLength = 300.0f;
        this.endPoints = new Pair<>(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredWidth() {
        return -1;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredHeight() {
        return ((LinearProgressIndicatorSpec) this.spec).trackThickness + (((LinearProgressIndicatorSpec) this.spec).waveAmplitude * 2);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void adjustCanvas(Canvas canvas, Rect bounds, float trackThicknessFraction, boolean isShowing, boolean isHiding) {
        if (this.trackLength != bounds.width()) {
            this.trackLength = bounds.width();
            invalidateCachedPaths();
        }
        float trackSize = getPreferredHeight();
        canvas.translate(bounds.left + (bounds.width() / 2.0f), bounds.top + (bounds.height() / 2.0f) + Math.max(0.0f, (bounds.height() - trackSize) / 2.0f));
        if (((LinearProgressIndicatorSpec) this.spec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        float halfTrackLength = this.trackLength / 2.0f;
        float halfTrackSize = trackSize / 2.0f;
        canvas.clipRect(-halfTrackLength, -halfTrackSize, halfTrackLength, halfTrackSize);
        this.displayedTrackThickness = ((LinearProgressIndicatorSpec) this.spec).trackThickness * trackThicknessFraction;
        this.displayedCornerRadius = Math.min(((LinearProgressIndicatorSpec) this.spec).trackThickness / 2, ((LinearProgressIndicatorSpec) this.spec).getTrackCornerRadiusInPx()) * trackThicknessFraction;
        this.displayedAmplitude = ((LinearProgressIndicatorSpec) this.spec).waveAmplitude * trackThicknessFraction;
        this.displayedInnerCornerRadius = Math.min(((LinearProgressIndicatorSpec) this.spec).trackThickness / 2.0f, ((LinearProgressIndicatorSpec) this.spec).getTrackInnerCornerRadiusInPx()) * trackThicknessFraction;
        if (isShowing || isHiding) {
            if ((isShowing && ((LinearProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (isHiding && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
                canvas.scale(1.0f, -1.0f);
            }
            if (isShowing || (isHiding && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3)) {
                canvas.translate(0.0f, (((LinearProgressIndicatorSpec) this.spec).trackThickness * (1.0f - trackThicknessFraction)) / 2.0f);
            }
        }
        if (isHiding && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = trackThicknessFraction;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int drawableAlpha) {
        int color = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, drawableAlpha);
        this.drawingDeterminateIndicator = activeIndicator.isDeterminate;
        drawLine(canvas, paint, activeIndicator.startFraction, activeIndicator.endFraction, color, activeIndicator.gapSize, activeIndicator.gapSize, activeIndicator.amplitudeFraction, activeIndicator.phaseFraction, true);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillTrack(Canvas canvas, Paint paint, float startFraction, float endFraction, int color, int drawableAlpha, int gapSize) {
        int color2 = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        drawLine(canvas, paint, startFraction, endFraction, color2, gapSize, gapSize, 0.0f, 0.0f, false);
    }

    private void drawLine(Canvas canvas, Paint paint, float startFraction, float endFraction, int paintColor, int startGapSize, int endGapSize, float amplitudeFraction, float phaseFraction, boolean drawingActiveIndicator) {
        float cornerRampDownThreshold;
        float endCornerRadius;
        LinearDrawingDelegate linearDrawingDelegate;
        float f;
        Paint paint2;
        Canvas canvas2;
        float startFraction2 = MathUtils.clamp(startFraction, 0.0f, 1.0f);
        float endFraction2 = MathUtils.clamp(endFraction, 0.0f, 1.0f);
        float startFraction3 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, startFraction2);
        float startFraction4 = this.totalTrackLengthFraction;
        float endFraction3 = com.google.android.material.math.MathUtils.lerp(1.0f - startFraction4, 1.0f, endFraction2);
        int startGapSize2 = (int) ((startGapSize * MathUtils.clamp(startFraction3, 0.0f, 0.01f)) / 0.01f);
        int endGapSize2 = (int) ((endGapSize * (1.0f - MathUtils.clamp(endFraction3, 0.99f, 1.0f))) / 0.01f);
        int startPx = (int) ((this.trackLength * startFraction3) + startGapSize2);
        int endPx = (int) ((this.trackLength * endFraction3) - endGapSize2);
        float startCornerRadius = this.displayedCornerRadius;
        float endCornerRadius2 = this.displayedCornerRadius;
        if (this.displayedCornerRadius != this.displayedInnerCornerRadius) {
            float cornerRampDownThreshold2 = Math.max(this.displayedCornerRadius, this.displayedInnerCornerRadius) / this.trackLength;
            float startCornerRadius2 = com.google.android.material.math.MathUtils.lerp(this.displayedCornerRadius, this.displayedInnerCornerRadius, MathUtils.clamp(startPx / this.trackLength, 0.0f, cornerRampDownThreshold2) / cornerRampDownThreshold2);
            float endCornerRadius3 = com.google.android.material.math.MathUtils.lerp(this.displayedCornerRadius, this.displayedInnerCornerRadius, MathUtils.clamp((this.trackLength - endPx) / this.trackLength, 0.0f, cornerRampDownThreshold2) / cornerRampDownThreshold2);
            cornerRampDownThreshold = startCornerRadius2;
            endCornerRadius = endCornerRadius3;
        } else {
            cornerRampDownThreshold = startCornerRadius;
            endCornerRadius = endCornerRadius2;
        }
        float startCornerRadius3 = this.trackLength;
        float originX = (-startCornerRadius3) / 2.0f;
        boolean drawWavyPath = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && drawingActiveIndicator && amplitudeFraction > 0.0f;
        if (startPx <= endPx) {
            float startBlockCenterX = startPx + cornerRampDownThreshold;
            float endBlockCenterX = endPx - endCornerRadius;
            float startBlockWidth = cornerRampDownThreshold * 2.0f;
            float endBlockWidth = 2.0f * endCornerRadius;
            paint.setColor(paintColor);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.displayedTrackThickness);
            ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.first).translate(startBlockCenterX + originX, 0.0f);
            ((DrawingDelegate.PathPoint) this.endPoints.second).translate(endBlockCenterX + originX, 0.0f);
            if (startPx == 0 && endBlockCenterX + endCornerRadius < startBlockCenterX + cornerRampDownThreshold) {
                drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.first, startBlockWidth, this.displayedTrackThickness, cornerRampDownThreshold, (DrawingDelegate.PathPoint) this.endPoints.second, endBlockWidth, this.displayedTrackThickness, endCornerRadius, true);
                return;
            }
            float endCornerRadius4 = endCornerRadius;
            if (startBlockCenterX - cornerRampDownThreshold > endBlockCenterX - endCornerRadius4) {
                float endCornerRadius5 = this.displayedTrackThickness;
                drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.second, endBlockWidth, endCornerRadius5, endCornerRadius4, (DrawingDelegate.PathPoint) this.endPoints.first, startBlockWidth, this.displayedTrackThickness, cornerRampDownThreshold, false);
                return;
            }
            float startCornerRadius4 = cornerRampDownThreshold;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(((LinearProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
            if (drawWavyPath) {
                linearDrawingDelegate = this;
                f = 0.0f;
                paint2 = paint;
                linearDrawingDelegate.calculateDisplayedPath(this.activePathMeasure, this.displayedActivePath, this.endPoints, startBlockCenterX / this.trackLength, endBlockCenterX / this.trackLength, amplitudeFraction, phaseFraction);
                canvas2 = canvas;
                canvas2.drawPath(linearDrawingDelegate.displayedActivePath, paint2);
            } else {
                canvas.drawLine(((DrawingDelegate.PathPoint) this.endPoints.first).posVec[0], ((DrawingDelegate.PathPoint) this.endPoints.first).posVec[1], ((DrawingDelegate.PathPoint) this.endPoints.second).posVec[0], ((DrawingDelegate.PathPoint) this.endPoints.second).posVec[1], paint);
                canvas2 = canvas;
                linearDrawingDelegate = this;
                f = 0.0f;
                paint2 = paint;
            }
            if (((LinearProgressIndicatorSpec) linearDrawingDelegate.spec).useStrokeCap()) {
                return;
            }
            if (startBlockCenterX > f && startCornerRadius4 > f) {
                linearDrawingDelegate.drawRoundedBlock(canvas2, paint2, (DrawingDelegate.PathPoint) linearDrawingDelegate.endPoints.first, startBlockWidth, linearDrawingDelegate.displayedTrackThickness, startCornerRadius4);
            }
            if (endBlockCenterX < linearDrawingDelegate.trackLength && endCornerRadius4 > f) {
                linearDrawingDelegate.drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) linearDrawingDelegate.endPoints.second, endBlockWidth, linearDrawingDelegate.displayedTrackThickness, endCornerRadius4);
            }
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void drawStopIndicator(Canvas canvas, Paint paint, int color, int drawableAlpha) {
        float stopIndicatorCenterX;
        int paintColor = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize > 0 && paintColor != 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(paintColor);
            if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding != null) {
                stopIndicatorCenterX = ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding.floatValue() + (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize / 2.0f);
            } else {
                stopIndicatorCenterX = this.displayedTrackThickness / 2.0f;
            }
            drawRoundedBlock(canvas, paint, new DrawingDelegate.PathPoint(new float[]{(this.trackLength / 2.0f) - stopIndicatorCenterX, 0.0f}, new float[]{1.0f, 0.0f}), ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize, ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize, (this.displayedCornerRadius * ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize) / this.displayedTrackThickness);
        }
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint drawCenter, float drawWidth, float drawHeight, float drawCornerSize) {
        drawRoundedBlock(canvas, paint, drawCenter, drawWidth, drawHeight, drawCornerSize, null, 0.0f, 0.0f, 0.0f, false);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint drawCenter, float drawWidth, float drawHeight, float drawCornerSize, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint clipCenter, float clipWidth, float clipHeight, float clipCornerSize, boolean clipRight) {
        float f;
        char c;
        char c2;
        float clipWidth2;
        float clipWidth3;
        float drawHeight2 = Math.min(drawHeight, this.displayedTrackThickness);
        RectF drawRect = new RectF((-drawWidth) / 2.0f, (-drawHeight2) / 2.0f, drawWidth / 2.0f, drawHeight2 / 2.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        if (clipCenter == null) {
            canvas.translate(drawCenter.posVec[0], drawCenter.posVec[1]);
            canvas.rotate(vectorToCanvasRotation(drawCenter.tanVec));
            canvas.drawRoundRect(drawRect, drawCornerSize, drawCornerSize, paint);
        } else {
            float clipHeight2 = Math.min(clipHeight, this.displayedTrackThickness);
            float clipCornerSize2 = Math.min(clipWidth / 2.0f, (clipCornerSize * clipHeight2) / this.displayedTrackThickness);
            RectF patchRect = new RectF();
            if (clipRight) {
                c2 = 1;
                f = 2.0f;
                float leftEdgeDiff = (clipCenter.posVec[0] - clipCornerSize2) - (drawCenter.posVec[0] - drawCornerSize);
                if (leftEdgeDiff > 0.0f) {
                    clipCenter.translate((-leftEdgeDiff) / 2.0f, 0.0f);
                    clipWidth3 = clipWidth + leftEdgeDiff;
                } else {
                    clipWidth3 = clipWidth;
                }
                c = 0;
                patchRect.set(0.0f, (-drawHeight2) / 2.0f, drawWidth / 2.0f, drawHeight2 / 2.0f);
                clipWidth2 = clipWidth3;
            } else {
                f = 2.0f;
                c = 0;
                c2 = 1;
                float rightEdgeDiff = (clipCenter.posVec[0] + clipCornerSize2) - (drawCenter.posVec[0] + drawCornerSize);
                if (rightEdgeDiff < 0.0f) {
                    clipCenter.translate((-rightEdgeDiff) / 2.0f, 0.0f);
                    clipWidth2 = clipWidth - rightEdgeDiff;
                } else {
                    clipWidth2 = clipWidth;
                }
                patchRect.set((-drawWidth) / 2.0f, (-drawHeight2) / 2.0f, 0.0f, drawHeight2 / 2.0f);
            }
            RectF clipRect = new RectF((-clipWidth2) / f, (-clipHeight2) / f, clipWidth2 / f, clipHeight2 / f);
            canvas.translate(clipCenter.posVec[c], clipCenter.posVec[c2]);
            canvas.rotate(vectorToCanvasRotation(clipCenter.tanVec));
            Path clipPath = new Path();
            clipPath.addRoundRect(clipRect, clipCornerSize2, clipCornerSize2, Path.Direction.CCW);
            canvas.clipPath(clipPath);
            canvas.rotate(-vectorToCanvasRotation(clipCenter.tanVec));
            canvas.translate(-clipCenter.posVec[c], -clipCenter.posVec[c2]);
            canvas.translate(drawCenter.posVec[c], drawCenter.posVec[c2]);
            canvas.rotate(vectorToCanvasRotation(drawCenter.tanVec));
            canvas.drawRect(patchRect, paint);
            canvas.drawRoundRect(drawRect, drawCornerSize, drawCornerSize, paint);
        }
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        if (!((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            this.cachedActivePath.lineTo(this.trackLength, 0.0f);
        } else {
            int wavelength = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
            int cycleCount = (int) (this.trackLength / wavelength);
            this.adjustedWavelength = this.trackLength / cycleCount;
            for (int i = 0; i <= cycleCount; i++) {
                this.cachedActivePath.cubicTo((i * 2) + 0.48f, 0.0f, ((i * 2) + 1) - 0.48f, 1.0f, (i * 2) + 1, 1.0f);
                this.cachedActivePath.cubicTo((i * 2) + 1 + 0.48f, 1.0f, ((i * 2) + 2) - 0.48f, 0.0f, (i * 2) + 2, 0.0f);
            }
            this.transform.reset();
            this.transform.setScale(this.adjustedWavelength / 2.0f, -2.0f);
            this.transform.postTranslate(0.0f, 1.0f);
            this.cachedActivePath.transform(this.transform);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path displayedPath, Pair<DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint> endPoints, float start, float end, float amplitudeFraction, float phaseFraction) {
        float start2;
        float end2;
        int wavelength = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (pathMeasure == this.activePathMeasure && wavelength != this.cachedWavelength) {
            this.cachedWavelength = wavelength;
            invalidateCachedPaths();
        }
        displayedPath.rewind();
        float resultTranslationX = (-this.trackLength) / 2.0f;
        boolean hasWavyEffect = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator);
        if (hasWavyEffect) {
            float cycleCount = this.trackLength / this.adjustedWavelength;
            float phaseFractionInPath = phaseFraction / cycleCount;
            float ratio = cycleCount / (cycleCount + 1.0f);
            start2 = (start + phaseFractionInPath) * ratio;
            end2 = (end + phaseFractionInPath) * ratio;
            resultTranslationX -= this.adjustedWavelength * phaseFraction;
        } else {
            start2 = start;
            end2 = end;
        }
        float startDistance = pathMeasure.getLength() * start2;
        float endDistance = pathMeasure.getLength() * end2;
        pathMeasure.getSegment(startDistance, endDistance, displayedPath, true);
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint startPoint = (DrawingDelegate.PathPoint) endPoints.first;
        startPoint.reset();
        pathMeasure.getPosTan(startDistance, startPoint.posVec, startPoint.tanVec);
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint endPoint = (DrawingDelegate.PathPoint) endPoints.second;
        endPoint.reset();
        pathMeasure.getPosTan(endDistance, endPoint.posVec, endPoint.tanVec);
        this.transform.reset();
        this.transform.setTranslate(resultTranslationX, 0.0f);
        startPoint.translate(resultTranslationX, 0.0f);
        endPoint.translate(resultTranslationX, 0.0f);
        if (hasWavyEffect) {
            float scaleY = this.displayedAmplitude * amplitudeFraction;
            this.transform.postScale(1.0f, scaleY);
            startPoint.scale(1.0f, scaleY);
            endPoint.scale(1.0f, scaleY);
        }
        displayedPath.transform(this.transform);
    }
}
