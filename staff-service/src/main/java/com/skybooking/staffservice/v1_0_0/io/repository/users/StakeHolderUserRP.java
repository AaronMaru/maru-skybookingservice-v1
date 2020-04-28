package com.skybooking.staffservice.v1_0_0.io.repository.users;

import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeHolderUserRP extends JpaRepository<StakeHolderUserEntity, Long> {

}
