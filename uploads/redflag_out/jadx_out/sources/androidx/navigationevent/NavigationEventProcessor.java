package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.navigationevent.NavigationEventTransitionState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: NavigationEventProcessor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010!\u001a\u00020\"J\u001b\u0010#\u001a\u00020\"2\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0000¢\u0006\u0002\b%J$\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u00112\b\b\u0002\u0010)\u001a\u00020\u0015J\u0012\u0010*\u001a\u00020\"2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u0011J\u001e\u0010+\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\u0006\u0010,\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u0015J\u000e\u0010-\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u0018J\"\u0010.\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00152\n\b\u0002\u00100\u001a\u0004\u0018\u000101J\u001e\u00102\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u000201J \u00103\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00152\b\u00104\u001a\u0004\u0018\u000105J\u0016\u00106\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0015J\u0018\u00107\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\b\b\u0002\u0010/\u001a\u00020\u0015H\u0002J'\u00108\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\u0016\u00109\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011\u0012\u0004\u0012\u00020\u001e0:H\u0082\bJ\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020=0<H\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0018\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0016\u0010\u0003R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00180\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Landroidx/navigationevent/NavigationEventProcessor;", "", "<init>", "()V", "_transitionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroidx/navigationevent/NavigationEventTransitionState;", "transitionState", "Lkotlinx/coroutines/flow/StateFlow;", "getTransitionState", "()Lkotlinx/coroutines/flow/StateFlow;", "_history", "Landroidx/navigationevent/NavigationEventHistory;", "history", "getHistory", "overlayHandlers", "Lkotlin/collections/ArrayDeque;", "Landroidx/navigationevent/NavigationEventHandler;", "defaultHandlers", "inProgressHandler", "inProgressDirection", "", "getInProgressDirection$annotations", "inProgressInput", "Landroidx/navigationevent/NavigationEventInput;", "unspecifiedInputs", "", "defaultInputs", "overlayInputs", "hasEnabledDefaultHandlers", "", "hasEnabledOverlayHandlers", "hasEnabledAnyHandlers", "refreshEnabledHandlers", "", "updateEnabledHandlerInfo", "handler", "updateEnabledHandlerInfo$navigationevent", "addHandler", "dispatcher", "Landroidx/navigationevent/NavigationEventDispatcher;", "priority", "removeHandler", "addInput", "input", "removeInput", "dispatchOnStarted", "direction", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "dispatchOnProgressed", "dispatchOnCompleted", "onBackCompletedFallback", "Landroidx/navigationevent/OnBackCompletedFallback;", "dispatchOnCancelled", "resolveEnabledHandler", "findHandler", "predicate", "Lkotlin/Function1;", "resolveCombinedBackInfo", "", "Landroidx/navigationevent/NavigationEventInfo;", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NavigationEventProcessor {
    private boolean hasEnabledAnyHandlers;
    private boolean hasEnabledDefaultHandlers;
    private boolean hasEnabledOverlayHandlers;
    private int inProgressDirection;
    private NavigationEventHandler<?> inProgressHandler;
    private NavigationEventInput inProgressInput;
    private final MutableStateFlow<NavigationEventTransitionState> _transitionState = StateFlowKt.MutableStateFlow(NavigationEventTransitionState.Idle.INSTANCE);
    private final StateFlow<NavigationEventTransitionState> transitionState = FlowKt.asStateFlow(this._transitionState);
    private final MutableStateFlow<NavigationEventHistory> _history = StateFlowKt.MutableStateFlow(new NavigationEventHistory());
    private final StateFlow<NavigationEventHistory> history = FlowKt.asStateFlow(this._history);
    private final ArrayDeque<NavigationEventHandler<?>> overlayHandlers = new ArrayDeque<>();
    private final ArrayDeque<NavigationEventHandler<?>> defaultHandlers = new ArrayDeque<>();
    private final Set<NavigationEventInput> unspecifiedInputs = new LinkedHashSet();
    private final Set<NavigationEventInput> defaultInputs = new LinkedHashSet();
    private final Set<NavigationEventInput> overlayInputs = new LinkedHashSet();

    private static /* synthetic */ void getInProgressDirection$annotations() {
    }

    public final StateFlow<NavigationEventTransitionState> getTransitionState() {
        return this.transitionState;
    }

    public final StateFlow<NavigationEventHistory> getHistory() {
        return this.history;
    }

    public final void refreshEnabledHandlers() {
        boolean newOverlayEnabled;
        boolean newDefaultEnabled;
        Iterable $this$any$iv = this.overlayHandlers;
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator<NavigationEventHandler<?>> it = $this$any$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    NavigationEventHandler<?> navigationEventHandler = (NavigationEventHandler) element$iv;
                    if (navigationEventHandler.isBackEnabled() || navigationEventHandler.isForwardEnabled()) {
                        newOverlayEnabled = true;
                        break;
                    }
                } else {
                    newOverlayEnabled = false;
                    break;
                }
            }
        } else {
            newOverlayEnabled = false;
        }
        Iterable $this$any$iv2 = this.defaultHandlers;
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator<NavigationEventHandler<?>> it2 = $this$any$iv2.iterator();
            while (true) {
                if (it2.hasNext()) {
                    Object element$iv2 = it2.next();
                    NavigationEventHandler<?> navigationEventHandler2 = (NavigationEventHandler) element$iv2;
                    if (navigationEventHandler2.isBackEnabled() || navigationEventHandler2.isForwardEnabled()) {
                        newDefaultEnabled = true;
                        break;
                    }
                } else {
                    newDefaultEnabled = false;
                    break;
                }
            }
        } else {
            newDefaultEnabled = false;
        }
        boolean newAnyEnabled = newOverlayEnabled || newDefaultEnabled;
        boolean overlayEnabledChanged = this.hasEnabledOverlayHandlers != newOverlayEnabled;
        boolean defaultEnabledChanged = this.hasEnabledDefaultHandlers != newDefaultEnabled;
        boolean anyEnabledChanged = this.hasEnabledAnyHandlers != newAnyEnabled;
        if (overlayEnabledChanged) {
            for (NavigationEventInput input : this.overlayInputs) {
                input.doOnHasEnabledHandlersChanged$navigationevent(newOverlayEnabled);
            }
        }
        if (defaultEnabledChanged) {
            for (NavigationEventInput input2 : this.defaultInputs) {
                input2.doOnHasEnabledHandlersChanged$navigationevent(newDefaultEnabled);
            }
        }
        if (anyEnabledChanged) {
            for (NavigationEventInput input3 : this.unspecifiedInputs) {
                input3.doOnHasEnabledHandlersChanged$navigationevent(newAnyEnabled);
            }
        }
        this.hasEnabledOverlayHandlers = newOverlayEnabled;
        this.hasEnabledDefaultHandlers = newDefaultEnabled;
        this.hasEnabledAnyHandlers = newAnyEnabled;
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler$default = this.inProgressHandler;
        if (navigationEventHandlerResolveEnabledHandler$default == null) {
            navigationEventHandlerResolveEnabledHandler$default = resolveEnabledHandler$default(this, 0, 1, null);
        }
        updateEnabledHandlerInfo$navigationevent(navigationEventHandlerResolveEnabledHandler$default);
    }

    public final void updateEnabledHandlerInfo$navigationevent(NavigationEventHandler<?> handler) {
        NavigationEventHistory newHistory;
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler$default = this.inProgressHandler;
        if (navigationEventHandlerResolveEnabledHandler$default == null) {
            navigationEventHandlerResolveEnabledHandler$default = resolveEnabledHandler$default(this, 0, 1, null);
        }
        if (!Intrinsics.areEqual(navigationEventHandlerResolveEnabledHandler$default, handler)) {
            return;
        }
        if (navigationEventHandlerResolveEnabledHandler$default == null) {
            newHistory = new NavigationEventHistory();
        } else {
            newHistory = new NavigationEventHistory(navigationEventHandlerResolveEnabledHandler$default.getCurrentInfo(), resolveCombinedBackInfo(), navigationEventHandlerResolveEnabledHandler$default.getForwardInfo());
        }
        NavigationEventHistory oldHistory = this._history.getValue();
        if (Intrinsics.areEqual(oldHistory, newHistory)) {
            return;
        }
        this._history.setValue(newHistory);
        for (NavigationEventInput input : this.overlayInputs) {
            input.doOnHistoryChanged$navigationevent(newHistory);
        }
        for (NavigationEventInput input2 : this.defaultInputs) {
            input2.doOnHistoryChanged$navigationevent(newHistory);
        }
        for (NavigationEventInput input3 : this.unspecifiedInputs) {
            input3.doOnHistoryChanged$navigationevent(newHistory);
        }
    }

    public static /* synthetic */ void addHandler$default(NavigationEventProcessor navigationEventProcessor, NavigationEventDispatcher navigationEventDispatcher, NavigationEventHandler navigationEventHandler, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 1;
        }
        navigationEventProcessor.addHandler(navigationEventDispatcher, navigationEventHandler, i);
    }

    public final void addHandler(NavigationEventDispatcher dispatcher, NavigationEventHandler<?> handler, int priority) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        Intrinsics.checkNotNullParameter(handler, "handler");
        if (!(handler.getDispatcher() == null)) {
            throw new IllegalArgumentException(("Handler '" + handler + "' is already registered with a dispatcher").toString());
        }
        switch (priority) {
            case 0:
                this.overlayHandlers.addFirst(handler);
                break;
            case 1:
                this.defaultHandlers.addFirst(handler);
                break;
            default:
                throw new IllegalArgumentException("Unsupported priority value: " + priority);
        }
        handler.setDispatcher$navigationevent(dispatcher);
        refreshEnabledHandlers();
    }

    public final void removeHandler(NavigationEventHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        if (Intrinsics.areEqual(handler, this.inProgressHandler)) {
            switch (this.inProgressDirection) {
                case -1:
                    handler.doOnBackCancelled$navigationevent();
                    break;
                case 1:
                    handler.doOnForwardCancelled$navigationevent();
                    break;
            }
            this.inProgressHandler = null;
            this.inProgressDirection = 0;
            this.inProgressInput = null;
        }
        this.overlayHandlers.remove(handler);
        this.defaultHandlers.remove(handler);
        handler.setDispatcher$navigationevent(null);
        refreshEnabledHandlers();
    }

    public final void addInput(NavigationEventDispatcher dispatcher, NavigationEventInput input, int priority) {
        Set<NavigationEventInput> set;
        boolean hasEnabledHandlers;
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        Intrinsics.checkNotNullParameter(input, "input");
        if (!(input.getDispatcher() == null)) {
            throw new IllegalArgumentException(("Input '" + input + "' is already added to dispatcher " + input.getDispatcher() + '.').toString());
        }
        switch (priority) {
            case 0:
                set = this.overlayInputs;
                break;
            case 1:
                set = this.defaultInputs;
                break;
            default:
                set = this.unspecifiedInputs;
                break;
        }
        set.add(input);
        input.setDispatcher$navigationevent(dispatcher);
        input.doOnAdded$navigationevent(dispatcher);
        input.doOnHistoryChanged$navigationevent(this.history.getValue());
        switch (priority) {
            case 0:
                hasEnabledHandlers = this.hasEnabledOverlayHandlers;
                break;
            case 1:
                hasEnabledHandlers = this.hasEnabledDefaultHandlers;
                break;
            default:
                hasEnabledHandlers = this.hasEnabledAnyHandlers;
                break;
        }
        input.doOnHasEnabledHandlersChanged$navigationevent(hasEnabledHandlers);
    }

    public final void removeInput(NavigationEventInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        this.overlayInputs.remove(input);
        this.defaultInputs.remove(input);
        this.unspecifiedInputs.remove(input);
        input.setDispatcher$navigationevent(null);
        input.doOnRemoved$navigationevent();
    }

    public static /* synthetic */ void dispatchOnStarted$default(NavigationEventProcessor navigationEventProcessor, NavigationEventInput navigationEventInput, int i, NavigationEvent navigationEvent, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            navigationEvent = null;
        }
        navigationEventProcessor.dispatchOnStarted(navigationEventInput, i, navigationEvent);
    }

    public final void dispatchOnStarted(NavigationEventInput input, int direction, NavigationEvent event) {
        Intrinsics.checkNotNullParameter(input, "input");
        if (this.inProgressDirection != 0) {
            return;
        }
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler = resolveEnabledHandler(direction);
        this.inProgressHandler = navigationEventHandlerResolveEnabledHandler;
        this.inProgressDirection = direction;
        this.inProgressInput = input;
        if (event != null) {
            switch (direction) {
                case -1:
                    if (navigationEventHandlerResolveEnabledHandler != null) {
                        navigationEventHandlerResolveEnabledHandler.doOnBackStarted$navigationevent(event);
                    }
                    break;
                case 1:
                    if (navigationEventHandlerResolveEnabledHandler != null) {
                        navigationEventHandlerResolveEnabledHandler.doOnForwardStarted$navigationevent(event);
                    }
                    break;
            }
            this._transitionState.setValue(new NavigationEventTransitionState.InProgress(event, direction));
        }
    }

    public final void dispatchOnProgressed(NavigationEventInput input, int direction, NavigationEvent event) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(event, "event");
        if (!Intrinsics.areEqual(input, this.inProgressInput) || direction != this.inProgressDirection) {
            return;
        }
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler = this.inProgressHandler;
        if (navigationEventHandlerResolveEnabledHandler == null) {
            navigationEventHandlerResolveEnabledHandler = resolveEnabledHandler(direction);
        }
        switch (direction) {
            case -1:
                if (navigationEventHandlerResolveEnabledHandler != null) {
                    navigationEventHandlerResolveEnabledHandler.doOnBackProgressed$navigationevent(event);
                }
                break;
            case 1:
                if (navigationEventHandlerResolveEnabledHandler != null) {
                    navigationEventHandlerResolveEnabledHandler.doOnForwardProgressed$navigationevent(event);
                }
                break;
        }
        this._transitionState.setValue(new NavigationEventTransitionState.InProgress(event, direction));
    }

    public final void dispatchOnCompleted(NavigationEventInput input, int direction, OnBackCompletedFallback onBackCompletedFallback) {
        Intrinsics.checkNotNullParameter(input, "input");
        if (!Intrinsics.areEqual(input, this.inProgressInput) || direction != this.inProgressDirection) {
            return;
        }
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler = this.inProgressHandler;
        if (navigationEventHandlerResolveEnabledHandler == null) {
            navigationEventHandlerResolveEnabledHandler = resolveEnabledHandler(direction);
        }
        this.inProgressHandler = null;
        this.inProgressDirection = 0;
        this.inProgressInput = null;
        switch (direction) {
            case -1:
                if (navigationEventHandlerResolveEnabledHandler == null) {
                    if (onBackCompletedFallback != null) {
                        onBackCompletedFallback.onBackCompletedFallback();
                    }
                } else {
                    navigationEventHandlerResolveEnabledHandler.doOnBackCompleted$navigationevent();
                }
                break;
            case 1:
                if (navigationEventHandlerResolveEnabledHandler != null) {
                    navigationEventHandlerResolveEnabledHandler.doOnForwardCompleted$navigationevent();
                }
                break;
        }
        this._transitionState.setValue(NavigationEventTransitionState.Idle.INSTANCE);
    }

    public final void dispatchOnCancelled(NavigationEventInput input, int direction) {
        Intrinsics.checkNotNullParameter(input, "input");
        if (!Intrinsics.areEqual(input, this.inProgressInput) || direction != this.inProgressDirection) {
            return;
        }
        NavigationEventHandler<?> navigationEventHandlerResolveEnabledHandler = this.inProgressHandler;
        if (navigationEventHandlerResolveEnabledHandler == null) {
            navigationEventHandlerResolveEnabledHandler = resolveEnabledHandler(direction);
        }
        this.inProgressHandler = null;
        this.inProgressDirection = 0;
        this.inProgressInput = null;
        switch (direction) {
            case -1:
                if (navigationEventHandlerResolveEnabledHandler != null) {
                    navigationEventHandlerResolveEnabledHandler.doOnBackCancelled$navigationevent();
                }
                break;
            case 1:
                if (navigationEventHandlerResolveEnabledHandler != null) {
                    navigationEventHandlerResolveEnabledHandler.doOnForwardCancelled$navigationevent();
                }
                break;
        }
        this._transitionState.setValue(NavigationEventTransitionState.Idle.INSTANCE);
    }

    static /* synthetic */ NavigationEventHandler resolveEnabledHandler$default(NavigationEventProcessor navigationEventProcessor, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return navigationEventProcessor.resolveEnabledHandler(i);
    }

    private final NavigationEventHandler<?> resolveEnabledHandler(int direction) {
        Object element$iv$iv;
        Object element$iv$iv2;
        Object element$iv$iv3;
        Object obj = null;
        switch (direction) {
            case -1:
                Iterable $this$firstOrNull$iv$iv = this.overlayHandlers;
                Iterator<NavigationEventHandler<?>> it = $this$firstOrNull$iv$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv$iv = it.next();
                        if (((NavigationEventHandler) element$iv$iv).isBackEnabled()) {
                        }
                    } else {
                        element$iv$iv = null;
                    }
                }
                NavigationEventHandler<?> navigationEventHandler = (NavigationEventHandler) element$iv$iv;
                if (navigationEventHandler != null) {
                    return navigationEventHandler;
                }
                Iterable $this$firstOrNull$iv$iv2 = this.defaultHandlers;
                Iterator<NavigationEventHandler<?>> it2 = $this$firstOrNull$iv$iv2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Object element$iv$iv4 = it2.next();
                        if (((NavigationEventHandler) element$iv$iv4).isBackEnabled()) {
                            obj = element$iv$iv4;
                        }
                    }
                }
                return (NavigationEventHandler) obj;
            case 0:
                Iterable $this$firstOrNull$iv$iv3 = this.overlayHandlers;
                Iterator<NavigationEventHandler<?>> it3 = $this$firstOrNull$iv$iv3.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        element$iv$iv2 = it3.next();
                        NavigationEventHandler<?> navigationEventHandler2 = (NavigationEventHandler) element$iv$iv2;
                        if (navigationEventHandler2.isBackEnabled() || navigationEventHandler2.isForwardEnabled()) {
                        }
                    } else {
                        element$iv$iv2 = null;
                    }
                }
                NavigationEventHandler<?> navigationEventHandler3 = (NavigationEventHandler) element$iv$iv2;
                if (navigationEventHandler3 != null) {
                    return navigationEventHandler3;
                }
                Iterable $this$firstOrNull$iv$iv4 = this.defaultHandlers;
                Iterator<NavigationEventHandler<?>> it4 = $this$firstOrNull$iv$iv4.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        Object element$iv$iv5 = it4.next();
                        NavigationEventHandler<?> navigationEventHandler4 = (NavigationEventHandler) element$iv$iv5;
                        if (navigationEventHandler4.isBackEnabled() || navigationEventHandler4.isForwardEnabled()) {
                            obj = element$iv$iv5;
                        }
                    }
                }
                return (NavigationEventHandler) obj;
            case 1:
                Iterable $this$firstOrNull$iv$iv5 = this.overlayHandlers;
                Iterator<NavigationEventHandler<?>> it5 = $this$firstOrNull$iv$iv5.iterator();
                while (true) {
                    if (it5.hasNext()) {
                        element$iv$iv3 = it5.next();
                        if (((NavigationEventHandler) element$iv$iv3).isForwardEnabled()) {
                        }
                    } else {
                        element$iv$iv3 = null;
                    }
                }
                NavigationEventHandler<?> navigationEventHandler5 = (NavigationEventHandler) element$iv$iv3;
                if (navigationEventHandler5 != null) {
                    return navigationEventHandler5;
                }
                Iterable $this$firstOrNull$iv$iv6 = this.defaultHandlers;
                Iterator<NavigationEventHandler<?>> it6 = $this$firstOrNull$iv$iv6.iterator();
                while (true) {
                    if (it6.hasNext()) {
                        Object element$iv$iv6 = it6.next();
                        if (((NavigationEventHandler) element$iv$iv6).isForwardEnabled()) {
                            obj = element$iv$iv6;
                        }
                    }
                }
                return (NavigationEventHandler) obj;
            default:
                throw new IllegalStateException(("Unsupported direction: '" + direction + "'.").toString());
        }
    }

    private final NavigationEventHandler<?> findHandler(Function1<? super NavigationEventHandler<?>, Boolean> predicate) {
        Object obj;
        Object element$iv;
        Iterable $this$firstOrNull$iv = this.overlayHandlers;
        Iterator<NavigationEventHandler<?>> it = $this$firstOrNull$iv.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (predicate.invoke(element$iv).booleanValue()) {
                break;
            }
        }
        NavigationEventHandler<?> navigationEventHandler = (NavigationEventHandler) element$iv;
        if (navigationEventHandler != null) {
            return navigationEventHandler;
        }
        Iterable $this$firstOrNull$iv2 = this.defaultHandlers;
        Iterator<NavigationEventHandler<?>> it2 = $this$firstOrNull$iv2.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object element$iv2 = it2.next();
            if (predicate.invoke(element$iv2).booleanValue()) {
                obj = element$iv2;
                break;
            }
        }
        return (NavigationEventHandler) obj;
    }

    private final List<NavigationEventInfo> resolveCombinedBackInfo() {
        List combinedBackInfo = new ArrayList();
        Iterator it = this.overlayHandlers.iterator();
        while (it.hasNext()) {
            NavigationEventHandler handler = (NavigationEventHandler) it.next();
            if (handler.isBackEnabled() && !handler.getBackInfo().isEmpty()) {
                combinedBackInfo.addAll(handler.getBackInfo());
            }
        }
        Iterator it2 = this.defaultHandlers.iterator();
        while (it2.hasNext()) {
            NavigationEventHandler handler2 = (NavigationEventHandler) it2.next();
            if (handler2.isBackEnabled() && !handler2.getBackInfo().isEmpty()) {
                combinedBackInfo.addAll(handler2.getBackInfo());
            }
        }
        return combinedBackInfo;
    }
}
