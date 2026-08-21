package androidx.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigationevent.NavigationEventInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Landroidx/activity/OnBackPressedCallbackInfo;", "Landroidx/navigationevent/NavigationEventInfo;", "callback", "Landroidx/activity/OnBackPressedCallback;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "<init>", "(Landroidx/activity/OnBackPressedCallback;Landroidx/lifecycle/LifecycleOwner;)V", "getCallback", "()Landroidx/activity/OnBackPressedCallback;", "getOwner", "()Landroidx/lifecycle/LifecycleOwner;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
final /* data */ class OnBackPressedCallbackInfo extends NavigationEventInfo {
    private final OnBackPressedCallback callback;
    private final LifecycleOwner owner;

    public static /* synthetic */ OnBackPressedCallbackInfo copy$default(OnBackPressedCallbackInfo onBackPressedCallbackInfo, OnBackPressedCallback onBackPressedCallback, LifecycleOwner lifecycleOwner, int i, Object obj) {
        if ((i & 1) != 0) {
            onBackPressedCallback = onBackPressedCallbackInfo.callback;
        }
        if ((i & 2) != 0) {
            lifecycleOwner = onBackPressedCallbackInfo.owner;
        }
        return onBackPressedCallbackInfo.copy(onBackPressedCallback, lifecycleOwner);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final OnBackPressedCallback getCallback() {
        return this.callback;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final LifecycleOwner getOwner() {
        return this.owner;
    }

    public final OnBackPressedCallbackInfo copy(OnBackPressedCallback callback, LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return new OnBackPressedCallbackInfo(callback, owner);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OnBackPressedCallbackInfo)) {
            return false;
        }
        OnBackPressedCallbackInfo onBackPressedCallbackInfo = (OnBackPressedCallbackInfo) other;
        return Intrinsics.areEqual(this.callback, onBackPressedCallbackInfo.callback) && Intrinsics.areEqual(this.owner, onBackPressedCallbackInfo.owner);
    }

    public int hashCode() {
        return (this.callback.hashCode() * 31) + (this.owner == null ? 0 : this.owner.hashCode());
    }

    public String toString() {
        return "OnBackPressedCallbackInfo(callback=" + this.callback + ", owner=" + this.owner + ')';
    }

    public OnBackPressedCallbackInfo(OnBackPressedCallback callback, LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.owner = owner;
    }

    public /* synthetic */ OnBackPressedCallbackInfo(OnBackPressedCallback onBackPressedCallback, LifecycleOwner lifecycleOwner, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(onBackPressedCallback, (i & 2) != 0 ? null : lifecycleOwner);
    }

    public final OnBackPressedCallback getCallback() {
        return this.callback;
    }

    public final LifecycleOwner getOwner() {
        return this.owner;
    }
}
