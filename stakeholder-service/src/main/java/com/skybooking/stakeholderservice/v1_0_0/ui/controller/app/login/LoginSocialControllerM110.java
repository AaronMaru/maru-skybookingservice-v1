package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.login;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSocialSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialV110RQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mv1.1.0/auth")
public class LoginSocialControllerM110 {

    @Autowired
    private LoginSocialSV loginSocialSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login Social
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginSocialRQ
     * @Return ResponseEntity
     */
    @PostMapping("/login/social")
    public ResRS loginSocial(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginSocialV110RQ loginSocialRQ) {
        UserDetailsTokenRS data = loginSocialSV.loginSocialV110(httpHeaders, loginSocialRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", data);
    }


}
