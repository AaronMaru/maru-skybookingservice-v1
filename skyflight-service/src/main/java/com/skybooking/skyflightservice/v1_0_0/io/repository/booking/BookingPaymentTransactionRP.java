package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingPaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPaymentTransactionRP extends JpaRepository<BookingPaymentTransactionEntity, Integer> {

    @Query(value = "SELECT transaction_id FROM booking_payment_transactions WHERE status = 1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLatestRow();
}
