package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.company;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company.CompanySV;
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

        return localization.resAPI(HttpStatus.OK, "res_succ", companySV.listCompany(keyword));

    }

    @GetMapping("company/{companyCode}")
    public ResRS companyDetail(@PathVariable String companyCode) {

        return localization.resAPI(HttpStatus.OK, "res_succ", companySV.companyDetail(companyCode));

    }
}
