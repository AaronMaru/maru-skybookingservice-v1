package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OfflineBookingPnrValidator.class)
@Documented
public @interface OfflineBookingPnrValidate {

    String message() default "PNR_CODE_EXISTED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
