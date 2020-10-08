package com.skybooking.skypointservice.v1_0_0.ui.controller.account;

import com.skybooking.skypointservice.v1_0_0.service.account.AccountSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("v1.0.0/account")
public class AccountController {

    @Autowired
    private AccountSV accountSV;

    @RequestMapping(value = "balance", method = RequestMethod.GET)
    public StructureRS balance(HttpServletRequest httpServletRequest) {
        return accountSV.getBalance(httpServletRequest);
    }

    @RequestMapping(value = "sky-owner/balance/info", method = RequestMethod.GET)
    public StructureRS getSkyOwnerAccountBalanceInfo(HttpServletRequest httpServletRequest, @RequestParam String userCode) {
        return accountSV.getSkyOwnerAccountBalanceInfo(httpServletRequest, userCode);
    }
}
