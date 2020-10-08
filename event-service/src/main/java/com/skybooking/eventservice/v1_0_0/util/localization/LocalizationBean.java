package com.skybooking.eventservice.v1_0_0.util.localization;

import com.skybooking.eventservice.v1_0_0.io.entity.locale.TranslationEntity;
import com.skybooking.eventservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.eventservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class LocalizationBean {


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
     * @Param status
     * @Param message
     * @Param data
     * @Return String
     */
    public ResRS resAPI(HttpStatus status, String message, Object data) {
        ResRS resAPI = new ResRS(status.value(), multiLanguageRes(message), data);
        return resAPI;
    }


}
