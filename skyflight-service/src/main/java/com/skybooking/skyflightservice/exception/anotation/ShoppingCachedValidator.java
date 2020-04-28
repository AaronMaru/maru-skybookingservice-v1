package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class ShoppingCachedValidator implements ConstraintValidator<ShoppingCached, Object> {

    private String message;
    private String requestId;
    private String legIds;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private QuerySV querySV;

    @Override
    public void initialize(ShoppingCached constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.requestId = constraintAnnotation.requestId();
        this.legIds = constraintAnnotation.legIds();
    }

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext context) {

        try
        {
            var requestId = BeanUtils.getProperty(request, this.requestId);
            var legIds = BeanUtils.getArrayProperty(request, this.legIds);

            if (requestId.equals("") || querySV.flightShoppingById(requestId) == null) {
                this.message = "Request ID is invalid.";
                return this.validator(context);
            }

            this.message = "Leg ID is invalid.";
            if (legIds.length < 0) {
                return this.validator(context);
            }

            for (String legId : legIds) {
                if (detailSV.getLegDetail(requestId, legId) == null) {
                    return this.validator(context);
                }
            }

        } catch (final Exception ignore) {
            return false;
        }

        return true;
    }

    private boolean validator(ConstraintValidatorContext context) {

        context
            .buildConstraintViolationWithTemplate(this.message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return false;
    }
}
