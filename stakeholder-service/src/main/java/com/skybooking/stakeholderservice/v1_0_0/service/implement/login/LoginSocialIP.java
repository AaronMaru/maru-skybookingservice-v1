package com.skybooking.stakeholderservice.v1_0_0.service.implement.login;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSocialSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.TokenTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialSkyownerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginSocialIP implements LoginSocialSV {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private UserBean userBean;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    Environment environment;

    @Autowired
    private SkyownerBean skyownerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login Social
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param loginSocialRQ
     * @Param httpHeaders
     * @Return ResponseEntity
     */
    public UserDetailsTokenRS loginSocial(HttpHeaders httpHeaders, LoginSocialRQ loginSocialRQ) {

        String credential = userBean.oauth2Credential(httpHeaders);

        UserEntity user = userRepository.findByEmailOrProviderId(loginSocialRQ.getUsername(), loginSocialRQ.getProvider());

        String password = "defaultPassword";

        if (user == null) {
            user = addSkyuser(loginSocialRQ);
            logger.activities(ActivityLoggingBean.Action.REGISTER, user);
        }

        if (user.getProvider() == null) {
            throw new BadRequestException("This email register already please go to login page", null);
        }

        TokenTF data = userBean.getCredential(user.getEmail(), password, credential, null, loginSocialRQ.getProvider());

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, data.getAccess_token()), userDetailsTokenRS);

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     * @Return code
     */
    public UserDetailsTokenRS loginSocialSkyowner(HttpHeaders httpHeaders, LoginSocialSkyownerRQ skyownerSocialRQ) {
        LoginSocialRQ loginSocialRQ = new LoginSocialRQ();
        SkyownerRegisterRQ skyownerRegisterRQ = new SkyownerRegisterRQ();

        BeanUtils.copyProperties(skyownerSocialRQ, loginSocialRQ);
        BeanUtils.copyProperties(skyownerSocialRQ, skyownerRegisterRQ);

        String credential = userBean.oauth2Credential(httpHeaders);

        UserEntity user = userRepository.findByEmailOrProviderId(skyownerSocialRQ.getUsername(), skyownerSocialRQ.getProvider());

        String password = "defaultPassword";

        if (user == null) {
            user = addSkyuser(loginSocialRQ);
            skyownerBean.addStakeHolderCompany(skyownerRegisterRQ, user, userRepository);
            logger.activities(ActivityLoggingBean.Action.REGISTER, user);
        }

        if (user.getProvider() == null) {
            throw new BadRequestException("This email register already please go to login page", null);
        }

        TokenTF data = userBean.getCredential(user.getEmail(), password, credential, null, skyownerSocialRQ.getProvider());

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, data.getAccess_token()), userDetailsTokenRS);

        return userDetailsTokenRS;

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement register skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRq
     * @Return SkyUserRegisterRQ
     */
    public UserEntity addSkyuser(LoginSocialRQ loginSocialRQ) {

        UserEntity userEntity = new UserEntity();

        loginSocialRQ.setPassword(pwdEncoder.encode("defaultPassword"));

        BeanUtils.copyProperties(loginSocialRQ, userEntity);

        if (NumberUtils.isNumber(userEntity.getUsername())) {
            userEntity.setPhone(userEntity.getUsername());
        } else {
            userEntity.setEmail(userEntity.getUsername());
        }
        userEntity.setUsername(null);
        userEntity.setVerified(1);
        userEntity.setSlug(apiBean.createSlug("users"));

        StakeHolderUserEntity stakeHolderUserEntity = stakeHolderUserRP.findByUserCode("SKYU");
        String userCode = generalBean.stakeUniqueCode("SKYU", (stakeHolderUserEntity != null) ? stakeHolderUserEntity.getUserCode() : null, "01");
        userEntity.setUsername(userCode);

        addStakeHolderUser(loginSocialRQ, userEntity, userCode);

        UserEntity user = userRepository.save(userEntity);

        return user;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create stakeholder user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param loginSocialRQ
     * @Param userEntity
     */
    public void addStakeHolderUser(LoginSocialRQ loginSocialRQ, UserEntity userEntity, String userCode) {

        StakeHolderUserEntity stkUser = new StakeHolderUserEntity();

        stkUser.setUserCode(userCode);

        stkUser.setFirstName(loginSocialRQ.getFirstName());
        stkUser.setLastName(loginSocialRQ.getLastName());
        stkUser.setSlug(apiBean.createSlug("profiles"));

        userEntity.setStakeHolderUser(stkUser);
        stkUser.setUserEntity(userEntity);

        String fileName = userBean.uploadPhoto(loginSocialRQ.getPhotoUrl(),"/profile", "/profile/_thumbnail");
        stkUser.setPhoto(fileName);

        apiBean.addContact(loginSocialRQ.getUsername(),null, stkUser, null);

    }


}
