package com.skybooking.backofficeservice.exception.anotation.quick;

import com.skybooking.backofficeservice.v1_0_0.io.repository.QuickRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUniqueValidate implements ConstraintValidator<IsUnique, String> {

    private  String destinationCode;

    @Autowired
    private QuickRP quickRP;

    @Override
    public void initialize(IsUnique constraintAnnotation) {
        this.destinationCode = constraintAnnotation.destinationCode();
    }

    @Override
    public boolean isValid(String destinationCode, ConstraintValidatorContext constraintValidatorContext) {

       var quick = quickRP.findQuickByCode(destinationCode);

        if(quick == null){
            return  true;
        }

        return false;
    }
}
