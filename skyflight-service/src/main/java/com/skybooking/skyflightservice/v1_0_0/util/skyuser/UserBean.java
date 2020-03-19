package com.skybooking.skyflightservice.v1_0_0.util.skyuser;

import com.skybooking.skyflightservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.skyflightservice.v1_0_0.io.entity.user.UserEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserBean {

    @Autowired
    Environment environment;

    @Autowired
    private UserRepository userRepository;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user principal
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return UserEntity
     */
    public UserEntity getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userRepository.findByUsername(authentication.getName());

        if (user == null) {
            throw new UnauthorizedException("Unauthorized", null);
        }

        if (user.getStakeHolderUser().getStatus() != 1) {
            throw new UnauthorizedException("Unauthorized", null);
        }

        return user;

    }


}