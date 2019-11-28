package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.user;

import com.skybooking.stakeholderservice.v1_0_0.service.implement.user.VerifyServiceIP;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.0.0/auth")
public class VerifyControllerW {

    @Autowired
    private VerifyServiceIP verifyServiceImp;

    @Autowired
    private Localization localization;

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
    public ResponseEntity verifyUser(@Valid @RequestBody VerifyRQ verifyRequest) {
        verifyServiceImp.verifyUser(verifyRequest, Integer.parseInt(environment.getProperty("spring.verifyStatus.verify")));
        return new ResponseEntity<>(localization.resAPI("vf_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend verify
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/resend-verify")
    public ResponseEntity resendVerify(@RequestBody SendVerifyRQ sendVerifyRequest) {
        verifyServiceImp.resendVerify(sendVerifyRequest, Integer.parseInt(environment.getProperty("spring.verifyStatus.verify")));
        return new ResponseEntity<>(localization.resAPI("succ_sms", ""), HttpStatus.TEMPORARY_REDIRECT);
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
    public ResponseEntity verifyUserToActive(@Valid @RequestBody VerifyRQ verifyRequest) {
        verifyServiceImp.verifyUser(verifyRequest, Integer.parseInt(environment.getProperty("spring.verifyStatus.activeUser")));
        return new ResponseEntity<>(localization.resAPI("acc_act_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send verify to active account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/send-verify-active")
    public ResponseEntity sendVerifyToActive(@RequestBody SendVerifyRQ sendVerifyRequest) {
        verifyServiceImp.resendVerify(sendVerifyRequest, Integer.parseInt(environment.getProperty("spring.verifyStatus.activeUser")));
        return new ResponseEntity<>(localization.resAPI("succ_sms", ""), HttpStatus.TEMPORARY_REDIRECT);
    }


}
