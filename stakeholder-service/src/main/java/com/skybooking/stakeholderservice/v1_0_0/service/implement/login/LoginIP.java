package com.skybooking.stakeholderservice.v1_0_0.service.implement.login;

import com.skybooking.stakeholderservice.exception.httpstatus.ForbiddenException;
import com.skybooking.stakeholderservice.exception.httpstatus.ProxyException;
import com.skybooking.stakeholderservice.exception.httpstatus.TemporaryException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.TokenRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginIP implements LoginSV {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBean userBean;

    @Autowired
    private BCryptPasswordEncoder pwdEncode;

    @Autowired
    private LocalizationBean localizationBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Param loginRQ
     * @Return UserDetailsTokenRS
     */
    @Override
    public UserDetailsTokenRS login(HttpHeaders httpHeaders, LoginRQ loginRQ) {

        UserEntity user = userRepository.findByPhoneOrEmail(loginRQ.getUsername(), loginRQ.getCode());
        return login(httpHeaders, loginRQ, user);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Login user v1.1.0
     * for this version, user login with social v1.1.0 they also can login manual with this version
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpHeaders HttpHeaders
     * @param loginRQ LoginRQ
     * @return UserDetailsTokenRS
     */
    @Override
    public UserDetailsTokenRS loginV110(HttpHeaders httpHeaders, LoginRQ loginRQ) {

        UserEntity user = userRepository.findByPhoneOrEmailV100(loginRQ.getUsername(), loginRQ.getCode());
        return login(httpHeaders, loginRQ, user);

    }

    private UserDetailsTokenRS login(HttpHeaders httpHeaders, LoginRQ loginRQ, UserEntity user) {

        String emailOrPhone = NumberUtils.isNumber(loginRQ.getUsername()) ? "phone" : "email";

        if (user == null)
            throw new UnauthorizedException(String.format(localizationBean.multiLanguageRes("acc_inc"), emailOrPhone), null);

        if (!pwdEncode.matches(loginRQ.getPassword(), user.getPassword()))
            throw new UnauthorizedException(String.format(localizationBean.multiLanguageRes("acc_inc"), emailOrPhone), null);

        checkUserStatus(user, loginRQ.getPassword());

        String credential = userBean.oauth2Credential(httpHeaders);
        TokenRS token = userBean.getCredential(loginRQ.getUsername(), loginRQ.getPassword(), credential, loginRQ.getCode(), null);

        userBean.registerPlayer(user);

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, token), userDetailsTokenRS);
        userBean.storeUserTokenLastLogin(userDetailsTokenRS.getToken(), user);

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check user status
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param user
     */
    public void checkUserStatus(UserEntity user, String password) {

        if (user.getVerified() != 1) {
            userBean.storeTokenRedis(user, password);
            throw new TemporaryException("usr_log_not_vf", null);
        }

        if (user.getStakeHolderUser() == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        if (user.getStakeHolderUser().getIsSkyowner() == 1 && user.getStakeHolderUser().getStakeholderCompanies().size() == 0) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        switch (user.getStakeHolderUser().getStatus()) {
            case 0:
                throw new ForbiddenException("acc_deact", null);
            case 2:
                throw new ProxyException("acc_ban", null);
        }

    }


}
