package com.skybooking.stakeholderservice.v1_0_0.service.implement.login;

import com.skybooking.stakeholderservice.exception.httpstatus.ForbiddenException;
import com.skybooking.stakeholderservice.exception.httpstatus.ProxyException;
import com.skybooking.stakeholderservice.exception.httpstatus.TemporaryException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.login.LoginSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.TokenTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
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

        String credential = userBean.oauth2Credential(httpHeaders);

        String password = loginRQ.getPassword();

        UserEntity user = userRepository.findByUsernameOrEmail(loginRQ.getUsername());

        String emailOrPhone = "email";
        if (NumberUtils.isNumber(loginRQ.getUsername())) {
            user = userRepository.findByPhoneAndCode(loginRQ.getUsername(), loginRQ.getCode());
            emailOrPhone = "phone";
        }

        if (user == null) {
            throw new UnauthorizedException(String.format("Your %s or password is incorrect", emailOrPhone), null);
        }

        if (!pwdEncode.matches(loginRQ.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(String.format("Your %s or password is incorrect", emailOrPhone), null);
        }

        checkUserStatus(user, password);

        TokenTF data = userBean.getCredential(loginRQ.getUsername(),loginRQ.getPassword(), credential, loginRQ.getCode(), null);

        userBean.registerPlayer(user);

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, data.getAccess_token()), userDetailsTokenRS);

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
            throw new TemporaryException("You need to confirm your account. We have sent you an activation code, please check your email or SMS.", null);
        }

        if (user.getStakeHolderUser() == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        if (user.getStakeHolderUser().getIsSkyowner() == 1 && user.getStakeHolderUser().getStakeholderCompanies().size() == 0) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        switch (user.getStakeHolderUser().getStatus()) {
            case 0:
                throw new ForbiddenException("Your account inactive", null);
            case 2:
                throw new ProxyException("Your account was ban", null);
        }

    }


}
