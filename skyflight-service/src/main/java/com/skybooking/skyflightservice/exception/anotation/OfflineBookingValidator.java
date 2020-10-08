package com.skybooking.skyflightservice.exception.anotation;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.OfflineItineraryRQ;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfflineBookingValidator implements ConstraintValidator<OfflineBookingValidate, OfflineItineraryRQ> {

    private String message;

    @Autowired private BookingRP bookingRP;

    @Override
    public void initialize(OfflineBookingValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(OfflineItineraryRQ offlineItineraryRQ, ConstraintValidatorContext context) {

        if (offlineItineraryRQ.getBookingType() == null) return true;

        if (offlineItineraryRQ.getBookingType().equals("FLIGHT_ISSUED_TICKET_FAIL")) {
            if (offlineItineraryRQ.getReferenceCode() == null) {
                message = "REQUIRE_REFERENCE_CODE";
                return validator(context);
            } else {

                try {
                    BookingEntity booking = bookingRP.getBookingByBookingCode(offlineItineraryRQ.getReferenceCode());
                    if (booking == null) { // no booking record
                        message = "REFERENCE_CODE_NOT_EXIST";
                        return this.validator(context);
                    }

                    BookingEntity hasBooking = bookingRP.getBookingByReferenceCode(offlineItineraryRQ.getReferenceCode());
                    if (hasBooking != null) { // booking created
                        message = "REFERENCE_CODE_CREATED";
                        return this.validator(context);
                    }

                    if (booking.getStatus() == TicketConstant.TICKET_FAIL) {
                        return true;
                    } else { // booking is not issued air ticket fail
                        message = "REFERENCE_CODE_NOT_ISSUED_TICKET_FAIL";
                        return this.validator(context);
                    }

                } catch (Exception exception) {
                    message = "REFERENCE_CODE_INVALID";
                    return validator(context);
                }
            }
        } else {
            if (offlineItineraryRQ.getContactPerson() == null) {
                message = "REQUIRE_CONTACT_PERSON";
                return validator(context);
            } else {
                return true;
            }
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
