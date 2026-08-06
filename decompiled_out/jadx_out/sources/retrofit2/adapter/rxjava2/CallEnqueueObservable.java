package retrofit2.adapter.rxjava2;

import com.amz.apps.b10;
import com.amz.apps.hi1;
import com.amz.apps.m21;
import com.amz.apps.o51;
import com.amz.apps.ov;
import io.reactivex.exceptions.CompositeException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
final class CallEnqueueObservable<T> extends m21<Response<T>> {
    private final Call<T> originalCall;

    public static final class CallCallback<T> implements ov, Callback<T> {
        private final Call<?> call;
        private final o51<? super Response<T>> observer;
        boolean terminated = false;

        public CallCallback(Call<?> call, o51<? super Response<T>> o51Var) {
            this.call = call;
            this.observer = o51Var;
        }

        @Override // com.amz.apps.ov
        public void dispose() {
            this.call.cancel();
        }

        public boolean isDisposed() {
            return this.call.isCanceled();
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<T> call, Throwable th) {
            if (call.isCanceled()) {
                return;
            }
            try {
                this.observer.onError(th);
            } catch (Throwable th2) {
                b10.throwIfFatal(th2);
                hi1.onError(new CompositeException(th, th2));
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<T> call, Response<T> response) {
            if (call.isCanceled()) {
                return;
            }
            try {
                this.observer.onNext(response);
                if (call.isCanceled()) {
                    return;
                }
                this.terminated = true;
                this.observer.onComplete();
            } catch (Throwable th) {
                if (this.terminated) {
                    hi1.onError(th);
                    return;
                }
                if (call.isCanceled()) {
                    return;
                }
                try {
                    this.observer.onError(th);
                } catch (Throwable th2) {
                    b10.throwIfFatal(th2);
                    hi1.onError(new CompositeException(th, th2));
                }
            }
        }
    }

    public CallEnqueueObservable(Call<T> call) {
        this.originalCall = call;
    }

    @Override // com.amz.apps.m21
    public void subscribeActual(o51<? super Response<T>> o51Var) {
        Call<T> callClone = this.originalCall.clone();
        CallCallback callCallback = new CallCallback(callClone, o51Var);
        o51Var.onSubscribe(callCallback);
        callClone.enqueue(callCallback);
    }
}
