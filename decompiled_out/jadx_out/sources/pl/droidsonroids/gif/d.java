package pl.droidsonroids.gif;

import android.os.SystemClock;
import com.amz.apps.vi1;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: RenderTask.java */
/* JADX INFO: loaded from: classes2.dex */
public final class d extends vi1 {
    public d(a aVar) {
        super(aVar);
    }

    @Override // com.amz.apps.vi1
    public void doWork() {
        a aVar = this.a;
        long jN = aVar.q.n(aVar.p);
        if (jN >= 0) {
            this.a.c = SystemClock.uptimeMillis() + jN;
            if (this.a.isVisible() && this.a.b) {
                a aVar2 = this.a;
                if (!aVar2.v) {
                    aVar2.a.remove(this);
                    a aVar3 = this.a;
                    aVar3.z = aVar3.a.schedule(this, jN, TimeUnit.MILLISECONDS);
                }
            }
            if (!this.a.r.isEmpty() && this.a.getCurrentFrameIndex() == this.a.q.h() - 1) {
                a aVar4 = this.a;
                aVar4.w.sendEmptyMessageAtTime(aVar4.getCurrentLoop(), this.a.c);
            }
        } else {
            a aVar5 = this.a;
            aVar5.c = Long.MIN_VALUE;
            aVar5.b = false;
        }
        if (!this.a.isVisible() || this.a.w.hasMessages(-1)) {
            return;
        }
        this.a.w.sendEmptyMessageAtTime(-1, 0L);
    }
}
