package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.setting;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/wv1.0.0/utils")
public class SettingControllerW {


    @Autowired
    private SettingSV settingSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting lists of setting
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/settings")
    public ResRS getSetting() {
        SettingRS settingRS = settingSV.getSetting();
        return localization.resAPI(HttpStatus.OK, "res_succ", settingRS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send download link for mobile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping("/send-download-link")
    public ResRS sendLinkDownload(@RequestBody @Valid SendDownloadLinkRQ sendDownloadLinkRQ) {
        settingSV.sendLinkDownload(sendDownloadLinkRQ);
        return localization.resAPI(HttpStatus.OK, "sent_succ", "");
    }

}
