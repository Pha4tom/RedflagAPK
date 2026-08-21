package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import com.google.android.material.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class StateListSizeChange {
    private static final int INITIAL_CAPACITY = 10;
    private SizeChange defaultSizeChange;
    int stateCount;
    int[][] stateSpecs = new int[10][];
    SizeChange[] sizeChanges = new SizeChange[10];

    public enum SizeChangeType {
        PERCENT,
        PIXELS
    }

    public static StateListSizeChange create(Context context, TypedArray attributes, int index) {
        int type;
        int resourceId = attributes.getResourceId(index, 0);
        if (resourceId == 0) {
            return null;
        }
        String typeName = context.getResources().getResourceTypeName(resourceId);
        if (!typeName.equals("xml")) {
            return null;
        }
        try {
            XmlResourceParser parser = context.getResources().getXml(resourceId);
            try {
                StateListSizeChange stateListSizeChange = new StateListSizeChange();
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
                    stateListSizeChange.loadSizeChangeFromItems(context, parser, attrs, context.getTheme());
                }
                if (parser != null) {
                    parser.close();
                }
                return stateListSizeChange;
            } finally {
            }
        } catch (Resources.NotFoundException | IOException | XmlPullParserException e) {
            return null;
        }
    }

    public boolean isStateful() {
        return this.stateCount > 1;
    }

    public SizeChange getDefaultSizeChange() {
        return this.defaultSizeChange;
    }

    public SizeChange getSizeChangeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        return idx < 0 ? this.defaultSizeChange : this.sizeChanges[idx];
    }

    public int getMaxWidthChange(int baseWidth) {
        int maxWidthChange = -baseWidth;
        for (int i = 0; i < this.stateCount; i++) {
            SizeChange sizeChange = this.sizeChanges[i];
            if (sizeChange.widthChange.type == SizeChangeType.PIXELS) {
                maxWidthChange = (int) Math.max(maxWidthChange, sizeChange.widthChange.amount);
            } else if (sizeChange.widthChange.type == SizeChangeType.PERCENT) {
                maxWidthChange = (int) Math.max(maxWidthChange, baseWidth * sizeChange.widthChange.amount);
            }
        }
        return maxWidthChange;
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

    private void loadSizeChangeFromItems(Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a;
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
                        boolean z = false;
                        if (theme != null) {
                            a = theme.obtainStyledAttributes(attrs, R.styleable.StateListSizeChange, 0, 0);
                        } else {
                            a = res.obtainAttributes(attrs, R.styleable.StateListSizeChange);
                        }
                        SizeChangeAmount widthChangeAmount = getSizeChangeAmount(a, R.styleable.StateListSizeChange_widthChange, null);
                        a.recycle();
                        int j = 0;
                        int numAttrs = attrs.getAttributeCount();
                        int[] stateSpec = new int[numAttrs];
                        int i2 = 0;
                        while (i2 < numAttrs) {
                            int stateResId = attrs.getAttributeNameResource(i2);
                            if (stateResId != R.attr.widthChange) {
                                int j2 = j + 1;
                                stateSpec[j] = attrs.getAttributeBooleanValue(i2, z) ? stateResId : -stateResId;
                                j = j2;
                            }
                            i2++;
                            z = false;
                        }
                        addStateSizeChange(StateSet.trimStateSet(stateSpec, j), new SizeChange(widthChangeAmount));
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

    private SizeChangeAmount getSizeChangeAmount(TypedArray a, int index, SizeChangeAmount defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defaultValue;
        }
        if (value.type == 5) {
            return new SizeChangeAmount(SizeChangeType.PIXELS, TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
        }
        if (value.type == 6) {
            return new SizeChangeAmount(SizeChangeType.PERCENT, value.getFraction(1.0f, 1.0f));
        }
        return defaultValue;
    }

    private void addStateSizeChange(int[] stateSpec, SizeChange sizeChange) {
        if (this.stateCount == 0 || stateSpec.length == 0) {
            this.defaultSizeChange = sizeChange;
        }
        if (this.stateCount >= this.stateSpecs.length) {
            growArray(this.stateCount, this.stateCount + 10);
        }
        this.stateSpecs[this.stateCount] = stateSpec;
        this.sizeChanges[this.stateCount] = sizeChange;
        this.stateCount++;
    }

    private void growArray(int oldSize, int newSize) {
        int[][] newStateSets = new int[newSize][];
        System.arraycopy(this.stateSpecs, 0, newStateSets, 0, oldSize);
        this.stateSpecs = newStateSets;
        SizeChange[] newSizeChanges = new SizeChange[newSize];
        System.arraycopy(this.sizeChanges, 0, newSizeChanges, 0, oldSize);
        this.sizeChanges = newSizeChanges;
    }

    public static class SizeChange {
        public SizeChangeAmount widthChange;

        SizeChange(SizeChangeAmount widthChange) {
            this.widthChange = widthChange;
        }

        SizeChange(SizeChange other) {
            this.widthChange = new SizeChangeAmount(other.widthChange.type, other.widthChange.amount);
        }
    }

    public static class SizeChangeAmount {
        float amount;
        SizeChangeType type;

        SizeChangeAmount(SizeChangeType type, float amount) {
            this.type = type;
            this.amount = amount;
        }

        public int getChange(int baseSize) {
            if (this.type == SizeChangeType.PERCENT) {
                return (int) (this.amount * baseSize);
            }
            if (this.type == SizeChangeType.PIXELS) {
                return (int) this.amount;
            }
            return 0;
        }
    }
}
