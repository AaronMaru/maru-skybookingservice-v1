package com.skybooking.skyhotelservice.exception.anotation.checkinoutdate;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckoutDateValidator implements ConstraintValidator<CheckoutDate, Object> {

    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(final CheckoutDate constraintAnnotation) {
        firstField = constraintAnnotation.first();
        secondField = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        boolean valid = true;

        try
        {
            final String firstDate = BeanUtils.getProperty(value, firstField);
            final String secondDate = BeanUtils.getProperty(value, secondField);

            if (secondDate.compareTo(firstDate) < 0) {
                valid = false;
            }
        }

        catch (final Exception ignore) {}

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;

    }

}
