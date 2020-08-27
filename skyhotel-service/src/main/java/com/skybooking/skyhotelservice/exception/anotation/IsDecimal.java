package com.skybooking.skyhotelservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsDecimalValidator.class)
@Documented
public @interface IsDecimal {
    String message() default "$property must be a decimal number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regex() default "-?\\d+(\\.\\d+)?e?";
}
