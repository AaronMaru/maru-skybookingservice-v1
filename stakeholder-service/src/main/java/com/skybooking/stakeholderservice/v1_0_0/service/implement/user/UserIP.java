package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.ForbiddenException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserLanguageEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.CountryRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.OauthUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserLanguageRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRefreshRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.*;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.skybooking.stakeholderservice.constant.MailStatusConstant.*;

@Service
public class UserIP implements UserSV {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private UserBean userBean;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private Environment environment;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private VerifyUserRP verifyRepository;

    @Autowired
    private SkyownerBean skyownerBean;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private StakeholderUserInvitationRP invitationsRP;

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private VerifyUserRP verifyRP;

    @Autowired
    private OauthUserRP oauthUserRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CountryRP countryRP;

    @Autowired
    private StakeholderUserInvitationRP stkInvitationRP;

    @Autowired
    private PushNotificationOptions notification;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private UserLanguageRP userLanguageRP;

    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get User Details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return userDetailDao
     */
    public UserDetailsRS getUser() {

        UserEntity user = userBean.getUserPrincipal();

        UserDetailsRS userDetailRS = new UserDetailsRS();
        TokenRS token = new TokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, token), userDetailRS);

        return userDetailRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get User Details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return userDetailDao
     */
    public UserDetailsRS updateProfile(ProfileRQ profileRQ, MultipartFile multipartFile) {

        UserEntity user = userBean.getUserPrincipal();

        generalBean.errorMultipart(multipartFile);

        StakeHolderUserEntity stkHolder = user.getStakeHolderUser();

        if (profileRQ.getFirstName() != null) {
            stkHolder.setFirstName(profileRQ.getFirstName());
        }
        if (profileRQ.getLastName() != null) {
            stkHolder.setLastName(profileRQ.getLastName());
        }
        if (profileRQ.getNationality() != null) {
            if (countryRP.existsIso(profileRQ.getNationality()) == null) {
                throw new BadRequestException("not_found", null);
            }
            stkHolder.setNationality(profileRQ.getNationality());
        }

        if (profileRQ.getAddress() != null) {
            apiBean.updateContact(profileRQ.getAddress(), null, stkHolder, "a", "address");
        }

        if (profileRQ.getGender() != null) {
            stkHolder.setGender(profileRQ.getGender());
        }
        if (profileRQ.getDateOfBirth() != null) {
            stkHolder.setDateOfBirth(profileRQ.getDateOfBirth());
        }

        user.setStakeHolderUser(stkHolder);
        if (multipartFile != null) {
            String fileName = userBean.uploadFileForm(multipartFile, "/profile", "/profile/_thumbnail");
            stkHolder.setPhoto(fileName);
        }

        userRepository.save(user);

        UserDetailsRS userDetailsRS = new UserDetailsRS();
        TokenRS token = new TokenRS();
        BeanUtils.copyProperties(userBean.userFields(user, token), userDetailsRS);

        logger.activities(ActivityLoggingBean.Action.UPDATE_STAKE_USER, user);

        return userDetailsRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * User change password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param pwdRequest
     */
    public void changePassword(ChangePasswordRQ passwordRQ) {

        UserEntity user = userBean.getUserPrincipal();

        if (user.getProviderId() != null) {
            throw new BadRequestException("sth_w_w", null);
        }

        Boolean b = pwdEncoder.matches(passwordRQ.getOldPassword(), user.getPassword());

        if (!b) {
            throw new BadRequestException("incrt_pwd", null);
        }
        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));
        userRepository.save(user);

        userBean.senNotify(user.getStakeHolderUser().getId(), "skyuser_change_new_password");

        logger.activities(ActivityLoggingBean.Action.CHANGE_PASSWORD, user);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param passwordRQ
     */
    public UserDetailsTokenRS resetPassword(ResetPasswordRQ passwordRQ, HttpHeaders httpHeaders) {

        String receiver = userBean.getUsername(passwordRQ.getUsername(), passwordRQ.getCode());

        UserEntity user = userRepository.findByEmailOrPhone(passwordRQ.getUsername(), passwordRQ.getCode().replaceAll(" ", ""));

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        StakeHolderUserEntity skyuser = user.getStakeHolderUser();
        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(passwordRQ.getToken(),
            Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        generalBean.expiredInvalidToken(verifyUserEntity, passwordRQ.getToken());

        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));
        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.RESET_PASSWORD, user);

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, 0, PASSWORD_RESET_SUCCESSFULLY);

        emailBean.sendEmailSMS("success-reset-password", mailData);
        userBean.senNotify(user.getStakeHolderUser().getId(), "skyuser_forget_and_create_new_password");

        UserDetailsTokenRS userDetailsTokenRS = userBean.userDetailByLogin(user, passwordRQ.getUsername(), passwordRQ.getNewPassword());
        userBean.forceDeviceLogout(user.getId());
        userBean.storeUserTokenLastLogin(userDetailsTokenRS.getToken(), user);

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reset password (For mobile)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param passwordRQ
     */
    public UserDetailsTokenRS resetPasswordMobile(ResetPasswordMobileRQ resetPasswordMobileRQ) {

        String username = userBean.getUsername(resetPasswordMobileRQ.getUsername(), resetPasswordMobileRQ.getCode());

        var verify = verifyRP.findByUsernameAndVerifiedAndStatus(username, 1, Integer.parseInt(environment.getProperty("spring.verifyStatus.verifyUserAppReset")));
        if (verify == null) {
            throw new BadRequestException("sth_w_w", null);
        }

        UserEntity user = userRepository.findByEmailOrPhone(resetPasswordMobileRQ.getUsername(), resetPasswordMobileRQ.getCode());
        if (user == null) {
            throw new UnauthorizedException("unauthorized", null);
        }

        user.setPassword(pwdEncoder.encode(resetPasswordMobileRQ.getNewPassword()));
        verify.setVerified(2);

        try {
            verifyRP.save(verify);
            userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }

        UserDetailsTokenRS userDetailsTokenRS = userBean.userDetailByLogin(user, resetPasswordMobileRQ.getUsername(), resetPasswordMobileRQ.getNewPassword());

        userBean.forceDeviceLogout(user.getId());
        userBean.storeUserTokenLastLogin(userDetailsTokenRS.getToken(), user);
        userBean.senNotify(user.getStakeHolderUser().getId(), "skyuser_forget_and_create_new_password");

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public void updateContact(UpdateContactRQ contactRQ) {

        UserEntity user = userBean.getUserPrincipal();

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(contactRQ.getToken().replaceAll(" ", ""),
            Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        generalBean.expiredInvalidToken(verifyUserEntity, contactRQ.getToken().replaceAll(" ", ""));

        if (NumberUtils.isNumber(contactRQ.getUsername())) {

            user.setPhone(contactRQ.getUsername());
            user.setCode(contactRQ.getCode());
            apiBean.updateContact(contactRQ.getUsername(), contactRQ.getCode(), user.getStakeHolderUser(), "p", null);

        } else if (EmailValidator.getInstance().isValid(contactRQ.getUsername())) {
            user.setEmail(contactRQ.getUsername());
            apiBean.updateContact(contactRQ.getUsername(), contactRQ.getCode(), user.getStakeHolderUser(), "e", null);
        } else {
            throw new BadRequestException("data_invalid", contactRQ.getUsername());
        }

        userRepository.save(user);
        updateExistsEmailInv(user.getStakeHolderUser().getId(), user.getEmail());

        logger.activities(ActivityLoggingBean.Action.UPDATE_CONTACT, user);

    }

    private void updateExistsEmailInv(Long skyuserId, String email) {
        var invSky = stkInvitationRP.findByInviteStakeholderUserIdAndInviteToIsNotNull(skyuserId);
        if (invSky != null) {
            invSky.setInviteTo(email);
            stkInvitationRP.save(invSky);
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to update contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param updateContactRequest
     */
    public int sendCodeUpdateContact(UpdateContactRQ contactRQ) {

        UserEntity user = userBean.getUserPrincipal();

        if (user.getProviderId() != null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        Boolean b = pwdEncoder.matches(contactRQ.getPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", null);
        }

        // need to add httpstatus conflex
        generalBean.expireRequest(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));

        int code = apiBean.createVerifyCode(user,
            Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")), null);
        String fullName = user.getStakeHolderUser().getFirstName() + " " + user.getStakeHolderUser().getLastName();
        String receiver = NumberUtils.isNumber(contactRQ.getUsername())
            ? contactRQ.getCode() + contactRQ.getUsername().replaceFirst("^0+(?!$)", "")
            : contactRQ.getUsername();

        userRepository.save(user);

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, code, REQUEST_CHANGE_EMAIL);
        emailBean.sendEmailSMS("send-login", mailData);

        return code;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param deactiveAccount
     */
    public void deactiveAccount(DeactiveAccountRQ accountRQ) {

        UserEntity user = userBean.getUserPrincipal();

        Boolean b = pwdEncoder.matches(accountRQ.getPassword(), user.getPassword());

        if (!b) {
            throw new ForbiddenException("incrt_pwd", null);
        }

        user.getStakeHolderUser().setStatus(0);
        userRepository.save(user);

        apiBean.storeUserStatus(user, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")),
            "had_deact");

        String receiver = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        logger.activities(ActivityLoggingBean.Action.DEACTIVE_USER, user);

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, 0, ACCOUNT_DEACTIVATED_SUCCESSFULLY);
        emailBean.sendEmailSMS("deactive-account", mailData);

    }


    public void changeLanguage(ChangeLanguageRQ changeLanguageRQ) {

        UserEntity user = userBean.getUserPrincipal();
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        var userLanguage = userLanguageRP.findBySkyuserIdAndDeviceIdAndCompanyIdIsNull(user.getStakeHolderUser().getId(), changeLanguageRQ.getDeviceId());
        if (filterRQ.getCompanyHeaderId() != null) {
            userLanguage = userLanguageRP.findBySkyuserIdAndCompanyIdAndDeviceId(user.getStakeHolderUser().getId(), filterRQ.getCompanyHeaderId(), changeLanguageRQ.getDeviceId());
        }
        if (userLanguage == null) {
            userLanguage = new UserLanguageEntity();
        }

        userLanguage.setLocaleKey(headerBean.getLocalization());
        userLanguage.setCompanyId(filterRQ.getCompanyHeaderId());
        userLanguage.setDeviceId(changeLanguageRQ.getDeviceId());
        userLanguage.setSkyuserId(user.getStakeHolderUser().getId());

        userLanguageRP.save(userLanguage);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Apply skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyRQ
     */
    public CompanyRS applySkyowner(CompanyRQ companyRQ) {

        skyownerBean.licenseValid(companyRQ.getBusinessTypeId(), companyRQ.getLicenses());

        UserEntity user = userBean.getUserPrincipal();

        if (user.getStakeHolderUser().getIsSkyowner() == 1) {
            throw new BadRequestException("ald_apl_skyowner", null);
        }

        var checkExists = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());

        if (checkExists != null) {
            throw new BadRequestException("sth_w_w", null);
        }


        userBean.senNotify(user.getStakeHolderUser().getId(), "skyowner_welcome_message");

        SkyownerRegisterRQ skyownerRQ = new SkyownerRegisterRQ();
        BeanUtils.copyProperties(companyRQ, skyownerRQ);

        List<CompanyRS> companyRS = skyownerBean.addStakeHolderCompany(skyownerRQ, user, userRepository);

        return companyRS.get(0);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Listing invitation of user from skyowner
     * -----------------------------------------------------------------------------------------------------------------
     */
    public List<InvitationRS> getInvitations() {

        UserEntity user = userBean.getUserPrincipal();

        List<StakeholderUserInvitationEntity> invitations = invitationsRP.findByInviteStakeholderUserIdAndStatus(user.getStakeHolderUser().getId(), 0);

        List<InvitationRS> invitationRSES = new ArrayList<>();

        for (StakeholderUserInvitationEntity invitation : invitations) {

            InvitationRS invitationRS = new InvitationRS();

            StakeholderCompanyEntity companys = companyRP.findById(invitation.getStakeholderCompanyId()).orElse(null);
            if (companys != null) {
                invitationRS.setCompanyName(companys.getCompanyName());
                invitationRS.setId(invitation.getId());
                String imageName = companys.getProfileImg() != null ? companys.getProfileImg() : "default.png";

                invitationRS.setPhotoMedium(environment.getProperty("spring.awsImageUrl.companyProfile") + "medium/" + imageName);
                invitationRS.setPhotoSmall(environment.getProperty("spring.awsImageUrl.companyProfile") + "_thumbnail/" + imageName);
                invitationRSES.add(invitationRS);
            }

        }

        return invitationRSES;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Option for user to accept or refuse
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param optionStaffRQ;
     */
    public void options(OptionStaffRQ optionStaffRQ) {

        UserEntity user = userBean.getUserPrincipal();

        StakeholderUserInvitationEntity invitation = invitationsRP.findByIdAndInviteStakeholderUserId(optionStaffRQ.getInvitationId(), user.getStakeHolderUser().getId());

        if (invitation == null) {
            throw new BadRequestException("sth_w_w", null);
        }

        if (invitation.getStatus() != 0) {
            throw new BadRequestException("sth_w_w", null);
        }

        invitation.setStatus(optionStaffRQ.getStatus());
        invitationsRP.save(invitation);

        String scriptKey = optionStaffRQ.getStatus().equals(1) ? "staff_accept" : "staff_reject";

        if (optionStaffRQ.getStatus().equals(1)) {
            skyownerBean.addStaff(invitation, user.getStakeHolderUser().getId());
        }

        sendNotifyToallStaff(invitation.getStakeholderCompanyId(), scriptKey);

    }

    private void sendNotifyToallStaff(Long companyId, String script) {

        var players = notificationNQ.companyPlayerId(companyId, "admin");
        LinkedHashSet<InviteNotifyRS> storeNotify = new LinkedHashSet<>();

        players.forEach(data -> {
            InviteNotifyRS inviteNotifyRS = new InviteNotifyRS();
            HashMap<String, Object> scriptData = new HashMap<>();
            scriptData.put("playerId", data.getPlayerId());
            scriptData.put("noInsert", "");
            scriptData.put("scriptKey", script);
            scriptData.put("companyId", companyId);
            notification.sendMessageToUsers(scriptData);

            ScriptingTO scriptingTO = notificationNQ.scripting( headerBean.getLocalizationId(), script );

            inviteNotifyRS.setSkyuserId((long) data.getSkyuserId());
            inviteNotifyRS.setScriptId(scriptingTO.getScriptId());
            storeNotify.add(inviteNotifyRS);
        });

        storeNotify.forEach(data -> {
            notification.addNotificationHisitory(null, data.getScriptId(), data.getSkyuserId(), companyId);
        });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Logout
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Return Boolean
     */
    @Override
    public Boolean logout(HttpHeaders httpHeaders) {

        var authorization = httpHeaders.getFirst("Authorization");

        if (authorization != null && authorization.contains("Bearer")) {

            var accessToken = authorization.substring("Bearer".length() + 1);
            var jti = jwtUtils.getClaim(accessToken, "jti", String.class);
            var oauthUser = oauthUserRP.getFirst(jti, 1);

            if (oauthUser == null) {
                return false;
            }

            oauthUser.setStatus(0);
            oauthUserRP.save(oauthUser);

            return true;
        }

        return false;
    }


    @Override
    public TokenRS refreshToken(HttpHeaders httpHeaders, LoginRefreshRQ loginRQ) {
        String credential = userBean.oauth2Credential(httpHeaders);
        var data = userBean.getRefreshToken(loginRQ.getRefreshToken(), credential);
        UserEntity user = userRepository.findById(data.getUserId());

        userBean.storeUserTokenLastLogin(data.getToken(), user);

        return data;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Delete user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     */
//    public void deleteUser(String username, String code) {
//
//        var user = userRepository.findByEmailOrPhone(username, code);
//        if (user == null) {
//            throw new BadRequestException("The user dose not exists", null);
//        }
//
//        var skyuser = Sta
//
//    }


}
