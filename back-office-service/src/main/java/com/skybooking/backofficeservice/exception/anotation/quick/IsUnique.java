package com.skybooking.backofficeservice.exception.anotation.quick;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsUniqueValidate.class)
@Documented
public @interface IsUnique {

    String message() default "this destination code already existed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String destinationCode();

}
