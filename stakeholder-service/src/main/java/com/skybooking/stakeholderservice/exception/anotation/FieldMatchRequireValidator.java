package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.BussinessTypeRP;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class FieldMatchRequireValidator implements ConstraintValidator<FieldMatchRequire, Object> {

    @Autowired
    private BussinessTypeRP bussinessTypeRP;

    private String firstFieldName;
    private String secondFieldName;
    private String thirdFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatchRequire constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        thirdFieldName = constraintAnnotation.third();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        boolean valid = true;

        try
        {
            final String firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            final Object thirdObj = BeanUtils.getProperty(value, thirdFieldName);

            Optional<BussinessTypeEntity> bussinessType = bussinessTypeRP.findById(Long.parseLong(firstObj));
            if(!bussinessType.isEmpty()) {
                if (bussinessType.get().getName().equals("Goverment")) {
                    valid = secondObj != null && !secondObj.equals("") && thirdObj != null && !thirdObj.equals("");
                }
            }

        }

        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;

    }

}
