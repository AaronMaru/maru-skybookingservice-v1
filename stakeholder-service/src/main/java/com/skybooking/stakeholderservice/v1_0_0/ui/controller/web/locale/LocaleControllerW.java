package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.locale;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale.LocaleSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.locale.LocaleRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/wv1.0.0/utils")
public class LocaleControllerW {

    @Autowired
    private LocaleSV localeSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get module translation by locale id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return Response
     */
    @GetMapping("/language-module/{lang}")
    public ResRS getLanguageModuleByLocaleId(@PathVariable String lang) {
        ModuleLanguageRS response = localeSV.findModuleLanguageByLocaleId();
        return localization.resAPI(HttpStatus.OK, "res_succ", response.getRoot());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * List all locale
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    @GetMapping(value = "/locale")
    public ResRS getAllLocale() {
        return localization.resAPI(HttpStatus.OK, "res_succ", localeSV.findAllLocale());
    }

}
