package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: GifViewUtils.java */
/* JADX INFO: loaded from: classes2.dex */
public final class c {
    public static final List<String> a = Arrays.asList("raw", "drawable", "mipmap");

    /* JADX INFO: compiled from: GifViewUtils.java */
    public static class a extends b {
        public final int c;
        public final int d;

        public a() {
            this.c = 0;
            this.d = 0;
        }

        public a(ImageView imageView, AttributeSet attributeSet) {
            super(imageView, attributeSet);
            this.c = a(imageView, attributeSet, true);
            this.d = a(imageView, attributeSet, false);
        }

        public static int a(ImageView imageView, AttributeSet attributeSet, boolean z) {
            int attributeResourceValue = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", z ? "src" : "background", 0);
            if (attributeResourceValue > 0) {
                if (c.a.contains(imageView.getResources().getResourceTypeName(attributeResourceValue)) && !c.a(imageView, z, attributeResourceValue)) {
                    return attributeResourceValue;
                }
            }
            return 0;
        }
    }

    /* JADX INFO: compiled from: GifViewUtils.java */
    public static class b {
        public final boolean a;
        public final int b;

        public b() {
            this.a = false;
            this.b = -1;
        }

        public b(View view, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.GifView, 0, 0);
            this.a = typedArrayObtainStyledAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
            this.b = typedArrayObtainStyledAttributes.getInt(R.styleable.GifView_loopCount, -1);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static boolean a(ImageView imageView, boolean z, int i) {
        Resources resources = imageView.getResources();
        if (resources != null) {
            try {
                if (!a.contains(resources.getResourceTypeName(i))) {
                    return false;
                }
                pl.droidsonroids.gif.a aVar = new pl.droidsonroids.gif.a(resources, i);
                if (z) {
                    imageView.setImageDrawable(aVar);
                    return true;
                }
                imageView.setBackground(aVar);
                return true;
            } catch (Resources.NotFoundException | IOException unused) {
            }
        }
        return false;
    }
}
