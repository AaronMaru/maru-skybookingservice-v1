package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.user;

import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/wv1.0.0")
public class UserControllerW {


    @Autowired
    private UserSV userSV;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private Localization localization;

    @Autowired
    private GeneralBean generalBean;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/user")
    public ResponseEntity getUser() {
        UserDetailsRS userDetailsRS = userSV.getUser();
        return new ResponseEntity<>(userDetailsRS, HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update user profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param profileRequest
     * @Return ResponseEntity
     */
    @PatchMapping(value = "/update-profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDetailsRS> updateProfile(@Valid ProfileRQ profileRequest, @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws ParseException {
        UserDetailsRS userDetailsRS = userSV.updateProfile(profileRequest, multipartFile);
        return new ResponseEntity<>(userDetailsRS, HttpStatus.OK);
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
    public ResponseEntity changPassword(@Valid @RequestBody ChangePasswordRQ pwdRequest) {
        userSV.changePassword(pwdRequest);
        return new ResponseEntity<>(localization.resAPI("ch_pwd_succ", ""), HttpStatus.OK);
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
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRQ pwdResetRequest) {
        userSV.resetPassword(pwdResetRequest);
        return new ResponseEntity<>(localization.resAPI("reset_pwd_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     * @Return ResponseEntity
     */
    @PostMapping("/auth/send-forgot-password")
    public ResponseEntity sendCodeResetPassword(@RequestBody SendVerifyRQ sendVerifyRequest) {
        userSV.sendCodeResetPassword(sendVerifyRequest);
        return new ResponseEntity<>(localization.resAPI("succ_sms", ""), HttpStatus.TEMPORARY_REDIRECT);
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
    public ResponseEntity updateContact(@Valid @RequestBody UpdateContactRQ updateContactRequest) {
        userSV.updateContact(updateContactRequest);
        return new ResponseEntity<>(localization.resAPI("update_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to verify update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     * @Return ResponseEntity
     */
    @PostMapping("/send-update-contact")
    public ResponseEntity sendCodeUpdateContact(@RequestBody UpdateContactRQ updateContactRequest) {
        userSV.sendCodeUpdateContact(updateContactRequest);
        return new ResponseEntity<>(localization.resAPI("succ_sms", ""), HttpStatus.TEMPORARY_REDIRECT);
    }


//    /**
//     * -----------------------------------------------------------------------------------------------------------------
//     * Logout
//     * -----------------------------------------------------------------------------------------------------------------
//     *
//     * @No_return
//     */
//    @DeleteMapping("/logout")
//    public ResponseEntity revokeToken(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//
//        String token = request.getHeader("authorization");
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[1]);
//        tokenStore.removeAccessToken(oAuth2AccessToken);
//
//        return new ResponseEntity<>(localization.resAPI("Logout sucessful", ""), HttpStatus.OK);
//    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive account skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param deactiveAccount
     * @Return ResponseEntity
     */
    @PatchMapping("/deactive-account")
    public ResponseEntity deactiveAccount(@Valid @RequestBody DeactiveAccountRQ deactiveAccount) {
        userSV.deactiveAccount(deactiveAccount);
        return new ResponseEntity<>(localization.resAPI("succ_deact", ""), HttpStatus.OK);
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

        return new ResponseEntity<>(localization.resAPI("apl_skyowner_succ", ""), HttpStatus.TEMPORARY_REDIRECT);

    }


}
