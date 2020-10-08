package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.locale.LocaleRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.LocaleExistRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.LocaleRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocaleSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get module language translation by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ModuleLanguageRS
     */
    ModuleLanguageRS findModuleLanguageByLocaleId();


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all locale information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    List<LocaleRS> findAllLocale();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get locale by key
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    LocaleExistRS getLocaleByKey(String lang);
}
