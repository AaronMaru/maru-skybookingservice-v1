package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.locale;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale.LocaleSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mv1.0.0/utils")
public class LocaleControllerM {

    @Autowired
    private LocaleSV localeSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get module translation by locale id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Response
     */
    @GetMapping("/language-module")
    public ResRS getLanguageModuleByLocaleId() {
        ModuleLanguageRS response = localeSV.findModuleLanguageByLocaleId();
        return localization.resAPI(HttpStatus.OK, "res_succ", response.getRoot());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list all locale
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    @GetMapping(value = "/locale")
    public ResRS getAllLocale() {
        return localization.resAPI(HttpStatus.OK, "res_succ", localeSV.findAllLocale());
    }
}
