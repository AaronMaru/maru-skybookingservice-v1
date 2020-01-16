package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingQueryEntity;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import org.springframework.stereotype.Service;

@Service
public interface QuerySV {

    ShoppingQueryEntity flightShoppingCreate(FlightShoppingRQ request);

    ShoppingQueryEntity flightShoppingExist(FlightShoppingRQ request);

}
