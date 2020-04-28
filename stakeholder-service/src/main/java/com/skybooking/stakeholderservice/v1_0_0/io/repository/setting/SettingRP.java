package com.skybooking.stakeholderservice.v1_0_0.io.repository.setting;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting.FrontendConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRP extends JpaRepository<FrontendConfigEntity, Long> {

    /**
     * Get setting config
     */
    @Query(value = "SELECT * FROM frontend_config WHERE type IN('appstore', 'contact', 'third_party', 'social_link', '" +
            "', 'twitter', 'seo', 'facebook')", nativeQuery = true)
    List<FrontendConfigEntity> findAllByType();

    @Query(value = "SELECT * FROM frontend_config WHERE name = :keyword AND type = 'appstore'", nativeQuery = true)
    FrontendConfigEntity getDeepLink(String keyword);

}
