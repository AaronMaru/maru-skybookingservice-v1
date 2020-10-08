package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.BussinessTypeRP;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class BussinessTypeValidator implements ConstraintValidator<BussinessType, Long> {

    @Autowired
    private BussinessTypeRP bussinessTypeRP;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        boolean b = true;

        if (id == null) {
            return false;
        }

        Optional<BussinessTypeEntity> bussinessType = bussinessTypeRP.findById(id);

        if (bussinessType.isEmpty()) {
            b = false;
        }

        return b;

    }

}
