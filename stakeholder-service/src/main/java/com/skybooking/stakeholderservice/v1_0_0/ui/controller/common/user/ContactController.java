package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.user;

import com.skybooking.stakeholderservice.v1_0_0.service.usercontact.UserContactSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.UserContactRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1.0.0/contact")
@RequiredArgsConstructor
public class ContactController extends BaseController {

    private final UserContactSV userContactSV;

    @GetMapping()
    public ResponseEntity<BaseResponse> check()
    {
        return response(userContactSV.check());
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody UserContactRQ userContactRQ)
    {
        return response(userContactSV.create(userContactRQ));
    }

    @PostMapping("resend-code")
    public ResponseEntity<BaseResponse> resendCode()
    {
        return response(userContactSV.resendCode());
    }

    @PostMapping("verified-code")
    public ResponseEntity<BaseResponse> verifiedCode(@Valid @RequestBody VerifyRQ verifyRQ)
    {
        return response(userContactSV.verifiedCode(verifyRQ));
    }
}
