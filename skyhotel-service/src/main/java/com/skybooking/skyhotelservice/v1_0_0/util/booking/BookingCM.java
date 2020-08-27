package com.skybooking.skyhotelservice.v1_0_0.util.booking;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PriceInfoRS;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.NumberFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookingCM {

    public PriceInfoRS getPriceInfo(HotelBookingEntity booking, PaymentMandatoryRQ paymentRQ) {

        PriceInfoRS priceInfo = new PriceInfoRS();

        BigDecimal costAmount = NumberFormatter.trimAmount(booking.getCost().add(booking.getMarkupAmount()).add(booking.getPaymentFeeAmount()));

        BigDecimal totalAmount = booking.getTotalAmount();
        BigDecimal discountPaymentPercentage = paymentRQ.getPercentage().divide(BigDecimal.valueOf(100));

        BigDecimal discountPaymentAmount = NumberFormatter.trimAmount(totalAmount.multiply(discountPaymentPercentage));
        BigDecimal paidAmount = totalAmount.subtract(discountPaymentAmount);

        BigDecimal basePaymentPercentage = paymentRQ.getPercentageBase().divide(BigDecimal.valueOf(100));
        BigDecimal basePaymentAmount = NumberFormatter.trimAmount(paidAmount.multiply(basePaymentPercentage));

        /**
         * Set price info
         */
        priceInfo.setCostAmount(costAmount);

        //General markup 5%
        priceInfo.setMarkupPaymentMethodPercentage(booking.getPaymentFeePercentage());
        priceInfo.setMarkupPaymentMethodAmount(booking.getPaymentFeeAmount());

        //Markup price 3%
        priceInfo.setDiscountPaymentMethodPercentage(discountPaymentPercentage);
        priceInfo.setDiscountPaymentMethodAmount(discountPaymentAmount);

        //Base markup 1.5%
        priceInfo.setBasePaymentPercentage(basePaymentPercentage);
        priceInfo.setBasePaymentAmount(basePaymentAmount);

        priceInfo.setPaidAmount(paidAmount);

        return priceInfo;

    }

}
