package com.skybooking.skypointservice.v1_0_0.service.refund;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.refund.RefundRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RefundSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Refund point
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param refundRQ           RefundRQ
     * @return StructureRS
     */
    StructureRS refund(HttpServletRequest httpServletRequest, RefundRQ refundRQ);
}
