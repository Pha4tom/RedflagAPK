package retrofit2.converter.gson;

import com.amz.apps.gj0;
import com.amz.apps.k42;
import com.amz.apps.lc0;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes2.dex */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final k42<T> adapter;
    private final lc0 gson;

    public GsonResponseBodyConverter(lc0 lc0Var, k42<T> k42Var) {
        this.gson = lc0Var;
        this.adapter = k42Var;
    }

    @Override // retrofit2.Converter
    public T convert(ResponseBody responseBody) throws IOException {
        gj0 gj0VarNewJsonReader = this.gson.newJsonReader(responseBody.charStream());
        try {
            T t = this.adapter.read(gj0VarNewJsonReader);
            if (gj0VarNewJsonReader.peek() == JsonToken.END_DOCUMENT) {
                return t;
            }
            throw new JsonIOException("JSON document was not fully consumed.");
        } finally {
            responseBody.close();
        }
    }
}
