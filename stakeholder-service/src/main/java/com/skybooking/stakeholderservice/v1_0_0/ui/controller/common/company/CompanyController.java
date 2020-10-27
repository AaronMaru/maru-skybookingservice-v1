package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.company;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company.CompanySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyUserRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/utils")
public class CompanyController {

    @Autowired
    private CompanySV companySV;

    @Autowired
    private LocalizationBean localization;

    @GetMapping("company")
    public ResRS companyInfo(@RequestParam String keyword) {

        return localization.resAPI(HttpStatus.OK, "res_succ",
                keyword.equals("") ? null : companySV.listCompany(keyword));

    }

    @GetMapping("company/{companyCode}")
    public ResRS companyDetail(@PathVariable String companyCode) {

        return localization.resAPI(HttpStatus.OK, "res_succ", companySV.companyDetail(companyCode));

    }

    @GetMapping("company-user")
    public ResRS companyOrUser(@RequestParam String keyword) {

        return localization.resAPI(HttpStatus.OK, "res_succ",
                keyword.equals("") ? null :companySV.listCompanyUser(keyword));

    }

    @GetMapping("company-user/{companyCode}")
    public ResRS companyOrUserDetail(@PathVariable String companyCode) {

        return localization.resAPI(HttpStatus.OK, "res_succ", companySV.companyOrUserDetails(companyCode));

    }

    @PostMapping("company-user")
    public ResRS companyOrUserList(@RequestBody CompanyUserRQ companyUserRQ) {

        return localization.resAPI(HttpStatus.OK, "res_succ", companySV.companyOrUserList(companyUserRQ));

    }
}
