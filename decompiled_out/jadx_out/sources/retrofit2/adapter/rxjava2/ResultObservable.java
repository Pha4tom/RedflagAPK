package retrofit2.adapter.rxjava2;

import com.amz.apps.b10;
import com.amz.apps.hi1;
import com.amz.apps.m21;
import com.amz.apps.o51;
import com.amz.apps.ov;
import io.reactivex.exceptions.CompositeException;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
final class ResultObservable<T> extends m21<Result<T>> {
    private final m21<Response<T>> upstream;

    public static class ResultObserver<R> implements o51<Response<R>> {
        private final o51<? super Result<R>> observer;

        public ResultObserver(o51<? super Result<R>> o51Var) {
            this.observer = o51Var;
        }

        @Override // com.amz.apps.o51
        public void onComplete() {
            this.observer.onComplete();
        }

        @Override // com.amz.apps.o51
        public void onError(Throwable th) {
            try {
                this.observer.onNext(Result.error(th));
                this.observer.onComplete();
            } catch (Throwable th2) {
                try {
                    this.observer.onError(th2);
                } catch (Throwable th3) {
                    b10.throwIfFatal(th3);
                    hi1.onError(new CompositeException(th2, th3));
                }
            }
        }

        @Override // com.amz.apps.o51
        public void onNext(Response<R> response) {
            this.observer.onNext(Result.response(response));
        }

        @Override // com.amz.apps.o51
        public void onSubscribe(ov ovVar) {
            this.observer.onSubscribe(ovVar);
        }
    }

    public ResultObservable(m21<Response<T>> m21Var) {
        this.upstream = m21Var;
    }

    @Override // com.amz.apps.m21
    public void subscribeActual(o51<? super Result<T>> o51Var) {
        this.upstream.subscribe(new ResultObserver(o51Var));
    }
}
