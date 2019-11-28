package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
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
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceIP implements UserSV {

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
    public UserDetailsRS updateProfile(ProfileRQ profileRequest, MultipartFile multipartFile) throws ParseException {

        UserEntity userEntity = userBean.getUserPrincipal();

        StakeHolderUserEntity stkHolder = userEntity.getStakeHolderUser();

        if (profileRequest.getFirstName() != null) {
            stkHolder.setFirstName(profileRequest.getFirstName());
        }
        if (profileRequest.getLastName() != null) {
            stkHolder.setLastName(profileRequest.getLastName());
        }
        if (profileRequest.getNationality() != null) {
            stkHolder.setNationality(profileRequest.getNationality());
        }

        if (profileRequest.getAddress() != null) {
            apiBean.updateContact(profileRequest.getAddress(), null, stkHolder, "a", "address");
        }

        if (profileRequest.getGender() != null) {
            stkHolder.setGender(profileRequest.getGender());
        }

        if (profileRequest.getDateOfBirth() != null) {
            stkHolder.setDateOfBirth(profileRequest.getDateOfBirth());
        }

        userEntity.setStakeHolderUser(stkHolder);
        if (multipartFile != null) {
            String fileName = userBean.uploadFileForm(multipartFile, "/profile", "/profile/_thumbnail");
            stkHolder.setPhoto(fileName);
        }

        userRepository.save(userEntity);

        UserDetailsRS userDetailsRS = new UserDetailsRS();
        BeanUtils.copyProperties(userBean.userFields(userEntity, null), userDetailsRS);

        logger.activities(ActivityLoggingBean.Action.UPDATE_STAKE_USER, userEntity);

        return userDetailsRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * User change password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     */
    public void changePassword(ChangePasswordRQ pwdRequest) {

        UserEntity userEntity = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(pwdRequest.getOldPassword(), userEntity.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }
        userEntity.setPassword(pwdEncoder.encode(pwdRequest.getNewPassword()));

        userRepository.save(userEntity);

        logger.activities(ActivityLoggingBean.Action.CHANGE_PASSWORD, userEntity);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     */
    public void resetPassword(ResetPasswordRQ pwdRequest) {

        SmsMessage sms = new SmsMessage();

        String username = pwdRequest.getUsername();

        if (NumberUtils.isNumber(pwdRequest.getUsername())) {
            username = pwdRequest.getCode() + pwdRequest.getUsername();
        }

        UserEntity userEntity = userRepository.findByEmailOrPhone(pwdRequest.getUsername(), pwdRequest.getCode());
        StakeHolderUserEntity stakeHolderUserEntity = userEntity.getStakeHolderUser();
        String fullName = stakeHolderUserEntity.getFirstName() + " " + stakeHolderUserEntity.getLastName();

        if (userEntity == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(pwdRequest.getToken(), Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        generalBean.expiredInvalidToken(verifyUserEntity, pwdRequest.getToken());

        userEntity.setPassword(pwdEncoder.encode(pwdRequest.getNewPassword()));
        userRepository.save(userEntity);

        logger.activities(ActivityLoggingBean.Action.RESET_PASSWORD, userEntity);

        Map<String, String> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", fullName);
        mailTemplateData.put("templateName", "complete-reset-password");


        apiBean.sendEmailSMS(username, sms.sendSMS("success-reset-password", null), mailTemplateData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     */
    public int sendCodeResetPassword(SendVerifyRQ sendVerifyRequest) {

        SmsMessage sms = new SmsMessage();
        UserEntity userEntity = userRepository.findByEmailOrPhone(sendVerifyRequest.getUsername(), sendVerifyRequest.getCode());

        if (userEntity == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }
        StakeHolderUserEntity stakeHolderUser = userEntity.getStakeHolderUser();

        generalBean.expireRequest(userEntity, Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        int code = apiBean.createVerifyCode(userEntity, Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        userRepository.save(userEntity);

        Map<String, String> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName());
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", "reset-password");

        apiBean.sendEmailSMS(sendVerifyRequest.getUsername(), sms.sendSMS("send-verify", Integer.toString(code)), mailTemplateData);

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public void updateContact(UpdateContactRQ updateContactRq) {

        UserEntity userEntity = userBean.getUserPrincipal();

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(updateContactRq.getToken(), Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        generalBean.expiredInvalidToken(verifyUserEntity, updateContactRq.getToken());

        if (NumberUtils.isNumber(updateContactRq.getUsername())) {

            userEntity.setPhone(updateContactRq.getUsername());
            userEntity.setCode(updateContactRq.getCode());
            apiBean.updateContact(updateContactRq.getUsername(), updateContactRq.getCode(), userEntity.getStakeHolderUser(), "p", null);

        } else if (EmailValidator.getInstance().isValid(updateContactRq.getUsername())) {
            userEntity.setEmail(updateContactRq.getUsername());
            apiBean.updateContact(updateContactRq.getUsername(), updateContactRq.getCode(), userEntity.getStakeHolderUser(), "e", null);
        } else {
            throw new BadRequestException("Invalid data", updateContactRq.getUsername());
        }

        userRepository.save(userEntity);

        logger.activities(ActivityLoggingBean.Action.UPDATE_CONTACT, userEntity);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public int sendCodeUpdateContact(UpdateContactRQ updateContactRq) {

        SmsMessage sms = new SmsMessage();

        UserEntity userEntity = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(updateContactRq.getPassword(), userEntity.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }

        //need to add httpstatus conflex
        generalBean.expireRequest(userEntity, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));

        int code = apiBean.createVerifyCode(userEntity, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        userRepository.save(userEntity);

        apiBean.sendEmailSMS(updateContactRq.getUsername(), sms.sendSMS("send-verify", Integer.toString(code)), null);

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param deactiveAccount
     */
    public void deactiveAccount(DeactiveAccountRQ deactiveAccount) {

        UserEntity userEntity = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(deactiveAccount.getPassword(), userEntity.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }

        userEntity.getStakeHolderUser().setStatus(0);
        userRepository.save(userEntity);

        apiBean.storeUserStatus(userEntity, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "User was deactive account");

        String username = (userEntity.getEmail() != null) ? userEntity.getEmail() : userEntity.getCode() + userEntity.getPhone();
        SmsMessage sms = new SmsMessage();

        logger.activities(ActivityLoggingBean.Action.DEACTIVE_USER, userEntity);

        apiBean.sendEmailSMS(username, sms.sendSMS("deactive-account", null), null);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Apply skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRegisterRQ
     */
    public void applySkyowner(CompanyRQ companyRQ) {

        UserEntity userEntity = userBean.getUserPrincipal();

//        if (userEntity.getStakeHolderUser().getIsSkyowner() == 1) {
//            throw new BadRequestException("ald_apl_skyowner", "");
//        }

        SkyownerRegisterRQ skyownerRQ = new SkyownerRegisterRQ();
        BeanUtils.copyProperties(companyRQ, skyownerRQ);
        skyownerRQ.setUsername(companyRQ.getEmail());

        skyownerBean.addStakeHolderCompany(skyownerRQ, userEntity, userRepository);

    }


}
