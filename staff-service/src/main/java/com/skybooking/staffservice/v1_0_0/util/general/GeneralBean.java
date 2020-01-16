package com.skybooking.staffservice.v1_0_0.util.general;

import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
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
     * Find a company
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
    public void addInvitation(Long skyuserId, Long companyId, String invTo) {

        StakeholderUserInvitationEntity exits = invitationRP.findByInviteStakeholderUserIdAndStakeholderCompanyId(skyuserId, companyId);

        if (exits != null) {
            throw new BadRequestException("The user already invited", "");
        }

        StakeholderUserInvitationEntity invitation = new StakeholderUserInvitationEntity();

        invitation.setInviteStakeholderUserId(skyuserId);
        invitation.setStakeholderCompanyId(companyId);
        invitation.setInviteTo(invTo);

        invitationRP.save(invitation);

    }


}
