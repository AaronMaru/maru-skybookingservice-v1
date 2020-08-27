package com.skybooking.eventservice.v1_0_0.io.repository.user;

import com.skybooking.eventservice.v1_0_0.io.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Find user by username
     */
    UserEntity findByUsername(String username);

}
