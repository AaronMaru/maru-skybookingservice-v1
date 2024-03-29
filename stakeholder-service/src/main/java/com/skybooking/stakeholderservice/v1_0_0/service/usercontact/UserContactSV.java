package com.skybooking.stakeholderservice.v1_0_0.service.usercontact;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.UserContactRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserContactSV {

    BaseResponse check();
    BaseResponse create(UserContactRQ userContactRQ);
    BaseResponse resendCode();
    BaseResponse verifiedCode(VerifyRQ verifyRQ);

}
