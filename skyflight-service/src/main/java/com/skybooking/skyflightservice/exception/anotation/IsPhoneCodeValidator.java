package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsPhoneCodeValidator implements ConstraintValidator<IsPhoneCode, String> {

    private String message;

    @Override
    public void initialize(IsPhoneCode constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        // condition
        String regex = "^\\+(?:[0-9])+$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(value).matches()) {
            return validator(context);
        }

        return true;
    }

    private boolean validator(ConstraintValidatorContext context) {

        if (context != null) {
            context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }

        return false;
    }
}
