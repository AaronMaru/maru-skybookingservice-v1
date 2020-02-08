package com.skybooking.skyhistoryservice.v1_0_0.util.general;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.TranslationEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateBean {

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private MultiLanguageNQ multiLanguageNQ;

    @Autowired
    private TranslationRP translationRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Template mail data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    public Map<String, Object> mailData(String receiver, String name, int code, String script, String template, String label) {

        MultiLanguageTO multiLanguageTO = this.getMailData(script);

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("receiver", receiver);
        mailTemplateData.put("fullName", name);
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", template);
        mailTemplateData.put("script", multiLanguageTO);

        List<TranslationEntity> labels = this.translationLabel(label);

        labels.forEach(item -> mailTemplateData.put(item.getKey(), item.getValue()));

        return mailTemplateData;
    }


    public MultiLanguageTO getMailData(String keyword) {

        Long localeID = headerBean.getLocalizationId();

        MailScriptLocaleTO exist = multiLanguageNQ.mailScriptLocale(localeID, keyword);

        localeID = (exist == null) ? 1 : localeID;

        MultiLanguageTO multiLanguageTO = multiLanguageNQ.mailMultiLanguage(localeID, keyword);

        return multiLanguageTO;

    }

    private List<TranslationEntity> translationLabel(String label) {

        String locale = headerBean.getLocalization();

        LocaleEntity localeEntity = localeRP.findLocaleByLocale(locale);

        List<TranslationEntity> translationEntities = translationRP.findByModule(localeEntity.getId(), label);

        return translationEntities;
    }

    public Map<String, Object> dataPdfTemplate(String pdfTemplate, String label) {

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("templateName", pdfTemplate);


        List<TranslationEntity> labels = this.translationLabel(label);

        labels.forEach(item -> pdfData.put(item.getKey(), item.getValue()));

        return pdfData;
    }
}
