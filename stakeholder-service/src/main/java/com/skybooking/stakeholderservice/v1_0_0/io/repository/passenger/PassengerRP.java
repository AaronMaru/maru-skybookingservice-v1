package com.skybooking.stakeholderservice.v1_0_0.io.repository.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRP extends JpaRepository<PassengerEntity, Long> {

    @Query(value = "SELECT * FROM booking_passengers WHERE id = ?1 AND (CASE WHEN ?2 THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL END)", nativeQuery = true)
    PassengerEntity findPassenger(Long id, Long companyId);

    @Query(value = "SELECT * FROM booking_passengers WHERE stakeholder_user_id = :skyuserId AND id_number = :idNumber AND id_type = :idType AND stakeholder_company_id IS NULL", nativeQuery = true)
    PassengerEntity checkExistPassengerSkyUser(Long skyuserId, String idNumber, Integer idType);

    @Query(value = "SELECT * FROM booking_passengers WHERE stakeholder_user_id = :skyuserId AND id_number = :idNumber AND id_type = :idType AND stakeholder_company_id = :companyId", nativeQuery = true)
    PassengerEntity checkExistPassengerSkyOwner(Long skyuserId, String idNumber, Integer idType, Long companyId);

}
