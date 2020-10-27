package com.skybooking.skygatewayservice.utils;

import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


public class AESEncryptionDecryption {

    public static String encrypt(String text, String key, String initVector) throws ZuulException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            throw new ZuulException(e, BAD_REQUEST.value(), "Error while decrypting data.");
        }
    }

    public static String decrypt(String encrypted, String key, String initVector) throws ZuulException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception e) {
            throw new ZuulException(e, BAD_REQUEST.value(), "Incorrect encrypted data.");
        }
    }

    public static void main(String[] arg) throws ZuulException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "PTS030009720");
        String key = "12345678901234567890123456789012";
        String initVector = "1234567890123456";
        System.out.println(encrypt(jsonObject.toString(), key, initVector));

        System.out.println(decrypt("aXoy+jOwkpeAPhGqjIcq6lNDdgRXGqY0V52EtTGYe7A=", key, initVector));
    }
}
