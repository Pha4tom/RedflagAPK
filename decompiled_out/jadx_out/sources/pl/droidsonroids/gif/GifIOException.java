package pl.droidsonroids.gif;

import java.io.IOException;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class GifIOException extends IOException {
    private static final long serialVersionUID = 13038402904505L;
    public final GifError a;
    public final String b;

    public GifIOException(int i, String str) {
        GifError gifError;
        GifError[] gifErrorArrValues = GifError.values();
        int length = gifErrorArrValues.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                gifError = GifError.d;
                gifError.b = i;
                break;
            } else {
                gifError = gifErrorArrValues[i2];
                if (gifError.b == i) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        this.a = gifError;
        this.b = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        GifError gifError = this.a;
        String str = this.b;
        if (str == null) {
            gifError.getClass();
            return String.format(Locale.ENGLISH, "GifError %d: %s", Integer.valueOf(gifError.b), gifError.a);
        }
        StringBuilder sb = new StringBuilder();
        gifError.getClass();
        sb.append(String.format(Locale.ENGLISH, "GifError %d: %s", Integer.valueOf(gifError.b), gifError.a));
        sb.append(": ");
        sb.append(str);
        return sb.toString();
    }
}
