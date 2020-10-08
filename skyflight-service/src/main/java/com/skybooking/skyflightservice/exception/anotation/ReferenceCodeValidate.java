package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ReferenceCodeValidator.class)
@Documented
public @interface ReferenceCodeValidate {

    String message() default "REFERENCE_CODE_INVALID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
