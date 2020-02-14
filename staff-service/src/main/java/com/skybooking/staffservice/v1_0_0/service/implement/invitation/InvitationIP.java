package com.skybooking.staffservice.v1_0_0.service.implement.invitation;

import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
import com.skybooking.staffservice.exception.httpstatus.ConflictException;
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
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InvitationExpireRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.decrypt.AES;
import com.skybooking.staffservice.v1_0_0.util.email.EmailBean;
import com.skybooking.staffservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.staffservice.v1_0_0.util.notification.NotificationBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private GeneralBean general;

    @Autowired
    private StakeholderUserInvitationRP userInvRP;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private NotificationBean notificationBean;

    @Autowired
    private EmailBean emailBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List
     */
    public List<SkyuserDetailsRS> findSkyUsers() {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("Opp something went wrong", null);
        }

        List<SkyuserSearchTO> skyUsers = invitationNQ.listSkyuserByEmailOrPhone(request.getParameter("email"));
        List<SkyuserDetailsRS> skyUsersRS = new ArrayList<>();

        for (SkyuserSearchTO skyUser : skyUsers) {
            SkyuserDetailsRS skyUserRS = new SkyuserDetailsRS();

            BeanUtils.copyProperties(skyUser, skyUserRS);
            skyUserRS.setPhotoMedium(
                    environment.getProperty("spring.awsImageUrl.profile.url_larg") + skyUser.getPhoto());
            skyUsersRS.add(skyUserRS);
        }

        return skyUsersRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return
     */
    public void inviteSkyUser(SkyuserIdStaffRQ inviteStaff) {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        UserEntity skyuser = userRepository.findById(inviteStaff.getUserId());

        if (skyuser == null) {
            throw new BadRequestException("sth_w_w", null);
        }

        String b = companyHasUserRP.existUserCompany(skyuser.getStakeHolderUser().getId());

        List<RoleTO> roleTO = invitationNQ.listOrFindRole("byRole", inviteStaff.getSkyuserRole());

        if (b.equals("1") || roleTO.size() == 0) {
            throw new BadRequestException("sth_w_w", null);
        }

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        general.addInvitation(skyuser.getStakeHolderUser().getId(), companyId, null, "hasAcc");

        notificationBean.sendNotiSkyuser(skyuser.getStakeHolderUser().getId());

        StakeHolderUserEntity stakeHolderUser = skyuser.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        Map<String, Object> mailData = emailBean.mailData(skyuser.getEmail(), fullName, 0, "invitation_is_waiting_for_your_confirmation");
        emailBean.sendEmailSMS("Invitation", mailData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no existing account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void inviteSkyUserNotExistsAcc(InviteStaffNoAccRQ inviteStaffNoAccRQ) {

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        checkExistInv(inviteStaffNoAccRQ);

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        StakeholderUserInvitationEntity stakeholderUser = general.addInvitation(null, companyId, inviteStaffNoAccRQ.getUsername(), "noAcc");

        Map<String, Object> mailData = emailBean.mailData(inviteStaffNoAccRQ.getUsername(), "", 0, "invitation_is_waiting_for_your_confirmation");
        mailData.put("dataEncrypt", this.encryptId(stakeholderUser.getId().toString()));

        emailBean.sendEmailSMS("Invitation", mailData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no existing account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void checkExistInv(InviteStaffNoAccRQ inviteStaffNoAccRQ) {

        StakeholderUserInvitationEntity userInv = userInvRP.findFirstByInviteTo(inviteStaffNoAccRQ.getUsername());
        if (userInv != null) {
            throw new ConflictException("inv_ald", null);
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
            throw new BadRequestException("sth_w_w", null);
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

        StakeholderUserInvitationEntity stakeholderUser = userInvRP.findFirstByInviteTo(inviteStaffNoAccRQ.getUsername());

        if (stakeholderUser == null) {
            throw new BadRequestException("sth_w_w", "");
        }

        Map<String, Object> mailData = emailBean.mailData(inviteStaffNoAccRQ.getUsername(), "", 0, "invitation_is_waiting_for_your_confirmation");
        mailData.put("dataEncrypt", this.encryptId(stakeholderUser.getId().toString()));
        emailBean.sendEmailSMS("Invitation again", mailData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check expiration link invitaion
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param InvitationExpireRQ
     * @Return HashMap<String, Boolean>
     */
    @Override
    public HashMap<String, Boolean> checkExpired(InvitationExpireRQ invitationExpireRQ) {

        int inviteId = Integer.valueOf(this.decryptId(invitationExpireRQ.getInviteKey()));

        StakeholderUserInvitationEntity userInvited = userInvRP.findStakeholderUserInvitationById(inviteId);

        boolean isExpire = this.expiredInvitation(userInvited);

        HashMap<String, Boolean> data = new HashMap<>();

        data.put("expired", isExpire);

        return data;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check expiration link invitaion
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param StakeholderUserInvitationEntity
     */
    private boolean expiredInvitation(StakeholderUserInvitationEntity userInvited) {

        if (userInvited == null) {
            throw new BadRequestException("sth_w_w", "");
        }

        Date currentDate = new Date();
        Timestamp currentTime = new Timestamp(currentDate.getTime());

        long diff = currentTime.getTime() - userInvited.getCreatedAt().getTime();

        int diffMin = (int) (diff / (60 * 1000));

        if (diffMin > 60) {
            return false;
        }
        return true;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encrypt id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String key
     */
    private String encryptId(String key) {
        final String secretKey = "sky-booking";

        return AES.encrypt(key, secretKey);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Decrypt id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String key
     */
    private String decryptId(String key) {
        final String secretKey = "sky-booking";

        return AES.decrypt(key, secretKey);
    }

}
