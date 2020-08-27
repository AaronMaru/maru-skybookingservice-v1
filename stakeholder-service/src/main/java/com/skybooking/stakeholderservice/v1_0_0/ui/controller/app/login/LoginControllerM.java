package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.login;

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
@RequestMapping("/mv1.0.0/auth")
public class LoginControllerM {

    @Autowired
    private LoginSV loginSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login Social
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginRQ
     * @Return ResponseEntity
     */
    @PostMapping("/login")
    public ResRS login(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginRQ loginRQ) {
        UserDetailsTokenRS data = loginSV.login(httpHeaders, loginRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", data);
    }

}
