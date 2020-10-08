package com.skybooking.skyhotelservice.v1_0_0.client.action.event;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.event.PaymentInfoRQEV;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.event.PaymentSuccessRQEV;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.event.PaymentSucessMailRSEV;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentAction extends BaseAction {

    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    public PaymentSucessMailRSEV paymentSucces(PaymentSuccessRQEV request) {

        var bookingLanguageCached = bookingLanguageRedisRP.findById(request.getBookingCode());

        try {
            return client
                    .mutate()
                    .build()
                    .post()
                    .uri(appConfig.getEventService() + EndpointConstant.PaymentSuccess.V1_0_0)
                    .header("X-localization", bookingLanguageCached.get().getLanguage())
                    .header("X-localization", request.getLang())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaymentSucessMailRSEV.class)
                    .block();
        } catch (Exception exception) {
            throw exception;
        }

    }

    public PaymentSucessMailRSEV paymentInfo(PaymentInfoRQEV request) {

        var bookingLanguageCached = bookingLanguageRedisRP.findById(request.getBookingCode());

        try {
            return client
                    .mutate()
                    .build()
                    .post()
                    .uri(appConfig.getEventService() + EndpointConstant.PaymentInfo.V1_0_0)
//                    .uri("http://localhost:7010/v1.0.0/email/payment-info")
                    .header("X-localization", (request.getType().equals("all")) ? bookingLanguageCached.get().getLanguage() : request.getLang())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaymentSucessMailRSEV.class)
                    .block();
        } catch (Exception exception) {
            throw exception;
        }
    }

}
