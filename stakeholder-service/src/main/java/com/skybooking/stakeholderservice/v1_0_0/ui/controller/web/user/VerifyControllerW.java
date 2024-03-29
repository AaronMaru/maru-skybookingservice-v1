package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.0.0/auth")
public class VerifyControllerW {

    @Autowired
    private VerifySV verifySV;

    @Autowired
    private LocalizationBean localization;

    @Autowired
    private Environment environment;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify user
     * -----------------------------------------------------------------------------------------------------------------
     * @Param verifyRequest
     * @Return ResponseEntity
     */
    @PatchMapping("/verify")
    public ResRS verifyUser(@Valid @RequestBody VerifyRQ verifyRQ) {
        UserDetailsTokenRS userDetailsTokenRS = verifySV.verifyUser(verifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verify")));
        return localization.resAPI(HttpStatus.OK,"vf_succ", userDetailsTokenRS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend login
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/resend-verify")
    public ResRS resendVerify(@RequestBody SendVerifyRQ sendVerifyRQ) {
        verifySV.resendVerify(sendVerifyRQ, Integer.parseInt(environment.getProperty("spring.verifyStatus.verify")));
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     * @Return ResRS
     */
    @PostMapping("/send-forgot-password")
    public ResRS sendCodeResetPassword(@RequestBody SendVerifyRQ sendVerifyRQ) {
        verifySV.sendCodeResetPassword(sendVerifyRQ);
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


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


}
