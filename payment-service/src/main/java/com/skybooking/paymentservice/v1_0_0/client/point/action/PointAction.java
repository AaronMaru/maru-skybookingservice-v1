package com.skybooking.paymentservice.v1_0_0.client.point.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PostOnlineTopUpRQ;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.TransactionRQ;
import com.skybooking.paymentservice.v1_0_0.util.auth.AuthUtility;
import com.skybooking.paymentservice.v1_0_1.ui.model.response.StructureRS;
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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method in booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param transactionRQ
     * @return
     */
    public StructureRS getMandatoryData(TransactionRQ transactionRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getPointUrl() + appConfig.getPointVersion() + "/transaction/detail")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(transactionRQ)
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

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getPointUrl() + appConfig.getPointVersion() + "/top-up/online/post")
                .header("X-localization", "en")
                .bodyValue(postOnlineTopUpRQ)
                .retrieve()
                .bodyToMono(StructureRS.class)
                .block();
    }

}
