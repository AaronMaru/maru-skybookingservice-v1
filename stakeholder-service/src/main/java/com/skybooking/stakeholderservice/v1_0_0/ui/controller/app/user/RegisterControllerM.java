package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.RegisterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
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
@RequestMapping("/mv1.0.0/auth")
public class RegisterControllerM {

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
    public ResponseEntity addSkyuser(@Valid @RequestBody SkyUserRegisterRQ userRequest) {
        registerSV.skyuser(userRequest);
        return new ResponseEntity<>(localization.resAPI("reg_succ", ""), HttpStatus.TEMPORARY_REDIRECT);
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
    public Object addSkyowner(@Valid @ModelAttribute("userRequest") SkyownerRegisterRQ userRequest, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
        }

        registerSV.skyowner(userRequest);
        return new ResponseEntity<>(localization.resAPI("reg_succ", ""), HttpStatus.TEMPORARY_REDIRECT);

    }

}
