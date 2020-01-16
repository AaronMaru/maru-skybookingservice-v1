package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.springframework.stereotype.Service;

@Service
public interface VerifySV {

    UserDetailsTokenRS verifyUser(VerifyRQ verifyRQ, int status);
    int resendVerify(SendVerifyRQ sendVerifyRQ, Integer status);

}
