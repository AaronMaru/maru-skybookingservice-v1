package com.skybooking.paymentservice.v1_0_0.service.implement.paymentForm;

import com.skybooking.paymentservice.constant.HotelBookingStatusConstant;
import com.skybooking.paymentservice.v1_0_0.client.hotel.action.HotelAction;
import com.skybooking.paymentservice.v1_0_0.util.generator.GeneralUtility;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
public class PaymentFormIP {

    @Autowired
    private Payments payments;

    @Autowired
    private HotelAction hotelAction;

    @Autowired
    private GeneralUtility general;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Payment Form For IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param model
     * @return
     */
    public String ipay88Form(@RequestParam Map<String, String> request, Model model) {

        var dataToken = payments.upadatePayment(request);
        general.updatePaymentBookingStatus(dataToken.getBookingCode(), HotelBookingStatusConstant.PAYMENT_CREATED);

        if (dataToken.getRender() == 1) {
            return "error";
        }
        payments.updateUrlToken(payments.upadatePayment(request).getId());
        payments.ipay88Payload(payments.getPaymentInfoHotel(dataToken, hotelAction.getMandatoryData(dataToken.getBookingCode())), model);
        return "ipay88/form";

    }


}
