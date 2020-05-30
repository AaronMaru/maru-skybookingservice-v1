package com.skybooking.staffservice.v1_0_0.service.implement.invitation;

import com.skybooking.staffservice.constant.NotificationConstant;
import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
import com.skybooking.staffservice.exception.httpstatus.ConflictException;
import com.skybooking.staffservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.staffservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.CompanyPlayerTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.PendingStaffEmailTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.RoleTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.SkyuserSearchTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.StaffNQ;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.staffservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.staffservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.staffservice.v1_0_0.service.interfaces.invitation.InvitationSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InvitationExpireRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.ResendPendingEmailRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.InviteNotifyRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.staffservice.v1_0_0.util.decrypt.AES;
import com.skybooking.staffservice.v1_0_0.util.email.EmailBean;
import com.skybooking.staffservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.staffservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

import static com.skybooking.staffservice.constant.MailStatusConstant.ADD_SKYUSER_TO_STAFF;
import static com.skybooking.staffservice.constant.MailStatusConstant.INVITATION_IS_WAITING_FOR_YOUR_CONFIRMATION;


@Service
public class InvitationIP implements InvitationSV {

    @Value("${spring.email.code}")
    private String MAIL_CODE;

    @Value("${link.expire}")
    private Long INVITATION_DURATION;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StaffNQ invitationNQ;

    @Autowired
    Environment environment;

    @Autowired
    private GeneralBean general;

    @Autowired
    private StakeholderUserInvitationRP userInvRP;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PushNotificationOptions notification;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private HeaderBean headerBean;


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
            throw new UnauthorizedException("sth_w_w", null);
        }

        List<SkyuserSearchTO> skyUsers = invitationNQ.listSkyuserByEmailOrPhone(request.getParameter("keyword") != null && !request.getParameter("keyword").equals("") ? request.getParameter("keyword") : "all");
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

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        ValidationCompanyId(filterRQ.getCompanyId());

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        var skyuser = stakeHolderUserRP.findById(inviteStaff.getUserId());

        if (skyuser.isEmpty()) {
            throw new BadRequestException("The user dose not exist", null);
        }

        String b = companyHasUserRP.existUserCompany(skyuser.get().getId());
        List<RoleTO> roleTO = invitationNQ.listOrFindRole("byRole", inviteStaff.getSkyuserRole());

        if (b.equals("1") || roleTO.size() == 0) {
            throw new BadRequestException("sth_w_w", null);
        }

        var invited = general.addInvitation(skyuser.get().getId(), filterRQ.getCompanyHeaderId(), inviteStaff.getSkyuserRole(), null, "hasAcc");

        Map<String, Object> mailData = emailBean.mailData((skyuser.get().getUserEntity().getEmail() == null) ? "" : skyuser.get().getUserEntity().getEmail(), "Customer", 0, ADD_SKYUSER_TO_STAFF);
        mailData.put("inviterName", "Layla");
        emailBean.sendEmailSMS("Invitation", mailData);

        sendNotifyToAllStaff(skyuser.get().getId(), filterRQ.getCompanyHeaderId(), invited);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no existing account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public void inviteSkyUserNotExistsAcc(InviteStaffNoAccRQ inviteStaffNoAccRQ) throws UnsupportedEncodingException {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        ValidationCompanyId(filterRQ.getCompanyId());

        String userType = jwtUtils.getClaim("userType", String.class);
        if (userType.equals("skyuser")) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        List<RoleTO> roleTO = invitationNQ.listOrFindRole("byRole", inviteStaffNoAccRQ.getSkyuserRole());
        if (roleTO.size() == 0) {
            throw new BadRequestException("sth_w_w", null);
        }

        checkExistInv(inviteStaffNoAccRQ);

        var stakeholderUser = general.addInvitation(null, filterRQ.getCompanyHeaderId(), inviteStaffNoAccRQ.getSkyuserRole(), inviteStaffNoAccRQ.getUsername(), "noAcc");

        Map<String, Object> mailData = emailBean.mailData(inviteStaffNoAccRQ.getUsername(), "Customer", 0, INVITATION_IS_WAITING_FOR_YOUR_CONFIRMATION);
        mailData.put("dataEncrypt", this.encode(this.encryptId(stakeholderUser.getId().toString())));

        mailData.put("inviterName", this.getInviterName(filterRQ.getCompanyHeaderId()));

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

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        filterRQ.setAction(checkSearchOrFilter());
        List<PendingStaffEmailTO> emailsTO = invitationNQ.listPendingEmailStaff(filterRQ.getCompanyHeaderId(),
                filterRQ.getKeyword(),
                filterRQ.getStartDate(),
                filterRQ.getEndDate(),
                filterRQ.getAction());

        List<PendingEmailStaffRS> emailsRS = new ArrayList<>();

        for (PendingStaffEmailTO emailTO : emailsTO) {
            PendingEmailStaffRS emailRS = new PendingEmailStaffRS();
            BeanUtils.copyProperties(emailTO, emailRS);

            emailRS.setInvitedAt(dateTimeBean.convertDateTime(emailTO.getInvitedAt()));

            emailsRS.add(emailRS);
        }

        return emailsRS;

    }
    private String checkSearchOrFilter() {

        if (request.getParameter("startDate") != null || request.getParameter("endDate") != null
        ) {

            return "FILTER";

        } else if(request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")) {

            return "SEARCH";

        }

        return "SEARCH";
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove pending email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public void removePendingEmail(Integer id) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());

        StakeholderUserInvitationEntity emailRemove = userInvRP.findByIdAndStakeholderCompanyId(id, filterRQ.getCompanyHeaderId());

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
    public void resendPendingEmail(ResendPendingEmailRQ resendPendingEmail) throws UnsupportedEncodingException {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());

        StakeholderUserInvitationEntity userInvited = userInvRP.findByIdAndStakeholderCompanyId(resendPendingEmail.getInviteId(), filterRQ.getCompanyHeaderId());

        if (userInvited == null) {
            throw new BadRequestException("inv_usr_list", null);
        }

        userInvited.setUpdatedAt(null);
        userInvRP.save(userInvited);

        Map<String, Object> mailData = emailBean.mailData(userInvited.getInviteTo(), "Customer", 0, INVITATION_IS_WAITING_FOR_YOUR_CONFIRMATION);
        mailData.put("dataEncrypt", this.encode(this.encryptId(userInvited.getId().toString())));

        mailData.put("inviterName", this.getInviterName(filterRQ.getCompanyHeaderId()));

        if (userInvited.getInviteTo() == null) {
            var skyUser = stakeHolderUserRP.findById(userInvited.getInviteFrom());
            HashMap<String, Object> scriptData = new HashMap<>();
            scriptData.put("skyuserId", skyUser.get().getId().toString());
            scriptData.put("scriptKey", "skyowner_add_staffs");
            scriptData.put("type", NotificationConstant.ADD_SKYUSER);
            notification.sendMessageToUsers(scriptData);
        } else {
            emailBean.sendEmailSMS("Invitation again", mailData);
        }

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
    public HashMap<String, Object> checkExpired(InvitationExpireRQ invitationExpireRQ) throws UnsupportedEncodingException {

        int inviteId = Integer.parseInt(this.decryptId(this.decode(invitationExpireRQ.getInviteKey())));

        StakeholderUserInvitationEntity userInvited = userInvRP.findStakeholderUserInvitationById(inviteId);

        boolean isExpire = this.expiredInvitation(userInvited);

        HashMap<String, Object> data = new HashMap<>();

        data.put("expired", isExpire);

        if (isExpire) {
            data.put("email", userInvited.getInviteTo());
        }

        return data;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check expiration link invitation one hour
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

        long diff = currentTime.getTime() - userInvited.getUpdatedAt().getTime();

        int diffMin = (int) (diff / (60 * 1000));

        return diffMin <= INVITATION_DURATION;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encrypt id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String key
     */
    private String encryptId(String key) {
        return AES.encrypt(key, MAIL_CODE);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Decrypt id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String key
     */
    private String decryptId(String key) {
        return AES.decrypt(key, MAIL_CODE);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Encode encryptId
     * Reason cuz of encrypt get special character
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String value
     */
    private String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    String getInviterName(Long companyId) {

        var companyOwner = companyHasUserRP.findOwnerCompany(companyId);

        var stakeHolderUser = stakeHolderUserRP.findById(companyOwner.getStakeholderUserId()).orElse(null);
        String inviterName = "";
        if (stakeHolderUser != null) {
            inviterName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        }

        return inviterName;
    }

    //This method just temporary
    //Validation
    public String ValidationCompanyId(Long cId) {

        if ((request.getHeader("X-CompanyId") == null || request.getHeader("X-CompanyId").isEmpty())) {
            throw new BadRequestException("sth_w_w", null);
        }

        Long companyId = (long) Integer.parseInt(request.getHeader("X-CompanyId"));

        if (companyId == 0) {
            return "skyuser";
        }

        if (!companyId.equals(cId)) {
            throw new BadRequestException("sth_w_w", null);
        }

        return "company";
    }


    private void sendNotifyToAllStaff(Long skyuserId, Long companyId, StakeholderUserInvitationEntity invitation) {
        var playerId = notificationNQ.stakeholderPlayerId(skyuserId);

        var skyuser = stakeHolderUserRP.findById(skyuserId).get();

        List<CompanyPlayerTO> players = notificationNQ.companyPlayerId(companyId, "admin");
        LinkedHashSet<InviteNotifyRS> storeNotify = new LinkedHashSet<>();

        players.forEach(data -> {
            InviteNotifyRS inviteNotifyRS = companyPlayer((long) data.getSkyuserId(), data.getPlayerId(), null);
            storeNotify.add(inviteNotifyRS);
        });
        InviteNotifyRS inviteNotifyRS = companyPlayer(skyuser.getId(), (playerId.size() > 0) ? playerId.get(0).getPlayerId() : "", "has");
        storeNotify.add(inviteNotifyRS);

        storeNotify.forEach(data -> {
            HashMap<String, Object> scriptData = new HashMap<>();
            if (data.getNoCompanyId() != null) {
                scriptData.put("addSkyuser", "");
                scriptData.put("inviteId", invitation.getId());
            }
            scriptData.put("scriptId", data.getScriptId());
            scriptData.put("skyuserId", data.getSkyuserId());
            scriptData.put("scriptKey", "skyowner_add_staffs");
            scriptData.put("type", NotificationConstant.ADD_SKYUSER);

            notification.addNotificationHisitory(scriptData);
        });
    }

    public InviteNotifyRS companyPlayer(Long skyuserId, String playerId, String option) {
        InviteNotifyRS inviteNotifyRS = new InviteNotifyRS();
        HashMap<String, Object> scriptData = new HashMap<>();
        scriptData.put("playerId", playerId);
        scriptData.put("noInsert", "");
        scriptData.put("scriptKey", "skyowner_add_staffs");
        notification.sendMessageToUsers(scriptData);

        ScriptingTO scriptingTO = notificationNQ.scripting(headerBean.getLocalizationId(), "skyowner_add_staffs");
        inviteNotifyRS.setSkyuserId(skyuserId);
        inviteNotifyRS.setScriptId(scriptingTO.getScriptId());
        if (option != null) {
            inviteNotifyRS.setNoCompanyId("addSkyuser");
        }
        return inviteNotifyRS;
    }


}
