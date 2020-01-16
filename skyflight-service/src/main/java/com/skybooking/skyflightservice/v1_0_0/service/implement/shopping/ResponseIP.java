package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ResponseSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseIP implements ResponseSV {

    public static final String RESPONSE_CACHED_NAME = "shopping-response";

    @Autowired
    private HazelcastInstance instance;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create shopping sabre response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param responses
     * @return ShoppingResponseEntity
     */
    @Override
    public ShoppingResponseEntity flightShoppingCreate(String id, List<SabreBargainFinderRS> responses) {
        var shopping = new ShoppingResponseEntity(id, responses);

        instance.getMap(RESPONSE_CACHED_NAME).put(shopping.getId(), shopping);

        return shopping;
    }

    @Override
    public ShoppingResponseEntity flightShoppingById(String id) {
        return (ShoppingResponseEntity) instance.getMap(RESPONSE_CACHED_NAME).getOrDefault(id, null);
    }
}
