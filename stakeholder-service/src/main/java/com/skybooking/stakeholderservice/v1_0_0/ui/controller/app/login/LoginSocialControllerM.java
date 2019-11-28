package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.login;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSocialSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialSkyownerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mv1.0.0/auth")
public class LoginSocialControllerM {

    @Autowired
    private LoginSocialSV loginSocialSV;

    @Autowired
    private GeneralBean generalBean;


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
    public ResponseEntity loginSocial(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginSocialRQ loginSocialRQ) {
        UserDetailsTokenRS data = loginSocialSV.loginSocial(httpHeaders, loginSocialRQ);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Register and login skyowner Social
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginSocialRQ
     * @Return ResponseEntity
     */
    @PostMapping(value = "/register/skyowner/social", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object skyownerSocial(@RequestHeader HttpHeaders httpHeaders, @Valid @ModelAttribute LoginSocialSkyownerRQ loginSocialRQ, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
        }

        UserDetailsTokenRS data = loginSocialSV.loginSocialSkyowner(httpHeaders, loginSocialRQ);
        return new ResponseEntity<>(data, HttpStatus.OK);

    }


}
