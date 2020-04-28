package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting.FrontendConfigEntity;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SettingNotificationRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingNotificationRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettingSV {
    SettingRS getSetting();
    List<SettingNotificationRS> getSettingNotifications(Integer stakeholder, Integer company);
    void updateSettingNotification(Integer stakeholder, Integer company, SettingNotificationRQ notificationRQ);
    void sendLinkDownload(SendDownloadLinkRQ sendDownloadLinkRQ, String keyword);

    FrontendConfigEntity getDeepLink(String keyword);
}
