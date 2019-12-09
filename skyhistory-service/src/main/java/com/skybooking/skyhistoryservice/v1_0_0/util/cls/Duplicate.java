package com.skybooking.skyhistoryservice.v1_0_0.util.cls;

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
    static public Map<String, Object> mailTemplateData(Object data, String template) {

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("data", data);
        mailTemplateData.put("templateName", template);

        return mailTemplateData;

    }

    static public Map<String, Object> pdfData(Object data, String template) {

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("data", data);
        pdfData.put("templateName", template);

        return pdfData ;

    }
}
