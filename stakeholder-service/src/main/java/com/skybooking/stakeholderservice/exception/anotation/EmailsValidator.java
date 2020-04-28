package com.skybooking.stakeholderservice.exception.anotation;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailsValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean b = true;

        if (NumberUtils.isNumber(value)) {
            return b;
        }

        if (value == null) {
           return true;
        }
        if (!EmailValidator.getInstance().isValid(value) && !value.isEmpty()) {

            b = false;
        }

        return b;

    }

}
