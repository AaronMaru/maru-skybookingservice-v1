package com.skybooking.paymentservice.v1_0_0.util.generator;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Component
public class GeneralUtility {

    @Autowired
    private AppConfig appConfig;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Base Url
     * -----------------------------------------------------------------------------------------------------------------ÓØ
     *
     * @return
     */
    public String getBaseUrl() {
        return appConfig.getPaymentUrl();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encode for booking code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public String paymentEncode(PaymentRQ paymentRQ) {
        return paymentRQ.getBookingCode() + "_@8#-" + UUID.randomUUID();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Decode for booking code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param decodeToken
     * @return
     */
    public String paymentDecode(String decodeToken) {
        String[] splited = decodeToken.split("_@8#-");
        return splited[0];
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encode token base 64
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param token
     * @return
     */
    public String tokenEncodeBase64(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Decode token base 64
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param token
     * @return
     */
    public String tokenDecodeBase64(String token) {

        byte[] actualByte = Base64.getDecoder()
                .decode(token);
        return new String(actualByte);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encrypt signature for IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param signature
     * @return
     */
    public String encrypt(String signature) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA");
            md.update(signature.getBytes("UTF-8")); // step 3
            byte raw[] = md.digest(); // step 4
            String hash = Base64.getEncoder().encodeToString(raw); // step 5
            return hash; // step 6
        } catch (NoSuchAlgorithmException e) {
        } catch (java.io.UnsupportedEncodingException e) {
        }

        return null;

    }

    public static BigDecimal trimAmount(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

}
