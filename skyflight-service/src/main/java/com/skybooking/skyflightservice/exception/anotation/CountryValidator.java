package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.v1_0_0.io.entity.country.CountryEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.country.CountryRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CountryValidator implements ConstraintValidator<Country, Long> {

    @Autowired
    private CountryRP countryRP;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        boolean b = true;

        if (id == null) {
            return false;
        }

        Optional<CountryEntity> country = countryRP.findById(id);

        if (country.isEmpty()) {
            b = false;
        }

        return b;

    }

}
