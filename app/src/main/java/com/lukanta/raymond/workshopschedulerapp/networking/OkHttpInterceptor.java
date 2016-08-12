package com.lukanta.raymond.workshopschedulerapp.networking;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by raymondlukanta on 28/05/16.
 */
public class OkHttpInterceptor implements Interceptor {
    private static final String TAG = "OkHttpInterceptor";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        long t1 = System.nanoTime();

        Request modifiedRequest = originalRequest.newBuilder().build();

        Log.i(TAG, String.format("Sending %s request %s on %s%n%sRequest Body: %s",
                modifiedRequest.method(), modifiedRequest.url(), chain.connection(), modifiedRequest.headers(), bodyToString(modifiedRequest)));

        Response response = chain.proceed(modifiedRequest);

        long t2 = System.nanoTime();

        Log.i(TAG, String.format("Received response for %s %s in %.1fms%n%s",
                response.request().method(), response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();

            RequestBody body = copy.body();
            if (body != null) {
                body.writeTo(buffer);
            } else {
                return "{}";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
