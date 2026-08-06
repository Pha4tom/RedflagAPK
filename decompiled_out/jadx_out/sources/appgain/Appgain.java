package appgain;

import go.Seq;

/* JADX INFO: loaded from: classes.dex */
public abstract class Appgain {
    static {
        Seq.touch();
        _init();
    }

    private Appgain() {
    }

    private static native void _init();

    public static native void run(String str);

    public static native void stop(String str);

    public static void touch() {
    }
}
