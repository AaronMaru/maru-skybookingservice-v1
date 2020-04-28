package com.skybooking.paymentservice.exception.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BookingCodeValidator.class)
@Documented
public @interface BookingCodeValidate {

    String message() default "Booking code is invalid";
    int[] statuses();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
