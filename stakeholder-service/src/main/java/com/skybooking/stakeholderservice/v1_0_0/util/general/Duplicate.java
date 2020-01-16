package com.skybooking.stakeholderservice.v1_0_0.util.general;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Duplicate {

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private MultiLanguageNQ multiLanguageNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Template mail data and SMS
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    public Map<String, Object> mailData(String name, int code, String template) {

        MultiLanguageTO multiLanguageTO = this.getMailData(template);

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", name);
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", template);
        mailTemplateData.put("script", multiLanguageTO);

        return mailTemplateData;
    }

    public MultiLanguageTO getMailData(String keyword) {

        Long localeID = headerBean.getLocalizationId(null);

        MailScriptLocaleTO exist = multiLanguageNQ.mailScriptLocale(localeID, keyword);

        localeID = (exist == null) ? 1 : localeID;

        MultiLanguageTO multiLanguageTO = multiLanguageNQ.mailMultiLanguage(localeID, keyword);

        return multiLanguageTO;

    }

}
