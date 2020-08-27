package com.skybooking.skyhotelservice.exception.anotation.checkinoutdate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckinDateValidator.class)
@Documented
public @interface CheckinDate {

    String message() default "Checkin date can not be past date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
