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
    void changePassword(ChangePasswordRQ pwdRequest);
    UserDetailsRS updateProfile(ProfileRQ profileRequest, MultipartFile multipartFile) throws ParseException;
    void deactiveAccount(DeactiveAccountRQ deactiveAccount);
    void resetPassword(ResetPasswordRQ pwdRequest);
    int sendCodeResetPassword(SendVerifyRQ sendVerifyRequest);
    void updateContact(UpdateContactRQ updateContactRq);
    int sendCodeUpdateContact(UpdateContactRQ updateContactRq);
    void applySkyowner(CompanyRQ companyRQ);

}
