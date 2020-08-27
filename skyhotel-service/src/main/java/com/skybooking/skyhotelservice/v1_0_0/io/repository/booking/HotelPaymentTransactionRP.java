package com.skybooking.skyhotelservice.v1_0_0.io.repository.booking;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelPaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelPaymentTransactionRP extends JpaRepository<HotelPaymentTransactionEntity, Long> {

    @Query(value = "SELECT transaction_id FROM hotel_payment_transaction ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLatestRow();

    HotelPaymentTransactionEntity findByBookingIdAndStatus(Long bookingId, int status);

}
