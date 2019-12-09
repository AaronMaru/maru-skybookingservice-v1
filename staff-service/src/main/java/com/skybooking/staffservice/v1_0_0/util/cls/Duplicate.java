package com.skybooking.staffservice.v1_0_0.util.cls;

import java.util.HashMap;
import java.util.Map;

public class Duplicate {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Template mail data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    static public Map<String, String> mailTemplateData(String fullName, int code, String template) {

        Map<String, String> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", fullName);
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", template);

        return mailTemplateData;

    }
}
