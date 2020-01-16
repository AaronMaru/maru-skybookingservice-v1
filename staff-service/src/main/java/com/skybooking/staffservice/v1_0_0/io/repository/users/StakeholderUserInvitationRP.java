package com.skybooking.staffservice.v1_0_0.io.repository.users;


import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderUserInvitationRP extends JpaRepository<StakeholderUserInvitationEntity, Long> {


    /**
     * Find email invited
     */
    StakeholderUserInvitationEntity findFirstByInviteTo(String username);


    /**
     * Find email and delete
     */
    StakeholderUserInvitationEntity findByIdAndStakeholderCompanyId(Integer id, Long companyId);

    /**
     * Find by skyuserId and companyId
     */
    StakeholderUserInvitationEntity findByInviteStakeholderUserIdAndStakeholderCompanyId(Long skyuserId, Long companyId);
}
