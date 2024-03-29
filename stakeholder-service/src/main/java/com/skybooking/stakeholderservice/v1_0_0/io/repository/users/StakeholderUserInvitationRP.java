package com.skybooking.stakeholderservice.v1_0_0.io.repository.users;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StakeholderUserInvitationRP extends JpaRepository<StakeholderUserInvitationEntity, Long> {


    /**
     * Find email invited
     */
    StakeholderUserInvitationEntity findFirstByInviteTo(String username);

    StakeholderUserInvitationEntity findByInviteStakeholderUserIdAndInviteToIsNotNull(Long skyuserId);

    /**
     * Find by skyuser id
     */
    List<StakeholderUserInvitationEntity> findByInviteStakeholderUserIdAndStatus(Long skyuserId, Integer status);


    /**
     * Find by id and skyuserId
     */
    StakeholderUserInvitationEntity findByIdAndInviteStakeholderUserId(Long id, Long skyuserId);

}
