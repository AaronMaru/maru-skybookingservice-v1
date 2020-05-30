package com.skybooking.stakeholderservice.v1_0_0.service.implement.setting;

import com.skybooking.stakeholderservice.constant.SettingNotificationConstant;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting.FrontendConfigEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.setting.SettingNotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.setting.SettingRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SettingNotificationRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingNotificationRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.stakeholderservice.constant.MailStatusConstant.DOWNLOAD_SKY_BOOKING_APPS;

@Service
public class SettingIP implements SettingSV {

    @Autowired
    private SettingRP settingRP;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private Environment environment;

    @Autowired
    private SettingNotificationNQ settingNotificationNQ;


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
        settingRS.setAwsUrl(environment.getProperty("spring.awsImageUrl.banner"));

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
                    if (setting.getTextValue() == null) {
                        facebook.put(setting.getName(), setting.getNumericValue().toString());
                    }
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
     * Get setting notifications by stakeholder and company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param stakeholder
     * @param company
     * @return List
     */
    @Override
    public List<SettingNotificationRS> getSettingNotifications(Integer stakeholder, Integer company) {

        var settings = new ArrayList<SettingNotificationRS>();

        var notifications = settingNotificationNQ.getNotificationSetting(stakeholder, company);

        var emailNotification = new SettingNotificationRS();
        emailNotification.setSetting(SettingNotificationConstant.emailNotification);

        var pushNotification = new SettingNotificationRS();
        pushNotification.setSetting(SettingNotificationConstant.pushNotification);

        if (notifications != null) {
            emailNotification.setEnabled(notifications.getEmailNotification());
            pushNotification.setEnabled(notifications.getPushNotification());
        }

        settings.add(emailNotification);
        settings.add(pushNotification);

        return settings;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * update notification setting
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param stakeholder
     * @param company
     * @param notificationRQ
     */
    @Override
    public void updateSettingNotification(Integer stakeholder, Integer company, SettingNotificationRQ notificationRQ) {

        if (notificationRQ.getSetting().equals(SettingNotificationConstant.emailNotification)) {
            settingNotificationNQ.updateEmailNotificationSetting(stakeholder, company, notificationRQ.getEnabled());
        }

        if (notificationRQ.getSetting().equals(SettingNotificationConstant.pushNotification)) {
            settingNotificationNQ.updatePushNotificationSetting(stakeholder, company, notificationRQ.getEnabled());
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send download link for mobile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param SendDownloadLinkRQ
     */
    public void sendLinkDownload(SendDownloadLinkRQ sendDownloadLinkRQ, String keyword) {

        String receiver = NumberUtils.isNumber(sendDownloadLinkRQ.getUsername())
            ? sendDownloadLinkRQ.getCode() + sendDownloadLinkRQ.getUsername().replaceFirst("^0+(?!$)", "")
            : sendDownloadLinkRQ.getUsername();

        Map<String, Object> mailData = emailBean.mailData(receiver, null, 0, DOWNLOAD_SKY_BOOKING_APPS);

        FrontendConfigEntity frontendConfigEntity = this.getDeepLink(keyword);
        mailData.put("deepLink", frontendConfigEntity == null ? "" : frontendConfigEntity.getTextValue());

        emailBean.sendEmailSMS("send-download-link", mailData);

    }

    @Override
    public FrontendConfigEntity getDeepLink(String keyword) {
        FrontendConfigEntity frontendConfigEntity = settingRP.getDeepLink(keyword);
        return frontendConfigEntity;
    }

}
