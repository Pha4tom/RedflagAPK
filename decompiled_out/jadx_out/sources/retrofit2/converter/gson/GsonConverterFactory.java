package retrofit2.converter.gson;

import com.amz.apps.lc0;
import com.amz.apps.o42;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: classes2.dex */
public final class GsonConverterFactory extends Converter.Factory {
    private final lc0 gson;

    private GsonConverterFactory(lc0 lc0Var) {
        this.gson = lc0Var;
    }

    public static GsonConverterFactory create() {
        return create(new lc0());
    }

    public static GsonConverterFactory create(lc0 lc0Var) {
        if (lc0Var != null) {
            return new GsonConverterFactory(lc0Var);
        }
        throw new NullPointerException("gson == null");
    }

    @Override // retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        return new GsonRequestBodyConverter(this.gson, this.gson.getAdapter(o42.get(type)));
    }

    @Override // retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        return new GsonResponseBodyConverter(this.gson, this.gson.getAdapter(o42.get(type)));
    }
}
