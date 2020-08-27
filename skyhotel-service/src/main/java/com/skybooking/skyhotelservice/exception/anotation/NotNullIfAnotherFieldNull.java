package com.skybooking.skyhotelservice.exception.anotation;

import com.skybooking.skyhotelservice.exception.anotation.checkinoutdate.CheckoutDate;
import com.skybooking.skyhotelservice.exception.anotation.checkinoutdate.CheckoutDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullIfAnotherFieldNullValidator.class)
@Documented
public @interface NotNullIfAnotherFieldNull {

    String message() default "$field required if $ifField not provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();
    String ifField();

}
