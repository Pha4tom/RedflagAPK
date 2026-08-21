package androidx.activity;

import android.window.OnBackInvokedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigationevent.NavigationEvent;
import androidx.navigationevent.NavigationEventDispatcher;
import androidx.navigationevent.NavigationEventHandler;
import androidx.navigationevent.NavigationEventInput;
import androidx.navigationevent.OnBackCompletedFallback;
import androidx.navigationevent.OnBackInvokedDefaultInput;
import androidx.navigationevent.OnBackInvokedOverlayInput;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001 B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bB\u0015\b\u0017\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0007\u0010\tJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\b\u0010\n\u001a\u00020\u0006H\u0007J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\b\u0010\u001e\u001a\u00020\u0012H\u0007J\b\u0010\u001f\u001a\u00020\u0012H\u0007R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00060\u0010R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Landroidx/activity/OnBackPressedDispatcher;", "", "fallbackOnBackPressed", "Ljava/lang/Runnable;", "onHasEnabledCallbacksChanged", "Landroidx/core/util/Consumer;", "", "<init>", "(Ljava/lang/Runnable;Landroidx/core/util/Consumer;)V", "(Ljava/lang/Runnable;)V", "hasEnabledCallbacks", "eventDispatcher", "Landroidx/navigationevent/NavigationEventDispatcher;", "getEventDispatcher$activity", "()Landroidx/navigationevent/NavigationEventDispatcher;", "eventInput", "Landroidx/activity/OnBackPressedDispatcher$OnBackPressedEventInput;", "setOnBackInvokedDispatcher", "", "invoker", "Landroid/window/OnBackInvokedDispatcher;", "addCallback", "onBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "dispatchOnBackStarted", "backEvent", "Landroidx/activity/BackEventCompat;", "dispatchOnBackProgressed", "onBackPressed", "dispatchOnBackCancelled", "OnBackPressedEventInput", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class OnBackPressedDispatcher {
    private final NavigationEventDispatcher eventDispatcher;
    private final OnBackPressedEventInput eventInput;
    private final Runnable fallbackOnBackPressed;
    private boolean hasEnabledCallbacks;
    private final Consumer<Boolean> onHasEnabledCallbacksChanged;

    /* JADX WARN: Multi-variable type inference failed */
    public OnBackPressedDispatcher() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public OnBackPressedDispatcher(Runnable fallbackOnBackPressed, Consumer<Boolean> consumer) {
        this.fallbackOnBackPressed = fallbackOnBackPressed;
        this.onHasEnabledCallbacksChanged = consumer;
        this.eventDispatcher = new NavigationEventDispatcher(new OnBackCompletedFallback() { // from class: androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda0
            @Override // androidx.navigationevent.OnBackCompletedFallback
            public final void onBackCompletedFallback() {
                OnBackPressedDispatcher.eventDispatcher$lambda$0(this.f$0);
            }
        });
        this.eventInput = new OnBackPressedEventInput();
        this.eventDispatcher.addInput(this.eventInput);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void eventDispatcher$lambda$0(OnBackPressedDispatcher this$0) {
        Runnable runnable = this$0.fallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: renamed from: getEventDispatcher$activity, reason: from getter */
    public final NavigationEventDispatcher getEventDispatcher() {
        return this.eventDispatcher;
    }

    public OnBackPressedDispatcher(Runnable fallbackOnBackPressed) {
        this(fallbackOnBackPressed, null);
    }

    public /* synthetic */ OnBackPressedDispatcher(Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : runnable);
    }

    public final void setOnBackInvokedDispatcher(OnBackInvokedDispatcher invoker) {
        Intrinsics.checkNotNullParameter(invoker, "invoker");
        this.eventDispatcher.addInput(new OnBackInvokedDefaultInput(invoker), 1);
        this.eventDispatcher.addInput(new OnBackInvokedOverlayInput(invoker), 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addCallback(OnBackPressedCallback onBackPressedCallback) {
        Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
        NavigationEventDispatcher.addHandler$default(this.eventDispatcher, onBackPressedCallback.createNavigationEventHandler$activity(new OnBackPressedCallbackInfo(onBackPressedCallback, null, 2, 0 == true ? 1 : 0)), 0, 2, null);
    }

    public final void addCallback(LifecycleOwner owner, OnBackPressedCallback onBackPressedCallback) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getState() == Lifecycle.State.DESTROYED) {
            return;
        }
        OnBackPressedCallbackInfo info = new OnBackPressedCallbackInfo(onBackPressedCallback, owner);
        NavigationEventHandler<?> navigationEventHandlerCreateNavigationEventHandler$activity = onBackPressedCallback.createNavigationEventHandler$activity(info);
        if (ActivityFlags.isOnBackPressedLifecycleOrderMaintained) {
            navigationEventHandlerCreateNavigationEventHandler$activity.setBackEnabled(false);
            NavigationEventDispatcher.addHandler$default(this.eventDispatcher, navigationEventHandlerCreateNavigationEventHandler$activity, 0, 2, null);
        }
        OnBackPressedDispatcher$addCallback$lifecycleObserver$1 lifecycleObserver = new OnBackPressedDispatcher$addCallback$lifecycleObserver$1(navigationEventHandlerCreateNavigationEventHandler$activity, onBackPressedCallback, this, lifecycle);
        lifecycle.addObserver(lifecycleObserver);
        onBackPressedCallback.addCloseable$activity(lifecycleObserver);
    }

    /* JADX INFO: renamed from: hasEnabledCallbacks, reason: from getter */
    public final boolean getHasEnabledCallbacks() {
        return this.hasEnabledCallbacks;
    }

    public final void dispatchOnBackStarted(BackEventCompat backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        this.eventInput.backStarted(backEvent.toNavigationEvent());
    }

    public final void dispatchOnBackProgressed(BackEventCompat backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        this.eventInput.backProgressed(backEvent.toNavigationEvent());
    }

    public final void onBackPressed() {
        this.eventInput.backCompleted();
    }

    public final void dispatchOnBackCancelled() {
        this.eventInput.backCancelled();
    }

    /* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\f\u001a\u00020\u0005J\u0006\u0010\r\u001a\u00020\u0005¨\u0006\u000e"}, d2 = {"Landroidx/activity/OnBackPressedDispatcher$OnBackPressedEventInput;", "Landroidx/navigationevent/NavigationEventInput;", "<init>", "(Landroidx/activity/OnBackPressedDispatcher;)V", "onHasEnabledHandlersChanged", "", "hasEnabledHandlers", "", "backStarted", NotificationCompat.CATEGORY_EVENT, "Landroidx/navigationevent/NavigationEvent;", "backProgressed", "backCancelled", "backCompleted", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private final class OnBackPressedEventInput extends NavigationEventInput {
        public OnBackPressedEventInput() {
        }

        @Override // androidx.navigationevent.NavigationEventInput
        protected void onHasEnabledHandlersChanged(boolean hasEnabledHandlers) {
            OnBackPressedDispatcher.this.hasEnabledCallbacks = hasEnabledHandlers;
            Consumer consumer = OnBackPressedDispatcher.this.onHasEnabledCallbacksChanged;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(hasEnabledHandlers));
            }
        }

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
    }
}
