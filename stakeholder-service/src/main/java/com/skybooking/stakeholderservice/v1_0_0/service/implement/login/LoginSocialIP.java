package com.skybooking.stakeholderservice.v1_0_0.service.implement.login;

import com.skybooking.stakeholderservice.constant.PasswordConstant;
import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSocialSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialV110RQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.TokenRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
    private HeaderBean headerBean;


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

        if (user == null) {
            loginSocialRQ.setPassword(pwdEncoder.encode(PasswordConstant.DEFAULT));
            user = addSkyuser(loginSocialRQ);
            logger.activities(ActivityLoggingBean.Action.REGISTER, user);
        }

        if (user.getProvider() == null) {
            throw new BadRequestException("fail_reg", null);
        }

        TokenRS token = userBean.getCredential(user.getEmail(), PasswordConstant.DEFAULT, credential, null, loginSocialRQ.getProvider());

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();

        BeanUtils.copyProperties(userBean.userFields(user, token), userDetailsTokenRS);
        userDetailsTokenRS.setTypeSky(loginSocialRQ.getTypeSky());

        userBean.registerPlayer(user);

        userBean.storeUserTokenLastLogin(userDetailsTokenRS.getToken(), user);

        return userDetailsTokenRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login Social
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param loginSocialRQ
     * @Param httpHeaders
     * @Return ResponseEntity
     */
    public UserDetailsTokenRS loginSocialV110(HttpHeaders httpHeaders, LoginSocialV110RQ loginSocialRQ) {

        String credential = userBean.oauth2Credential(httpHeaders);
        UserEntity user = userRepository.findByEmailOrProviderId(loginSocialRQ.getUsername(), loginSocialRQ.getProvider());
        TokenRS token;
        String password = loginSocialRQ.getPassword();

        if (user == null) {
            loginSocialRQ.setPassword(pwdEncoder.encode(password));
            user = addSkyuser(loginSocialRQ);
            logger.activities(ActivityLoggingBean.Action.REGISTER, user);
        }

        if (user.getProvider() == null) {
            throw new BadRequestException("fail_reg", null);
        }

        if (loginSocialRQ.getPassword() != null) {
            if (pwdEncoder.matches(PasswordConstant.DEFAULT, user.getPassword()) || user.getPassword() == null) {
                user.setPassword(pwdEncoder.encode(password));
                userRepository.save(user);
                token = userBean.getCredential(user.getEmail(), password, credential, null, loginSocialRQ.getProvider());
            } else if (pwdEncoder.matches(password, user.getPassword())) {
                token = userBean.getCredential(user.getEmail(), password, credential, null, loginSocialRQ.getProvider());
            } else {
                token = userBean.getCredential(user.getEmail(), credential, null, loginSocialRQ.getProvider());
            }
        } else {
            token = userBean.getCredential(user.getEmail(), credential, null, loginSocialRQ.getProvider());
        }

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();

        BeanUtils.copyProperties(userBean.userFields(user, token), userDetailsTokenRS);
        userDetailsTokenRS.setTypeSky(loginSocialRQ.getTypeSky());

        userBean.registerPlayer(user);

        userBean.storeUserTokenLastLogin(userDetailsTokenRS.getToken(), user);

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
        stkUser.setStatus(1);
        stkUser.setCurrencyId((long) 103);

        HashMap<String, String> userAgent = headerBean.getUserAgent();
        stkUser.setCreatedFrom(userAgent.get("from"));
        stkUser.setDeviceName(userAgent.get("device"));

        userEntity.setStakeHolderUser(stkUser);
        stkUser.setUserEntity(userEntity);

        String fileName = userBean.uploadPhoto(loginSocialRQ.getPhotoUrl(),"/profile", "/profile/_thumbnail");
        stkUser.setPhoto(fileName);

        apiBean.addContact(loginSocialRQ.getUsername(),null, stkUser, null);

    }


}
