package androidx.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;

/* JADX INFO: compiled from: ActivityFlags.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0018\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0003¨\u0006\u0007"}, d2 = {"Landroidx/activity/ActivityFlags;", "", "<init>", "()V", "isOnBackPressedLifecycleOrderMaintained", "", "isOnBackPressedLifecycleOrderMaintained$annotations", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ActivityFlags {
    public static final ActivityFlags INSTANCE = new ActivityFlags();
    public static boolean isOnBackPressedLifecycleOrderMaintained = true;

    public static /* synthetic */ void isOnBackPressedLifecycleOrderMaintained$annotations() {
    }

    private ActivityFlags() {
    }
}
