package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyMRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mv1.0.0/auth")
public class VerifyControllerM {


    @Autowired
    private VerifySV verifySV;

    @Autowired
    GeneralBean generalBean;

    @Autowired
    private LocalizationBean localization;

    @Autowired
    private Environment environment;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify user to active account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     * @Return ResponseEntity
     */
    @PatchMapping("/verify-active")
    public ResRS verifyUserToActive(@Valid @RequestBody VerifyRQ verifyRQ) {
        verifySV.verifyUser(verifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.activeUser")));
        return localization.resAPI(HttpStatus.OK,"acc_act_succ", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send login to active account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/send-verify-active")
    public ResRS sendVerifyToActive(@RequestBody SendVerifyRQ sendVerifyRQ) {
        verifySV.resendVerify(sendVerifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.activeUser")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send verify
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRQ
     */
    @PostMapping("/send-verify")
    public ResRS sendVerify(@Valid @RequestBody SendVerifyRQ sendVerifyRQ) {
        verifySV.sendVerify(sendVerifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserApp")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param VerifyMRQ
     */
    @PostMapping("/verify-app")
    public ResRS verify(@RequestBody VerifyMRQ verifyMRQ) {
        verifySV.verify(verifyMRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserApp")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_succ", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/send-forgot-password")
    public ResRS sendCodeResetPassword(@RequestBody SendVerifyRQ sendVerifyRQ) {
        verifySV.sendVerify(sendVerifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserAppReset")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param VerifyMRQ
     */
    @PostMapping("/verify-reset-password")
    public ResRS verifyResetPassword(@RequestBody VerifyMRQ verifyMRQ) {
        verifySV.verify(verifyMRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserAppReset")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_succ", null);
    }



}
