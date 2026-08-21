package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.selects.SelectClause1;

/* JADX INFO: compiled from: Channels.common.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u001a$\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0087@¢\u0006\u0002\u0010\u0006\u001a$\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\b\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0007\u001aP\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\b\rH\u0086\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e\u001a2\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00100\fH\u0086H¢\u0006\u0002\u0010\u0012\u001a$\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0014\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0086@¢\u0006\u0002\u0010\u0006\u001a\u001a\u0010\u0015\u001a\u00020\u0010*\u0006\u0012\u0002\b\u00030\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"DEFAULT_CLOSE_MESSAGE", "", "receiveOrNull", "E", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "consume", "R", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "", "action", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "cancelConsumed", "cause", "", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE, xs = "kotlinx/coroutines/channels/ChannelsKt")
final /* synthetic */ class ChannelsKt__Channels_commonKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1, reason: invalid class name */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {161}, m = "consumeEach", n = {"action", "$this$consume$iv"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__Channels_commonKt.consumeEach(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {209}, m = "toList", n = {"$this$toList_u24lambda_u242", "$this$consume$iv$iv"}, s = {"L$1", "L$2"})
    static final class C00711<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C00711(Continuation<? super C00711> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toList(null, this);
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public static final /* synthetic */ Object receiveOrNull(ReceiveChannel $this$receiveOrNull, Continuation $completion) {
        Intrinsics.checkNotNull($this$receiveOrNull, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E of kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.receiveOrNull?>");
        return $this$receiveOrNull.receiveOrNull($completion);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of 'onReceiveCatching'")
    public static final /* synthetic */ SelectClause1 onReceiveOrNull(ReceiveChannel $this$onReceiveOrNull) {
        Intrinsics.checkNotNull($this$onReceiveOrNull, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E of kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.onReceiveOrNull?>");
        return $this$onReceiveOrNull.getOnReceiveOrNull();
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> receiveChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        try {
            R rInvoke = function1.invoke(receiveChannel);
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return rInvoke;
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x006d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f A[Catch: all -> 0x0099, TryCatch #3 {all -> 0x0099, blocks: (B:24:0x0077, B:26:0x007f, B:27:0x008e), top: B:45:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008e A[Catch: all -> 0x0099, TRY_LEAVE, TryCatch #3 {all -> 0x0099, blocks: (B:24:0x0077, B:26:0x007f, B:27:0x008e), top: B:45:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x006e -> B:45:0x0077). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r10, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) throws java.lang.Throwable {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L19
        L14:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r12)
        L19:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L4a;
                case 1: goto L2c;
                default: goto L24;
            }
        L24:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2c:
            r10 = 0
            r11 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch: java.lang.Throwable -> L47
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L77
        L47:
            r2 = move-exception
            goto La3
        L4a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r6 = r10
            r10 = 0
            r5 = 0
            r4 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch: java.lang.Throwable -> La0
            r4 = r11
            r11 = r10
            r10 = r3
            r3 = r7
            r7 = r4
            r4 = r8
        L5e:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L47
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L47
            r0.L$2 = r4     // Catch: java.lang.Throwable -> L47
            r8 = 1
            r0.label = r8     // Catch: java.lang.Throwable -> L47
            java.lang.Object r8 = r4.hasNext(r0)     // Catch: java.lang.Throwable -> L47
            if (r8 != r2) goto L6e
            return r2
        L6e:
            r9 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r9
        L77:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Throwable -> L99
            boolean r1 = r1.booleanValue()     // Catch: java.lang.Throwable -> L99
            if (r1 == 0) goto L8e
            java.lang.Object r1 = r5.next()     // Catch: java.lang.Throwable -> L99
            r8.invoke(r1)     // Catch: java.lang.Throwable -> L99
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L5e
        L8e:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L99
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L99:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            r5 = r6
            r6 = r7
            goto La3
        La0:
            r2 = move-exception
            r11 = r10
            r10 = r3
        La3:
            r3 = r2
            throw r2     // Catch: java.lang.Throwable -> La6
        La6:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <E> Object consumeEach$$forInline(ReceiveChannel<? extends E> receiveChannel, Function1<? super E, Unit> function1, Continuation<? super Unit> continuation) {
        try {
            ChannelIterator<? extends E> it = receiveChannel.iterator();
            while (((Boolean) it.hasNext(null)).booleanValue()) {
                Object e = it.next();
                function1.invoke(e);
            }
            Unit unit = Unit.INSTANCE;
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return Unit.INSTANCE;
        } finally {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0084 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009b A[Catch: all -> 0x00bf, TryCatch #4 {all -> 0x00bf, blocks: (B:24:0x0093, B:26:0x009b, B:27:0x00b0), top: B:49:0x0093 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00b0 A[Catch: all -> 0x00bf, TRY_LEAVE, TryCatch #4 {all -> 0x00bf, blocks: (B:24:0x0093, B:26:0x009b, B:27:0x00b0), top: B:49:0x0093 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0085 -> B:49:0x0093). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <E> java.lang.Object toList(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r17, kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 222
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toList(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final void cancelConsumed(ReceiveChannel<?> receiveChannel, Throwable cause) {
        if (cause != null) {
            CancellationException = cause instanceof CancellationException ? (CancellationException) cause : null;
            if (CancellationException == null) {
                CancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", cause);
            }
        }
        receiveChannel.cancel(CancellationException);
    }
}
