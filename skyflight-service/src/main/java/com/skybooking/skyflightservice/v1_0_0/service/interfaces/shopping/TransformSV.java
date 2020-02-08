package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;

public interface TransformSV {

    ShoppingTransformEntity getShoppingTransform(ShoppingResponseEntity response);
    ShoppingTransformEntity getShoppingTransformDetail(ShoppingTransformEntity source, long locale);
    ShoppingTransformEntity getShoppingTransformDetailMarkup(ShoppingTransformEntity source, double markup, String currency);
    ShoppingTransformEntity getShoppingTransformDetailWithFilter(ShoppingTransformEntity source);
    ShoppingTransformEntity getShoppingTransformById(String id);
}
