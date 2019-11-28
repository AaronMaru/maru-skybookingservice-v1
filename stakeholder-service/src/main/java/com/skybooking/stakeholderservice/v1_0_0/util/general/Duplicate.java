package com.skybooking.stakeholderservice.v1_0_0.util.general;

import java.util.HashMap;
import java.util.Map;

public class Duplicate {

    static public Map<String, String>  duplicateContent(String fullName, String code) {
        Map<String, String> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", fullName);
        mailTemplateData.put("code", code);
        mailTemplateData.put("templateName", "verify-code");

        return  mailTemplateData;
    }
}
