package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.setting;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting.SettingRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wv1.0.0/utils")
public class SettingControllerW {


    @Autowired
    private SettingSV settingSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting lists of setting
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/settings")
    public ResponseEntity getSetting() {
        SettingRS settingRS = settingSV.getSetting();
        return new ResponseEntity<>(settingRS, HttpStatus.OK);
    }

}
