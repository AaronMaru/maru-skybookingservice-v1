package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.ConflictException;
import com.skybooking.stakeholderservice.exception.httpstatus.ForbiddenException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.redis.UserTokenRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.TokenRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyMRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.skybooking.stakeholderservice.constant.MailStatusConstant.*;

@Service
public class VerifyIP implements VerifySV {

    @Autowired
    private VerifyUserRP verifyRP;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private Environment environment;

    @Autowired
    private UserTokenRP userTokenRP;

    @Autowired
    private UserBean userBean;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private PushNotificationOptions notification;

    @Autowired
    private LocalizationBean localizationBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String
     */
    public UserDetailsTokenRS verifyUser(VerifyRQ verifyRQ, int status) {

        VerifyUserEntity verify = verifyRP.findByTokenAndStatus(verifyRQ.getToken(), status);

        generalBean.expiredInvalidToken(verify, verifyRQ.getToken().replaceAll(" ", ""));

        verify.getUserEntity().setVerified(1);
        verify.getUserEntity().getStakeHolderUser().setStatus(1);

        UserEntity user = userRepository.save(verify.getUserEntity());
        StakeHolderUserEntity skyuser = user.getStakeHolderUser();

        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();
        String receiver = (user.getEmail() != null) ? user.getEmail()
                : user.getCode() + user.getPhone();

        apiBean.storeUserStatus(verify.getUserEntity(),
                Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "Waiting login");

        String keyScript = status == 1 ? ACCOUNT_VERIFIED_SUCCESSFULLY : ACCOUNT_REACTIVATED_SUCCESSFULLY;
        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, 0, keyScript);

        emailBean.sendEmailSMS("verify-success", mailData);

        logger.activities(ActivityLoggingBean.Action.VERIFY_USER_ACTIVE, verify.getUserEntity());

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        if (status == 1) {
            UserTokenEntity userToken = userTokenRP.findById(user.getId()).orElse(null);

            TokenRS token = new TokenRS();
            token.setToken(userToken.getToken());
            token.setRefreshToken(userToken.getRefreshToken());

            BeanUtils.copyProperties(userBean.userFields(user, token), userDetailsTokenRS);

            HashMap<String, Object> data = new HashMap<>();
            data.put("scriptKey", "skyuser_welcome_message");
            data.put("skyuserId", user.getStakeHolderUser().getId());
            notification.sendMessageToUsers(data);

            userBean.storeUserTokenLastLogin(token.getToken(), user);
        }

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend login user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Param status
     * @Return String
     */
    public int resendVerify(SendVerifyRQ verifyRQ, Integer status) {

        UserEntity user = userValidation(verifyRQ.getUsername(), verifyRQ.getCode());

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();

        if (stakeHolderUser.getStatus() == Integer.parseInt(environment.getProperty("spring.stkStatus.active"))) {
            throw new UnauthorizedException("act_ald", null);
        }

        generalBean.expireRequest(user, status);

        int code = apiBean.createVerifyCode(user, status, null);
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String receiver = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();

        userRepository.save(user);

        String keyScript = status == 1 ? RESEND_NEW_VERIFICATION_CODE : NEW_VERIFICATION_CODE_REACTIVATING_ACCOUNT;
        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, code, keyScript);
        emailBean.sendEmailSMS("send-login", mailData);

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify (Mobile process)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRQ
     */
    public void verify(VerifyMRQ verifyMRQ, Integer status) {

        String receiver = userBean.getUsername(verifyMRQ.getUsername(), verifyMRQ.getCode());

        var verify = verifyRP.findByTokenAndUsernameAndStatus(verifyMRQ.getToken(), receiver, status);

        generalBean.expiredInvalidToken(verify, verifyMRQ.getToken().replaceAll(" ", ""));

        verify.setVerified(1);

        verifyRP.save(verify);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send verify (Mobile process)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRQ
     */
    public void sendVerify(SendVerifyRQ sendVerifyRQ, Integer status) {

        if (status == Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserApp"))) {
            checkOwnerStaff(sendVerifyRQ);
        } else {
            userValidation(sendVerifyRQ.getUsername(), sendVerifyRQ.getCode());
        }

        String receiver = userBean.getUsername(sendVerifyRQ.getUsername(), sendVerifyRQ.getCode());

        generalBean.expireRequestMobile(receiver);

        int code = apiBean.createVerifyCode(null, status, receiver);

        Map<String, Object> mailData = emailBean.mailData(receiver, "", code, ACCOUNT_VERIFICATION_CODE);
        emailBean.sendEmailSMS("send-login", mailData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check manual user skyowner or staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRQ
     */
    public void checkOwnerStaff(SendVerifyRQ sendVerifyRQ) {

        String username = userBean.getUsername(sendVerifyRQ.getUsername(), null);
        String phoneOrEmail = phoneOrEmail(sendVerifyRQ.getUsername());

        UserEntity user = userRepository.findByEmailOrPhone(username, sendVerifyRQ.getCode());

        if (user != null) {
            Map<String, Object> data = new LinkedHashMap();
            if (user.getStakeHolderUser().getIsSkyowner() == 1) {
                data.put("type", "skyowner");
                throw new BadRequestException(String.format(localizationBean.multiLanguageRes("exist_skyowner"), phoneOrEmail), data);
            }

            var checkStaff = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());
            if (checkStaff != null) {
                data.put("type", "staff");
                throw new BadRequestException(String.format(localizationBean.multiLanguageRes("exist_skystaff"), phoneOrEmail), data);
            }

            if (user.getProviderId() != null) {
                throw new BadRequestException(String.format(localizationBean.multiLanguageRes("exist_skyuser"), phoneOrEmail), null);
            }

            throw new ForbiddenException(String.format(localizationBean.multiLanguageRes("exist_skyuser"), phoneOrEmail), null);

        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     */
    public int sendCodeResetPassword(SendVerifyRQ verifyRQ) {

        UserEntity user = userValidation(verifyRQ.getUsername(), verifyRQ.getCode());

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String receiver = userBean.getUsername(verifyRQ.getUsername(), verifyRQ.getCode());

        generalBean.expireRequest(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        int tokenCode = apiBean.createVerifyCode(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")), null);

        userRepository.save(user);

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, tokenCode, RESET_YOUR_PASSWORD);
        emailBean.sendEmailSMS("send-login", mailData);

        return tokenCode;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * User validation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param usernameRQ
     * @Param codeRQ
     * @Return UserEntity
     */
    private UserEntity userValidation(String usernameRQ, String codeRQ) {

        String username = userBean.getUsername(usernameRQ, null);

        UserEntity user = userRepository.findByEmailOrPhone(username, codeRQ);

        if (user == null) {
            throw new BadRequestException(String.format(localizationBean.multiLanguageRes("cont_not_exist"), phoneOrEmail(usernameRQ)), null);
        }

        if (user.getProviderId() != null) {
            throw new ConflictException(String.format(localizationBean.multiLanguageRes("reset_pwd_social"), user.getProvider()), null);
        }

        return user;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Determine phone or email than get as string
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     * @Return String
     */
    private String phoneOrEmail(String username) {
        String phoneOrEmail = NumberUtils.isNumber(username) ? "phone" : "email";
        return phoneOrEmail;
    }

}



