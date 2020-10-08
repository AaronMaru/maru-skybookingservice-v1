package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.login;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.1.0/auth")
public class LoginControllerW110 {

    @Autowired
    private LoginSV loginSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginRQ
     * @Return ResRS
     */
    @PostMapping("/login")
    public ResRS login(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginRQ loginRQ) {

        UserDetailsTokenRS data = loginSV.loginV110(httpHeaders, loginRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", data);
    }

}
