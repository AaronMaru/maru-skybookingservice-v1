package com.skybooking.stakeholderservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BussinessTypeValidator.class)
@Documented
public @interface BussinessType {

    String message() default "Invalid bussiness type id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
