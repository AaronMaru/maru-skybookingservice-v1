package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.FareComponentID;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.PassengerInformation;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.leg.Leg;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.Schedule;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.ScheduleComponent;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransformSabre {

    private int directionIndex;
    private String departureDate;
    private SabreBargainFinderRS sabreBargainFinderRS;

    private MutableMap<String, Airline> airlines = UnifiedMap.newMap();
    private MutableMap<String, Aircraft> aircrafts = UnifiedMap.newMap();
    private MutableMap<String, Location> locations = UnifiedMap.newMap();
    private MutableMap<String, PriceDetail> prices = UnifiedMap.newMap();
    private MutableMap<String, BaggageDetail> baggages = UnifiedMap.newMap();
    private MutableMap<String, Segment> segments = UnifiedMap.newMap();
    private MutableMap<String, com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Leg> legs = UnifiedMap.newMap();

    private MutableMap<String, String> segmentsCached = UnifiedMap.newMap();
    private MutableMap<String, String> legsCached = UnifiedMap.newMap();
    private MutableMap<String, BaggageAllowance> baggageAllowancesCached = UnifiedMap.newMap();

    public TransformSabre(int directionIndex, String departureDate, SabreBargainFinderRS sabreBargainFinderRS) {

        this.directionIndex = directionIndex;
        this.sabreBargainFinderRS = sabreBargainFinderRS;
        this.departureDate = departureDate;

        this.start();

    }

    public MutableMap<String, Airline> getAirlines() {
        return airlines;
    }


    public MutableMap<String, Aircraft> getAircrafts() {
        return aircrafts;
    }


    public MutableMap<String, Location> getLocations() {
        return locations;
    }


    public MutableMap<String, PriceDetail> getPrices() {
        return prices;
    }


    public MutableMap<String, BaggageDetail> getBaggages() {
        return baggages;
    }


    public MutableMap<String, Segment> getSegments() {
        return segments;
    }


    public MutableMap<String, com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Leg> getLegs() {
        return legs;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * process the extracting and parsing data from sabre response
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void start() {

        this.processingScheduleComponents();
        this.processingBaggageComponents();
        this.processingLegComponents();
        this.processingItineraryGroupComponents();
        this.calculatePriceDetail();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data in itinerary components
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void processingItineraryGroupComponents() {

        FastList.newList(sabreBargainFinderRS.getItineraryResponse().getItineraryGroups())
            .forEachWithIndex(((itineraryGroupType, index) -> {
                itineraryGroupType
                    .getItineraries()
                    .forEach(itinerary -> {
                        processingItineraryComponents(itinerary, index);
                    });
            }));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data in itineraries component
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itinerary
     * @param itineraryIndex
     */
    private void processingItineraryComponents(com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.Itinerary itinerary, int itineraryIndex) {

        var legRef = itinerary.getLegs().stream().findFirst().get();
        var legCached = legsCached.get(legRef.getId().toString());

        var leg = legs.get(legCached);

        itinerary
            .getPricingInformation()
            .stream()
            .findFirst()
            .ifPresent(pricingInformation -> {

                var priceDetail = new PriceDetail();
                var baggageDetail = new BaggageDetail();

                var priceDetailId = new StringBuilder()
                    .append("P")
                    .append("-")
                    .append(directionIndex)
                    .append("-")
                    .append(legRef.getId())
                    .append("-")
                    .append(itineraryIndex)
                    .append("-")
                    .append(itinerary.getId())
                    .toString();

                var baggageDetailId = new StringBuilder()
                    .append("B")
                    .append("-")
                    .append(directionIndex)
                    .append("-")
                    .append(legRef.getId())
                    .append("-")
                    .append(itineraryIndex)
                    .append("-")
                    .append(itinerary.getId())
                    .toString();

                priceDetail.setId(priceDetailId);
                priceDetail.setCurrency("USD");

                baggageDetail.setId(baggageDetailId);

                leg.setPrice(priceDetailId);
                leg.setBaggage(baggageDetailId);

                pricingInformation
                    .getFare()
                    .getPassengerInfoList()
                    .forEach(passengerInfoList -> {
                        setPriceDetail(priceDetail, passengerInfoList.getPassengerInfo());
                        setBaggageDetail(baggageDetail, passengerInfoList.getPassengerInfo());
                    });

                pricingInformation
                    .getFare()
                    .getPassengerInfoList()
                    .stream()
                    .findFirst()
                    .ifPresent(passengerInfoList -> {
                        getItineraryFareSegmentDetails(passengerInfoList.getPassengerInfo().getFareComponents())
                            .forEachWithIndex(((fareSegment, idx) -> {
                                leg.getSegments().get(idx).setBookingCode(fareSegment.getBookingCode());
                                leg.getSegments().get(idx).setCabin(fareSegment.getCabin());
                                leg.getSegments().get(idx).setCabinName(fareSegment.getCabinName());
                                leg.getSegments().get(idx).setAvailabilityBreak(fareSegment.isAvailabilityBreak());
                                leg.getSegments().get(idx).setMeal(fareSegment.getMeal());
                                leg.getSegments().get(idx).setMealName(fareSegment.getMealName());
                                leg.getSegments().get(idx).setSeatsRemain(fareSegment.getSeatsRemain());
                            }));
                    });


            });

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * calculate of total and average price
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void calculatePriceDetail() {
        prices.forEachKeyValue((priceId, priceDetail) -> {

            var totalSummary = FastList.newList(priceDetail.getDetails())
                .summarizeDouble(price -> NumberFormatter.amount(price.getBaseFare().add(price.getTax()).multiply(BigDecimal.valueOf(price.getQuantity())).doubleValue()));

            var totalQuantitiesPassenger = FastList.newList(priceDetail.getDetails()).summarizeInt(Price::getQuantity);

            var totalAvg = totalSummary.getSum() / totalQuantitiesPassenger.getSum();

            priceDetail.setCurrency("USD");
            priceDetail.setTotal(NumberFormatter.amount(totalSummary.getSum()));
            priceDetail.setTotalAvg(NumberFormatter.amount(totalAvg));

        });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set baggage detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param baggageDetail
     * @param passenger
     */
    private void setBaggageDetail(BaggageDetail baggageDetail, PassengerInformation passenger) {
        var baggageRef = passenger
            .getBaggageInformation()
            .stream()
            .findFirst()
            .get();

        var baggageAllowanceCached = baggageAllowancesCached.get(baggageRef.getAllowance().getId().toString());

        var baggage = new Baggage();
        baggage.setType(passenger.getPassengerType());
        baggage.setPieces(baggageAllowanceCached.getPieces());
        baggage.setPiece(baggageAllowanceCached.isPiece());
        baggage.setUnit(baggageAllowanceCached.getUnit());
        baggage.setWeights(baggageAllowanceCached.getWeights());
        baggage.setNonRefundable(passenger.getNonRefundable());

        baggageDetail.getDetails().add(baggage);

        baggages.put(baggageDetail.getId(), baggageDetail);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set price detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param priceDetail
     * @param passenger
     */
    private void setPriceDetail(PriceDetail priceDetail, PassengerInformation passenger) {

        var price = new Price();
        price.setType(passenger.getPassengerType());
        price.setQuantity(passenger.getPassengerNumber());
        price.setBaseFare(passenger.getPassengerTotalFare().getEquivalentAmount());
        price.setTax(passenger.getPassengerTotalFare().getTotalTaxAmount());
        price.setCurrency(passenger.getPassengerTotalFare().getCurrency());

        priceDetail.getDetails().add(price);
        prices.put(priceDetail.getId(), priceDetail);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get fare segment detail of each segments
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param fareComponents
     * @return FastList
     */
    private FastList<FareSegment> getItineraryFareSegmentDetails(List<FareComponentID> fareComponents) {
        FastList<FareSegment> segments = FastList.newList();
        fareComponents
            .forEach(fareComponentID -> fareComponentID
                .getSegments()
                .forEach(fareComponentSegment -> {

                    var seats = Optional.ofNullable(fareComponentSegment.getSegment().getSeatsAvailable()).orElse(0);
                    var availabilityBreak = Optional.ofNullable(fareComponentSegment.getSegment().getAvailabilityBreak()).orElse(false);
                    var cabin = Optional.ofNullable(fareComponentSegment.getSegment().getCabinCode()).orElse("");
                    var meal = Optional.ofNullable(fareComponentSegment.getSegment().getMealCode()).orElse("");
                    var bookingCode = Optional.ofNullable(fareComponentSegment.getSegment().getBookingCode()).orElse("");

                    var fareSegment = new FareSegment();
                    fareSegment.setAvailabilityBreak(availabilityBreak);
                    fareSegment.setSeatsRemain(seats);
                    fareSegment.setCabin(cabin);
                    fareSegment.setCabinName(cabin);
                    fareSegment.setMeal(meal);
                    fareSegment.setMealName(meal);
                    fareSegment.setBookingCode(bookingCode);

                    segments.add(fareSegment);

                }));

        return segments;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data in baggage allowances component
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void processingBaggageComponents() {
        sabreBargainFinderRS
            .getItineraryResponse()
            .getBaggageAllowances()
            .forEach(baggageAllowance -> {
                setBaggageAllowanceCached(baggageAllowance);
            });

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set baggage allowance cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param baggageAllowance
     */
    private void setBaggageAllowanceCached(com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.allowance.BaggageAllowance baggageAllowance) {
        var isPiece = Optional.ofNullable(baggageAllowance.getPieceCount()).isPresent();
        var piece = Optional.ofNullable(baggageAllowance.getPieceCount()).orElse(0);
        var unit = Optional.ofNullable(baggageAllowance.getUnit()).orElse("kg");
        var weight = Optional.ofNullable(baggageAllowance.getWeight()).orElse(20);

        var baggage = new BaggageAllowance();

        baggage.setPiece(isPiece);
        baggage.setPieces(piece);
        baggage.setUnit(unit);
        baggage.setWeights(weight);

        baggageAllowancesCached.put(baggageAllowance.getId().toString(), baggage);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data in leg component
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void processingLegComponents() {

        sabreBargainFinderRS
            .getItineraryResponse()
            .getLegs()
            .parallelStream()
            .forEachOrdered(legComponent -> {
                this.setLegComponentLeg(legComponent);
            });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set legs
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param legComponent
     */
    private void setLegComponentLeg(Leg legComponent) {

        var segmentDetails = getLegSegmentDetails(legComponent.getSchedules());
        var airlineDetails = getLegAirlineDetails(segmentDetails);

        var multiAir = airlineDetails.groupBy(LegAirline::getAirline).sizeDistinct() > 1;

        var segmentSize = segmentDetails.size();
        var segmentStop = segmentDetails.summarizeInt(LegSegmentDetail::getStops).getSum();

        var firstSegmentDetail = segmentDetails.getFirst();
        var lastSegmentDetail = segmentDetails.getLast();

        var firstSegment = segments.get(firstSegmentDetail.getSegment());
        var lastSegment = segments.get(lastSegmentDetail.getSegment());

        var departureDateTime = DateUtility.plusDays(firstSegment.getDepartureTime(), firstSegmentDetail.getDateAdjustment());
        var arrivalDateTime = DateUtility.plusDays(lastSegment.getArrivalTime(), lastSegmentDetail.getDateAdjustment());

        var duration = DateUtility.getMinuteDurations(departureDateTime, arrivalDateTime);

        var legId = new StringBuilder()
            .append(directionIndex)
            .append("-")
            .append(firstSegment.getDeparture())
            .append("-")
            .append(lastSegment.getArrival())
            .append("-")
            .append(legComponent.getId())
            .toString();

        var leg = new com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Leg();

        leg.setId(legId);
        leg.setLegIndex(legComponent.getId());
        leg.setDirectionIndex(directionIndex);

        leg.setDeparture(firstSegment.getDeparture());
        leg.setDepartureTime(departureDateTime);
        leg.setDepartureTerminal(firstSegment.getDepartureTerminal());

        leg.setArrival(lastSegment.getArrival());
        leg.setArrivalTime(arrivalDateTime);
        leg.setArrivalTerminal(lastSegment.getArrivalTerminal());

        leg.setDuration(duration);

        leg.setAirlines(airlineDetails);
        leg.setMultiAir(multiAir);
        leg.setSegments(segmentDetails);

        if (!multiAir && segmentSize == 1 && segmentStop == 0) {
            leg.setDirectFlight(true);
        }

        legs.put(legId, leg);
        legsCached.put(legComponent.getId().toString(), legId);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg airlines
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param segmentDetails
     * @return FastList
     */
    private FastList<LegAirline> getLegAirlineDetails(FastList<LegSegmentDetail> segmentDetails) {
        FastList<LegAirline> airlines = FastList.newList();
        segmentDetails
            .forEach(segmentDetail -> {

                var segment = segments.get(segmentDetail.getSegment());

                var legAirline = new LegAirline();
                legAirline.setAirline(segment.getAirline());
                legAirline.setAircraft(segment.getAircraft());
                legAirline.setFlightNumber(segment.getFlightNumber());

                airlines.add(legAirline);
            });

        return airlines;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg segment detail fast list with layover duration added.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param legSegmentDetails
     * @return FastList
     */
    private FastList<LegSegmentDetail> getLayoverDurationLegSegmentDetails(FastList<LegSegmentDetail> legSegmentDetails) {

        var startIdx = 0;

        legSegmentDetails.forEachWithIndex(((legSegmentDetail, idx) -> {

            var currentSegment = segments.get(legSegmentDetail.getSegment());

            if (startIdx == idx) {
                legSegmentDetail.setLayoverDuration(0);
            } else {

                var previousDetail = legSegmentDetails.get(idx - 1);
                var previousSegment = segments.get(previousDetail.getSegment());

                var arrivalDateTime = DateUtility.plusDays(previousSegment.getArrivalTime(), previousDetail.getDateAdjustment());
                var departureDateTime = DateUtility.plusDays(currentSegment.getDepartureTime(), legSegmentDetail.getDateAdjustment());
                var layover = DateUtility.getMinuteDurations(arrivalDateTime, departureDateTime);

                legSegmentDetail.setLayoverDuration(layover);
            }
        }));

        return legSegmentDetails;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg segment detail fast list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param schedules
     * @return FastList
     */
    private FastList<LegSegmentDetail> getLegSegmentDetails(List<Schedule> schedules) {

        FastList<LegSegmentDetail> legSegmentDetails = FastList.newList();

        for (Schedule schedule : schedules) {
            legSegmentDetails.add(getLegSegmentDetail(schedule));
        }

        return getLayoverDurationLegSegmentDetails(legSegmentDetails);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get leg segment detail id, adjustment date, stops count and duration
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param schedule
     * @return LegSegmentDetail
     */
    private LegSegmentDetail getLegSegmentDetail(Schedule schedule) {

        var segmentCached = segmentsCached.get(schedule.getId().toString());
        var segment = segments.get(segmentCached);

        var adjustmentDate = Optional.ofNullable(schedule.getDepartureDateAdjustment()).orElse(0);
        var duration = DateUtility.getMinuteDurations(DateUtility.plusDays(segment.getDepartureTime(), adjustmentDate), DateUtility.plusDays(segment.getArrivalTime(), adjustmentDate));
        var stop = segment.getStopCount();

        var segmentDetail = new LegSegmentDetail();

        segmentDetail.setSegment(segmentCached);
        segmentDetail.setDateAdjustment(adjustmentDate);
        segmentDetail.setDuration(duration);
        segmentDetail.setStops(stop);

        return segmentDetail;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data in schedule component
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void processingScheduleComponents() {

        sabreBargainFinderRS
            .getItineraryResponse()
            .getScheduleComponents()
            .parallelStream()
            .forEachOrdered(scheduleComponent -> {

                this.setScheduleComponentAirline(scheduleComponent);
                this.setScheduleComponentAircraft(scheduleComponent);
                this.setScheduleComponentLocation(scheduleComponent);
                this.setScheduleComponentSegment(scheduleComponent);

            });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set segments
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param scheduleComponent
     */
    private void setScheduleComponentSegment(ScheduleComponent scheduleComponent) {

        var segmentId = new StringBuilder()
            .append(directionIndex)
            .append("-")
            .append(scheduleComponent.getDeparture().getAirport())
            .append("-")
            .append(scheduleComponent.getArrival().getAirport())
            .append("-")
            .append(scheduleComponent.getId())
            .toString();

        var segment = new Segment();

        segment.setId(segmentId);
        segment.setDirectionIndex(directionIndex);
        segment.setSegmentIndex(scheduleComponent.getId());

        segment.setDeparture(scheduleComponent.getDeparture().getAirport());
        segment.setDepartureTime(departureDate.concat("T").concat(scheduleComponent.getDeparture().getTime()));
        segment.setDepartureTerminal(Optional.ofNullable(scheduleComponent.getDeparture().getTerminal()).orElse(""));

        segment.setArrival(scheduleComponent.getArrival().getAirport());
        segment.setArrivalTime(departureDate.concat("T").concat(scheduleComponent.getArrival().getTime()));
        segment.setArrivalTerminal(Optional.ofNullable(scheduleComponent.getArrival().getTerminal()).orElse(""));

        segment.setFlightNumber(Optional.ofNullable(scheduleComponent.getCarrier().getMarketingFlightNumber()).map(Object::toString).orElse(""));
        segment.setOperatingAirline(Optional.ofNullable(scheduleComponent.getCarrier().getOperating()).orElse(""));
        segment.setOperatingFlightNumber(Optional.ofNullable(scheduleComponent.getCarrier().getOperatingFlightNumber()).map(Object::toString).orElse(""));

        segment.setAirline(Optional.ofNullable(scheduleComponent.getCarrier().getMarketing()).orElse(""));
        segment.setAircraft(Optional.ofNullable(scheduleComponent.getCarrier().getEquipment().getCode()).orElse(""));

        segment.setStopCount(Optional.ofNullable(scheduleComponent.getStopCount()).orElse(0));

        if (segment.getStopCount() > 0) {
            segment.setHiddenStops(getSegmentHiddenStops(scheduleComponent.getHiddenStops()));
        }

        var arrivalDateAdjustment = Optional.ofNullable(scheduleComponent.getArrival().getDateAdjustment()).orElse(0);

        if (arrivalDateAdjustment > 0) {
            segment.setArrivalTime(DateUtility.plusDays(segment.getArrivalTime(), arrivalDateAdjustment));
        }

        segment.setSegmentIndex(scheduleComponent.getId());

        segments.putIfAbsent(segmentId, segment);
        segmentsCached.putIfAbsent(scheduleComponent.getId().toString(), segmentId);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hidden stops
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hiddenStops
     * @return List
     */
    private List<HiddenStop> getSegmentHiddenStops(List<com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.HiddenStop> hiddenStops) {

        return hiddenStops.stream().map(hiddenStop -> {

            var arrivalAdjustmentDateTime = Optional.ofNullable(hiddenStop.getArrivalDateAdjustment()).orElse(0);
            var departureAdjustmentDateTime = Optional.ofNullable(hiddenStop.getDepartureDateAdjustment()).orElse(0);

            var arrivalDateTime = DateUtility.plusDays(departureDate.concat("T").concat(hiddenStop.getArrivalTime()), arrivalAdjustmentDateTime);
            var departureDateTime = DateUtility.plusDays(departureDate.concat("T").concat(hiddenStop.getDepartureTime()), departureAdjustmentDateTime);

            var stop = new HiddenStop();
            stop.setAirport(Optional.ofNullable(hiddenStop.getAirport()).orElse(""));
            stop.setCity(Optional.ofNullable(hiddenStop.getCity()).orElse(""));
            stop.setCountry(Optional.ofNullable(hiddenStop.getCountry()).orElse(""));
            stop.setDepartureTime(departureDateTime);
            stop.setArrivalTime(arrivalDateTime);
            stop.setDuration(Optional.ofNullable(hiddenStop.getElapsedLayoverTime()).orElse(0));
            stop.setEquipment(Optional.ofNullable(hiddenStop.getEquipment()).orElse(""));
            stop.setElapsedTime(Optional.ofNullable(hiddenStop.getElapsedTime()).orElse(0));

            return stop;

        }).collect(Collectors.toList());

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set locations
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param scheduleComponent
     */
    private void setScheduleComponentLocation(ScheduleComponent scheduleComponent) {

        var departure = new Location();
        departure.setCode(scheduleComponent.getDeparture().getAirport());
        departure.setAirport(scheduleComponent.getDeparture().getAirport());
        departure.setCity(scheduleComponent.getDeparture().getCity());
        departure.setLatitude(0);
        departure.setLongitude(0);

        var arrival = new Location();
        arrival.setCode(scheduleComponent.getArrival().getAirport());
        arrival.setAirport(scheduleComponent.getArrival().getAirport());
        arrival.setCity(scheduleComponent.getArrival().getCity());
        arrival.setLatitude(0);
        arrival.setLongitude(0);

        locations.putIfAbsent(departure.getCode(), departure);
        locations.putIfAbsent(arrival.getCode(), arrival);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set airlines
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param scheduleComponent
     */
    private void setScheduleComponentAirline(ScheduleComponent scheduleComponent) {

        var carrier = scheduleComponent.getCarrier();
        var airline = new Airline();

        airline.setCode(carrier.getMarketing());
        airline.setName(carrier.getMarketing());

        airlines.putIfAbsent(airline.getCode(), airline);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set aircrafts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param scheduleComponent
     */
    private void setScheduleComponentAircraft(ScheduleComponent scheduleComponent) {

        var carrier = scheduleComponent.getCarrier();
        var aircraft = new Aircraft();

        aircraft.setCode(carrier.getEquipment().getCode());
        aircraft.setName(carrier.getEquipment().getCode());

        aircrafts.putIfAbsent(aircraft.getCode(), aircraft);

    }


}
