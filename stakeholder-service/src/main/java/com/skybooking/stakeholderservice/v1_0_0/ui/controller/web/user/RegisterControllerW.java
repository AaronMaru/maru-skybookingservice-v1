package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.RegisterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/wv1.0.0/auth")
public class RegisterControllerW {

    @Autowired
    private RegisterSV registerSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Register skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param userRequest
     * @Return ResponseEntity
     */
    @PostMapping("/register")
    public ResRS addSkyuser(@Valid @RequestBody SkyUserRegisterRQ skyuserRQ) {
        registerSV.skyuser(skyuserRQ, "web");
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"reg_succ", "");
    }


}
