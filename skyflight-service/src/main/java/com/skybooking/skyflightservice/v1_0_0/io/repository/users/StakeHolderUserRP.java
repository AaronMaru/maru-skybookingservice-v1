package com.skybooking.skyflightservice.v1_0_0.io.repository.users;

import com.skybooking.skyflightservice.v1_0_0.io.entity.user.StakeHolderUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeHolderUserRP extends JpaRepository<StakeHolderUserEntity, Long> {

    /**
     * Get last stakeholder user code
     */
    @Query(value = "SELECT * FROM stakeholder_users WHERE user_code LIKE %?1% ORDER BY id DESC LIMIT 1", nativeQuery = true)
    StakeHolderUserEntity findByUserCode(String userCode);

}
