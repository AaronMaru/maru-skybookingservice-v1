package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.locale;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale.LocaleSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1.0.0/utils")
public class LocaleController {

    @Autowired
    private LocaleSV localeSV;

    @Autowired
    private LocalizationBean localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get locale by Key
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    @GetMapping(value = "/locale/exist/{lang}")
    public ResRS getLocaleByKey(@PathVariable String lang) {
        return localization.resAPI(HttpStatus.OK, "res_succ", localeSV.getLocaleByKey(lang));
    }

}
