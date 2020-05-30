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
import java.math.RoundingMode;
import java.util.Comparator;
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
    private MutableMap<String, LayoverAirport> layoverAirports = UnifiedMap.newMap();
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

    public MutableMap<String, LayoverAirport> getLayoverAirports() {
        return layoverAirports;
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
            .forEachWithIndex(((itineraryGroupType, index) -> itineraryGroupType
                .getItineraries()
                .forEach(itinerary -> processingItineraryComponents(itinerary, index))));
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

                // set seat remaining
                var seats = leg.getSegments().stream().min(Comparator.comparingInt(LegSegmentDetail::getSeatsRemain)).map(LegSegmentDetail::getSeatsRemain).get();
                leg.setSeats(seats);

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
                .summarizeDouble(price -> NumberFormatter.amount(price.getBaseFare().add(price.getTax()).multiply(BigDecimal.valueOf(price.getQuantity())).doubleValue())).getSum();

            var totalQuantitiesPassenger = FastList.newList(priceDetail.getDetails()).summarizeInt(PriceList::getQuantity).getSum();
            var totalCommissionAmount = NumberFormatter.trimAmount(priceDetail.getDetails().stream().collect(Collectors.summarizingDouble(price -> price.getCommissionAmount().doubleValue())).getSum());

//            var totalCommissionAmount = BigDecimal.valueOf(12);

            var grandTotal = BigDecimal.valueOf(totalSummary).subtract(totalCommissionAmount);
            var grandTotalAvg = grandTotal.divide(BigDecimal.valueOf(totalQuantitiesPassenger), RoundingMode.HALF_UP);
            var totalAverage = BigDecimal.valueOf(totalSummary).divide(BigDecimal.valueOf(totalQuantitiesPassenger), RoundingMode.HALF_DOWN);

            var price = new Price();
            price.setCurrency("USD");
            price.setTotalCommission(totalCommissionAmount);
            price.setTotal(BigDecimal.valueOf(totalSummary));
            price.setGrandTotalAverage(grandTotalAvg);
            price.setGrandTotal(grandTotal);
            price.setTotalAverage(totalAverage);

            priceDetail.setTotalCommissionAmount(totalCommissionAmount);
            priceDetail.setBasePrice(price);
            priceDetail.setPrice(price);

            if (totalCommissionAmount.doubleValue() > 0) {
                priceDetail.setCommission(true);
            }

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
        passenger
            .getBaggageInformation()
            .stream()
            .findFirst()
            .ifPresentOrElse(it -> {

                var baggageAllowanceCached = baggageAllowancesCached.get(it.getAllowance().getId().toString());

                var baggage = new Baggage();

                baggage.setType(passenger.getPassengerType());
                baggage.setPieces(baggageAllowanceCached.getPieces());
                baggage.setPiece(baggageAllowanceCached.isPiece());
                baggage.setUnit(baggageAllowanceCached.getUnit());

                baggage.setWeights(baggageAllowanceCached.getWeights());
                if (baggageAllowanceCached.isPiece() && baggageAllowanceCached.getPieces() > 0) {
                    if (passenger.getPassengerType().equals("INF")) {
                        baggage.setWeights(10);
                    }
                }

                baggage.setNonRefundable(passenger.getNonRefundable());

                baggageDetail.getDetails().add(baggage);

                baggages.put(baggageDetail.getId(), baggageDetail);
            }, () -> {

                var baggageAllowanceCached = new BaggageAllowance();
                baggageAllowanceCached.setPiece(false);
                baggageAllowanceCached.setPieces(0);
                baggageAllowanceCached.setUnit("kg");
                baggageAllowanceCached.setWeights(0);

                var baggage = new Baggage();
                baggage.setType(passenger.getPassengerType());
                baggage.setPieces(baggageAllowanceCached.getPieces());
                baggage.setPiece(baggageAllowanceCached.isPiece());
                baggage.setUnit(baggageAllowanceCached.getUnit());
                baggage.setNonRefundable(passenger.getNonRefundable());

                baggageDetail.getDetails().add(baggage);
                baggages.put(baggageDetail.getId(), baggageDetail);

            });


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

        var price = new PriceList();

        price.setType(passenger.getPassengerType());
        price.setQuantity(passenger.getPassengerNumber());
        price.setBaseFare(passenger.getPassengerTotalFare().getEquivalentAmount());
        price.setTax(passenger.getPassengerTotalFare().getTotalTaxAmount());
        price.setCurrency(passenger.getPassengerTotalFare().getCurrency());
        price.setCommissionAmount(passenger.getPassengerTotalFare().getCommissionAmount());
        price.setCommissionPercentage(passenger.getPassengerTotalFare().getCommissionPercentage());

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

        if(isPiece && piece == 0) {
            weight = 0;
        }

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
            .forEachOrdered(this::setLegComponentLeg);
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
        var legStops = Math.addExact(segmentStop, (segmentSize - 1));

        var firstSegmentDetail = segmentDetails.getFirst();
        var lastSegmentDetail = segmentDetails.getLast();

        var firstSegment = segments.get(firstSegmentDetail.getSegment());
        var lastSegment = segments.get(lastSegmentDetail.getSegment());

        var departureDateTime = DateUtility.plusDays(firstSegment.getDepartureTime(), Math.addExact(firstSegmentDetail.getDateAdjustment(), firstSegmentDetail.getPreviousDateAdjustment()));
        var arrivalDateTime = DateUtility.plusDays(lastSegment.getArrivalTime(), Math.addExact(lastSegmentDetail.getDateAdjustment(), lastSegmentDetail.getPreviousDateAdjustment()));

        var duration = DateUtility.getMinuteDurations(departureDateTime, arrivalDateTime);

//        var adjustmentDates = segmentDetails.summarizeInt(LegSegmentDetail::getDateAdjustment).getSum();
        var adjustmentDates = segmentDetails.get(segmentSize - 1).getDateAdjustment();
        var layoverDuration = segmentDetails.summarizeLong(LegSegmentDetail::getLayoverDuration).getSum();

        var legId = new StringBuilder()
            .append("L")
            .append("-")
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

        leg.setStops(Long.valueOf(legStops).intValue());
        leg.setAdjustmentDates(Long.valueOf(adjustmentDates).intValue());
        leg.setLayoverDuration(layoverDuration);

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
            var layoverAirport = new LayoverAirport();

            if (startIdx == idx) {

                var duration = DateUtility.getMinuteDurations(DateUtility.plusDays(currentSegment.getDepartureTime(), legSegmentDetail.getDateAdjustment()), DateUtility.plusDays(currentSegment.getArrivalTime(), legSegmentDetail.getDateAdjustment()));

                legSegmentDetail.setLayoverDuration(0);
                legSegmentDetail.setPreviousDateAdjustment(0);
                legSegmentDetail.setDuration(duration);

            } else {

                var previousDetail = legSegmentDetails.get(idx - 1);
                var previousSegment = segments.get(previousDetail.getSegment());

                var previousDateAdjustment = 0; //Math.addExact(previousDetail.getDateAdjustment(), previousDetail.getPreviousDateAdjustment());
                var arrivalDateTime = DateUtility.plusDays(previousSegment.getArrivalTime(), previousDateAdjustment);

                var currentDateAdjustment = Math.addExact(legSegmentDetail.getDateAdjustment(), previousDateAdjustment);
                var departureDateTime = DateUtility.plusDays(currentSegment.getDepartureTime(), currentDateAdjustment);

                var duration = DateUtility.getMinuteDurations(DateUtility.plusDays(currentSegment.getDepartureTime(), currentDateAdjustment), DateUtility.plusDays(currentSegment.getArrivalTime(), currentDateAdjustment));
                var layover = DateUtility.getMinuteDurations(arrivalDateTime, departureDateTime);

                legSegmentDetail.setPreviousDateAdjustment(previousDateAdjustment);
                legSegmentDetail.setDuration(duration);
                legSegmentDetail.setLayoverDuration(layover);
                legSegmentDetail.setLayoverAirport(previousSegment.getArrival());

                layoverAirport.setCode(previousSegment.getDeparture());

                this.layoverAirports.putIfAbsent(previousSegment.getArrival(), layoverAirport);

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
        var stop = segment.getStopCount();

        var segmentDetail = new LegSegmentDetail();

        segmentDetail.setSegment(segmentCached);
        segmentDetail.setDateAdjustment(adjustmentDate);
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
                this.setScheduleComponentLayoverAirport(scheduleComponent);
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
            .append("S")
            .append("-")
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

            var location = new Location();
            location.setCode(stop.getAirport());
            location.setAirport(stop.getAirport());
            location.setCity(stop.getCity());
            location.setLongitude(0);
            location.setLatitude(0);

            locations.putIfAbsent(location.getCode(), location);

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

        var operatingAirline = new Airline();
        operatingAirline.setCode(carrier.getOperating());
        operatingAirline.setName(carrier.getOperating());

        airlines.putIfAbsent(airline.getCode(), airline);
        airlines.putIfAbsent(operatingAirline.getCode(), operatingAirline);

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


    private void setScheduleComponentLayoverAirport(ScheduleComponent scheduleComponent) {

        var layoverAirport = new LayoverAirport();
        layoverAirport.setCode(scheduleComponent.getDeparture().getAirport());
        layoverAirport.setAirport(scheduleComponent.getDeparture().getAirport());
        layoverAirport.setCity(scheduleComponent.getDeparture().getCity());
        layoverAirport.setLatitude(0);
        layoverAirport.setLongitude(0);

        layoverAirports.putIfAbsent(layoverAirport.getCode(), layoverAirport);

    }


}
