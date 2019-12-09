package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import org.springframework.stereotype.Service;

@Service
public interface VerifySV {

    void verifyUser(VerifyRQ verifyRQ, int status);
    int resendVerify(SendVerifyRQ sendVerifyRQ, Integer status);

}
