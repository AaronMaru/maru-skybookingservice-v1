package com.skybooking.paymentservice.v1_0_0.io.repository.users;

import com.skybooking.paymentservice.v1_0_0.io.enitity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Find user by username
     * @return
     */
    UserEntity findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE id = :userId LIMIT 1", nativeQuery = true)
    UserEntity first(Long userId);

}
