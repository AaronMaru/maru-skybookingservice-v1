package com.skybooking.skyflightservice.v1_0_0.io.repository.bookmark;

import com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark.FlightSaveOriginDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FlightSaveOriginDestinationRP extends JpaRepository<FlightSaveOriginDestinationEntity, Integer> {
    Stream<FlightSaveOriginDestinationEntity> findAllByFlightSaveId(Integer flightSavedId);
}
