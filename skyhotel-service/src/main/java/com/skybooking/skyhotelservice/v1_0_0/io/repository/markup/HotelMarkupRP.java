package com.skybooking.skyhotelservice.v1_0_0.io.repository.markup;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.markup.HotelMarkupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelMarkupRP extends JpaRepository<HotelMarkupEntity, Long> {
    List<HotelMarkupEntity> getAllByType(String type);
}
