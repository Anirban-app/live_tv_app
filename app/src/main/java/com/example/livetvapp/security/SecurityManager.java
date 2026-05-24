package com.example.livetvapp.security;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityManager {
    private static final byte[] HARDCODED_KEY = new byte[] {
        0x19, 0x7b, 0x4d, 0x73, 0x2e, 0x34, 0x5c, 0x6f,
        0x48, 0x13, 0x2a, 0x7d, 0x5f, 0x66, 0x71, 0x2c,
        0x33, 0x50, 0x41, 0x1d, 0x29, 0x7e, 0x65, 0x5a,
        0x23, 0x6d, 0x14, 0x48, 0x1e, 0x0a, 0x78, 0x3c
    };

    public static String buildDeviceJson(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return String.format(Locale.US,
                "{\"androidId\":\"%s\",\"manufacturer\":\"%s\",\"model\":\"%s\",\"hardware\":\"%s\",\"timestamp\":%d}",
                androidId,
                sanitize(Build.MANUFACTURER),
                sanitize(Build.MODEL),
                sanitize(Build.HARDWARE),
                System.currentTimeMillis());
    }

    public static byte[] getMasterKey() {
        return HARDCODED_KEY.clone();
    }

    public static byte[] deriveSessionKey(Context context) {
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            String salt = String.format(Locale.US, "%s|%s|%s", Build.MANUFACTURER, Build.MODEL, Build.HARDWARE);
            PBEKeySpec spec = new PBEKeySpec((androidId + salt).toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 10000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();
            return keyBytes;
        } catch (Exception e) {
            return getMasterKey();
        }
    }

    private static String sanitize(String value) {
        return value == null ? "unknown" : value.replace('"', ' ').replace('\n', ' ');
    }
}
