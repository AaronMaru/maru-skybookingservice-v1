package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OfflineBookingCodeValidator.class)
@Documented
public @interface OfflineBookingCodeValidate {

    String message() default "Reference code is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
