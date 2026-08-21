package androidx.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigationevent.NavigationEventDispatcher;
import androidx.navigationevent.NavigationEventHandler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0005H\u0016¨\u0006\u000b"}, d2 = {"androidx/activity/OnBackPressedDispatcher$addCallback$lifecycleObserver$1", "Landroidx/lifecycle/LifecycleEventObserver;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", NotificationCompat.CATEGORY_EVENT, "Landroidx/lifecycle/Lifecycle$Event;", "close", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class OnBackPressedDispatcher$addCallback$lifecycleObserver$1 implements LifecycleEventObserver, AutoCloseable {
    final /* synthetic */ NavigationEventHandler<?> $eventHandler;
    final /* synthetic */ Lifecycle $lifecycle;
    final /* synthetic */ OnBackPressedCallback $onBackPressedCallback;
    final /* synthetic */ OnBackPressedDispatcher this$0;

    OnBackPressedDispatcher$addCallback$lifecycleObserver$1(NavigationEventHandler<?> navigationEventHandler, OnBackPressedCallback $onBackPressedCallback, OnBackPressedDispatcher $receiver, Lifecycle $lifecycle) {
        this.$eventHandler = navigationEventHandler;
        this.$onBackPressedCallback = $onBackPressedCallback;
        this.this$0 = $receiver;
        this.$lifecycle = $lifecycle;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(event, "event");
        boolean z = false;
        if (ActivityFlags.isOnBackPressedLifecycleOrderMaintained) {
            NavigationEventHandler<?> navigationEventHandler = this.$eventHandler;
            if (event.getTargetState().isAtLeast(Lifecycle.State.STARTED) && this.$onBackPressedCallback.getIsEnabled()) {
                z = true;
            }
            navigationEventHandler.setBackEnabled(z);
        } else if (event == Lifecycle.Event.ON_START) {
            NavigationEventDispatcher.addHandler$default(this.this$0.getEventDispatcher(), this.$eventHandler, 0, 2, null);
        } else if (event == Lifecycle.Event.ON_STOP) {
            this.$eventHandler.remove();
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.$eventHandler.remove();
            this.$lifecycle.removeObserver(this);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.$lifecycle.removeObserver(this);
    }
}
