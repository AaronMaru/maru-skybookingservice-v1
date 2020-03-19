package com.skybooking.skyflightservice.v1_0_0.io.repository.meal;

import com.skybooking.skyflightservice.v1_0_0.io.entity.meal.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRP extends JpaRepository<MealEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM meals WHERE status = 1")
    List<MealEntity> findAll();

}
