package com.skybooking.stakeholderservice.v1_0_0.io.repository.users;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderUserInvitationRP extends JpaRepository<StakeholderUserInvitationEntity, Long> {


    /**
     * Find email invited
     */
    StakeholderUserInvitationEntity findFirstByInviteFrom(String username);


    /**
     * Find email and delete
     */
    StakeholderUserInvitationEntity findByIdAndStakeholderCompanyId(Integer id, Long companyId);



}
