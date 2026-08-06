package retrofit2.converter.gson;

import com.amz.apps.k42;
import com.amz.apps.lc0;
import com.amz.apps.le;
import com.amz.apps.pj0;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes2.dex */
final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final k42<T> adapter;
    private final lc0 gson;

    public GsonRequestBodyConverter(lc0 lc0Var, k42<T> k42Var) {
        this.gson = lc0Var;
        this.adapter = k42Var;
    }

    @Override // retrofit2.Converter
    public RequestBody convert(T t) throws IOException {
        le leVar = new le();
        pj0 pj0VarNewJsonWriter = this.gson.newJsonWriter(new OutputStreamWriter(leVar.outputStream(), UTF_8));
        this.adapter.write(pj0VarNewJsonWriter, t);
        pj0VarNewJsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, leVar.readByteString());
    }
}
