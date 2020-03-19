package com.skybooking.paymentservice.v1_0_0.io.repository.activity;

import com.skybooking.paymentservice.v1_0_0.io.enitity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRP extends JpaRepository<ActivityEntity, Long> {
}
