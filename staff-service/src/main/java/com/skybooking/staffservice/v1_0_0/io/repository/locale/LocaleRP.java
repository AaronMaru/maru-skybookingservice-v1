package com.skybooking.staffservice.v1_0_0.io.repository.locale;

import com.skybooking.staffservice.v1_0_0.io.enitity.locale.LocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocaleRP extends JpaRepository<LocaleEntity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM frontend_locales WHERE locale = ?1 AND status = 1", nativeQuery = true)
    String existsLocale(String locale);


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
