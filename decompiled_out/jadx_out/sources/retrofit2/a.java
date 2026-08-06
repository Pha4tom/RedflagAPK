package retrofit2;

import retrofit2.DefaultCallAdapterFactory;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ DefaultCallAdapterFactory.ExecutorCallbackCall.AnonymousClass1 b;
    public final /* synthetic */ Callback c;
    public final /* synthetic */ Object d;

    public /* synthetic */ a(DefaultCallAdapterFactory.ExecutorCallbackCall.AnonymousClass1 anonymousClass1, Callback callback, Object obj, int i) {
        this.a = i;
        this.b = anonymousClass1;
        this.c = callback;
        this.d = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.a;
        Callback callback = this.c;
        DefaultCallAdapterFactory.ExecutorCallbackCall.AnonymousClass1 anonymousClass1 = this.b;
        Object obj = this.d;
        switch (i) {
            case 0:
                anonymousClass1.lambda$onResponse$0(callback, (Response) obj);
                break;
            default:
                anonymousClass1.lambda$onFailure$1(callback, (Throwable) obj);
                break;
        }
    }
}
