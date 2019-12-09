package com.skybooking.skyflightservice.v1_0_0.service.interfaces;


import com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingSV {
     SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ);
}
