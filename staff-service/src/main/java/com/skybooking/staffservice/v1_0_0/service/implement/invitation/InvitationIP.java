package com.skybooking.staffservice.v1_0_0.service.implement.invitation;

import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
import com.skybooking.staffservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.staffservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.PendingStaffEmailTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.RoleTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.SkyuserSearchTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.StaffSvNQ;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.staffservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.staffservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.staffservice.v1_0_0.service.interfaces.invitation.InvitationSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;
import com.skybooking.staffservice.v1_0_0.util.ApiBean;
import com.skybooking.staffservice.v1_0_0.util.GeneralBean;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.cls.Duplicate;
import com.skybooking.staffservice.v1_0_0.util.notification.NotificationBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationIP implements InvitationSV {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StaffSvNQ invitationNQ;

    @Autowired
    Environment environment;

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private UserBean userBean;

    @Autowired
    private GeneralBean general;

    @Autowired
    private StakeholderUserInvitationRP userInvRP;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private NotificationBean notificationBean;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    public List<SkyuserDetailsRS> findSkyusers() {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("Opp something went wrong", "");
        }

        List<SkyuserSearchTO> skyusers = invitationNQ.listSkyuserByEmailOrPhone(request.getParameter("email"));
        List<SkyuserDetailsRS> skyusersRS = new ArrayList<>();

        for (SkyuserSearchTO skyuser : skyusers) {
            SkyuserDetailsRS skyuserRS = new SkyuserDetailsRS();

            BeanUtils.copyProperties(skyuser, skyuserRS);
            skyuserRS.setPhotoMedium(
                    environment.getProperty("spring.awsImageUrl.profile.url_larg") + skyuser.getPhoto());
            skyusersRS.add(skyuserRS);
        }

        return skyusersRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return
     */
    public void invSkyuser(SkyuserIdStaffRQ inviteStaff) {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        UserEntity skyuser = userRepository.findById(inviteStaff.getUserId());

        if (skyuser == null) {
            throw new BadRequestException("sth_w_w", "");
        }

        String b = companyHasUserRP.existUserCompany(skyuser.getStakeHolderUser().getId());

        List<RoleTO> roleTO = invitationNQ.listOrFindRole("byRole", inviteStaff.getSkyuserRole());

        if (b.equals("1") || roleTO.size() == 0) {
            throw new BadRequestException("sth_w_w", "");
        }

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        general.addStaff(companyId, skyuser.getStakeHolderUser().getId(), roleTO.get(0).getUserType());

        notificationBean.sendNotiSkyuser(skyuser.getStakeHolderUser().getId());

        StakeHolderUserEntity stakeHolderUser = skyuser.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        apiBean.sendEmailSMS(skyuser.getEmail(), "Invitation",
                Duplicate.mailTemplateData(fullName, 0, "invite-skyowner"));

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no existing account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void invSkyuserNotExistsAcc(InviteStaffNoAccRQ inviteStaffNoAccRQ) {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        checkExistInv(inviteStaffNoAccRQ);

        StakeholderUserInvitationEntity userInv = new StakeholderUserInvitationEntity();

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        userInv.setStakeholderCompanyId(companyId);
        userInv.setInviteFrom(inviteStaffNoAccRQ.getUsername());

        userInvRP.save(userInv);

        apiBean.sendEmailSMS(inviteStaffNoAccRQ.getUsername(), "Invitation",
                Duplicate.mailTemplateData("", 0, "invite-nonskyowner"));

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no existing account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void checkExistInv(InviteStaffNoAccRQ inviteStaffNoAccRQ) {

        StakeholderUserInvitationEntity userInv = userInvRP.findFirstByInviteFrom(inviteStaffNoAccRQ.getUsername());

        if (userInv != null) {
            throw new BadRequestException("inv_ald", "");
        }

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * List pending email staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public List<PendingEmailStaffRS> getPendingEmail() {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        List<PendingStaffEmailTO> emailsTO = invitationNQ.listPendingEmailStaff(companyId);

        List<PendingEmailStaffRS> emailsRS = new ArrayList<>();

        for (PendingStaffEmailTO emailTO : emailsTO) {
            PendingEmailStaffRS emailRS = new PendingEmailStaffRS();
            BeanUtils.copyProperties(emailTO, emailRS);

            emailsRS.add(emailRS);
        }

        return emailsRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove pending email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public void removePendingEmail(Integer id) {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        StakeholderUserInvitationEntity emailRemove = userInvRP.findByIdAndStakeholderCompanyId(id, companyId);

        if (emailRemove == null) {
            throw new BadRequestException("sth_w_w", "");
        }

        userInvRP.delete(emailRemove);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend pending email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void resendPendingEmail(InviteStaffNoAccRQ inviteStaffNoAccRQ) {
        apiBean.sendEmailSMS(inviteStaffNoAccRQ.getUsername(), "Invitation again",
                Duplicate.mailTemplateData("", 0, "invite-nonskyowner"));
    }

}
