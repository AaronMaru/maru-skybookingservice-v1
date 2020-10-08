package com.skybooking.skypointservice.v1_0_0.ui.controller.topUp;

import com.skybooking.skypointservice.v1_0_0.service.topUp.TopUpSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.ConfirmOfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/top-up")
public class OfflineTopUpController {
    @Autowired
    private TopUpSV topUpSV;

    @PreAuthorize("#oauth2.hasScope('offline-topup-skypoint')")
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    public StructureRS topUp(HttpServletRequest httpServletRequest, @ModelAttribute OfflineTopUpRQ offlineTopUpRQ) {
        return topUpSV.offlineTopUp(httpServletRequest, offlineTopUpRQ);
    }

    @RequestMapping(value = "/offline/confirm", method = RequestMethod.POST)
    public StructureRS confirmTopUp(HttpServletRequest httpServletRequest, @RequestBody ConfirmOfflineTopUpRQ confirmOfflineTopUpRQ) {
        return topUpSV.confirmOfflineTopUp(httpServletRequest, confirmOfflineTopUpRQ);
    }
}
