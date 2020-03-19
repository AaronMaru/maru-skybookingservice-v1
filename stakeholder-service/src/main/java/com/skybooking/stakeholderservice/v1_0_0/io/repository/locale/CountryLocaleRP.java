package com.skybooking.stakeholderservice.v1_0_0.io.repository.locale;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.CountryLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryLocaleRP extends JpaRepository<CountryLocaleEntity, Long> {

    @Query(value = "SELECT * FROM country_city_locale WHERE country_city_id = ?1 AND locale_id = ?2 LIMIT 1", nativeQuery = true)
    CountryLocaleEntity findCountryBylocale(Long countryId, Long localeId);

}
