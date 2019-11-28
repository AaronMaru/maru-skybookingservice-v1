package com.skybooking.stakeholderservice.v1_0_0.io.repository.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRP extends JpaRepository<PassengerEntity, Long> {
}
