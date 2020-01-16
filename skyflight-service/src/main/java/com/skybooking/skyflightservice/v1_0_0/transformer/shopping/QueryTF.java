package com.skybooking.skyflightservice.v1_0_0.transformer.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.FlightLeg;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.FlightShopping;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingQueryEntity;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightLegRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;

import java.util.ArrayList;

public class QueryTF {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get request entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return ShoppingQueryEntity
     */
    public static ShoppingQueryEntity getRequestEntity(FlightShoppingRQ request) {

        var legs = new ArrayList<FlightLeg>();
        for (FlightLegRQ leg : request.getLegs()) {
            legs.add(new FlightLeg(leg.getOrigin(), leg.getDestination(), leg.getDate()));
        }

        return new ShoppingQueryEntity(new FlightShopping(request.getAdult(), request.getChild(), request.getInfant(), request.getTripType(), request.getClassType(), legs));

    }

}
