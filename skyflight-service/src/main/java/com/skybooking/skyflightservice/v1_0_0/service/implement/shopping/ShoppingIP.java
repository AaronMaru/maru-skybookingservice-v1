package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ResponseSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.FlighShoppingTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightDetailRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.shopping.ShoppingUtils;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingIP implements ShoppingSV {

    @Autowired
    private ShoppingAction shoppingAction;

    @Autowired
    private RevalidateFlight revalidateFlight;

    @Autowired
    private QuerySV querySV;

    @Autowired
    private ResponseSV responseSV;

    @Autowired
    private TransformSV transformSV;

    @Autowired
    private ShoppingUtils shoppingUtils;

    @Autowired
    private HeaderBean headerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get sabre shopping
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @return SabreBargainFinderRS
     */
    @Override
    public SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ) {
        return shoppingAction.getShopping(shoppingRQ).block();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping as parallel requests
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @return ShoppingResponseEntity
     */
    @Override
    public ShoppingResponseEntity shoppingAsync(FlightShoppingRQ shoppingRQ) {

        var query = querySV.flightShoppingExist(shoppingRQ);

        if (query == null) {
            query = querySV.flightShoppingCreate(shoppingRQ);
        }

        var responses = shoppingAction.getShoppingList(shoppingRQ);

        return responseSV.flightShoppingCreate(query.getId(), responses, query);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data with markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @param metadata
     * @param currency
     * @param locale
     * @return ShoppingTransformEntity
     */
    @Override
    public FlightShoppingRS shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, UserAuthenticationMetaTA metadata, String currency, long locale) {

        var markUp = shoppingUtils.getUserMarkupPrice(metadata, shoppingRQ.getClassType());

        return FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailWithFavorite(shoppingRQ, transformSV.getShoppingTransformDetailWithFilter(transformSV.getShoppingTransformDetailMarkup(this.shoppingTransform(shoppingRQ, locale, currency), markUp.getMarkup().doubleValue(), currency)), metadata));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @param locale
     * @param currency
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ, long locale, String currency) {

        var query = querySV.flightShoppingExist(shoppingRQ);

        var transform = new ShoppingTransformEntity();

        if (query != null) {
            transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransformById(query.getId()), locale, currency);

            if (transform == null) {
                querySV.flightShoppingRemove(shoppingRQ);
            } else {
                return transform;
            }
        }

        transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransform(this.shoppingAsync(shoppingRQ)), locale, currency);

        if (transform == null) {
            querySV.flightShoppingRemove(shoppingRQ);
        }

        return transform;
    }


    @Override
    public RevalidateM revalidate(BookingCreateRQ request) {
        return revalidateFlight.revalidate(request);
    }


    @Override
    public FlightDetailRS getFlightDetail(FlightDetailRQ flightDetailRQ, UserAuthenticationMetaTA metadata) {

        var flightDetail = new FlightDetailRS();
        var requestId = flightDetailRQ.getRequestId();
        var markUp = shoppingUtils.getUserMarkupPrice(metadata, querySV.flightShoppingById(requestId).getQuery().getClassType());
        var response = FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailMarkup(transformSV.getShoppingTransformDetail(transformSV.getShoppingTransformById(requestId), headerBean.getLocalizationId(), headerBean.getCurrencyCode()), markUp.getMarkup().doubleValue(), headerBean.getCurrencyCode()));

        List<LegRS> legs = new ArrayList<>();
        List<PriceDetailRS> prices = new ArrayList<>();
        List<BaggageDetailRS> baggages = new ArrayList<>();
        MutableMap<String, SegmentRS> segments = UnifiedMap.newMap();
        MutableMap<String, AirlineRS> airlines = UnifiedMap.newMap();
        MutableMap<String, AircraftRS> aircrafts = UnifiedMap.newMap();
        MutableMap<String, LocationRS> locations = UnifiedMap.newMap();

        for (String legId : flightDetailRQ.getLegIds()) {
            var legDetail = response.getLegs().stream().filter(item -> item.getId().equalsIgnoreCase(legId)).findFirst().get();

            prices.add(response.getPrices().stream().filter(item -> item.getId().equalsIgnoreCase(legDetail.getPrice())).findFirst().get());
            baggages.add(response.getBaggages().stream().filter(item -> item.getId().equalsIgnoreCase(legDetail.getBaggage())).findFirst().get());

            for (LegAirlineRS legAirlineRS : legDetail.getAirlines()) {
                airlines.putIfAbsent(legAirlineRS.getAirline(), response.getAirlines().stream().filter(item -> item.getCode().equalsIgnoreCase(legAirlineRS.getAirline())).findFirst().get());
                aircrafts.putIfAbsent(legAirlineRS.getAircraft(), response.getAircrafts().stream().filter(item -> item.getCode().equalsIgnoreCase(legAirlineRS.getAircraft())).findFirst().get());
            }

            var locationsRS = response.getLocations();
            for (LegSegmentDetailRS legSegmentDetailRS : legDetail.getSegments()) {
                segments.putIfAbsent(legSegmentDetailRS.getSegment(), response.getSegments().stream().filter(item -> item.getId().equalsIgnoreCase(legSegmentDetailRS.getSegment())).findFirst().get());

                if (legSegmentDetailRS.getLayoverAirport() != null) {
                    locations.putIfAbsent(legSegmentDetailRS.getLayoverAirport(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legSegmentDetailRS.getLayoverAirport())).findFirst().get());
                }
            }

            locations.putIfAbsent(legDetail.getDeparture(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legDetail.getDeparture())).findFirst().get());
            locations.putIfAbsent(legDetail.getArrival(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legDetail.getArrival())).findFirst().get());

            legs.add(legDetail);
        }

        flightDetail.setLegs(legs);
        flightDetail.setPrices(prices);
        flightDetail.setBaggages(baggages);
        flightDetail.setSegments(segments.toList());
        flightDetail.setAirlines(airlines.toList());
        flightDetail.setAircrafts(aircrafts.toList());
        flightDetail.setLocations(locations.toList());

        return flightDetail;

    }

}
