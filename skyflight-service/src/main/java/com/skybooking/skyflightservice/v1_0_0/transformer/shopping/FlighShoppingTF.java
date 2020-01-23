package com.skybooking.skyflightservice.v1_0_0.transformer.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightShoppingRS;
import org.modelmapper.ModelMapper;

public class FlighShoppingTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get flight shopping response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @return FlightShoppingRS
     */
    public static FlightShoppingRS getResponse(ShoppingTransformEntity source) {

        if (source == null) return null;

        var mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        return mapper.map(source, FlightShoppingRS.class);

    }

}
