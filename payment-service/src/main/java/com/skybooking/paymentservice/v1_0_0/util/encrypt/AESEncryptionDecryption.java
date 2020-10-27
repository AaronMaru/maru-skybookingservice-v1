package com.skybooking.paymentservice.v1_0_0.util.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESEncryptionDecryption {

    public static String encrypt(String text, String key, String initVector) throws Exception {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            throw new Exception("Error while decrypting data.", e);
        }
    }

    public static String decrypt(String encrypted, String key, String initVector) throws Exception {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception e) {
            throw new Exception("Incorrect encrypted data.", e);
        }
    }

    public static void main(String[] arg) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 500);
        jsonObject.put("transactionFor", "FLIGHT");
        jsonObject.put("referenceCode", "BFTX002343");
        String key = "12345678901234567890123456789012";
        String initVector = "1234567890123456";
        System.out.println(encrypt(jsonObject.toString(), key, initVector));

        System.out.println(decrypt("Jqt/xb7X+rlQz2oAhl+RvuDMXj2Op5ca3pDJkB1gwB2USYW63Xu1Kw2NEWpAK3ZalVgsJ/K/ev2wylflOspl89x6TM/8Mv54P3e6465ikgQ=", key, initVector));
    }
}
