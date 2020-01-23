package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightShoppingRS;

public interface ShoppingSV {

    SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ);

    ShoppingResponseEntity shoppingAsync(FlightShoppingRQ shoppingRQ);

    ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ);

    FlightShoppingRS shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, String userType, Integer userId);

    Boolean revalidate(BCreateRQ bookingRQ);

}
