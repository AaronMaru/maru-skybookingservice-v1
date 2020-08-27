package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfflineBookingCodeValidator implements ConstraintValidator<OfflineBookingCodeValidate, String> {

    private String message;

    @Autowired private BookingRP bookingRP;

    @Override
    public void initialize(OfflineBookingCodeValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String referenceCode, ConstraintValidatorContext context) {

        if (referenceCode == null) {
            return false;
        }

        try {
            BookingEntity booking = bookingRP.getBookingByBookingCode(referenceCode);
            if (booking == null) return this.validator(context); // no booking record

            BookingEntity hasBooking = bookingRP.getBookingByReferenceCode(referenceCode);
            if (hasBooking != null) { // booking created
                message = "Reference code created already";
                return this.validator(context);
            }

            if (booking.getStatus() == TicketConstant.TICKET_FAIL)
                return true;
            else // booking is not issued air ticket fail
                return this.validator(context);

        } catch (Exception exception) {
            return false;
        }
    }

    private boolean validator(ConstraintValidatorContext context) {

        context
            .buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return false;
    }
}
