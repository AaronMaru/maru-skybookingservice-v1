package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class VerifyIP implements VerifySV {

    @Autowired
    private VerifyUserRP verifyRepository;

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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String
     */
    public void verifyUser(VerifyRQ verifyRQ, int status) {

        SmsMessage sms = new SmsMessage();
        VerifyUserEntity verify = verifyRepository.findByTokenAndStatus(verifyRQ.getToken(), status);
        generalBean.expiredInvalidToken(verify, verifyRQ.getToken());

        verify.getUserEntity().setVerified(1);
        verify.getUserEntity().getStakeHolderUser().setStatus(1);

        UserEntity user = userRepository.save(verify.getUserEntity());
        StakeHolderUserEntity skyuser = user.getStakeHolderUser();
        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();
        String username = (user.getEmail() != null) ? user.getEmail()
                : user.getCode() + user.getPhone();

        apiBean.storeUserStatus(verify.getUserEntity(),
                Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "Waiting verify");

        apiBean.sendEmailSMS(username, sms.sendSMS("verify-success", 0),
                Duplicate.mailTemplateData(fullName, 0, status == 1 ? "welcome" : "active-account"));

        logger.activities(ActivityLoggingBean.Action.VERIFY_USER_ACTIVE, verify.getUserEntity());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Param status
     * @Return String
     */
    public int resendVerify(SendVerifyRQ verifyRQ, Integer status) {

        SmsMessage sms = new SmsMessage();

        UserEntity user = userRepository.findByEmailOrPhone(verifyRQ.getUsername(), verifyRQ.getCode());
        if (user == null) {
            throw new UnauthorizedException("sth_w_w", "");
        }
        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();

        generalBean.expireRequest(user, status);

        int code = apiBean.createVerifyCode(user, status);
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String username = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();

        userRepository.save(user);

        apiBean.sendEmailSMS(username, sms.sendSMS("send-verify", code),
                Duplicate.mailTemplateData(fullName, code, "verify-code"));

        return code;
    }

}
