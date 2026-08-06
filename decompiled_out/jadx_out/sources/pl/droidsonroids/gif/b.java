package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: compiled from: GifViewSavedState.java */
/* JADX INFO: loaded from: classes2.dex */
public final class b extends View.BaseSavedState {
    public static final Parcelable.Creator<b> CREATOR = new a();
    public final long[][] a;

    /* JADX INFO: compiled from: GifViewSavedState.java */
    public class a implements Parcelable.Creator<b> {
        @Override // android.os.Parcelable.Creator
        public b createFromParcel(Parcel parcel) {
            return new b(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public b[] newArray(int i) {
            return new b[i];
        }
    }

    public b(Parcel parcel) {
        super(parcel);
        this.a = new long[parcel.readInt()][];
        int i = 0;
        while (true) {
            long[][] jArr = this.a;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = parcel.createLongArray();
            i++;
        }
    }

    public b(Parcelable parcelable, Drawable... drawableArr) {
        super(parcelable);
        this.a = new long[drawableArr.length][];
        for (int i = 0; i < drawableArr.length; i++) {
            Drawable drawable = drawableArr[i];
            if (drawable instanceof pl.droidsonroids.gif.a) {
                this.a[i] = ((pl.droidsonroids.gif.a) drawable).q.i();
            } else {
                this.a[i] = null;
            }
        }
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        long[][] jArr = this.a;
        parcel.writeInt(jArr.length);
        for (long[] jArr2 : jArr) {
            parcel.writeLongArray(jArr2);
        }
    }
}
