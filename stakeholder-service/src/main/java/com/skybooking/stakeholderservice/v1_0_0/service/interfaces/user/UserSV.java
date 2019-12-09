package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Service
public interface UserSV {

    UserDetailsRS getUser();
    void changePassword(ChangePasswordRQ passwordRQ);
    UserDetailsRS updateProfile(ProfileRQ profileRQ, MultipartFile multipartFile) throws ParseException;
    void deactiveAccount(DeactiveAccountRQ accountRQ);
    void resetPassword(ResetPasswordRQ passwordRQ);
    int sendCodeResetPassword(SendVerifyRQ sendVerifyRQ);
    void updateContact(UpdateContactRQ contactRQ);
    int sendCodeUpdateContact(UpdateContactRQ contactRQ);
    void applySkyowner(CompanyRQ companyRQ);

}
