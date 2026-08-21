package androidx.constraintlayout.core.dsl;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MotionScene {
    ArrayList<Transition> mTransitions = new ArrayList<>();
    ArrayList<ConstraintSet> mConstraintSets = new ArrayList<>();

    public void addTransition(Transition transition) {
        this.mTransitions.add(transition);
    }

    public void addConstraintSet(ConstraintSet constraintSet) {
        this.mConstraintSets.add(constraintSet);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("{\n");
        if (!this.mTransitions.isEmpty()) {
            ret.append("Transitions:{\n");
            for (Transition transition : this.mTransitions) {
                ret.append(transition.toString());
            }
            ret.append("},\n");
        }
        if (!this.mConstraintSets.isEmpty()) {
            ret.append("ConstraintSets:{\n");
            for (ConstraintSet constraintSet : this.mConstraintSets) {
                ret.append(constraintSet.toString());
            }
            ret.append("},\n");
        }
        ret.append("}\n");
        return ret.toString();
    }
}
