package com.skybooking.stakeholderservice.v1_0_0.service.implement.setting;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting.FrontendConfigEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.setting.SettingRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettingIP implements SettingSV {

    @Autowired
    private SettingRP settingRP;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private EmailBean emailBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting lists of setting
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return SettingRS
     */
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

        for (FrontendConfigEntity setting : settings) {

            String type = setting.getType();

            switch (type) {
                case "twitter":
                    twitter.put(setting.getName(), setting.getTextValue());
                    settingRS.setTwitterDetails(twitter);
                    break;
                case "third_party":
                    thirdParty.put(setting.getName(), setting.getTextValue());
                    settingRS.setThirdPartyDetails(thirdParty);
                    break;
                case "facebook":
                    facebook.put(setting.getName(), setting.getTextValue());
                    settingRS.setFacebookDetails(facebook);
                    break;
                case "appstore":
                    appstore.put(setting.getName(), setting.getTextValue());
                    settingRS.setAppstoreDetails(appstore);
                    break;
                case "social_link":
                    socialLink.put(setting.getName(), setting.getTextValue());
                    settingRS.setSocialMediaDetails(socialLink);
                    break;
                case "seo":
                    seo.put(setting.getName(), setting.getTextValue());
                    settingRS.setSeoDetails(seo);
                    break;
                case "contact":
                    contact.put(setting.getName(), setting.getTextValue());
                    settingRS.setContactDetails(contact);
                    break;
            }
        }

        return settingRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send download link for mobile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param SendDownloadLinkRQ
     */
    public void sendLinkDownload(SendDownloadLinkRQ sendDownloadLinkRQ) {

        String receiver = NumberUtils.isNumber(sendDownloadLinkRQ.getUsername())
                ? sendDownloadLinkRQ.getCode() + sendDownloadLinkRQ.getUsername().replaceFirst("^0+(?!$)", "")
                : sendDownloadLinkRQ.getUsername();

        Map<String, Object> mailData = emailBean.mailData(receiver,null, 0, "download_skybooking_apps");
        emailBean.sendEmailSMS("send-download-link", mailData);

    }

}
