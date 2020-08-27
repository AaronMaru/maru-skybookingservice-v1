package com.skybooking.skyhotelservice.exception.anotation.checkinoutdate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CheckinDateValidator implements ConstraintValidator<CheckinDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            var date = LocalDate.parse(value);
            if (date.compareTo(LocalDate.now()) < 0) {
                return false;
            }
            return true;
        } catch (DateTimeParseException ex) {
            return true;
        }

    }

}
