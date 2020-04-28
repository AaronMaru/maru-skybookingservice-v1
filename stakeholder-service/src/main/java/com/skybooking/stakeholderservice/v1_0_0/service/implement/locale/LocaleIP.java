package com.skybooking.stakeholderservice.v1_0_0.service.implement.locale;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.TranslationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.locale.LocaleSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.locale.LocaleTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.LocaleRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.ModuleLanguageRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.StringUtil;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class LocaleIP implements LocaleSV {

    @Autowired
    TranslationRP translationRP;

    @Autowired
    LocaleRP localeRP;

    @Autowired
    private HeaderBean headerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get module language translate by locale id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return response
     */
    @Override
    public ModuleLanguageRS findModuleLanguageByLocaleId() {

        List<TranslationEntity> translationEntities = translationRP.findAllByLocaleId(headerBean.getLocalizationId());

        if (translationEntities.size() <= 0)
            translationEntities = translationRP.findAllByLocaleId(localeRP.findLocaleByLocale("en").getId());

        ModuleLanguageRS response = new ModuleLanguageRS();

        for (TranslationEntity translationEntity : translationEntities) {

            String module = StringUtil.toCamel(translationEntity.getModule());
            String key = translationEntity.getKey();
            String value = translationEntity.getValue();

            Map<String, String> keyValue = new TreeMap<>();
            keyValue.put("key", key);
            keyValue.put("value", value);

            if (!response.getRoot().containsKey(module))
                response.getRoot().put(module, new ArrayList<>());

            response.getRoot().get(module).add(keyValue);
        }

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list all locale information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    @Override
    public List<LocaleRS> findAllLocale() {
        return LocaleTF.getResponseList(localeRP.findAll());
    }


}
