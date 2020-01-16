package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.locale;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale.LocaleSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wv1.0.0/utils")
public class LocaleControllerW {

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private LocaleSV localeSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get module translation by locale id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return Response
     */
    @GetMapping("/language-module/{lang}")
    public ResRS getLanguageModuleByLocaleId(@PathVariable String lang) {
        System.out.println(headerBean.getLocalizationId(lang));
        ModuleLanguageRS response = localeSV.findModuleLanguageByLocaleId(headerBean.getLocalizationId(lang));
        return localization.resAPI(HttpStatus.OK, "res_succ", response.getRoot());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list all locale
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    @GetMapping(value = "/locale")
    public ResRS getAllLocale() {
        return localization.resAPI(HttpStatus.OK, "res_succ", localeSV.findAllLocale());
    }

}
