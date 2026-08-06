package retrofit2.adapter.rxjava2;

import com.amz.apps.b10;
import com.amz.apps.hi1;
import com.amz.apps.m21;
import com.amz.apps.o51;
import com.amz.apps.ov;
import io.reactivex.exceptions.CompositeException;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
final class BodyObservable<T> extends m21<T> {
    private final m21<Response<T>> upstream;

    public static class BodyObserver<R> implements o51<Response<R>> {
        private final o51<? super R> observer;
        private boolean terminated;

        public BodyObserver(o51<? super R> o51Var) {
            this.observer = o51Var;
        }

        @Override // com.amz.apps.o51
        public void onComplete() {
            if (this.terminated) {
                return;
            }
            this.observer.onComplete();
        }

        @Override // com.amz.apps.o51
        public void onError(Throwable th) {
            if (!this.terminated) {
                this.observer.onError(th);
                return;
            }
            AssertionError assertionError = new AssertionError("This should never happen! Report as a bug with the full stacktrace.");
            assertionError.initCause(th);
            hi1.onError(assertionError);
        }

        @Override // com.amz.apps.o51
        public void onNext(Response<R> response) {
            if (response.isSuccessful()) {
                this.observer.onNext(response.body());
                return;
            }
            this.terminated = true;
            HttpException httpException = new HttpException(response);
            try {
                this.observer.onError(httpException);
            } catch (Throwable th) {
                b10.throwIfFatal(th);
                hi1.onError(new CompositeException(httpException, th));
            }
        }

        @Override // com.amz.apps.o51
        public void onSubscribe(ov ovVar) {
            this.observer.onSubscribe(ovVar);
        }
    }

    public BodyObservable(m21<Response<T>> m21Var) {
        this.upstream = m21Var;
    }

    @Override // com.amz.apps.m21
    public void subscribeActual(o51<? super T> o51Var) {
        this.upstream.subscribe(new BodyObserver(o51Var));
    }
}
