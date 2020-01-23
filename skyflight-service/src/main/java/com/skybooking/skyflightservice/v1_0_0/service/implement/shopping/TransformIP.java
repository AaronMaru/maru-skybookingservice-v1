package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.allowance.BaggageAllowance;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.FareComponent;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.FareComponentID;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.FareComponentSegment;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.ItineraryGroupType;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.PassengerInfoList;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.Schedule;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.ScheduleComponent;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AircraftNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TransformIP implements TransformSV {

    public static final String TRANSFORM_CACHED_NAME = "shopping-transform";

    @Autowired
    private HazelcastInstance instance;

    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private AirlineNQ airlineNQ;

    @Autowired
    private AircraftNQ aircraftNQ;

    @Autowired
    private AppConfig appConfig;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param responses
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransform(ShoppingResponseEntity responses) {

        if (responses.getResponses() == null) return null;

        var transform = new ShoppingTransformEntity();

        transform.setTrip(responses.getQuery().getQuery().getTripType());

        // transform items
        var mAirlines = new TreeMap<String, Airline>();
        var mAircrafts = new TreeMap<String, Aircraft>();
        var mLocations = new TreeMap<String, Location>();
        var mPrices = new HashMap<String, PriceDetail>();
        var mBaggages = new HashMap<String, BaggageDetail>();
        var mSegments = new HashMap<String, Segment>();
        var mLegs = new HashMap<String, Leg>();
        var mDirect = new ArrayList<Itinerary>();
        var mCheapest = new ArrayList<Itinerary>();

        // cached items
        var cachedSchedules = new HashMap<String, ScheduleComponent>();
        var cachedLegs = new HashMap<String, com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.leg.Leg>();
        var cachedFares = new HashMap<String, FareComponent>();
        var cachedBaggage = new HashMap<String, BaggageAllowance>();

        // reference items
        var referenceScheduleSegment = new HashMap<String, String>();
        var referenceLegLeg = new HashMap<String, String>();

        mDirect.add(new Itinerary());

        // map => (airline, list => itinerary)
        var directLegDesc = new TreeMap<String, Itinerary>();

        int responseIndex = 0;

        for (SabreBargainFinderRS response : responses.getResponses()) {

            if (response == null) return null;

            var totalItinerary = response.getItineraryResponse().getStatistics().getItineraryCount();

            if (totalItinerary == null || totalItinerary == 0) {
                return null;
            }

            var legDescription = response.getItineraryResponse().getItineraryGroups().get(0).getGroupDescription().getLegDescriptions().get(0);

            mCheapest.add(new Itinerary());

            // schedules
            for (ScheduleComponent schedule : response.getItineraryResponse().getScheduleComponents()) {

                var carrier = schedule.getCarrier();

                // airline
                var marketing = new Airline();
                marketing.setCode(carrier.getMarketing());
                marketing.setName(carrier.getMarketing());

                var operating = new Airline();
                operating.setCode(carrier.getOperating());
                operating.setName(carrier.getOperating());

                mAirlines.putIfAbsent(marketing.getCode(), marketing);

                // aircraft
                var aircraft = new Aircraft();
                aircraft.setCode(carrier.getEquipment().getCode());
                aircraft.setName(carrier.getEquipment().getCode());

                mAircrafts.putIfAbsent(aircraft.getCode(), aircraft);

                // location
                var departure = new Location();
                departure.setCode(schedule.getDeparture().getAirport());
                departure.setAirport(schedule.getDeparture().getAirport());
                departure.setCity(schedule.getDeparture().getCity());
                departure.setLatitude(0);
                departure.setLongitude(0);

                var arrival = new Location();
                arrival.setCode(schedule.getArrival().getAirport());
                arrival.setAirport(schedule.getArrival().getAirport());
                arrival.setCity(schedule.getArrival().getCity());
                arrival.setLatitude(0);
                arrival.setLongitude(0);

                mLocations.putIfAbsent(departure.getCode(), departure);
                mLocations.putIfAbsent(arrival.getCode(), arrival);

                // segment
                var segmentId = responseIndex + "-" + schedule.getDeparture().getAirport() + "-" + schedule.getArrival().getAirport() + "-" + schedule.getId();

                var segment = new Segment();
                segment.setId(segmentId);

                segment.setDeparture(schedule.getDeparture().getAirport());
                segment.setDepartureTime(legDescription.getDepartureDate() + "T" + schedule.getDeparture().getTime());
                segment.setDepartureTerminal(schedule.getDeparture().getTerminal());
                segment.setArrival(schedule.getArrival().getAirport());
                segment.setArrivalTime(legDescription.getDepartureDate() + "T" + schedule.getArrival().getTime());
                segment.setArrivalTerminal(schedule.getArrival().getTerminal());

                var marketingFlightNumber = schedule.getCarrier().getMarketingFlightNumber() == null ? "" : schedule.getCarrier().getMarketingFlightNumber().toString();
                var operatingFlightNumber = schedule.getCarrier().getOperatingFlightNumber() == null ? "" : schedule.getCarrier().getOperatingFlightNumber().toString();

                segment.setFlightNumber(marketingFlightNumber);
                segment.setOperatingAirline(schedule.getCarrier().getOperating());
                segment.setOperatingFlightNumber(operatingFlightNumber);
                segment.setAirline(schedule.getCarrier().getMarketing());
                segment.setAircraft(schedule.getCarrier().getEquipment().getCode());

                segment.setStopCount(schedule.getStopCount());
                segment.setDirectionIndex(responseIndex);

                // inner adjustment segment
                var arrivalAdjustmentDate = schedule.getArrival().getDateAdjustment() == null ? 0 : schedule.getArrival().getDateAdjustment();

                if (arrivalAdjustmentDate > 0) {
                    segment.setArrivalTime(DateUtility.plusDays(segment.getArrivalTime(), arrivalAdjustmentDate));
                }

                if (segment.getStopCount() > 0) {

                    var hiddenStops = schedule
                        .getHiddenStops()
                        .stream()
                        .map(hiddenStop -> {

                            var stop = new HiddenStop();
                            stop.setAirport(hiddenStop.getAirport());
                            stop.setCity(hiddenStop.getCity());
                            stop.setCountry(hiddenStop.getCountry());
                            stop.setArrivalTime(legDescription.getDepartureDate() + "T" + hiddenStop.getArrivalTime());
                            stop.setDepartureTime(legDescription.getDepartureDate() + "T" + hiddenStop.getDepartureTime());

                            var arrivalStopAdjustmentDate = hiddenStop.getArrivalDateAdjustment() == null ? 0 : hiddenStop.getArrivalDateAdjustment();
                            var departureStopAdjustmentDate = hiddenStop.getDepartureDateAdjustment() == null ? 0 : hiddenStop.getDepartureDateAdjustment();

                            if (arrivalStopAdjustmentDate > 0) {
                                stop.setArrivalTime(DateUtility.plusDays(stop.getArrivalTime(), arrivalAdjustmentDate));
                            }

                            if (departureStopAdjustmentDate > 0) {
                                stop.setDepartureTime(DateUtility.plusDays(stop.getDepartureTime(), departureStopAdjustmentDate));
                            }

                            stop.setDuration(hiddenStop.getElapsedLayoverTime());

                            return stop;

                        }).collect(Collectors.toList());

                    segment.getHiddenStops().addAll(hiddenStops);

                }

                mSegments.put(segmentId, segment);

                // cached
                var cachedId = responseIndex + "-" + schedule.getId();
                cachedSchedules.put(cachedId, schedule);
                referenceScheduleSegment.put(cachedId, segmentId);

            }

            // map => (airline, legGroup)
            var cheapLegDesc = new TreeMap<String, LegGroup>();

            // legs
            for (com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.leg.Leg legResponse : response.getItineraryResponse().getLegs()) {

                var leg = new Leg();

                var airline = "";

                for (Schedule schedule : legResponse.getSchedules()) {

                    var cachedId = responseIndex + "-" + schedule.getId();
                    var mSegment = mSegments.get(referenceScheduleSegment.get(cachedId));

                    var legSegmentDetail = new LegSegmentDetail();
                    legSegmentDetail.setSegment(mSegment.getId());

                    if (schedule.getDepartureDateAdjustment() != null) {
                        legSegmentDetail.setDateAdjustment(schedule.getDepartureDateAdjustment());
                    }

                    var legAirline = new LegAirline();
                    legAirline.setAirline(mSegment.getAirline());
                    legAirline.setAircraft(mSegment.getAircraft());
                    legAirline.setFlightNumber(mSegment.getFlightNumber());

                    leg.getSegments().add(legSegmentDetail);
                    leg.getAirlines().add(legAirline);


                    if (!airline.isEmpty() && !airline.equalsIgnoreCase(mSegment.getAirline())) {
                        leg.setMultiAir(true);
                    }

                    airline = mSegment.getAirline();

                }

                var totalSegment = leg.getSegments().size();
                var firstSegmentId = leg.getSegments().get(0).getSegment();

                var lastSegmentId = leg.getSegments().get(totalSegment - 1).getSegment();

                var firstSegment = mSegments.get(firstSegmentId);
                var lastSegment = mSegments.get(lastSegmentId);

                // leg.setDuration(DateUtility.getMinuteDurations(firstSegment.getDepartureTime(), lastSegment.getArrivalTime()));

                leg.setDeparture(firstSegment.getDeparture());
                leg.setDepartureTime(firstSegment.getDepartureTime());
                leg.setDepartureTerminal(firstSegment.getDepartureTerminal());

                leg.setArrival(lastSegment.getArrival());
                leg.setArrivalTime(lastSegment.getArrivalTime());
                leg.setArrivalTerminal(lastSegment.getArrivalTerminal());

                var legId = responseIndex + "-" + leg.getDeparture() + "-" + leg.getArrival() + "-" + legResponse.getId();
                leg.setId(legId);
                leg.setDirectionIndex(responseIndex);

                // set direct flight

                var directAirline = firstSegment.getAirline();
                var directLegGroups = directLegDesc.getOrDefault(directAirline, new Itinerary());

                directLegGroups.setAirline(directAirline);

                if (directLegGroups.getLegGroups().size() < responseIndex + 1) {
                    directLegGroups.getLegGroups().add(new LegGroup());
                }


                if (totalSegment == 1 && firstSegment.getStopCount() == 0) {

                    var directLegDescription = new LegDescription();
                    directLegDescription.setLeg(legId);

                    if (!directLegGroups.getLegGroups().isEmpty()) {
                        var size = directLegGroups.getLegGroups().size() - 1;
                        directLegGroups.getLegGroups().get(size).getLegsDesc().add(directLegDescription);
                    }

                    leg.setDirectFlight(true);

                }

                directLegDesc.put(directAirline, directLegGroups);

                // set cheap flight

                var legGroupAirline = cheapLegDesc.getOrDefault(firstSegment.getAirline(), new LegGroup());
                legGroupAirline.setAirline(firstSegment.getAirline());

                var legDescAirline = new LegDescription();
                legDescAirline.setLeg(legId);
                legGroupAirline.getLegsDesc().add(legDescAirline);

                cheapLegDesc.put(firstSegment.getAirline(), legGroupAirline);

                mLegs.put(legId, leg);

                // cached
                var cachedId = responseIndex + "-" + legResponse.getId();
                cachedLegs.put(cachedId, legResponse);
                referenceLegLeg.put(cachedId, legId);

            }

            mCheapest.get(responseIndex).getLegGroups().addAll(cheapLegDesc.values());

            // fare
            for (FareComponent fareComponent : response.getItineraryResponse().getFareComponents()) {
                // cached
                var cachedId = responseIndex + "-" + fareComponent.getId();
                cachedFares.put(cachedId, fareComponent);
            }

            // baggage
            for (BaggageAllowance baggageAllowance : response.getItineraryResponse().getBaggageAllowances()) {

                var cachedId = responseIndex + "-" + baggageAllowance.getId();

                var piece = baggageAllowance.getPieceCount() == null ? 0 : baggageAllowance.getPieceCount();
                var unit = baggageAllowance.getUnit() == null ? "kg" : baggageAllowance.getUnit();
                var weight = baggageAllowance.getWeight() == null ? 20 : baggageAllowance.getWeight();

                var baggage = new BaggageAllowance();
                baggage.setPieceCount(piece);
                baggage.setUnit(unit);
                baggage.setWeight(weight);

                cachedBaggage.put(cachedId, baggage);
            }

            // itinerary
            var itineraryIndex = 0;

            for (ItineraryGroupType itineraryGroup : response.getItineraryResponse().getItineraryGroups()) {

                for (com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.Itinerary itinerary : itineraryGroup.getItineraries()) {
                    var priceDetail = new PriceDetail();
                    var baggageDetail = new BaggageDetail();

                    var legId = responseIndex + "-" + itinerary.getLegs().get(0).getId();
                    var leg = mLegs.get(referenceLegLeg.get(legId));

                    // pricing information list
                    var priceInformation = itinerary.getPricingInformation().get(0);
                    var passengerInfoList = priceInformation.getFare().getPassengerInfoList();

                    for (PassengerInfoList passengerInfo : passengerInfoList) {
                        var passenger = passengerInfo.getPassengerInfo();

                        var price = new Price();

                        price.setType(passenger.getPassengerType());
                        price.setQuantity(passenger.getPassengerNumber());
                        price.setBaseFare(passenger.getPassengerTotalFare().getEquivalentAmount());
                        price.setTax(passenger.getPassengerTotalFare().getTotalTaxAmount());
                        price.setCurrency(passenger.getPassengerTotalFare().getCurrency());

                        priceDetail.getDetails().add(price);

                        var baggageAllowanceId = responseIndex + "-" + passenger.getBaggageInformation().get(0).getAllowance().getId();
                        var baggageAllowance = cachedBaggage.get(baggageAllowanceId);


                        var baggage = new Baggage();
                        baggage.setType(passenger.getPassengerType());
                        baggage.setPiece(baggageAllowance.getPieceCount());
                        baggage.setUnit(baggageAllowance.getUnit());
                        baggage.setWeight(baggageAllowance.getWeight());
                        baggage.setNonRefundable(passenger.getNonRefundable());

                        baggageDetail.getDetails().add(baggage);

                        var segments = new ArrayList<com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.Segment>();

                        for (FareComponentID fareComponent : passenger.getFareComponents()) {
                            for (FareComponentSegment fareSegment : fareComponent.getSegments()) {
                                segments.add(fareSegment.getSegment());
                            }
                        }

                        var segmentIndex = 0;

                        for (com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.Segment segmentStatus : segments) {

                            var availabilityBreak = segmentStatus.getAvailabilityBreak() == null ? false : segmentStatus.getAvailabilityBreak();
                            leg.getSegments().get(segmentIndex).setAvailabilityBreak(availabilityBreak);

                            var segmentId = leg.getSegments().get(segmentIndex).getSegment();
                            var segment = mSegments.get(segmentId);

                            var seats = segmentStatus.getSeatsAvailable() == null ? 0 : segmentStatus.getSeatsAvailable();
                            var cabin = segmentStatus.getCabinCode();
                            var cabinName = cabin;
                            var meal = segmentStatus.getMealCode() == null ? "" : segmentStatus.getMealCode();
                            var mealName = meal;
                            var bookingCode = segmentStatus.getBookingCode();

                            segment.setBookingCode(bookingCode);
                            segment.setSeatsRemain(seats);
                            segment.setCabin(cabin);
                            segment.setCabinName(cabinName);
                            segment.setMeal(meal);
                            segment.setMealName(mealName);

                            mSegments.replace(segmentId, segment);

                            segmentIndex++;
                        }

                    }

                    var priceId = "P" + "-" + legId + "-" + itineraryIndex + "-" + itinerary.getId();
                    priceDetail.setId(priceId);
                    priceDetail.setCurrency("USD");

                    var baggageId = "B" + "-" + legId + "-" + itineraryIndex + "-" + itinerary.getId();
                    baggageDetail.setId(baggageId);

                    mPrices.put(priceId, priceDetail);
                    mBaggages.put(baggageId, baggageDetail);

                    leg.setBaggage(baggageId);
                    leg.setPrice(priceId);

                    mLegs.replace(legId, leg);
                }

                itineraryIndex++;
            }

            responseIndex++;
        }


        // filter direct flight

        var direct = directLegDesc.values()
            .stream()
            .filter(directItinerary -> (directItinerary.getLegGroups().stream().noneMatch(legGroup -> (legGroup.getLegsDesc().isEmpty()))))
            .collect(Collectors.toList());

        // permutation cheapest flight
        var itinerarySize = 0;

        for (Itinerary itinerary : mCheapest) {
            var size = itinerary.getLegGroups().size();
            if (size > itinerarySize) {
                itinerarySize = size;
            }
        }

        // expand cheapest flight elements
        var cheapestResponseIndex = 0;
        for (Itinerary itinerary : mCheapest) {

            var size = itinerary.getLegGroups().size();
            var totalExpandSize = itinerarySize - size;
            var totalRound = Math.floorDiv(itinerarySize, size) + Math.floorMod(itinerarySize, size);

            do {

                for (int index = 0; index < totalExpandSize; index++) {
                    var leg = mCheapest.get(cheapestResponseIndex).getLegGroups().get(index);
                    mCheapest.get(cheapestResponseIndex).getLegGroups().add(leg);
                }

                totalRound--;

            } while (totalRound == 0);

            cheapestResponseIndex++;
        }

        // arrange cheapest flight elements
        List<Itinerary> cheapest = new ArrayList<Itinerary>();

        cheapestResponseIndex = 0;

        for (Itinerary itinerary : mCheapest) {

            var legIndex = 0;

            for (LegGroup legGroup : itinerary.getLegGroups()) {


                if (cheapestResponseIndex == 0) {

                    var cheap = new Itinerary();
                    cheap.getLegGroups().add(legGroup);
                    cheapest.add(cheap);

                } else {
                    cheapest.get(legIndex).getLegGroups().add(legGroup);
                }
                legIndex++;
            }

            cheapestResponseIndex++;
        }

        // calculate duration of leg and segment
        var legs = mLegs
            .values()
            .stream()
            .map(leg -> {
                var count = leg.getSegments().stream().count();

                var segments = leg.getSegments().stream().map(legSegmentDetail -> {

                    var segmentId = legSegmentDetail.getSegment();
                    var adjustmentDate = legSegmentDetail.getDateAdjustment();
                    var segment = mSegments.values().stream().filter(mSegment -> mSegment.getId().equalsIgnoreCase(segmentId)).findFirst().get();

                    if (adjustmentDate > 0) {
                        segment.setDepartureTime(DateUtility.plusDays(segment.getDepartureTime(), adjustmentDate));
                        segment.setArrivalTime(DateUtility.plusDays(segment.getArrivalTime(), adjustmentDate));
                    }

                    var duration = DateUtility.getMinuteDurations(segment.getDepartureTime(), segment.getArrivalTime());
                    legSegmentDetail.setDuration(duration);
                    legSegmentDetail.setStop(segment.getStopCount());

                    return legSegmentDetail;

                }).collect(Collectors.toList());


                // calculate the layover duration
                if (count > 1) {

                    var segmentIndex = 0;

                    for (LegSegmentDetail segment : segments) {

                        // skip first segment
                        if (segmentIndex == 0) {
                            segmentIndex++;
                            continue;
                        }

                        var previousId = segments.get(segmentIndex - 1).getSegment();

                        var previousSegment = mSegments.values().stream().filter(mSegment -> mSegment.getId().equalsIgnoreCase(previousId)).findFirst().get();
                        var currentSegment = mSegments.values().stream().filter(mSegment -> mSegment.getId().equalsIgnoreCase(segment.getSegment())).findFirst().get();

                        segment.setLayOverDuration(DateUtility.getMinuteDurations(previousSegment.getArrivalTime(), currentSegment.getDepartureTime()));
                    }
                }

                leg.setSegments(segments);

                var lastSegment = segments.stream().skip(count - 1).findFirst().get();

                if (lastSegment.getDateAdjustment() > 0) {
                    leg.setArrivalTime(DateUtility.plusDays(leg.getArrivalTime(), lastSegment.getDateAdjustment()));
                }

                var duration = DateUtility.getMinuteDurations(leg.getDepartureTime(), leg.getArrivalTime());

                leg.setDuration(duration);

                return leg;

            }).collect(Collectors.toList());

        transform.setRequestId(responses.getId());
        transform.setAirlines(new ArrayList<>(mAirlines.values()));
        transform.setAircrafts(new ArrayList<>(mAircrafts.values()));
        transform.setLocations(new ArrayList<>(mLocations.values()));
        transform.setSegments(new ArrayList<>(mSegments.values()));
        transform.setLegs(legs);
        transform.setPrices(new ArrayList<>(mPrices.values()));
        transform.setBaggages(new ArrayList<>(mBaggages.values()));
        transform.setDirect(direct);
        transform.setCheapest(cheapest);

        transform = this.getShoppingTransformDetailMarkup(transform, 0);

        return transform;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetail(ShoppingTransformEntity source) {

        if (source == null) return null;

        var airlines = source.getAirlines();
        var aircrafts = source.getAircrafts();
        var locations = source.getLocations();

        for (Airline airline : airlines) {

            var airlineTO = airlineNQ.getAirlineInformation(airline.getCode());

            if (airlineTO != null) {

                airline.setName(airlineTO.getAirline());
                airline.setUrl90(appConfig.getAIRLINE_LOGO_PATH() + "/90/" + airlineTO.getLogo());
                airline.setUrl45(appConfig.getAIRLINE_LOGO_PATH() + "/45/" + airlineTO.getLogo());

            }

        }

        for (Aircraft aircraft : aircrafts) {
            var aircraftTO = aircraftNQ.getAircraftInformation(aircraft.getCode());

            if (aircraftTO != null) {
                aircraft.setName(aircraftTO.getAircraft());
            }
        }

        for (Location location : locations) {

            var locationTO = flightLocationNQ.getFlightLocationInformation(location.getCode());

            if (locationTO != null) {

                location.setAirport(locationTO.getAirport());
                location.setCity(locationTO.getCity());
                location.setLatitude(locationTO.getLatitude().doubleValue());
                location.setLongitude(locationTO.getLongitude().doubleValue());

            }

        }

        source.setAirlines(airlines);
        source.setAircrafts(aircrafts);
        source.setLocations(locations);

        instance.getMap(TRANSFORM_CACHED_NAME).put(source.getRequestId(), source, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data with markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @param markup
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetailMarkup(ShoppingTransformEntity source, double markup) {

        if (source == null) return null;

        var prices = source.getPrices();

        var formatter = NumberFormat.getInstance(new Locale("en"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        for (PriceDetail price : prices) {

            var total = 0.0;
            var average = 0.0;
            var passengers = 0;

            for (Price detail : price.getDetails()) {

                var baseFareMarkup = formatter.format(detail.getBaseFare().doubleValue() + (detail.getBaseFare().doubleValue() * markup));
                var taxMarkup = formatter.format(detail.getTax().doubleValue() + (detail.getTax().doubleValue() * markup));

                detail.setBaseFare(new BigDecimal(baseFareMarkup));
                detail.setTax(new BigDecimal(taxMarkup));

                total = (detail.getTax().add(detail.getBaseFare()).doubleValue() * detail.getQuantity()) + total;

                passengers += detail.getQuantity();
            }

            average = Double.parseDouble(formatter.format(total / passengers));
            total = Double.parseDouble(formatter.format(total));

            price.setTotal(total);
            price.setTotalAvg(average);

        }

        source.setPrices(prices);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping data detail with filter lowest price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetailWithFilter(ShoppingTransformEntity source) {

        if (source == null) return null;

        var formatter = NumberFormat.getInstance(new Locale("en"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        // apply lowest price to airline
        List<Airline> airlines = source.getAirlines();

        // apply sorting and filter lowest price
        List<Itinerary> cheapest = source.getCheapest()
            .stream()
            .map(itinerary -> {
                List<LegGroup> legGroups = itinerary
                    .getLegGroups()
                    .stream()
                    .map(legGroup -> {
                        List<LegDescription> legDescriptions = legGroup.getLegsDesc()
                            .stream()
                            .sorted((previous, next) -> {

                                var previousLeg = source.getLegs().stream().filter(leg -> leg.getId().equalsIgnoreCase(previous.getLeg())).findFirst().get();
                                var nextLeg = source.getLegs().stream().filter(leg -> leg.getId().equalsIgnoreCase(next.getLeg())).findFirst().get();
                                var previousPrice = source.getPrices().stream().filter(priceDetail -> priceDetail.getId().equalsIgnoreCase(previousLeg.getPrice())).findFirst().get();
                                var nextPrice = source.getPrices().stream().filter(priceDetail -> priceDetail.getId().equalsIgnoreCase(nextLeg.getPrice())).findFirst().get();

                                return Double.compare(previousPrice.getTotal(), nextPrice.getTotal());
                            }).collect(Collectors.toList());

                        legGroup.setLegsDesc(legDescriptions);

                        return legGroup;
                    }).collect(Collectors.toList());
                itinerary.setLegGroups(legGroups);
                return itinerary;
            })
            .map(itinerary -> {
                itinerary.getLegGroups()
                    .stream()
                    .forEach(legGroup -> {
                        if (legGroup.getLegsDesc().size() > 0) {
                            itinerary.getLowest().add(legGroup.getLegsDesc().get(0).getLeg());
                        }
                    });
                return itinerary;
            }).collect(Collectors.toList());

        cheapest
            .stream()
            .forEach(itinerary -> {
                var totalDirection = itinerary.getLegGroups().size();
                if (totalDirection > 0) {

                    var airlineCode = itinerary.getLegGroups().get(0).getAirline();

                    var lowestPrice = itinerary
                        .getLowest()
                        .stream()
                        .map(legId -> {
                            var legDetail = source.getLegs().stream().filter(leg -> leg.getId().equalsIgnoreCase(legId)).findFirst().get();
                            var priceDetail = source.getPrices().stream().filter(price -> price.getId().equalsIgnoreCase(legDetail.getPrice())).findFirst().get();

                            return priceDetail.getTotal();
                        }).collect(Collectors.summingDouble(Double::doubleValue));

                    lowestPrice = Double.parseDouble(formatter.format(lowestPrice));

                    for (Airline airline : airlines) {
                        if (airline.getCode().equalsIgnoreCase(airlineCode)) {

                            if (airline.getPrice() == 0) {
                                airline.setPrice(lowestPrice);
                            }

                            if (airline.getPrice() > lowestPrice) {
                                airline.setPrice(lowestPrice);
                            }

                            break;
                        }
                    }
                }
            });

        source.setAirlines(airlines);
        source.setCheapest(cheapest);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping data detail from cached by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformById(String id) {
        return (ShoppingTransformEntity) instance.getMap(TRANSFORM_CACHED_NAME).getOrDefault(id, null);
    }
}
