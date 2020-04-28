package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.stakeholderservice.exception.anotation.FieldMatch;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
public class ChangePasswordRQ {

    @IsNotEmpty
    private String oldPassword;

    @IsNotEmpty
    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "Invalid password")
    private String newPassword;

    @IsNotEmpty
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
