package com.skybooking.paymentservice.exception.annotation;

import com.skybooking.paymentservice.v1_0_0.io.repository.booking.HotelBookingRP;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HotelCodeValidator implements ConstraintValidator<HotelCodeValidate, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;
    private String[] statuses;

    @Autowired
    private HotelBookingRP bookingRP;

    @Override
    public void initialize(final HotelCodeValidate constraintAnnotation) {
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

            if (secondObj.equals("HOTEL")) {
                var booking = bookingRP.getBooking(bookingCode);
                if (booking == null) {
                    return false;
                }
                for (String status: statuses) {
                    if (booking.getStatus().equals(status)) {
                        System.out.println(status);
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
