package kotlinx.coroutines.flow.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.encoding.Base64;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: Combine.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u008d\u0001\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0014\u0010\b\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00060\n0\t2\u0016\u0010\u000b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0006\u0018\u00010\t0\f29\u0010\r\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000e¢\u0006\u0002\b\u0010H\u0081@¢\u0006\u0002\u0010\u0011\u001ak\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00050\n\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u0014\"\u0004\b\u0002\u0010\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00140\n2(\u0010\r\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000eH\u0000¢\u0006\u0002\u0010\u0017*\u001c\b\u0002\u0010\u0000\"\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001¨\u0006\u0018"}, d2 = {"Update", "Lkotlin/collections/IndexedValue;", "", "combineInternal", "", "R", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "flows", "", "Lkotlinx/coroutines/flow/Flow;", "arrayFactory", "Lkotlin/Function0;", "transform", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;[Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "zipImpl", "T1", "T2", "flow", "flow2", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CombineKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2, reason: invalid class name */
    /* JADX INFO: compiled from: Combine.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", f = "Combine.kt", i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2}, l = {ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG, 73, Base64.mimeLineLength}, m = "invokeSuspend", n = {"latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<T[]> $arrayFactory;
        final /* synthetic */ Flow<T>[] $flows;
        final /* synthetic */ FlowCollector<R> $this_combineInternal;
        final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, FlowCollector<? super R> flowCollector, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$flows = flowArr;
            this.$arrayFactory = function0;
            this.$transform = function3;
            this.$this_combineInternal = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x00dc A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00dd  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00e9 A[LOOP:0: B:25:0x00e9->B:50:?, LOOP_START, PHI: r6 r9
          0x00e9: PHI (r6v12 'remainingAbsentValues' int) = (r6v11 'remainingAbsentValues' int), (r6v13 'remainingAbsentValues' int) binds: [B:22:0x00e4, B:50:?] A[DONT_GENERATE, DONT_INLINE]
          0x00e9: PHI (r9v8 'element' kotlin.collections.IndexedValue) = (r9v7 'element' kotlin.collections.IndexedValue), (r9v20 'element' kotlin.collections.IndexedValue) binds: [B:22:0x00e4, B:50:?] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0113  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x015b  */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x0135 -> B:17:0x00c2). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x0158 -> B:17:0x00c2). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x015b -> B:17:0x00c2). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r21) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 362
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: Combine.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1", f = "Combine.kt", i = {}, l = {28}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Flow<T>[] $flows;
            final /* synthetic */ int $i;
            final /* synthetic */ AtomicInteger $nonClosed;
            final /* synthetic */ Channel<IndexedValue<Object>> $resultChannel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Flow<? extends T>[] flowArr, int i, AtomicInteger atomicInteger, Channel<IndexedValue<Object>> channel, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$flows = flowArr;
                this.$i = i;
                this.$nonClosed = atomicInteger;
                this.$resultChannel = channel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$flows, this.$i, this.$nonClosed, this.$resultChannel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
            /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r10) throws java.lang.Throwable {
                /*
                    r9 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r9.label
                    r2 = 1
                    r3 = 0
                    switch(r1) {
                        case 0: goto L1a;
                        case 1: goto L13;
                        default: goto Lb;
                    }
                Lb:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r0)
                    throw r10
                L13:
                    r0 = r9
                    kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L18
                    goto L3d
                L18:
                    r1 = move-exception
                    goto L54
                L1a:
                    kotlin.ResultKt.throwOnFailure(r10)
                    r1 = r9
                    kotlinx.coroutines.flow.Flow<T>[] r4 = r1.$flows     // Catch: java.lang.Throwable -> L50
                    int r5 = r1.$i     // Catch: java.lang.Throwable -> L50
                    r4 = r4[r5]     // Catch: java.lang.Throwable -> L50
                    kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1 r5 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1     // Catch: java.lang.Throwable -> L50
                    kotlinx.coroutines.channels.Channel<kotlin.collections.IndexedValue<java.lang.Object>> r6 = r1.$resultChannel     // Catch: java.lang.Throwable -> L50
                    int r7 = r1.$i     // Catch: java.lang.Throwable -> L50
                    r5.<init>(r6, r7)     // Catch: java.lang.Throwable -> L50
                    kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5     // Catch: java.lang.Throwable -> L50
                    r6 = r1
                    kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch: java.lang.Throwable -> L50
                    r1.label = r2     // Catch: java.lang.Throwable -> L50
                    java.lang.Object r4 = r4.collect(r5, r6)     // Catch: java.lang.Throwable -> L50
                    if (r4 != r0) goto L3c
                    return r0
                L3c:
                    r0 = r1
                L3d:
                    java.util.concurrent.atomic.AtomicInteger r1 = r0.$nonClosed
                    int r1 = r1.decrementAndGet()
                    if (r1 != 0) goto L4c
                    kotlinx.coroutines.channels.Channel<kotlin.collections.IndexedValue<java.lang.Object>> r1 = r0.$resultChannel
                    kotlinx.coroutines.channels.SendChannel r1 = (kotlinx.coroutines.channels.SendChannel) r1
                    kotlinx.coroutines.channels.SendChannel.DefaultImpls.close$default(r1, r3, r2, r3)
                L4c:
                    kotlin.Unit r1 = kotlin.Unit.INSTANCE
                    return r1
                L50:
                    r0 = move-exception
                    r8 = r1
                    r1 = r0
                    r0 = r8
                L54:
                    java.util.concurrent.atomic.AtomicInteger r4 = r0.$nonClosed
                    int r4 = r4.decrementAndGet()
                    if (r4 != 0) goto L63
                    kotlinx.coroutines.channels.Channel<kotlin.collections.IndexedValue<java.lang.Object>> r4 = r0.$resultChannel
                    kotlinx.coroutines.channels.SendChannel r4 = (kotlinx.coroutines.channels.SendChannel) r4
                    kotlinx.coroutines.channels.SendChannel.DefaultImpls.close$default(r4, r3, r2, r3)
                L63:
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt.AnonymousClass2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: Combine.kt */
            @Metadata(k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
            static final class C00051<T> implements FlowCollector {
                final /* synthetic */ int $i;
                final /* synthetic */ Channel<IndexedValue<Object>> $resultChannel;

                C00051(Channel<IndexedValue<Object>> channel, int i) {
                    this.$resultChannel = channel;
                    this.$i = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x0054 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) throws java.lang.Throwable {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1$emit$1
                        if (r0 == 0) goto L14
                        r0 = r9
                        kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1$emit$1 r0 = (kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1$emit$1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r1 = r1 & r2
                        if (r1 == 0) goto L14
                        int r1 = r0.label
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L19
                    L14:
                        kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1$emit$1 r0 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1$emit$1
                        r0.<init>(r7, r9)
                    L19:
                        java.lang.Object r1 = r0.result
                        java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r3 = r0.label
                        switch(r3) {
                            case 0: goto L34;
                            case 1: goto L30;
                            case 2: goto L2c;
                            default: goto L24;
                        }
                    L24:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r0)
                        throw r8
                    L2c:
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto L55
                    L30:
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto L4b
                    L34:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r3 = r7
                        kotlinx.coroutines.channels.Channel<kotlin.collections.IndexedValue<java.lang.Object>> r4 = r3.$resultChannel
                        kotlin.collections.IndexedValue r5 = new kotlin.collections.IndexedValue
                        int r6 = r3.$i
                        r5.<init>(r6, r8)
                        r6 = 1
                        r0.label = r6
                        java.lang.Object r8 = r4.send(r5, r0)
                        if (r8 != r2) goto L4b
                        return r2
                    L4b:
                        r8 = 2
                        r0.label = r8
                        java.lang.Object r8 = kotlinx.coroutines.YieldKt.yield(r0)
                        if (r8 != r2) goto L55
                        return r2
                    L55:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt.AnonymousClass2.AnonymousClass1.C00051.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }
        }
    }

    public static final <R, T> Object combineInternal(FlowCollector<? super R> flowCollector, Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super Unit> continuation) {
        Object objFlowScope = FlowCoroutineKt.flowScope(new AnonymousClass2(flowArr, function0, function3, flowCollector, null), continuation);
        return objFlowScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objFlowScope : Unit.INSTANCE;
    }

    public static final <T1, T2, R> Flow<R> zipImpl(final Flow<? extends T1> flow, final Flow<? extends T2> flow2, final Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
                Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new CombineKt$zipImpl$1$1(flow2, flow, flowCollector, function3, null), continuation);
                return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
            }
        };
    }
}
