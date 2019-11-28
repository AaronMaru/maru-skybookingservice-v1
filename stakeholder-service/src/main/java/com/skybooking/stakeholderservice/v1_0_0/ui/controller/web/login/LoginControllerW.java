package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.login;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.0.0/auth")
public class LoginControllerW {

    @Autowired
    private LoginSV loginSV;


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
    public ResponseEntity login(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginRQ loginRQ) {
        UserDetailsTokenRS data = loginSV.login(httpHeaders, loginRQ);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
