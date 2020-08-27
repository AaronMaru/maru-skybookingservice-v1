package com.skybooking.skyflightservice.v1_0_0.service.implement.backoffice;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.baggages.BookingBaggageAllowanceEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.baggages.BookingBaggageEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.*;
import com.skybooking.skyflightservice.v1_0_0.io.repository.baggages.BookingBaggageAllowanceRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.baggages.BookingBaggageRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.FullReservation;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary.Item;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary.Itinerary;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary.PriceQuote;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation.*;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.OfflineItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.GeneratorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfflineBooking {

    private final BookingRP bookingRP;
    private final BookingOriginDestinationRP originDestinationRP;
    private final BookingStopAirportRP stopAirportRP;
    private final BookingTravelItineraryRP travelItineraryRP;
    private final BookingFareBreakdownRP fareBreakdownRP;
    private final BookingSpecialServiceRP specialServiceRP;
    private final BookingBaggageRP baggageRP;
    private final BookingBaggageAllowanceRP baggageAllowanceRP;
    private final BookingAirTicketRP airTicketRP;
    private final BookingUtility bookingUtility;
    private final BookingPassengerRP bookingPassengerRP;

    private final String dateTimeFormatter = "yyyy-MM-dd'T'HH:mm:ss";


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert reservation record to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param fullReservation
     * @param itineraryRQ
     * @return Boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    protected Boolean save(FullReservation fullReservation, OfflineItineraryRQ itineraryRQ) {

        try {

            BookingEntity booking = booking(fullReservation.getReservation(), itineraryRQ);
            originDestination(booking.getId(), fullReservation.getReservation());
            specialService(booking.getId(), fullReservation.getReservation());
            travelItinerary(booking.getId(), fullReservation);
            airTicket(booking, fullReservation);
            baggage(booking.getId(), fullReservation.getItineraryDetail());

            return true;

        } catch (Exception exception) {
            return false;
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itinerary
     * @param itineraryRQ
     * @return BookingEntity
     */
    protected BookingEntity booking(Reservation itinerary, OfflineItineraryRQ itineraryRQ) {

        //TODO: reference code must be exists.
        BookingEntity oldBooking = bookingRP.findByBookingCode(itineraryRQ.getReferenceCode());

        BigDecimal totalAmount = itinerary
                .getAccountingLines()
                .getAccountingLine()
                .stream()
                .map(it -> it.getBaseFare().add(it.getCommissionAmount()).add(it.getTaxAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal commission = itinerary
            .getAccountingLines()
            .getAccountingLine()
            .stream()
            .map(AccountingLine::getCommissionAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        String departureDate = itinerary.getPassengerReservation().getSegments().getPoc().getDeparture();
        double markupPercentage = itineraryRQ.getMarkupPercentage() / 100;
        BigDecimal markupAmount = totalAmount.multiply(BigDecimal.valueOf(markupPercentage));

        BookingEntity booking = new BookingEntity();
        booking.setStakeholderUserId(oldBooking.getStakeholderUserId());
        booking.setStakeholderCompanyId(oldBooking.getStakeholderCompanyId());
        booking.setCustName(oldBooking.getCustName());
        booking.setCustAddress(oldBooking.getCustAddress());
        booking.setCustCity(oldBooking.getCustCity());
        booking.setCustZip(oldBooking.getCustZip());
        booking.setContFullname(oldBooking.getContFullname());
        booking.setContPhonecode(oldBooking.getContPhonecode());
        booking.setContPhone(oldBooking.getContPhone());
        booking.setContEmail(oldBooking.getContEmail());
        booking.setContLocationCode(oldBooking.getContLocationCode());
        booking.setBookingCode(GeneratorUtils.bookingCode(bookingRP.getLatestRow()));
        booking.setBookingType(oldBooking.getBookingType());
        booking.setItineraryRef(itineraryRQ.getPnrCode());
        booking.setDepDate(DateUtility.convertDateTimeToDate(departureDate, dateTimeFormatter));
        booking.setTripType(oldBooking.getTripType());
        booking.setPq(bookingUtility.getPassengerQuantityCodeNumber(new ArrayList<>()));
        booking.setTotalAmount(totalAmount);
        booking.setCurrencyConvert(oldBooking.getCurrencyConvert());
        booking.setCurrencyCode(oldBooking.getCurrencyCode());
        booking.setCurrencyConRate(oldBooking.getCurrencyConRate());
        booking.setMarkupAmount(markupAmount);
        booking.setMarkupPercentage(Double.toString(markupPercentage));
        booking.setVatPercentage(0.0);
        booking.setIsCheck(0);
        booking.setIsAdditional(0);
        booking.setIsOfflineBooking(1);
        booking.setReferenceCode(oldBooking.getBookingCode());
        booking.setStatus(TicketConstant.TICKET_ISSUED);
        booking.setCreatedBy(String.valueOf(itineraryRQ.getEmployeeId())); // employee created offline booking
        booking.setDisPayMetAmount(BigDecimal.ZERO);
        booking.setDisPayMetPercentage(BigDecimal.ZERO);
        booking.setMarkupPayAmount(BigDecimal.ZERO);
        booking.setMarkupPayPercentage(BigDecimal.ZERO);
        booking.setPayMetFeeAmount(BigDecimal.ZERO);
        booking.setPayMetFeePercentage(BigDecimal.ZERO);
        booking.setCommissionAmount(commission);
        booking.setCommissionTotalAmount(totalAmount.add(markupAmount).subtract(commission));
        booking.setIsOfflineBooking(1);
        booking.setReferenceCode(itineraryRQ.getReferenceCode());

        oldBooking.setStatus(TicketConstant.TICKET_ISSUED_MANUAL);
        bookingRP.save(oldBooking);

        return bookingRP.save(booking);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert origin destination entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param itinerary
     */
    protected void originDestination(Integer bookingId, Reservation itinerary) {

        List<Segment> segments = itinerary.getPassengerReservation().getSegments().getSegment();

        int parentId = 0;
        int totalStop = 0;

        for (Segment segment : segments) {

            var directSegment = !segment.getAir().isInboundConnection() && !segment.getAir().isOutboundConnection();
            var parentSegment = !segment.getAir().isInboundConnection() && segment.getAir().isOutboundConnection();
            var childSegment = segment.getAir().isInboundConnection() && segment.getAir().isOutboundConnection();
            var lastSegment = segment.getAir().isInboundConnection() && !segment.getAir().isOutboundConnection();

            BookingOriginDestinationEntity bookingOriginDestination = new BookingOriginDestinationEntity();

            bookingOriginDestination.setBookingId(bookingId);

            if (directSegment) {

                bookingOriginDestination.setGroupAirSegs("");
                bookingOriginDestination.setArrageAirSegs("");
                bookingOriginDestination.setElapsedTime(0);
                bookingOriginDestination.setMultipleAirStatus(0);
                bookingOriginDestination.setAdjustmentDate(0);
                bookingOriginDestination.setSeatRemaining(0);
                bookingOriginDestination.setStopQty(segment.getAir().getStopQuantity());
                bookingOriginDestination.setStop(segment.getAir().getStopQuantity());
                bookingOriginDestination.setAirlineCode(segment.getAir().getMarketingAirlineCode());
                bookingOriginDestination.setAirlineName("");
                bookingOriginDestination.setEquipType(segment.getAir().getEquipmentType());
                bookingOriginDestination.setAirCraftName("");
                bookingOriginDestination.setFlightNumber(segment.getAir().getFlightNumber());

                bookingOriginDestination.setDepLocationCode(segment.getAir().getDepartureAirport());
                bookingOriginDestination.setDepCity("");
                bookingOriginDestination.setDepAirportName("");
                bookingOriginDestination.setDepTerminal(segment.getAir().getDepartureTerminalCode());
                bookingOriginDestination.setDepTimezone(0);
                bookingOriginDestination.setDepDate(DateUtility.convertDateTimeToDate(segment.getAir().getDepartureDateTime(), dateTimeFormatter));
                bookingOriginDestination.setDepLatitude("0");
                bookingOriginDestination.setDepLongitude("0");

                bookingOriginDestination.setArrLocationCode(segment.getAir().getArrivalAirport());
                bookingOriginDestination.setArrCity("");
                bookingOriginDestination.setArrAirportName("");
                bookingOriginDestination.setArrTerminal(segment.getAir().getArrivalTerminalCode());
                bookingOriginDestination.setArrTimezone(0);
                bookingOriginDestination.setArrDate(DateUtility.convertDateTimeToDate(segment.getAir().getArrivalDateTime(), dateTimeFormatter));
                bookingOriginDestination.setArrLatitude("0");
                bookingOriginDestination.setDepLongitude("0");

                bookingOriginDestination.setLayover(0);
                bookingOriginDestination.setAdjustmentDate(0);
                bookingOriginDestination.setAirlineRef(segment.getAir().getAirlineRefId());

                bookingOriginDestination.setCabinCode(segment.getAir().getCabin().getCode());
                bookingOriginDestination.setCabinName(segment.getAir().getCabin().getName());
                bookingOriginDestination.setMeal(segment.getAir().getMeal().getCode());

                //TODO: missing value
                bookingOriginDestination.setBookingCode("");
                bookingOriginDestination.setStop(0);

                originDestinationRP.save(bookingOriginDestination);


            }

            if (parentSegment) {

                bookingOriginDestination.setGroupAirSegs("");
                bookingOriginDestination.setArrageAirSegs("");
                bookingOriginDestination.setElapsedTime(0);
                bookingOriginDestination.setMultipleAirStatus(0);
                bookingOriginDestination.setAdjustmentDate(0);

                parentId = originDestinationRP.save(bookingOriginDestination).getId();

            }

            if (childSegment || lastSegment) {

                bookingOriginDestination.setParentId(parentId);
                bookingOriginDestination.setElapsedTime(0);
                bookingOriginDestination.setMultipleAirStatus(0);
                bookingOriginDestination.setSeatRemaining(0);
                bookingOriginDestination.setStopQty(segment.getAir().getStopQuantity());
                bookingOriginDestination.setStop(segment.getAir().getStopQuantity());
                bookingOriginDestination.setAirlineCode(segment.getAir().getMarketingAirlineCode());
                bookingOriginDestination.setAirlineName("");
                bookingOriginDestination.setEquipType(segment.getAir().getEquipmentType());
                bookingOriginDestination.setAirCraftName("");
                bookingOriginDestination.setFlightNumber(segment.getAir().getFlightNumber());

                bookingOriginDestination.setDepLocationCode(segment.getAir().getDepartureAirport());
                bookingOriginDestination.setDepCity("");
                bookingOriginDestination.setDepAirportName("");
                bookingOriginDestination.setDepTerminal(segment.getAir().getDepartureTerminalCode());
                bookingOriginDestination.setDepTimezone(0);
                bookingOriginDestination.setDepDate(DateUtility.convertDateTimeToDate(segment.getAir().getDepartureDateTime(), dateTimeFormatter));
                bookingOriginDestination.setDepLatitude("0");
                bookingOriginDestination.setDepLongitude("0");

                bookingOriginDestination.setArrLocationCode(segment.getAir().getArrivalAirport());
                bookingOriginDestination.setArrCity("");
                bookingOriginDestination.setArrAirportName("");
                bookingOriginDestination.setArrTerminal(segment.getAir().getArrivalTerminalCode());
                bookingOriginDestination.setArrTimezone(0);
                bookingOriginDestination.setArrDate(DateUtility.convertDateTimeToDate(segment.getAir().getArrivalDateTime(), dateTimeFormatter));
                bookingOriginDestination.setArrLatitude("0");
                bookingOriginDestination.setDepLongitude("0");

                bookingOriginDestination.setLayover(0);
                bookingOriginDestination.setAdjustmentDate(0);
                bookingOriginDestination.setAirlineRef(segment.getAir().getAirlineRefId());

                bookingOriginDestination.setCabinCode(segment.getAir().getCabin().getCode());
                bookingOriginDestination.setCabinName(segment.getAir().getCabin().getName());
                bookingOriginDestination.setMeal(segment.getAir().getMeal().getCode());

                //TODO: missing value
                bookingOriginDestination.setBookingCode("");

                totalStop = totalStop + segment.getAir().getStopQuantity() + 1;

                var child = originDestinationRP.save(bookingOriginDestination);

                var hiddenSegments = segment.getAir().getHiddenStop();

                if (!hiddenSegments.isEmpty()) {
                    this.stopAirport(child.getId(), hiddenSegments);
                }

                if (lastSegment) {

                    var parent = originDestinationRP.getOne(parentId);
                    parent.setStop(totalStop);
                    originDestinationRP.save(parent);

                    // reset
                    totalStop = 0;
                    parentId = 0;
                }
            }
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert stop air port entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingODId
     * @param segments
     */
    protected void stopAirport(Integer bookingODId, List<HiddenStop> segments) {

        for (HiddenStop segment : segments) {

            BookingStopAirportEntity stopAirport = new BookingStopAirportEntity();

            stopAirport.setBookingOdId(bookingODId);
            stopAirport.setArrDate(DateUtility.convertDateTimeToDate(segment.getArrivalDateTime(), dateTimeFormatter));
            stopAirport.setDepDate(DateUtility.convertDateTimeToDate(segment.getDepartureDateTime(), dateTimeFormatter));
            stopAirport.setLocationCode(segment.getAirport());
            stopAirport.setEquipment(segment.getEquipmentType());

            // TODO: insert missing fields

            stopAirportRP.save(stopAirport);
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert special service entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param itinerary
     */
    protected void specialService(Integer bookingId, Reservation itinerary) {

        List<OpenReservationElement> reservationElement = itinerary.getOpenReservationElements().getOpenReservationElement();

        for (OpenReservationElement element : reservationElement) {

            if (element.getServiceRequest() == null) continue;

            BookingSpecialServiceEntity bookingSSE = new BookingSpecialServiceEntity();

            bookingSSE.setRph(element.getId());
            bookingSSE.setBookingId(bookingId);
            bookingSSE.setSsrCode(element.getServiceRequest().getServiceType());
            bookingSSE.setType(element.getServiceRequest().getSsrType());
            bookingSSE.setAirlineCode(element.getServiceRequest().getAirlineCode());
            bookingSSE.setRemark(element.getServiceRequest().getFullText());

            specialServiceRP.save(bookingSSE);

        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking travel itinerary entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param itinerary
     */
    protected void travelItinerary(Integer bookingId, FullReservation itinerary) {

        String cabinCode = itinerary
                .getReservation()
                .getPassengerReservation()
                .getSegments()
                .getSegment()
                .stream()
                .findFirst()
                .map(it -> it.getAir().getCabin().getCode())
                .orElse("");


        var itineraryPricing = itinerary.getItineraryDetail().getTravelItinerary().getItineraryInfo().getItineraryPricing();

        List<PriceQuote> priceQuotes = itineraryPricing.getPriceQuote();

        for (PriceQuote quote : priceQuotes) {

            String passengerCode = quote.getPricedItinerary().getAirItineraryPricingInfo().getPassengerTypeQuantity().getCode();
            String passengerQuantity = quote.getPricedItinerary().getAirItineraryPricingInfo().getPassengerTypeQuantity().getQuantity();

            String currencyCode = quote.getPricedItinerary().getAirItineraryPricingInfo().getItinTotalFare().getTotalFare().getCurrencyCode();
            BigDecimal totalAmount = quote.getPricedItinerary().getAirItineraryPricingInfo().getItinTotalFare().getTotalFare().getAmount();
            BigDecimal baseFareAmount = quote.getPricedItinerary().getAirItineraryPricingInfo().getItinTotalFare().getBaseFare().getAmount();
            BigDecimal taxAmount = quote.getPricedItinerary().getAirItineraryPricingInfo().getItinTotalFare().getTaxes().getTax().getAmount();

            BookingTravelItineraryEntity bookingTravelItinerary = new BookingTravelItineraryEntity();
            bookingTravelItinerary.setBookingId(bookingId);
            bookingTravelItinerary.setPassType(bookingUtility.getPassengerType(passengerCode));
            bookingTravelItinerary.setPassQty(Integer.valueOf(passengerQuantity));
            bookingTravelItinerary.setCurrencyCode(currencyCode);
            bookingTravelItinerary.setBaseFare(baseFareAmount);
            bookingTravelItinerary.setTotalTax(taxAmount);
            bookingTravelItinerary.setTotalAmount(totalAmount);
            bookingTravelItinerary.setNonRefundableInd(false);

            bookingTravelItinerary.setPrivateFare("");
            bookingTravelItinerary.setBaggageInfo("");
            bookingTravelItinerary.setFareCalculation("");
            bookingTravelItinerary.setEndorsements("");
            bookingTravelItinerary.setNoted("");
            bookingTravelItinerary.setBagAirline("");
            bookingTravelItinerary.setPieceStatus(1);
            bookingTravelItinerary.setBagPiece(1);
            bookingTravelItinerary.setBagWeight(20);
            bookingTravelItinerary.setBagUnit("kg");
            bookingTravelItinerary.setComPercentage(BigDecimal.ZERO);
            bookingTravelItinerary.setComAmount(BigDecimal.ZERO);
            bookingTravelItinerary.setComMkamount(BigDecimal.ZERO);

            BookingTravelItineraryEntity created = travelItineraryRP.save(bookingTravelItinerary);

            /* insert fare breakdown entity*/
            fareBreakdown(created.getId(), quote, cabinCode, passengerCode, totalAmount);


        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert fare breakdown entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param travelItineraryId
     * @param itinerary
     * @param cabinCode
     * @param passengerCode
     * @param totalAmount
     */
    protected void fareBreakdown(Integer travelItineraryId, PriceQuote itinerary, String cabinCode, String passengerCode, BigDecimal totalAmount) {

        var ptcFareBreakdown = itinerary.getPricedItinerary().getAirItineraryPricingInfo().getPTC_FareBreakdown();

        BookingFareBreakdownEntity bookingFareBreakdown = new BookingFareBreakdownEntity();
        bookingFareBreakdown.setTravelItinId(travelItineraryId);
        bookingFareBreakdown.setCabin(cabinCode);
        bookingFareBreakdown.setBagAllowance("");
        bookingFareBreakdown.setCode("");
        bookingFareBreakdown.setAmount(totalAmount);
        bookingFareBreakdown.setPassType(bookingUtility.getPassengerType(passengerCode));
        bookingFareBreakdown.setFareType("");
        bookingFareBreakdown.setFilingCarrier("");
        bookingFareBreakdown.setGlobalInd("");
        bookingFareBreakdown.setMarket("");

        fareBreakdownRP.save(bookingFareBreakdown);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking air ticket entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param booking
     * @param fullReservation
     */
    protected void airTicket(BookingEntity booking, FullReservation fullReservation) {

        List<String> tickets = fullReservation.getReservation()
                .getPassengerReservation()
                .getTicketingInfo()
                .getTicketDetails()
                .stream()
                .map(TicketDetail::getTicketNumber)
                .collect(Collectors.toList());

        List<AccountingLine> accountingLines = fullReservation.getReservation().getAccountingLines().getAccountingLine();

        for (AccountingLine accountingLine : accountingLines) {
            Passenger resPassenger = fullReservation
                    .getReservation()
                    .getPassengerReservation()
                    .getPassengers()
                    .getPassenger()
                    .stream()
                    .filter(item -> item.getNameAssocId() == accountingLine.getIndex())
                    .findFirst()
                    .get();

            String ticketNumber = tickets
                    .stream()
                    .filter(it -> it.contains(accountingLine.getDocumentNumber()))
                    .findFirst()
                    .orElse("");

            BigDecimal totalAmount = accountingLine.getBaseFare().add(accountingLine.getTaxAmount());

            List<BookingPassengerEntity> passengerEntities;
            if (booking.getStakeholderCompanyId() == null)
                passengerEntities = bookingPassengerRP.retrieveByUser(booking.getStakeholderCompanyId());
            else
                passengerEntities = bookingPassengerRP.retrieveByOwner(booking.getStakeholderUserId());

            BookingAirTicketEntity bookingAirTicket = new BookingAirTicketEntity();
            bookingAirTicket.setBookingId(booking.getId());
            bookingAirTicket.setFirstName(resPassenger.getFirstName());
            bookingAirTicket.setLastName(resPassenger.getLastName());
            bookingAirTicket.setPassType(bookingUtility.getPassengerType(resPassenger.getPassengerType()));
            bookingAirTicket.setTicketNumber(ticketNumber);
            bookingAirTicket.setCurrency("USD");
            bookingAirTicket.setAmount(totalAmount);
            bookingAirTicket.setDecimalPlace("2");

            if (passengerEntities.size() > 0) {
                passengerEntities.stream()
                        .filter(item -> item.getFirstName().equals(resPassenger.getFirstName())
                                && item.getLastName().equals(resPassenger.getLastName()))
                        .findFirst()
                        .ifPresent(passenger -> {
                            bookingAirTicket.setGender(passenger.getGender());
                            bookingAirTicket.setBirthday(passenger.getBirthday());
                            bookingAirTicket.setNationality(passenger.getNationality());
                            bookingAirTicket.setIdType(passenger.getIdType());
                            bookingAirTicket.setStatus(1);
                            bookingAirTicket.setIdNumber(passenger.getIdNumber());
                        });

            } else {
                bookingAirTicket.setGender("");
                bookingAirTicket.setBirthday(new Date());
                bookingAirTicket.setNationality("");
                bookingAirTicket.setIdType(0);
                bookingAirTicket.setStatus(1);
                bookingAirTicket.setIdNumber("");
            }

            airTicketRP.save(bookingAirTicket);

        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert baggage entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param itinerary
     */
    protected void baggage(Integer bookingId, Itinerary itinerary) {
        int index = 0;

        for (Item item : itinerary.getTravelItinerary().getItineraryInfo().getReservationItems().getItem()) {
            BookingBaggageEntity baggage = new BookingBaggageEntity();
            baggage.setBookingId(bookingId);
            baggage.setLocationCode(item.getFlightSegment().get(0).getOriginLocation().getLocationCode().concat("-")
                    .concat(item.getFlightSegment().get(0).getDestinationLocation().getLocationCode()));

            BookingBaggageEntity saved = baggageRP.save(baggage);

            for (PriceQuote priceQuote : itinerary.getTravelItinerary().getItineraryInfo().getItineraryPricing().getPriceQuote()) {

                var flightSegment = priceQuote
                        .getPricedItinerary()
                        .getAirItineraryPricingInfo()
                        .getPTC_FareBreakdown()
                        .getFlightSegment()
                        .get(index);

                BookingBaggageAllowanceEntity baggageAllowance = new BookingBaggageAllowanceEntity();
                baggageAllowance.setBaggage_id(saved.getId());
                baggageAllowance.setPassType(priceQuote.getPricedItinerary().getAirItineraryPricingInfo().getPassengerTypeQuantity().getCode());
                baggageAllowance.setAirlineCode(flightSegment.getMarketingAirline().getCode());

                if (flightSegment.getBaggageAllowance() != null) {
                    if (flightSegment.getBaggageAllowance().getNumber().contains("P")) {
                        baggageAllowance.setIsPiece((byte) 1);
                        baggageAllowance.setPieces(Integer.parseInt(flightSegment.getBaggageAllowance().getNumber().replace("P", "")));
                        baggageAllowance.setWeight(20); // TODO: sometimes have weight
                    } else {
                        if (flightSegment.getBaggageAllowance().getNumber().contains("NIL")) {
                            baggageAllowance.setIsPiece((byte) 0);
                            baggageAllowance.setPieces(0);
                            baggageAllowance.setWeight(0);
                        } else {
                            baggageAllowance.setIsPiece((byte) 0);
                            baggageAllowance.setPieces(0);
                            baggageAllowance.setWeight(Integer.parseInt(flightSegment.getBaggageAllowance().getNumber().replace("K", "")));
                        }
                    }
                }

                baggageAllowance.setUnit("kg");
                baggageAllowance.setNonRefundable(false);
                baggageAllowanceRP.save(baggageAllowance);

            }
            index++;
        }
    }

}
