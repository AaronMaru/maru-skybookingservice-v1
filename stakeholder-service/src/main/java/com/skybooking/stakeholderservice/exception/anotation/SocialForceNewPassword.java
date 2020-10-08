package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.constant.PasswordConstant;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SocialForceNewPasswordValidator.class)
public @interface SocialForceNewPassword {

    String message() default PasswordConstant.SOCIAL_REQUIRE_PASSWORD;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
