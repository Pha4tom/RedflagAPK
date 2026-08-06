package pl.droidsonroids.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.io.IOException;
import java.util.List;
import pl.droidsonroids.gif.c;

/* JADX INFO: loaded from: classes2.dex */
public class GifImageView extends ImageView {
    public boolean a;

    public GifImageView(Context context, AttributeSet attributeSet) {
        c.a aVar;
        super(context, attributeSet);
        List<String> list = c.a;
        if (attributeSet == null || isInEditMode()) {
            aVar = new c.a();
        } else {
            aVar = new c.a(this, attributeSet);
            int i = aVar.b;
            if (i >= 0) {
                Drawable drawable = getDrawable();
                if (drawable instanceof a) {
                    ((a) drawable).setLoopCount(i);
                }
                Drawable background = getBackground();
                if (background instanceof a) {
                    ((a) background).setLoopCount(i);
                }
            }
        }
        this.a = aVar.a;
        int i2 = aVar.c;
        if (i2 > 0) {
            super.setImageResource(i2);
        }
        int i3 = aVar.d;
        if (i3 > 0) {
            super.setBackgroundResource(i3);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof b)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        b bVar = (b) parcelable;
        super.onRestoreInstanceState(bVar.getSuperState());
        Drawable drawable = getDrawable();
        long[][] jArr = bVar.a;
        if (jArr[0] != null && (drawable instanceof a)) {
            ((a) drawable).a(r0.q.q(r1, r0.p));
        }
        Drawable background = getBackground();
        if (jArr[1] == null || !(background instanceof a)) {
            return;
        }
        ((a) background).a(r0.q.q(r5, r0.p));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new b(super.onSaveInstanceState(), this.a ? getDrawable() : null, this.a ? getBackground() : null);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        if (c.a(this, false, i)) {
            return;
        }
        super.setBackgroundResource(i);
    }

    public void setFreezesAnimation(boolean z) {
        this.a = z;
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        if (c.a(this, true, i)) {
            return;
        }
        super.setImageResource(i);
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        boolean z;
        List<String> list = c.a;
        if (uri != null) {
            try {
                setImageDrawable(new a(getContext().getContentResolver(), uri));
                z = true;
            } catch (IOException unused) {
                z = false;
            }
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        super.setImageURI(uri);
    }
}
