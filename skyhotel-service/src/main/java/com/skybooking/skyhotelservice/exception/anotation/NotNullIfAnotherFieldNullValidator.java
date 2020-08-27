package com.skybooking.skyhotelservice.exception.anotation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullIfAnotherFieldNullValidator implements ConstraintValidator<NotNullIfAnotherFieldNull, Object> {

    private String field;
    private String ifField;
    private String message;

    @Override
    public void initialize(NotNullIfAnotherFieldNull constraintAnnotation) {
        field = constraintAnnotation.field();
        ifField = constraintAnnotation.ifField();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            Object f = BeanUtils.getProperty(value, field);
            Object ifF = BeanUtils.getProperty(value, ifField);
            if (f == null && ifF == null) {
                message = message
                        .replace("$field", field)
                        .replace("$ifField", ifField);
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(field)
                        .addConstraintViolation().disableDefaultConstraintViolation();
                return false;
            }
        } catch (Exception ignored) {}
        return true;
    }
}
