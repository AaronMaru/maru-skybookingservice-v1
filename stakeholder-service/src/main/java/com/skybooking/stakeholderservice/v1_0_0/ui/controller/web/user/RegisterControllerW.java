package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.RegisterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.0.0/auth")
public class RegisterControllerW {

    @Autowired
    private RegisterSV registerSV;

    @Autowired
    private Localization localization;

    @Autowired
    private GeneralBean generalBean;


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
        registerSV.skyuser(skyuserRQ);
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"reg_succ", "");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Register skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param userRequest
     * @Return ResponseEntity
     */
    @PostMapping(value = "/register/skyowner", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object addSkyowner(@Valid @ModelAttribute("userRequest") SkyownerRegisterRQ skyuserRQ, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
        }

        registerSV.skyowner(skyuserRQ);
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"reg_succ", "");

    }

}
