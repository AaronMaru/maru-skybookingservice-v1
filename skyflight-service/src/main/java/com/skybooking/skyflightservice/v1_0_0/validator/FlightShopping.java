package com.skybooking.skyflightservice.v1_0_0.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FlightShoppingValidator.class)
public @interface FlightShopping {

    String message() default "The flight shopping request is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
