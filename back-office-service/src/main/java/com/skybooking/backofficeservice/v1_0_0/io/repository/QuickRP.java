package com.skybooking.backofficeservice.v1_0_0.io.repository;

import com.skybooking.backofficeservice.v1_0_0.io.entity.QuickEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickRP extends JpaRepository<QuickEntity, Integer>{

    @Query(value = "SELECT * FROM  hotel_popular_city hpc  WHERE hpc.destination_code  = :destinationCode", nativeQuery = true)
    QuickEntity findQuickByCode(String destinationCode);

    @Query(value = "SELECT * FROM hotel_popular_city hpc  WHERE hpc.id  = :id", nativeQuery = true)
    QuickEntity findQuickById(Integer id);
}
