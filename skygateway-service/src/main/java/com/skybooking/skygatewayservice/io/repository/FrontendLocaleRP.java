package com.skybooking.skygatewayservice.io.repository;

import com.skybooking.skygatewayservice.io.entity.FrontendLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontendLocaleRP extends JpaRepository<FrontendLocaleEntity, Integer> {

    @Query(value = "SELECT * FROM frontend_locales LIMIT 1", nativeQuery = true)
    FrontendLocaleEntity first();

    @Query(value = "SELECT * FROM frontend_locales AS fl WHERE fl.locale = :locale AND fl.status = 1 LIMIT 1", nativeQuery = true)
    FrontendLocaleEntity available(String locale);

}
