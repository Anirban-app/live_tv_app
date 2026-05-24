package com.example.livetvapp.security;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    public static byte[] generateRandomBytes(int length) {
        byte[] key = new byte[length];
        new SecureRandom().nextBytes(key);
        return key;
    }

    public static String encryptAesBase64(byte[] key, String plainText) throws Exception {
        byte[] iv = generateRandomBytes(16);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] ciphertext = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] output = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, output, 0, iv.length);
        System.arraycopy(ciphertext, 0, output, iv.length, ciphertext.length);
        return Base64.encodeToString(output, Base64.NO_WRAP);
    }

    public static String decryptAesBase64(byte[] key, String base64Text) throws Exception {
        byte[] data = Base64.decode(base64Text, Base64.NO_WRAP);
        byte[] iv = new byte[16];
        System.arraycopy(data, 0, iv, 0, iv.length);
        byte[] ciphertext = new byte[data.length - iv.length];
        System.arraycopy(data, iv.length, ciphertext, 0, ciphertext.length);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] plainBytes = cipher.doFinal(ciphertext);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    public static String hmacSha256Base64(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_ALGORITHM);
        mac.init(secretKey);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(rawHmac, Base64.NO_WRAP);
    }

    public static void clear(byte[] data) {
        if (data != null) {
            Arrays.fill(data, (byte) 0);
        }
    }
}
