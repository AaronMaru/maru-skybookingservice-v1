package com.skybooking.skypointservice.v1_0_0.io.repository.account;

import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRP extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findAccountEntityByUserCodeAndType(String userCode, String userType);
}
