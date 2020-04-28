package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.user;

import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/mv1.0.0")
public class UserControllerM {


    @Autowired
    private UserSV userSV;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private LocalizationBean localization;

    @Autowired
    private GeneralBean generalBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/profile")
    public ResRS getUser() {
        UserDetailsRS userDetailsRS = userSV.getUser();
        return localization.resAPI(HttpStatus.OK,"res_succ",userDetailsRS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update user profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param profileRequest
     * @Return ResponseEntity
     */
    @PatchMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object updateProfile(@Valid @ModelAttribute("profileRQ") ProfileRQ profileRQ, Errors errors, @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws ParseException {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
        }

        UserDetailsRS userDetailsRS = userSV.updateProfile(profileRQ, multipartFile);
        return localization.resAPI(HttpStatus.OK,"res_succ",userDetailsRS);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Change user password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     * @Return ResponseEntity
     */
    @PatchMapping("/change-password")
    public ResRS changPassword(@Valid @RequestBody ChangePasswordRQ passwordRQ) {
        userSV.changePassword(passwordRQ);
        return localization.resAPI(HttpStatus.OK, "ch_pwd_succ", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     * @Return ResponseEntity
     */
    @PatchMapping("/auth/forgot-password")
    public ResRS resetPassword(@Valid @RequestBody ResetPasswordMobileRQ resetPasswordMobileRQ) {
        UserDetailsTokenRS userDetail = userSV.resetPasswordMobile(resetPasswordMobileRQ);
        return localization.resAPI(HttpStatus.OK, "reset_pwd_succ", userDetail);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     * @Return ResponseEntity
     */
    @PatchMapping("/update-contact")
    public ResRS updateContact(@Valid @RequestBody UpdateContactRQ contactRQ) {
        userSV.updateContact(contactRQ);
        return localization.resAPI(HttpStatus.OK, "update_succ", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to login update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     * @Return ResponseEntity
     */
    @PostMapping("/send-update-contact")
    public ResRS sendCodeUpdateContact(@Valid @RequestBody UpdateContactRQ contactRQ) {
        userSV.sendCodeUpdateContact(contactRQ);
        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"vf_rdy_sent", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive account skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param deactiveAccount
     * @Return ResponseEntity
     */
    @PatchMapping("/deactive-account")
    public ResRS deactiveAccount(@Valid @RequestBody DeactiveAccountRQ accountRQ) {
        userSV.deactiveAccount(accountRQ);
        return localization.resAPI(HttpStatus.OK, "succ_deact", null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Apply skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRequest
     * @Return ResponseEntity
     */
    @PostMapping(value = "/apply-skyowner", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object applySkyowner(@Valid @ModelAttribute("companyRQ") CompanyRQ companyRQ, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(generalBean.errors(errors), HttpStatus.BAD_REQUEST);
        }

        userSV.applySkyowner(companyRQ);

        return localization.resAPI(HttpStatus.TEMPORARY_REDIRECT,"apl_skyowner_succ", null);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Listing invitation of user from skyowner
     * -----------------------------------------------------------------------------------------------------------------
     */
    @GetMapping("/invitations")
    public ResRS invitations() {
        return localization.resAPI(HttpStatus.OK,"res_succ", userSV.getInvitations());
    }

}
