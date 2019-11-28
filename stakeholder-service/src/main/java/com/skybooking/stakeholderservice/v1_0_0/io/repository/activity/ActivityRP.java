package com.skybooking.stakeholderservice.v1_0_0.io.repository.activity;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRP extends JpaRepository<ActivityEntity, Long> {
}
