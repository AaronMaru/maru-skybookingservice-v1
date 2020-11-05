package com.skybooking.skypointservice.util;

import com.skybooking.skypointservice.v1_0_0.client.stakeholder.action.StakeholderAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class HeaderDataUtil {

    @Autowired
    private StakeholderAction stakeholderAction;

    public String languageCodeExist(HttpServletRequest httpServletRequest) {
        String reqLocale = httpServletRequest.getHeader("X-localization");

        var resRS = stakeholderAction.checkExistLang(reqLocale);

        boolean lang = Boolean.parseBoolean(resRS.getData().get("isExist").toString());

        if (!lang) {
            reqLocale = "en";
        }

        return reqLocale == null ? "en" : reqLocale;
    }
}
