package com.skybooking.skyflightservice.v1_0_0.io.repository.locale;

import com.skybooking.skyflightservice.v1_0_0.io.entity.locale.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRP extends JpaRepository<TranslationEntity, Long> {

    @Query(value = "SELECT * FROM frontend_translation as ft LEFT JOIN frontend_locales as fl ON ft.locale_id = fl.id WHERE ft.module = 'api' AND ft.key = ?1 AND fl.locale = ?2", nativeQuery = true)
    TranslationEntity findByKey(String key, String lang);


    /**
     * ----------------------------------------------------------------------------------------------------------------
     * get all module translate entities by locale id
     * ----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return List
     */
    List<TranslationEntity> findAllByLocaleId(long id);

}
