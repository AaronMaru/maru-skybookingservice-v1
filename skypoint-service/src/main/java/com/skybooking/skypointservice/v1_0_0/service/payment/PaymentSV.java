package com.skybooking.skypointservice.v1_0_0.service.payment;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PaymentSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Earning point step check (Validate)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ PaymentRQ
     * @return StructureRS
     */
    StructureRS earningCheck(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Earning point step confirm (Save earned point transaction)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param paymentRQ          PaymentRQ
     * @return StructureRS
     */
    StructureRS earningConfirm(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Redeem point step check (Validate account and amount)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param paymentRQ          PaymentRQ
     * @return StructureRS
     */
    StructureRS redeemCheck(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Redeem point step confirm (Save redeemed point transaction)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param paymentRQ          PaymentRQ
     * @return StructureRS
     */
    StructureRS redeemConfirm(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ);
}
