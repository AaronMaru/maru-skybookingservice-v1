package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.user;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0.0")
public class UserController {

    @Autowired
    private UserSV userSV;

    @PostMapping("/logout")
    public ResponseEntity<ResRS> login(@RequestHeader HttpHeaders httpHeaders) {

        if (userSV.logout(httpHeaders)) {
            return new ResponseEntity<>(new ResRS(HttpStatus.OK.value()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResRS(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);

    }
}
