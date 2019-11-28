package com.skybooking.stakeholderservice.v1_0_0.service.implement.setting;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting.FrontendConfigEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.setting.SettingRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SettingIP implements SettingSV {

    @Autowired
    private SettingRP settingRP;

    @Override
    public SettingRS getSetting() {

        List<FrontendConfigEntity> settings = settingRP.findAllByType();
        SettingRS settingRS = new SettingRS();

        var twitter = new HashMap<String, String>();
        var thirdParty = new HashMap<String, String>();
        var facebook = new HashMap<String, String>();
        var appstore = new HashMap<String, String>();
        var socialLink = new HashMap<String, String>();
        var seo = new HashMap<String, String>();
        var contact = new HashMap<String, String>();

        for (FrontendConfigEntity seting: settings) {

            String type = seting.getType();

            switch (type) {
                case "twitter":
                    twitter.put(seting.getName(), seting.getTextValue());
                    settingRS.setTwitterDetails(twitter);
                    break;
                case "third_party":
                    thirdParty.put(seting.getName(), seting.getTextValue());
                    settingRS.setThirdPartyDetails(thirdParty);
                    break;
                case "facebook":
                    facebook.put(seting.getName(), seting.getTextValue());
                    settingRS.setFacebookDetails(facebook);
                    break;
                case "appstore":
                    appstore.put(seting.getName(), seting.getTextValue());
                    settingRS.setAppstoreDetails(appstore);
                    break;
                case "social_link":
                    socialLink.put(seting.getName(), seting.getTextValue());
                    settingRS.setSocialMediaDetails(socialLink);
                    break;
                case "seo":
                    seo.put(seting.getName(), seting.getTextValue());
                    settingRS.setSeoDetails(seo);
                    break;
                case "contact":
                    contact.put(seting.getName(), seting.getTextValue());
                    settingRS.setContactDetails(contact);
                    break;
            }
        }

        return settingRS;

    }

}
