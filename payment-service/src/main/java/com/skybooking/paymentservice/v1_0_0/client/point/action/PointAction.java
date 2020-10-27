package com.skybooking.paymentservice.v1_0_0.client.point.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PointRQ;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PostOnlineTopUpRQ;
import com.skybooking.paymentservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.paymentservice.v1_0_0.util.auth.AuthUtility;
import com.skybooking.paymentservice.v1_0_0.util.encrypt.AESEncryptionDecryption;
import com.skybooking.paymentservice.v1_0_1.ui.model.response.StructureRS;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PointAction {

    @Autowired
    private WebClient client;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;


    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method in booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pointRQ
     * @return
     */
    public StructureRS getMandatoryData(PointRQ pointRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getPointUrl() + appConfig.getPointVersion() + "/transaction/detail")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(pointRQ)
                .retrieve()
                .bodyToMono(StructureRS.class)
                .block();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method in booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param postOnlineTopUpRQ
     * @return
     */
    public StructureRS paymentPointTopUpPost(PostOnlineTopUpRQ postOnlineTopUpRQ) {

        var bookingLanguageCached = bookingLanguageRedisRP.findById(postOnlineTopUpRQ.getTransactionCode());

        PointRQ pointRQ = new PointRQ();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionCode", postOnlineTopUpRQ.getTransactionCode());
        jsonObject.put("paymentStatus", postOnlineTopUpRQ.getPaymentStatus());

        String key = "12345678901234567890123456789012";
        String initVector = "1234567890123456";

        try {
            pointRQ.setData(AESEncryptionDecryption.encrypt(jsonObject.toString(), key, initVector));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getPointUrl() + appConfig.getPointVersion() + "/top-up/online/post")
                .header("X-localization", bookingLanguageCached.get().getLanguage())
                .bodyValue(pointRQ)
                .retrieve()
                .bodyToMono(StructureRS.class)
                .block();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Pay Flight or Hotel By Redeem
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pointRQ
     * @return
     */
    public StructureRS payByRedeemPoint(PointRQ pointRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getPointUrl() + appConfig.getPointVersion() + "/payment/redeem/confirm")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(pointRQ)
                .retrieve()
                .bodyToMono(StructureRS.class)
                .block();
    }
}
