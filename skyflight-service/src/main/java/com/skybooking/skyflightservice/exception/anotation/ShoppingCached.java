package com.skybooking.skyflightservice.exception.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ShoppingCachedValidator.class)
public @interface ShoppingCached {

    String message() default "Request ID is invalid.";
    String requestId() default "requestId";
    String legIds() default "legIds";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
