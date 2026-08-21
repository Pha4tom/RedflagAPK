package androidx.navigationevent;

import android.window.OnBackInvokedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;

/* JADX INFO: compiled from: OnBackInvokedInput.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroidx/navigationevent/OnBackInvokedOverlayInput;", "Landroidx/navigationevent/OnBackInvokedInput;", "onBackInvokedDispatcher", "Landroid/window/OnBackInvokedDispatcher;", "<init>", "(Landroid/window/OnBackInvokedDispatcher;)V", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class OnBackInvokedOverlayInput extends OnBackInvokedInput {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OnBackInvokedOverlayInput(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        super(onBackInvokedDispatcher, DurationKt.NANOS_IN_MILLIS, null);
        Intrinsics.checkNotNullParameter(onBackInvokedDispatcher, "onBackInvokedDispatcher");
    }
}
