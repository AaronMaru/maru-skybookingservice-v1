package com.skybooking.staffservice.v1_0_0.util.localization;


import com.skybooking.staffservice.v1_0_0.io.enitity.locale.TranslationEntity;
import com.skybooking.staffservice.v1_0_0.io.repository.ResRS;
import com.skybooking.staffservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;

public class Localization {


    @Autowired
    private TranslationRP translationRP;

    @Autowired
    private HeaderBean headerBean;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Multi language response message
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param key
     * @Return String
     */
    public String multiLanguageRes(String key) {

        String locale = headerBean.getLocalization();
        TranslationEntity translationByLocale = translationRP.findByKey(key, locale);

        if (translationByLocale != null) {
            return translationByLocale.getValue();
        }

        TranslationEntity translationDefault = translationRP.findByKey(key, "en");
        if (translationDefault != null) {
            return translationDefault.getValue();
        }

        return key;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Res API message
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param message
     * @Param data
     * @Return String
     */
    public ResRS resAPI(String message, Object data) {
        ResRS resAPI = new ResRS(multiLanguageRes(message), data);
        return resAPI;
    }



}
