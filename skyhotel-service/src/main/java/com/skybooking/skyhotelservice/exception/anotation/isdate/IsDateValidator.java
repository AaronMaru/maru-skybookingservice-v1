package com.skybooking.skyhotelservice.exception.anotation.isdate;

import com.skybooking.core.validators.annotations.IsDate;
import lombok.Data;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class IsDateValidator implements ConstraintValidator<IsDate, String> {

    private String message;
    private String format;
    private Class<?>[] groups;


    @Override
    public void initialize(IsDate constraintAnnotation) {
        this.format = constraintAnnotation.format();
        this.message = constraintAnnotation.message();
        this.groups = constraintAnnotation.groups();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null) return true;

        try {

            var formatter = DateTimeFormatter.ofPattern(this.format);
            LocalDate.parse(value, formatter);

        } catch (Exception ex) {
            return validator(constraintValidatorContext);
        }

        return true;
    }

    private boolean validator(ConstraintValidatorContext constraintValidatorContext) {
        if (constraintValidatorContext != null) {

            var property = ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintViolationCreationContexts().get(0).getPath().asString();
            message = message.replace("$property", property);

            constraintValidatorContext
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }

        return false;
    }

    public boolean isValid(String value) {
        return this.isValid(value, null);
    }
}
