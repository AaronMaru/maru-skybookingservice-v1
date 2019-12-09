package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.setting;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mv1.0.0/utils")
public class SettingControllerM {


    @Autowired
    private SettingSV settingSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting lists of setting
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/settings")
    public Object getSetting() {
        SettingRS settingRS = settingSV.getSetting();
        return localization.resAPI(HttpStatus.OK, "res_succ", settingRS);
    }


}
