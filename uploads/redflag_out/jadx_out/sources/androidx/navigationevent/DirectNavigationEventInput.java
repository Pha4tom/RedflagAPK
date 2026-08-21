package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DirectNavigationEventInput.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\b\u0010\t\u001a\u00020\u0005H\u0007J\b\u0010\n\u001a\u00020\u0005H\u0007J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\b\u0010\r\u001a\u00020\u0005H\u0007J\b\u0010\u000e\u001a\u00020\u0005H\u0007¨\u0006\u000f"}, d2 = {"Landroidx/navigationevent/DirectNavigationEventInput;", "Landroidx/navigationevent/NavigationEventInput;", "<init>", "()V", "backStarted", "", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "backProgressed", "backCancelled", "backCompleted", "forwardStarted", "forwardProgressed", "forwardCancelled", "forwardCompleted", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class DirectNavigationEventInput extends NavigationEventInput {
    public final void backStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        dispatchOnBackStarted(event);
    }

    public final void backProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        dispatchOnBackProgressed(event);
    }

    public final void backCancelled() {
        dispatchOnBackCancelled();
    }

    public final void backCompleted() {
        dispatchOnBackCompleted();
    }

    public final void forwardStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        dispatchOnForwardStarted(event);
    }

    public final void forwardProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        dispatchOnForwardProgressed(event);
    }

    public final void forwardCancelled() {
        dispatchOnForwardCancelled();
    }

    public final void forwardCompleted() {
        dispatchOnForwardCompleted();
    }
}
