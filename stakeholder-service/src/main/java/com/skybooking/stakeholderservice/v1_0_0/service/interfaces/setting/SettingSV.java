package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import org.springframework.stereotype.Service;

@Service
public interface SettingSV {
    SettingRS getSetting();
    void sendLinkDownload(SendDownloadLinkRQ sendDownloadLinkRQ);
}
