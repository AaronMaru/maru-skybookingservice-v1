package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.constant.BookingConstant;
import com.skybooking.skyflightservice.constant.CompanyConstant;
import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.*;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.HiddenStop;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.*;
import com.skybooking.skyflightservice.v1_0_0.io.repository.frontend.FrontendConfigRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingDataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.TravelItineraryTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.GeneratorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.CalculatorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingDataIP extends MetadataIP implements BookingDataSV {

    @Autowired
    private FrontendConfigRP frontendConfigRP;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingSpecialServiceRP bookingSpecialServiceRP;

    @Autowired
    private BookingTravelItineraryRP bookingTravelItineraryRP;

    @Autowired
    private BookingFareBreakdownRP bookingFareBreakdownRP;

    @Autowired
    private BookingOriginDestinationRP bookingOriginDestinationRP;

    @Autowired
    private BookingStopAirportRP bookingStopAirportRP;

    @Autowired
    private BookingAirTicketRP bookingAirTicketRP;

    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private MarkupNQ markupNQ;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public BookingEntity save(BookingRequestTA requestTA, BookingMetadataTA metadataTA, JsonNode pnrRS, BookingCreateRQ request) {

        var booking = this.insertBooking(requestTA, metadataTA, pnrRS);
        this.insertBookingOriginDestination(requestTA, booking.getId());
        this.insertBookingSpecialService(booking.getId(), pnrRS);
        List<TravelItineraryTA> travelItineraryTAS = this.insertBookingTravelItinerary(booking.getId(), pnrRS, request);
        this.insertBookingAirTicket(booking.getId(), travelItineraryTAS, request);

        return booking;

    }


    @Override
    public BookingEntity insertBooking(BookingRequestTA requestTA, BookingMetadataTA metadataTA, JsonNode pnrRS) {

        var booking = new BookingEntity();
        var company = new CompanyConstant();
        var generalMarkupPayment = markupNQ.getGeneralMarkupPayment();
        var locationCode = pnrRS.get("CreatePassengerNameRecordRS")
            .get("TravelItineraryRead")
            .get("TravelItinerary")
            .get("CustomerInfo")
            .get("ContactNumbers")
            .get("ContactNumber")
            .get(0).get("LocationCode").textValue();
        var travelItineraries = pnrRS.get("CreatePassengerNameRecordRS")
            .get("AirPrice")
            .get(0)
            .get("PriceQuote")
            .get("PricedItinerary")
            .get("AirItineraryPricingInfo");
        var itemMarkup = CalculatorUtils.getBookingMarkup(travelItineraries, new BigDecimal(metadataTA.getMarkupPercentage()), generalMarkupPayment.getMarkup());

        booking.setStakeholderUserId(metadataTA.getUser().getStakeholderId());
        booking.setStakeholderCompanyId(metadataTA.getUser().getCompanyId());
        booking.setCustName(company.getName());
        booking.setCustAddress(company.getAddress());
        booking.setCustCity(company.getCity());
        booking.setCustZip(company.getPostalCode());
        booking.setContFullname(requestTA.getRequest().getContact().getName());
        booking.setContPhonecode(requestTA.getRequest().getContact().getPhoneCode());
        booking.setContPhone(requestTA.getRequest().getContact().getPhoneNumber());
        booking.setContEmail(requestTA.getRequest().getContact().getEmail());
        booking.setContLocationCode(locationCode);
        booking.setBookingCode(GeneratorUtils.bookingCode(bookingRP.getLatestRow()));
        booking.setBookingType(requestTA.getBookingType());
        booking.setItineraryRef(requestTA.getItineraryCode());
        booking.setDepDate(DateUtility.convertZonedDateTimeToDateTime(metadataTA.getDepartureDate()));
        booking.setTripType(bookingUtility.getTripType(metadataTA.getTripType()));
        booking.setPq(bookingUtility.getPassengerQuantityCodeNumber(requestTA.getRequest().getPassengers()));
        booking.setTotalAmount(BigDecimal.valueOf(metadataTA.getTotalAmount()));
        booking.setCurrencyConvert(metadataTA.getCurrencyLocaleCode());
        booking.setCurrencyCode(metadataTA.getCurrencyCode());
        booking.setCurrencyConRate(metadataTA.getExchangeRate());
        booking.setMarkupAmount(BigDecimal.valueOf(metadataTA.getMarkupAmount()));
        booking.setMarkupPercentage(Double.toString(metadataTA.getMarkupPercentage()));

        booking.setDisPayMetAmount(BigDecimal.ZERO);
        booking.setDisPayMetPercentage(BigDecimal.ZERO);
        booking.setMarkupPayAmount(itemMarkup.getPaymentMethodMarkupAmount());
        booking.setMarkupPayPercentage(generalMarkupPayment.getMarkup());
        booking.setPayMetFeeAmount(BigDecimal.ZERO);
        booking.setPayMetFeePercentage(BigDecimal.ZERO);

        booking.setVatPercentage(frontendConfigRP.getVatPercentage().doubleValue());
        booking.setIsCheck(0);
        booking.setStatus(BookingConstant.PNR_CREATED);
        booking.setCreatedBy(metadataTA.getUser().getUserId().toString()); // user's logged in id

        return bookingRP.save(booking);

    }


    @Override
    public void insertBookingOriginDestination(BookingRequestTA requestTA, Integer bookingId) {

        var requestId = requestTA.getRequest().getRequestId();

        for (String legId : requestTA.getRequest().getLegIds()) {

            var leg = detailSV.getLegDetail(requestId, legId);
            var bookingOD = new BookingOriginDestinationEntity();

            bookingOD.setBookingId(bookingId);

            //TODO: set transfer information
            bookingOD.setGroupAirSegs("");
            bookingOD.setArrageAirSegs("");
            bookingOD.setElapsedTime(Math.toIntExact(leg.getDuration()));
            bookingOD.setMultipleAirStatus(leg.isMultiAir() ? 1 : 0);

            // store parent data
            bookingOD = bookingOriginDestinationRP.save(bookingOD);

            var totalStop = leg.getSegments().size() - 1;

            for (LegSegmentDetail segment : leg.getSegments()) {

                var segmentDetail = detailSV.getSegmentDetail(requestId, segment.getSegment());
                var bookingSegmentOD = new BookingOriginDestinationEntity();

                bookingSegmentOD.setBookingId(bookingId);
                bookingSegmentOD.setParentId(bookingOD.getId());
                bookingSegmentOD.setElapsedTime(Math.toIntExact(segment.getDuration()));
                bookingSegmentOD.setMultipleAirStatus(0);
                bookingSegmentOD.setSeatRemaining(segment.getSeatsRemain());
                bookingSegmentOD.setCabinCode(segment.getCabin());
                bookingSegmentOD.setCabinName(segment.getCabinName());
                bookingSegmentOD.setMeal(segment.getMeal());
                bookingSegmentOD.setStopQty(segmentDetail.getStopCount());
                bookingSegmentOD.setBookingCode(segment.getBookingCode());
                bookingSegmentOD.setAirlineCode(segmentDetail.getAirline());
                bookingSegmentOD.setAirlineName(detailSV.getAirlineDetail(requestId, segmentDetail.getAirline()).getName());
                bookingSegmentOD.setEquipType(segmentDetail.getAircraft());
                bookingSegmentOD.setAirCraftName(detailSV.getAircraftDetail(requestId, segmentDetail.getAircraft()).getName());
                bookingSegmentOD.setFlightNumber(Integer.valueOf(segmentDetail.getFlightNumber()));

                var departureLocation = detailSV.getLocationDetail(requestId, segmentDetail.getDeparture());
                bookingSegmentOD.setDepLocationCode(departureLocation.getCode());
                bookingSegmentOD.setDepCity(departureLocation.getCity());
                bookingSegmentOD.setDepAirportName(departureLocation.getAirport());
                bookingSegmentOD.setDepTerminal((segmentDetail.getDepartureTerminal() == null) ? "" : segmentDetail.getDepartureTerminal());
                bookingSegmentOD.setDepTimezone(DateUtility.getZonedDateTimeId(segmentDetail.getDepartureTime()));
                bookingSegmentOD.setDepLatitude(Double.toString(departureLocation.getLatitude()));
                bookingSegmentOD.setDepLongitude(Double.toString(departureLocation.getLongitude()));

                var arrivalLocation = detailSV.getLocationDetail(requestId, segmentDetail.getArrival());
                bookingSegmentOD.setArrLocationCode(arrivalLocation.getCode());
                bookingSegmentOD.setArrCity(arrivalLocation.getCity());
                bookingSegmentOD.setArrAirportName(arrivalLocation.getAirport());
                bookingSegmentOD.setArrTerminal((segmentDetail.getArrivalTerminal() == null) ? "" : segmentDetail.getArrivalTerminal());
                bookingSegmentOD.setArrTimezone(DateUtility.getZonedDateTimeId(segmentDetail.getArrivalTime()));
                bookingSegmentOD.setArrLatitude(Double.toString(arrivalLocation.getLatitude()));
                bookingSegmentOD.setArrLongitude(Double.toString(arrivalLocation.getLongitude()));
                bookingSegmentOD.setDepDate(DateUtility.convertZonedDateTimeToDateTime(DateUtility.plusDays(segmentDetail.getDepartureTime(), segment.getDateAdjustment())));
                bookingSegmentOD.setArrDate(DateUtility.convertZonedDateTimeToDateTime(DateUtility.plusDays(segmentDetail.getArrivalTime(), segment.getDateAdjustment())));
                bookingSegmentOD.setLayover(Math.toIntExact(segment.getLayoverDuration()));

                totalStop += segment.getStops();

                // store segment
                var bookingOd = bookingOriginDestinationRP.save(bookingSegmentOD);
                if (segmentDetail.getStopCount() > 0) {
                    this.insertBookingStopAirport(bookingOd.getId(), segmentDetail.getHiddenStops(), segment.getDateAdjustment());
                }
            }

            bookingOD.setStop(totalStop);
            bookingOriginDestinationRP.save(bookingOD);
        }
    }


    @Override
    public void insertBookingStopAirport(Integer bookingOdId, List<HiddenStop> hiddenStops, int dateAdjustment) {

        for (HiddenStop hiddenStop : hiddenStops) {

            BookingStopAirportEntity stopAirport = new BookingStopAirportEntity();

            stopAirport.setBookingOdId(bookingOdId);
            stopAirport.setElapsedTime((int) hiddenStop.getElapsedTime());
            stopAirport.setLocationCode(hiddenStop.getAirport());
            stopAirport.setCity(flightLocationNQ.getFlightLocationInformation(hiddenStop.getAirport(), 1).getCity());
            stopAirport.setLocation(flightLocationNQ.getFlightLocationInformation(hiddenStop.getAirport(), 1).getAirport());
            stopAirport.setArrDate(DateUtility.convertZonedDateTimeToDateTime(DateUtility.plusDays(hiddenStop.getArrivalTime(), dateAdjustment)));
            stopAirport.setDepDate(DateUtility.convertZonedDateTimeToDateTime(DateUtility.plusDays(hiddenStop.getDepartureTime(), dateAdjustment)));
            stopAirport.setDuration(String.valueOf(hiddenStop.getDuration()));
            stopAirport.setGmtOffset(DateUtility.getZonedDateTimeId(hiddenStop.getArrivalTime()));
            stopAirport.setEquipment(hiddenStop.getEquipment());

            bookingStopAirportRP.save(stopAirport);

        }

    }


    @Override
    public void insertBookingSpecialService(Integer bookingId, JsonNode pnrRS) {

        for (JsonNode specialService : pnrRS.get("CreatePassengerNameRecordRS").get("TravelItineraryRead").get("TravelItinerary").get("SpecialServiceInfo")) {

            BookingSpecialServiceEntity bookingSSR = new BookingSpecialServiceEntity();
            var remark = "";

            bookingSSR.setBookingId(bookingId);
            bookingSSR.setRph(Integer.parseInt(specialService.get("RPH").textValue()));
            bookingSSR.setType(specialService.get("Type").textValue());
            bookingSSR.setSsrCode(specialService.get("Service").get("SSR_Code").textValue());
            bookingSSR.setAirlineCode(specialService.get("Service").get("Airline").get("Code").textValue());

            for (JsonNode text : specialService.get("Service").get("Text")) {
                remark += text.textValue();
            }

            bookingSSR.setRemark(remark);

            bookingSpecialServiceRP.save(bookingSSR);

        }

    }


    @Override
    public List<TravelItineraryTA> insertBookingTravelItinerary(Integer bookingId, JsonNode pnrRS, BookingCreateRQ request) {

        var travelItineraries = pnrRS.get("CreatePassengerNameRecordRS").get("AirPrice").get(0).get("PriceQuote").get("PricedItinerary").get("AirItineraryPricingInfo");
        List<TravelItineraryTA> travelItineraryTAS = new ArrayList<>();

        for (JsonNode travelItin : travelItineraries) {

            var bookingTI = new BookingTravelItineraryEntity();
            var travelItineraryTA = new TravelItineraryTA();

            bookingTI.setBookingId(bookingId);
            bookingTI.setPassType(travelItin.get("PassengerTypeQuantity").get("Code").textValue());
            bookingTI.setPassQty(Integer.valueOf(travelItin.get("PassengerTypeQuantity").get("Quantity").textValue()));

            if (travelItin.get("ItinTotalFare").has("PrivateFare")) {
                bookingTI.setPrivateFare(travelItin.get("ItinTotalFare").get("PrivateFare").get("Ind").textValue());
            }

            bookingTI.setBaseFare(new BigDecimal(travelItin.get("ItinTotalFare").get("BaseFare").get("Amount").textValue()));
            bookingTI.setCurrencyCode(travelItin.get("ItinTotalFare").get("BaseFare").get("CurrencyCode").textValue());

            if (travelItin.get("ItinTotalFare").hasNonNull("EquivFare")) {
                bookingTI.setBaseFare(new BigDecimal(travelItin.get("ItinTotalFare").get("EquivFare").get("Amount").textValue()));
                bookingTI.setCurrencyCode(travelItin.get("ItinTotalFare").get("EquivFare").get("CurrencyCode").textValue());
            }

            bookingTI.setTotalAmount(new BigDecimal(travelItin.get("ItinTotalFare").get("TotalFare").get("Amount").textValue()));
            bookingTI.setTotalTax(new BigDecimal(travelItin.get("ItinTotalFare").get("Taxes").get("TotalAmount").textValue()));
            bookingTI.setNonRefundableInd(travelItin.get("ItinTotalFare").get("NonRefundableInd").textValue());
            bookingTI.setBaggageInfo(travelItin.get("ItinTotalFare").get("BaggageInfo").get("NonUS_DOT_Disclosure").withArray("Text").textValue());
            bookingTI.setFareCalculation(travelItin.get("FareCalculation").get("Text").textValue());
            bookingTI.setEndorsements(travelItin.get("ItinTotalFare").get("Endorsements").get("Text").textValue());

            if (travelItin.get("ItinTotalFare").has("Warnings")) {
                bookingTI.setNoted(travelItin.get("ItinTotalFare").get("Warnings").get("Warning").textValue());
            }

            bookingTI.setPieceStatus(1);
            bookingTI.setBagAirline("");
            bookingTI.setBagPiece(1);
            bookingTI.setBagWeight(20);
            bookingTI.setBagUnit("");
            bookingTI.setComPercentage(BigDecimal.ZERO);
            bookingTI.setComAmount(BigDecimal.ZERO);
            bookingTI.setComMkamount(BigDecimal.ZERO);

            travelItineraryTA.setAmount(bookingTI.getTotalAmount());
            travelItineraryTA.setCurrencyCode(bookingTI.getCurrencyCode());
            travelItineraryTA.setPassengerType(bookingTI.getPassType());

            travelItineraryTAS.add(travelItineraryTA);

            BookingTravelItineraryEntity bookingTravelItineraryEntity = bookingTravelItineraryRP.save(bookingTI);
            this.insertFareBreakdown(bookingTravelItineraryEntity, travelItin.get("PTC_FareBreakdown"));

        }

        return travelItineraryTAS;

    }


    @Override
    public void insertFareBreakdown(BookingTravelItineraryEntity bookingTravelItineraryEntity, JsonNode fareBreakdowns) {

        for (JsonNode fareBreakdown : fareBreakdowns) {

            BookingFareBreakdownEntity bookingFareBreakdownEntity = new BookingFareBreakdownEntity();

            bookingFareBreakdownEntity.setTravelItinId(bookingTravelItineraryEntity.getId());
            bookingFareBreakdownEntity.setCabin(fareBreakdown.get("Cabin").textValue());
            bookingFareBreakdownEntity.setBagAllowance(fareBreakdown.get("FreeBaggageAllowance").textValue());

            if (fareBreakdown.get("FareBasis").isObject()) {
                bookingFareBreakdownEntity.setCode(fareBreakdown.get("FareBasis").get("Code").textValue());
                bookingFareBreakdownEntity.setAmount(new BigDecimal(fareBreakdown.get("FareBasis").get("FareAmount").textValue()));
                bookingFareBreakdownEntity.setPassType(fareBreakdown.get("FareBasis").get("FarePassengerType").textValue());
                bookingFareBreakdownEntity.setFareType(fareBreakdown.get("FareBasis").get("FareType").textValue());
                bookingFareBreakdownEntity.setFilingCarrier(fareBreakdown.get("FareBasis").get("FilingCarrier").textValue());
                bookingFareBreakdownEntity.setGlobalInd(fareBreakdown.get("FareBasis").get("GlobalInd").textValue());
                bookingFareBreakdownEntity.setMarket(fareBreakdown.get("FareBasis").get("Market").textValue());
            }

            bookingFareBreakdownRP.save(bookingFareBreakdownEntity);
        }
    }


    @Override
    public void insertBookingAirTicket(Integer bookingId, List<TravelItineraryTA> travelItineraryTAS, BookingCreateRQ request) {

        request.getPassengers().forEach(bookingPassengerRQ -> {

            var bookingAirTicketEntity = new BookingAirTicketEntity();
            var passengerType = PassengerUtil.type(bookingPassengerRQ.getBirthDate());
            var travel = travelItineraryTAS.stream().filter(e -> e.getPassengerType().equals(passengerType)).findFirst().get();

            bookingAirTicketEntity.setBookingId(bookingId);
            bookingAirTicketEntity.setFirstName(bookingPassengerRQ.getFirstName());
            bookingAirTicketEntity.setLastName(bookingPassengerRQ.getLastName());
            bookingAirTicketEntity.setAmount(travel.getAmount());
            bookingAirTicketEntity.setCurrency(travel.getCurrencyCode());
            bookingAirTicketEntity.setDecimalPlace("2");
            bookingAirTicketEntity.setPassType(bookingUtility.getPassengerType(passengerType));
            bookingAirTicketEntity.setBirthday(bookingPassengerRQ.getBirthDate());
            bookingAirTicketEntity.setGender(bookingPassengerRQ.getGender());
            bookingAirTicketEntity.setNationality(bookingPassengerRQ.getNationality());
            bookingAirTicketEntity.setIdType(bookingPassengerRQ.getIdType());
            bookingAirTicketEntity.setIdNumber(bookingPassengerRQ.getIdNumber());
            bookingAirTicketEntity.setStatus(0);

            bookingAirTicketRP.save(bookingAirTicketEntity);
        });

    }

    @Override
    public void updateTicket(JsonNode ticketInfo, BookingEntity booking) {

        var tickets = bookingAirTicketRP.getTickets(booking.getId());
        var numberTicket = ticketInfo.get("AirTicketRS").get("Summary");

        numberTicket.forEach(item -> {

            tickets
                .stream()
                .filter(ticket -> ticket.getFirstName().equalsIgnoreCase(item.get("FirstName").textValue()) && ticket.getLastName().equalsIgnoreCase(item.get("LastName").textValue()))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.setTicketNumber(item.get("DocumentNumber").textValue());
                    ticket.setStatus(1);
                    bookingAirTicketRP.save(ticket);
                });

        });

        booking.setStatus(TicketConstant.TICKET_ISSUED);
        bookingRP.save(booking);
    }

}
