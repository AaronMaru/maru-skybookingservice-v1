package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.RegisterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

import static com.skybooking.stakeholderservice.constant.MailStatusConstant.ACCOUNT_VERIFICATION_CODE;

@Service
public class RegisterIP implements RegisterSV {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private EmailBean emailBean;

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

    @Autowired
    private UserBean userBean;

    @Autowired
    private VerifyUserRP verifyUserRP;

    @Autowired
    private HeaderBean headerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     * @Return code
     */
    public UserDetailsTokenRS skyuser(SkyUserRegisterRQ skyuserRQ, String plateform) {

        UserDetailsTokenRS userDetails = new UserDetailsTokenRS();

        String password = skyuserRQ.getPassword();
        String username = userBean.getUsername(skyuserRQ.getUsername(), null);

        UserEntity user = addSkyuser(skyuserRQ, plateform);

        StakeholderUserInvitationEntity userInv = userInvRP.findFirstByInviteTo(username);

        if (userInv != null) {
            skyownerBean.addStaff(userInv, user.getStakeHolderUser().getId());
            userInv.setInviteStakeholderUserId(user.getStakeHolderUser().getId());
            userInv.setStatus(1);
            userInvRP.save(userInv);
        }
        if (plateform == "web") {
            userBean.storeTokenRedis(user, password);
        } else {
            userDetails = userBean.userDetailByLogin(user, username, password);
            userBean.storeUserTokenLastLogin(userDetails.getToken(), user);
        }

        return userDetails;

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement register skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRQ
     * @Return code
     */
    public UserEntity addSkyuser(SkyUserRegisterRQ skyuserRQ, String plateform) {

        UserEntity user = new UserEntity();

        String pwd = skyuserRQ.getPassword();
        skyuserRQ.setPassword(pwdEncoder.encode(pwd));

        BeanUtils.copyProperties(skyuserRQ, user);

        if (NumberUtils.isNumber(user.getUsername())) {
            user.setPhone(user.getUsername().replaceFirst("^0+(?!$)", ""));
        } else {
            user.setEmail(user.getUsername());
            user.setCode(null);
        }

        user.setUsername(null);
        user.setSlug(apiBean.createSlug("users"));

        StakeHolderUserEntity skyuser = stakeHolderUserRP.findByUserCode("SKYU");
        String userCode = generalBean.stakeUniqueCode("SKYU",
                (skyuser != null) ? skyuser.getUserCode() : null, "1");

        user.setUsername(userCode);

        addStakeHolderUser(skyuserRQ, user, userCode, plateform);
        user = userRepository.save(user);

        if (plateform == "web") {
            sendVerification(skyuserRQ, user);
        }

        apiBean.storeUserStatus(user, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")),
                "Waiting login");

        logger.activities(ActivityLoggingBean.Action.REGISTER, user);

        return user;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create stakeholder user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyUserRegisterRq
     * @Param userEntity
     */
    public void addStakeHolderUser(SkyUserRegisterRQ registerRQ, UserEntity userEntity, String userCode, String plateform) {

        StakeHolderUserEntity skyuser = new StakeHolderUserEntity();

        skyuser.setFirstName(registerRQ.getFirstName());
        skyuser.setLastName(registerRQ.getLastName());
        skyuser.setSlug(apiBean.createSlug("profiles"));
        skyuser.setCurrencyId((long) 103);
        skyuser.setUserCode(userCode);

        HashMap<String, String> userAgent = headerBean.getUserAgent();
        skyuser.setCreatedFrom(userAgent.get("from"));
        skyuser.setDeviceName(userAgent.get("device"));

        userEntity.setStakeHolderUser(skyuser);
        skyuser.setUserEntity(userEntity);

        apiBean.addContact(registerRQ.getUsername(), registerRQ.getCode(), skyuser, null);

        regMobile(registerRQ, userEntity, skyuser, plateform);

    }

    public void regMobile(SkyUserRegisterRQ registerRQ, UserEntity userEntity, StakeHolderUserEntity skyuser, String plateform) {

        String reciever = userBean.getUsername(registerRQ.getUsername(), registerRQ.getCode());

        if (plateform == "app") {
            var verify = verifyUserRP.findByUsername(reciever);
            if (verify.size() == 0) {
                throw new BadRequestException("sth_w_w", null);
            }

            if (verify.get(verify.size() - 1).getVerified() != 1) {
                throw new BadRequestException("sth_w_w", null);
            }
            userEntity.setVerified(1);
            skyuser.setStatus(1);
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send verification code to user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRQ
     * @Param userEntity
     */
    public int sendVerification(SkyUserRegisterRQ skyuserRQ, UserEntity user) {

        int code = apiBean.createVerifyCode(user, 1, null);
        String fullName = skyuserRQ.getFirstName() + " " + skyuserRQ.getLastName();
        String receiver = userBean.getUsername(skyuserRQ.getUsername(), skyuserRQ.getCode());

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, code, ACCOUNT_VERIFICATION_CODE);
        emailBean.sendEmailSMS("send-login", mailData);

        return code;

    }

}
