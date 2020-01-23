package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting;

import com.skybooking.stakeholderservice.exception.anotation.Code;
import com.skybooking.stakeholderservice.exception.anotation.Email;
import com.skybooking.stakeholderservice.exception.anotation.Phone;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Code(first = "username", second = "code", message = "Please provide country code")
public class SendDownloadLinkRQ {


    @Phone
    @Email
    @NotNull(message = "Please provide a username")
    @NotEmpty(message = "Please provide a username")
    private String username;

    private String code;

    public SendDownloadLinkRQ() {
    }

    public SendDownloadLinkRQ(@NotNull(message = "Please provide a username") @NotEmpty(message = "Please provide a username") String username, String code) {
        this.username = username;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
