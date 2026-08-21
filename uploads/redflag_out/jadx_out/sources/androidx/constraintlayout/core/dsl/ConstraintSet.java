package androidx.constraintlayout.core.dsl;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintSet {
    ArrayList<Constraint> mConstraints = new ArrayList<>();
    ArrayList<Helper> mHelpers = new ArrayList<>();
    private final String mName;

    public ConstraintSet(String name) {
        this.mName = name;
    }

    public void add(Constraint c) {
        this.mConstraints.add(c);
    }

    public void add(Helper h) {
        this.mHelpers.add(h);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(this.mName + ":{\n");
        if (!this.mConstraints.isEmpty()) {
            for (Constraint cs : this.mConstraints) {
                ret.append(cs.toString());
            }
        }
        if (!this.mHelpers.isEmpty()) {
            for (Helper h : this.mHelpers) {
                ret.append(h.toString());
            }
        }
        ret.append("},\n");
        return ret.toString();
    }
}
