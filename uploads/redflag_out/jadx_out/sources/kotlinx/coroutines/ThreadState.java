package kotlinx.coroutines;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Interruptible.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0011J\u0012\u0010\u0015\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\t\u0010\u0004\u001a\u00020\u0005X\u0082\u0004R\u0018\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lkotlinx/coroutines/ThreadState;", "Lkotlinx/coroutines/JobNode;", "<init>", "()V", "_state", "Lkotlinx/atomicfu/AtomicInt;", "targetThread", "Ljava/lang/Thread;", "kotlin.jvm.PlatformType", "Ljava/lang/Thread;", "cancelHandle", "Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "", "getOnCancelling", "()Z", "setup", "", "job", "Lkotlinx/coroutines/Job;", "clearInterrupt", "invoke", "cause", "", "invalidState", "", "state", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
final class ThreadState extends JobNode {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _state$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(ThreadState.class, "_state$volatile");
    private volatile /* synthetic */ int _state$volatile;
    private DisposableHandle cancelHandle;
    private final Thread targetThread = Thread.currentThread();

    private final /* synthetic */ int get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Unit> function1) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void set_state$volatile(int i) {
        this._state$volatile = i;
    }

    @Override // kotlinx.coroutines.JobNode
    public boolean getOnCancelling() {
        return true;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:104)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public final void setup(kotlinx.coroutines.Job r7) {
        /*
            r6 = this;
            r0 = r6
            kotlinx.coroutines.JobNode r0 = (kotlinx.coroutines.JobNode) r0
            r1 = 1
            r2 = 0
            r3 = 0
            kotlinx.coroutines.DisposableHandle r0 = kotlinx.coroutines.JobKt.invokeOnCompletion$default(r7, r3, r0, r1, r2)
            r6.cancelHandle = r0
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = get_state$volatile$FU()
            r1 = r6
        L11:
            int r2 = r0.get(r6)
            r4 = 0
            switch(r2) {
                case 0: goto L23;
                case 1: goto L19;
                case 2: goto L22;
                case 3: goto L22;
                default: goto L19;
            }
        L19:
            r6.invalidState(r2)
            kotlin.KotlinNothingValueException r3 = new kotlin.KotlinNothingValueException
            r3.<init>()
            throw r3
        L22:
            return
        L23:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = get_state$volatile$FU()
            boolean r5 = r5.compareAndSet(r6, r2, r3)
            if (r5 == 0) goto L2e
            return
        L2e:
            goto L11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.ThreadState.setup(kotlinx.coroutines.Job):void");
    }

    public final void clearInterrupt() {
        AtomicIntegerFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        while (true) {
            int state = handler$atomicfu$iv.get(this);
            switch (state) {
                case 0:
                    if (_state$volatile$FU.compareAndSet(this, state, 1)) {
                        DisposableHandle disposableHandle = this.cancelHandle;
                        if (disposableHandle != null) {
                            disposableHandle.dispose();
                            return;
                        }
                        return;
                    }
                    break;
                case 1:
                default:
                    invalidState(state);
                    throw new KotlinNothingValueException();
                case 2:
                    break;
                case 3:
                    Thread.interrupted();
                    return;
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:104)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // kotlinx.coroutines.JobNode
    public void invoke(java.lang.Throwable r7) {
        /*
            r6 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = get_state$volatile$FU()
            r1 = r6
        L5:
            int r2 = r0.get(r6)
            r3 = 0
            switch(r2) {
                case 0: goto L17;
                case 1: goto L16;
                case 2: goto L16;
                case 3: goto L16;
                default: goto Ld;
            }
        Ld:
            r6.invalidState(r2)
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        L16:
            return
        L17:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r4 = get_state$volatile$FU()
            r5 = 2
            boolean r4 = r4.compareAndSet(r6, r2, r5)
            if (r4 == 0) goto L30
            java.lang.Thread r4 = r6.targetThread
            r4.interrupt()
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r4 = get_state$volatile$FU()
            r5 = 3
            r4.set(r6, r5)
            return
        L30:
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.ThreadState.invoke(java.lang.Throwable):void");
    }

    private final Void invalidState(int state) {
        throw new IllegalStateException(("Illegal state " + state).toString());
    }
}
