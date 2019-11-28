package com.skybooking.stakeholderservice.exception.anotation;

import org.apache.commons.lang.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
            if (NumberUtils.isNumber(phone)) {
                return (phone.length() < 20 && phone.length() > 6);
            }
            return true;
    }

}
