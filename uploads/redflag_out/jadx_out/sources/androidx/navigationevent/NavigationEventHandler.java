package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.navigationevent.NavigationEventInfo;
import androidx.navigationevent.NavigationEventTransitionState;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NavigationEventHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0018\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tB\u0019\b\u0016\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\nJ\u0006\u0010$\u001a\u00020%J5\u0010&\u001a\u00020%2\u0006\u0010\f\u001a\u00028\u00002\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H\u0007¢\u0006\u0002\u0010'J\u0015\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0000¢\u0006\u0002\b+J\u0010\u0010,\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0015J\u0015\u0010-\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0000¢\u0006\u0002\b.J\u0010\u0010/\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0015J\r\u00100\u001a\u00020%H\u0000¢\u0006\u0002\b1J\b\u00102\u001a\u00020%H\u0015J\r\u00103\u001a\u00020%H\u0000¢\u0006\u0002\b4J\b\u00105\u001a\u00020%H\u0015J\u0015\u00106\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0000¢\u0006\u0002\b7J\u0010\u00108\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0015J\u0015\u00109\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0000¢\u0006\u0002\b:J\u0010\u0010;\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0015J\r\u0010<\u001a\u00020%H\u0000¢\u0006\u0002\b=J\b\u0010>\u001a\u00020%H\u0015J\r\u0010?\u001a\u00020%H\u0000¢\u0006\u0002\b@J\b\u0010A\u001a\u00020%H\u0015R \u0010\f\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00028\u0000@BX\u0086\u000e¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR*\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u001a\"\u0004\b\u001b\u0010\u001cR&\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\u001a\"\u0004\b\u001d\u0010\u001cR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006B"}, d2 = {"Landroidx/navigationevent/NavigationEventHandler;", "T", "Landroidx/navigationevent/NavigationEventInfo;", "", "initialInfo", "isBackEnabled", "", "isForwardEnabled", "<init>", "(Landroidx/navigationevent/NavigationEventInfo;ZZ)V", "(Landroidx/navigationevent/NavigationEventInfo;Z)V", "value", "currentInfo", "getCurrentInfo", "()Landroidx/navigationevent/NavigationEventInfo;", "Landroidx/navigationevent/NavigationEventInfo;", "", "backInfo", "getBackInfo", "()Ljava/util/List;", "forwardInfo", "getForwardInfo", "Landroidx/navigationevent/NavigationEventTransitionState;", "transitionState", "getTransitionState", "()Landroidx/navigationevent/NavigationEventTransitionState;", "()Z", "setBackEnabled", "(Z)V", "setForwardEnabled", "dispatcher", "Landroidx/navigationevent/NavigationEventDispatcher;", "getDispatcher$navigationevent", "()Landroidx/navigationevent/NavigationEventDispatcher;", "setDispatcher$navigationevent", "(Landroidx/navigationevent/NavigationEventDispatcher;)V", "remove", "", "setInfo", "(Landroidx/navigationevent/NavigationEventInfo;Ljava/util/List;Ljava/util/List;)V", "doOnBackStarted", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "doOnBackStarted$navigationevent", "onBackStarted", "doOnBackProgressed", "doOnBackProgressed$navigationevent", "onBackProgressed", "doOnBackCompleted", "doOnBackCompleted$navigationevent", "onBackCompleted", "doOnBackCancelled", "doOnBackCancelled$navigationevent", "onBackCancelled", "doOnForwardStarted", "doOnForwardStarted$navigationevent", "onForwardStarted", "doOnForwardProgressed", "doOnForwardProgressed$navigationevent", "onForwardProgressed", "doOnForwardCompleted", "doOnForwardCompleted$navigationevent", "onForwardCompleted", "doOnForwardCancelled", "doOnForwardCancelled$navigationevent", "onForwardCancelled", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class NavigationEventHandler<T extends NavigationEventInfo> {
    private List<? extends T> backInfo;
    private T currentInfo;
    private NavigationEventDispatcher dispatcher;
    private List<? extends T> forwardInfo;
    private boolean isBackEnabled;
    private boolean isForwardEnabled;
    private NavigationEventTransitionState transitionState;

    public final void setInfo(T currentInfo) {
        Intrinsics.checkNotNullParameter(currentInfo, "currentInfo");
        setInfo$default(this, currentInfo, null, null, 6, null);
    }

    public final void setInfo(T currentInfo, List<? extends T> backInfo) {
        Intrinsics.checkNotNullParameter(currentInfo, "currentInfo");
        Intrinsics.checkNotNullParameter(backInfo, "backInfo");
        setInfo$default(this, currentInfo, backInfo, null, 4, null);
    }

    public NavigationEventHandler(T initialInfo, boolean isBackEnabled, boolean isForwardEnabled) {
        Intrinsics.checkNotNullParameter(initialInfo, "initialInfo");
        this.currentInfo = initialInfo;
        this.backInfo = CollectionsKt.emptyList();
        this.forwardInfo = CollectionsKt.emptyList();
        this.transitionState = NavigationEventTransitionState.Idle.INSTANCE;
        this.isBackEnabled = isBackEnabled;
        this.isForwardEnabled = isForwardEnabled;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NavigationEventHandler(T initialInfo, boolean isBackEnabled) {
        this(initialInfo, isBackEnabled, false);
        Intrinsics.checkNotNullParameter(initialInfo, "initialInfo");
    }

    public final T getCurrentInfo() {
        return this.currentInfo;
    }

    public final List<T> getBackInfo() {
        return this.backInfo;
    }

    public final List<T> getForwardInfo() {
        return this.forwardInfo;
    }

    public final NavigationEventTransitionState getTransitionState() {
        return this.transitionState;
    }

    public final boolean isBackEnabled() {
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if ((navigationEventDispatcher == null || navigationEventDispatcher.isEnabled()) ? false : true) {
            return false;
        }
        return this.isBackEnabled;
    }

    public final void setBackEnabled(boolean value) {
        NavigationEventProcessor sharedProcessor;
        if (this.isBackEnabled == value) {
            return;
        }
        this.isBackEnabled = value;
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if (navigationEventDispatcher == null || (sharedProcessor = navigationEventDispatcher.getSharedProcessor()) == null) {
            return;
        }
        sharedProcessor.refreshEnabledHandlers();
    }

    public final boolean isForwardEnabled() {
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if ((navigationEventDispatcher == null || navigationEventDispatcher.isEnabled()) ? false : true) {
            return false;
        }
        return this.isForwardEnabled;
    }

    public final void setForwardEnabled(boolean value) {
        NavigationEventProcessor sharedProcessor;
        if (this.isForwardEnabled == value) {
            return;
        }
        this.isForwardEnabled = value;
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if (navigationEventDispatcher == null || (sharedProcessor = navigationEventDispatcher.getSharedProcessor()) == null) {
            return;
        }
        sharedProcessor.refreshEnabledHandlers();
    }

    /* JADX INFO: renamed from: getDispatcher$navigationevent, reason: from getter */
    public final NavigationEventDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public final void setDispatcher$navigationevent(NavigationEventDispatcher navigationEventDispatcher) {
        this.dispatcher = navigationEventDispatcher;
    }

    public final void remove() {
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if (navigationEventDispatcher != null) {
            navigationEventDispatcher.removeHandler$navigationevent(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void setInfo$default(NavigationEventHandler navigationEventHandler, NavigationEventInfo navigationEventInfo, List list, List list2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setInfo");
        }
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        navigationEventHandler.setInfo(navigationEventInfo, list, list2);
    }

    public final void setInfo(T currentInfo, List<? extends T> backInfo, List<? extends T> forwardInfo) {
        NavigationEventProcessor sharedProcessor;
        Intrinsics.checkNotNullParameter(currentInfo, "currentInfo");
        Intrinsics.checkNotNullParameter(backInfo, "backInfo");
        Intrinsics.checkNotNullParameter(forwardInfo, "forwardInfo");
        this.currentInfo = currentInfo;
        this.backInfo = backInfo;
        this.forwardInfo = forwardInfo;
        NavigationEventDispatcher navigationEventDispatcher = this.dispatcher;
        if (navigationEventDispatcher == null || (sharedProcessor = navigationEventDispatcher.getSharedProcessor()) == null) {
            return;
        }
        sharedProcessor.updateEnabledHandlerInfo$navigationevent(this);
    }

    public final void doOnBackStarted$navigationevent(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.transitionState = new NavigationEventTransitionState.InProgress(event, -1);
        onBackStarted(event);
    }

    protected void onBackStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public final void doOnBackProgressed$navigationevent(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.transitionState = new NavigationEventTransitionState.InProgress(event, -1);
        onBackProgressed(event);
    }

    protected void onBackProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public final void doOnBackCompleted$navigationevent() {
        this.transitionState = NavigationEventTransitionState.Idle.INSTANCE;
        onBackCompleted();
    }

    protected void onBackCompleted() {
        throw new UnsupportedOperationException("A handler that receives a 'backCompleted' event must override 'onBackCompleted()' to handle the callback.");
    }

    public final void doOnBackCancelled$navigationevent() {
        this.transitionState = NavigationEventTransitionState.Idle.INSTANCE;
        onBackCancelled();
    }

    protected void onBackCancelled() {
    }

    public final void doOnForwardStarted$navigationevent(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.transitionState = new NavigationEventTransitionState.InProgress(event, 1);
        onForwardStarted(event);
    }

    protected void onForwardStarted(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public final void doOnForwardProgressed$navigationevent(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.transitionState = new NavigationEventTransitionState.InProgress(event, 1);
        onForwardProgressed(event);
    }

    protected void onForwardProgressed(NavigationEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public final void doOnForwardCompleted$navigationevent() {
        this.transitionState = NavigationEventTransitionState.Idle.INSTANCE;
        onForwardCompleted();
    }

    protected void onForwardCompleted() {
        throw new UnsupportedOperationException("A handler that receives a 'forwardCompleted' event must override 'onForwardCompleted()' to handle the callback.");
    }

    public final void doOnForwardCancelled$navigationevent() {
        this.transitionState = NavigationEventTransitionState.Idle.INSTANCE;
        onForwardCancelled();
    }

    protected void onForwardCancelled() {
    }
}
