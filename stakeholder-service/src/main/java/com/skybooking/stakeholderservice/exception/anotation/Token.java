package com.skybooking.stakeholderservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TokenValidator.class)
@Documented
public @interface Token {

    public int status();

    String message() default "Invalid verify code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
