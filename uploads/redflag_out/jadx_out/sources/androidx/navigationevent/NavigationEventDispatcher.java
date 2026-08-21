package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: NavigationEventDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 =2\u00020\u0001:\u0002<=B\u001d\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\t\b\u0016¢\u0006\u0004\b\u0005\u0010\u0007B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\bB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001e\u0010%\u001a\u00020&2\n\u0010'\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\b\u0002\u0010(\u001a\u00020)H\u0007J\u0019\u0010*\u001a\u00020&2\n\u0010'\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0000¢\u0006\u0002\b+J\u0010\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001cH\u0007J\u0018\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010.\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001cH\u0007J'\u0010/\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001c2\u0006\u00100\u001a\u00020)2\b\u00101\u001a\u0004\u0018\u000102H\u0000¢\u0006\u0002\b3J%\u00104\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001c2\u0006\u00100\u001a\u00020)2\u0006\u00101\u001a\u000202H\u0000¢\u0006\u0002\b5J\u001d\u00106\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001c2\u0006\u00100\u001a\u00020)H\u0000¢\u0006\u0002\b7J\u001d\u00108\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001c2\u0006\u00100\u001a\u00020)H\u0000¢\u0006\u0002\b9J\b\u0010:\u001a\u00020&H\u0007J\b\u0010;\u001a\u00020&H\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u00020\u000b8BX\u0082\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\fR&\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b8F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\f\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00000\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8F¢\u0006\u0006\u001a\u0004\b \u0010!R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001e8F¢\u0006\u0006\u001a\u0004\b$\u0010!¨\u0006>"}, d2 = {"Landroidx/navigationevent/NavigationEventDispatcher;", "", "parent", "onBackCompletedFallback", "Landroidx/navigationevent/OnBackCompletedFallback;", "<init>", "(Landroidx/navigationevent/NavigationEventDispatcher;Landroidx/navigationevent/OnBackCompletedFallback;)V", "()V", "(Landroidx/navigationevent/OnBackCompletedFallback;)V", "(Landroidx/navigationevent/NavigationEventDispatcher;)V", "isDisposed", "", "()Z", "value", "isEnabled", "setEnabled", "(Z)V", "sharedProcessor", "Landroidx/navigationevent/NavigationEventProcessor;", "getSharedProcessor$navigationevent", "()Landroidx/navigationevent/NavigationEventProcessor;", "childDispatchers", "", "getChildDispatchers$navigationevent", "()Ljava/util/Set;", "handlers", "Landroidx/navigationevent/NavigationEventHandler;", "inputs", "Landroidx/navigationevent/NavigationEventInput;", "transitionState", "Lkotlinx/coroutines/flow/StateFlow;", "Landroidx/navigationevent/NavigationEventTransitionState;", "getTransitionState", "()Lkotlinx/coroutines/flow/StateFlow;", "history", "Landroidx/navigationevent/NavigationEventHistory;", "getHistory", "addHandler", "", "handler", "priority", "", "removeHandler", "removeHandler$navigationevent", "addInput", "input", "removeInput", "dispatchOnStarted", "direction", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "dispatchOnStarted$navigationevent", "dispatchOnProgressed", "dispatchOnProgressed$navigationevent", "dispatchOnCompleted", "dispatchOnCompleted$navigationevent", "dispatchOnCancelled", "dispatchOnCancelled$navigationevent", "dispose", "checkInvariants", "Priority", "Companion", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NavigationEventDispatcher {
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_OVERLAY = 0;
    private final Set<NavigationEventDispatcher> childDispatchers;
    private final Set<NavigationEventHandler<?>> handlers;
    private final Set<NavigationEventInput> inputs;
    private boolean isDisposed;
    private boolean isEnabled;
    private final OnBackCompletedFallback onBackCompletedFallback;
    private NavigationEventDispatcher parent;
    private final NavigationEventProcessor sharedProcessor;

    /* JADX INFO: compiled from: NavigationEventDispatcher.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/navigationevent/NavigationEventDispatcher$Priority;", "", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface Priority {
    }

    public final void addHandler(NavigationEventHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        addHandler$default(this, handler, 0, 2, null);
    }

    private NavigationEventDispatcher(NavigationEventDispatcher parent, OnBackCompletedFallback onBackCompletedFallback) {
        NavigationEventProcessor navigationEventProcessor;
        this.parent = parent;
        this.onBackCompletedFallback = onBackCompletedFallback;
        this.isEnabled = true;
        NavigationEventDispatcher navigationEventDispatcher = this.parent;
        this.sharedProcessor = (navigationEventDispatcher == null || (navigationEventProcessor = navigationEventDispatcher.sharedProcessor) == null) ? new NavigationEventProcessor() : navigationEventProcessor;
        this.childDispatchers = new LinkedHashSet();
        this.handlers = new LinkedHashSet();
        this.inputs = new LinkedHashSet();
        NavigationEventDispatcher navigationEventDispatcher2 = this.parent;
        if (navigationEventDispatcher2 != null) {
            navigationEventDispatcher2.childDispatchers.add(this);
        }
    }

    public NavigationEventDispatcher() {
        this(null, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NavigationEventDispatcher(OnBackCompletedFallback onBackCompletedFallback) {
        this(null, onBackCompletedFallback);
        Intrinsics.checkNotNullParameter(onBackCompletedFallback, "onBackCompletedFallback");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NavigationEventDispatcher(NavigationEventDispatcher parent) {
        this(parent, null);
        Intrinsics.checkNotNullParameter(parent, "parent");
    }

    private final boolean isDisposed() {
        NavigationEventDispatcher navigationEventDispatcher = this.parent;
        boolean z = false;
        if (navigationEventDispatcher != null && navigationEventDispatcher.isDisposed()) {
            z = true;
        }
        if (z) {
            return true;
        }
        return this.isDisposed;
    }

    public final boolean isEnabled() {
        NavigationEventDispatcher navigationEventDispatcher = this.parent;
        if ((navigationEventDispatcher == null || navigationEventDispatcher.isEnabled()) ? false : true) {
            return false;
        }
        return this.isEnabled;
    }

    public final void setEnabled(boolean value) {
        checkInvariants();
        if (this.isEnabled == value) {
            return;
        }
        this.isEnabled = value;
        this.sharedProcessor.refreshEnabledHandlers();
    }

    /* JADX INFO: renamed from: getSharedProcessor$navigationevent, reason: from getter */
    public final NavigationEventProcessor getSharedProcessor() {
        return this.sharedProcessor;
    }

    public final Set<NavigationEventDispatcher> getChildDispatchers$navigationevent() {
        return this.childDispatchers;
    }

    public final StateFlow<NavigationEventTransitionState> getTransitionState() {
        return this.sharedProcessor.getTransitionState();
    }

    public final StateFlow<NavigationEventHistory> getHistory() {
        return this.sharedProcessor.getHistory();
    }

    public static /* synthetic */ void addHandler$default(NavigationEventDispatcher navigationEventDispatcher, NavigationEventHandler navigationEventHandler, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1;
        }
        navigationEventDispatcher.addHandler(navigationEventHandler, i);
    }

    public final void addHandler(NavigationEventHandler<?> handler, int priority) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        checkInvariants();
        if (this.handlers.add(handler)) {
            this.sharedProcessor.addHandler(this, handler, priority);
        }
    }

    public final void removeHandler$navigationevent(NavigationEventHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        if (this.handlers.remove(handler)) {
            this.sharedProcessor.removeHandler(handler);
        }
    }

    public final void addInput(NavigationEventInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        if (this.inputs.add(input)) {
            this.sharedProcessor.addInput(this, input, -1);
        }
    }

    public final void addInput(NavigationEventInput input, int priority) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        boolean z = true;
        if (priority != 1 && priority != 0) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(("Unsupported priority value: " + priority).toString());
        }
        if (this.inputs.add(input)) {
            this.sharedProcessor.addInput(this, input, priority);
        }
    }

    public final void removeInput(NavigationEventInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        if (this.inputs.remove(input)) {
            this.sharedProcessor.removeInput(input);
        }
    }

    public final void dispatchOnStarted$navigationevent(NavigationEventInput input, int direction, NavigationEvent event) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        if (isEnabled()) {
            this.sharedProcessor.dispatchOnStarted(input, direction, event);
        }
    }

    public final void dispatchOnProgressed$navigationevent(NavigationEventInput input, int direction, NavigationEvent event) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(event, "event");
        checkInvariants();
        if (isEnabled()) {
            this.sharedProcessor.dispatchOnProgressed(input, direction, event);
        }
    }

    public final void dispatchOnCompleted$navigationevent(NavigationEventInput input, int direction) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        if (isEnabled()) {
            this.sharedProcessor.dispatchOnCompleted(input, direction, this.onBackCompletedFallback);
        }
    }

    public final void dispatchOnCancelled$navigationevent(NavigationEventInput input, int direction) {
        Intrinsics.checkNotNullParameter(input, "input");
        checkInvariants();
        if (isEnabled()) {
            this.sharedProcessor.dispatchOnCancelled(input, direction);
        }
    }

    public final void dispose() {
        Set<NavigationEventDispatcher> set;
        checkInvariants();
        this.isDisposed = true;
        ArrayDeque dispatchersToDispose = new ArrayDeque();
        dispatchersToDispose.add(this);
        while (!dispatchersToDispose.isEmpty()) {
            NavigationEventDispatcher currentDispatcher = (NavigationEventDispatcher) dispatchersToDispose.removeFirst();
            currentDispatcher.isDisposed = true;
            CollectionsKt.addAll(dispatchersToDispose, currentDispatcher.childDispatchers);
            for (NavigationEventInput input : CollectionsKt.toList(currentDispatcher.inputs)) {
                this.sharedProcessor.removeInput(input);
            }
            currentDispatcher.inputs.clear();
            for (NavigationEventHandler handler : CollectionsKt.toList(currentDispatcher.handlers)) {
                handler.remove();
            }
            currentDispatcher.handlers.clear();
            currentDispatcher.childDispatchers.clear();
            NavigationEventDispatcher navigationEventDispatcher = currentDispatcher.parent;
            if (navigationEventDispatcher != null && (set = navigationEventDispatcher.childDispatchers) != null) {
                set.remove(currentDispatcher);
            }
            currentDispatcher.parent = null;
        }
    }

    private final void checkInvariants() {
        if (isDisposed()) {
            throw new IllegalStateException("This NavigationEventDispatcher has already been disposed and cannot be used.".toString());
        }
    }
}
