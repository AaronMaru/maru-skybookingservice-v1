package com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;

public interface DetailSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform detail from cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @return ShoppingTransformEntity
     */
    ShoppingTransformEntity getShoppingDetail(String shoppingId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get airline detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param airlineId
     * @return Airline
     */
    Airline getAirlineDetail(String shoppingId, String airlineId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get aircraft detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param aircraftId
     * @return Aircraft
     */
    Aircraft getAircraftDetail(String shoppingId, String aircraftId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get baggage detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param baggageId
     * @return BaggageDetail
     */
    BaggageDetail getBaggageDetail(String shoppingId, String baggageId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get location detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param locationId
     * @return Location
     */
    Location getLocationDetail(String shoppingId, String locationId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param legId
     * @return Leg
     */
    Leg getLegDetail(String shoppingId, String legId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get price detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param priceId
     * @return PriceDetail
     */
    PriceDetail getPriceDetail(String shoppingId, String priceId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get segment detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param segmentId
     * @return Segment
     */
    Segment getSegmentDetail(String shoppingId, String segmentId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get expiration remaining time of shopping in minutes
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @return long
     */
    long getMinutesTimeToLive(String shoppingId);

}
