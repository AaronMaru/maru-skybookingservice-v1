package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;


@Service
public class UserIP implements UserSV {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private UserBean userBean;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private Environment environment;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private VerifyUserRP verifyRepository;

    @Autowired
    private SkyownerBean skyownerBean;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get User Details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return userDetailDao
     */
    public UserDetailsRS getUser() {

        UserEntity user = userBean.getUserPrincipal();

        UserDetailsRS userDetailRS = new UserDetailsRS();
        BeanUtils.copyProperties(userBean.userFields(user, null), userDetailRS);

        return userDetailRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get User Details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return userDetailDao
     */
    public UserDetailsRS updateProfile(ProfileRQ profileRQ, MultipartFile multipartFile) throws ParseException {

        UserEntity user = userBean.getUserPrincipal();

        StakeHolderUserEntity stkHolder = user.getStakeHolderUser();

        if (profileRQ.getFirstName() != null) {
            stkHolder.setFirstName(profileRQ.getFirstName());
        }
        if (profileRQ.getLastName() != null) {
            stkHolder.setLastName(profileRQ.getLastName());
        }
        if (profileRQ.getNationality() != null) {
            stkHolder.setNationality(profileRQ.getNationality());
        }

        if (profileRQ.getAddress() != null) {
            apiBean.updateContact(profileRQ.getAddress(), null, stkHolder, "a", "address");
        }

        if (profileRQ.getGender() != null) {
            stkHolder.setGender(profileRQ.getGender());
        }

        if (profileRQ.getDateOfBirth() != null) {
            stkHolder.setDateOfBirth(profileRQ.getDateOfBirth());
        }

        user.setStakeHolderUser(stkHolder);
        if (multipartFile != null) {
            String fileName = userBean.uploadFileForm(multipartFile, "/profile", "/profile/_thumbnail");
            stkHolder.setPhoto(fileName);
        }

        userRepository.save(user);

        UserDetailsRS userDetailsRS = new UserDetailsRS();
        BeanUtils.copyProperties(userBean.userFields(user, null), userDetailsRS);

        logger.activities(ActivityLoggingBean.Action.UPDATE_STAKE_USER, user);

        return userDetailsRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * User change password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     */
    public void changePassword(ChangePasswordRQ passwordRQ) {

        UserEntity user = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(passwordRQ.getOldPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }
        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));

        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.CHANGE_PASSWORD, user);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     */
    public void resetPassword(ResetPasswordRQ passwordRQ) {

        SmsMessage sms = new SmsMessage();

        String username = passwordRQ.getUsername();

        if (NumberUtils.isNumber(passwordRQ.getUsername())) {
            username = passwordRQ.getCode() + passwordRQ.getUsername();
        }

        UserEntity user = userRepository.findByEmailOrPhone(passwordRQ.getUsername(), passwordRQ.getCode());
        StakeHolderUserEntity skyuser = user.getStakeHolderUser();
        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(passwordRQ.getToken(), Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        generalBean.expiredInvalidToken(verifyUserEntity, passwordRQ.getToken());

        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));
        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.RESET_PASSWORD, user);

        apiBean.sendEmailSMS(username, sms.sendSMS("success-reset-password", 0), Duplicate.mailTemplateData(fullName, 0,"complete-reset-password"));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     */
    public int sendCodeResetPassword(SendVerifyRQ verifyRQ) {

        SmsMessage sms = new SmsMessage();
        UserEntity user = userRepository.findByEmailOrPhone(verifyRQ.getUsername(), verifyRQ.getCode());

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }
        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String username = NumberUtils.isNumber(verifyRQ.getUsername()) ? verifyRQ.getCode() + verifyRQ.getUsername().replaceFirst("^0+(?!$)", "") : verifyRQ.getUsername();

        generalBean.expireRequest(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        int code = apiBean.createVerifyCode(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        userRepository.save(user);

        System.out.println(username);

        apiBean.sendEmailSMS(username, sms.sendSMS("send-verify", code),
                Duplicate.mailTemplateData(fullName, code, "reset-password"));

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public void updateContact(UpdateContactRQ contactRQ) {

        UserEntity user = userBean.getUserPrincipal();

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(contactRQ.getToken(), Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        generalBean.expiredInvalidToken(verifyUserEntity, contactRQ.getToken());

        if (NumberUtils.isNumber(contactRQ.getUsername())) {

            user.setPhone(contactRQ.getUsername());
            user.setCode(contactRQ.getCode());
            apiBean.updateContact(contactRQ.getUsername(), contactRQ.getCode(), user.getStakeHolderUser(), "p", null);

        } else if (EmailValidator.getInstance().isValid(contactRQ.getUsername())) {
            user.setEmail(contactRQ.getUsername());
            apiBean.updateContact(contactRQ.getUsername(), contactRQ.getCode(), user.getStakeHolderUser(), "e", null);
        } else {
            throw new BadRequestException("Invalid data", contactRQ.getUsername());
        }

        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.UPDATE_CONTACT, user);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public int sendCodeUpdateContact(UpdateContactRQ contactRQ) {

        SmsMessage sms = new SmsMessage();

        UserEntity user = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(contactRQ.getPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }

        //need to add httpstatus conflex
        generalBean.expireRequest(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));

        int code = apiBean.createVerifyCode(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        String fullName = user.getStakeHolderUser().getFirstName() + " " + user.getStakeHolderUser().getLastName();
        String username = NumberUtils.isNumber(contactRQ.getUsername()) ? contactRQ.getCode() + contactRQ.getUsername().replaceFirst("^0+(?!$)", "") : contactRQ.getUsername();

        userRepository.save(user);

        apiBean.sendEmailSMS(username, sms.sendSMS("send-verify", code),
                Duplicate.mailTemplateData(fullName, code, "verify-code"));

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param deactiveAccount
     */
    public void deactiveAccount(DeactiveAccountRQ accountRQ) {

        UserEntity user = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(accountRQ.getPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }

        user.getStakeHolderUser().setStatus(0);
        userRepository.save(user);

        apiBean.storeUserStatus(user, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "User was deactive account");

        String username = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();
        SmsMessage sms = new SmsMessage();

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        logger.activities(ActivityLoggingBean.Action.DEACTIVE_USER, user);

        apiBean.sendEmailSMS(username, sms.sendSMS("deactive-account", 0), Duplicate.mailTemplateData(fullName, 0, "deactive-account"));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Apply skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRegisterRQ
     */
    public void applySkyowner(CompanyRQ companyRQ) {

        UserEntity user = userBean.getUserPrincipal();

        if (user.getStakeHolderUser().getIsSkyowner() == 1) {
            throw new BadRequestException("ald_apl_skyowner", "");
        }

        var checkExists = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());

        System.out.println(checkExists);

        if (checkExists != null) {
            throw new BadRequestException("sth_w_w", "");
        }

        SkyownerRegisterRQ skyownerRQ = new SkyownerRegisterRQ();
        BeanUtils.copyProperties(companyRQ, skyownerRQ);
        skyownerRQ.setUsername(companyRQ.getEmail());

        skyownerBean.addStakeHolderCompany(skyownerRQ, user, userRepository);

    }


}
