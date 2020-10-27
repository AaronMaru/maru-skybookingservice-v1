package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.user;

import com.skybooking.stakeholderservice.v1_0_0.service.usercontact.UserContactSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.UserContactRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1.0.0/contact")
@RequiredArgsConstructor
public class ContactController extends BaseController {

    private final UserContactSV userContactSV;

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody UserContactRQ userContactRQ)
    {
        return response(userContactSV.create(userContactRQ));
    }
}
