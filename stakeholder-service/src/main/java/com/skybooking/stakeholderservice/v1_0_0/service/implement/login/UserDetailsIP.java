package com.skybooking.stakeholderservice.v1_0_0.service.implement.login;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.UserPrinciple;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


@Service
public class UserDetailsIP implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ActivityLoggingBean logger;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user credential
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     * @Return UserDetails
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userRepository.findByUsernameOrEmail(username);

        if (NumberUtils.isNumber(username)) {
            user = userRepository.findByPhoneAndCode(username, request.getParameter("code"));
        }

        if (! request.getParameter("provider").isEmpty()) {
            user = userRepository.findByEmailOrProviderId(username, request.getParameter("provider_id"));
        }

        /**
         * This for refresh token
         */
        if (request.getParameter("refresh") != null) {
            user = userRepository.findUserForRefreshToken(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("acc_inc", null);
        }

        logger.activities(ActivityLoggingBean.Action.LOGIN, user);

        return UserPrinciple.build(user);

    }


}
