package com.skybooking.skypointservice.v1_0_0.ui.controller.topUp;

import com.skybooking.skypointservice.v1_0_0.service.topUp.TopUpSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.PostOnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/top-up")
public class OnlineTopUpController {
    @Autowired
    private TopUpSV topUpSV;

    @RequestMapping(value = "/online/pre")
    public StructureRS preTopUp(@RequestBody OnlineTopUpRQ onlineTopUpRQ) {
        return topUpSV.preTopUp(onlineTopUpRQ);
    }

    @RequestMapping(value = "/online/proceed")
    public StructureRS proceedTopUp(@RequestBody OnlineTopUpRQ onlineTopUpRQ) {
        return topUpSV.proceedTopUp(onlineTopUpRQ);
    }

    @RequestMapping(value = "/online/post")
    public StructureRS postTopUp(@RequestBody PostOnlineTopUpRQ postOnlineTopUpRQ) {
        return topUpSV.postTopUp(postOnlineTopUpRQ);
    }
}
