package com.skybooking.stakeholderservice.v1_0_0.io.repository.country;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.country.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRP extends JpaRepository<LocationEntity, Long> {
    LocationEntity findByLocationableId(Long id);
}
