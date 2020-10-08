package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReferenceCodeValidator implements ConstraintValidator<ReferenceCodeValidate, String> {

    private String message;

    @Autowired
    private BookingRP bookingRP;

    @Override
    public void initialize(ReferenceCodeValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        context
            .buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return bookingRP.getBookingByBookingCode(value, TicketConstant.TICKET_FAIL) != null;
    }
}
