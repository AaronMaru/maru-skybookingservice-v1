package com.skybooking.skyhotelservice.v1_0_0.io.repository.token;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.token.ServiceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTokenRP extends JpaRepository<ServiceTokenEntity, Long> {
    @Query(value = "SELECT * FROM service_cached_token WHERE app_id = :app AND supplier = :supplier AND service = :service ORDER BY created_at DESC LIMIT 1 ", nativeQuery = true)
    ServiceTokenEntity findLastAccessToken(String app, String supplier, String service);
}
