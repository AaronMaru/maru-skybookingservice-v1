package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.transformer.observer.BookingOS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;

public interface ShoppingSV {

    SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ);

    ShoppingResponseEntity shoppingAsync(FlightShoppingRQ shoppingRQ);

    ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ);

    ShoppingTransformEntity shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, String userType, Integer userId);

    BookingOS revalidate(String[] legs);

    BookingOS checkSeats();

}
