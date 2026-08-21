package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NavigationEventInput.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u0001¢\u0006\u0002\b\u000fJ\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u0015J\r\u0010\u0011\u001a\u00020\u000eH\u0001¢\u0006\u0002\b\u0012J\b\u0010\u0013\u001a\u00020\u000eH\u0015J\u0015\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000bH\u0001¢\u0006\u0002\b\u0016J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000bH\u0015J\u0015\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0001¢\u0006\u0002\b\u001bJ\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0015J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0005J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0005J\b\u0010!\u001a\u00020\u000eH\u0005J\b\u0010\"\u001a\u00020\u000eH\u0005J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0005J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0005J\b\u0010%\u001a\u00020\u000eH\u0005J\b\u0010&\u001a\u00020\u000eH\u0005R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Landroidx/navigationevent/NavigationEventInput;", "", "<init>", "()V", "dispatcher", "Landroidx/navigationevent/NavigationEventDispatcher;", "getDispatcher$navigationevent", "()Landroidx/navigationevent/NavigationEventDispatcher;", "setDispatcher$navigationevent", "(Landroidx/navigationevent/NavigationEventDispatcher;)V", "isPredictiveBackInProgress", "", "isPredictiveForwardInProgress", "doOnAdded", "", "doOnAdded$navigationevent", "onAdded", "doOnRemoved", "doOnRemoved$navigationevent", "onRemoved", "doOnHasEnabledHandlersChanged", "hasEnabledHandlers", "doOnHasEnabledHandlersChanged$navigationevent", "onHasEnabledHandlersChanged", "doOnHistoryChanged", "history", "Landroidx/navigationevent/NavigationEventHistory;", "doOnHistoryChanged$navigationevent", "onHistoryChanged", "dispatchOnBackStarted", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "dispatchOnBackProgressed", "dispatchOnBackCancelled", "dispatchOnBackCompleted", "dispatchOnForwardStarted", "dispatchOnForwardProgressed", "dispatchOnForwardCancelled", "dispatchOnForwardCompleted", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class NavigationEventInput {
    private NavigationEventDispatcher dispatcher;
    private boolean isPredictiveBackInProgress;
    private boolean isPredictiveForwardInProgress;

    /* JADX INFO: renamed from: getDispatcher$navigationevent, reason: from getter */
    public final NavigationEventDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public final void setDispatcher$navigationevent(NavigationEventDispatcher navigationEventDispatcher) {
        this.dispatcher = navigationEventDispatcher;
    }

    public final void doOnAdded$navigationevent(NavigationEventDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        onAdded(dispatcher);
    }

    protected void onAdded(NavigationEventDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
    }

    public final void doOnRemoved$navigationevent() {
        onRemoved();
    }

    protected void onRemoved() {
    }

    public final void doOnHasEnabledHandlersChanged$navigationevent(boolean hasEnabledHandlers) {
        onHasEnabledHandlersChanged(hasEnabledHandlers);
    }

    protected void onHasEnabledHandlersChanged(boolean hasEnabledHandlers) {
    }

    public final void doOnHistoryChanged$navigationevent(NavigationEventHistory history) {
        Intrinsics.checkNotNullParameter(history, "history");
        onHistoryChanged(history);
    }

    protected void onHistoryChanged(NavigationEventHistory history) {
        Intrinsics.checkNotNullParameter(history, "history");
    }

    protected final void dispatchOnBackStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveBackInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, -1, event);
            this.isPredictiveBackInProgress = true;
        }
    }

    protected final void dispatchOnBackProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (this.isPredictiveBackInProgress) {
            dispatcher.dispatchOnProgressed$navigationevent(this, -1, event);
        }
    }

    protected final void dispatchOnBackCancelled() {
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveBackInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, -1, null);
        }
        dispatcher.dispatchOnCancelled$navigationevent(this, -1);
        this.isPredictiveBackInProgress = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void dispatchOnBackCompleted() {
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveBackInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, -1, null);
        }
        dispatcher.dispatchOnCompleted$navigationevent(this, -1);
        this.isPredictiveBackInProgress = false;
    }

    protected final void dispatchOnForwardStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveForwardInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, 1, event);
            this.isPredictiveForwardInProgress = true;
        }
    }

    protected final void dispatchOnForwardProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (this.isPredictiveForwardInProgress) {
            dispatcher.dispatchOnProgressed$navigationevent(this, 1, event);
        }
    }

    protected final void dispatchOnForwardCancelled() {
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveForwardInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, 1, null);
        }
        dispatcher.dispatchOnCancelled$navigationevent(this, 1);
        this.isPredictiveForwardInProgress = false;
    }

    protected final void dispatchOnForwardCompleted() {
        NavigationEventDispatcher dispatcher = this.dispatcher;
        if (dispatcher == null) {
            throw new IllegalStateException("This input is not added to any dispatcher.".toString());
        }
        if (!this.isPredictiveForwardInProgress) {
            dispatcher.dispatchOnStarted$navigationevent(this, 1, null);
        }
        dispatcher.dispatchOnCompleted$navigationevent(this, 1);
        this.isPredictiveForwardInProgress = false;
    }
}
