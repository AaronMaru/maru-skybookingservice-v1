package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRefreshRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1.0.0")
public class UserController {

    @Autowired
    private UserSV userSV;

    @Autowired
    private LocalizationBean localization;

    @PostMapping("/logout")
    public ResponseEntity<ResRS> login(@RequestHeader HttpHeaders httpHeaders) {

        if (userSV.logout(httpHeaders)) {
            return new ResponseEntity<>(new ResRS(HttpStatus.OK.value()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResRS(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Refresh token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginRQ
     * @Return ResRS
     */
    @PostMapping("/auth/login-refresh")
    public ResRS refreshToken(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginRefreshRQ loginRQ) {
        var data = userSV.refreshToken(httpHeaders, loginRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", data);
    }


}
