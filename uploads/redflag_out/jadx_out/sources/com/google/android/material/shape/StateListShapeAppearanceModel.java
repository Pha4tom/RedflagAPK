package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import com.google.android.material.R;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class StateListShapeAppearanceModel {
    public static final int CORNER_BOTTOM_LEFT = 4;
    public static final int CORNER_BOTTOM_RIGHT = 8;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    private static final int INITIAL_CAPACITY = 10;
    final StateListCornerSize bottomLeftCornerSizeOverride;
    final StateListCornerSize bottomRightCornerSizeOverride;
    final ShapeAppearanceModel defaultShape;
    final ShapeAppearanceModel[] shapeAppearanceModels;
    final int stateCount;
    final int[][] stateSpecs;
    final StateListCornerSize topLeftCornerSizeOverride;
    final StateListCornerSize topRightCornerSizeOverride;

    public static final class Builder {
        private StateListCornerSize bottomLeftCornerSizeOverride;
        private StateListCornerSize bottomRightCornerSizeOverride;
        private ShapeAppearanceModel defaultShape;
        private ShapeAppearanceModel[] shapeAppearanceModels;
        private int stateCount;
        private int[][] stateSpecs;
        private StateListCornerSize topLeftCornerSizeOverride;
        private StateListCornerSize topRightCornerSizeOverride;

        public Builder(StateListShapeAppearanceModel other) {
            this.stateCount = other.stateCount;
            this.defaultShape = other.defaultShape;
            this.stateSpecs = new int[other.stateSpecs.length][];
            this.shapeAppearanceModels = new ShapeAppearanceModel[other.shapeAppearanceModels.length];
            System.arraycopy(other.stateSpecs, 0, this.stateSpecs, 0, this.stateCount);
            System.arraycopy(other.shapeAppearanceModels, 0, this.shapeAppearanceModels, 0, this.stateCount);
            this.topLeftCornerSizeOverride = other.topLeftCornerSizeOverride;
            this.topRightCornerSizeOverride = other.topRightCornerSizeOverride;
            this.bottomLeftCornerSizeOverride = other.bottomLeftCornerSizeOverride;
            this.bottomRightCornerSizeOverride = other.bottomRightCornerSizeOverride;
        }

        public Builder(ShapeAppearanceModel shapeAppearanceModel) {
            initialize();
            addStateShapeAppearanceModel(StateSet.WILD_CARD, shapeAppearanceModel);
        }

        private Builder(Context context, int index) {
            int type;
            initialize();
            try {
                XmlResourceParser parser = context.getResources().getXml(index);
                try {
                    AttributeSet attrs = Xml.asAttributeSet(parser);
                    do {
                        type = parser.next();
                        if (type == 2) {
                            break;
                        }
                    } while (type != 1);
                    if (type != 2) {
                        throw new XmlPullParserException("No start tag found");
                    }
                    String name = parser.getName();
                    if (name.equals("selector")) {
                        StateListShapeAppearanceModel.loadShapeAppearanceModelsFromItems(this, context, parser, attrs, context.getTheme());
                    }
                    if (parser != null) {
                        parser.close();
                    }
                } catch (Throwable th) {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Resources.NotFoundException | IOException | XmlPullParserException e) {
                initialize();
            }
        }

        private void initialize() {
            this.defaultShape = new ShapeAppearanceModel();
            this.stateSpecs = new int[10][];
            this.shapeAppearanceModels = new ShapeAppearanceModel[10];
        }

        public Builder setCornerSizeOverride(StateListCornerSize cornerSizeOverride, int cornerPositionSet) {
            if (containsFlag(cornerPositionSet, 1)) {
                this.topLeftCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 2)) {
                this.topRightCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 4)) {
                this.bottomLeftCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 8)) {
                this.bottomRightCornerSizeOverride = cornerSizeOverride;
            }
            return this;
        }

        private boolean containsFlag(int flagSet, int flag) {
            return (flagSet | flag) == flagSet;
        }

        public Builder addStateShapeAppearanceModel(int[] stateSpec, ShapeAppearanceModel shapeAppearanceModel) {
            if (this.stateCount == 0 || stateSpec.length == 0) {
                this.defaultShape = shapeAppearanceModel;
            }
            if (this.stateCount >= this.stateSpecs.length) {
                growArray(this.stateCount, this.stateCount + 10);
            }
            this.stateSpecs[this.stateCount] = stateSpec;
            this.shapeAppearanceModels[this.stateCount] = shapeAppearanceModel;
            this.stateCount++;
            return this;
        }

        public Builder withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
            ShapeAppearanceModel[] newShapeAppearanceModels = new ShapeAppearanceModel[this.shapeAppearanceModels.length];
            for (int i = 0; i < this.stateCount; i++) {
                newShapeAppearanceModels[i] = this.shapeAppearanceModels[i].withTransformedCornerSizes(op);
            }
            this.shapeAppearanceModels = newShapeAppearanceModels;
            if (this.topLeftCornerSizeOverride != null) {
                this.topLeftCornerSizeOverride = this.topLeftCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.topRightCornerSizeOverride != null) {
                this.topRightCornerSizeOverride = this.topRightCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.bottomLeftCornerSizeOverride != null) {
                this.bottomLeftCornerSizeOverride = this.bottomLeftCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.bottomRightCornerSizeOverride != null) {
                this.bottomRightCornerSizeOverride = this.bottomRightCornerSizeOverride.withTransformedCornerSizes(op);
            }
            return this;
        }

        private void growArray(int oldSize, int newSize) {
            int[][] newStateSpecs = new int[newSize][];
            System.arraycopy(this.stateSpecs, 0, newStateSpecs, 0, oldSize);
            this.stateSpecs = newStateSpecs;
            ShapeAppearanceModel[] newShapeAppearanceModels = new ShapeAppearanceModel[newSize];
            System.arraycopy(this.shapeAppearanceModels, 0, newShapeAppearanceModels, 0, oldSize);
            this.shapeAppearanceModels = newShapeAppearanceModels;
        }

        public StateListShapeAppearanceModel build() {
            if (this.stateCount == 0) {
                return null;
            }
            return new StateListShapeAppearanceModel(this);
        }
    }

    public static StateListShapeAppearanceModel create(Context context, TypedArray attributes, int index) {
        int resourceId = attributes.getResourceId(index, 0);
        if (resourceId == 0) {
            return null;
        }
        String typeName = context.getResources().getResourceTypeName(resourceId);
        if (!Objects.equals(typeName, "xml")) {
            return null;
        }
        return new Builder(context, resourceId).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loadShapeAppearanceModelsFromItems(Builder builder, Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        Resources.Theme theme2 = theme;
        int i = 1;
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int type = parser.next();
            if (type != i) {
                int depth = parser.getDepth();
                if (depth >= innerDepth || type != 3) {
                    if (type != 2 || depth > innerDepth) {
                        theme2 = theme;
                        i = 1;
                    } else if (parser.getName().equals("item")) {
                        Resources res = context.getResources();
                        TypedArray a = theme2 == null ? res.obtainAttributes(attrs, R.styleable.MaterialShape) : theme2.obtainStyledAttributes(attrs, R.styleable.MaterialShape, 0, 0);
                        int shapeAppearanceId = a.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
                        int shapeAppearanceOverlayId = a.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
                        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context, shapeAppearanceId, shapeAppearanceOverlayId).build();
                        a.recycle();
                        int j = 0;
                        int numAttrs = attrs.getAttributeCount();
                        int[] stateSpec = new int[numAttrs];
                        for (int i2 = 0; i2 < numAttrs; i2++) {
                            int stateResId = attrs.getAttributeNameResource(i2);
                            if (stateResId != R.attr.shapeAppearance && stateResId != R.attr.shapeAppearanceOverlay) {
                                int j2 = j + 1;
                                stateSpec[j] = attrs.getAttributeBooleanValue(i2, false) ? stateResId : -stateResId;
                                j = j2;
                            }
                        }
                        builder.addStateShapeAppearanceModel(StateSet.trimStateSet(stateSpec, j), shapeAppearanceModel);
                        theme2 = theme;
                        i = 1;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private StateListShapeAppearanceModel(Builder builder) {
        this.stateCount = builder.stateCount;
        this.defaultShape = builder.defaultShape;
        this.stateSpecs = builder.stateSpecs;
        this.shapeAppearanceModels = builder.shapeAppearanceModels;
        this.topLeftCornerSizeOverride = builder.topLeftCornerSizeOverride;
        this.topRightCornerSizeOverride = builder.topRightCornerSizeOverride;
        this.bottomLeftCornerSizeOverride = builder.bottomLeftCornerSizeOverride;
        this.bottomRightCornerSizeOverride = builder.bottomRightCornerSizeOverride;
    }

    public int getStateCount() {
        return this.stateCount;
    }

    public ShapeAppearanceModel getDefaultShape(boolean withCornerSizeOverrides) {
        if (!withCornerSizeOverrides || (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null)) {
            return this.defaultShape;
        }
        ShapeAppearanceModel.Builder builder = this.defaultShape.toBuilder();
        if (this.topLeftCornerSizeOverride != null) {
            builder.setTopLeftCornerSize(this.topLeftCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.topRightCornerSizeOverride != null) {
            builder.setTopRightCornerSize(this.topRightCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.bottomLeftCornerSizeOverride != null) {
            builder.setBottomLeftCornerSize(this.bottomLeftCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.bottomRightCornerSizeOverride != null) {
            builder.setBottomRightCornerSize(this.bottomRightCornerSizeOverride.getDefaultCornerSize());
        }
        return builder.build();
    }

    protected ShapeAppearanceModel getShapeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        if (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null) {
            return this.shapeAppearanceModels[idx];
        }
        ShapeAppearanceModel.Builder builder = this.shapeAppearanceModels[idx].toBuilder();
        if (this.topLeftCornerSizeOverride != null) {
            builder.setTopLeftCornerSize(this.topLeftCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.topRightCornerSizeOverride != null) {
            builder.setTopRightCornerSize(this.topRightCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.bottomLeftCornerSizeOverride != null) {
            builder.setBottomLeftCornerSize(this.bottomLeftCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.bottomRightCornerSizeOverride != null) {
            builder.setBottomRightCornerSize(this.bottomRightCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        return builder.build();
    }

    private int indexOfStateSet(int[] stateSet) {
        int[][] stateSpecs = this.stateSpecs;
        for (int i = 0; i < this.stateCount; i++) {
            if (StateSet.stateSetMatches(stateSpecs[i], stateSet)) {
                return i;
            }
        }
        return -1;
    }

    public StateListShapeAppearanceModel withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
        return toBuilder().withTransformedCornerSizes(op).build();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public boolean isStateful() {
        if (this.stateCount > 1) {
            return true;
        }
        if (this.topLeftCornerSizeOverride != null && this.topLeftCornerSizeOverride.isStateful()) {
            return true;
        }
        if (this.topRightCornerSizeOverride != null && this.topRightCornerSizeOverride.isStateful()) {
            return true;
        }
        if (this.bottomLeftCornerSizeOverride == null || !this.bottomLeftCornerSizeOverride.isStateful()) {
            return this.bottomRightCornerSizeOverride != null && this.bottomRightCornerSizeOverride.isStateful();
        }
        return true;
    }

    public static int swapCornerPositionRtl(int flagSet) {
        int leftFlagSet = flagSet & 5;
        int rightFlagSet = flagSet & 10;
        return (leftFlagSet << 1) | (rightFlagSet >> 1);
    }
}
