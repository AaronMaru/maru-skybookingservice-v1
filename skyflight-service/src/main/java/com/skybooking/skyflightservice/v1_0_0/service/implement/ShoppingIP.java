package com.skybooking.skyflightservice.v1_0_0.service.implement;


import com.skybooking.skyflightservice.v1_0_0.client.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingIP implements ShoppingSV {

    @Autowired
    ShoppingAction shoppingAction;

    @Override
    public SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ) {
        return shoppingAction.getShopping(shoppingRQ);
    }
}
