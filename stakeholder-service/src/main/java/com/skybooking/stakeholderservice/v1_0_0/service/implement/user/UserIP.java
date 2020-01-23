package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.ForbiddenException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.UserSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.TokenTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.*;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.InvitationRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Duplicate duplicate;

    @Autowired
    private StakeholderUserInvitationRP invitationsRP;

    @Autowired
    private CompanyRP companyRP;


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
        BeanUtils.copyProperties(userBean.userFields(user, null), userDetailRS);

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
        BeanUtils.copyProperties(userBean.userFields(user, null), userDetailsRS);

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
            throw new UnauthorizedException("sth_w_w", "");
        }

        Boolean b = pwdEncoder.matches(passwordRQ.getOldPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }
        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));

        userRepository.save(user);

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

        String username = passwordRQ.getUsername();

        if (NumberUtils.isNumber(passwordRQ.getUsername())) {
            username = passwordRQ.getCode() + passwordRQ.getUsername();
        }

        UserEntity user = userRepository.findByEmailOrPhone(passwordRQ.getUsername(), passwordRQ.getCode().replaceAll(" ", ""));

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        StakeHolderUserEntity skyuser = user.getStakeHolderUser();
        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();

        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(passwordRQ.getToken(),
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        generalBean.expiredInvalidToken(verifyUserEntity, passwordRQ.getToken());

        user.setPassword(pwdEncoder.encode(passwordRQ.getNewPassword()));
        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.RESET_PASSWORD, user);

        Map<String, Object> mailData = duplicate.mailData(fullName, 0, "password_reset_successfully");

        apiBean.sendEmailSMS(username,"success-reset-password", mailData);

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        String credentials = environment.getProperty("spring.oauth2.client-id") + ":" + environment.getProperty("spring.oauth2.client-secret");
        String encCredentials = "Basic " + new String(Base64.encodeBase64(credentials.getBytes()));
        TokenTF data = userBean.getCredential(passwordRQ.getUsername(), passwordRQ.getNewPassword(), encCredentials, user.getCode(), null);
        BeanUtils.copyProperties(userBean.userFields(user, data.getAccess_token()), userDetailsTokenRS);

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send code to reset password
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyRequest
     */
    public int sendCodeResetPassword(SendVerifyRQ verifyRQ) {

        UserEntity user = userRepository.findByEmailOrPhone(verifyRQ.getUsername(), verifyRQ.getCode());

        if (user == null || user.getProviderId() != null) {
            throw new UnauthorizedException("Unauthorized", "");
        }

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String username = NumberUtils.isNumber(verifyRQ.getUsername())
                ? verifyRQ.getCode() + verifyRQ.getUsername().replaceFirst("^0+(?!$)", "")
                : verifyRQ.getUsername();

        generalBean.expireRequest(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        int code = apiBean.createVerifyCode(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.forgotPassword")));
        userRepository.save(user);

        Map<String, Object> mailData = duplicate.mailData(fullName, code, "reset_your_password");
        apiBean.sendEmailSMS(username,"send-login", mailData);

        return code;

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
            throw new BadRequestException("Invalid data", contactRQ.getUsername());
        }

        userRepository.save(user);

        logger.activities(ActivityLoggingBean.Action.UPDATE_CONTACT, user);

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
            throw new UnauthorizedException("sth_w_w", "");
        }

        Boolean b = pwdEncoder.matches(contactRQ.getPassword(), user.getPassword());

        if (!b) {
            throw new UnauthorizedException("incrt_pwd", "");
        }

        // need to add httpstatus conflex
        generalBean.expireRequest(user, Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));

        int code = apiBean.createVerifyCode(user,
                Integer.parseInt(environment.getProperty("spring.verifyStatus.updateContact")));
        String fullName = user.getStakeHolderUser().getFirstName() + " " + user.getStakeHolderUser().getLastName();
        String username = NumberUtils.isNumber(contactRQ.getUsername())
                ? contactRQ.getCode() + contactRQ.getUsername().replaceFirst("^0+(?!$)", "")
                : contactRQ.getUsername();

        userRepository.save(user);

        Map<String, Object> mailData = duplicate.mailData(fullName, code, "account_verification_code");
        apiBean.sendEmailSMS(username,"send-login", mailData);

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
            throw new ForbiddenException("incrt_pwd", "");
        }

        user.getStakeHolderUser().setStatus(0);
        userRepository.save(user);

        apiBean.storeUserStatus(user, Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")),
                "User was deactive account");

        String username = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();

        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();

        logger.activities(ActivityLoggingBean.Action.DEACTIVE_USER, user);

        Map<String, Object> mailData = duplicate.mailData(fullName, 0, "account_deactivated_successfully");
        apiBean.sendEmailSMS(username,"deactive-account", mailData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Apply skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyRQ
     */
    public void applySkyowner(CompanyRQ companyRQ) {

        skyownerBean.licenseValid(companyRQ.getBusinessTypeId(), companyRQ.getLicenses());

        UserEntity user = userBean.getUserPrincipal();

        if (user.getStakeHolderUser().getIsSkyowner() == 1) {
            throw new BadRequestException("ald_apl_skyowner", "");
        }

        var checkExists = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());

        if (checkExists != null) {
            throw new BadRequestException("sth_w_w", "");
        }

        SkyownerRegisterRQ skyownerRQ = new SkyownerRegisterRQ();
        BeanUtils.copyProperties(companyRQ, skyownerRQ);

        skyownerBean.addStakeHolderCompany(skyownerRQ, user, userRepository);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Listing invitation of user from skyowner
     * -----------------------------------------------------------------------------------------------------------------
     */
    public List<InvitationRS> getInvitations() {

        UserEntity user = userBean.getUserPrincipal();

        List<StakeholderUserInvitationEntity> invitations = invitationsRP.findByInviteStakeholderUserId(user.getStakeHolderUser().getId());

        List<InvitationRS> invitationRSES = new ArrayList<>();

        for(StakeholderUserInvitationEntity invitation : invitations) {

            InvitationRS invitationRS = new InvitationRS();

            StakeholderCompanyEntity companys = companyRP.findById(invitation.getStakeholderCompanyId()).orElse(null);
            if (companys != null) {
                invitationRS.setCompanyName(companys.getCompanyName());

                String imageName = companys.getProfileImg() != null ? companys.getProfileImg() : "default.png";

                invitationRS.setPhotoMedium(environment.getProperty("spring.awsImageUrl.companyProfile") + "/medium/" + imageName);
                invitationRS.setPhotoSmall(environment.getProperty("spring.awsImageUrl.companyProfile") + "/_thumbnail/" + imageName);
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
            throw new BadRequestException("sth_w_w", "");
        }

        if (invitation.getStatus() != 0) {
            throw new BadRequestException("sth_w_w", "");
        }

        invitation.setStatus(optionStaffRQ.getStatus());
        invitationsRP.save(invitation);

        if (invitation.getStatus() == 1) {
            skyownerBean.addStaff(invitation.getStakeholderCompanyId(), user.getStakeHolderUser().getId());
        }

    }


}
