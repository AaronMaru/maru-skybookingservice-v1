package com.skybooking.skyflightservice.v1_0_0.io.repository.country;

import com.skybooking.skyflightservice.v1_0_0.io.entity.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRP extends JpaRepository<CountryEntity, Long> {

    @Query(value = "SELECT * FROM country_city WHERE type = 'country' ", nativeQuery = true)
    List<CountryEntity> findAllCountry();

}
