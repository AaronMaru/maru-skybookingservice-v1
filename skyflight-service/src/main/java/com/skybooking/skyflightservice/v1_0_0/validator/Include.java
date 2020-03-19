package com.skybooking.skyflightservice.v1_0_0.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IncludeValidator.class)
public @interface Include {
    String message() default "The input value is not matched.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String contains();

    String delimiter();

    boolean caseSensitive() default false;
}
