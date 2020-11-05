package com.skybooking.stakeholderservice.v1_0_0.service.usercontact;

import com.skybooking.stakeholderservice.constant.PasswordConstant;
import com.skybooking.stakeholderservice.constant.UserContactMessageConstant;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.redis.UserContactCachedRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.BaseService;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.UserContactRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.CheckUserContactRS;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.auth.UserToken;
import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.skybooking.stakeholderservice.constant.MailStatusConstant.ACCOUNT_VERIFICATION_CODE;

@Service
@RequiredArgsConstructor
public class UserContactIP extends BaseService implements UserContactSV {

    private final UserRepository userRP;
    private final VerifyUserRP verifyUserRP;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCrypt;
    private final UserContactCachedRP userContactCachedRP;
    private final ApiBean apiBean;
    private final EmailBean emailBean;
    private final UserBean userBean;
    private final GeneralBean generalBean;

    @Override
    public BaseResponse check()
    {
        UserToken userToken = jwtUtils.getUserToken();
        UserEntity userEntity = userRP.findById(userToken.getUserId());
        CheckUserContactRS checkUserContactRS = new CheckUserContactRS();
        checkUserContactRS.setIsPassword(true);
        boolean isForceUserContact = false;

        if (userEntity.getCode() != null)
            checkUserContactRS.setPhoneCode(userEntity.getCode());
        else
            isForceUserContact = true;

        if (userEntity.getPhone() != null)
            checkUserContactRS.setPhoneNumber(userEntity.getPhone());
        else
            isForceUserContact = true;

        if (userEntity.getEmail() != null)
            checkUserContactRS.setEmail(userEntity.getEmail());
        else
            isForceUserContact = true;

        if (userEntity.getPassword() == null
            || userEntity.getPassword().equals("")
            || bCrypt.matches(PasswordConstant.DEFAULT, userEntity.getPassword()))
        {
            checkUserContactRS.setIsPassword(false);
            isForceUserContact = false;
        }

        if (isForceUserContact)
            return responseBody(HttpStatus.BAD_REQUEST, "", checkUserContactRS);

        return responseBodyWithSuccessMessage();
    }

    @Override
    public BaseResponse create(UserContactRQ userContactRQ)
    {
        UserToken userToken = jwtUtils.getUserToken();
        UserEntity userEntity = userRP.findById(userToken.getUserId());

        if (userEntity.getEmail() != null && userEntity.getPhone() != null)
            return responseBody(HttpStatus.FORBIDDEN, UserContactMessageConstant.FORBIDDEN);

        if (userContactRQ.getPassword() == null) {
            if (userEntity.getPassword() == null
                || userEntity.getPassword().equals("")
                || bCrypt.matches(PasswordConstant.DEFAULT, userEntity.getPassword()))
            {
                return responseBody(HttpStatus.BAD_REQUEST, UserContactMessageConstant.PASSWORD_REQUIRED);
            }
        } else {
            if (bCrypt.matches(PasswordConstant.DEFAULT, userEntity.getPassword()))
                userEntity.setPassword(bCrypt.encode(userContactRQ.getPassword()));
        }

        UserContactEntity userContactEntity = new UserContactEntity();
        if (userEntity.getCode() == null || userEntity.getCode().equals(""))
            userContactEntity.setPhoneCode(userContactRQ.getPhoneCode());

        if (userEntity.getPhone() == null || userEntity.getPhone().equals(""))
            userContactEntity.setPhoneNumber(userContactRQ.getPhoneNumber().replaceFirst("^0+(?!$)", ""));

        if (userEntity.getEmail() == null || userEntity.getEmail().equals(""))
            userContactEntity.setEmail(userContactRQ.getEmail());

        if (userEntity.getPassword() == null || userEntity.getPassword().equals(""))
            userContactEntity.setPassword(userContactRQ.getPassword());

        userContactEntity.setId(userEntity.getId());
        userContactCachedRP.save(userContactEntity);

        sendCode(userEntity, userContactEntity);

        return responseBodyWithSuccessMessage();
    }

    @Override
    public BaseResponse resendCode()
    {
        try {
            UserToken userToken = jwtUtils.getUserToken();
            Optional<UserContactEntity> userContactEntity = userContactCachedRP.findById(userToken.getUserId());

            if (userContactEntity.isPresent()) {
                UserContactEntity user = userContactEntity.get();

                UserEntity userEntity = userRP.findById(userToken.getUserId());
                sendCode(userEntity, user);

                return responseBodyWithSuccessMessage();
            }

            return responseBody(HttpStatus.FORBIDDEN, UserContactMessageConstant.FORBIDDEN);
        } catch (Exception exception) {
            return responseBody(HttpStatus.FORBIDDEN, UserContactMessageConstant.FORBIDDEN);
        }
    }

    @Override
    public BaseResponse verifiedCode(VerifyRQ verifyRQ)
    {
        UserToken userToken = jwtUtils.getUserToken();
        UserEntity userEntity = userRP.findById(userToken.getUserId());
        Optional<UserContactEntity> userContactEntity = userContactCachedRP.findById(userToken.getUserId());

        if (userContactEntity.isPresent()) {
            UserContactEntity userContact = userContactEntity.get();

            if (userEntity.getCode() == null)
                userEntity.setCode(userContact.getPhoneCode());

            if (userEntity.getPhone() == null)
                userEntity.setPhone(userContact.getPhoneNumber());

            if (userEntity.getEmail() == null)
                userEntity.setEmail(userContact.getEmail());

            if (userEntity.getPassword() == null
                || userEntity.getPassword().equals("")
                || bCrypt.matches(PasswordConstant.DEFAULT, userEntity.getPassword()))
            {
                userEntity.setPassword(bCrypt.encode(userContact.getPassword()));
            }

            VerifyUserEntity verify = verifyUserRP.findByTokenAndStatus(verifyRQ.getToken(), 1);
            generalBean.expiredInvalidToken(verify, verifyRQ.getToken().replaceAll(" ", ""));
            verifyUserRP.save(verify);
            userRP.save(userEntity);
            userContactCachedRP.delete(userContact);

            return responseBodyWithSuccessMessage();
        }

        return responseBody(HttpStatus.FORBIDDEN, UserContactMessageConstant.FORBIDDEN);
    }

    private void sendCode(UserEntity userEntity, UserContactEntity userContactEntity)
    {
        int code = apiBean.createVerifyCode(userEntity, 1);
        StakeHolderUserEntity stakeholder = userEntity.getStakeHolderUser();
        String fullName = stakeholder.getFirstName() + " " + stakeholder.getLastName();
        String username = userContactEntity.getEmail() != null ? userContactEntity.getEmail() : userContactEntity.getPhoneNumber();
        String receiver = userBean.getUsername(username, userContactEntity.getPhoneCode());

        Map<String, Object> mailData = emailBean.mailData(receiver, fullName, code, ACCOUNT_VERIFICATION_CODE);
        emailBean.sendEmailSMS("send-login", mailData);
    }
}
