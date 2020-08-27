package com.skybooking.skypointservice.v1_0_0.ui.controller.account;

import com.skybooking.skypointservice.v1_0_0.service.account.AccountSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/account")
public class AccountController {

    @Autowired
    private AccountSV accountSV;

    @GetMapping("balance")
    public StructureRS balance() {
        return accountSV.getBalance();
    }

    @GetMapping("backend-dashboard")
    public StructureRS backendDashboard() {
        return accountSV.backendDashboard();
    }

    @RequestMapping(value = "info/{userCode}", method = RequestMethod.POST)
    private StructureRS backendAccountInfo(@PathVariable(name = "userCode") String userCode) {
        return accountSV.backendAccountInfo(userCode);
    }

}
