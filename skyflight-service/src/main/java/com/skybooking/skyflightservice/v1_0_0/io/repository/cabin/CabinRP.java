package com.skybooking.skyflightservice.v1_0_0.io.repository.cabin;

import com.skybooking.skyflightservice.v1_0_0.io.entity.cabin.CabinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinRP extends JpaRepository<CabinEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM cabins WHERE status = 1")
    List<CabinEntity> findAll();

}
