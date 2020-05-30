package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;

import java.math.BigDecimal;
import java.util.List;

public interface TransformSV {

    ShoppingTransformEntity getShoppingTransform(ShoppingResponseEntity response);

    ShoppingTransformEntity getShoppingTransformDetail(ShoppingTransformEntity source, long locale, String currency);

    ShoppingTransformEntity getShoppingTransformDetailMarkup(ShoppingTransformEntity source, BigDecimal markup, String currency);

    ShoppingTransformEntity getShoppingTransformDetailWithFilter(ShoppingTransformEntity source);

    ShoppingTransformEntity getShoppingTransformDetailWithFavorite(FlightShoppingRQ flightShoppingRQ, ShoppingTransformEntity source, UserAuthenticationMetaTA userAuthenticationMetaTA);

    ShoppingTransformEntity getShoppingTransformById(String id);

    void setShoppingDetail(String id, ShoppingTransformEntity source);

    ShoppingTransformEntity getShoppingDetail(String id);

    void setNewClassOfService(String id, List<String> classOfService);

    List<String> getNewClassOfService(String id);

    void setFareBasis(String id, List<String> fareBasis);

    List<String> getFareBasis(String id);
}
