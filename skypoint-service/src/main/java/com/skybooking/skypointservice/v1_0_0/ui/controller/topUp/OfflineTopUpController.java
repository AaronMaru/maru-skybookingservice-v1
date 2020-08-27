package com.skybooking.skypointservice.v1_0_0.ui.controller.topUp;

import com.skybooking.skypointservice.v1_0_0.service.topUp.TopUpSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/top-up")
public class OfflineTopUpController {
    @Autowired
    private TopUpSV topUpSV;

    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    public StructureRS topUp(@ModelAttribute OfflineTopUpRQ offlineTopUpRQ) {
        return topUpSV.offlineTopUp(offlineTopUpRQ);
    }
}
