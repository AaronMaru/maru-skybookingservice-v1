package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightDetailRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightDetailRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightShoppingRS;

public interface ShoppingSV {

    SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ);

    ShoppingResponseEntity shoppingAsync(FlightShoppingRQ shoppingRQ);

    ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ, long locale, String currency);

    FlightShoppingRS shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, UserAuthenticationMetaTA authenticationMetaTA, String currency, long locale);

    RevalidateM revalidate(BookingCreateRQ bookingRQ);

    FlightDetailRS getFlightDetail(FlightDetailRQ flightDetailRQ, UserAuthenticationMetaTA authenticationMetaTA);

}
