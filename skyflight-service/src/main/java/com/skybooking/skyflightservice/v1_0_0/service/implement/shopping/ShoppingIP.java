package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skyflightservice.constant.TripTypeEnum;
import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.FareComponent;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.FareComponentID;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight.FlightInfoSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.FlighShoppingTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightDetailRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.shopping.ShoppingUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    private FlightInfoSV flightInfoSV;


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

        var airlines = flightInfoSV.getFlightInfoEnabled().stream().map(FlightInfoTO::getAirlineCode).collect(Collectors.toList());

        var flightShoppingRQ = new com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightShoppingRQ();

        flightShoppingRQ.setTripType(shoppingRQ.getTripType().toString());
        flightShoppingRQ.setClassType(shoppingRQ.getClassType());
        flightShoppingRQ.setAdult(shoppingRQ.getAdult());
        flightShoppingRQ.setChild(shoppingRQ.getChild());
        flightShoppingRQ.setInfant(shoppingRQ.getInfant());
        flightShoppingRQ.setAirlines(airlines);

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

        return FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailWithFavorite(shoppingRQ, transformSV.getShoppingTransformDetailWithFilter(transformSV.getShoppingTransformDetailMarkup(this.shoppingTransform(shoppingRQ, locale, currency), markUp.getMarkup(), currency)), metadata));

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
    @SneakyThrows
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
        var classOfServicesId = requestId;
        var fareBasisId = requestId;

        List<String> classOfServices = new ArrayList<>();
        SabreBargainFinderRS flightShopping = new SabreBargainFinderRS();

        /** new shopping full match trip type */
        if (!query.getTripType().equals(TripTypeEnum.ONEWAY)) {

            List<FlightLegShoppingRevalidateRQ> legRQS = new ArrayList<>();
            FlightShoppingRevalidateRQ flightShoppingRQ = new FlightShoppingRevalidateRQ();

            flightShoppingRQ.setAdult(query.getAdult());
            flightShoppingRQ.setChild(query.getChild());
            flightShoppingRQ.setInfant(query.getInfant());
            flightShoppingRQ.setClassType(query.getClassType());
            flightShoppingRQ.setTripType(TripTypeEnum.info(query.getTripType()));

            for (String leg : flightDetailRQ.getLegIds()) {

                classOfServicesId = classOfServicesId + leg;
                Leg legDetail = detailSV.getLegDetail(requestId, leg);

                FlightLegShoppingRevalidateRQ legRQ = new FlightLegShoppingRevalidateRQ();
                legRQ.setOrigin(legDetail.getDeparture());
                legRQ.setDestination(legDetail.getArrival());
                legRQ.setDepartureDateTime(legDetail.getDepartureTime().substring(0, 19));
                legRQ.setDepartureWindow(DateUtility.getDepartureWindow(legDetail.getDepartureTime(), 5));
                legRQ.setAirlines(legDetail.getAirlines().stream().map(LegAirline::getAirline).distinct().collect(Collectors.toList()));
                legRQS.add(legRQ);

            }

            flightShoppingRQ.setLegs(legRQS);

            flightShopping = shoppingAction.getShoppingRevalidate(flightShoppingRQ);

            if (flightShopping == null) {
                return null;
            }

            if (flightShopping.getItineraryResponse().getStatistics().getItineraryCount() == 1) {
                var passengerInfo = flightShopping.getItineraryResponse().getItineraryGroups().get(0).getItineraries().get(0).getPricingInformation().get(0).getFare().getPassengerInfoList().get(0).getPassengerInfo();

                if (passengerInfo.getPassengerType().equals(PassengerCode.ADULT)) {
                    for (FareComponentID fareComponentSegment : passengerInfo.getFareComponents()) {
                        classOfServices.add(fareComponentSegment.getSegments().get(0).getSegment().getBookingCode());
                    }

                    transformSV.setNewClassOfService(classOfServicesId, classOfServices);
                }
            }
        }

        /** set request for revalidate flight */
        var indexClassOfService = 0;

        for (String leg : flightDetailRQ.getLegIds()) {
            shoppingDetailId = shoppingDetailId + leg;
            fareBasisId = fareBasisId + leg;
            List<BookingSegmentDRQ> segments = new ArrayList<>();
            Leg legDetail = detailSV.getLegDetail(requestId, leg);

            for (LegSegmentDetail legSegment : legDetail.getSegments()) {

                var classOfService = legSegment.getBookingCode();
                if (!query.getTripType().equals(TripTypeEnum.ONEWAY)
                        && flightShopping.getItineraryResponse().getStatistics().getItineraryCount() == 1
                        && flightShopping.getItineraryResponse().getScheduleComponents().size() == transformSV.getNewClassOfService(classOfServicesId).size()) {
                    classOfService = transformSV.getNewClassOfService(classOfServicesId).get(indexClassOfService);
                }
                segments.add(
                        revalidateFlight.setDSegment(
                                detailSV.getSegmentDetail(requestId, legSegment.getSegment()),
                                legSegment.getDateAdjustment() + legSegment.getPreviousDateAdjustment(),
                                classOfService
                        )
                );

                indexClassOfService++;
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

        var mapper = new ObjectMapper();

        log.debug("SABRE FLIGHT DETAIL: [{}]", shoppingDetailId);
        log.debug("SABRE FLIGHT DETAIL REQUEST: {}", mapper.writeValueAsString(revalidateRQ));

        SabreBargainFinderRS pairCity = shoppingAction.revalidateV2(revalidateRQ);

        log.debug("SABRE FLIGHT DETAIL RESPONSE: {}", mapper.writeValueAsString(pairCity));

        if (pairCity.getItineraryResponse().getStatistics().getItineraryCount() == 0) {
            return null;
        }

        List<String> fareBasis = new ArrayList<>();

        for (FareComponent fareComponent: pairCity.getItineraryResponse().getFareComponents()) {

            var codeRaw = fareComponent.getBasisCode();
            var code = fareComponent.getBasisCode();
            System.out.println("==========" + code);

            if (codeRaw.contains("/CH")) {
                var codeArr = codeRaw.split("/CH");
                code = codeArr[0];
            } else if (codeRaw.contains("/IN")) {
                var codeArr = codeRaw.split("/IN");
                code = codeArr[0];
            }

            fareBasis.add(code);
        }

        System.out.println(fareBasis);

        transformSV.setFareBasis(fareBasisId + "-fare-basis", new ArrayList<>(new HashSet<>(fareBasis)));

        var passengerFareList = pairCity.getItineraryResponse().getItineraryGroups().get(0).getItineraries().get(0).getPricingInformation().get(0).getFare().getPassengerInfoList();

        passengerFareList.forEach(item -> {
            PriceList passengerPrice = new PriceList();
            passengerPrice.setType(item.getPassengerInfo().getPassengerType());
            passengerPrice.setBaseFare(item.getPassengerInfo().getPassengerTotalFare().getEquivalentAmount());
            passengerPrice.setBaseCurrencyBaseFare(item.getPassengerInfo().getPassengerTotalFare().getEquivalentAmount());
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

        // check commission
        var totalSummary = details.stream().map(it -> it.getBaseFare().add(it.getTax()).multiply(BigDecimal.valueOf(it.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalPassenger = details.stream().map(PriceList::getQuantity).reduce(0, Integer::sum);
        var totalCommissionAmount = details.stream().map(PriceList::getCommissionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        var grandTotal = totalSummary.subtract(totalCommissionAmount);
        var grandTotalAvg = grandTotal.divide(BigDecimal.valueOf(totalPassenger), RoundingMode.HALF_UP);
        var totalAverage = totalSummary.divide(BigDecimal.valueOf(totalPassenger), RoundingMode.HALF_DOWN);

        var price = new Price();
        price.setCurrency("USD");
        price.setTotalCommission(totalCommissionAmount);
        price.setTotal(totalSummary);
        price.setGrandTotalAverage(grandTotalAvg);
        price.setGrandTotal(grandTotal);
        price.setTotalAverage(totalAverage);

        priceDetail.setTotalCommissionAmount(totalCommissionAmount);
        priceDetail.setBasePrice(price);
        priceDetail.setPrice(price);

        if (totalCommissionAmount.doubleValue() > 0) {
            priceDetail.setCommission(true);
        }

        priceDetails.add(priceDetail);

        ShoppingTransformEntity shoppingTransformEntity = transformSV.getShoppingTransformById(requestId);
        shoppingTransformEntity.setPrices(priceDetails);

        /** insert cached data */
        transformSV.setShoppingDetail(shoppingDetailId, shoppingTransformEntity);

        var markUp = shoppingUtils.getUserMarkupPrice(metadata, querySV.flightShoppingById(requestId).getQuery().getClassType());

        var response = FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailMarkup(transformSV.getShoppingTransformDetail(shoppingTransformEntity, headerBean.getLocalizationId(), headerBean.getCurrencyCode()), markUp.getMarkup(), headerBean.getCurrencyCode()));

        MutableMap<String, SegmentRS> segmentMapRS = UnifiedMap.newMap();
        MutableMap<String, AirlineRS> airlineMapRS = UnifiedMap.newMap();
        MutableMap<String, AircraftRS> aircraftMapRS = UnifiedMap.newMap();
        MutableMap<String, LocationRS> locationMapRS = UnifiedMap.newMap();
        MutableMap<String, LegRS> legMapRS = UnifiedMap.newMap();
        MutableMap<String, BaggageDetailRS> baggageMapRS = UnifiedMap.newMap();

        for (SegmentRS segment : response.getSegments()) {
            segmentMapRS.put(segment.getId(), segment);
        }

        for (AirlineRS airline : response.getAirlines()) {
            airlineMapRS.put(airline.getCode(), airline);

        }

        for (AircraftRS aircraft : response.getAircrafts()) {
            aircraftMapRS.put(aircraft.getCode(), aircraft);
        }

        for (LocationRS location : response.getLocations()) {
            locationMapRS.put(location.getCode(), location);
        }

        for (LegRS leg : response.getLegs()) {
            legMapRS.put(leg.getId(), leg);
        }

        for (BaggageDetailRS baggage : response.getBaggages()) {
            baggageMapRS.put(baggage.getId(), baggage);
        }

        List<LegRS> legs = new ArrayList<>();
        List<PriceDetailRS> prices = new ArrayList<>();
        List<BaggageDetailRS> baggages = new ArrayList<>();

        MutableMap<String, SegmentRS> segments = UnifiedMap.newMap();
        MutableMap<String, AirlineRS> airlines = UnifiedMap.newMap();
        MutableMap<String, AircraftRS> aircrafts = UnifiedMap.newMap();
        MutableMap<String, LocationRS> locations = UnifiedMap.newMap();

        for (String legId : flightDetailRQ.getLegIds()) {

            var legDetail = legMapRS.get(legId);

            baggages.add(baggageMapRS.get(legDetail.getBaggage()));

            for (LegSegmentDetailRS segmentDetailRS : legDetail.getSegments()) {

                var segment = segmentMapRS.get(segmentDetailRS.getSegment());
                var airline = airlineMapRS.get(segment.getAirline());
                var aircraft = aircraftMapRS.get(segment.getAircraft());
                var operatingAirline = airlineMapRS.get(segment.getOperatingAirline());

                airlines.putIfAbsent(airline.getCode(), airline);
                airlines.putIfAbsent(operatingAirline.getCode(), operatingAirline);
                aircrafts.putIfAbsent(aircraft.getCode(), aircraft);

            }

            for (LegSegmentDetailRS legSegmentDetailRS : legDetail.getSegments()) {

                var segment = segmentMapRS.get(legSegmentDetailRS.getSegment());

                segments.putIfAbsent(legSegmentDetailRS.getSegment(), segment);

                if (legSegmentDetailRS.getLayoverAirport() != null) {
                    locations.putIfAbsent(legSegmentDetailRS.getLayoverAirport(), locationMapRS.get(legSegmentDetailRS.getLayoverAirport()));
                }

                if (segment.getStopCount() > 0) {

                    for (HiddenStopRS hiddenStop : segment.getHiddenStops()) {
                        locations.putIfAbsent(hiddenStop.getAirport(), locationMapRS.get(hiddenStop.getAirport()));
                    }

                }

                locations.put(segment.getDeparture(), locationMapRS.get(segment.getDeparture()));
                locations.put(segment.getArrival(), locationMapRS.get(segment.getArrival()));

            }

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

        log.debug("SABRE FLIGHT DETAIL TRANSFORM RESPONSE: {}", mapper.writeValueAsString(flightDetail));

        return flightDetail;

    }

}
