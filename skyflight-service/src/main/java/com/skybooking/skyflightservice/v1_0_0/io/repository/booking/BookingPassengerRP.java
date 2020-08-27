package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingPassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingPassengerRP extends JpaRepository<BookingPassengerEntity, Integer> {

    @Query(value = "SELECT * FROM booking_passengers WHERE stakeholder_user_id = ?", nativeQuery = true)
    List<BookingPassengerEntity> retrieveByUser(Integer id);

    @Query(value = "SELECT * FROM booking_passengers WHERE stakeholder_company_id = ?", nativeQuery = true)
    List<BookingPassengerEntity> retrieveByOwner(Integer id);

}
