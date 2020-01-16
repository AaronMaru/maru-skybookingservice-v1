package com.skybooking.paymentservice.v1_0_0.io.repository;


import com.skybooking.paymentservice.v1_0_0.io.enitity.PaymentEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRP extends JpaRepository<PaymentEnitity, Long> {

    @Query(value = "SELECT * FROM payment_ipayhths pi WHERE pi.booking_code = :bookingCode ORDER BY pi.id DESC LIMIT 1", nativeQuery = true)
    PaymentEnitity getUrlToken(@Param("bookingCode") String bookingCode);


}
