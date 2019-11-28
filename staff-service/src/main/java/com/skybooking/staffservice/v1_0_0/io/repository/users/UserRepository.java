package com.skybooking.staffservice.v1_0_0.io.repository.users;


import com.skybooking.staffservice.v1_0_0.io.enitity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    /**
     * Find user login by email or username
     */
    @Query(value = "SELECT * FROM users WHERE provider IS NULL AND (username = ?1 OR email = ?1)", nativeQuery = true)
    UserEntity findByUsernameOrEmail(String username);


    /**
     * Find user login by phone
     */
    @Query(value = "SELECT * FROM users WHERE provider IS NULL AND phone = ?1 AND code = ?2", nativeQuery = true)
    UserEntity findByPhoneAndCode(String username, String code);


    /**
     * Find user login by social
     */
    @Query(value = "SELECT * FROM users WHERE (email = ?1 OR provider_id = ?2) LIMIT 1", nativeQuery = true)
    UserEntity findByEmailOrProviderId(String username, String provider_id);


    /**
     * Find user by id
     */
    UserEntity findById(Long id);


    /**
     * Check user by email or phone
     */
    @Query(value = "SELECT * FROM users WHERE email = ?1 OR (phone = ?1 AND code = ?2)", nativeQuery = true)
    UserEntity findByEmailOrPhone(String username, String code);


    /**
     * Check exists user by phone
     */
    @Query(value = "SELECT CASE WHEN COUNT(email) + COUNT(phone) > 0 THEN 'true' ELSE 'false' END FROM users WHERE email = ?1 OR (phone = ?1 AND code = ?2)", nativeQuery = true)
    String existsByEmailOrPhone(String username, String code);

}
