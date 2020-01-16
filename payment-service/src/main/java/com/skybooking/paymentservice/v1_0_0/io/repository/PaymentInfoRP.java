package com.skybooking.paymentservice.v1_0_0.io.repository;

import com.skybooking.paymentservice.v1_0_0.io.enitity.PaymentInfoEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRP extends JpaRepository<PaymentInfoEnitity, Long> {

    @Query(value = "SELECT * FROM dis_payment_method_details pmd WHERE pmd.`code` = :paymentCode AND pmd.`status` = 1 LIMIT 1", nativeQuery = true)
    PaymentInfoEnitity getPaymentInfo(@Param("paymentCode") String paymentCode);

}
