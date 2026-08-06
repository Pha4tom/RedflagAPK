package retrofit2.adapter.rxjava2;

import com.amz.apps.b10;
import com.amz.apps.hi1;
import com.amz.apps.m21;
import com.amz.apps.o51;
import com.amz.apps.ov;
import io.reactivex.exceptions.CompositeException;
import retrofit2.Call;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
final class CallExecuteObservable<T> extends m21<Response<T>> {
    private final Call<T> originalCall;

    public static final class CallDisposable implements ov {
        private final Call<?> call;

        public CallDisposable(Call<?> call) {
            this.call = call;
        }

        @Override // com.amz.apps.ov
        public void dispose() {
            this.call.cancel();
        }

        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }

    public CallExecuteObservable(Call<T> call) {
        this.originalCall = call;
    }

    @Override // com.amz.apps.m21
    public void subscribeActual(o51<? super Response<T>> o51Var) {
        boolean z;
        Call<T> callClone = this.originalCall.clone();
        o51Var.onSubscribe(new CallDisposable(callClone));
        try {
            Response<T> responseExecute = callClone.execute();
            if (!callClone.isCanceled()) {
                o51Var.onNext(responseExecute);
            }
            if (callClone.isCanceled()) {
                return;
            }
            try {
                o51Var.onComplete();
            } catch (Throwable th) {
                th = th;
                z = true;
                b10.throwIfFatal(th);
                if (z) {
                    hi1.onError(th);
                    return;
                }
                if (callClone.isCanceled()) {
                    return;
                }
                try {
                    o51Var.onError(th);
                } catch (Throwable th2) {
                    b10.throwIfFatal(th2);
                    hi1.onError(new CompositeException(th, th2));
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = false;
        }
    }
}
