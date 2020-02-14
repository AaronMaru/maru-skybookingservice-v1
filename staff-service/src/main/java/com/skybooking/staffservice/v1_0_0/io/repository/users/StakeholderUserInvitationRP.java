package com.skybooking.staffservice.v1_0_0.io.repository.users;


import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderUserInvitationRP extends JpaRepository<StakeholderUserInvitationEntity, Long> {

    /**
     * Find email invited
     */
    StakeholderUserInvitationEntity findFirstByInviteTo(String username);

    StakeholderUserInvitationEntity findStakeholderUserInvitationById(Integer id);

    /**
     * Find email and delete
     */
    StakeholderUserInvitationEntity findByIdAndStakeholderCompanyId(Integer id, Long companyId);

    /**
     * Find by skyuserId and companyId
     */
    @Query(value = "SELECT * FROM stakeholder_user_invitations WHERE invite_stakeholder_user_id = ?1 AND invite_stakeholder_user_id IS NOT NULL AND stakeholder_company_id = ?2 LIMIT 1", nativeQuery = true)
    StakeholderUserInvitationEntity findByInviteStakeholderUserIdAndStakeholderCompanyId(Long skyuserId, Long companyId);

    /**
     * Find by invite to and companyId
     */
    StakeholderUserInvitationEntity findByInviteToAndStakeholderCompanyId(String invto, Long companyId);

}
