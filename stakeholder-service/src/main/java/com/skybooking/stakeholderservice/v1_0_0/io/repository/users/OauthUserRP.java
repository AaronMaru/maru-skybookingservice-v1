package com.skybooking.stakeholderservice.v1_0_0.io.repository.users;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.OauthUserAccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthUserRP extends JpaRepository<OauthUserAccessTokenEntity, Long> {

    @Query(value = "SELECT * FROM oauth_user_access_tokens WHERE jwt_id = :jwtId AND status = :status", nativeQuery = true)
    OauthUserAccessTokenEntity getFirst(String jwtId, Integer status);

    List<OauthUserAccessTokenEntity> findByUserIdAndStatus(Long userId, Integer status);
}
