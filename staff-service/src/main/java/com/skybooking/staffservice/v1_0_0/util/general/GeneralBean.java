package com.skybooking.staffservice.v1_0_0.util.general;

import com.skybooking.staffservice.exception.httpstatus.ConflictException;
import com.skybooking.staffservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.staffservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneralBean {


    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private StakeholderUserInvitationRP invitationRP;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find a companyConstant
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyId
     * @Param skyuserId
     */
    public void addStaff(Long companyId, Long skyuserId, String role) {

        StakeholderUserHasCompanyEntity companyHasUser = new StakeholderUserHasCompanyEntity();
        companyHasUser.setStakeholderCompanyId(companyId);
        companyHasUser.setStakeholderUserId(skyuserId);
        companyHasUser.setSkyuserRole(role);
        companyHasUser.setStatus(2);

        companyHasUserRP.save(companyHasUser);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Add invitation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyId
     * @Param skyuserId
     * @Param invFrom
     * @Param invTo
     */
    public StakeholderUserInvitationEntity addInvitation(Long skyuserId, Long companyId, String role, String invTo, String acc) {

        StakeholderUserInvitationEntity exits = invitationRP.findByInviteStakeholderUserIdAndStakeholderCompanyId(skyuserId, companyId);

        if (acc.equals("noAcc")) {
            exits = invitationRP.findByInviteToAndStakeholderCompanyId(invTo, companyId);
        }

        if (exits != null) {
            throw new ConflictException("The user already invited", null);
        }

        StakeholderUserInvitationEntity invitation = new StakeholderUserInvitationEntity();

        invitation.setInviteStakeholderUserId(skyuserId);
        invitation.setStakeholderCompanyId(companyId);
        invitation.setInviteTo(invTo);
        invitation.setSkyuserRole(role);

        invitationRP.save(invitation);

        return invitation;
    }


}
