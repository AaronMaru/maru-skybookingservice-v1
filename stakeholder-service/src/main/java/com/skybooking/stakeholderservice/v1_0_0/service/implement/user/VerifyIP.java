package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.redis.UserTokenRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyMRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
            BeanUtils.copyProperties(userBean.userFields(user, userToken.getToken()), userDetailsTokenRS);
            notification.sendMessageToUsers("skyuser_welcome_message", null, user.getStakeHolderUser().getId());
            return userDetailsTokenRS;
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

        UserEntity user = userRepository.findByEmailOrPhoneAndProviderIdIsNull(verifyRQ.getUsername(), verifyRQ.getCode());

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }
        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();

        if (stakeHolderUser.getStatus() == Integer.parseInt(environment.getProperty("spring.stkStatus.active"))) {
            throw new UnauthorizedException("The user already active", null);
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

        String username = userBean.getUsername(sendVerifyRQ.getUsername(),null);
        if (status == Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserApp"))) {
            checkOwnerStaff(sendVerifyRQ);
        } else {
            UserEntity user = userRepository.findByEmailOrPhoneAndProviderIdIsNull(username, sendVerifyRQ.getCode());
            if (user == null) {
                throw new BadRequestException(String.format("Sorry! The %s does not exist in the system.", NumberUtils.isNumber(sendVerifyRQ.getUsername()) ? "phone" : "email"), null);
            }
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

        String username = "";
        String emailOrPhone = "";

        if (NumberUtils.isNumber(sendVerifyRQ.getUsername())) {
            username = sendVerifyRQ.getUsername().replaceFirst("^0+(?!$)", "");
            emailOrPhone = "phone";
        } else {
            username = sendVerifyRQ.getUsername();
            emailOrPhone = "email";
        }

        UserEntity user = userRepository.findByEmailOrPhoneAndProviderIdIsNull(username, sendVerifyRQ.getCode());

        if (user != null) {
            Map<String, Object> data = new LinkedHashMap();
            if (user.getStakeHolderUser().getIsSkyowner() == 1) {
                data.put("type", "skyowner");
                throw new BadRequestException(String.format("Sorry! This %s was signed up as Skyowner already.", emailOrPhone), data);
            }

            var checkStaff = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());
            if (checkStaff != null) {
                data.put("type", "staff");
                throw new BadRequestException(String.format("Sorry! This %s was signed up as Skystaff already.", emailOrPhone), data);
            }

            throw new BadRequestException(String.format("Sorry! This %s was signed up as Skyuser already.", emailOrPhone), null);

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

        UserEntity user = userRepository.findByEmailOrPhoneAndProviderIdIsNull(verifyRQ.getUsername(), verifyRQ.getCode());

        if (user == null || user.getProviderId() != null) {
            throw new UnauthorizedException("Unauthorized", null);
        }

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String receiver = NumberUtils.isNumber(verifyRQ.getUsername())
                ? verifyRQ.getCode() + verifyRQ.getUsername().replaceFirst("^0+(?!$)", "")
                : verifyRQ.getUsername();

        generalBean.expireRequest(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        int code = apiBean.createVerifyCode(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")), null);
        userRepository.save(user);

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, code, RESET_YOUR_PASSWORD);
        emailBean.sendEmailSMS("send-login", mailData);

        return code;
    }
}



