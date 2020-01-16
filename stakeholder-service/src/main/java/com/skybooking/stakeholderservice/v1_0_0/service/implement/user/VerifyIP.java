package com.skybooking.stakeholderservice.v1_0_0.service.implement.user;

import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.redis.UserTokenRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user.VerifySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SendVerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.Duplicate;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class VerifyIP implements VerifySV {

    @Autowired
    private VerifyUserRP verifyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private Environment environment;

    @Autowired
    private Duplicate duplicate;

    @Autowired
    private UserTokenRP userTokenRP;

    @Autowired
    private UserBean userBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String
     */
    public UserDetailsTokenRS verifyUser(VerifyRQ verifyRQ, int status) {

        VerifyUserEntity verify = verifyRepository.findByTokenAndStatus(verifyRQ.getToken(), status);

        generalBean.expiredInvalidToken(verify, verifyRQ.getToken().replaceAll(" ", ""));

        verify.getUserEntity().setVerified(1);
        verify.getUserEntity().getStakeHolderUser().setStatus(1);

        UserEntity user = userRepository.save(verify.getUserEntity());
        StakeHolderUserEntity skyuser = user.getStakeHolderUser();

        String fullName = skyuser.getFirstName() + " " + skyuser.getLastName();
        String username = (user.getEmail() != null) ? user.getEmail()
                : user.getCode() + user.getPhone();

        apiBean.storeUserStatus(verify.getUserEntity(),
                Integer.parseInt(environment.getProperty("spring.stakeUser.inactive")), "Waiting verify");

        String keyScript = status == 1 ? "account_verified_successfully" : "account_reactivated_successfully";

        Map<String, Object> mailData = duplicate.mailData(fullName, 0, keyScript);
        apiBean.sendEmailSMS(username,"verify-success", mailData);

        logger.activities(ActivityLoggingBean.Action.VERIFY_USER_ACTIVE, verify.getUserEntity());

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        if (status == 1) {
            UserTokenEntity userToken = userTokenRP.findById(user.getId()).orElse(null);
            BeanUtils.copyProperties(userBean.userFields(user, userToken.getToken()), userDetailsTokenRS);
            return userDetailsTokenRS;
        }

        return userDetailsTokenRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend verify user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendVerifyRequest
     * @Param status
     * @Return String
     */
    public int resendVerify(SendVerifyRQ verifyRQ, Integer status) {

        UserEntity user = userRepository.findByEmailOrPhone(verifyRQ.getUsername(), verifyRQ.getCode());
        if (user == null) {
            throw new UnauthorizedException("sth_w_w", "");
        }
        StakeHolderUserEntity stakeHolderUser = user.getStakeHolderUser();

        generalBean.expireRequest(user, status);

        int code = apiBean.createVerifyCode(user, status);
        String fullName = stakeHolderUser.getFirstName() + " " + stakeHolderUser.getLastName();
        String username = (user.getEmail() != null) ? user.getEmail() : user.getCode() + user.getPhone();

        userRepository.save(user);

        String keyScript = status == 1 ? "resend_new_verification_code" : "new_verification_code_reactivating_account";

        Map<String, Object> mailData = duplicate.mailData(fullName, code, keyScript);
        apiBean.sendEmailSMS(username,"send-verify", mailData);

        return code;

    }


}
