package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user;

import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user.PaymentUserInfoTO;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRefreshRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Service
public interface UserSV {

    UserDetailsRS getUser();
    void changePassword(ChangePasswordRQ passwordRQ);
    UserDetailsRS updateProfile(ProfileRQ profileRQ, MultipartFile multipartFile) throws ParseException;
    void deactiveAccount(DeactiveAccountRQ accountRQ);
    UserDetailsTokenRS resetPassword(ResetPasswordRQ passwordRQ, HttpHeaders httpHeaders);
    void updateContact(UpdateContactRQ contactRQ);
    int sendCodeUpdateContact(UpdateContactRQ contactRQ);
    CompanyRS applySkyowner(CompanyRQ companyRQ);
    List<InvitationRS> getInvitations();
    void options(OptionStaffRQ optionStaffRQ);
    UserDetailsTokenRS resetPasswordMobile(ResetPasswordMobileRQ resetPasswordMobileRQ);

    Boolean logout(HttpHeaders httpHeaders);

    void changeLanguage(ChangeLanguageRQ changeLanguageRQ);

    TokenRS refreshToken(HttpHeaders httpHeaders, LoginRefreshRQ loginRQ);

    UserReferenceRS userReference(Long skyuserId);

    PaymentUserInfoTO paymentUserReference(Long skyUserId);
}
