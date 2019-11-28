package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.RegisterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceIP implements RegisterSV {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private SkyownerBean skyownerBean;

    @Autowired
    private StakeholderUserInvitationRP userInvRP;

    @Autowired
    private Environment environment;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     * @Return code
     */
    public int skyuser(SkyUserRegisterRQ skyuserRq) {

        UserEntity user = addSkyuser(skyuserRq);
        VerifyUserEntity verifyUserEntity = user.getVerifyUserEntity().stream().findFirst().get();

        StakeholderUserInvitationEntity userInv = userInvRP.findFirstByInviteFrom(skyuserRq.getUsername());

        if (userInv != null) {
            skyownerBean.addStaff(userInv.getStakeholderCompanyId(), user.getStakeHolderUser().getId());
        }

        return NumberUtils.toInt(verifyUserEntity.getToken());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     * @Return code
     */
    public int skyowner(SkyownerRegisterRQ skyownerRq) {

        SkyUserRegisterRQ skyuserRq = new SkyUserRegisterRQ();

        BeanUtils.copyProperties(skyownerRq, skyuserRq);

        UserEntity userEntity = addSkyuser(skyuserRq);

        VerifyUserEntity verifyUserEntity = userEntity.getVerifyUserEntity().stream().findFirst().get();

        skyownerBean.addStakeHolderCompany(skyownerRq, userEntity, userRepository);

        return NumberUtils.toInt(verifyUserEntity.getToken());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement register skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRq
     * @Return code
     */
    public UserEntity addSkyuser(SkyUserRegisterRQ skyuserRq) {

        UserEntity userEntity = new UserEntity();

        String pwd = skyuserRq.getPassword();
        skyuserRq.setPassword(pwdEncoder.encode(pwd));

        BeanUtils.copyProperties(skyuserRq, userEntity);

        if (NumberUtils.isNumber(userEntity.getUsername())) {
            userEntity.setPhone(userEntity.getUsername());
        } else {
            userEntity.setEmail(userEntity.getUsername());
            userEntity.setCode(null);
        }
        userEntity.setUsername(null);
        userEntity.setSlug(apiBean.createSlug("users"));

        StakeHolderUserEntity stakeHolderUserEntity = stakeHolderUserRP.findByUserCode("SKYU");
        String userCode = generalBean.stakeUniqueCode("SKYU",
                (stakeHolderUserEntity != null) ? stakeHolderUserEntity.getUserCode() : null, "01");
        userEntity.setUsername(userCode);

        sendVerification(skyuserRq, userEntity);

        addStakeHolderUser(skyuserRq, userEntity, userCode);
        userEntity = userRepository.save(userEntity);

        apiBean.storeUserStatus(userEntity, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")),
                "Waiting verify");

        logger.activities(ActivityLoggingBean.Action.REGISTER, userEntity);

        return userEntity;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create stakeholder user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyUserRegisterRq
     * @Param userEntity
     */
    public void addStakeHolderUser(SkyUserRegisterRQ skyUserRegisterRQ, UserEntity userEntity, String userCode) {

        StakeHolderUserEntity stkUser = new StakeHolderUserEntity();

        stkUser.setFirstName(skyUserRegisterRQ.getFirstName());
        stkUser.setLastName(skyUserRegisterRQ.getLastName());
        stkUser.setSlug(apiBean.createSlug("profiles"));

        stkUser.setUserCode(userCode);

        userEntity.setStakeHolderUser(stkUser);
        stkUser.setUserEntity(userEntity);

        apiBean.addContact(skyUserRegisterRQ.getUsername(), skyUserRegisterRQ.getCode(), stkUser, null);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send verification code to user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRq
     * @Param userEntity
     */
    public int sendVerification(SkyUserRegisterRQ skyuserRq, UserEntity userEntity) {

        SmsMessage sms = new SmsMessage();
        int code = apiBean.createVerifyCode(userEntity, 1);
        String fullName = skyuserRq.getFirstName() + " " + skyuserRq.getLastName();

        apiBean.sendEmailSMS(skyuserRq.getUsername(), sms.sendSMS("send-verify", Integer.toString(code)),
                Duplicate.duplicateContent(fullName, Integer.toString(code)));
        return code;

    }

}
