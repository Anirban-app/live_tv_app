package com.example.livetvapp.data.api;

import android.content.Context;
import androidx.annotation.NonNull;
import com.example.livetvapp.security.CryptoUtils;
import com.example.livetvapp.security.SecurityManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class EncryptedInterceptor implements Interceptor {
    private final Context context;

    public EncryptedInterceptor(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String deviceJson = SecurityManager.buildDeviceJson(context);
        byte[] sessionKey = SecurityManager.deriveSessionKey(context);
        byte[] masterKey = SecurityManager.getMasterKey();
        try {
            String encrypted = CryptoUtils.encryptAesBase64(sessionKey, deviceJson);
            long timestamp = System.currentTimeMillis();
            String signature = CryptoUtils.hmacSha256Base64(masterKey, encrypted + ":" + timestamp);

            Request request = chain.request().newBuilder()
                    .addHeader("X-Device-Auth", encrypted)
                    .addHeader("X-Signature", signature)
                    .addHeader("X-Timestamp", String.valueOf(timestamp))
                    .build();

            Response response = chain.proceed(request);
            return response;
        } catch (Exception e) {
            throw new IOException("Encryption handshake failed", e);
        } finally {
            CryptoUtils.clear(sessionKey);
            CryptoUtils.clear(masterKey);
        }
    }
}
