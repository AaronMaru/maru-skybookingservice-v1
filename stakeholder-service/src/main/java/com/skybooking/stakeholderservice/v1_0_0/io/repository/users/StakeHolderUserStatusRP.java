package com.skybooking.stakeholderservice.v1_0_0.io.repository.users;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StakeHolderUserStatusRP extends JpaRepository<StakeholderUserStatusEntity, Long> {
}
