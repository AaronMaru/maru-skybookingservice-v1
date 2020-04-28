package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsPhoneCodeValidator.class)
@Documented
public @interface IsPhoneCode {

    String message() default "Phone code is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
