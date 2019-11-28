package com.skybooking.stakeholderservice.v1_0_0.io.repository.notification;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.UserPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlayerRP extends JpaRepository<UserPlayerEntity, Long> {


    UserPlayerEntity findByStuserIdAndPlayerId(Long skyuserId, String playerId);

}
