package com.skybooking.skygatewayservice.io.repository;

import com.skybooking.skygatewayservice.io.entity.OauthUserAccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthUserRP extends JpaRepository<OauthUserAccessTokenEntity, Long> {

    @Query(value = "SELECT * FROM oauth_user_access_tokens WHERE jwt_id = :jwtId", nativeQuery = true)
    OauthUserAccessTokenEntity getFirst(String jwtId);
}
