package com.skybooking.skyflightservice.v1_0_0.io.repository.locale;

import com.skybooking.skyflightservice.v1_0_0.io.entity.locale.CountryLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryLocaleRP extends JpaRepository<CountryLocaleEntity, Long> {

}
