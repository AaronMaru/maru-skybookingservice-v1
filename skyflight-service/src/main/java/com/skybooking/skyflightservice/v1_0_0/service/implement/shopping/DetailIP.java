package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailIP implements DetailSV {

    @Autowired
    private HazelcastInstance instance;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform detail from cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingDetail(String shoppingId) {
        return (ShoppingTransformEntity) instance.getMap(TransformIP.TRANSFORM_CACHED_NAME).getOrDefault(shoppingId, null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get airline detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param airlineId
     * @return Airline
     */
    @Override
    public Airline getAirlineDetail(String shoppingId, String airlineId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getAirlines()
                .stream()
                .filter(airline -> airline.getCode().equalsIgnoreCase(airlineId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get aircraft detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param aircraftId
     * @return Aircraft
     */
    @Override
    public Aircraft getAircraftDetail(String shoppingId, String aircraftId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getAircrafts()
                .stream()
                .filter(aircraft -> aircraft.getCode().equalsIgnoreCase(aircraftId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get baggage detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param baggageId
     * @return BaggageDetail
     */
    @Override
    public BaggageDetail getBaggageDetail(String shoppingId, String baggageId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getBaggages()
                .stream()
                .filter(baggageDetail -> baggageDetail.getId().equalsIgnoreCase(baggageId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get location detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param locationId
     * @return Location
     */
    @Override
    public Location getLocationDetail(String shoppingId, String locationId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getLocations()
                .stream()
                .filter(location -> location.getCode().equalsIgnoreCase(locationId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param legId
     * @return Leg
     */
    @Override
    public Leg getLegDetail(String shoppingId, String legId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getLegs()
                .stream()
                .filter(leg -> leg.getId().equalsIgnoreCase(legId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get price detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param priceId
     * @return PriceDetail
     */
    @Override
    public PriceDetail getPriceDetail(String shoppingId, String priceId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getPrices()
                .stream()
                .filter(priceDetail -> priceDetail.getId().equalsIgnoreCase(priceId))
                .findFirst()
                .orElse(null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get segment detail by shopping id and id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingId
     * @param segmentId
     * @return Segment
     */
    @Override
    public Segment getSegmentDetail(String shoppingId, String segmentId) {
        var shopping = this.getShoppingDetail(shoppingId);
        if (shopping == null) return null;

        return shopping
                .getSegments()
                .stream()
                .filter(segment -> segment.getId().equalsIgnoreCase(segmentId))
                .findFirst()
                .orElse(null);
    }
}
