package com.skybooking.paymentservice.exception.annotation;

import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentMethodTO;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentCodeValidator implements ConstraintValidator<PaymentCodeValidate, String> {

    private String message;

    @Autowired
    private PaymentNQ paymentNQ;

    @Override
    public void initialize(PaymentCodeValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String paymentCode, ConstraintValidatorContext context) {

        /** set message validation */
        context
            .buildConstraintViolationWithTemplate(this.message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        /** check payment has or not */
        if (paymentCode == null) return false;

        try {

            var paymentMethods = paymentNQ.getListPaymentMethods();

            for (PaymentMethodTO paymentMethod: paymentMethods) {
                if (paymentCode.equals(paymentMethod.getCode())) {
                    return true;
                }
            }

            return false;

        } catch (Exception exception) {
            return false;
        }
    }

}
