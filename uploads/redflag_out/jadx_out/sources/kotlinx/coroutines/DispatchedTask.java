package kotlinx.coroutines;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.scheduling.Task;

/* JADX INFO: compiled from: DispatchedTask.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\f\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0003j\u0002`\u0002B\u0011\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\f\u001a\u0004\u0018\u00010\rH ¢\u0006\u0002\b\u000eJ\u001f\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\rH\u0010¢\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\rH\u0010¢\u0006\u0002\b\u001aJ\u0006\u0010\u001b\u001a\u00020\u0010J\u0015\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u001eR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX \u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/SchedulerTask;", "Lkotlinx/coroutines/scheduling/Task;", "resumeMode", "", "<init>", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "takeState", "", "takeState$kotlinx_coroutines_core", "cancelCompletedResult", "", "takenState", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getSuccessfulResult", "state", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "run", "handleFatalException", "exception", "handleFatalException$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int resumeMode) {
        this.resumeMode = resumeMode;
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c1 A[Catch: all -> 0x00f0, TryCatch #0 {all -> 0x00f0, blocks: (B:42:0x00ab, B:43:0x00ae, B:48:0x00dc, B:40:0x009f, B:46:0x00c1, B:47:0x00cf), top: B:70:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00cf A[Catch: all -> 0x00f0, TryCatch #0 {all -> 0x00f0, blocks: (B:42:0x00ab, B:43:0x00ae, B:48:0x00dc, B:40:0x009f, B:46:0x00c1, B:47:0x00cf), top: B:70:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e8 A[Catch: all -> 0x0102, TryCatch #1 {all -> 0x0102, blocks: (B:50:0x00e2, B:52:0x00e8, B:59:0x00f7, B:63:0x0101, B:61:0x00fd), top: B:72:0x004c }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            Method dump skipped, instruction units count: 271
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedTask.run():void");
    }

    public final void handleFatalException$kotlinx_coroutines_core(Throwable exception) {
        CoroutinesInternalError reason = new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", exception);
        CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().get$context(), reason);
    }
}
