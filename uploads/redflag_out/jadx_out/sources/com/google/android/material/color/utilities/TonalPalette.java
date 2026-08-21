package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class TonalPalette {
    Map<Integer, Integer> cache = new HashMap();
    double chroma;
    double hue;
    Hct keyColor;

    public static TonalPalette fromInt(int argb) {
        return fromHct(Hct.fromInt(argb));
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.getHue(), hct.getChroma(), hct);
    }

    public static TonalPalette fromHueAndChroma(double hue, double chroma) {
        Hct keyColor = new KeyColor(hue, chroma).create();
        return new TonalPalette(hue, chroma, keyColor);
    }

    private TonalPalette(double hue, double chroma, Hct keyColor) {
        this.hue = hue;
        this.chroma = chroma;
        this.keyColor = keyColor;
    }

    public int tone(int tone) {
        Integer color = this.cache.get(Integer.valueOf(tone));
        if (color == null) {
            color = Integer.valueOf(Hct.from(this.hue, this.chroma, tone).toInt());
            this.cache.put(Integer.valueOf(tone), color);
        }
        return color.intValue();
    }

    public Hct getHct(double tone) {
        return Hct.from(this.hue, this.chroma, tone);
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getHue() {
        return this.hue;
    }

    public Hct getKeyColor() {
        return this.keyColor;
    }

    private static final class KeyColor {
        private static final double MAX_CHROMA_VALUE = 200.0d;
        private final Map<Integer, Double> chromaCache = new HashMap();
        private final double hue;
        private final double requestedChroma;

        public KeyColor(double hue, double requestedChroma) {
            this.hue = hue;
            this.requestedChroma = requestedChroma;
        }

        public Hct create() {
            int lowerTone = 0;
            int upperTone = 100;
            while (lowerTone < upperTone) {
                int midTone = (lowerTone + upperTone) / 2;
                boolean isAscending = maxChroma(midTone) < maxChroma(midTone + 1);
                boolean sufficientChroma = maxChroma(midTone) >= this.requestedChroma - 0.01d;
                if (sufficientChroma) {
                    if (Math.abs(lowerTone - 50) < Math.abs(upperTone - 50)) {
                        upperTone = midTone;
                    } else {
                        if (lowerTone == midTone) {
                            return Hct.from(this.hue, this.requestedChroma, lowerTone);
                        }
                        lowerTone = midTone;
                    }
                } else if (isAscending) {
                    lowerTone = midTone + 1;
                } else {
                    upperTone = midTone;
                }
            }
            return Hct.from(this.hue, this.requestedChroma, lowerTone);
        }

        private double maxChroma(int tone) {
            Double newChroma;
            if (this.chromaCache.get(Integer.valueOf(tone)) == null && (newChroma = Double.valueOf(Hct.from(this.hue, MAX_CHROMA_VALUE, tone).getChroma())) != null) {
                this.chromaCache.put(Integer.valueOf(tone), newChroma);
            }
            return this.chromaCache.get(Integer.valueOf(tone)).doubleValue();
        }
    }
}
