package com.skybooking.skyflightservice.v1_0_0.io.repository.activity;

import com.skybooking.skyflightservice.v1_0_0.io.entity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRP extends JpaRepository<ActivityEntity, Long> {
}
