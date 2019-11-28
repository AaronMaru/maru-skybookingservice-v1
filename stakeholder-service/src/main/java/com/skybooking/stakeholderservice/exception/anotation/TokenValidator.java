package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenValidator implements ConstraintValidator<Token, String> {

    @Autowired
    private VerifyUserRP verifyRepository;

    private String message;
    private int status;

    @Override
    public void initialize(final Token constraintAnnotation) {
        status = constraintAnnotation.status();
        message = constraintAnnotation.message();
    }


    @Override
    public boolean isValid(String token, ConstraintValidatorContext context) {
        boolean valid = true;
        VerifyUserEntity verifyUserEntity = verifyRepository.findByTokenAndStatus(token, status);

        if (verifyUserEntity == null) {
            valid = false;
        }

        return valid;

    }

}
