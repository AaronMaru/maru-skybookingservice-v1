package com.skybooking.skyhotelservice.v1_0_0.io.repository.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.popularcity.HotelPopularCityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularCityRP extends JpaRepository<HotelPopularCityEntity, Long> {
    List<HotelPopularCityEntity> findByType(String type);

}
