package com.skybooking.skyflightservice.v1_0_0.io.repository.baggages;

import com.skybooking.skyflightservice.v1_0_0.io.entity.baggages.BookingBaggageAllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingBaggageAllowanceRP extends JpaRepository<BookingBaggageAllowanceEntity, Integer> {
}
