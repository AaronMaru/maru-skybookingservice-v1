package com.skybooking.stakeholderservice.v1_0_0.io.repository.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRP extends JpaRepository<PassengerEntity, Long> {

    Page<PassengerEntity> findByStakeHolderUserEntityAndCompanyIdNull(StakeHolderUserEntity skyuser, Pageable pageable);
    Page<PassengerEntity> findByStakeHolderUserEntityAndCompanyId(StakeHolderUserEntity skyuser, Pageable pageable, long companyId);

    @Query(value = "SELECT * FROM booking_passengers WHERE id = ?1 AND stakeholder_user_id = ?2 AND (CASE WHEN ?3 THEN stakeholder_company_id = ?3 ELSE stakeholder_company_id IS NULL END)", nativeQuery = true)
    PassengerEntity findPassenger(Long id, Long skyuserId, Long companyId);

    boolean existsByIdNumberAndIdType(String idNumber, int idType);
}
