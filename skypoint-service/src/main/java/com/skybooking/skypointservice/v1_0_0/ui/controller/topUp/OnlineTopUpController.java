package com.skybooking.skypointservice.v1_0_0.ui.controller.topUp;

import com.skybooking.skypointservice.v1_0_0.service.topUp.TopUpSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.PostOnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/top-up")
public class OnlineTopUpController {
    @Autowired
    private TopUpSV topUpSV;

    @RequestMapping(value = "/online/pre", method = RequestMethod.POST)
    public StructureRS preTopUp(HttpServletRequest httpServletRequest, @Validated @RequestBody OnlineTopUpRQ onlineTopUpRQ) {
        return topUpSV.preTopUp(httpServletRequest, onlineTopUpRQ);
    }

    @RequestMapping(value = "/online/proceed", method = RequestMethod.POST)
    public StructureRS proceedTopUp(HttpServletRequest httpServletRequest, @Validated @RequestBody OnlineTopUpRQ onlineTopUpRQ) {
        return topUpSV.proceedTopUp(httpServletRequest, onlineTopUpRQ);
    }

    @RequestMapping(value = "/online/post", method = RequestMethod.POST)
    public StructureRS postTopUp(HttpServletRequest httpServletRequest, @RequestBody PostOnlineTopUpRQ postOnlineTopUpRQ) {
        return topUpSV.postTopUp(httpServletRequest, postOnlineTopUpRQ);
    }
}
