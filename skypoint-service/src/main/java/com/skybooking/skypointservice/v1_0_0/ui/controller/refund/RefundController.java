package com.skybooking.skypointservice.v1_0_0.ui.controller.refund;

import com.skybooking.skypointservice.v1_0_0.service.refund.RefundSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.refund.RefundRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/refund")
public class RefundController {
    @Autowired
    private RefundSV refundSV;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public StructureRS refund(HttpServletRequest httpServletRequest, @Valid @RequestBody RefundRQ refundRQ) {
        return refundSV.refund(httpServletRequest, refundRQ);
    }
}
