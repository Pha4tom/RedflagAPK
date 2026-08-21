package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectClause2Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.TrySelectDetailedResult;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b1\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0004ï\u0001ð\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\"\b\u0002\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0004\b\t\u0010\nJ\u0016\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u0000H\u0096@¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u0000H\u0082@¢\u0006\u0002\u0010#J4\u0010%\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u0011H\u0082@¢\u0006\u0002\u0010)J\"\u0010*\u001a\u00020\b*\u00020+2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J#\u0010,\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0.H\u0002¢\u0006\u0002\u0010/J\u001d\u00100\u001a\b\u0012\u0004\u0012\u00020\b012\u0006\u0010\"\u001a\u00028\u0000H\u0016¢\u0006\u0004\b2\u00103J\u0018\u00104\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00028\u0000H\u0090@¢\u0006\u0004\b5\u0010#Jê\u0001\u00106\u001a\u0002H7\"\u0004\b\u0001\u001072\u0006\u0010\"\u001a\u00028\u00002\b\u00108\u001a\u0004\u0018\u0001092\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H70;2<\u0010<\u001a8\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0004\u0012\u0002H70=2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70;2h\b\u0002\u0010C\u001ab\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b((\u0012\u0004\u0012\u0002H70DH\u0082\b¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\b\u0012\u0004\u0012\u00020\b012\u0006\u0010\"\u001a\u00028\u0000H\u0004¢\u0006\u0004\bG\u00103JX\u0010H\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\u0006\u00108\u001a\u00020+2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\b0;2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0;H\u0082\b¢\u0006\u0002\u0010IJE\u0010J\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010K\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010LJE\u0010M\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010K\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010LJ\u0010\u0010N\u001a\u00020\u001a2\u0006\u0010O\u001a\u00020\u0011H\u0003J\u0010\u0010P\u001a\u00020\u001a2\u0006\u0010Q\u001a\u00020\u0011H\u0002J\r\u0010N\u001a\u00020\u001aH\u0010¢\u0006\u0002\bRJ\u0019\u0010S\u001a\u00020\u001a*\u0002092\u0006\u0010\"\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010TJ\b\u0010U\u001a\u00020\bH\u0014J\b\u0010V\u001a\u00020\bH\u0014J\u000e\u0010W\u001a\u00028\u0000H\u0096@¢\u0006\u0002\u0010XJ;\u0010Y\u001a\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u0011H\u0082@¢\u0006\u0002\u0010[J\"\u0010\\\u001a\u00020\b*\u00020+2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J\u0016\u0010]\u001a\u00020\b2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0002J\u0016\u0010^\u001a\b\u0012\u0004\u0012\u00028\u000001H\u0096@¢\u0006\u0004\b_\u0010XJ4\u0010`\u001a\b\u0012\u0004\u0012\u00028\u0000012\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u0011H\u0082@¢\u0006\u0004\ba\u0010[J\u001c\u0010b\u001a\u00020\b2\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000010.H\u0002J\u0015\u0010c\u001a\b\u0012\u0004\u0012\u00028\u000001H\u0016¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\b2\u0006\u0010g\u001a\u00020\u0011H\u0004J÷\u0001\u0010h\u001a\u0002H7\"\u0004\b\u0001\u001072\b\u00108\u001a\u0004\u0018\u0001092!\u0010i\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H70\u00072Q\u0010<\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(Z\u0012\u0004\u0012\u0002H70j2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70;2S\b\u0002\u0010C\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(Z\u0012\u0004\u0012\u0002H70jH\u0082\b¢\u0006\u0002\u0010kJ`\u0010l\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\u0006\u00108\u001a\u00020+2!\u0010i\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\b0\u00072\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0;H\u0082\bJ2\u0010m\u001a\u0004\u0018\u0001092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u000109H\u0002J2\u0010n\u001a\u0004\u0018\u0001092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u000109H\u0002J\"\u0010o\u001a\u00020\u001a*\u0002092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J\b\u0010p\u001a\u00020\bH\u0002J&\u0010q\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010r\u001a\u00020\u0011H\u0002J&\u0010s\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010r\u001a\u00020\u0011H\u0002J\u0012\u0010t\u001a\u00020\b2\b\b\u0002\u0010u\u001a\u00020\u0011H\u0002J\u0015\u0010v\u001a\u00020\b2\u0006\u0010w\u001a\u00020\u0011H\u0000¢\u0006\u0002\bxJ \u0010\u007f\u001a\u00020\b2\f\u0010\u0080\u0001\u001a\u0007\u0012\u0002\b\u00030\u0081\u00012\b\u0010\"\u001a\u0004\u0018\u000109H\u0014J%\u0010\u0082\u0001\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u00002\f\u0010\u0080\u0001\u001a\u0007\u0012\u0002\b\u00030\u0081\u0001H\u0002¢\u0006\u0003\u0010\u0083\u0001J!\u0010\u0084\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0085\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0086\u0001\u001a\u0004\u0018\u000109H\u0002J\"\u0010\u0092\u0001\u001a\u00020\b2\f\u0010\u0080\u0001\u001a\u0007\u0012\u0002\b\u00030\u0081\u00012\t\u0010\u0085\u0001\u001a\u0004\u0018\u000109H\u0002J\u0017\u0010\u0093\u0001\u001a\u00020\b2\f\u0010\u0080\u0001\u001a\u0007\u0012\u0002\b\u00030\u0081\u0001H\u0002J!\u0010\u0094\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0085\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0086\u0001\u001a\u0004\u0018\u000109H\u0002J!\u0010\u0095\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0085\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0086\u0001\u001a\u0004\u0018\u000109H\u0002J!\u0010\u0096\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0085\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0086\u0001\u001a\u0004\u0018\u000109H\u0002J\u0011\u0010\u009f\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000 \u0001H\u0096\u0002J\t\u0010ª\u0001\u001a\u00020\bH\u0014J\u0015\u0010«\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0016J\u0013\u0010\u00ad\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u009b\u0001J\u0007\u0010\u00ad\u0001\u001a\u00020\bJ \u0010\u00ad\u0001\u001a\u00020\b2\u0011\u0010¬\u0001\u001a\f\u0018\u00010¯\u0001j\u0005\u0018\u0001`®\u0001¢\u0006\u0003\u0010°\u0001J\u001b\u0010±\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0010¢\u0006\u0003\b²\u0001J\u001e\u0010³\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u009b\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u001aH\u0014J\t\u0010´\u0001\u001a\u00020\bH\u0002J1\u0010µ\u0001\u001a\u00020\b2&\u0010¶\u0001\u001a!\u0012\u0017\u0012\u0015\u0018\u00010\u009b\u0001¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(¬\u0001\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\t\u0010·\u0001\u001a\u00020\bH\u0002J\t\u0010¸\u0001\u001a\u00020\bH\u0002J\t\u0010¹\u0001\u001a\u00020\bH\u0002J\t\u0010º\u0001\u001a\u00020\bH\u0002J\u0018\u0010¼\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0007\u0010½\u0001\u001a\u00020\u0011H\u0002J\u0012\u0010¾\u0001\u001a\u00020\b2\u0007\u0010½\u0001\u001a\u00020\u0011H\u0002J\u000f\u0010¿\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0018\u0010À\u0001\u001a\u00020\u00112\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0018\u0010Â\u0001\u001a\u00020\b2\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J \u0010Ã\u0001\u001a\u00020\b2\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\r\u0010Ä\u0001\u001a\u00020\b*\u00020+H\u0002J\r\u0010Å\u0001\u001a\u00020\b*\u00020+H\u0002J\u0016\u0010Æ\u0001\u001a\u00020\b*\u00020+2\u0007\u0010Ç\u0001\u001a\u00020\u001aH\u0002J\u001b\u0010Ï\u0001\u001a\u00020\u001a2\u0007\u0010Ð\u0001\u001a\u00020\u00112\u0007\u0010Ì\u0001\u001a\u00020\u001aH\u0002J\u000f\u0010Ó\u0001\u001a\u00020\u001aH\u0000¢\u0006\u0003\bÔ\u0001J'\u0010Õ\u0001\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010w\u001a\u00020\u0011H\u0002J)\u0010Ö\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J)\u0010Ù\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J2\u0010Ú\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0007\u0010Û\u0001\u001a\u00020\u0011H\u0002J!\u0010Ü\u0001\u001a\u00020\b2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0012\u0010Ý\u0001\u001a\u00020\b2\u0007\u0010Þ\u0001\u001a\u00020\u0011H\u0002J\u0012\u0010ß\u0001\u001a\u00020\b2\u0007\u0010Þ\u0001\u001a\u00020\u0011H\u0002J\n\u0010à\u0001\u001a\u00030á\u0001H\u0016J\u0010\u0010â\u0001\u001a\u00030á\u0001H\u0000¢\u0006\u0003\bã\u0001J\u0007\u0010ä\u0001\u001a\u00020\bJJ\u0010å\u0001\u001a#\u0012\u0005\u0012\u00030\u009b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000001\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\b0æ\u0001*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0002¢\u0006\u0003\u0010ç\u0001J4\u0010è\u0001\u001a\u00020\b2\b\u0010¬\u0001\u001a\u00030\u009b\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u0000012\b\u0010é\u0001\u001a\u00030\u009c\u0001H\u0002¢\u0006\u0006\bê\u0001\u0010ë\u0001JM\u0010ì\u0001\u001a\u001e\u0012\u0005\u0012\u00030\u009b\u0001\u0012\u0006\u0012\u0004\u0018\u000109\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\b0j*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u00062\u0006\u0010\"\u001a\u00028\u0000H\u0002¢\u0006\u0003\u0010í\u0001JD\u0010ì\u0001\u001a\u001d\u0012\u0005\u0012\u00030\u009b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\b0æ\u0001*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0002¢\u0006\u0003\u0010ç\u0001J+\u0010î\u0001\u001a\u00020\b2\b\u0010¬\u0001\u001a\u00030\u009b\u00012\u0006\u0010\"\u001a\u00028\u00002\b\u0010é\u0001\u001a\u00030\u009c\u0001H\u0002¢\u0006\u0003\u0010ë\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00068\u0000X\u0081\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\t\u0010\f\u001a\u00020\rX\u0082\u0004R\t\u0010\u000e\u001a\u00020\rX\u0082\u0004R\t\u0010\u000f\u001a\u00020\rX\u0082\u0004R\u0014\u0010\u0010\u001a\u00020\u00118@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00118@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\t\u0010\u0018\u001a\u00020\rX\u0082\u0004R\u0014\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0015\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0082\u0004R\u0015\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0082\u0004R\u0015\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0082\u0004R,\u0010y\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000z8VX\u0096\u0004¢\u0006\f\u0012\u0004\b{\u0010|\u001a\u0004\b}\u0010~R%\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u0088\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\b\u0089\u0001\u0010|\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R+\u0010\u008c\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000010\u0088\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\b\u008d\u0001\u0010|\u001a\u0006\b\u008e\u0001\u0010\u008b\u0001R'\u0010\u008f\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0088\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\b\u0090\u0001\u0010|\u001a\u0006\b\u0091\u0001\u0010\u008b\u0001R\u008f\u0001\u0010\u0097\u0001\u001ax\u0012\u0019\u0012\u0017\u0012\u0002\b\u00030\u0081\u0001¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u0080\u0001\u0012\u0016\u0012\u0014\u0018\u000109¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u0099\u0001\u0012\u0016\u0012\u0014\u0018\u000109¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u009a\u0001\u0012 \u0012\u001e\u0012\u0005\u0012\u00030\u009b\u0001\u0012\u0006\u0012\u0004\u0018\u000109\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\b0j\u0018\u00010jj\u0005\u0018\u0001`\u0098\u0001X\u0082\u0004¢\u0006\f\n\u0003\u0010\u009e\u0001\u0012\u0005\b\u009d\u0001\u0010|R\u0012\u0010¡\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001090\u001dX\u0082\u0004R\u001a\u0010¢\u0001\u001a\u0005\u0018\u00010\u009b\u00018DX\u0084\u0004¢\u0006\b\u001a\u0006\b£\u0001\u0010¤\u0001R\u0018\u0010¥\u0001\u001a\u00030\u009b\u00018DX\u0084\u0004¢\u0006\b\u001a\u0006\b¦\u0001\u0010¤\u0001R\u0018\u0010§\u0001\u001a\u00030\u009b\u00018BX\u0082\u0004¢\u0006\b\u001a\u0006\b¨\u0001\u0010¤\u0001R\u0012\u0010©\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001090\u001dX\u0082\u0004R\u0016\u0010»\u0001\u001a\u00020\u001a8TX\u0094\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010\u001bR\u001d\u0010È\u0001\u001a\u00020\u001a8VX\u0097\u0004¢\u0006\u000e\u0012\u0005\bÉ\u0001\u0010|\u001a\u0005\bÈ\u0001\u0010\u001bR\u001b\u0010Ê\u0001\u001a\u00020\u001a*\u00020\u00118BX\u0082\u0004¢\u0006\b\u001a\u0006\bÊ\u0001\u0010Ë\u0001R\u001d\u0010Ì\u0001\u001a\u00020\u001a8VX\u0097\u0004¢\u0006\u000e\u0012\u0005\bÍ\u0001\u0010|\u001a\u0005\bÌ\u0001\u0010\u001bR\u001b\u0010Î\u0001\u001a\u00020\u001a*\u00020\u00118BX\u0082\u0004¢\u0006\b\u001a\u0006\bÎ\u0001\u0010Ë\u0001R\u001d\u0010Ñ\u0001\u001a\u00020\u001a8VX\u0097\u0004¢\u0006\u000e\u0012\u0005\bÒ\u0001\u0010|\u001a\u0005\bÑ\u0001\u0010\u001b¨\u0006ñ\u0001"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel;", "E", "Lkotlinx/coroutines/channels/Channel;", "capacity", "", "onUndeliveredElement", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "Lkotlin/Function1;", "", "<init>", "(ILkotlin/jvm/functions/Function1;)V", "Lkotlin/jvm/functions/Function1;", "sendersAndCloseStatus", "Lkotlinx/atomicfu/AtomicLong;", "receivers", "bufferEnd", "sendersCounter", "", "getSendersCounter$kotlinx_coroutines_core", "()J", "receiversCounter", "getReceiversCounter$kotlinx_coroutines_core", "bufferEndCounter", "getBufferEndCounter", "completedExpandBuffersAndPauseFlag", "isRendezvousOrUnlimited", "", "()Z", "sendSegment", "Lkotlinx/atomicfu/AtomicRef;", "Lkotlinx/coroutines/channels/ChannelSegment;", "receiveSegment", "bufferEndSegment", "send", "element", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onClosedSend", "sendOnNoWaiterSuspend", "segment", "index", "s", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareSenderForSuspension", "Lkotlinx/coroutines/Waiter;", "onClosedSendOnNoWaiterSuspend", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "sendBroadcast", "sendBroadcast$kotlinx_coroutines_core", "sendImpl", "R", "waiter", "", "onRendezvousOrBuffered", "Lkotlin/Function0;", "onSuspend", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "segm", "i", "onClosed", "onNoWaiterSuspend", "Lkotlin/Function4;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "trySendDropOldest", "trySendDropOldest-JP2dKIU", "sendImplOnNoWaiter", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlinx/coroutines/Waiter;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "updateCellSend", "closed", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I", "updateCellSendSlow", "shouldSendSuspend", "curSendersAndCloseStatus", "bufferOrRendezvousSend", "curSenders", "shouldSendSuspend$kotlinx_coroutines_core", "tryResumeReceiver", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "onReceiveEnqueued", "onReceiveDequeued", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOnNoWaiterSuspend", "r", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareReceiverForSuspension", "onClosedReceiveOnNoWaiterSuspend", "receiveCatching", "receiveCatching-JP2dKIU", "receiveCatchingOnNoWaiterSuspend", "receiveCatchingOnNoWaiterSuspend-GKJJFZk", "onClosedReceiveCatchingOnNoWaiterSuspend", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "dropFirstElementUntilTheSpecifiedCellIsInTheBuffer", "globalCellIndex", "receiveImpl", "onElementRetrieved", "Lkotlin/Function3;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "receiveImplOnNoWaiter", "updateCellReceive", "updateCellReceiveSlow", "tryResumeSender", "expandBuffer", "updateCellExpandBuffer", "b", "updateCellExpandBufferSlow", "incCompletedExpandBufferAttempts", "nAttempts", "waitExpandBufferCompletion", "globalIndex", "waitExpandBufferCompletion$kotlinx_coroutines_core", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend$annotations", "()V", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "registerSelectForSend", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "onClosedSelectOnSend", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)V", "processResultSelectSend", "ignoredParam", "selectResult", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive$annotations", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "getOnReceiveCatching$annotations", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull$annotations", "getOnReceiveOrNull", "registerSelectForReceive", "onClosedSelectOnReceive", "processResultSelectReceive", "processResultSelectReceiveOrNull", "processResultSelectReceiveCatching", "onUndeliveredElementReceiveCancellationConstructor", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "param", "internalResult", "", "Lkotlin/coroutines/CoroutineContext;", "getOnUndeliveredElementReceiveCancellationConstructor$annotations", "Lkotlin/jvm/functions/Function3;", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "_closeCause", "closeCause", "getCloseCause", "()Ljava/lang/Throwable;", "sendException", "getSendException", "receiveException", "getReceiveException", "closeHandler", "onClosedIdempotent", "close", "cause", "cancel", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelImpl", "cancelImpl$kotlinx_coroutines_core", "closeOrCancelImpl", "invokeCloseHandler", "invokeOnClose", "handler", "markClosed", "markCancelled", "markCancellationStarted", "completeCloseOrCancel", "isConflatedDropOldest", "completeClose", "sendersCur", "completeCancel", "closeLinkedList", "markAllEmptyCellsAsClosed", "lastSegment", "removeUnprocessedElements", "cancelSuspendedReceiveRequests", "resumeReceiverOnClosedChannel", "resumeSenderOnCancelledChannel", "resumeWaiterOnClosedChannel", "receiver", "isClosedForSend", "isClosedForSend$annotations", "isClosedForSend0", "(J)Z", "isClosedForReceive", "isClosedForReceive$annotations", "isClosedForReceive0", "isClosed", "sendersAndCloseStatusCur", "isEmpty", "isEmpty$annotations", "hasElements", "hasElements$kotlinx_coroutines_core", "isCellNonEmpty", "findSegmentSend", "id", "startFrom", "findSegmentReceive", "findSegmentBufferEnd", "currentBufferEndCounter", "moveSegmentBufferEndToSpecifiedOrLast", "updateSendersCounterIfLower", "value", "updateReceiversCounterIfLower", "toString", "", "toStringDebug", "toStringDebug$kotlinx_coroutines_core", "checkSegmentStructureInvariants", "bindCancellationFunResult", "Lkotlin/reflect/KFunction3;", "(Lkotlin/jvm/functions/Function1;)Lkotlin/reflect/KFunction;", "onCancellationChannelResultImplDoNotCall", "context", "onCancellationChannelResultImplDoNotCall-5_sEAP8", "(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", "bindCancellationFun", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)Lkotlin/jvm/functions/Function3;", "onCancellationImplDoNotCall", "SendBroadcast", "BufferedChannelIterator", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class BufferedChannel<E> implements Channel<E> {
    private volatile /* synthetic */ Object _closeCause$volatile;
    private volatile /* synthetic */ long bufferEnd$volatile;
    private volatile /* synthetic */ Object bufferEndSegment$volatile;
    private final int capacity;
    private volatile /* synthetic */ Object closeHandler$volatile;
    private volatile /* synthetic */ long completedExpandBuffersAndPauseFlag$volatile;
    public final Function1<E, Unit> onUndeliveredElement;
    private final Function3<SelectInstance<?>, Object, Object, Function3<Throwable, Object, CoroutineContext, Unit>> onUndeliveredElementReceiveCancellationConstructor;
    private volatile /* synthetic */ Object receiveSegment$volatile;
    private volatile /* synthetic */ long receivers$volatile;
    private volatile /* synthetic */ Object sendSegment$volatile;
    private volatile /* synthetic */ long sendersAndCloseStatus$volatile;
    private static final /* synthetic */ AtomicLongFieldUpdater sendersAndCloseStatus$volatile$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "sendersAndCloseStatus$volatile");
    private static final /* synthetic */ AtomicLongFieldUpdater receivers$volatile$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "receivers$volatile");
    private static final /* synthetic */ AtomicLongFieldUpdater bufferEnd$volatile$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "bufferEnd$volatile");
    private static final /* synthetic */ AtomicLongFieldUpdater completedExpandBuffersAndPauseFlag$volatile$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "completedExpandBuffersAndPauseFlag$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater sendSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "sendSegment$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater receiveSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "receiveSegment$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater bufferEndSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "bufferEndSegment$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _closeCause$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "_closeCause$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater closeHandler$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "closeHandler$volatile");

    private final /* synthetic */ Object getAndUpdate$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, ? extends Object> function1) {
        Object obj2;
        do {
            obj2 = atomicReferenceFieldUpdater.get(obj);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, obj2, function1.invoke(obj2)));
        return obj2;
    }

    private final /* synthetic */ long getBufferEnd$volatile() {
        return this.bufferEnd$volatile;
    }

    private final /* synthetic */ Object getBufferEndSegment$volatile() {
        return this.bufferEndSegment$volatile;
    }

    private final /* synthetic */ Object getCloseHandler$volatile() {
        return this.closeHandler$volatile;
    }

    private final /* synthetic */ long getCompletedExpandBuffersAndPauseFlag$volatile() {
        return this.completedExpandBuffersAndPauseFlag$volatile;
    }

    public static /* synthetic */ void getOnReceive$annotations() {
    }

    public static /* synthetic */ void getOnReceiveCatching$annotations() {
    }

    public static /* synthetic */ void getOnReceiveOrNull$annotations() {
    }

    public static /* synthetic */ void getOnSend$annotations() {
    }

    private static /* synthetic */ void getOnUndeliveredElementReceiveCancellationConstructor$annotations() {
    }

    private final /* synthetic */ Object getReceiveSegment$volatile() {
        return this.receiveSegment$volatile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater getReceiveSegment$volatile$FU() {
        return receiveSegment$volatile$FU;
    }

    private final /* synthetic */ long getReceivers$volatile() {
        return this.receivers$volatile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater getReceivers$volatile$FU() {
        return receivers$volatile$FU;
    }

    private final /* synthetic */ Object getSendSegment$volatile() {
        return this.sendSegment$volatile;
    }

    private final /* synthetic */ long getSendersAndCloseStatus$volatile() {
        return this.sendersAndCloseStatus$volatile;
    }

    private final /* synthetic */ Object get_closeCause$volatile() {
        return this._closeCause$volatile;
    }

    public static /* synthetic */ void isClosedForReceive$annotations() {
    }

    public static /* synthetic */ void isClosedForSend$annotations() {
    }

    public static /* synthetic */ void isEmpty$annotations() {
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Unit> function1) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void setBufferEnd$volatile(long j) {
        this.bufferEnd$volatile = j;
    }

    private final /* synthetic */ void setBufferEndSegment$volatile(Object obj) {
        this.bufferEndSegment$volatile = obj;
    }

    private final /* synthetic */ void setCloseHandler$volatile(Object obj) {
        this.closeHandler$volatile = obj;
    }

    private final /* synthetic */ void setCompletedExpandBuffersAndPauseFlag$volatile(long j) {
        this.completedExpandBuffersAndPauseFlag$volatile = j;
    }

    private final /* synthetic */ void setReceiveSegment$volatile(Object obj) {
        this.receiveSegment$volatile = obj;
    }

    private final /* synthetic */ void setReceivers$volatile(long j) {
        this.receivers$volatile = j;
    }

    private final /* synthetic */ void setSendSegment$volatile(Object obj) {
        this.sendSegment$volatile = obj;
    }

    private final /* synthetic */ void setSendersAndCloseStatus$volatile(long j) {
        this.sendersAndCloseStatus$volatile = j;
    }

    private final /* synthetic */ void set_closeCause$volatile(Object obj) {
        this._closeCause$volatile = obj;
    }

    private final /* synthetic */ void update$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1) {
        while (true) {
            long j = atomicLongFieldUpdater.get(obj);
            Object obj2 = obj;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = atomicLongFieldUpdater;
            if (atomicLongFieldUpdater2.compareAndSet(obj2, j, function1.invoke(Long.valueOf(j)).longValue())) {
                return;
            }
            atomicLongFieldUpdater = atomicLongFieldUpdater2;
            obj = obj2;
        }
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public Object receive(Continuation<? super E> continuation) {
        return receive$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    public Object mo1778receiveCatchingJP2dKIU(Continuation<? super ChannelResult<? extends E>> continuation) {
        return m1776receiveCatchingJP2dKIU$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public Object send(E e, Continuation<? super Unit> continuation) {
        return send$suspendImpl(this, e, continuation);
    }

    public Object sendBroadcast$kotlinx_coroutines_core(E e, Continuation<? super Boolean> continuation) {
        return sendBroadcast$suspendImpl(this, e, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BufferedChannel(int capacity, Function1<? super E, Unit> function1) {
        ChannelSegment channelSegment;
        this.capacity = capacity;
        this.onUndeliveredElement = function1;
        if (this.capacity >= 0) {
            this.bufferEnd$volatile = BufferedChannelKt.initialBufferEnd(this.capacity);
            this.completedExpandBuffersAndPauseFlag$volatile = getBufferEndCounter();
            ChannelSegment firstSegment = new ChannelSegment(0L, null, this, 3);
            this.sendSegment$volatile = firstSegment;
            this.receiveSegment$volatile = firstSegment;
            if (isRendezvousOrUnlimited()) {
                channelSegment = BufferedChannelKt.NULL_SEGMENT;
                Intrinsics.checkNotNull(channelSegment, "null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelSegment<E of kotlinx.coroutines.channels.BufferedChannel>");
            } else {
                channelSegment = firstSegment;
            }
            this.bufferEndSegment$volatile = channelSegment;
            this.onUndeliveredElementReceiveCancellationConstructor = this.onUndeliveredElement != null ? new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(this.f$0, (SelectInstance) obj, obj2, obj3);
                }
            } : null;
            this._closeCause$volatile = BufferedChannelKt.NO_CLOSE_CAUSE;
            return;
        }
        throw new IllegalArgumentException(("Invalid channel capacity: " + this.capacity + ", should be >=0").toString());
    }

    public /* synthetic */ BufferedChannel(int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : function1);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e) {
        return Channel.DefaultImpls.offer(this, e);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    public E poll() {
        return (E) Channel.DefaultImpls.poll(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public Object receiveOrNull(Continuation<? super E> continuation) {
        return Channel.DefaultImpls.receiveOrNull(this, continuation);
    }

    public final long getSendersCounter$kotlinx_coroutines_core() {
        long $this$sendersCounter$iv = sendersAndCloseStatus$volatile$FU.get(this);
        return $this$sendersCounter$iv & 1152921504606846975L;
    }

    public final long getReceiversCounter$kotlinx_coroutines_core() {
        return receivers$volatile$FU.get(this);
    }

    private final long getBufferEndCounter() {
        return bufferEnd$volatile$FU.get(this);
    }

    private final boolean isRendezvousOrUnlimited() {
        long it = getBufferEndCounter();
        return it == 0 || it == Long.MAX_VALUE;
    }

    static /* synthetic */ <E> Object send$suspendImpl(BufferedChannel<E> bufferedChannel, E e, Continuation<? super Unit> continuation) throws Throwable {
        int $i$f$sendImpl = 0;
        ChannelSegment<E> channelSegment = (ChannelSegment) sendSegment$volatile$FU.get(bufferedChannel);
        while (true) {
            long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(bufferedChannel);
            long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
            boolean closed$iv = bufferedChannel.isClosedForSend0(sendersAndCloseStatusCur$iv);
            long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id != id$iv) {
                ChannelSegment<E> channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(id$iv, channelSegment);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (closed$iv) {
                    Object objOnClosedSend = bufferedChannel.onClosedSend(e, continuation);
                    if (objOnClosedSend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend;
                    }
                }
            }
            switch (bufferedChannel.updateCellSend(channelSegment, i$iv, e, s$iv, null, closed$iv)) {
                case 0:
                    channelSegment.cleanPrev();
                    break;
                case 1:
                    break;
                case 2:
                    if (closed$iv) {
                        channelSegment.onSlotCleaned();
                        Object objOnClosedSend2 = bufferedChannel.onClosedSend(e, continuation);
                        if (objOnClosedSend2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                            return objOnClosedSend2;
                        }
                    } else if (DebugKt.getASSERTIONS_ENABLED()) {
                        throw new AssertionError();
                    }
                case 3:
                    Object objSendOnNoWaiterSuspend = bufferedChannel.sendOnNoWaiterSuspend(channelSegment, i$iv, e, s$iv, continuation);
                    if (objSendOnNoWaiterSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objSendOnNoWaiterSuspend;
                    }
                    break;
                case 4:
                    if (s$iv < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    Object objOnClosedSend3 = bufferedChannel.onClosedSend(e, continuation);
                    if (objOnClosedSend3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend3;
                    }
                    break;
                case 5:
                    channelSegment.cleanPrev();
                default:
                    $i$f$sendImpl = $i$f$sendImpl;
                    break;
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object onClosedSend(E e, Continuation<? super Unit> continuation) {
        Throwable thRecoverFromStackFrame;
        UndeliveredElementException it;
        UndeliveredElementException undeliveredElementExceptionRecoverFromStackFrame;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellable$iv.initCancellability();
        CancellableContinuationImpl continuation2 = cancellable$iv;
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null && (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) != null) {
            ExceptionsKt.addSuppressed(it, getSendException());
            CancellableContinuationImpl $this$resumeWithStackTrace$iv = continuation2;
            Result.Companion companion = Result.INSTANCE;
            if (DebugKt.getRECOVER_STACK_TRACES() && ($this$resumeWithStackTrace$iv instanceof CoroutineStackFrame)) {
                undeliveredElementExceptionRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(it, $this$resumeWithStackTrace$iv);
            } else {
                undeliveredElementExceptionRecoverFromStackFrame = it;
            }
            $this$resumeWithStackTrace$iv.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(undeliveredElementExceptionRecoverFromStackFrame)));
        } else {
            CancellableContinuationImpl $this$resumeWithStackTrace$iv2 = continuation2;
            Throwable exception$iv = getSendException();
            Result.Companion companion2 = Result.INSTANCE;
            if (DebugKt.getRECOVER_STACK_TRACES() && ($this$resumeWithStackTrace$iv2 instanceof CoroutineStackFrame)) {
                thRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, $this$resumeWithStackTrace$iv2);
            } else {
                thRecoverFromStackFrame = exception$iv;
            }
            $this$resumeWithStackTrace$iv2.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(thRecoverFromStackFrame)));
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object sendOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int index, E e, long s, Continuation<? super Unit> continuation) throws Throwable {
        CancellableContinuationImpl cancellable$iv = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cont = cancellable$iv;
        try {
            try {
            } catch (Throwable th) {
                e$iv = th;
            }
        } catch (Throwable th2) {
            e$iv = th2;
        }
        try {
            switch (updateCellSend(channelSegment, index, e, s, cont, false)) {
                case 0:
                    channelSegment.cleanPrev();
                    Result.Companion companion = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Unit.INSTANCE));
                    break;
                case 1:
                    Result.Companion companion2 = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Unit.INSTANCE));
                    break;
                case 2:
                    prepareSenderForSuspension(cont, channelSegment, index);
                    break;
                case 3:
                default:
                    throw new IllegalStateException("unexpected".toString());
                case 4:
                    if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    onClosedSendOnNoWaiterSuspend(e, cont);
                    break;
                case 5:
                    channelSegment.cleanPrev();
                    ChannelSegment segment$iv$iv = (ChannelSegment) sendSegment$volatile$FU.get(this);
                    while (true) {
                        long sendersAndCloseStatusCur$iv$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
                        long s$iv$iv = sendersAndCloseStatusCur$iv$iv & 1152921504606846975L;
                        boolean closed$iv$iv = isClosedForSend0(sendersAndCloseStatusCur$iv$iv);
                        CancellableContinuationImpl cont2 = cont;
                        long id$iv$iv = s$iv$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                        int i$iv$iv = (int) (s$iv$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                        if (segment$iv$iv.id != id$iv$iv) {
                            ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(id$iv$iv, segment$iv$iv);
                            if (channelSegmentFindSegmentSend != null) {
                                segment$iv$iv = channelSegmentFindSegmentSend;
                            } else if (closed$iv$iv) {
                                onClosedSendOnNoWaiterSuspend(e, cont2);
                            } else {
                                cont = cont2;
                            }
                            break;
                        }
                        switch (updateCellSend(segment$iv$iv, i$iv$iv, e, s$iv$iv, cont2, closed$iv$iv)) {
                            case 0:
                                segment$iv$iv.cleanPrev();
                                Result.Companion companion3 = Result.INSTANCE;
                                cont2.resumeWith(Result.m266constructorimpl(Unit.INSTANCE));
                                break;
                            case 1:
                                Result.Companion companion4 = Result.INSTANCE;
                                cont2.resumeWith(Result.m266constructorimpl(Unit.INSTANCE));
                                break;
                            case 2:
                                if (!closed$iv$iv) {
                                    CancellableContinuationImpl cancellableContinuationImpl = cont2 instanceof Waiter ? cont2 : null;
                                    if (cancellableContinuationImpl != null) {
                                        prepareSenderForSuspension(cancellableContinuationImpl, segment$iv$iv, i$iv$iv);
                                    }
                                } else {
                                    segment$iv$iv.onSlotCleaned();
                                    onClosedSendOnNoWaiterSuspend(e, cont2);
                                }
                                break;
                            case 3:
                                throw new IllegalStateException("unexpected".toString());
                            case 4:
                                if (s$iv$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                                    segment$iv$iv.cleanPrev();
                                }
                                onClosedSendOnNoWaiterSuspend(e, cont2);
                                break;
                            case 5:
                                segment$iv$iv.cleanPrev();
                                cont = cont2;
                                break;
                            default:
                                cont = cont2;
                                break;
                        }
                    }
                    break;
            }
            Object result = cancellable$iv.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        } catch (Throwable th3) {
            e$iv = th3;
            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw e$iv;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareSenderForSuspension(Waiter $this$prepareSenderForSuspension, ChannelSegment<E> channelSegment, int index) {
        $this$prepareSenderForSuspension.invokeOnCancellation(channelSegment, BufferedChannelKt.SEGMENT_SIZE + index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedSendOnNoWaiterSuspend(E element, CancellableContinuation<? super Unit> cont) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, cont.getContext());
        }
        CancellableContinuation<? super Unit> cancellableContinuation = cont;
        Throwable exception$iv = getSendException();
        if (DebugKt.getRECOVER_STACK_TRACES() && (cont instanceof CoroutineStackFrame)) {
            exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) cont);
        }
        Result.Companion companion = Result.INSTANCE;
        cancellableContinuation.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(exception$iv)));
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: trySend-JP2dKIU */
    public Object mo1771trySendJP2dKIU(E element) {
        ChannelSegment segment$iv;
        if (!shouldSendSuspend(sendersAndCloseStatus$volatile$FU.get(this))) {
            Object waiter$iv = BufferedChannelKt.INTERRUPTED_SEND;
            ChannelSegment segment$iv2 = (ChannelSegment) sendSegment$volatile$FU.get(this);
            while (true) {
                long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
                long s$iv = 1152921504606846975L & sendersAndCloseStatusCur$iv;
                boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
                long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (segment$iv2.id != id$iv) {
                    segment$iv = findSegmentSend(id$iv, segment$iv2);
                    if (segment$iv == null) {
                        if (closed$iv) {
                            return ChannelResult.INSTANCE.m1799closedJP2dKIU(getSendException());
                        }
                    }
                } else {
                    segment$iv = segment$iv2;
                }
                switch (updateCellSend(segment$iv, i$iv, element, s$iv, waiter$iv, closed$iv)) {
                    case 0:
                        segment$iv.cleanPrev();
                        return ChannelResult.INSTANCE.m1801successJP2dKIU(Unit.INSTANCE);
                    case 1:
                        return ChannelResult.INSTANCE.m1801successJP2dKIU(Unit.INSTANCE);
                    case 2:
                        if (closed$iv) {
                            segment$iv.onSlotCleaned();
                            return ChannelResult.INSTANCE.m1799closedJP2dKIU(getSendException());
                        }
                        Waiter waiter = waiter$iv instanceof Waiter ? (Waiter) waiter$iv : null;
                        if (waiter != null) {
                            prepareSenderForSuspension(waiter, segment$iv, i$iv);
                        }
                        ChannelSegment segm = segment$iv;
                        segm.onSlotCleaned();
                        return ChannelResult.INSTANCE.m1800failurePtdJZtk();
                    case 3:
                        throw new IllegalStateException("unexpected".toString());
                    case 4:
                        if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                            segment$iv.cleanPrev();
                        }
                        return ChannelResult.INSTANCE.m1799closedJP2dKIU(getSendException());
                    case 5:
                        segment$iv.cleanPrev();
                    default:
                        segment$iv2 = segment$iv;
                        break;
                }
            }
        } else {
            return ChannelResult.INSTANCE.m1800failurePtdJZtk();
        }
    }

    static /* synthetic */ <E> Object sendBroadcast$suspendImpl(BufferedChannel<E> bufferedChannel, E e, Continuation<? super Boolean> continuation) {
        boolean z = true;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellable$iv.initCancellability();
        CancellableContinuationImpl cont = cancellable$iv;
        if (!(bufferedChannel.onUndeliveredElement == null)) {
            throw new IllegalStateException("the `onUndeliveredElement` feature is unsupported for `sendBroadcast(e)`".toString());
        }
        Object waiter$iv = new SendBroadcast(cont);
        ChannelSegment segment$iv = (ChannelSegment) sendSegment$volatile$FU.get(bufferedChannel);
        while (true) {
            long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(bufferedChannel);
            long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
            boolean closed$iv = bufferedChannel.isClosedForSend0(sendersAndCloseStatusCur$iv);
            boolean z2 = z;
            long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(id$iv, segment$iv);
                if (channelSegmentFindSegmentSend != null) {
                    segment$iv = channelSegmentFindSegmentSend;
                } else if (closed$iv) {
                    Result.Companion companion = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Boxing.boxBoolean(false)));
                } else {
                    z = z2;
                }
            }
            switch (bufferedChannel.updateCellSend(segment$iv, i$iv, e, s$iv, waiter$iv, closed$iv)) {
                case 0:
                    segment$iv.cleanPrev();
                    Result.Companion companion2 = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Boxing.boxBoolean(z2)));
                    break;
                case 1:
                    Result.Companion companion3 = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Boxing.boxBoolean(z2)));
                    break;
                case 2:
                    if (closed$iv) {
                        segment$iv.onSlotCleaned();
                        Result.Companion companion4 = Result.INSTANCE;
                        cont.resumeWith(Result.m266constructorimpl(Boxing.boxBoolean(false)));
                    } else {
                        SendBroadcast sendBroadcast = waiter$iv instanceof Waiter ? (Waiter) waiter$iv : null;
                        if (sendBroadcast != null) {
                            bufferedChannel.prepareSenderForSuspension(sendBroadcast, segment$iv, i$iv);
                        }
                    }
                    break;
                case 3:
                    throw new IllegalStateException("unexpected".toString());
                case 4:
                    if (s$iv < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                    Result.Companion companion5 = Result.INSTANCE;
                    cont.resumeWith(Result.m266constructorimpl(Boxing.boxBoolean(false)));
                    break;
                case 5:
                    segment$iv.cleanPrev();
                default:
                    z = z2;
                    break;
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$SendBroadcast;", "Lkotlinx/coroutines/Waiter;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "<init>", "(Lkotlinx/coroutines/CancellableContinuation;)V", "getCont", "()Lkotlinx/coroutines/CancellableContinuation;", "invokeOnCancellation", "", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private static final class SendBroadcast implements Waiter {
        private final /* synthetic */ CancellableContinuationImpl<Boolean> $$delegate_0;
        private final CancellableContinuation<Boolean> cont;

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment<?> segment, int index) {
            this.$$delegate_0.invokeOnCancellation(segment, index);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SendBroadcast(CancellableContinuation<? super Boolean> cancellableContinuation) {
            Intrinsics.checkNotNull(cancellableContinuation, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlin.Boolean>");
            this.$$delegate_0 = (CancellableContinuationImpl) cancellableContinuation;
            this.cont = cancellableContinuation;
        }

        public final CancellableContinuation<Boolean> getCont() {
            return this.cont;
        }
    }

    static /* synthetic */ Object sendImpl$default(BufferedChannel $this, Object element, Object waiter, Function0 onRendezvousOrBuffered, Function2 onSuspend, Function0 onClosed, Function4 onNoWaiterSuspend, int i, Object obj) {
        Function4 onNoWaiterSuspend2;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendImpl");
        }
        if ((i & 32) == 0) {
            onNoWaiterSuspend2 = onNoWaiterSuspend;
        } else {
            onNoWaiterSuspend2 = new Function4() { // from class: kotlinx.coroutines.channels.BufferedChannel.sendImpl.1
                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2, Object p3, Object p4) {
                    return invoke((ChannelSegment<Object>) p1, ((Number) p2).intValue(), p3, ((Number) p4).longValue());
                }

                public final Void invoke(ChannelSegment<E> channelSegment, int i2, E e, long j) {
                    throw new IllegalStateException("unexpected".toString());
                }
            };
        }
        ChannelSegment segment = (ChannelSegment) sendSegment$volatile$FU.get($this);
        while (true) {
            long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.getAndIncrement($this);
            long s = sendersAndCloseStatusCur & 1152921504606846975L;
            boolean closed = $this.isClosedForSend0(sendersAndCloseStatusCur);
            long id = s / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i2 = (int) (s % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment channelSegmentFindSegmentSend = $this.findSegmentSend(id, segment);
                if (channelSegmentFindSegmentSend != null) {
                    segment = channelSegmentFindSegmentSend;
                } else if (closed) {
                    return onClosed.invoke();
                }
            }
            switch ($this.updateCellSend(segment, i2, element, s, waiter, closed)) {
                case 0:
                    segment.cleanPrev();
                    return onRendezvousOrBuffered.invoke();
                case 1:
                    return onRendezvousOrBuffered.invoke();
                case 2:
                    if (closed) {
                        segment.onSlotCleaned();
                        return onClosed.invoke();
                    }
                    Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                    if (waiter2 != null) {
                        $this.prepareSenderForSuspension(waiter2, segment, i2);
                    }
                    return onSuspend.invoke(segment, Integer.valueOf(i2));
                case 3:
                    return onNoWaiterSuspend2.invoke(segment, Integer.valueOf(i2), element, Long.valueOf(s));
                case 4:
                    if (s < $this.getReceiversCounter$kotlinx_coroutines_core()) {
                        segment.cleanPrev();
                    }
                    return onClosed.invoke();
                case 5:
                    segment.cleanPrev();
                    break;
            }
        }
    }

    private final <R> R sendImpl(E element, Object waiter, Function0<? extends R> onRendezvousOrBuffered, Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> onSuspend, Function0<? extends R> onClosed, Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment segment = (ChannelSegment) sendSegment$volatile$FU.get(this);
        while (true) {
            long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
            long s = sendersAndCloseStatusCur & 1152921504606846975L;
            boolean closed = isClosedForSend0(sendersAndCloseStatusCur);
            long id = s / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (s % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(id, segment);
                if (channelSegmentFindSegmentSend != null) {
                    segment = channelSegmentFindSegmentSend;
                } else if (closed) {
                    return onClosed.invoke();
                }
            }
            switch (updateCellSend(segment, i, element, s, waiter, closed)) {
                case 0:
                    segment.cleanPrev();
                    return onRendezvousOrBuffered.invoke();
                case 1:
                    return onRendezvousOrBuffered.invoke();
                case 2:
                    if (closed) {
                        segment.onSlotCleaned();
                        return onClosed.invoke();
                    }
                    Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                    if (waiter2 != null) {
                        prepareSenderForSuspension(waiter2, segment, i);
                    }
                    return onSuspend.invoke(segment, Integer.valueOf(i));
                case 3:
                    return onNoWaiterSuspend.invoke(segment, Integer.valueOf(i), element, Long.valueOf(s));
                case 4:
                    if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                        segment.cleanPrev();
                    }
                    return onClosed.invoke();
                case 5:
                    segment.cleanPrev();
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004c, code lost:
    
        return kotlinx.coroutines.channels.ChannelResult.INSTANCE.m1799closedJP2dKIU(getSendException());
     */
    /* JADX INFO: renamed from: trySendDropOldest-JP2dKIU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final java.lang.Object m1780trySendDropOldestJP2dKIU(E r20) {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1780trySendDropOldestJP2dKIU(java.lang.Object):java.lang.Object");
    }

    private final void sendImplOnNoWaiter(ChannelSegment<E> segment, int index, E element, long s, Waiter waiter, Function0<Unit> onRendezvousOrBuffered, Function0<Unit> onClosed) {
        Unit unitInvoke;
        switch (updateCellSend(segment, index, element, s, waiter, false)) {
            case 0:
                segment.cleanPrev();
                onRendezvousOrBuffered.invoke();
                return;
            case 1:
                onRendezvousOrBuffered.invoke();
                return;
            case 2:
                prepareSenderForSuspension(waiter, segment, index);
                return;
            case 3:
            default:
                throw new IllegalStateException("unexpected".toString());
            case 4:
                if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                    segment.cleanPrev();
                }
                onClosed.invoke();
                return;
            case 5:
                segment.cleanPrev();
                ChannelSegment segment$iv = (ChannelSegment) sendSegment$volatile$FU.get(this);
                while (true) {
                    long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
                    long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
                    boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
                    long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                    int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                    if (segment$iv.id != id$iv) {
                        ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(id$iv, segment$iv);
                        if (channelSegmentFindSegmentSend != null) {
                            segment$iv = channelSegmentFindSegmentSend;
                        } else if (closed$iv) {
                            unitInvoke = onClosed.invoke();
                        }
                    }
                    switch (updateCellSend(segment$iv, i$iv, element, s$iv, waiter, closed$iv)) {
                        case 0:
                            segment$iv.cleanPrev();
                            unitInvoke = onRendezvousOrBuffered.invoke();
                            break;
                        case 1:
                            unitInvoke = onRendezvousOrBuffered.invoke();
                            break;
                        case 2:
                            if (!closed$iv) {
                                Waiter waiter2 = waiter instanceof Waiter ? waiter : null;
                                if (waiter2 != null) {
                                    prepareSenderForSuspension(waiter2, segment$iv, i$iv);
                                }
                                unitInvoke = Unit.INSTANCE;
                            } else {
                                segment$iv.onSlotCleaned();
                                unitInvoke = onClosed.invoke();
                            }
                            break;
                        case 3:
                            throw new IllegalStateException("unexpected".toString());
                        case 4:
                            if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                                segment$iv.cleanPrev();
                            }
                            unitInvoke = onClosed.invoke();
                            break;
                        case 5:
                            segment$iv.cleanPrev();
                            continue;
                    }
                }
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int updateCellSend(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        segment.storeElement$kotlinx_coroutines_core(index, element);
        if (closed) {
            return updateCellSendSlow(segment, index, element, s, waiter, closed);
        }
        Object state = segment.getState$kotlinx_coroutines_core(index);
        if (state == null) {
            if (bufferOrRendezvousSend(s)) {
                if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (waiter == null) {
                    return 3;
                }
                if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                    return 2;
                }
            }
        } else if (state instanceof Waiter) {
            segment.cleanElement$kotlinx_coroutines_core(index);
            if (tryResumeReceiver(state, element)) {
                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                onReceiveDequeued();
                return 0;
            }
            if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                segment.onCancelledRequest(index, true);
            }
            return 5;
        }
        return updateCellSendSlow(segment, index, element, s, waiter, closed);
    }

    private final int updateCellSendSlow(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state != null) {
                if (state != BufferedChannelKt.IN_BUFFER) {
                    if (state != BufferedChannelKt.INTERRUPTED_RCV) {
                        if (state == BufferedChannelKt.POISONED) {
                            segment.cleanElement$kotlinx_coroutines_core(index);
                            return 5;
                        }
                        if (state == BufferedChannelKt.getCHANNEL_CLOSED()) {
                            segment.cleanElement$kotlinx_coroutines_core(index);
                            completeCloseOrCancel();
                            return 4;
                        }
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if ((((state instanceof Waiter) || (state instanceof WaiterEB)) ? 1 : 0) == 0) {
                                throw new AssertionError();
                            }
                        }
                        segment.cleanElement$kotlinx_coroutines_core(index);
                        Object receiver = state instanceof WaiterEB ? ((WaiterEB) state).waiter : state;
                        if (tryResumeReceiver(receiver, element)) {
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                            onReceiveDequeued();
                            return 0;
                        }
                        if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                            segment.onCancelledRequest(index, true);
                        }
                        return 5;
                    }
                    segment.cleanElement$kotlinx_coroutines_core(index);
                    return 5;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (bufferOrRendezvousSend(s) && !closed) {
                if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (closed) {
                if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.INTERRUPTED_SEND)) {
                    segment.onCancelledRequest(index, false);
                    return 4;
                }
            } else {
                if (waiter == null) {
                    return 3;
                }
                if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                    return 2;
                }
            }
        }
    }

    private final boolean shouldSendSuspend(long curSendersAndCloseStatus) {
        if (isClosedForSend0(curSendersAndCloseStatus)) {
            return false;
        }
        long $this$sendersCounter$iv = curSendersAndCloseStatus & 1152921504606846975L;
        return !bufferOrRendezvousSend($this$sendersCounter$iv);
    }

    private final boolean bufferOrRendezvousSend(long curSenders) {
        return curSenders < getBufferEndCounter() || curSenders < getReceiversCounter$kotlinx_coroutines_core() + ((long) this.capacity);
    }

    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return shouldSendSuspend(sendersAndCloseStatus$volatile$FU.get(this));
    }

    private final boolean tryResumeReceiver(Object $this$tryResumeReceiver, E e) {
        if ($this$tryResumeReceiver instanceof SelectInstance) {
            return ((SelectInstance) $this$tryResumeReceiver).trySelect(this, e);
        }
        if ($this$tryResumeReceiver instanceof ReceiveCatching) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveCatching<E of kotlinx.coroutines.channels.BufferedChannel>");
            CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = ((ReceiveCatching) $this$tryResumeReceiver).cont;
            ChannelResult channelResultM1786boximpl = ChannelResult.m1786boximpl(ChannelResult.INSTANCE.m1801successJP2dKIU(e));
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, channelResultM1786boximpl, (Function3) (function1 != null ? bindCancellationFunResult(function1) : null));
        }
        if ($this$tryResumeReceiver instanceof BufferedChannelIterator) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator<E of kotlinx.coroutines.channels.BufferedChannel>");
            return ((BufferedChannelIterator) $this$tryResumeReceiver).tryResumeHasNext(e);
        }
        if ($this$tryResumeReceiver instanceof CancellableContinuation) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<E of kotlinx.coroutines.channels.BufferedChannel>");
            CancellableContinuation cancellableContinuation = (CancellableContinuation) $this$tryResumeReceiver;
            Function1<E, Unit> function12 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuation, e, (Function3) (function12 != null ? bindCancellationFun(function12) : null));
        }
        throw new IllegalStateException(("Unexpected receiver type: " + $this$tryResumeReceiver).toString());
    }

    protected void onReceiveEnqueued() {
    }

    protected void onReceiveDequeued() {
    }

    static /* synthetic */ <E> Object receive$suspendImpl(BufferedChannel<E> bufferedChannel, Continuation<? super E> continuation) throws Throwable {
        ChannelSegment<E> channelSegment = (ChannelSegment) getReceiveSegment$volatile$FU().get(bufferedChannel);
        while (!bufferedChannel.isClosedForReceive()) {
            long r$iv = getReceivers$volatile$FU().getAndIncrement(bufferedChannel);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id != id$iv) {
                ChannelSegment<E> channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(id$iv, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Object updCellResult$iv = bufferedChannel.updateCellReceive(channelSegment, i$iv, r$iv, null);
            if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                if (updCellResult$iv != BufferedChannelKt.FAILED) {
                    if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return bufferedChannel.receiveOnNoWaiterSuspend(channelSegment, i$iv, r$iv, continuation);
                    }
                    channelSegment.cleanPrev();
                    return updCellResult$iv;
                }
                if (r$iv < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
            } else {
                throw new IllegalStateException("unexpected".toString());
            }
        }
        throw StackTraceRecoveryKt.recoverStackTrace(bufferedChannel.getReceiveException());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v9, types: [kotlinx.coroutines.Waiter] */
    public final Object receiveOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int index, long r, Continuation<? super E> continuation) throws Throwable {
        CancellableContinuationImpl cancellable$iv;
        CancellableContinuationImpl cancellable$iv2;
        int $i$f$suspendCancellableCoroutineReusable = 0;
        Continuation<? super E> continuation2 = continuation;
        int i = 0;
        CancellableContinuationImpl cancellable$iv3 = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation2));
        try {
            try {
                Object updCellResult$iv = updateCellReceive(channelSegment, index, r, cancellable$iv3);
                try {
                    if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                        try {
                            try {
                                if (updCellResult$iv != BufferedChannelKt.FAILED) {
                                    cancellable$iv2 = cancellable$iv3;
                                    channelSegment.cleanPrev();
                                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                                    cancellable$iv3.resume(updCellResult$iv, (Function3<? super Throwable, ? super Object, ? super CoroutineContext, Unit>) (function1 != null ? bindCancellationFun(function1) : null));
                                } else {
                                    if (r < getSendersCounter$kotlinx_coroutines_core()) {
                                        channelSegment.cleanPrev();
                                    }
                                    ChannelSegment segment$iv$iv = (ChannelSegment) getReceiveSegment$volatile$FU().get(this);
                                    while (!isClosedForReceive()) {
                                        long r$iv$iv = getReceivers$volatile$FU().getAndIncrement(this);
                                        int $i$f$suspendCancellableCoroutineReusable2 = $i$f$suspendCancellableCoroutineReusable;
                                        Continuation<? super E> continuation3 = continuation2;
                                        try {
                                            long id$iv$iv = r$iv$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                                            int i2 = i;
                                            cancellable$iv2 = cancellable$iv3;
                                            int i$iv$iv = (int) (r$iv$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                                            if (segment$iv$iv.id != id$iv$iv) {
                                                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(id$iv$iv, segment$iv$iv);
                                                if (channelSegmentFindSegmentReceive != null) {
                                                    segment$iv$iv = channelSegmentFindSegmentReceive;
                                                } else {
                                                    $i$f$suspendCancellableCoroutineReusable = $i$f$suspendCancellableCoroutineReusable2;
                                                    continuation2 = continuation3;
                                                    i = i2;
                                                    cancellable$iv3 = cancellable$iv2;
                                                }
                                            }
                                            Object updCellResult$iv$iv = updateCellReceive(segment$iv$iv, i$iv$iv, r$iv$iv, cancellable$iv3);
                                            if (updCellResult$iv$iv != BufferedChannelKt.SUSPEND) {
                                                if (updCellResult$iv$iv == BufferedChannelKt.FAILED) {
                                                    if (r$iv$iv < getSendersCounter$kotlinx_coroutines_core()) {
                                                        segment$iv$iv.cleanPrev();
                                                    }
                                                    $i$f$suspendCancellableCoroutineReusable = $i$f$suspendCancellableCoroutineReusable2;
                                                    continuation2 = continuation3;
                                                    i = i2;
                                                    cancellable$iv3 = cancellable$iv2;
                                                } else {
                                                    if (updCellResult$iv$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                                        throw new IllegalStateException("unexpected".toString());
                                                    }
                                                    segment$iv$iv.cleanPrev();
                                                    Function1<E, Unit> function12 = this.onUndeliveredElement;
                                                    cancellable$iv3.resume(updCellResult$iv$iv, (Function3<? super Throwable, ? super Object, ? super CoroutineContext, Unit>) (function12 != null ? bindCancellationFun(function12) : null));
                                                }
                                            } else {
                                                ?? r5 = cancellable$iv3 instanceof Waiter ? cancellable$iv3 : null;
                                                if (r5 != 0) {
                                                    prepareReceiverForSuspension(r5, segment$iv$iv, i$iv$iv);
                                                }
                                            }
                                        } catch (Throwable th) {
                                            e$iv = th;
                                            cancellable$iv = cancellable$iv3;
                                            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                                            throw e$iv;
                                        }
                                    }
                                    onClosedReceiveOnNoWaiterSuspend(cancellable$iv3);
                                    cancellable$iv2 = cancellable$iv3;
                                }
                            } catch (Throwable th2) {
                                e$iv = th2;
                            }
                        } catch (Throwable th3) {
                            e$iv = th3;
                            cancellable$iv = cancellable$iv3;
                            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                            throw e$iv;
                        }
                    } else {
                        try {
                            prepareReceiverForSuspension(cancellable$iv3, channelSegment, index);
                            cancellable$iv2 = cancellable$iv3;
                        } catch (Throwable th4) {
                            e$iv = th4;
                            cancellable$iv = cancellable$iv3;
                            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                            throw e$iv;
                        }
                    }
                    Object result = cancellable$iv2.getResult();
                    if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        DebugProbesKt.probeCoroutineSuspended(continuation);
                    }
                    return result;
                } catch (Throwable th5) {
                    e$iv = th5;
                }
            } catch (Throwable th6) {
                e$iv = th6;
                cancellable$iv = cancellable$iv3;
            }
        } catch (Throwable th7) {
            e$iv = th7;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareReceiverForSuspension(Waiter $this$prepareReceiverForSuspension, ChannelSegment<E> channelSegment, int index) {
        onReceiveEnqueued();
        $this$prepareReceiverForSuspension.invokeOnCancellation(channelSegment, index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveOnNoWaiterSuspend(CancellableContinuation<? super E> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(getReceiveException())));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU$suspendImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static /* synthetic */ <E> java.lang.Object m1776receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r14, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 226
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1776receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX INFO: renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m1777receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment<E> r26, int r27, long r28, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r30) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 440
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1777receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveCatchingOnNoWaiterSuspend(CancellableContinuation<? super ChannelResult<? extends E>> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m266constructorimpl(ChannelResult.m1786boximpl(ChannelResult.INSTANCE.m1799closedJP2dKIU(getCloseCause()))));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public Object mo1779tryReceivePtdJZtk() {
        long r = receivers$volatile$FU.get(this);
        long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.get(this);
        if (isClosedForReceive0(sendersAndCloseStatusCur)) {
            return ChannelResult.INSTANCE.m1799closedJP2dKIU(getCloseCause());
        }
        long $this$sendersCounter$iv = sendersAndCloseStatusCur & 1152921504606846975L;
        if (r >= $this$sendersCounter$iv) {
            return ChannelResult.INSTANCE.m1800failurePtdJZtk();
        }
        Object waiter$iv = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment segment$iv = (ChannelSegment) getReceiveSegment$volatile$FU().get(this);
        while (!isClosedForReceive()) {
            long r$iv = getReceivers$volatile$FU().getAndIncrement(this);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(id$iv, segment$iv);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    segment$iv = channelSegmentFindSegmentReceive;
                }
            }
            Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv, waiter$iv);
            if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                Waiter waiter = waiter$iv instanceof Waiter ? (Waiter) waiter$iv : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, segment$iv, i$iv);
                }
                ChannelSegment segm = segment$iv;
                waitExpandBufferCompletion$kotlinx_coroutines_core(r$iv);
                segm.onSlotCleaned();
                return ChannelResult.INSTANCE.m1800failurePtdJZtk();
            }
            ChannelSegment segment$iv2 = segment$iv;
            if (updCellResult$iv != BufferedChannelKt.FAILED) {
                if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected".toString());
                }
                segment$iv2.cleanPrev();
                return ChannelResult.INSTANCE.m1801successJP2dKIU(updCellResult$iv);
            }
            if (r$iv < getSendersCounter$kotlinx_coroutines_core()) {
                segment$iv2.cleanPrev();
            }
            segment$iv = segment$iv2;
        }
        return ChannelResult.INSTANCE.m1799closedJP2dKIU(getCloseCause());
    }

    protected final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long globalCellIndex) {
        ChannelSegment<E> channelSegmentFindSegmentReceive;
        UndeliveredElementException it;
        if (DebugKt.getASSERTIONS_ENABLED() && !isConflatedDropOldest()) {
            throw new AssertionError();
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        while (true) {
            long r = receivers$volatile$FU.get(this);
            if (globalCellIndex < Math.max(((long) this.capacity) + r, getBufferEndCounter())) {
                return;
            }
            if (receivers$volatile$FU.compareAndSet(this, r, 1 + r)) {
                long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment.id == id) {
                    channelSegmentFindSegmentReceive = channelSegment;
                } else {
                    channelSegmentFindSegmentReceive = findSegmentReceive(id, channelSegment);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    }
                }
                Object updCellResult = updateCellReceive(channelSegmentFindSegmentReceive, i, r, null);
                if (updCellResult != BufferedChannelKt.FAILED) {
                    channelSegmentFindSegmentReceive.cleanPrev();
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    if (function1 != null && (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, updCellResult, null, 2, null)) != null) {
                        throw it;
                    }
                } else if (r < getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegmentFindSegmentReceive.cleanPrev();
                }
                channelSegment = channelSegmentFindSegmentReceive;
            }
        }
    }

    static /* synthetic */ Object receiveImpl$default(BufferedChannel $this, Object waiter, Function1 onElementRetrieved, Function3 onSuspend, Function0 onClosed, Function3 onNoWaiterSuspend, int i, Object obj) {
        Function3 onNoWaiterSuspend2;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: receiveImpl");
        }
        if ((i & 16) == 0) {
            onNoWaiterSuspend2 = onNoWaiterSuspend;
        } else {
            onNoWaiterSuspend2 = new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel.receiveImpl.1
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2, Object p3) {
                    return invoke((ChannelSegment) p1, ((Number) p2).intValue(), ((Number) p3).longValue());
                }

                public final Void invoke(ChannelSegment<E> channelSegment, int i2, long j) {
                    throw new IllegalStateException("unexpected".toString());
                }
            };
        }
        ChannelSegment segment = (ChannelSegment) getReceiveSegment$volatile$FU().get($this);
        while (!$this.isClosedForReceive()) {
            long r = getReceivers$volatile$FU().getAndIncrement($this);
            long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i2 = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment channelSegmentFindSegmentReceive = $this.findSegmentReceive(id, segment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    segment = channelSegmentFindSegmentReceive;
                }
            }
            Object updCellResult = $this.updateCellReceive(segment, i2, r, waiter);
            if (updCellResult != BufferedChannelKt.SUSPEND) {
                if (updCellResult != BufferedChannelKt.FAILED) {
                    if (updCellResult == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return onNoWaiterSuspend2.invoke(segment, Integer.valueOf(i2), Long.valueOf(r));
                    }
                    segment.cleanPrev();
                    return onElementRetrieved.invoke(updCellResult);
                }
                if (r < $this.getSendersCounter$kotlinx_coroutines_core()) {
                    segment.cleanPrev();
                }
            } else {
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    $this.prepareReceiverForSuspension(waiter2, segment, i2);
                }
                return onSuspend.invoke(segment, Integer.valueOf(i2), Long.valueOf(r));
            }
        }
        return onClosed.invoke();
    }

    private final <R> R receiveImpl(Object waiter, Function1<? super E, ? extends R> onElementRetrieved, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onSuspend, Function0<? extends R> onClosed, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment segment = (ChannelSegment) getReceiveSegment$volatile$FU().get(this);
        while (!isClosedForReceive()) {
            long r = getReceivers$volatile$FU().getAndIncrement(this);
            long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(id, segment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    segment = channelSegmentFindSegmentReceive;
                }
            }
            Object updCellResult = updateCellReceive(segment, i, r, waiter);
            if (updCellResult != BufferedChannelKt.SUSPEND) {
                if (updCellResult != BufferedChannelKt.FAILED) {
                    if (updCellResult == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return onNoWaiterSuspend.invoke(segment, Integer.valueOf(i), Long.valueOf(r));
                    }
                    segment.cleanPrev();
                    return onElementRetrieved.invoke(updCellResult);
                }
                if (r < getSendersCounter$kotlinx_coroutines_core()) {
                    segment.cleanPrev();
                }
            } else {
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    prepareReceiverForSuspension(waiter2, segment, i);
                }
                return onSuspend.invoke(segment, Integer.valueOf(i), Long.valueOf(r));
            }
        }
        return onClosed.invoke();
    }

    private final void receiveImplOnNoWaiter(ChannelSegment<E> segment, int index, long r, Waiter waiter, Function1<? super E, Unit> onElementRetrieved, Function0<Unit> onClosed) {
        Object updCellResult = updateCellReceive(segment, index, r, waiter);
        if (updCellResult != BufferedChannelKt.SUSPEND) {
            if (updCellResult != BufferedChannelKt.FAILED) {
                segment.cleanPrev();
                onElementRetrieved.invoke(updCellResult);
                return;
            }
            if (r < getSendersCounter$kotlinx_coroutines_core()) {
                segment.cleanPrev();
            }
            ChannelSegment segment$iv = (ChannelSegment) getReceiveSegment$volatile$FU().get(this);
            while (!isClosedForReceive()) {
                long r$iv = getReceivers$volatile$FU().getAndIncrement(this);
                long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (segment$iv.id != id$iv) {
                    ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(id$iv, segment$iv);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        segment$iv = channelSegmentFindSegmentReceive;
                    }
                }
                Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv, waiter);
                if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                    if (updCellResult$iv != BufferedChannelKt.FAILED) {
                        if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                            throw new IllegalStateException("unexpected".toString());
                        }
                        segment$iv.cleanPrev();
                        onElementRetrieved.invoke(updCellResult$iv);
                        return;
                    }
                    if (r$iv < getSendersCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                } else {
                    Waiter waiter2 = waiter instanceof Waiter ? waiter : null;
                    if (waiter2 != null) {
                        prepareReceiverForSuspension(waiter2, segment$iv, i$iv);
                    }
                    Unit unit = Unit.INSTANCE;
                    return;
                }
            }
            onClosed.invoke();
            return;
        }
        prepareReceiverForSuspension(waiter, segment, index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCellReceive(ChannelSegment<E> segment, int index, long r, Object waiter) {
        Object state = segment.getState$kotlinx_coroutines_core(index);
        if (state == null) {
            long $this$sendersCounter$iv = sendersAndCloseStatus$volatile$FU.get(this);
            long senders = $this$sendersCounter$iv & 1152921504606846975L;
            if (r >= senders) {
                if (waiter == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state, waiter)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state == BufferedChannelKt.BUFFERED && segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            return segment.retrieveElement$kotlinx_coroutines_core(index);
        }
        return updateCellReceiveSlow(segment, index, r, waiter);
    }

    private final Object updateCellReceiveSlow(ChannelSegment<E> segment, int index, long r, Object waiter) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state == null || state == BufferedChannelKt.IN_BUFFER) {
                long $this$sendersCounter$iv = sendersAndCloseStatus$volatile$FU.get(this);
                long senders = $this$sendersCounter$iv & 1152921504606846975L;
                if (r < senders) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else {
                    if (waiter == null) {
                        return BufferedChannelKt.SUSPEND_NO_WAITER;
                    }
                    if (segment.casState$kotlinx_coroutines_core(index, state, waiter)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else if (state == BufferedChannelKt.BUFFERED) {
                if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.DONE_RCV)) {
                    expandBuffer();
                    return segment.retrieveElement$kotlinx_coroutines_core(index);
                }
            } else {
                if (state != BufferedChannelKt.INTERRUPTED_SEND && state != BufferedChannelKt.POISONED) {
                    if (state != BufferedChannelKt.getCHANNEL_CLOSED()) {
                        if (state != BufferedChannelKt.RESUMING_BY_EB && segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_RCV)) {
                            boolean helpExpandBuffer = state instanceof WaiterEB;
                            Object sender = state instanceof WaiterEB ? ((WaiterEB) state).waiter : state;
                            if (tryResumeSender(sender, segment, index)) {
                                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                                expandBuffer();
                                return segment.retrieveElement$kotlinx_coroutines_core(index);
                            }
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                            segment.onCancelledRequest(index, false);
                            if (helpExpandBuffer) {
                                expandBuffer();
                            }
                            return BufferedChannelKt.FAILED;
                        }
                    } else {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                }
                return BufferedChannelKt.FAILED;
            }
        }
    }

    private final boolean tryResumeSender(Object $this$tryResumeSender, ChannelSegment<E> channelSegment, int index) {
        if ($this$tryResumeSender instanceof CancellableContinuation) {
            Intrinsics.checkNotNull($this$tryResumeSender, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
            return BufferedChannelKt.tryResume0$default((CancellableContinuation) $this$tryResumeSender, Unit.INSTANCE, null, 2, null);
        }
        if ($this$tryResumeSender instanceof SelectInstance) {
            Intrinsics.checkNotNull($this$tryResumeSender, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectImplementation<*>");
            TrySelectDetailedResult trySelectResult = ((SelectImplementation) $this$tryResumeSender).trySelectDetailed(this, Unit.INSTANCE);
            if (trySelectResult == TrySelectDetailedResult.REREGISTER) {
                channelSegment.cleanElement$kotlinx_coroutines_core(index);
            }
            return trySelectResult == TrySelectDetailedResult.SUCCESSFUL;
        }
        if ($this$tryResumeSender instanceof SendBroadcast) {
            return BufferedChannelKt.tryResume0$default(((SendBroadcast) $this$tryResumeSender).getCont(), true, null, 2, null);
        }
        throw new IllegalStateException(("Unexpected waiter: " + $this$tryResumeSender).toString());
    }

    private final void expandBuffer() {
        if (isRendezvousOrUnlimited()) {
            return;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) bufferEndSegment$volatile$FU.get(this);
        while (true) {
            long b = bufferEnd$volatile$FU.getAndIncrement(this);
            long id = b / ((long) BufferedChannelKt.SEGMENT_SIZE);
            long s = getSendersCounter$kotlinx_coroutines_core();
            if (s <= b) {
                if (channelSegment.id < id && channelSegment.getNext() != 0) {
                    moveSegmentBufferEndToSpecifiedOrLast(id, channelSegment);
                }
                incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
                return;
            }
            if (channelSegment.id != id) {
                ChannelSegment<E> channelSegmentFindSegmentBufferEnd = findSegmentBufferEnd(id, channelSegment, b);
                if (channelSegmentFindSegmentBufferEnd == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentBufferEnd;
                }
            }
            int i = (int) (b % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (updateCellExpandBuffer(channelSegment, i, b)) {
                incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
                return;
            }
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
        }
    }

    private final boolean updateCellExpandBuffer(ChannelSegment<E> segment, int index, long b) {
        Object state = segment.getState$kotlinx_coroutines_core(index);
        if ((state instanceof Waiter) && b >= receivers$volatile$FU.get(this) && segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_EB)) {
            if (!tryResumeSender(state, segment, index)) {
                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                segment.onCancelledRequest(index, false);
                return false;
            }
            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
            return true;
        }
        return updateCellExpandBufferSlow(segment, index, b);
    }

    private final boolean updateCellExpandBufferSlow(ChannelSegment<E> segment, int index, long b) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state instanceof Waiter) {
                if (b >= receivers$volatile$FU.get(this)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_EB)) {
                        if (!tryResumeSender(state, segment, index)) {
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                            segment.onCancelledRequest(index, false);
                            return false;
                        }
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
                        return true;
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, state, new WaiterEB((Waiter) state))) {
                    return true;
                }
            } else {
                if (state == BufferedChannelKt.INTERRUPTED_SEND) {
                    return false;
                }
                if (state == null) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.IN_BUFFER)) {
                        return true;
                    }
                } else {
                    if (state == BufferedChannelKt.BUFFERED || state == BufferedChannelKt.POISONED || state == BufferedChannelKt.DONE_RCV || state == BufferedChannelKt.INTERRUPTED_RCV || state == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        return true;
                    }
                    if (state != BufferedChannelKt.RESUMING_BY_RCV) {
                        throw new IllegalStateException(("Unexpected cell state: " + state).toString());
                    }
                }
            }
        }
    }

    static /* synthetic */ void incCompletedExpandBufferAttempts$default(BufferedChannel bufferedChannel, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incCompletedExpandBufferAttempts");
        }
        if ((i & 1) != 0) {
            j = 1;
        }
        bufferedChannel.incCompletedExpandBufferAttempts(j);
    }

    private final void incCompletedExpandBufferAttempts(long nAttempts) {
        long $this$ebPauseExpandBuffers$iv;
        long it = completedExpandBuffersAndPauseFlag$volatile$FU.addAndGet(this, nAttempts);
        if ((it & 4611686018427387904L) != 0) {
            do {
                $this$ebPauseExpandBuffers$iv = completedExpandBuffersAndPauseFlag$volatile$FU.get(this);
            } while (($this$ebPauseExpandBuffers$iv & 4611686018427387904L) != 0);
        }
    }

    public final void waitExpandBufferCompletion$kotlinx_coroutines_core(long globalIndex) {
        BufferedChannel<E> bufferedChannel = this;
        if (bufferedChannel.isRendezvousOrUnlimited()) {
            return;
        }
        while (bufferedChannel.getBufferEndCounter() <= globalIndex) {
            bufferedChannel = this;
        }
        int i = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
        int i2 = 0;
        while (true) {
            long j = DurationKt.MAX_MILLIS;
            if (i2 >= i) {
                AtomicLongFieldUpdater handler$atomicfu$iv = completedExpandBuffersAndPauseFlag$volatile$FU;
                while (true) {
                    long it = handler$atomicfu$iv.get(bufferedChannel);
                    if (handler$atomicfu$iv.compareAndSet(bufferedChannel, it, BufferedChannelKt.constructEBCompletedAndPauseFlag(it & DurationKt.MAX_MILLIS, true))) {
                        break;
                    } else {
                        bufferedChannel = this;
                    }
                }
                while (true) {
                    long b = bufferedChannel.getBufferEndCounter();
                    long ebCompletedAndBit = completedExpandBuffersAndPauseFlag$volatile$FU.get(bufferedChannel);
                    long $this$ebCompletedCounter$iv = ebCompletedAndBit & j;
                    int $i$f$getEbPauseExpandBuffers = (4611686018427387904L & ebCompletedAndBit) != 0 ? 1 : 0;
                    int i3 = $i$f$getEbPauseExpandBuffers;
                    if (b == $this$ebCompletedCounter$iv && b == bufferedChannel.getBufferEndCounter()) {
                        break;
                    }
                    long j2 = j;
                    if (i3 == 0) {
                        bufferedChannel = this;
                        completedExpandBuffersAndPauseFlag$volatile$FU.compareAndSet(bufferedChannel, ebCompletedAndBit, BufferedChannelKt.constructEBCompletedAndPauseFlag($this$ebCompletedCounter$iv, true));
                        j = j2;
                    } else {
                        bufferedChannel = this;
                        j = j2;
                    }
                }
                AtomicLongFieldUpdater handler$atomicfu$iv2 = completedExpandBuffersAndPauseFlag$volatile$FU;
                while (true) {
                    long it2 = handler$atomicfu$iv2.get(bufferedChannel);
                    long j3 = j;
                    if (!handler$atomicfu$iv2.compareAndSet(bufferedChannel, it2, BufferedChannelKt.constructEBCompletedAndPauseFlag(it2 & j3, false))) {
                        bufferedChannel = this;
                        j = j3;
                    } else {
                        return;
                    }
                }
            } else {
                long b2 = bufferedChannel.getBufferEndCounter();
                long ebCompleted = DurationKt.MAX_MILLIS & completedExpandBuffersAndPauseFlag$volatile$FU.get(bufferedChannel);
                if (b2 == ebCompleted && b2 == bufferedChannel.getBufferEndCounter()) {
                    return;
                } else {
                    i2++;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public SelectClause2<E, BufferedChannel<E>> getOnSend() {
        BufferedChannel$onSend$1 bufferedChannel$onSend$1 = BufferedChannel$onSend$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$1, 3);
        BufferedChannel$onSend$2 bufferedChannel$onSend$2 = BufferedChannel$onSend$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause2Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$2, 3), null, 8, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void registerSelectForSend(SelectInstance<?> select, Object element) {
        Object obj = element;
        ChannelSegment segment$iv = (ChannelSegment) sendSegment$volatile$FU.get(this);
        while (true) {
            long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
            long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
            boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
            long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(id$iv, segment$iv);
                if (channelSegmentFindSegmentSend != null) {
                    segment$iv = channelSegmentFindSegmentSend;
                } else if (closed$iv) {
                    onClosedSelectOnSend(element, select);
                    return;
                }
            }
            switch (updateCellSend(segment$iv, i$iv, obj, s$iv, select, closed$iv)) {
                case 0:
                    segment$iv.cleanPrev();
                    select.selectInRegistrationPhase(Unit.INSTANCE);
                    return;
                case 1:
                    select.selectInRegistrationPhase(Unit.INSTANCE);
                    return;
                case 2:
                    if (closed$iv) {
                        segment$iv.onSlotCleaned();
                        onClosedSelectOnSend(element, select);
                        return;
                    } else {
                        Waiter waiter = select instanceof Waiter ? (Waiter) select : null;
                        if (waiter != null) {
                            prepareSenderForSuspension(waiter, segment$iv, i$iv);
                        }
                        return;
                    }
                case 3:
                    throw new IllegalStateException("unexpected".toString());
                case 4:
                    if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                    onClosedSelectOnSend(element, select);
                    return;
                case 5:
                    segment$iv.cleanPrev();
                default:
                    obj = obj;
                    break;
            }
        }
    }

    private final void onClosedSelectOnSend(E element, SelectInstance<?> select) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, select.getContext());
        }
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectSend(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
            throw getSendException();
        }
        return this;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<E> getOnReceive() {
        BufferedChannel$onReceive$1 bufferedChannel$onReceive$1 = BufferedChannel$onReceive$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$1, 3);
        BufferedChannel$onReceive$2 bufferedChannel$onReceive$2 = BufferedChannel$onReceive$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        BufferedChannel$onReceiveCatching$1 bufferedChannel$onReceiveCatching$1 = BufferedChannel$onReceiveCatching$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$1, 3);
        BufferedChannel$onReceiveCatching$2 bufferedChannel$onReceiveCatching$2 = BufferedChannel$onReceiveCatching$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<E> getOnReceiveOrNull() {
        BufferedChannel$onReceiveOrNull$1 bufferedChannel$onReceiveOrNull$1 = BufferedChannel$onReceiveOrNull$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$1, 3);
        BufferedChannel$onReceiveOrNull$2 bufferedChannel$onReceiveOrNull$2 = BufferedChannel$onReceiveOrNull$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerSelectForReceive(SelectInstance<?> select, Object ignoredParam) {
        ChannelSegment segment$iv;
        ChannelSegment segment$iv2 = (ChannelSegment) getReceiveSegment$volatile$FU().get(this);
        while (!isClosedForReceive()) {
            long r$iv = getReceivers$volatile$FU().getAndIncrement(this);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv2.id != id$iv) {
                ChannelSegment segment$iv3 = findSegmentReceive(id$iv, segment$iv2);
                if (segment$iv3 == null) {
                    continue;
                } else {
                    segment$iv = segment$iv3;
                }
            } else {
                segment$iv = segment$iv2;
            }
            SelectInstance<?> selectInstance = select;
            Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv, selectInstance);
            if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                if (updCellResult$iv != BufferedChannelKt.FAILED) {
                    if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        throw new IllegalStateException("unexpected".toString());
                    }
                    segment$iv.cleanPrev();
                    selectInstance.selectInRegistrationPhase(updCellResult$iv);
                    return;
                }
                if (r$iv < getSendersCounter$kotlinx_coroutines_core()) {
                    segment$iv.cleanPrev();
                }
                segment$iv2 = segment$iv;
                select = selectInstance;
            } else {
                Waiter waiter = selectInstance instanceof Waiter ? (Waiter) selectInstance : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, segment$iv, i$iv);
                }
                return;
            }
        }
        onClosedSelectOnReceive(select);
    }

    private final void onClosedSelectOnReceive(SelectInstance<?> select) {
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceive(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
            throw getReceiveException();
        }
        return selectResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveOrNull(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
            if (getCloseCause() == null) {
                return null;
            }
            throw getReceiveException();
        }
        return selectResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveCatching(Object ignoredParam, Object selectResult) {
        return ChannelResult.m1786boximpl(selectResult == BufferedChannelKt.getCHANNEL_CLOSED() ? ChannelResult.INSTANCE.m1799closedJP2dKIU(getCloseCause()) : ChannelResult.INSTANCE.m1801successJP2dKIU(selectResult));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Function3 onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(final BufferedChannel this$0, final SelectInstance select, Object obj, final Object element) {
        return new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(element, this$0, select, (Throwable) obj2, obj3, (CoroutineContext) obj4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(Object $element, BufferedChannel this$0, SelectInstance $select, Throwable th, Object obj, CoroutineContext coroutineContext) {
        if ($element != BufferedChannelKt.getCHANNEL_CLOSED()) {
            OnUndeliveredElementKt.callUndeliveredElement(this$0.onUndeliveredElement, $element, $select.getContext());
        }
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public ChannelIterator<E> iterator() {
        return new BufferedChannelIterator();
    }

    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000e\u0010\n\u001a\u00020\tH\u0096B¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\tH\u0002J,\u0010\r\u001a\u00020\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0082@¢\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u00162\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u000e\u0010\u0019\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00028\u0000¢\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;", "Lkotlinx/coroutines/channels/ChannelIterator;", "Lkotlinx/coroutines/Waiter;", "<init>", "(Lkotlinx/coroutines/channels/BufferedChannel;)V", "receiveResult", "", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "", "hasNext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onClosedHasNext", "hasNextOnNoWaiterSuspend", "segment", "Lkotlinx/coroutines/channels/ChannelSegment;", "index", "", "r", "", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnCancellation", "", "Lkotlinx/coroutines/internal/Segment;", "onClosedHasNextNoWaiterSuspend", "next", "()Ljava/lang/Object;", "tryResumeHasNext", "element", "(Ljava/lang/Object;)Z", "tryResumeHasNextOnClosedChannel", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private final class BufferedChannelIterator implements ChannelIterator<E>, Waiter {
        private CancellableContinuationImpl<? super Boolean> continuation;
        private Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        public /* synthetic */ Object next(Continuation $completion) {
            return ChannelIterator.DefaultImpls.next(this, $completion);
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public Object hasNext(Continuation<? super Boolean> continuation) throws Throwable {
            ChannelSegment<E> channelSegmentFindSegmentReceive;
            boolean zOnClosedHasNext = true;
            if (this.receiveResult == BufferedChannelKt.NO_RECEIVE_RESULT || this.receiveResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
                BufferedChannel<E> bufferedChannel = BufferedChannel.this;
                ChannelSegment<E> channelSegment = (ChannelSegment) BufferedChannel.getReceiveSegment$volatile$FU().get(bufferedChannel);
                while (true) {
                    if (bufferedChannel.isClosedForReceive()) {
                        zOnClosedHasNext = onClosedHasNext();
                        break;
                    }
                    long r$iv = BufferedChannel.getReceivers$volatile$FU().getAndIncrement(bufferedChannel);
                    long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                    int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                    if (channelSegment.id != id$iv) {
                        channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(id$iv, channelSegment);
                        if (channelSegmentFindSegmentReceive == null) {
                            continue;
                        }
                    } else {
                        channelSegmentFindSegmentReceive = channelSegment;
                    }
                    Object updCellResult$iv = bufferedChannel.updateCellReceive(channelSegmentFindSegmentReceive, i$iv, r$iv, null);
                    BufferedChannel<E> bufferedChannel2 = bufferedChannel;
                    ChannelSegment<E> channelSegment2 = channelSegmentFindSegmentReceive;
                    if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                        if (updCellResult$iv != BufferedChannelKt.FAILED) {
                            if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                return hasNextOnNoWaiterSuspend(channelSegment2, i$iv, r$iv, continuation);
                            }
                            channelSegment2.cleanPrev();
                            this.receiveResult = updCellResult$iv;
                        } else {
                            if (r$iv < bufferedChannel2.getSendersCounter$kotlinx_coroutines_core()) {
                                channelSegment2.cleanPrev();
                            }
                            channelSegment = channelSegment2;
                            bufferedChannel = bufferedChannel2;
                        }
                    } else {
                        throw new IllegalStateException("unreachable".toString());
                    }
                }
            }
            return Boxing.boxBoolean(zOnClosedHasNext);
        }

        private final boolean onClosedHasNext() throws Throwable {
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(cause);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object hasNextOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int index, long r, Continuation<? super Boolean> continuation) throws Throwable {
            Function3 function3BindCancellationFun;
            BufferedChannel<E> bufferedChannel = BufferedChannel.this;
            int $i$f$suspendCancellableCoroutineReusable = 0;
            Continuation<? super Boolean> continuation2 = continuation;
            CancellableContinuationImpl cancellable$iv = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation2));
            try {
                this.continuation = cancellable$iv;
                Object updCellResult$iv = bufferedChannel.updateCellReceive(channelSegment, index, r, this);
                try {
                    if (updCellResult$iv != BufferedChannelKt.SUSPEND) {
                        try {
                            if (updCellResult$iv != BufferedChannelKt.FAILED) {
                                channelSegment.cleanPrev();
                                this.receiveResult = updCellResult$iv;
                                this.continuation = null;
                                Boolean boolBoxBoolean = Boxing.boxBoolean(true);
                                Function1<E, Unit> function1 = bufferedChannel.onUndeliveredElement;
                                if (function1 != null) {
                                    function3BindCancellationFun = bufferedChannel.bindCancellationFun(function1, updCellResult$iv);
                                } else {
                                    function3BindCancellationFun = null;
                                }
                                cancellable$iv.resume(boolBoxBoolean, (Function3<? super Throwable, ? super Boolean, ? super CoroutineContext, Unit>) function3BindCancellationFun);
                            } else {
                                if (r < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                                    channelSegment.cleanPrev();
                                }
                                ChannelSegment segment$iv$iv = (ChannelSegment) BufferedChannel.getReceiveSegment$volatile$FU().get(bufferedChannel);
                                while (!bufferedChannel.isClosedForReceive()) {
                                    long r$iv$iv = BufferedChannel.getReceivers$volatile$FU().getAndIncrement(bufferedChannel);
                                    long id$iv$iv = r$iv$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                                    int $i$f$suspendCancellableCoroutineReusable2 = $i$f$suspendCancellableCoroutineReusable;
                                    try {
                                        Continuation<? super Boolean> continuation3 = continuation2;
                                        int i$iv$iv = (int) (r$iv$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                                        if (segment$iv$iv.id != id$iv$iv) {
                                            ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(id$iv$iv, segment$iv$iv);
                                            if (channelSegmentFindSegmentReceive != null) {
                                                segment$iv$iv = channelSegmentFindSegmentReceive;
                                            } else {
                                                $i$f$suspendCancellableCoroutineReusable = $i$f$suspendCancellableCoroutineReusable2;
                                                continuation2 = continuation3;
                                            }
                                        }
                                        ChannelSegment segment$iv$iv2 = segment$iv$iv;
                                        Object updCellResult$iv$iv = bufferedChannel.updateCellReceive(segment$iv$iv2, i$iv$iv, r$iv$iv, this);
                                        if (updCellResult$iv$iv != BufferedChannelKt.SUSPEND) {
                                            if (updCellResult$iv$iv == BufferedChannelKt.FAILED) {
                                                if (r$iv$iv < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                                                    segment$iv$iv2.cleanPrev();
                                                }
                                                segment$iv$iv = segment$iv$iv2;
                                                $i$f$suspendCancellableCoroutineReusable = $i$f$suspendCancellableCoroutineReusable2;
                                                continuation2 = continuation3;
                                            } else {
                                                if (updCellResult$iv$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                                    throw new IllegalStateException("unexpected".toString());
                                                }
                                                segment$iv$iv2.cleanPrev();
                                                this.receiveResult = updCellResult$iv$iv;
                                                this.continuation = null;
                                                Boolean boolBoxBoolean2 = Boxing.boxBoolean(true);
                                                Function1<E, Unit> function12 = bufferedChannel.onUndeliveredElement;
                                                cancellable$iv.resume(boolBoxBoolean2, (Function3<? super Throwable, ? super Boolean, ? super CoroutineContext, Unit>) (function12 != null ? bufferedChannel.bindCancellationFun(function12, updCellResult$iv$iv) : null));
                                            }
                                        } else {
                                            BufferedChannelIterator bufferedChannelIterator = this instanceof Waiter ? this : null;
                                            if (bufferedChannelIterator != null) {
                                                bufferedChannel.prepareReceiverForSuspension(bufferedChannelIterator, segment$iv$iv2, i$iv$iv);
                                            }
                                        }
                                    } catch (Throwable th) {
                                        e$iv = th;
                                        cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                                        throw e$iv;
                                    }
                                }
                                onClosedHasNextNoWaiterSuspend();
                            }
                        } catch (Throwable th2) {
                            e$iv = th2;
                        }
                    } else {
                        try {
                            bufferedChannel.prepareReceiverForSuspension(this, channelSegment, index);
                        } catch (Throwable th3) {
                            e$iv = th3;
                            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                            throw e$iv;
                        }
                    }
                    Object result = cancellable$iv.getResult();
                    if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        DebugProbesKt.probeCoroutineSuspended(continuation);
                    }
                    return result;
                } catch (Throwable th4) {
                    e$iv = th4;
                }
            } catch (Throwable th5) {
                e$iv = th5;
            }
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment<?> segment, int index) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, index);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void onClosedHasNextNoWaiterSuspend() {
            Throwable thRecoverFromStackFrame;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m266constructorimpl(false));
                return;
            }
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl instanceof CoroutineStackFrame)) {
                thRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(cause, cancellableContinuationImpl);
            } else {
                thRecoverFromStackFrame = cause;
            }
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuationImpl2.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(thRecoverFromStackFrame)));
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public E next() throws Throwable {
            E e = (E) this.receiveResult;
            if (!(e != BufferedChannelKt.NO_RECEIVE_RESULT)) {
                throw new IllegalStateException("`hasNext()` has not been invoked".toString());
            }
            this.receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;
            if (e == BufferedChannelKt.getCHANNEL_CLOSED()) {
                throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.this.getReceiveException());
            }
            return e;
        }

        public final boolean tryResumeHasNext(E element) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            Function3 function3BindCancellationFun = null;
            this.continuation = null;
            this.receiveResult = element;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            Function1<E, Unit> function1 = BufferedChannel.this.onUndeliveredElement;
            if (function1 != null) {
                function3BindCancellationFun = BufferedChannel.this.bindCancellationFun(function1, element);
            }
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl2, true, function3BindCancellationFun);
        }

        public final void tryResumeHasNextOnClosedChannel() {
            Throwable thRecoverFromStackFrame;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m266constructorimpl(false));
                return;
            }
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl instanceof CoroutineStackFrame)) {
                thRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(cause, cancellableContinuationImpl);
            } else {
                thRecoverFromStackFrame = cause;
            }
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuationImpl2.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(thRecoverFromStackFrame)));
        }
    }

    protected final Throwable getCloseCause() {
        return (Throwable) _closeCause$volatile$FU.get(this);
    }

    protected final Throwable getSendException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Throwable getReceiveException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedReceiveChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    protected void onClosedIdempotent() {
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(Throwable cause) {
        return closeOrCancelImpl(cause, false);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final boolean cancel(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel() {
        cancelImpl$kotlinx_coroutines_core(null);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(CancellationException cause) {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
        return closeOrCancelImpl(cause == null ? new CancellationException("Channel was cancelled") : cause, true);
    }

    protected boolean closeOrCancelImpl(Throwable cause, boolean cancel) {
        if (cancel) {
            markCancellationStarted();
        }
        boolean closedByThisOperation = AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_closeCause$volatile$FU, this, BufferedChannelKt.NO_CLOSE_CAUSE, cause);
        if (cancel) {
            markCancelled();
        } else {
            markClosed();
        }
        completeCloseOrCancel();
        onClosedIdempotent();
        if (closedByThisOperation) {
            invokeCloseHandler();
        }
        return closedByThisOperation;
    }

    private final void invokeCloseHandler() {
        Object closeHandler;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = closeHandler$volatile$FU;
        do {
            closeHandler = handler$atomicfu$iv.get(this);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, closeHandler, closeHandler == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        if (closeHandler == null) {
            return;
        }
        ((Function1) closeHandler).invoke(getCloseCause());
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(closeHandler$volatile$FU, this, null, handler)) {
            AtomicReferenceFieldUpdater handler$atomicfu$iv = closeHandler$volatile$FU;
            do {
                Object cur = handler$atomicfu$iv.get(this);
                if (cur != BufferedChannelKt.CLOSE_HANDLER_CLOSED) {
                    if (cur != BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                        throw new IllegalStateException(("Another handler is already registered: " + cur).toString());
                    }
                    throw new IllegalStateException("Another handler was already registered and successfully invoked".toString());
                }
            } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(closeHandler$volatile$FU, this, BufferedChannelKt.CLOSE_HANDLER_CLOSED, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
            handler.invoke(getCloseCause());
        }
    }

    private final void markClosed() {
        long cur;
        long cur2;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            switch ((int) (cur >> 60)) {
                case 0:
                    cur2 = BufferedChannelKt.constructSendersAndCloseStatus(cur & 1152921504606846975L, 2);
                    break;
                case 1:
                    cur2 = BufferedChannelKt.constructSendersAndCloseStatus(cur & 1152921504606846975L, 3);
                    break;
                default:
                    return;
            }
        } while (!handler$atomicfu$iv.compareAndSet(this, cur, cur2));
    }

    private final void markCancelled() {
        long cur;
        long $this$sendersCounter$iv;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            $this$sendersCounter$iv = cur & 1152921504606846975L;
        } while (!handler$atomicfu$iv.compareAndSet(this, cur, BufferedChannelKt.constructSendersAndCloseStatus($this$sendersCounter$iv, 3)));
    }

    private final void markCancellationStarted() {
        long cur;
        long $this$sendersCounter$iv;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            if (((int) (cur >> 60)) == 0) {
                $this$sendersCounter$iv = cur & 1152921504606846975L;
            } else {
                return;
            }
        } while (!handler$atomicfu$iv.compareAndSet(this, cur, BufferedChannelKt.constructSendersAndCloseStatus($this$sendersCounter$iv, 1)));
    }

    private final void completeCloseOrCancel() {
        isClosedForSend();
    }

    protected boolean isConflatedDropOldest() {
        return false;
    }

    private final ChannelSegment<E> completeClose(long sendersCur) {
        ChannelSegment<E> channelSegmentCloseLinkedList = closeLinkedList();
        if (isConflatedDropOldest()) {
            long lastBufferedCellGlobalIndex = markAllEmptyCellsAsClosed(channelSegmentCloseLinkedList);
            if (lastBufferedCellGlobalIndex != -1) {
                dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(lastBufferedCellGlobalIndex);
            }
        }
        cancelSuspendedReceiveRequests(channelSegmentCloseLinkedList, sendersCur);
        return channelSegmentCloseLinkedList;
    }

    private final void completeCancel(long sendersCur) {
        removeUnprocessedElements(completeClose(sendersCur));
    }

    private final ChannelSegment<E> closeLinkedList() {
        Object lastSegment = bufferEndSegment$volatile$FU.get(this);
        ChannelSegment it = (ChannelSegment) sendSegment$volatile$FU.get(this);
        if (it.id > ((ChannelSegment) lastSegment).id) {
            lastSegment = it;
        }
        ChannelSegment it2 = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        if (it2.id > ((ChannelSegment) lastSegment).id) {
            lastSegment = it2;
        }
        return (ChannelSegment) ConcurrentLinkedListKt.close((ConcurrentLinkedListNode) lastSegment);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
    
        r1 = (kotlinx.coroutines.channels.ChannelSegment) r0.getPrev();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0048, code lost:
    
        if (r1 != null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004a, code lost:
    
        return -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment<E> r9) {
        /*
            r8 = this;
            r0 = r9
        L1:
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r1 = r1 + (-1)
        L6:
            r2 = -1
            r4 = -1
            if (r4 >= r1) goto L42
            long r4 = r0.id
            int r6 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r6 = (long) r6
            long r4 = r4 * r6
            long r6 = (long) r1
            long r4 = r4 + r6
            long r6 = r8.getReceiversCounter$kotlinx_coroutines_core()
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L1c
            return r2
        L1c:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core(r1)
            if (r2 == 0) goto L31
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.access$getIN_BUFFER$p()
            if (r2 != r3) goto L2b
            goto L31
        L2b:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r2 != r3) goto L30
            return r4
        L30:
            goto L3f
        L31:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r3 = r0.casState$kotlinx_coroutines_core(r1, r2, r3)
            if (r3 == 0) goto L1c
            r0.onSlotCleaned()
        L3f:
            int r1 = r1 + (-1)
            goto L6
        L42:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r1 = r0.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r1 = (kotlinx.coroutines.channels.ChannelSegment) r1
            if (r1 != 0) goto L4b
            return r2
        L4b:
            r0 = r1
            goto L1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment):long");
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ba, code lost:
    
        r5 = (kotlinx.coroutines.channels.ChannelSegment) r4.getPrev();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c0, code lost:
    
        if (r5 != null) goto L65;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment<E> r13) {
        /*
            Method dump skipped, instruction units count: 249
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void cancelSuspendedReceiveRequests(ChannelSegment<E> lastSegment, long sendersCounter) {
        Object suspendedReceivers = InlineList.m1828constructorimpl$default(null, 1, null);
        loop0: for (ChannelSegment<E> channelSegment = lastSegment; channelSegment != null; channelSegment = (ChannelSegment) channelSegment.getPrev()) {
            for (int index = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < index; index--) {
                if ((channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) index) < sendersCounter) {
                    break loop0;
                }
                while (true) {
                    Object state = channelSegment.getState$kotlinx_coroutines_core(index);
                    if (state == null || state == BufferedChannelKt.IN_BUFFER) {
                        if (channelSegment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            channelSegment.onSlotCleaned();
                            break;
                        }
                    } else if (state instanceof WaiterEB) {
                        if (channelSegment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            suspendedReceivers = InlineList.m1833plusFjFbRPM(suspendedReceivers, ((WaiterEB) state).waiter);
                            channelSegment.onCancelledRequest(index, true);
                            break;
                        }
                    } else {
                        if (!(state instanceof Waiter)) {
                            break;
                        }
                        if (channelSegment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            suspendedReceivers = InlineList.m1833plusFjFbRPM(suspendedReceivers, state);
                            channelSegment.onCancelledRequest(index, true);
                            break;
                        }
                    }
                }
            }
        }
        if (suspendedReceivers == null) {
            return;
        }
        if (!(suspendedReceivers instanceof ArrayList)) {
            Waiter it = (Waiter) suspendedReceivers;
            resumeReceiverOnClosedChannel(it);
            return;
        }
        Intrinsics.checkNotNull(suspendedReceivers, "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>");
        ArrayList list$iv = (ArrayList) suspendedReceivers;
        for (int i$iv = list$iv.size() - 1; -1 < i$iv; i$iv--) {
            Waiter it2 = (Waiter) list$iv.get(i$iv);
            resumeReceiverOnClosedChannel(it2);
        }
    }

    private final void resumeReceiverOnClosedChannel(Waiter $this$resumeReceiverOnClosedChannel) {
        resumeWaiterOnClosedChannel($this$resumeReceiverOnClosedChannel, true);
    }

    private final void resumeSenderOnCancelledChannel(Waiter $this$resumeSenderOnCancelledChannel) {
        resumeWaiterOnClosedChannel($this$resumeSenderOnCancelledChannel, false);
    }

    private final void resumeWaiterOnClosedChannel(Waiter $this$resumeWaiterOnClosedChannel, boolean receiver) {
        if (!($this$resumeWaiterOnClosedChannel instanceof SendBroadcast)) {
            if (!($this$resumeWaiterOnClosedChannel instanceof CancellableContinuation)) {
                if (!($this$resumeWaiterOnClosedChannel instanceof ReceiveCatching)) {
                    if (!($this$resumeWaiterOnClosedChannel instanceof BufferedChannelIterator)) {
                        if (!($this$resumeWaiterOnClosedChannel instanceof SelectInstance)) {
                            throw new IllegalStateException(("Unexpected waiter: " + $this$resumeWaiterOnClosedChannel).toString());
                        }
                        ((SelectInstance) $this$resumeWaiterOnClosedChannel).trySelect(this, BufferedChannelKt.getCHANNEL_CLOSED());
                        return;
                    }
                    ((BufferedChannelIterator) $this$resumeWaiterOnClosedChannel).tryResumeHasNextOnClosedChannel();
                    return;
                }
                CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = ((ReceiveCatching) $this$resumeWaiterOnClosedChannel).cont;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m266constructorimpl(ChannelResult.m1786boximpl(ChannelResult.INSTANCE.m1799closedJP2dKIU(getCloseCause()))));
                return;
            }
            Continuation continuation = (Continuation) $this$resumeWaiterOnClosedChannel;
            Result.Companion companion2 = Result.INSTANCE;
            continuation.resumeWith(Result.m266constructorimpl(ResultKt.createFailure(receiver ? getReceiveException() : getSendException())));
            return;
        }
        CancellableContinuation<Boolean> cont = ((SendBroadcast) $this$resumeWaiterOnClosedChannel).getCont();
        Result.Companion companion3 = Result.INSTANCE;
        cont.resumeWith(Result.m266constructorimpl(false));
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return isClosedForSend0(sendersAndCloseStatus$volatile$FU.get(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClosedForSend0(long $this$isClosedForSend0) {
        return isClosed($this$isClosedForSend0, false);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        return isClosedForReceive0(sendersAndCloseStatus$volatile$FU.get(this));
    }

    private final boolean isClosedForReceive0(long $this$isClosedForReceive0) {
        return isClosed($this$isClosedForReceive0, true);
    }

    private final boolean isClosed(long sendersAndCloseStatusCur, boolean isClosedForReceive) {
        switch ((int) (sendersAndCloseStatusCur >> 60)) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                completeClose(sendersAndCloseStatusCur & 1152921504606846975L);
                return (isClosedForReceive && hasElements$kotlinx_coroutines_core()) ? false : true;
            case 3:
                completeCancel(sendersAndCloseStatusCur & 1152921504606846975L);
                return true;
            default:
                throw new IllegalStateException(("unexpected close status: " + ((int) (sendersAndCloseStatusCur >> 60))).toString());
        }
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        if (isClosedForReceive() || hasElements$kotlinx_coroutines_core()) {
            return false;
        }
        return !isClosedForReceive();
    }

    public final boolean hasElements$kotlinx_coroutines_core() {
        while (true) {
            ChannelSegment<E> channelSegment = (ChannelSegment) receiveSegment$volatile$FU.get(this);
            long r = getReceiversCounter$kotlinx_coroutines_core();
            long s = getSendersCounter$kotlinx_coroutines_core();
            if (s <= r) {
                return false;
            }
            long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
            if (channelSegment.id != id) {
                ChannelSegment<E> channelSegmentFindSegmentReceive = findSegmentReceive(id, channelSegment);
                if (channelSegmentFindSegmentReceive != null) {
                    channelSegment = channelSegmentFindSegmentReceive;
                } else if (((ChannelSegment) receiveSegment$volatile$FU.get(this)).id < id) {
                    return false;
                }
            }
            channelSegment.cleanPrev();
            int i = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (isCellNonEmpty(channelSegment, i, r)) {
                return true;
            }
            receivers$volatile$FU.compareAndSet(this, r, 1 + r);
        }
    }

    private final boolean isCellNonEmpty(ChannelSegment<E> segment, int index, long globalIndex) {
        Object state;
        do {
            state = segment.getState$kotlinx_coroutines_core(index);
            if (state != null && state != BufferedChannelKt.IN_BUFFER) {
                if (state == BufferedChannelKt.BUFFERED) {
                    return true;
                }
                if (state != BufferedChannelKt.INTERRUPTED_SEND && state != BufferedChannelKt.getCHANNEL_CLOSED() && state != BufferedChannelKt.DONE_RCV && state != BufferedChannelKt.POISONED) {
                    if (state == BufferedChannelKt.RESUMING_BY_EB) {
                        return true;
                    }
                    return state != BufferedChannelKt.RESUMING_BY_RCV && globalIndex == getReceiversCounter$kotlinx_coroutines_core();
                }
                return false;
            }
        } while (!segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.POISONED));
        expandBuffer();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentSend(long id, ChannelSegment<E> startFrom) {
        Object s$iv;
        boolean z;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = sendSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, createNewSegment$iv);
            if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv = SegmentOrClosed.m1843getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv.id) {
                    z = true;
                    break;
                }
                if (!to$iv$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    z = true;
                } else if (to$iv$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv.remove();
                }
            }
        } while (!z);
        if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
            completeCloseOrCancel();
            if (startFrom.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m1843getSegmentimpl(s$iv);
        if (channelSegment.id > id) {
            updateSendersCounterIfLower(channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            channelSegment.cleanPrev();
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentReceive(long id, ChannelSegment<E> startFrom) {
        Object s$iv;
        boolean z;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = receiveSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, createNewSegment$iv);
            if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv = SegmentOrClosed.m1843getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv.id) {
                    z = true;
                    break;
                }
                if (!to$iv$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    z = true;
                } else if (to$iv$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv.remove();
                }
            }
        } while (!z);
        if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
            completeCloseOrCancel();
            if (startFrom.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getSendersCounter$kotlinx_coroutines_core()) {
                startFrom.cleanPrev();
            }
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m1843getSegmentimpl(s$iv);
        if (!isRendezvousOrUnlimited() && id <= getBufferEndCounter() / ((long) BufferedChannelKt.SEGMENT_SIZE)) {
            AtomicReferenceFieldUpdater handler$atomicfu$iv2 = bufferEndSegment$volatile$FU;
            while (true) {
                Segment cur$iv = (Segment) handler$atomicfu$iv2.get(this);
                if (cur$iv.id >= channelSegment.id || !channelSegment.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv2, this, cur$iv, channelSegment)) {
                    if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv.remove();
                    }
                } else if (channelSegment.decPointers$kotlinx_coroutines_core()) {
                    channelSegment.remove();
                }
            }
        }
        if (channelSegment.id > id) {
            updateReceiversCounterIfLower(channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    private final ChannelSegment<E> findSegmentBufferEnd(long id, ChannelSegment<E> startFrom, long currentBufferEndCounter) {
        Object s$iv;
        boolean z;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = bufferEndSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, createNewSegment$iv);
            if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv = SegmentOrClosed.m1843getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv.id) {
                    z = true;
                    break;
                }
                if (!to$iv$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    z = true;
                } else if (to$iv$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv.remove();
                }
            }
        } while (!z);
        if (SegmentOrClosed.m1845isClosedimpl(s$iv)) {
            completeCloseOrCancel();
            moveSegmentBufferEndToSpecifiedOrLast(id, startFrom);
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m1843getSegmentimpl(s$iv);
        if (channelSegment.id > id) {
            if (bufferEnd$volatile$FU.compareAndSet(this, currentBufferEndCounter + 1, channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE))) {
                incCompletedExpandBufferAttempts((channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) - currentBufferEndCounter);
                return null;
            }
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void moveSegmentBufferEndToSpecifiedOrLast(long id, ChannelSegment<E> startFrom) {
        boolean z;
        ChannelSegment<E> channelSegment;
        ChannelSegment<E> channelSegment2;
        ChannelSegment<E> channelSegment3 = startFrom;
        while (channelSegment3.id < id && (channelSegment2 = (ChannelSegment) channelSegment3.getNext()) != null) {
            channelSegment3 = channelSegment2;
        }
        while (true) {
            if (!channelSegment3.isRemoved() || (channelSegment = (ChannelSegment) channelSegment3.getNext()) == null) {
                AtomicReferenceFieldUpdater handler$atomicfu$iv = bufferEndSegment$volatile$FU;
                while (true) {
                    Segment cur$iv = (Segment) handler$atomicfu$iv.get(this);
                    z = true;
                    if (cur$iv.id >= channelSegment3.id) {
                        break;
                    }
                    if (!channelSegment3.tryIncPointers$kotlinx_coroutines_core()) {
                        z = false;
                        break;
                    } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv, channelSegment3)) {
                        if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                            cur$iv.remove();
                        }
                    } else if (channelSegment3.decPointers$kotlinx_coroutines_core()) {
                        channelSegment3.remove();
                    }
                }
                if (z) {
                    return;
                }
            } else {
                channelSegment3 = channelSegment;
            }
        }
    }

    private final void updateSendersCounterIfLower(long value) {
        long cur;
        long update;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            long $this$sendersCounter$iv = cur & 1152921504606846975L;
            if ($this$sendersCounter$iv >= value) {
                return;
            } else {
                update = BufferedChannelKt.constructSendersAndCloseStatus($this$sendersCounter$iv, (int) (cur >> 60));
            }
        } while (!sendersAndCloseStatus$volatile$FU.compareAndSet(this, cur, update));
    }

    private final void updateReceiversCounterIfLower(long value) {
        AtomicLongFieldUpdater handler$atomicfu$iv = receivers$volatile$FU;
        while (true) {
            long cur = handler$atomicfu$iv.get(this);
            if (cur >= value) {
                return;
            }
            long value2 = value;
            if (receivers$volatile$FU.compareAndSet(this, cur, value2)) {
                return;
            } else {
                value = value2;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        String cellStateString;
        BufferedChannel<E> bufferedChannel = this;
        StringBuilder sb = new StringBuilder();
        long $this$sendersCloseStatus$iv = sendersAndCloseStatus$volatile$FU.get(bufferedChannel);
        switch ((int) ($this$sendersCloseStatus$iv >> 60)) {
            case 2:
                sb.append("closed,");
                break;
            case 3:
                sb.append("cancelled,");
                break;
        }
        sb.append("capacity=" + bufferedChannel.capacity + ',');
        sb.append("data=[");
        boolean z = true;
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new ChannelSegment[]{receiveSegment$volatile$FU.get(bufferedChannel), sendSegment$volatile$FU.get(bufferedChannel), bufferEndSegment$volatile$FU.get(bufferedChannel)});
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            ChannelSegment it = (ChannelSegment) element$iv$iv;
            if (it != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(element$iv$iv);
            }
        }
        ArrayList $this$minBy$iv = arrayList;
        Iterator iterator$iv = $this$minBy$iv.iterator();
        if (!iterator$iv.hasNext()) {
            throw new NoSuchElementException();
        }
        Object minElem$iv = iterator$iv.next();
        if (iterator$iv.hasNext()) {
            ChannelSegment it2 = (ChannelSegment) minElem$iv;
            long minValue$iv = it2.id;
            while (true) {
                Object e$iv = iterator$iv.next();
                ChannelSegment it3 = (ChannelSegment) e$iv;
                long v$iv = it3.id;
                if (minValue$iv > v$iv) {
                    minElem$iv = e$iv;
                    minValue$iv = v$iv;
                }
                if (iterator$iv.hasNext()) {
                    bufferedChannel = this;
                }
            }
        }
        ChannelSegment firstSegment = (ChannelSegment) minElem$iv;
        long r = bufferedChannel.getReceiversCounter$kotlinx_coroutines_core();
        long s = bufferedChannel.getSendersCounter$kotlinx_coroutines_core();
        ChannelSegment segment = firstSegment;
        while (true) {
            int i = 0;
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            while (true) {
                if (i < i2) {
                    boolean z2 = z;
                    int i3 = i2;
                    long globalCellIndex = (segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) i);
                    if (globalCellIndex < s || globalCellIndex < r) {
                        Object cellState = segment.getState$kotlinx_coroutines_core(i);
                        Object element = segment.getElement$kotlinx_coroutines_core(i);
                        if (cellState instanceof CancellableContinuation) {
                            cellStateString = (globalCellIndex >= r || globalCellIndex < s) ? (globalCellIndex >= s || globalCellIndex < r) ? "cont" : "send" : "receive";
                        } else if (cellState instanceof SelectInstance) {
                            cellStateString = (globalCellIndex >= r || globalCellIndex < s) ? (globalCellIndex >= s || globalCellIndex < r) ? "select" : "onSend" : "onReceive";
                        } else if (cellState instanceof ReceiveCatching) {
                            cellStateString = "receiveCatching";
                        } else if (cellState instanceof SendBroadcast) {
                            cellStateString = "sendBroadcast";
                        } else if (cellState instanceof WaiterEB) {
                            cellStateString = "EB(" + cellState + ')';
                        } else if (Intrinsics.areEqual(cellState, BufferedChannelKt.RESUMING_BY_RCV) || Intrinsics.areEqual(cellState, BufferedChannelKt.RESUMING_BY_EB)) {
                            cellStateString = "resuming_sender";
                        } else if (cellState == null || Intrinsics.areEqual(cellState, BufferedChannelKt.IN_BUFFER) || Intrinsics.areEqual(cellState, BufferedChannelKt.DONE_RCV) || Intrinsics.areEqual(cellState, BufferedChannelKt.POISONED) || Intrinsics.areEqual(cellState, BufferedChannelKt.INTERRUPTED_RCV) || Intrinsics.areEqual(cellState, BufferedChannelKt.INTERRUPTED_SEND) || Intrinsics.areEqual(cellState, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            i++;
                            i2 = i3;
                            z = z2;
                        } else {
                            cellStateString = cellState.toString();
                        }
                        if (element != null) {
                            sb.append('(' + cellStateString + ',' + element + "),");
                        } else {
                            sb.append(cellStateString + ',');
                        }
                        i++;
                        i2 = i3;
                        z = z2;
                    }
                } else {
                    boolean z3 = z;
                    ChannelSegment channelSegment = (ChannelSegment) segment.getNext();
                    if (channelSegment != null) {
                        segment = channelSegment;
                        z = z3;
                    }
                }
            }
        }
        if (StringsKt.last(sb) == ',') {
            Intrinsics.checkNotNullExpressionValue(sb.deleteCharAt(sb.length() - 1), "deleteCharAt(...)");
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String toStringDebug$kotlinx_coroutines_core() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = new StringBuilder().append("S=").append(getSendersCounter$kotlinx_coroutines_core()).append(",R=").append(getReceiversCounter$kotlinx_coroutines_core()).append(",B=").append(getBufferEndCounter()).append(",B'=").append(completedExpandBuffersAndPauseFlag$volatile$FU.get(this)).append(",C=");
        long $this$sendersCloseStatus$iv = sendersAndCloseStatus$volatile$FU.get(this);
        sb.append(sbAppend.append((int) ($this$sendersCloseStatus$iv >> 60)).append(',').toString());
        long $this$sendersCloseStatus$iv2 = sendersAndCloseStatus$volatile$FU.get(this);
        int $i$f$getSendersCloseStatus = (int) ($this$sendersCloseStatus$iv2 >> 60);
        switch ($i$f$getSendersCloseStatus) {
            case 1:
                sb.append("CANCELLATION_STARTED,");
                break;
            case 2:
                sb.append("CLOSED,");
                break;
            case 3:
                sb.append("CANCELLED,");
                break;
        }
        sb.append("SEND_SEGM=" + DebugStringsKt.getHexAddress(sendSegment$volatile$FU.get(this)) + ",RCV_SEGM=" + DebugStringsKt.getHexAddress(receiveSegment$volatile$FU.get(this)));
        if (!isRendezvousOrUnlimited()) {
            sb.append(",EB_SEGM=" + DebugStringsKt.getHexAddress(bufferEndSegment$volatile$FU.get(this)));
        }
        sb.append("  ");
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new ChannelSegment[]{receiveSegment$volatile$FU.get(this), sendSegment$volatile$FU.get(this), bufferEndSegment$volatile$FU.get(this)});
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            ChannelSegment it = (ChannelSegment) element$iv$iv;
            if (it != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(element$iv$iv);
            }
        }
        ArrayList $this$minBy$iv = arrayList;
        Iterator iterator$iv = $this$minBy$iv.iterator();
        if (!iterator$iv.hasNext()) {
            throw new NoSuchElementException();
        }
        Object minElem$iv = iterator$iv.next();
        if (iterator$iv.hasNext()) {
            ChannelSegment it2 = (ChannelSegment) minElem$iv;
            long minValue$iv = it2.id;
            do {
                Object e$iv = iterator$iv.next();
                ChannelSegment it3 = (ChannelSegment) e$iv;
                long v$iv = it3.id;
                if (minValue$iv > v$iv) {
                    minElem$iv = e$iv;
                    minValue$iv = v$iv;
                }
            } while (iterator$iv.hasNext());
        }
        ChannelSegment firstSegment = (ChannelSegment) minElem$iv;
        ChannelSegment channelSegment = firstSegment;
        while (true) {
            StringBuilder sbAppend2 = new StringBuilder().append(DebugStringsKt.getHexAddress(channelSegment)).append("=[").append(channelSegment.isRemoved() ? "*" : "").append(channelSegment.id).append(",prev=");
            ChannelSegment channelSegment2 = (ChannelSegment) channelSegment.getPrev();
            sb.append(sbAppend2.append(channelSegment2 != null ? DebugStringsKt.getHexAddress(channelSegment2) : null).append(',').toString());
            int i = BufferedChannelKt.SEGMENT_SIZE;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = i2;
                Object cellState = channelSegment.getState$kotlinx_coroutines_core(i3);
                Object element = channelSegment.getElement$kotlinx_coroutines_core(i3);
                String cellStateString = cellState instanceof CancellableContinuation ? "cont" : cellState instanceof SelectInstance ? "select" : cellState instanceof ReceiveCatching ? "receiveCatching" : cellState instanceof SendBroadcast ? "send(broadcast)" : cellState instanceof WaiterEB ? "EB(" + cellState + ')' : String.valueOf(cellState);
                sb.append('[' + i3 + "]=(" + cellStateString + ',' + element + "),");
            }
            StringBuilder sbAppend3 = new StringBuilder().append("next=");
            ChannelSegment channelSegment3 = (ChannelSegment) channelSegment.getNext();
            sb.append(sbAppend3.append(channelSegment3 != null ? DebugStringsKt.getHexAddress(channelSegment3) : null).append("]  ").toString());
            ChannelSegment channelSegment4 = (ChannelSegment) channelSegment.getNext();
            if (channelSegment4 == null) {
                return sb.toString();
            }
            channelSegment = channelSegment4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void checkSegmentStructureInvariants() {
        /*
            Method dump skipped, instruction units count: 645
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.checkSegmentStructureInvariants():void");
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.BufferedChannel$bindCancellationFunResult$1, reason: invalid class name */
    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function3<Throwable, ChannelResult<? extends E>, CoroutineContext, Unit> {
        AnonymousClass1(Object obj) {
            super(3, obj, BufferedChannel.class, "onCancellationChannelResultImplDoNotCall", "onCancellationChannelResultImplDoNotCall-5_sEAP8(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th, Object p2, CoroutineContext coroutineContext) {
            m1781invoke5_sEAP8(th, ((ChannelResult) p2).getHolder(), coroutineContext);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke-5_sEAP8, reason: not valid java name */
        public final void m1781invoke5_sEAP8(Throwable p0, Object p1, CoroutineContext p2) {
            ((BufferedChannel) this.receiver).m1775onCancellationChannelResultImplDoNotCall5_sEAP8(p0, p1, p2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KFunction<Unit> bindCancellationFunResult(Function1<? super E, Unit> function1) {
        return new AnonymousClass1(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCancellationChannelResultImplDoNotCall-5_sEAP8, reason: not valid java name */
    public final void m1775onCancellationChannelResultImplDoNotCall5_sEAP8(Throwable cause, Object element, CoroutineContext context) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        Intrinsics.checkNotNull(function1);
        Object objM1791getOrNullimpl = ChannelResult.m1791getOrNullimpl(element);
        Intrinsics.checkNotNull(objM1791getOrNullimpl);
        OnUndeliveredElementKt.callUndeliveredElement(function1, objM1791getOrNullimpl, context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Function3<Throwable, Object, CoroutineContext, Unit> bindCancellationFun(final Function1<? super E, Unit> function1, final E e) {
        return new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return BufferedChannel.bindCancellationFun$lambda$89(function1, e, (Throwable) obj, obj2, (CoroutineContext) obj3);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit bindCancellationFun$lambda$89(Function1 $this_bindCancellationFun, Object $element, Throwable th, Object obj, CoroutineContext context) {
        OnUndeliveredElementKt.callUndeliveredElement($this_bindCancellationFun, $element, context);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.BufferedChannel$bindCancellationFun$2, reason: invalid class name */
    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function3<Throwable, E, CoroutineContext, Unit> {
        AnonymousClass2(Object obj) {
            super(3, obj, BufferedChannel.class, "onCancellationImplDoNotCall", "onCancellationImplDoNotCall(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th, Object p2, CoroutineContext coroutineContext) {
            invoke2(th, p2, coroutineContext);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0, E e, CoroutineContext p2) {
            ((BufferedChannel) this.receiver).onCancellationImplDoNotCall(p0, e, p2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KFunction<Unit> bindCancellationFun(Function1<? super E, Unit> function1) {
        return new AnonymousClass2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onCancellationImplDoNotCall(Throwable cause, E element, CoroutineContext context) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        Intrinsics.checkNotNull(function1);
        OnUndeliveredElementKt.callUndeliveredElement(function1, element, context);
    }
}
