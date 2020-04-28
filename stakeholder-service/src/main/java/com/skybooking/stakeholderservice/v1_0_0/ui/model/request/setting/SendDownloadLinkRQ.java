package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting;

import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import com.skybooking.stakeholderservice.exception.anotation.Code;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

@Code(first = "username", second = "code", message = "Please provide country code")
public class SendDownloadLinkRQ {

    @NotNull(message = "Please provide a username")
    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
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
