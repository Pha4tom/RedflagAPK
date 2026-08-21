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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class StateListCornerSize {
    private static final int INITIAL_CAPACITY = 10;
    private CornerSize defaultCornerSize;
    int stateCount;
    int[][] stateSpecs = new int[10][];
    CornerSize[] cornerSizes = new CornerSize[10];

    public static StateListCornerSize create(Context context, TypedArray attributes, int index, CornerSize defaultCornerSize) {
        int type;
        int resourceId = attributes.getResourceId(index, 0);
        if (resourceId == 0) {
            return create(ShapeAppearanceModel.getCornerSize(attributes, index, defaultCornerSize));
        }
        String typeName = context.getResources().getResourceTypeName(resourceId);
        if (!typeName.equals("xml")) {
            return create(ShapeAppearanceModel.getCornerSize(attributes, index, defaultCornerSize));
        }
        try {
            XmlResourceParser parser = context.getResources().getXml(resourceId);
            try {
                StateListCornerSize stateListCornerSize = new StateListCornerSize();
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
                    stateListCornerSize.loadCornerSizesFromItems(context, parser, attrs, context.getTheme());
                }
                if (parser != null) {
                    parser.close();
                }
                return stateListCornerSize;
            } finally {
            }
        } catch (Resources.NotFoundException | IOException | XmlPullParserException e) {
            return create(defaultCornerSize);
        }
    }

    public static StateListCornerSize create(CornerSize cornerSize) {
        StateListCornerSize stateListCornerSize = new StateListCornerSize();
        stateListCornerSize.addStateCornerSize(StateSet.WILD_CARD, cornerSize);
        return stateListCornerSize;
    }

    public StateListCornerSize withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
        StateListCornerSize newStateListCornerSize = new StateListCornerSize();
        newStateListCornerSize.stateCount = this.stateCount;
        newStateListCornerSize.stateSpecs = new int[this.stateSpecs.length][];
        System.arraycopy(this.stateSpecs, 0, newStateListCornerSize.stateSpecs, 0, this.stateSpecs.length);
        newStateListCornerSize.cornerSizes = new CornerSize[this.cornerSizes.length];
        for (int i = 0; i < this.stateCount; i++) {
            newStateListCornerSize.cornerSizes[i] = op.apply(this.cornerSizes[i]);
        }
        return newStateListCornerSize;
    }

    public boolean isStateful() {
        return this.stateCount > 1;
    }

    public CornerSize getDefaultCornerSize() {
        return this.defaultCornerSize;
    }

    public CornerSize getCornerSizeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        return idx < 0 ? this.defaultCornerSize : this.cornerSizes[idx];
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

    private void loadCornerSizesFromItems(Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i = 1;
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int type = parser.next();
            if (type != i) {
                int depth = parser.getDepth();
                if (depth >= innerDepth || type != 3) {
                    if (type != 2 || depth > innerDepth) {
                        i = 1;
                    } else if (parser.getName().equals("item")) {
                        Resources res = context.getResources();
                        TypedArray a = theme == null ? res.obtainAttributes(attrs, R.styleable.ShapeAppearance) : theme.obtainStyledAttributes(attrs, R.styleable.ShapeAppearance, 0, 0);
                        CornerSize cornerSize = ShapeAppearanceModel.getCornerSize(a, R.styleable.ShapeAppearance_cornerSize, new AbsoluteCornerSize(0.0f));
                        a.recycle();
                        int j = 0;
                        int numAttrs = attrs.getAttributeCount();
                        int[] stateSpec = new int[numAttrs];
                        for (int i2 = 0; i2 < numAttrs; i2++) {
                            int stateResId = attrs.getAttributeNameResource(i2);
                            if (stateResId != R.attr.cornerSize) {
                                int j2 = j + 1;
                                stateSpec[j] = attrs.getAttributeBooleanValue(i2, false) ? stateResId : -stateResId;
                                j = j2;
                            }
                        }
                        addStateCornerSize(StateSet.trimStateSet(stateSpec, j), cornerSize);
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

    private void addStateCornerSize(int[] stateSpec, CornerSize cornerSize) {
        if (this.stateCount == 0 || stateSpec.length == 0) {
            this.defaultCornerSize = cornerSize;
        }
        if (this.stateCount >= this.stateSpecs.length) {
            growArray(this.stateCount, this.stateCount + 10);
        }
        this.stateSpecs[this.stateCount] = stateSpec;
        this.cornerSizes[this.stateCount] = cornerSize;
        this.stateCount++;
    }

    private void growArray(int oldSize, int newSize) {
        int[][] newStateSets = new int[newSize][];
        System.arraycopy(this.stateSpecs, 0, newStateSets, 0, oldSize);
        this.stateSpecs = newStateSets;
        CornerSize[] newCornerSizes = new CornerSize[newSize];
        System.arraycopy(this.cornerSizes, 0, newCornerSizes, 0, oldSize);
        this.cornerSizes = newCornerSizes;
    }
}
