package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerifyServiceIP {

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
    public void verifyUser(VerifyRQ verifyRequest, int status) {

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(verifyRequest.getToken(), status);
        generalBean.expiredInvalidToken(verifyUserEntity, verifyRequest.getToken());

        verifyUserEntity.getUserEntity().setVerified(1);
        verifyUserEntity.getUserEntity().getStakeHolderUser().setStatus(1);

        UserEntity userEntity = userRepository.save(verifyUserEntity.getUserEntity());
        StakeHolderUserEntity stakeHolderUserEntity = userEntity.getStakeHolderUser();
        String fullName = stakeHolderUserEntity.getFirstName() + " " + stakeHolderUserEntity.getLastName();

        apiBean.storeUserStatus(verifyUserEntity.getUserEntity(),
                Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "Waiting verify");

        Map<String, String> mailTemplateData = new HashMap<>();
        mailTemplateData.put("fullName", fullName);
        mailTemplateData.put("templateName", "welcome");

        apiBean.sendEmailSMS(userEntity.getEmail(), "", mailTemplateData);
        logger.activities(ActivityLoggingBean.Action.VERIFY_USER_ACTIVE, verifyUserEntity.getUserEntity());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Return String
     */
    public int resendVerify(SendVerifyRQ sendVerifyRequest, Integer status) {

        SmsMessage sms = new SmsMessage();

        UserEntity userEntity = userRepository.findByEmailOrPhone(sendVerifyRequest.getUsername(),
                sendVerifyRequest.getCode());
        if (userEntity == null) {
            throw new UnauthorizedException("Opp something went wrong", "");
        }
        StakeHolderUserEntity stakeHolderUser = userEntity.getStakeHolderUser();

        generalBean.expireRequest(userEntity, status);

        int code = apiBean.createVerifyCode(userEntity, status);
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        userRepository.save(userEntity);


        apiBean.sendEmailSMS(sendVerifyRequest.getUsername(), sms.sendSMS("send-verify", Integer.toString(code)),
                Duplicate.duplicateContent(fullName, Integer.toString(code)));

        return code;
    }
}
