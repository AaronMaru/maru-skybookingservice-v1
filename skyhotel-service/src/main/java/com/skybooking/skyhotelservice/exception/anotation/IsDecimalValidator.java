package com.skybooking.skyhotelservice.exception.anotation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsDecimalValidator implements ConstraintValidator<IsDecimal, Object> {

    private String message;
    private Pattern pattern;

    @Override
    public void initialize(IsDecimal constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.pattern = Pattern.compile(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (isNumeric(value.toString()))
            return true;
        String property = ((ConstraintValidatorContextImpl)context).getConstraintViolationCreationContexts().get(0).getPath().asString();
        message = message
                .replace("$property", property);
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
