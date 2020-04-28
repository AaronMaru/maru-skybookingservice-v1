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
@Constraint(validatedBy = PaymentCodeValidator.class)
@Documented
public @interface PaymentCodeValidate {

    String message() default "Payment code is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
