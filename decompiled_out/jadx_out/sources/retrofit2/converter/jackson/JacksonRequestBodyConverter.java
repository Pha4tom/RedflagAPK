package retrofit2.converter.jackson;

import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes2.dex */
final class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");
    private final ObjectWriter adapter;

    public JacksonRequestBodyConverter(ObjectWriter objectWriter) {
        this.adapter = objectWriter;
    }

    @Override // retrofit2.Converter
    public RequestBody convert(T t) throws IOException {
        return RequestBody.create(MEDIA_TYPE, this.adapter.writeValueAsBytes(t));
    }
}
