package com.skybooking.paymentservice.exception.annotation;

import com.skybooking.paymentservice.v1_0_0.io.repository.booking.BookingRP;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlightCodeValidator implements ConstraintValidator<FlightCodeValidate, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;
    private int[] statuses;

    @Autowired
    private BookingRP bookingRP;

    @Override
    public void initialize(final FlightCodeValidate constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
        statuses = constraintAnnotation.statuses();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(firstFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        try
        {
            final String bookingCode = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            if (secondObj.equals("FLIGHT")) {
                var booking = bookingRP.getBooking(bookingCode);
                if (booking == null) {
                    return false;
                }
                System.out.println(booking);
                for (int status: statuses) {
                    if (booking.getStatus() == status) {
                        return true;
                    }
                }
                return false;
            }

            return true;

        } catch (final Exception ignore) {
            return false;
        }

    }

}
