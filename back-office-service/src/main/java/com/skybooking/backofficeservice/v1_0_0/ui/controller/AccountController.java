package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.account.AccountStructureServiceRS;
import com.skybooking.backofficeservice.v1_0_0.service.account.AccountSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/account")
public class AccountController {

    @Autowired
    private AccountSV accountSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get profile (skyuser, skyowner) detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return AccountStructureRS
     */
    @GetMapping("/detail")
    public AccountStructureServiceRS profileDetail(){
        try {
            AccountStructureServiceRS profileDetail = accountSV.profileDetail();

            return profileDetail;

        }catch (Exception exception){
            throw  exception;
        }
    }
}
