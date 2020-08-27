package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfflineBookingPnrValidator implements ConstraintValidator<OfflineBookingPnrValidate, String> {

    private String message;

    @Autowired
    private BookingRP bookingRP;

    @Override
    public void initialize(OfflineBookingPnrValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context
            .buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return bookingRP.getBookingByItineraryRef(value) == null;
    }

}
