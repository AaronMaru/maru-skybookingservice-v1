package com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRP extends JpaRepository<TranslationEntity, Long> {

    @Query(value = "SELECT * FROM frontend_translation as ft LEFT JOIN frontend_locales as fl ON ft.locale_id = fl.id WHERE ft.module = 'api' AND ft.key = ?1 AND fl.locale = ?2", nativeQuery = true)
    TranslationEntity findByKey(String key, String lang);

    List<TranslationEntity> findAllByLocaleId(long id);

    @Query(value = "SELECT * FROM frontend_translation WHERE module = :key AND locale_id = :lang", nativeQuery = true)
    List<TranslationEntity> findByModule(long lang, String key);
}
