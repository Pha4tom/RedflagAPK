package retrofit2;

import com.amz.apps.cg;
import com.amz.apps.dg;
import com.amz.apps.dh1;
import com.amz.apps.g52;
import com.amz.apps.kh0;
import com.amz.apps.lh0;
import com.amz.apps.p80;
import com.amz.apps.tr;
import com.amz.apps.vr;
import com.amz.apps.yo;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Method;
import kotlin.KotlinNullPointerException;
import kotlin.Result;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.apache.commons.lang3.ClassUtils;

/* JADX INFO: compiled from: KotlinExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class KotlinExtensions {

    /* JADX INFO: renamed from: retrofit2.KotlinExtensions$suspendAndThrow$1, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinExtensions.kt */
    @tr(c = "retrofit2.KotlinExtensions", f = "KotlinExtensions.kt", l = {113}, m = "suspendAndThrow")
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(yo yoVar) {
            super(yoVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KotlinExtensions.suspendAndThrow(null, this);
        }
    }

    public static final <T> Object await(final Call<T> call, yo<? super T> yoVar) {
        final dg dgVar = new dg(IntrinsicsKt__IntrinsicsJvmKt.intercepted(yoVar), 1);
        dgVar.invokeOnCancellation(new p80<Throwable, g52>() { // from class: retrofit2.KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$1
            {
                super(1);
            }

            @Override // com.amz.apps.p80
            public /* bridge */ /* synthetic */ g52 invoke(Throwable th) {
                invoke2(th);
                return g52.a;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                call.cancel();
            }
        });
        call.enqueue(new Callback<T>() { // from class: retrofit2.KotlinExtensions$await$2$2
            @Override // retrofit2.Callback
            public void onFailure(Call<T> call2, Throwable th) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(th, "t");
                cg cgVar = dgVar;
                int i = Result.b;
                cgVar.resumeWith(Result.m83constructorimpl(dh1.createFailure(th)));
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<T> call2, Response<T> response) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(response, "response");
                if (!response.isSuccessful()) {
                    cg cgVar = dgVar;
                    HttpException httpException = new HttpException(response);
                    int i = Result.b;
                    cgVar.resumeWith(Result.m83constructorimpl(dh1.createFailure(httpException)));
                    return;
                }
                T tBody = response.body();
                if (tBody != null) {
                    dgVar.resumeWith(Result.m83constructorimpl(tBody));
                    return;
                }
                Object objTag = call2.request().tag(Invocation.class);
                if (objTag == null) {
                    kh0.throwNpe();
                }
                kh0.checkExpressionValueIsNotNull(objTag, "call.request().tag(Invocation::class.java)!!");
                Method method = ((Invocation) objTag).method();
                StringBuilder sb = new StringBuilder("Response from ");
                kh0.checkExpressionValueIsNotNull(method, FirebaseAnalytics.Param.METHOD);
                Class<?> declaringClass = method.getDeclaringClass();
                kh0.checkExpressionValueIsNotNull(declaringClass, "method.declaringClass");
                sb.append(declaringClass.getName());
                sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                sb.append(method.getName());
                sb.append(" was null but response body type was declared as non-null");
                KotlinNullPointerException kotlinNullPointerException = new KotlinNullPointerException(sb.toString());
                cg cgVar2 = dgVar;
                int i2 = Result.b;
                cgVar2.resumeWith(Result.m83constructorimpl(dh1.createFailure(kotlinNullPointerException)));
            }
        });
        Object result = dgVar.getResult();
        if (result == lh0.getCOROUTINE_SUSPENDED()) {
            vr.probeCoroutineSuspended(yoVar);
        }
        return result;
    }

    public static final <T> Object awaitNullable(final Call<T> call, yo<? super T> yoVar) {
        final dg dgVar = new dg(IntrinsicsKt__IntrinsicsJvmKt.intercepted(yoVar), 1);
        dgVar.invokeOnCancellation(new p80<Throwable, g52>() { // from class: retrofit2.KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$2
            {
                super(1);
            }

            @Override // com.amz.apps.p80
            public /* bridge */ /* synthetic */ g52 invoke(Throwable th) {
                invoke2(th);
                return g52.a;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                call.cancel();
            }
        });
        call.enqueue(new Callback<T>() { // from class: retrofit2.KotlinExtensions$await$4$2
            @Override // retrofit2.Callback
            public void onFailure(Call<T> call2, Throwable th) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(th, "t");
                cg cgVar = dgVar;
                int i = Result.b;
                cgVar.resumeWith(Result.m83constructorimpl(dh1.createFailure(th)));
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<T> call2, Response<T> response) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(response, "response");
                if (response.isSuccessful()) {
                    dgVar.resumeWith(Result.m83constructorimpl(response.body()));
                    return;
                }
                cg cgVar = dgVar;
                HttpException httpException = new HttpException(response);
                int i = Result.b;
                cgVar.resumeWith(Result.m83constructorimpl(dh1.createFailure(httpException)));
            }
        });
        Object result = dgVar.getResult();
        if (result == lh0.getCOROUTINE_SUSPENDED()) {
            vr.probeCoroutineSuspended(yoVar);
        }
        return result;
    }

    public static final <T> Object awaitResponse(final Call<T> call, yo<? super Response<T>> yoVar) {
        final dg dgVar = new dg(IntrinsicsKt__IntrinsicsJvmKt.intercepted(yoVar), 1);
        dgVar.invokeOnCancellation(new p80<Throwable, g52>() { // from class: retrofit2.KotlinExtensions$awaitResponse$$inlined$suspendCancellableCoroutine$lambda$1
            {
                super(1);
            }

            @Override // com.amz.apps.p80
            public /* bridge */ /* synthetic */ g52 invoke(Throwable th) {
                invoke2(th);
                return g52.a;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                call.cancel();
            }
        });
        call.enqueue(new Callback<T>() { // from class: retrofit2.KotlinExtensions$awaitResponse$2$2
            @Override // retrofit2.Callback
            public void onFailure(Call<T> call2, Throwable th) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(th, "t");
                cg cgVar = dgVar;
                int i = Result.b;
                cgVar.resumeWith(Result.m83constructorimpl(dh1.createFailure(th)));
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<T> call2, Response<T> response) {
                kh0.checkParameterIsNotNull(call2, "call");
                kh0.checkParameterIsNotNull(response, "response");
                dgVar.resumeWith(Result.m83constructorimpl(response));
            }
        });
        Object result = dgVar.getResult();
        if (result == lh0.getCOROUTINE_SUSPENDED()) {
            vr.probeCoroutineSuspended(yoVar);
        }
        return result;
    }

    public static final /* synthetic */ <T> T create(Retrofit retrofit) {
        kh0.checkParameterIsNotNull(retrofit, "$this$create");
        kh0.reifiedOperationMarker(4, "T");
        return (T) retrofit.create(Object.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object suspendAndThrow(final java.lang.Exception r4, com.amz.apps.yo<?> r5) throws java.lang.Throwable {
        /*
            boolean r0 = r5 instanceof retrofit2.KotlinExtensions.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r5
            retrofit2.KotlinExtensions$suspendAndThrow$1 r0 = (retrofit2.KotlinExtensions.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            retrofit2.KotlinExtensions$suspendAndThrow$1 r0 = new retrofit2.KotlinExtensions$suspendAndThrow$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = com.amz.apps.lh0.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r4 = r0.L$0
            java.lang.Exception r4 = (java.lang.Exception) r4
            com.amz.apps.dh1.throwOnFailure(r5)
            goto L5c
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            com.amz.apps.dh1.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            kotlinx.coroutines.b r5 = com.amz.apps.mv.getDefault()
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            retrofit2.KotlinExtensions$suspendAndThrow$$inlined$suspendCoroutineUninterceptedOrReturn$lambda$1 r3 = new retrofit2.KotlinExtensions$suspendAndThrow$$inlined$suspendCoroutineUninterceptedOrReturn$lambda$1
            r3.<init>()
            r5.dispatch(r2, r3)
            java.lang.Object r4 = com.amz.apps.lh0.getCOROUTINE_SUSPENDED()
            java.lang.Object r5 = com.amz.apps.lh0.getCOROUTINE_SUSPENDED()
            if (r4 != r5) goto L59
            com.amz.apps.vr.probeCoroutineSuspended(r0)
        L59:
            if (r4 != r1) goto L5c
            return r1
        L5c:
            com.amz.apps.g52 r4 = com.amz.apps.g52.a
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.KotlinExtensions.suspendAndThrow(java.lang.Exception, com.amz.apps.yo):java.lang.Object");
    }
}
