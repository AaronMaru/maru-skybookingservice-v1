package com.skybooking.paymentservice.exception.annotation;

import com.skybooking.paymentservice.v1_0_0.io.repository.booking.BookingRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingCodeValidator implements ConstraintValidator<BookingCodeValidate, String> {

    private String message;
    private int[] statuses;

    @Autowired
    private BookingRP bookingRP;

    @Override
    public void initialize(BookingCodeValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.statuses = constraintAnnotation.statuses();
    }

    @Override
    public boolean isValid(String bookingCode, ConstraintValidatorContext context) {
        context
            .buildConstraintViolationWithTemplate(this.message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        if (bookingCode == null) {
            return false;
        }

        try {
            var booking = bookingRP.getBooking(bookingCode);
            if (booking == null) {
                return false;
            }

            for (int status: statuses) {
                if (booking.getStatus() == status) {
                    return true;
                }
            }

            return false;

        } catch (Exception exception) {
            return false;
        }
    }
}
