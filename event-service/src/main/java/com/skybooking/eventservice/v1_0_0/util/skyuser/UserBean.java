package com.skybooking.eventservice.v1_0_0.util.skyuser;

import com.skybooking.eventservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.eventservice.v1_0_0.io.entity.user.UserEntity;
import com.skybooking.eventservice.v1_0_0.io.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserBean {

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