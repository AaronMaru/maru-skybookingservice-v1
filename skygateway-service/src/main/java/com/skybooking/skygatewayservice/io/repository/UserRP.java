package com.skybooking.skygatewayservice.io.repository;

import com.skybooking.skygatewayservice.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRP extends JpaRepository<UserEntity, Integer> {
}
