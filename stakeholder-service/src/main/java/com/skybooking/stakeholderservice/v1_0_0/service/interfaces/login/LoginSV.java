package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public interface LoginSV {
    UserDetailsTokenRS login(HttpHeaders httpHeaders, LoginRQ loginRQ);
    UserDetailsTokenRS loginV110(HttpHeaders httpHeaders, LoginRQ loginRQ);
}
