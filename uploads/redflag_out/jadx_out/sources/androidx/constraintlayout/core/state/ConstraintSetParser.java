package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLArray;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.parser.CLString;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.helpers.BarrierReference;
import androidx.constraintlayout.core.state.helpers.ChainReference;
import androidx.constraintlayout.core.state.helpers.FlowReference;
import androidx.constraintlayout.core.state.helpers.GuidelineReference;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintSetParser {
    private static final boolean PARSER_DEBUG = false;

    interface GeneratedValue {
        float value();
    }

    public enum MotionLayoutDebugFlags {
        NONE,
        SHOW_ALL,
        UNKNOWN
    }

    public static class DesignElement {
        String mId;
        HashMap<String, String> mParams;
        String mType;

        public String getId() {
            return this.mId;
        }

        public String getType() {
            return this.mType;
        }

        public HashMap<String, String> getParams() {
            return this.mParams;
        }

        DesignElement(String id, String type, HashMap<String, String> params) {
            this.mId = id;
            this.mType = type;
            this.mParams = params;
        }
    }

    public static class LayoutVariables {
        HashMap<String, Integer> mMargins = new HashMap<>();
        HashMap<String, GeneratedValue> mGenerators = new HashMap<>();
        HashMap<String, ArrayList<String>> mArrayIds = new HashMap<>();

        void put(String elementName, int element) {
            this.mMargins.put(elementName, Integer.valueOf(element));
        }

        void put(String elementName, float start, float incrementBy) {
            if (this.mGenerators.containsKey(elementName) && (this.mGenerators.get(elementName) instanceof OverrideValue)) {
                return;
            }
            this.mGenerators.put(elementName, new Generator(start, incrementBy));
        }

        void put(String elementName, float from, float to, float step, String prefix, String postfix) {
            if (this.mGenerators.containsKey(elementName) && (this.mGenerators.get(elementName) instanceof OverrideValue)) {
                return;
            }
            FiniteGenerator generator = new FiniteGenerator(from, to, step, prefix, postfix);
            this.mGenerators.put(elementName, generator);
            this.mArrayIds.put(elementName, generator.array());
        }

        public void putOverride(String elementName, float value) {
            GeneratedValue generator = new OverrideValue(value);
            this.mGenerators.put(elementName, generator);
        }

        float get(Object elementName) {
            if (elementName instanceof CLString) {
                String stringValue = ((CLString) elementName).content();
                if (this.mGenerators.containsKey(stringValue)) {
                    return this.mGenerators.get(stringValue).value();
                }
                if (this.mMargins.containsKey(stringValue)) {
                    return this.mMargins.get(stringValue).floatValue();
                }
                return 0.0f;
            }
            if (elementName instanceof CLNumber) {
                return ((CLNumber) elementName).getFloat();
            }
            return 0.0f;
        }

        ArrayList<String> getList(String elementName) {
            if (this.mArrayIds.containsKey(elementName)) {
                return this.mArrayIds.get(elementName);
            }
            return null;
        }

        void put(String elementName, ArrayList<String> elements) {
            this.mArrayIds.put(elementName, elements);
        }
    }

    static class Generator implements GeneratedValue {
        float mCurrent;
        float mIncrementBy;
        float mStart;
        boolean mStop = false;

        Generator(float start, float incrementBy) {
            this.mStart = 0.0f;
            this.mIncrementBy = 0.0f;
            this.mCurrent = 0.0f;
            this.mStart = start;
            this.mIncrementBy = incrementBy;
            this.mCurrent = start;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            if (!this.mStop) {
                this.mCurrent += this.mIncrementBy;
            }
            return this.mCurrent;
        }
    }

    static class FiniteGenerator implements GeneratedValue {
        float mFrom;
        float mInitial;
        float mMax;
        String mPostfix;
        String mPrefix;
        float mStep;
        float mTo;
        boolean mStop = false;
        float mCurrent = 0.0f;

        FiniteGenerator(float from, float to, float step, String prefix, String postfix) {
            this.mFrom = 0.0f;
            this.mTo = 0.0f;
            this.mStep = 0.0f;
            this.mFrom = from;
            this.mTo = to;
            this.mStep = step;
            this.mPrefix = prefix == null ? "" : prefix;
            this.mPostfix = postfix != null ? postfix : "";
            this.mMax = to;
            this.mInitial = from;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            if (this.mCurrent >= this.mMax) {
                this.mStop = true;
            }
            if (!this.mStop) {
                this.mCurrent += this.mStep;
            }
            return this.mCurrent;
        }

        public ArrayList<String> array() {
            ArrayList<String> array = new ArrayList<>();
            int value = (int) this.mInitial;
            int maxInt = (int) this.mMax;
            for (int i = value; i <= maxInt; i++) {
                array.add(this.mPrefix + value + this.mPostfix);
                value += (int) this.mStep;
            }
            return array;
        }
    }

    static class OverrideValue implements GeneratedValue {
        float mValue;

        OverrideValue(float value) {
            this.mValue = value;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            return this.mValue;
        }
    }

    public static void parseJSON(String content, Transition transition, int state) {
        try {
            CLObject json = CLParser.parse(content);
            ArrayList<String> elements = json.names();
            if (elements == null) {
                return;
            }
            for (String elementName : elements) {
                CLElement base_element = json.get(elementName);
                if (base_element instanceof CLObject) {
                    CLObject element = (CLObject) base_element;
                    CLObject customProperties = element.getObjectOrNull("custom");
                    if (customProperties != null) {
                        ArrayList<String> properties = customProperties.names();
                        for (String property : properties) {
                            CLElement value = customProperties.get(property);
                            if (value instanceof CLNumber) {
                                transition.addCustomFloat(state, elementName, property, value.getFloat());
                            } else if (value instanceof CLString) {
                                long color = parseColorString(value.content());
                                if (color != -1) {
                                    transition.addCustomColor(state, elementName, property, (int) color);
                                }
                            }
                        }
                    }
                }
            }
        } catch (CLParsingException e) {
            System.err.println("Error parsing JSON " + e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void parseMotionSceneJSON(androidx.constraintlayout.core.state.CoreMotionScene r7, java.lang.String r8) {
        /*
            androidx.constraintlayout.core.parser.CLObject r0 = androidx.constraintlayout.core.parser.CLParser.parse(r8)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            java.util.ArrayList r1 = r0.names()     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r1 != 0) goto Lb
            return
        Lb:
            java.util.Iterator r2 = r1.iterator()     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
        Lf:
            boolean r3 = r2.hasNext()     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r3 == 0) goto L5e
            java.lang.Object r3 = r2.next()     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            java.lang.String r3 = (java.lang.String) r3     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            androidx.constraintlayout.core.parser.CLElement r4 = r0.get(r3)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            boolean r5 = r4 instanceof androidx.constraintlayout.core.parser.CLObject     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r5 == 0) goto L5d
            r5 = r4
            androidx.constraintlayout.core.parser.CLObject r5 = (androidx.constraintlayout.core.parser.CLObject) r5     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            int r6 = r3.hashCode()     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            switch(r6) {
                case -2137403731: goto L42;
                case -241441378: goto L38;
                case 1101852654: goto L2e;
                default: goto L2d;
            }     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
        L2d:
            goto L4c
        L2e:
            java.lang.String r6 = "ConstraintSets"
            boolean r6 = r3.equals(r6)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r6 == 0) goto L2d
            r6 = 0
            goto L4d
        L38:
            java.lang.String r6 = "Transitions"
            boolean r6 = r3.equals(r6)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r6 == 0) goto L2d
            r6 = 1
            goto L4d
        L42:
            java.lang.String r6 = "Header"
            boolean r6 = r3.equals(r6)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            if (r6 == 0) goto L2d
            r6 = 2
            goto L4d
        L4c:
            r6 = -1
        L4d:
            switch(r6) {
                case 0: goto L59;
                case 1: goto L55;
                case 2: goto L51;
                default: goto L50;
            }     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
        L50:
            goto L5d
        L51:
            parseHeader(r7, r5)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            goto L5d
        L55:
            parseTransitions(r7, r5)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
            goto L5d
        L59:
            parseConstraintSets(r7, r5)     // Catch: androidx.constraintlayout.core.parser.CLParsingException -> L5f
        L5d:
            goto Lf
        L5e:
            goto L78
        L5f:
            r0 = move-exception
            java.io.PrintStream r1 = java.lang.System.err
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error parsing JSON "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.println(r2)
        L78:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.parseMotionSceneJSON(androidx.constraintlayout.core.state.CoreMotionScene, java.lang.String):void");
    }

    static void parseConstraintSets(CoreMotionScene scene, CLObject json) throws CLParsingException {
        ArrayList<String> constraintSetNames = json.names();
        if (constraintSetNames == null) {
            return;
        }
        for (String csName : constraintSetNames) {
            CLObject constraintSet = json.getObject(csName);
            boolean added = false;
            String ext = constraintSet.getStringOrNull("Extends");
            if (ext != null && !ext.isEmpty()) {
                String base = scene.getConstraintSet(ext);
                if (base != null) {
                    CLObject baseJson = CLParser.parse(base);
                    ArrayList<String> widgetsOverride = constraintSet.names();
                    if (widgetsOverride != null) {
                        for (String widgetOverrideName : widgetsOverride) {
                            CLElement value = constraintSet.get(widgetOverrideName);
                            if (value instanceof CLObject) {
                                override(baseJson, widgetOverrideName, (CLObject) value);
                            }
                        }
                        scene.setConstraintSetContent(csName, baseJson.toJSON());
                        added = true;
                    }
                }
            }
            if (!added) {
                scene.setConstraintSetContent(csName, constraintSet.toJSON());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void override(androidx.constraintlayout.core.parser.CLObject r8, java.lang.String r9, androidx.constraintlayout.core.parser.CLObject r10) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            Method dump skipped, instruction units count: 262
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.override(androidx.constraintlayout.core.parser.CLObject, java.lang.String, androidx.constraintlayout.core.parser.CLObject):void");
    }

    static void parseTransitions(CoreMotionScene scene, CLObject json) throws CLParsingException {
        ArrayList<String> elements = json.names();
        if (elements == null) {
            return;
        }
        for (String elementName : elements) {
            scene.setTransitionContent(elementName, json.getObject(elementName).toJSON());
        }
    }

    static void parseHeader(CoreMotionScene scene, CLObject json) {
        String name = json.getStringOrNull("export");
        if (name != null) {
            scene.setDebugName(name);
        }
    }

    public static void parseJSON(String content, State state, LayoutVariables layoutVariables) throws CLParsingException {
        try {
            CLObject json = CLParser.parse(content);
            populateState(json, state, layoutVariables);
        } catch (CLParsingException e) {
            System.err.println("Error parsing JSON " + e);
        }
    }

    public static void populateState(CLObject parsedJson, State state, LayoutVariables layoutVariables) throws CLParsingException {
        CLElement element;
        ArrayList<String> elements = parsedJson.names();
        if (elements == null) {
            return;
        }
        for (String elementName : elements) {
            element = parsedJson.get(elementName);
            switch (elementName) {
                case "Variables":
                    if (!(element instanceof CLObject)) {
                        break;
                    } else {
                        parseVariables(state, layoutVariables, (CLObject) element);
                        break;
                    }
                    break;
                case "Helpers":
                    if (!(element instanceof CLArray)) {
                        break;
                    } else {
                        parseHelpers(state, layoutVariables, (CLArray) element);
                        break;
                    }
                    break;
                case "Generate":
                    if (!(element instanceof CLObject)) {
                        break;
                    } else {
                        parseGenerate(state, layoutVariables, (CLObject) element);
                        break;
                    }
                    break;
                default:
                    if (element instanceof CLObject) {
                        String type = lookForType((CLObject) element);
                        if (type != null) {
                            switch (type) {
                                case "hGuideline":
                                    parseGuidelineParams(0, state, elementName, (CLObject) element);
                                    break;
                                case "vGuideline":
                                    parseGuidelineParams(1, state, elementName, (CLObject) element);
                                    break;
                                case "barrier":
                                    parseBarrier(state, elementName, (CLObject) element);
                                    break;
                                case "vChain":
                                case "hChain":
                                    parseChainType(type, state, elementName, layoutVariables, (CLObject) element);
                                    break;
                                case "vFlow":
                                case "hFlow":
                                    parseFlowType(type, state, elementName, layoutVariables, (CLObject) element);
                                    break;
                                case "grid":
                                case "row":
                                case "column":
                                    parseGridType(type, state, elementName, layoutVariables, (CLObject) element);
                                    break;
                            }
                        } else {
                            parseWidget(state, layoutVariables, elementName, (CLObject) element);
                            break;
                        }
                    } else {
                        if (element instanceof CLNumber) {
                            layoutVariables.put(elementName, element.getInt());
                        }
                        break;
                    }
                    break;
            }
        }
    }

    private static void parseVariables(State state, LayoutVariables layoutVariables, CLObject json) throws CLParsingException {
        ArrayList<String> elements = json.names();
        if (elements == null) {
            return;
        }
        for (String elementName : elements) {
            CLElement element = json.get(elementName);
            if (element instanceof CLNumber) {
                layoutVariables.put(elementName, element.getInt());
            } else if (element instanceof CLObject) {
                CLObject obj = (CLObject) element;
                if (!obj.has(TypedValues.TransitionType.S_FROM) || !obj.has(TypedValues.TransitionType.S_TO)) {
                    if (obj.has(TypedValues.TransitionType.S_FROM) && obj.has("step")) {
                        float start = layoutVariables.get(obj.get(TypedValues.TransitionType.S_FROM));
                        float increment = layoutVariables.get(obj.get("step"));
                        layoutVariables.put(elementName, start, increment);
                    } else if (obj.has("ids")) {
                        CLArray ids = obj.getArray("ids");
                        ArrayList<String> arrayIds = new ArrayList<>();
                        for (int i = 0; i < ids.size(); i++) {
                            arrayIds.add(ids.getString(i));
                        }
                        layoutVariables.put(elementName, arrayIds);
                    } else if (obj.has("tag")) {
                        layoutVariables.put(elementName, state.getIdsForTag(obj.getString("tag")));
                    }
                } else {
                    float from = layoutVariables.get(obj.get(TypedValues.TransitionType.S_FROM));
                    float to = layoutVariables.get(obj.get(TypedValues.TransitionType.S_TO));
                    String prefix = obj.getStringOrNull("prefix");
                    String postfix = obj.getStringOrNull("postfix");
                    layoutVariables.put(elementName, from, to, 1.0f, prefix, postfix);
                }
            }
        }
    }

    public static void parseDesignElementsJSON(String content, ArrayList<DesignElement> list) throws CLParsingException {
        String elementName;
        CLElement element;
        int i;
        CLObject json = CLParser.parse(content);
        ArrayList<String> elements = json.names();
        if (elements != null && 0 < elements.size()) {
            elementName = elements.get(0);
            element = json.get(elementName);
            i = 0;
            switch (elementName) {
                case "Design":
                    if (element instanceof CLObject) {
                        CLObject obj = (CLObject) element;
                        ArrayList<String> elements2 = obj.names();
                        int j = 0;
                        while (j < elements2.size()) {
                            String designElementName = elements2.get(j);
                            CLObject designElement = (CLObject) ((CLObject) element).get(designElementName);
                            System.out.printf("element found " + designElementName + "", new Object[i]);
                            String type = designElement.getStringOrNull("type");
                            if (type != null) {
                                HashMap<String, String> parameters = new HashMap<>();
                                int size = designElement.size();
                                for (int k = 0; k < size; k++) {
                                    CLKey key = (CLKey) designElement.get(j);
                                    String paramName = key.content();
                                    String paramValue = key.getValue().content();
                                    if (paramValue != null) {
                                        parameters.put(paramName, paramValue);
                                    }
                                }
                                list.add(new DesignElement(elementName, type, parameters));
                            }
                            j++;
                            i = 0;
                        }
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void parseHelpers(androidx.constraintlayout.core.state.State r7, androidx.constraintlayout.core.state.ConstraintSetParser.LayoutVariables r8, androidx.constraintlayout.core.parser.CLArray r9) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r0 = 0
        L1:
            int r1 = r9.size()
            if (r0 >= r1) goto L68
            androidx.constraintlayout.core.parser.CLElement r1 = r9.get(r0)
            boolean r2 = r1 instanceof androidx.constraintlayout.core.parser.CLArray
            if (r2 == 0) goto L65
            r2 = r1
            androidx.constraintlayout.core.parser.CLArray r2 = (androidx.constraintlayout.core.parser.CLArray) r2
            int r3 = r2.size()
            r4 = 1
            if (r3 <= r4) goto L65
            r3 = 0
            java.lang.String r5 = r2.getString(r3)
            int r6 = r5.hashCode()
            switch(r6) {
                case -1785507558: goto L45;
                case -1252464839: goto L3b;
                case -851656725: goto L30;
                case 965681512: goto L26;
                default: goto L25;
            }
        L25:
            goto L50
        L26:
            java.lang.String r6 = "hGuideline"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L25
            r5 = 2
            goto L51
        L30:
            java.lang.String r6 = "vChain"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L25
            r5 = r4
            goto L51
        L3b:
            java.lang.String r6 = "hChain"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L25
            r5 = r3
            goto L51
        L45:
            java.lang.String r6 = "vGuideline"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L25
            r5 = 3
            goto L51
        L50:
            r5 = -1
        L51:
            switch(r5) {
                case 0: goto L61;
                case 1: goto L5d;
                case 2: goto L59;
                case 3: goto L55;
                default: goto L54;
            }
        L54:
            goto L65
        L55:
            parseGuideline(r4, r7, r2)
            goto L65
        L59:
            parseGuideline(r3, r7, r2)
            goto L65
        L5d:
            parseChain(r4, r7, r8, r2)
            goto L65
        L61:
            parseChain(r3, r7, r8, r2)
        L65:
            int r0 = r0 + 1
            goto L1
        L68:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.parseHelpers(androidx.constraintlayout.core.state.State, androidx.constraintlayout.core.state.ConstraintSetParser$LayoutVariables, androidx.constraintlayout.core.parser.CLArray):void");
    }

    static void parseGenerate(State state, LayoutVariables layoutVariables, CLObject json) throws CLParsingException {
        ArrayList<String> elements = json.names();
        if (elements == null) {
            return;
        }
        for (String elementName : elements) {
            CLElement element = json.get(elementName);
            ArrayList<String> arrayIds = layoutVariables.getList(elementName);
            if (arrayIds != null && (element instanceof CLObject)) {
                for (String id : arrayIds) {
                    parseWidget(state, layoutVariables, id, (CLObject) element);
                }
            }
        }
    }

    static void parseChain(int orientation, State state, LayoutVariables margins, CLArray helper) throws CLParsingException {
        String styleValue;
        ChainReference chain = orientation == 0 ? state.horizontalChain() : state.verticalChain();
        CLElement refs = helper.get(1);
        if ((refs instanceof CLArray) && ((CLArray) refs).size() >= 1) {
            for (int i = 0; i < ((CLArray) refs).size(); i++) {
                chain.add(((CLArray) refs).getString(i));
            }
            int i2 = helper.size();
            if (i2 > 2) {
                CLElement params = helper.get(2);
                if (!(params instanceof CLObject)) {
                    return;
                }
                CLObject obj = (CLObject) params;
                ArrayList<String> constraints = obj.names();
                for (String constraintName : constraints) {
                    switch (constraintName) {
                        case "style":
                            CLElement styleObject = ((CLObject) params).get(constraintName);
                            if ((styleObject instanceof CLArray) && ((CLArray) styleObject).size() > 1) {
                                styleValue = ((CLArray) styleObject).getString(0);
                                float biasValue = ((CLArray) styleObject).getFloat(1);
                                chain.bias(biasValue);
                            } else {
                                styleValue = styleObject.content();
                            }
                            switch (styleValue) {
                                case "packed":
                                    chain.style(State.Chain.PACKED);
                                    break;
                                case "spread_inside":
                                    chain.style(State.Chain.SPREAD_INSIDE);
                                    break;
                                default:
                                    chain.style(State.Chain.SPREAD);
                                    break;
                            }
                            break;
                        default:
                            parseConstraint(state, margins, (CLObject) params, chain, constraintName);
                            break;
                    }
                }
            }
        }
    }

    private static float toPix(State state, float dp) {
        return state.getDpToPixel().toPixels(dp);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static void parseChainType(String orientation, State state, String chainName, LayoutVariables margins, CLObject object) throws CLParsingException {
        ChainReference chainReferenceVerticalChain;
        int i;
        CLElement refs;
        CLElement refs2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float preMargin;
        String styleValue;
        int i7 = 0;
        if (orientation.charAt(0) != 'h') {
            chainReferenceVerticalChain = state.verticalChain();
        } else {
            chainReferenceVerticalChain = state.horizontalChain();
        }
        ChainReference chain = chainReferenceVerticalChain;
        chain.setKey(chainName);
        for (String params : object.names()) {
            int i8 = -1;
            int i9 = 3;
            int i10 = 2;
            int i11 = 1;
            switch (params.hashCode()) {
                case -1383228885:
                    i = params.equals("bottom") ? 4 : -1;
                    break;
                case -567445985:
                    i = params.equals("contains") ? i7 : -1;
                    break;
                case 100571:
                    i = params.equals("end") ? 2 : -1;
                    break;
                case 115029:
                    i = params.equals("top") ? 3 : -1;
                    break;
                case 3317767:
                    i = params.equals("left") ? 5 : -1;
                    break;
                case 108511772:
                    i = params.equals("right") ? 6 : -1;
                    break;
                case 109757538:
                    i = params.equals("start") ? 1 : -1;
                    break;
                case 109780401:
                    i = params.equals("style") ? 7 : -1;
                    break;
                default:
                    i = -1;
                    break;
            }
            switch (i) {
                case 0:
                    CLElement refs3 = object.get(params);
                    if (refs3 instanceof CLArray) {
                        if (((CLArray) refs3).size() >= 1) {
                            int i12 = 0;
                            while (i12 < ((CLArray) refs3).size()) {
                                CLElement chainElement = ((CLArray) refs3).get(i12);
                                if (chainElement instanceof CLArray) {
                                    CLArray array = (CLArray) chainElement;
                                    if (array.size() <= 0) {
                                        refs2 = refs3;
                                        i2 = i9;
                                        i3 = i10;
                                        i4 = i11;
                                        i5 = i12;
                                    } else {
                                        String id = array.get(i7).content();
                                        float weight = Float.NaN;
                                        float postMargin = Float.NaN;
                                        float preGoneMargin = Float.NaN;
                                        float postGoneMargin = Float.NaN;
                                        switch (array.size()) {
                                            case 2:
                                                i6 = i9;
                                                weight = array.getFloat(i11);
                                                preMargin = Float.NaN;
                                                break;
                                            case 3:
                                                i6 = i9;
                                                weight = array.getFloat(i11);
                                                preMargin = toPix(state, array.getFloat(i10));
                                                postMargin = preMargin;
                                                break;
                                            case 4:
                                                weight = array.getFloat(i11);
                                                float preMargin2 = toPix(state, array.getFloat(i10));
                                                i6 = 3;
                                                postMargin = toPix(state, array.getFloat(3));
                                                preMargin = preMargin2;
                                                break;
                                            case 5:
                                            default:
                                                i6 = i9;
                                                preMargin = Float.NaN;
                                                break;
                                            case 6:
                                                weight = array.getFloat(i11);
                                                float preMargin3 = toPix(state, array.getFloat(i10));
                                                postMargin = toPix(state, array.getFloat(i9));
                                                preGoneMargin = toPix(state, array.getFloat(4));
                                                postGoneMargin = toPix(state, array.getFloat(5));
                                                preMargin = preMargin3;
                                                i6 = 3;
                                                break;
                                        }
                                        float weight2 = weight;
                                        i2 = i6;
                                        refs2 = refs3;
                                        i3 = i10;
                                        i5 = i12;
                                        i4 = i11;
                                        chain.addChainElement(id, weight2, preMargin, postMargin, preGoneMargin, postGoneMargin);
                                    }
                                } else {
                                    refs2 = refs3;
                                    i2 = i9;
                                    i3 = i10;
                                    i4 = i11;
                                    i5 = i12;
                                    chain.add(chainElement.content());
                                }
                                i12 = i5 + 1;
                                refs3 = refs2;
                                i9 = i2;
                                i10 = i3;
                                i11 = i4;
                                i7 = 0;
                            }
                        } else {
                            refs = refs3;
                        }
                        break;
                    } else {
                        refs = refs3;
                    }
                    System.err.println(chainName + " contains should be an array \"" + refs.content() + "\"");
                    return;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    parseConstraint(state, margins, object, chain, params);
                    break;
                case 7:
                    CLElement styleObject = object.get(params);
                    if ((styleObject instanceof CLArray) && ((CLArray) styleObject).size() > 1) {
                        styleValue = ((CLArray) styleObject).getString(i7);
                        float biasValue = ((CLArray) styleObject).getFloat(1);
                        chain.bias(biasValue);
                    } else {
                        styleValue = styleObject.content();
                    }
                    switch (styleValue.hashCode()) {
                        case -995865480:
                            if (styleValue.equals("packed")) {
                                i8 = i7;
                            }
                            break;
                        case 1311368264:
                            if (styleValue.equals("spread_inside")) {
                                i8 = 1;
                            }
                            break;
                    }
                    switch (i8) {
                        case 0:
                            chain.style(State.Chain.PACKED);
                            break;
                        case 1:
                            chain.style(State.Chain.SPREAD_INSIDE);
                            break;
                        default:
                            chain.style(State.Chain.SPREAD);
                            break;
                    }
                    break;
            }
            i7 = 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void parseGridType(java.lang.String r16, androidx.constraintlayout.core.state.State r17, java.lang.String r18, androidx.constraintlayout.core.state.ConstraintSetParser.LayoutVariables r19, androidx.constraintlayout.core.parser.CLObject r20) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            Method dump skipped, instruction units count: 712
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.parseGridType(java.lang.String, androidx.constraintlayout.core.state.State, java.lang.String, androidx.constraintlayout.core.state.ConstraintSetParser$LayoutVariables, androidx.constraintlayout.core.parser.CLObject):void");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static void parseFlowType(String flowType, State state, String flowName, LayoutVariables layoutVariables, CLObject element) throws CLParsingException {
        int i;
        float preMargin;
        float postMargin;
        float paddingLeft;
        float paddingTop;
        float paddingLeft2;
        float paddingBottom;
        Float vLastBiasValue;
        Float vLastBiasValue2;
        Float vFirstBiasValue;
        Float hLastBiasValue;
        Float hLastBiasValue2;
        Float hFirstBiasValue;
        String vStyleValueStr;
        String hStyleValueStr;
        int i2 = 0;
        int i3 = 1;
        boolean isVertical = flowType.charAt(0) == 'v';
        FlowReference flow = state.getFlow(flowName, isVertical);
        for (String param : element.names()) {
            int i4 = -1;
            switch (param.hashCode()) {
                case -1254185091:
                    i = param.equals("hAlign") ? 8 : -1;
                    break;
                case -1237307863:
                    i = param.equals("hStyle") ? 12 : -1;
                    break;
                case -1198076529:
                    i = param.equals("hFlowBias") ? 10 : -1;
                    break;
                case -853376977:
                    i = param.equals("vAlign") ? 7 : -1;
                    break;
                case -836499749:
                    i = param.equals("vStyle") ? 11 : -1;
                    break;
                case -806339567:
                    i = param.equals("padding") ? 6 : -1;
                    break;
                case -732635235:
                    i = param.equals("vFlowBias") ? 9 : -1;
                    break;
                case -567445985:
                    i = param.equals("contains") ? i2 : -1;
                    break;
                case -488900360:
                    i = param.equals("maxElement") ? 5 : -1;
                    break;
                case 3169614:
                    i = param.equals("hGap") ? 4 : -1;
                    break;
                case 3575610:
                    i = param.equals("type") ? i3 : -1;
                    break;
                case 3586688:
                    i = param.equals("vGap") ? 3 : -1;
                    break;
                case 3657802:
                    i = param.equals("wrap") ? 2 : -1;
                    break;
                default:
                    i = -1;
                    break;
            }
            switch (i) {
                case 0:
                    CLElement refs = element.get(param);
                    if (!(refs instanceof CLArray) || ((CLArray) refs).size() < i3) {
                        System.err.println(flowName + " contains should be an array \"" + refs.content() + "\"");
                        return;
                    }
                    int i5 = 0;
                    while (i5 < ((CLArray) refs).size()) {
                        CLElement chainElement = ((CLArray) refs).get(i5);
                        if (chainElement instanceof CLArray) {
                            CLArray array = (CLArray) chainElement;
                            if (array.size() > 0) {
                                String id = array.get(0).content();
                                float weight = Float.NaN;
                                switch (array.size()) {
                                    case 2:
                                        weight = array.getFloat(1);
                                        preMargin = Float.NaN;
                                        postMargin = Float.NaN;
                                        break;
                                    case 3:
                                        weight = array.getFloat(1);
                                        postMargin = toPix(state, array.getFloat(2));
                                        preMargin = postMargin;
                                        break;
                                    case 4:
                                        weight = array.getFloat(i3);
                                        float preMargin2 = toPix(state, array.getFloat(2));
                                        float postMargin2 = toPix(state, array.getFloat(3));
                                        preMargin = preMargin2;
                                        postMargin = postMargin2;
                                        break;
                                    default:
                                        preMargin = Float.NaN;
                                        postMargin = Float.NaN;
                                        break;
                                }
                                flow.addFlowElement(id, weight, preMargin, postMargin);
                            }
                        } else {
                            flow.add(chainElement.content());
                        }
                        i5++;
                        i3 = 1;
                    }
                    break;
                    break;
                case 1:
                    if (element.get(param).content().equals("hFlow")) {
                        flow.setOrientation(0);
                    } else {
                        flow.setOrientation(i3);
                    }
                    break;
                case 2:
                    String wrapValue = element.get(param).content();
                    flow.setWrapMode(State.Wrap.getValueByString(wrapValue));
                    break;
                case 3:
                    int vGapValue = element.get(param).getInt();
                    flow.setVerticalGap(vGapValue);
                    break;
                case 4:
                    int hGapValue = element.get(param).getInt();
                    flow.setHorizontalGap(hGapValue);
                    break;
                case 5:
                    int maxElementValue = element.get(param).getInt();
                    flow.setMaxElementsWrap(maxElementValue);
                    break;
                case 6:
                    CLElement paddingObject = element.get(param);
                    if ((paddingObject instanceof CLArray) && ((CLArray) paddingObject).size() > i3) {
                        paddingLeft = ((CLArray) paddingObject).getInt(0);
                        paddingLeft2 = paddingLeft;
                        paddingTop = ((CLArray) paddingObject).getInt(i3);
                        paddingBottom = paddingTop;
                        if (((CLArray) paddingObject).size() > 2) {
                            float paddingRight = ((CLArray) paddingObject).getInt(2);
                            try {
                                paddingBottom = ((CLArray) paddingObject).getInt(3);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                paddingBottom = 0.0f;
                            }
                            paddingLeft2 = paddingRight;
                        }
                    } else {
                        paddingLeft = paddingObject.getInt();
                        paddingTop = paddingLeft;
                        paddingLeft2 = paddingLeft;
                        paddingBottom = paddingLeft;
                    }
                    float paddingBottom2 = toPix(state, paddingLeft);
                    flow.setPaddingLeft(Math.round(paddingBottom2));
                    flow.setPaddingTop(Math.round(toPix(state, paddingTop)));
                    flow.setPaddingRight(Math.round(toPix(state, paddingLeft2)));
                    flow.setPaddingBottom(Math.round(toPix(state, paddingBottom)));
                    break;
                case 7:
                    String vAlignValue = element.get(param).content();
                    switch (vAlignValue.hashCode()) {
                        case -1720785339:
                            if (vAlignValue.equals("baseline")) {
                                i4 = 2;
                            }
                            break;
                        case -1383228885:
                            if (vAlignValue.equals("bottom")) {
                                i4 = i3;
                            }
                            break;
                        case 115029:
                            if (vAlignValue.equals("top")) {
                                i4 = 0;
                            }
                            break;
                    }
                    switch (i4) {
                        case 0:
                            flow.setVerticalAlign(0);
                            break;
                        case 1:
                            flow.setVerticalAlign(i3);
                            break;
                        case 2:
                            flow.setVerticalAlign(3);
                            break;
                        default:
                            flow.setVerticalAlign(2);
                            break;
                    }
                    break;
                case 8:
                    String hAlignValue = element.get(param).content();
                    switch (hAlignValue.hashCode()) {
                        case 100571:
                            if (hAlignValue.equals("end")) {
                                i4 = i3;
                            }
                            break;
                        case 109757538:
                            if (hAlignValue.equals("start")) {
                                i4 = 0;
                            }
                            break;
                    }
                    switch (i4) {
                        case 0:
                            flow.setHorizontalAlign(0);
                            break;
                        case 1:
                            flow.setHorizontalAlign(i3);
                            break;
                        default:
                            flow.setHorizontalAlign(2);
                            break;
                    }
                    break;
                case 9:
                    CLElement vBiasObject = element.get(param);
                    Float.valueOf(0.5f);
                    Float vFirstBiasValue2 = Float.valueOf(0.5f);
                    Float vLastBiasValue3 = Float.valueOf(0.5f);
                    if ((vBiasObject instanceof CLArray) && ((CLArray) vBiasObject).size() > i3) {
                        Float vFirstBiasValue3 = Float.valueOf(((CLArray) vBiasObject).getFloat(0));
                        Float vBiasValue = Float.valueOf(((CLArray) vBiasObject).getFloat(i3));
                        if (((CLArray) vBiasObject).size() <= 2) {
                            vLastBiasValue = vLastBiasValue3;
                            vLastBiasValue2 = vFirstBiasValue3;
                            vFirstBiasValue = vBiasValue;
                        } else {
                            vLastBiasValue = Float.valueOf(((CLArray) vBiasObject).getFloat(2));
                            vLastBiasValue2 = vFirstBiasValue3;
                            vFirstBiasValue = vBiasValue;
                        }
                    } else {
                        vLastBiasValue = vLastBiasValue3;
                        vLastBiasValue2 = vFirstBiasValue2;
                        vFirstBiasValue = Float.valueOf(vBiasObject.getFloat());
                    }
                    try {
                        flow.verticalBias(vFirstBiasValue.floatValue());
                        if (vLastBiasValue2.floatValue() != 0.5f) {
                            flow.setFirstVerticalBias(vLastBiasValue2.floatValue());
                        }
                        if (vLastBiasValue.floatValue() != 0.5f) {
                            flow.setLastVerticalBias(vLastBiasValue.floatValue());
                        }
                    } catch (NumberFormatException e2) {
                    }
                    break;
                case 10:
                    CLElement hBiasObject = element.get(param);
                    Float.valueOf(0.5f);
                    Float hFirstBiasValue2 = Float.valueOf(0.5f);
                    Float hLastBiasValue3 = Float.valueOf(0.5f);
                    if ((hBiasObject instanceof CLArray) && ((CLArray) hBiasObject).size() > i3) {
                        Float hFirstBiasValue3 = Float.valueOf(((CLArray) hBiasObject).getFloat(0));
                        Float hBiasValue = Float.valueOf(((CLArray) hBiasObject).getFloat(i3));
                        if (((CLArray) hBiasObject).size() <= 2) {
                            hLastBiasValue = hLastBiasValue3;
                            hLastBiasValue2 = hFirstBiasValue3;
                            hFirstBiasValue = hBiasValue;
                        } else {
                            hLastBiasValue = Float.valueOf(((CLArray) hBiasObject).getFloat(2));
                            hLastBiasValue2 = hFirstBiasValue3;
                            hFirstBiasValue = hBiasValue;
                        }
                    } else {
                        hLastBiasValue = hLastBiasValue3;
                        hLastBiasValue2 = hFirstBiasValue2;
                        hFirstBiasValue = Float.valueOf(hBiasObject.getFloat());
                    }
                    try {
                        flow.horizontalBias(hFirstBiasValue.floatValue());
                        if (hLastBiasValue2.floatValue() != 0.5f) {
                            flow.setFirstHorizontalBias(hLastBiasValue2.floatValue());
                        }
                        if (hLastBiasValue.floatValue() != 0.5f) {
                            flow.setLastHorizontalBias(hLastBiasValue.floatValue());
                        }
                    } catch (NumberFormatException e3) {
                    }
                    break;
                case 11:
                    CLElement vStyleObject = element.get(param);
                    String vFirstStyleValueStr = "";
                    String vLastStyleValueStr = "";
                    if ((vStyleObject instanceof CLArray) && ((CLArray) vStyleObject).size() > i3) {
                        vFirstStyleValueStr = ((CLArray) vStyleObject).getString(0);
                        vStyleValueStr = ((CLArray) vStyleObject).getString(i3);
                        if (((CLArray) vStyleObject).size() > 2) {
                            vLastStyleValueStr = ((CLArray) vStyleObject).getString(2);
                        }
                    } else {
                        vStyleValueStr = vStyleObject.content();
                    }
                    if (!vStyleValueStr.equals("")) {
                        flow.setVerticalStyle(State.Chain.getValueByString(vStyleValueStr));
                    }
                    if (!vFirstStyleValueStr.equals("")) {
                        flow.setFirstVerticalStyle(State.Chain.getValueByString(vFirstStyleValueStr));
                    }
                    if (!vLastStyleValueStr.equals("")) {
                        flow.setLastVerticalStyle(State.Chain.getValueByString(vLastStyleValueStr));
                    }
                    break;
                case 12:
                    CLElement hStyleObject = element.get(param);
                    String hFirstStyleValueStr = "";
                    String hLastStyleValueStr = "";
                    if ((hStyleObject instanceof CLArray) && ((CLArray) hStyleObject).size() > i3) {
                        hFirstStyleValueStr = ((CLArray) hStyleObject).getString(i2);
                        hStyleValueStr = ((CLArray) hStyleObject).getString(i3);
                        if (((CLArray) hStyleObject).size() > 2) {
                            hLastStyleValueStr = ((CLArray) hStyleObject).getString(2);
                        }
                    } else {
                        hStyleValueStr = hStyleObject.content();
                    }
                    if (!hStyleValueStr.equals("")) {
                        flow.setHorizontalStyle(State.Chain.getValueByString(hStyleValueStr));
                    }
                    if (!hFirstStyleValueStr.equals("")) {
                        flow.setFirstHorizontalStyle(State.Chain.getValueByString(hFirstStyleValueStr));
                    }
                    if (!hLastStyleValueStr.equals("")) {
                        flow.setLastHorizontalStyle(State.Chain.getValueByString(hLastStyleValueStr));
                    }
                    break;
                default:
                    ConstraintReference reference = state.constraints(flowName);
                    applyAttribute(state, layoutVariables, reference, element, param);
                    break;
            }
            i2 = 0;
            i3 = 1;
        }
    }

    static void parseGuideline(int orientation, State state, CLArray helper) throws CLParsingException {
        String guidelineId;
        CLElement params = helper.get(1);
        if ((params instanceof CLObject) && (guidelineId = ((CLObject) params).getStringOrNull("id")) != null) {
            parseGuidelineParams(orientation, state, guidelineId, (CLObject) params);
        }
    }

    static void parseGuidelineParams(int orientation, State state, String guidelineId, CLObject params) throws CLParsingException {
        ArrayList<String> constraints;
        ConstraintReference reference;
        boolean isLtr;
        ArrayList<String> constraints2 = params.names();
        if (constraints2 == null) {
            return;
        }
        ConstraintReference reference2 = state.constraints(guidelineId);
        if (orientation == 0) {
            state.horizontalGuideline(guidelineId);
        } else {
            state.verticalGuideline(guidelineId);
        }
        boolean isLtr2 = !state.isRtl() || orientation == 0;
        GuidelineReference guidelineReference = (GuidelineReference) reference2.getFacade();
        boolean isPercent = false;
        float value = 0.0f;
        boolean fromStart = true;
        for (String constraintName : constraints2) {
            switch (constraintName) {
                case "left":
                    constraints = constraints2;
                    reference = reference2;
                    isLtr = isLtr2;
                    float value2 = toPix(state, params.getFloat(constraintName));
                    value = value2;
                    fromStart = true;
                    break;
                case "right":
                    constraints = constraints2;
                    reference = reference2;
                    isLtr = isLtr2;
                    float value3 = toPix(state, params.getFloat(constraintName));
                    value = value3;
                    fromStart = false;
                    break;
                case "start":
                    constraints = constraints2;
                    reference = reference2;
                    isLtr = isLtr2;
                    float value4 = toPix(state, params.getFloat(constraintName));
                    value = value4;
                    fromStart = isLtr;
                    break;
                case "end":
                    constraints = constraints2;
                    reference = reference2;
                    isLtr = isLtr2;
                    float value5 = toPix(state, params.getFloat(constraintName));
                    boolean fromStart2 = !isLtr;
                    value = value5;
                    fromStart = fromStart2;
                    break;
                case "percent":
                    isPercent = true;
                    CLArray percentParams = params.getArrayOrNull(constraintName);
                    if (percentParams == null) {
                        constraints = constraints2;
                        reference = reference2;
                        isLtr = isLtr2;
                        fromStart = true;
                        value = params.getFloat(constraintName);
                        break;
                    } else {
                        constraints = constraints2;
                        reference = reference2;
                        if (percentParams.size() <= 1) {
                            isLtr = isLtr2;
                            break;
                        } else {
                            isLtr = isLtr2;
                            String origin = percentParams.getString(0);
                            value = percentParams.getFloat(1);
                            switch (origin) {
                                case "left":
                                    fromStart = true;
                                    break;
                                case "right":
                                    fromStart = false;
                                    break;
                                case "start":
                                    fromStart = isLtr;
                                    break;
                                case "end":
                                    fromStart = !isLtr;
                                    break;
                            }
                        }
                    }
                    break;
                default:
                    constraints = constraints2;
                    reference = reference2;
                    isLtr = isLtr2;
                    break;
            }
            isLtr2 = isLtr;
            constraints2 = constraints;
            reference2 = reference;
        }
        if (isPercent) {
            if (fromStart) {
                guidelineReference.percent(value);
                return;
            } else {
                guidelineReference.percent(1.0f - value);
                return;
            }
        }
        if (fromStart) {
            guidelineReference.start(Float.valueOf(value));
        } else {
            guidelineReference.end(Float.valueOf(value));
        }
    }

    static void parseBarrier(State state, String elementName, CLObject element) throws CLParsingException {
        boolean isLtr = !state.isRtl();
        BarrierReference reference = state.barrier(elementName, State.Direction.END);
        ArrayList<String> constraints = element.names();
        if (constraints == null) {
            return;
        }
        for (String constraintName : constraints) {
            switch (constraintName) {
                case "direction":
                    switch (element.getString(constraintName)) {
                        case "start":
                            if (isLtr) {
                                reference.setBarrierDirection(State.Direction.LEFT);
                                break;
                            } else {
                                reference.setBarrierDirection(State.Direction.RIGHT);
                                break;
                            }
                            break;
                        case "end":
                            if (isLtr) {
                                reference.setBarrierDirection(State.Direction.RIGHT);
                                break;
                            } else {
                                reference.setBarrierDirection(State.Direction.LEFT);
                                break;
                            }
                            break;
                        case "left":
                            reference.setBarrierDirection(State.Direction.LEFT);
                            break;
                        case "right":
                            reference.setBarrierDirection(State.Direction.RIGHT);
                            break;
                        case "top":
                            reference.setBarrierDirection(State.Direction.TOP);
                            break;
                        case "bottom":
                            reference.setBarrierDirection(State.Direction.BOTTOM);
                            break;
                    }
                    break;
                case "margin":
                    float margin = element.getFloatOrNaN(constraintName);
                    if (Float.isNaN(margin)) {
                        break;
                    } else {
                        reference.margin(Float.valueOf(toPix(state, margin)));
                        break;
                    }
                    break;
                case "contains":
                    CLArray list = element.getArrayOrNull(constraintName);
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            String elementNameReference = list.get(j).content();
                            ConstraintReference elementReference = state.constraints(elementNameReference);
                            reference.add(elementReference);
                        }
                        break;
                    } else {
                        break;
                    }
                    break;
            }
        }
    }

    static void parseWidget(State state, LayoutVariables layoutVariables, String elementName, CLObject element) throws CLParsingException {
        ConstraintReference reference = state.constraints(elementName);
        parseWidget(state, layoutVariables, reference, element);
    }

    static void applyAttribute(State state, LayoutVariables layoutVariables, ConstraintReference reference, CLObject element, String attributeName) throws CLParsingException {
        ConstraintReference targetReference;
        switch (attributeName) {
            case "width":
                reference.setWidth(parseDimension(element, attributeName, state, state.getDpToPixel()));
                break;
            case "height":
                reference.setHeight(parseDimension(element, attributeName, state, state.getDpToPixel()));
                break;
            case "center":
                String target = element.getString(attributeName);
                if (target.equals("parent")) {
                    targetReference = state.constraints(State.PARENT);
                } else {
                    targetReference = state.constraints(target);
                }
                reference.startToStart(targetReference);
                reference.endToEnd(targetReference);
                reference.topToTop(targetReference);
                reference.bottomToBottom(targetReference);
                break;
            case "centerHorizontally":
                String target2 = element.getString(attributeName);
                ConstraintReference targetReference2 = target2.equals("parent") ? state.constraints(State.PARENT) : state.constraints(target2);
                reference.startToStart(targetReference2);
                reference.endToEnd(targetReference2);
                break;
            case "centerVertically":
                String target3 = element.getString(attributeName);
                ConstraintReference targetReference3 = target3.equals("parent") ? state.constraints(State.PARENT) : state.constraints(target3);
                reference.topToTop(targetReference3);
                reference.bottomToBottom(targetReference3);
                break;
            case "alpha":
                float value = layoutVariables.get(element.get(attributeName));
                reference.alpha(value);
                break;
            case "scaleX":
                float value2 = layoutVariables.get(element.get(attributeName));
                reference.scaleX(value2);
                break;
            case "scaleY":
                float value3 = layoutVariables.get(element.get(attributeName));
                reference.scaleY(value3);
                break;
            case "translationX":
                float value4 = layoutVariables.get(element.get(attributeName));
                reference.translationX(toPix(state, value4));
                break;
            case "translationY":
                float value5 = layoutVariables.get(element.get(attributeName));
                reference.translationY(toPix(state, value5));
                break;
            case "translationZ":
                float value6 = layoutVariables.get(element.get(attributeName));
                reference.translationZ(toPix(state, value6));
                break;
            case "pivotX":
                float value7 = layoutVariables.get(element.get(attributeName));
                reference.pivotX(value7);
                break;
            case "pivotY":
                float value8 = layoutVariables.get(element.get(attributeName));
                reference.pivotY(value8);
                break;
            case "rotationX":
                float value9 = layoutVariables.get(element.get(attributeName));
                reference.rotationX(value9);
                break;
            case "rotationY":
                float value10 = layoutVariables.get(element.get(attributeName));
                reference.rotationY(value10);
                break;
            case "rotationZ":
                float value11 = layoutVariables.get(element.get(attributeName));
                reference.rotationZ(value11);
                break;
            case "visibility":
                switch (element.getString(attributeName)) {
                    case "visible":
                        reference.visibility(0);
                        break;
                    case "invisible":
                        reference.visibility(4);
                        reference.alpha(0.0f);
                        break;
                    case "gone":
                        reference.visibility(8);
                        break;
                }
                break;
            case "vBias":
                float value12 = layoutVariables.get(element.get(attributeName));
                reference.verticalBias(value12);
                break;
            case "hRtlBias":
                float value13 = layoutVariables.get(element.get(attributeName));
                if (state.isRtl()) {
                    value13 = 1.0f - value13;
                }
                reference.horizontalBias(value13);
                break;
            case "hBias":
                float value14 = layoutVariables.get(element.get(attributeName));
                reference.horizontalBias(value14);
                break;
            case "vWeight":
                float value15 = layoutVariables.get(element.get(attributeName));
                reference.setVerticalChainWeight(value15);
                break;
            case "hWeight":
                float value16 = layoutVariables.get(element.get(attributeName));
                reference.setHorizontalChainWeight(value16);
                break;
            case "custom":
                parseCustomProperties(element, reference, attributeName);
                break;
            case "motion":
                parseMotionProperties(element.get(attributeName), reference);
                break;
            default:
                parseConstraint(state, layoutVariables, element, reference, attributeName);
                break;
        }
    }

    static void parseWidget(State state, LayoutVariables layoutVariables, ConstraintReference reference, CLObject element) throws CLParsingException {
        if (reference.getWidth() == null) {
            reference.setWidth(Dimension.createWrap());
        }
        if (reference.getHeight() == null) {
            reference.setHeight(Dimension.createWrap());
        }
        ArrayList<String> constraints = element.names();
        if (constraints == null) {
            return;
        }
        for (String constraintName : constraints) {
            applyAttribute(state, layoutVariables, reference, element, constraintName);
        }
    }

    static void parseCustomProperties(CLObject element, ConstraintReference reference, String constraintName) throws CLParsingException {
        ArrayList<String> properties;
        CLObject json = element.getObjectOrNull(constraintName);
        if (json == null || (properties = json.names()) == null) {
            return;
        }
        for (String property : properties) {
            CLElement value = json.get(property);
            if (value instanceof CLNumber) {
                reference.addCustomFloat(property, value.getFloat());
            } else if (value instanceof CLString) {
                long it = parseColorString(value.content());
                if (it != -1) {
                    reference.addCustomColor(property, (int) it);
                }
            }
        }
    }

    private static int indexOf(String val, String... types) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(val)) {
                return i;
            }
        }
        return -1;
    }

    private static void parseMotionProperties(CLElement element, ConstraintReference reference) throws CLParsingException {
        if (!(element instanceof CLObject)) {
            return;
        }
        CLObject obj = (CLObject) element;
        TypedBundle bundle = new TypedBundle();
        ArrayList<String> constraints = obj.names();
        if (constraints == null) {
            return;
        }
        for (String constraintName : constraints) {
            switch (constraintName) {
                case "pathArc":
                    String val = obj.getString(constraintName);
                    int ord = indexOf(val, "none", "startVertical", "startHorizontal", "flip", "below", "above");
                    if (ord == -1) {
                        System.err.println(obj.getLine() + " pathArc = '" + val + "'");
                        break;
                    } else {
                        bundle.add(TypedValues.MotionType.TYPE_PATHMOTION_ARC, ord);
                        break;
                    }
                    break;
                case "relativeTo":
                    bundle.add(TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, obj.getString(constraintName));
                    break;
                case "easing":
                    bundle.add(TypedValues.MotionType.TYPE_EASING, obj.getString(constraintName));
                    break;
                case "stagger":
                    bundle.add(600, obj.getFloat(constraintName));
                    break;
                case "quantize":
                    CLElement quant = obj.get(constraintName);
                    if (quant instanceof CLArray) {
                        CLArray array = (CLArray) quant;
                        int len = array.size();
                        if (len > 0) {
                            bundle.add(TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, array.getInt(0));
                            if (len > 1) {
                                bundle.add(TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_TYPE, array.getString(1));
                                if (len > 2) {
                                    bundle.add(TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE, array.getFloat(2));
                                }
                            }
                        }
                        break;
                    } else {
                        bundle.add(TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, obj.getInt(constraintName));
                        break;
                    }
                    break;
            }
        }
        reference.mMotionProperties = bundle;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void parseConstraint(androidx.constraintlayout.core.state.State r25, androidx.constraintlayout.core.state.ConstraintSetParser.LayoutVariables r26, androidx.constraintlayout.core.parser.CLObject r27, androidx.constraintlayout.core.state.ConstraintReference r28, java.lang.String r29) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            Method dump skipped, instruction units count: 920
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.parseConstraint(androidx.constraintlayout.core.state.State, androidx.constraintlayout.core.state.ConstraintSetParser$LayoutVariables, androidx.constraintlayout.core.parser.CLObject, androidx.constraintlayout.core.state.ConstraintReference, java.lang.String):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static androidx.constraintlayout.core.state.Dimension parseDimensionMode(java.lang.String r5) {
        /*
            r0 = 0
            androidx.constraintlayout.core.state.Dimension r1 = androidx.constraintlayout.core.state.Dimension.createFixed(r0)
            int r2 = r5.hashCode()
            switch(r2) {
                case -1460244870: goto L2d;
                case -995424086: goto L23;
                case -895684237: goto L18;
                case 3657802: goto Ld;
                default: goto Lc;
            }
        Lc:
            goto L37
        Ld:
            java.lang.String r2 = "wrap"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto Lc
            r2 = r0
            goto L38
        L18:
            java.lang.String r2 = "spread"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto Lc
            r2 = 2
            goto L38
        L23:
            java.lang.String r2 = "parent"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto Lc
            r2 = 3
            goto L38
        L2d:
            java.lang.String r2 = "preferWrap"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto Lc
            r2 = 1
            goto L38
        L37:
            r2 = -1
        L38:
            switch(r2) {
                case 0: goto L75;
                case 1: goto L6e;
                case 2: goto L67;
                case 3: goto L62;
                default: goto L3b;
            }
        L3b:
            java.lang.String r2 = "%"
            boolean r2 = r5.endsWith(r2)
            if (r2 == 0) goto L7a
        L44:
            r2 = 37
            int r2 = r5.indexOf(r2)
            java.lang.String r2 = r5.substring(r0, r2)
            float r3 = java.lang.Float.parseFloat(r2)
            r4 = 1120403456(0x42c80000, float:100.0)
            float r3 = r3 / r4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            androidx.constraintlayout.core.state.Dimension r4 = androidx.constraintlayout.core.state.Dimension.createPercent(r4, r3)
            androidx.constraintlayout.core.state.Dimension r1 = r4.suggested(r0)
            goto L8d
        L62:
            androidx.constraintlayout.core.state.Dimension r1 = androidx.constraintlayout.core.state.Dimension.createParent()
            goto L8e
        L67:
            java.lang.Object r0 = androidx.constraintlayout.core.state.Dimension.SPREAD_DIMENSION
            androidx.constraintlayout.core.state.Dimension r1 = androidx.constraintlayout.core.state.Dimension.createSuggested(r0)
            goto L8e
        L6e:
            java.lang.Object r0 = androidx.constraintlayout.core.state.Dimension.WRAP_DIMENSION
            androidx.constraintlayout.core.state.Dimension r1 = androidx.constraintlayout.core.state.Dimension.createSuggested(r0)
            goto L8e
        L75:
            androidx.constraintlayout.core.state.Dimension r1 = androidx.constraintlayout.core.state.Dimension.createWrap()
            goto L8e
        L7a:
            java.lang.String r0 = ":"
            boolean r0 = r5.contains(r0)
            if (r0 == 0) goto L8d
            androidx.constraintlayout.core.state.Dimension r0 = androidx.constraintlayout.core.state.Dimension.createRatio(r5)
            java.lang.Object r2 = androidx.constraintlayout.core.state.Dimension.SPREAD_DIMENSION
            androidx.constraintlayout.core.state.Dimension r1 = r0.suggested(r2)
            goto L8e
        L8d:
        L8e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintSetParser.parseDimensionMode(java.lang.String):androidx.constraintlayout.core.state.Dimension");
    }

    static Dimension parseDimension(CLObject element, String constraintName, State state, CorePixelDp dpToPixels) throws CLParsingException {
        CLElement dimensionElement = element.get(constraintName);
        Dimension dimension = Dimension.createFixed(0);
        if (dimensionElement instanceof CLString) {
            return parseDimensionMode(dimensionElement.content());
        }
        if (dimensionElement instanceof CLNumber) {
            return Dimension.createFixed(state.convertDimension(Float.valueOf(dpToPixels.toPixels(element.getFloat(constraintName)))));
        }
        if (dimensionElement instanceof CLObject) {
            CLObject obj = (CLObject) dimensionElement;
            String mode = obj.getStringOrNull("value");
            if (mode != null) {
                dimension = parseDimensionMode(mode);
            }
            CLElement minEl = obj.getOrNull("min");
            if (minEl != null) {
                if (minEl instanceof CLNumber) {
                    float min = ((CLNumber) minEl).getFloat();
                    dimension.min(state.convertDimension(Float.valueOf(dpToPixels.toPixels(min))));
                } else if (minEl instanceof CLString) {
                    dimension.min(Dimension.WRAP_DIMENSION);
                }
            }
            CLElement maxEl = obj.getOrNull("max");
            if (maxEl != null) {
                if (maxEl instanceof CLNumber) {
                    float max = ((CLNumber) maxEl).getFloat();
                    dimension.max(state.convertDimension(Float.valueOf(dpToPixels.toPixels(max))));
                    return dimension;
                }
                if (maxEl instanceof CLString) {
                    dimension.max(Dimension.WRAP_DIMENSION);
                    return dimension;
                }
                return dimension;
            }
            return dimension;
        }
        return dimension;
    }

    static long parseColorString(String value) {
        if (value.startsWith("#")) {
            String str = value.substring(1);
            if (str.length() == 6) {
                str = "FF" + str;
            }
            return Long.parseLong(str, 16);
        }
        return -1L;
    }

    static String lookForType(CLObject element) throws CLParsingException {
        ArrayList<String> constraints = element.names();
        for (String constraintName : constraints) {
            if (constraintName.equals("type")) {
                return element.getString("type");
            }
        }
        return null;
    }
}
