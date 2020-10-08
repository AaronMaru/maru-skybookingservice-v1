package com.skybooking.skygatewayservice.utils;

import com.netflix.zuul.exception.ZuulException;
import feign.FeignException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AESEncryptionDecryption {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String secret = "aesskybooking";
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt) throws ZuulException {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new ZuulException(e, BAD_REQUEST.value(), "Error while decrypting data.");
        }
    }
 
    public static String decrypt(String strToDecrypt) throws ZuulException {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            throw new ZuulException(e, BAD_REQUEST.value(), "Incorrect encrypted data.");
        }
    }

    public static void main(String arg[]) throws ZuulException {
        JSONObject data = new JSONObject();
        data.put("amount", 500);
        data.put("paymentMethodCode", "JC");

        JSONObject contactInfo = new JSONObject();
        contactInfo.put("email", "momsambath@skybooking.info");
        contactInfo.put("name", "Dara Reak");
        contactInfo.put("phoneCode", "+855");
        contactInfo.put("phoneNumber", "9676765");


        data.put("contactInfo", contactInfo);
        String encrypt =  encrypt(data.toString());

        System.out.println("=== Encrypt: " + encrypt);
        System.out.println("=== Decrypt: " + decrypt(encrypt));
    }


}