package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.LocaleRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocaleSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get module language translation by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return ModuleLanguageRS
     */
    ModuleLanguageRS findModuleLanguageByLocaleId(long id);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all locale information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    List<LocaleRS> findAllLocale();

}
