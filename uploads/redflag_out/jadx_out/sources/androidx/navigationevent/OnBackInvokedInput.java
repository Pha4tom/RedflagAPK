package androidx.navigationevent;

import android.os.Build;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnBackInvokedInput.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001B\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u000bH\u0014J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000bH\u0002J\b\u0010\u0012\u001a\u00020\tH\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0001\u0002\u0013\u0014¨\u0006\u0015"}, d2 = {"Landroidx/navigationevent/OnBackInvokedInput;", "Landroidx/navigationevent/NavigationEventInput;", "onBackInvokedDispatcher", "Landroid/window/OnBackInvokedDispatcher;", "onBackInvokedCallbackPriority", "", "<init>", "(Landroid/window/OnBackInvokedDispatcher;I)V", "onBackInvokedCallback", "Landroid/window/OnBackInvokedCallback;", "backInvokedCallbackRegistered", "", "onRemoved", "", "onHasEnabledHandlersChanged", "hasEnabledHandlers", "updateBackInvokedCallbackState", "shouldBeRegistered", "createOnBackAnimationCallback", "Landroidx/navigationevent/OnBackInvokedDefaultInput;", "Landroidx/navigationevent/OnBackInvokedOverlayInput;", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class OnBackInvokedInput extends NavigationEventInput {
    private boolean backInvokedCallbackRegistered;
    private final OnBackInvokedCallback onBackInvokedCallback;
    private final int onBackInvokedCallbackPriority;
    private final OnBackInvokedDispatcher onBackInvokedDispatcher;

    public /* synthetic */ OnBackInvokedInput(OnBackInvokedDispatcher onBackInvokedDispatcher, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(onBackInvokedDispatcher, i);
    }

    private OnBackInvokedInput(OnBackInvokedDispatcher onBackInvokedDispatcher, int onBackInvokedCallbackPriority) {
        OnBackInvokedCallback onBackInvokedCallbackCreateOnBackAnimationCallback;
        this.onBackInvokedDispatcher = onBackInvokedDispatcher;
        this.onBackInvokedCallbackPriority = onBackInvokedCallbackPriority;
        if (Build.VERSION.SDK_INT == 33) {
            onBackInvokedCallbackCreateOnBackAnimationCallback = new OnBackInvokedCallback() { // from class: androidx.navigationevent.OnBackInvokedInput$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    this.f$0.dispatchOnBackCompleted();
                }
            };
        } else {
            onBackInvokedCallbackCreateOnBackAnimationCallback = createOnBackAnimationCallback();
        }
        this.onBackInvokedCallback = onBackInvokedCallbackCreateOnBackAnimationCallback;
    }

    @Override // androidx.navigationevent.NavigationEventInput
    protected void onRemoved() {
        updateBackInvokedCallbackState(false);
    }

    @Override // androidx.navigationevent.NavigationEventInput
    protected void onHasEnabledHandlersChanged(boolean hasEnabledHandlers) {
        updateBackInvokedCallbackState(hasEnabledHandlers);
    }

    private final void updateBackInvokedCallbackState(boolean shouldBeRegistered) {
        if (shouldBeRegistered && !this.backInvokedCallbackRegistered) {
            this.onBackInvokedDispatcher.registerOnBackInvokedCallback(this.onBackInvokedCallbackPriority, this.onBackInvokedCallback);
            this.backInvokedCallbackRegistered = true;
        } else if (!shouldBeRegistered && this.backInvokedCallbackRegistered) {
            this.onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.onBackInvokedCallback);
            this.backInvokedCallbackRegistered = false;
        }
    }

    private final OnBackInvokedCallback createOnBackAnimationCallback() {
        return new OnBackAnimationCallback() { // from class: androidx.navigationevent.OnBackInvokedInput.createOnBackAnimationCallback.1
            @Override // android.window.OnBackAnimationCallback
            public void onBackStarted(BackEvent backEvent) {
                Intrinsics.checkNotNullParameter(backEvent, "backEvent");
                OnBackInvokedInput.this.dispatchOnBackStarted(NavigationEvent_androidKt.NavigationEvent(backEvent));
            }

            @Override // android.window.OnBackAnimationCallback
            public void onBackProgressed(BackEvent backEvent) {
                Intrinsics.checkNotNullParameter(backEvent, "backEvent");
                OnBackInvokedInput.this.dispatchOnBackProgressed(NavigationEvent_androidKt.NavigationEvent(backEvent));
            }

            @Override // android.window.OnBackInvokedCallback
            public void onBackInvoked() {
                OnBackInvokedInput.this.dispatchOnBackCompleted();
            }

            @Override // android.window.OnBackAnimationCallback
            public void onBackCancelled() {
                OnBackInvokedInput.this.dispatchOnBackCancelled();
            }
        };
    }
}
