package com.skybooking.stakeholderservice.v1_0_0.service.usercontact;

import com.skybooking.stakeholderservice.constant.PasswordConstant;
import com.skybooking.stakeholderservice.constant.UserContactMessageConstant;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.BaseService;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.UserContactRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.auth.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContactIP extends BaseService implements UserContactSV {

    private final UserRepository userRP;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCrypt;

    @Override
    public BaseResponse create(UserContactRQ userContactRQ) {

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

        userEntity.setCode(userContactRQ.getPhoneCode());
        userEntity.setPhone(userContactRQ.getPhoneNumber().replaceFirst("^0+(?!$)", ""));
        userEntity.setEmail(userContactRQ.getEmail());

        userRP.save(userEntity);

        return responseBodyWithSuccessMessage();
    }
}
