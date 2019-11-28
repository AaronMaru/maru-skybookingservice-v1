package com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightInfoRP extends JpaRepository<FlightInfoEntity, Long> {

    FlightInfoEntity findFirstByAirlineCode(String airlineCode);

}
