package com.skybooking.skyflightservice.v1_0_0.validator;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightLegRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlightShoppingValidator implements ConstraintValidator<FlightShopping, FlightShoppingRQ> {

    private String message;

    @Override
    public void initialize(FlightShopping constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(FlightShoppingRQ flightShoppingRQ, ConstraintValidatorContext context) {

        var valid = false;

        var trip = flightShoppingRQ.getTripType();

        if (trip.equalsIgnoreCase("one")) {
            valid = this.oneTripValidate(flightShoppingRQ);
        } else if (trip.equalsIgnoreCase("round")) {
            valid = this.roundTripValidate(flightShoppingRQ);
        } else {
            valid = this.multiTripValidate(flightShoppingRQ);
        }


        if (valid) {
            valid = this.passengerValidate(flightShoppingRQ);
        }

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * passenger number validate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShoppingRQ
     * @return Boolean
     */
    private boolean passengerValidate(FlightShoppingRQ flightShoppingRQ) {

        var message = "The passenger information is incorrect.";

        var MAXIMUM = 9;

        var adult = flightShoppingRQ.getAdult();
        var child = flightShoppingRQ.getChild();
        var infant = flightShoppingRQ.getInfant();
        var total = adult + child + infant;

        if (!(child <= (adult * 2))) {
            this.message = message;
            return false;
        }

        if (!(infant <= adult)) {
            this.message = message;
            return false;
        }

        if (total > MAXIMUM) {
            this.message = message;
            return false;
        }

        return true;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * one trip validate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShopping
     * @return Boolean
     */
    private boolean oneTripValidate(FlightShoppingRQ flightShopping) {

        var message = "The one trip flight information is incorrect.";

        var legs = flightShopping.getLegs();
        var size = legs.size();

        if (size != 1) {
            this.message = message;
            return false;
        }

        var first = legs.get(0);

        if (this.flightPairValidate(first)) {
            this.message = message;
            return false;
        }

        return true;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * round trip validate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShopping
     * @return Boolean
     */
    private boolean roundTripValidate(FlightShoppingRQ flightShopping) {

        var message = "The round trip flight information is incorrect.";

        var legs = flightShopping.getLegs();
        var size = legs.size();

        if (size != 2) {
            this.message = message;
            return false;
        }

        var first = legs.get(0);
        var second = legs.get(1);

        if (this.flightPairValidate(first) || this.flightPairValidate(second)) {
            this.message = message;
            return false;
        }

        if (!first.getOrigin().equalsIgnoreCase(second.getDestination())) {
            this.message = message;
            return false;
        }

        if (!first.getDestination().equalsIgnoreCase(second.getOrigin())) {
            this.message = message;
            return false;
        }

        if (first.getDate().compareTo(second.getDate()) > 0) {
            this.message = message;
            return false;
        }

        return true;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * multiple trip validate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShopping
     * @return Boolean
     */
    private boolean multiTripValidate(FlightShoppingRQ flightShopping) {

        var message = "The multiple trip flight information is incorrect.";

        var legs = flightShopping.getLegs();
        var size = legs.size();

        if (size < 2) {
            this.message = message;
            return false;
        }

        var first = legs.get(0);

        for (int i = 1; i < legs.size(); i++) {

            var second = legs.get(i);

            if (this.flightPairValidate(first) || this.flightPairValidate(second)) {
                this.message = message;
                return false;
            }

            if (first.getDate().compareTo(second.getDate()) > 0) {
                this.message = message;
                return false;
            }

            first = second;
        }

        return true;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * pair city code validate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param body
     * @return Boolean
     */
    private boolean flightPairValidate(FlightLegRQ body) {

        if (body.getOrigin() == null || body.getDestination() == null || body.getDate() == null) {
            return false;
        }

        return body.getOrigin().equalsIgnoreCase(body.getDestination());
    }


}
