package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.login;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSocialSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mv1.0.0/auth")
public class LoginSocialControllerM {

    @Autowired
    private LoginSocialSV loginSocialSV;

    @Autowired
    private Localization localization;


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
    public ResRS loginSocial(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginSocialRQ loginSocialRQ) {
        UserDetailsTokenRS data = loginSocialSV.loginSocial(httpHeaders, loginSocialRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", data);
    }


//    /**
//     * -----------------------------------------------------------------------------------------------------------------
//     * Register and login skyowner Social
//     * -----------------------------------------------------------------------------------------------------------------
//     *
//     * @Param httpHeaders
//     * @Param loginSocialRQ
//     * @Return ResponseEntity
//     */
//    @PostMapping(value = "/register/skyowner/social", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Object skyownerSocial(@RequestHeader HttpHeaders httpHeaders, @Valid @ModelAttribute LoginSocialSkyownerRQ loginSocialRQ, Errors errors) {
//
//        if (errors.hasErrors()) {
//            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
//        }
//
//        UserDetailsTokenRS data = loginSocialSV.loginSocialSkyowner(httpHeaders, loginSocialRQ);
//        return localization.resAPI(HttpStatus.OK, "res_succ", data);
//
//    }


}
