package com.skybooking.stakeholderservice.v1_0_0.io.repository.passwordReset;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passwordReset.PasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Long> {

    /**
     * Check user by email or phone
     */
    @Query(value = "SELECT * FROM password_resets WHERE token = ?2 AND (username = ?1 OR email = ?1) ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    PasswordResetEntity findByEmailOrUsernameAndToken(String username, String Token);

}
