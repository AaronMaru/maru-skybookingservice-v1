package com.skybooking.skyflightservice.v1_0_0.io.repository.bookmark;

import com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark.FlightSavesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FlightSavesRP extends JpaRepository<FlightSavesEntity, Integer> {
    Stream<FlightSavesEntity> findAllByUserId(Integer userId);
    Stream<FlightSavesEntity> findAllByCompanyId(Integer companyId);
}
