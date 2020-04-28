package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightLegRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.OriginDestination;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.RevalidateRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.FlighShoppingTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightDetailRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;
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

    @Autowired
    private DetailSV detailSV;



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

        var flightShoppingRQ = new com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightShoppingRQ();

        flightShoppingRQ.setTripType(shoppingRQ.getTripType().toString());
        flightShoppingRQ.setClassType(shoppingRQ.getClassType());
        flightShoppingRQ.setAdult(shoppingRQ.getAdult());
        flightShoppingRQ.setChild(shoppingRQ.getChild());
        flightShoppingRQ.setInfant(shoppingRQ.getInfant());

        shoppingRQ.getLegs().forEach(flightLegRQ -> {

            var leg = new FlightLegRQ();
            leg.setOrigin(flightLegRQ.getDeparture());
            leg.setDestination(flightLegRQ.getArrival());
            leg.setDate(flightLegRQ.getDate().toString());

            flightShoppingRQ.getLegs().add(leg);
        });


        return shoppingAction.getShopping(flightShoppingRQ).block();
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

        var query = querySV.flightShoppingCreate(shoppingRQ);

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

        var transform = new ShoppingTransformEntity();
        transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransform(this.shoppingAsync(shoppingRQ)), locale, currency);

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
        var query = querySV.flightShoppingById(requestId).getQuery();
        RevalidateRQ revalidateRQ = new RevalidateRQ();
        revalidateRQ.setAdult(query.getAdult());
        revalidateRQ.setChild(query.getChild());
        revalidateRQ.setInfant(query.getInfant());
        List<OriginDestination> originDestinations = new ArrayList<>();
        List<PriceDetail> priceDetails = new ArrayList<>();
        PriceDetail priceDetail = new PriceDetail();
        List<PriceList> details = new ArrayList<>();
        var shoppingDetailId = requestId;

        /** set request for revalidate flight */
        for (String leg: flightDetailRQ.getLegIds()) {
            shoppingDetailId = shoppingDetailId + leg;
            List<BookingSegmentDRQ> segments = new ArrayList<>();
            Leg legDetail = detailSV.getLegDetail(requestId, leg);
            for (LegSegmentDetail legSegment: legDetail.getSegments()) {
                segments.add(
                    revalidateFlight.setDSegment(
                        detailSV.getSegmentDetail(requestId, legSegment.getSegment()),
                        legSegment.getDateAdjustment(),
                        legSegment.getBookingCode()
                    )
                );
            }

            originDestinations.add(
                new OriginDestination(
                    segments,
                    legDetail.getDepartureTime(),
                    legDetail.getDeparture(),
                    legDetail.getArrival()
                )
            );
        }

        revalidateRQ.setOriginDestinations(originDestinations);
        SabreBargainFinderRS pairCity = shoppingAction.revalidateV2(revalidateRQ);

        if (pairCity.getItineraryResponse().getStatistics().getItineraryCount() == 0) {
            return null;
        }

        var passengerFareList = pairCity.getItineraryResponse().getItineraryGroups().get(0).getItineraries().get(0).getPricingInformation().get(0).getFare().getPassengerInfoList();

        passengerFareList.forEach(item -> {
            PriceList passengerPrice = new PriceList();
            passengerPrice.setType(item.getPassengerInfo().getPassengerType());
            passengerPrice.setBaseFare(item.getPassengerInfo().getPassengerTotalFare().getBaseFareAmount());
            passengerPrice.setBaseCurrencyBaseFare(item.getPassengerInfo().getPassengerTotalFare().getBaseFareAmount());
            passengerPrice.setTax(item.getPassengerInfo().getPassengerTotalFare().getTotalTaxAmount());
            passengerPrice.setBaseCurrencyTax(item.getPassengerInfo().getPassengerTotalFare().getTotalTaxAmount());
            passengerPrice.setCommissionAmount(item.getPassengerInfo().getPassengerTotalFare().getCommissionAmount());
            passengerPrice.setCommissionPercentage(item.getPassengerInfo().getPassengerTotalFare().getCommissionPercentage());
            passengerPrice.setCurrency(headerBean.getCurrencyCode());
            passengerPrice.setBaseCurrency("USD");
            passengerPrice.setQuantity(item.getPassengerInfo().getPassengerNumber());
            details.add(passengerPrice);
        });

        priceDetail.setDetails(details);
        priceDetails.add(priceDetail);

        ShoppingTransformEntity shoppingTransformEntity = transformSV.getShoppingTransformById(requestId);
        shoppingTransformEntity.setPrices(priceDetails);
        /** insert cached data */
        transformSV.setShoppingDetail(shoppingDetailId, shoppingTransformEntity);

        var markUp = shoppingUtils.getUserMarkupPrice(metadata, querySV.flightShoppingById(requestId).getQuery().getClassType());
        var response = FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailMarkup(transformSV.getShoppingTransformDetail(shoppingTransformEntity, headerBean.getLocalizationId(), headerBean.getCurrencyCode()), markUp.getMarkup().doubleValue(), headerBean.getCurrencyCode()));

        List<LegRS> legs = new ArrayList<>();
        List<PriceDetailRS> prices = new ArrayList<>();
        List<BaggageDetailRS> baggages = new ArrayList<>();
        MutableMap<String, SegmentRS> segments = UnifiedMap.newMap();
        MutableMap<String, AirlineRS> airlines = UnifiedMap.newMap();
        MutableMap<String, AircraftRS> aircrafts = UnifiedMap.newMap();
        MutableMap<String, LocationRS> locations = UnifiedMap.newMap();

        for (String legId : flightDetailRQ.getLegIds()) {
            var legDetail = response.getLegs().stream().filter(item -> item.getId().equalsIgnoreCase(legId)).findFirst().get();

//            prices.add(response.getPrices().stream().filter(item -> item.getId().equalsIgnoreCase(legDetail.getPrice())).findFirst().get());
            baggages.add(response.getBaggages().stream().filter(item -> item.getId().equalsIgnoreCase(legDetail.getBaggage())).findFirst().get());

            for (LegAirlineRS legAirlineRS : legDetail.getAirlines()) {
                airlines.putIfAbsent(legAirlineRS.getAirline(), response.getAirlines().stream().filter(item -> item.getCode().equalsIgnoreCase(legAirlineRS.getAirline())).findFirst().get());
                aircrafts.putIfAbsent(legAirlineRS.getAircraft(), response.getAircrafts().stream().filter(item -> item.getCode().equalsIgnoreCase(legAirlineRS.getAircraft())).findFirst().get());
            }

            var locationsRS = response.getLocations();

            for (LegSegmentDetailRS legSegmentDetailRS : legDetail.getSegments()) {

                var segment = response.getSegments().stream().filter(item -> item.getId().equalsIgnoreCase(legSegmentDetailRS.getSegment())).findFirst().get();

                segments.putIfAbsent(legSegmentDetailRS.getSegment(), segment);

                if (legSegmentDetailRS.getLayoverAirport() != null) {
                    locations.putIfAbsent(legSegmentDetailRS.getLayoverAirport(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legSegmentDetailRS.getLayoverAirport())).findFirst().get());
                }

                if (segment.getStopCount() > 0) {

                    for (HiddenStopRS hiddenStop : segment.getHiddenStops()) {
                        locations.putIfAbsent(hiddenStop.getAirport(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(hiddenStop.getAirport())).findFirst().get());
                    }

                }

            }

            locations.putIfAbsent(legDetail.getDeparture(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legDetail.getDeparture())).findFirst().get());
            locations.putIfAbsent(legDetail.getArrival(), locationsRS.stream().filter(item -> item.getCode().equalsIgnoreCase(legDetail.getArrival())).findFirst().get());

            legs.add(legDetail);
        }

        prices.add(response.getPrices().get(0));
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
