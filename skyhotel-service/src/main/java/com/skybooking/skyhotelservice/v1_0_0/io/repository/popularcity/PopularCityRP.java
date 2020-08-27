package com.skybooking.skyhotelservice.v1_0_0.io.repository.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.popularcity.HotelPopularCityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularCityRP extends JpaRepository<HotelPopularCityEntity, Long> {
}
