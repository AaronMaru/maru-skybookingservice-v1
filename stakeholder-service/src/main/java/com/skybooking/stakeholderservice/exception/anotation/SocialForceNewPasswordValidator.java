package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.constant.PasswordConstant;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginSocialV110RQ;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SocialForceNewPasswordValidator implements ConstraintValidator<SocialForceNewPassword, LoginSocialV110RQ> {

    private String message;

    @Autowired private UserRepository userRP;
    @Autowired private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void initialize(SocialForceNewPassword constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @SneakyThrows
    @Override
    public boolean isValid(LoginSocialV110RQ loginSocialRQ, ConstraintValidatorContext context) {

        UserEntity userEntity = userRP.findByEmailOrPhone(loginSocialRQ.getUsername(), "");

        if (userEntity == null && loginSocialRQ.getPassword() == null) return invalid(context);

        if (loginSocialRQ.getPassword() == null) {
            if (passwordEncoder.matches(PasswordConstant.DEFAULT + "", userEntity.getPassword())
                || userEntity.getPassword() == null) {
                message = PasswordConstant.SOCIAL_REQUIRE_PASSWORD;
                return invalid(context);
            } else {
                return true;
            }
        }


        if (!loginSocialRQ.getPassword().equals(loginSocialRQ.getConfirmPassword())) {
            message = "PASSWORD_NOT_MATCH";
            return invalid(context);
        }

        return true;
    }

    private boolean invalid(ConstraintValidatorContext context) {
        context
            .buildConstraintViolationWithTemplate(message)
            .addPropertyNode("password")
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return false;
    }
}
