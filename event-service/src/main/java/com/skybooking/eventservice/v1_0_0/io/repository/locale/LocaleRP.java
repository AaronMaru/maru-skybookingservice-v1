package com.skybooking.eventservice.v1_0_0.io.repository.locale;


import com.skybooking.eventservice.v1_0_0.io.entity.locale.LocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocaleRP extends JpaRepository<LocaleEntity, Long> {

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * get locale by locale code
     * ----------------------------------------------------------------------------------------------------------------
     *
     * @param locale
     * @return LocaleEntity
     */
    LocaleEntity findLocaleByLocale(String locale);
}
