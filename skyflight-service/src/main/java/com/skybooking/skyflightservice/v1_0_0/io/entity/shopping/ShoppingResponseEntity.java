package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShoppingResponseEntity implements Serializable {

    private String id;
    private List<SabreBargainFinderRS> responses;

    public ShoppingResponseEntity(String id, List<SabreBargainFinderRS> responses) {
        this.id = id;
        this.responses = responses;
    }
}
