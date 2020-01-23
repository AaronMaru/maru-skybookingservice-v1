package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingQueryEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseSV {

    ShoppingResponseEntity flightShoppingCreate(String id, List<SabreBargainFinderRS> responses, ShoppingQueryEntity query);
    ShoppingResponseEntity flightShoppingById(String id);

}
