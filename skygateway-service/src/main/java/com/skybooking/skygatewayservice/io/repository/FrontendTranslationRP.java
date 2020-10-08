package com.skybooking.skygatewayservice.io.repository;

import com.skybooking.skygatewayservice.io.entity.FrontendTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontendTranslationRP extends JpaRepository<FrontendTranslationEntity, Integer> {

    @Query(value = "SELECT * FROM frontend_translation AS ft WHERE ft.key = :key AND ft.locale_id = :localeId LIMIT 1", nativeQuery = true)
    FrontendTranslationEntity first(String key, Integer localeId);

}
