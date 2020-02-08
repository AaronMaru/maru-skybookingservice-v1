package com.skybooking.stakeholderservice.v1_0_0.io.repository.verify;

import com.google.common.base.Verify;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VerifyUserRP extends JpaRepository<VerifyUserEntity, Integer> {


    /**
     * Find token login user
     */
    @Query(value = "SELECT * FROM verify_users WHERE token = ?1 AND status = ?2  ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    VerifyUserEntity findByTokenAndStatus(String token, int status);


    /**
     * Find tokens by user id
     */
    @Query(value = "SELECT * FROM verify_users WHERE user_id = ?1 AND status = ?2", nativeQuery = true)
    List<VerifyUserEntity> findByUserId(Long id, Integer status);

    /**
     * Find by tokens and username
     */
    VerifyUserEntity findByTokenAndUsername(String token, String username);

    /**
     * Find by username
     */
    List<VerifyUserEntity> findByUsername(String username);

}
