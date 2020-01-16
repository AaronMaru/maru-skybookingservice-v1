package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public interface LoginSocialSV {
    UserDetailsTokenRS loginSocial(HttpHeaders httpHeaders, LoginSocialRQ loginSocialRQ);
//    UserDetailsTokenRS loginSocialSkyowner(HttpHeaders httpHeaders, LoginSocialSkyownerRQ loginSocialRQ);
}
