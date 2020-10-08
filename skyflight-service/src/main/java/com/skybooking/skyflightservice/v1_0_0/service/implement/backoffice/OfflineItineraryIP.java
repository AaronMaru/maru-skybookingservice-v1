package com.skybooking.skyflightservice.v1_0_0.service.implement.backoffice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.BookingAction;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingAirTicketEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingPaymentTransactionEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingAirTicketRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingPaymentTransactionRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.implement.baseservice.BaseServiceIP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice.OfflineItinerarySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.FullReservation;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary.Itinerary;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation.AccountingLine;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation.PricedItinerary;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation.Reservation;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.CheckItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.OfflineItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.CheckOfflineItineraryRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.CreateOfflineBookingRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary.PassengerInfoRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary.PaymentInfoRS;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OfflineItineraryIP extends BaseServiceIP implements OfflineItinerarySV {

    public static final String OFFLINE_BOOKING_CACHED = "OFFLINE_BOOKING_CACHED";
    public static final String PREFIX_ID = "BOOKING_ITINERARY_OFFLINE_";

    private final HazelcastInstance instance;
    private final AppConfig appConfig;
    private final BookingAction bookingAction;
    private final OfflineBooking offlineBooking;
    private final BookingRP bookingRP;
    private final BookingPaymentTransactionRP paymentRP;
    private final BookingAirTicketRP airTicketRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * checking offline reservation by PNR code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param checkItineraryRQ CheckItineraryRQ
     * @return StructureRS
     */
    @Override
    public StructureRS check(CheckItineraryRQ checkItineraryRQ) {

        FullReservation fullReservation = getOfflineReservation(checkItineraryRQ.getPnrCode());

        if (fullReservation == null)
            return responseBody(HttpStatus.BAD_REQUEST, "PNR_CODE_INVALID");

        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal commission = fullReservation
            .getReservation()
            .getAccountingLines()
            .getAccountingLine()
            .stream()
            .map(AccountingLine::getCommissionAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (PricedItinerary pricedItinerary: fullReservation.getReservation().getPassengerReservation().getItineraryPricing().getPricedItinerary()) {
            amount = amount.add(pricedItinerary.getAirItineraryPricingInfo().getItinTotalFare().getTotals().getTotal().getContent());
        }


        List<PassengerInfoRS> passengerInfo = new ArrayList<>();
        PaymentInfoRS paymentInfo = null;

        if (checkItineraryRQ.getReferenceCode() != null) {
            BookingEntity bookingEntity = bookingRP.getBookingByBookingCode(checkItineraryRQ.getReferenceCode(), TicketConstant.TICKET_FAIL);
            if (bookingEntity != null) {
                BookingPaymentTransactionEntity paymentEntity = paymentRP.findByBookingIdAndStatus(bookingEntity.getId(), 1);
                if (paymentEntity != null) {
                    paymentInfo = new PaymentInfoRS();
                    paymentInfo.setAmount(paymentEntity.getAmount());
                    paymentInfo.setCurrency(paymentEntity.getCurrency());
                    paymentInfo.setCardHolder(paymentEntity.getHolderName());
                    paymentInfo.setCardNumber(paymentEntity.getCardNumber());
                    paymentInfo.setBankName(paymentEntity.getBankName());
                    paymentInfo.setPaymentCode(paymentEntity.getPaymentCode());
                }

                List<BookingAirTicketEntity> airTicketEntities = airTicketRP.getTickets(bookingEntity.getId());
                if (airTicketEntities.size() > 0) {
                    airTicketEntities.forEach(item -> {
                        PassengerInfoRS passenger = new PassengerInfoRS();
                        passenger.setFirstName(item.getFirstName());
                        passenger.setLastName(item.getLastName());
                        passenger.setPassengerType(item.getPassType());
                        passenger.setAmount(item.getAmount());
                        passenger.setCurrency(item.getCurrency());
                        passenger.setBirthDay(item.getBirthday());
                        passenger.setGender(PassengerUtil.gender(item.getGender()));
                        passenger.setNationality(item.getNationality());
                        passenger.setIdType(PassengerUtil.idType(item.getIdType()));
                        passenger.setIdNumber(item.getIdNumber());
                        passengerInfo.add(passenger);
                    });
                }
            }
        } else {
            fullReservation.getReservation().getAccountingLines().getAccountingLine().forEach(item -> {
                fullReservation
                    .getReservation()
                    .getPassengerReservation()
                    .getPassengers()
                    .getPassenger()
                    .stream()
                    .filter(psg -> psg.getNameAssocId() == item.getIndex())
                    .findFirst()
                    .ifPresent(psg -> {
                        PassengerInfoRS passenger = new PassengerInfoRS();
                        passenger.setFirstName(psg.getFirstName());
                        passenger.setLastName(psg.getLastName());
                        passenger.setPassengerType(psg.getPassengerType());
                        passenger.setTicketNumber(item.getDocumentNumber());
                        passenger.setAmount(item.getBaseFare().add(item.getTaxAmount()).subtract(item.getCommissionAmount()));
                        passengerInfo.add(passenger);
                    });
            });
        }

        return responseBodyWithSuccessMessage(new CheckOfflineItineraryRS(amount, commission, passengerInfo, paymentInfo));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create and insert offline PNR reservation to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraryRQ OfflineItineraryRQ
     * @return StructureRS
     */
    @Override
    public StructureRS create(OfflineItineraryRQ itineraryRQ) {

        FullReservation fullReservation = getOfflineReservation(itineraryRQ.getPnrCode());

        if (fullReservation == null)
            return responseBody(HttpStatus.INTERNAL_SERVER_ERROR);

        String bookingCode = offlineBooking.save(fullReservation, itineraryRQ);

        if (bookingCode.equals("")) return responseBody(HttpStatus.INTERNAL_SERVER_ERROR);
        else return responseBody(HttpStatus.CREATED, "", new CreateOfflineBookingRS(bookingCode));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * save reservation to cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id              String
     * @param fullReservation FullReservationTA
     */
    private void saveOfflineItinerary(String id, FullReservation fullReservation) {
        instance
                .getMap(OFFLINE_BOOKING_CACHED)
                .put(PREFIX_ID + id, fullReservation, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get offline reservation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pnrCode
     * @return
     */
    private FullReservation getOfflineReservation(String pnrCode) {

        FullReservation fullReservation = (FullReservation) instance
                .getMap(OFFLINE_BOOKING_CACHED)
                .getOrDefault(PREFIX_ID + pnrCode, null);

        if (fullReservation != null) return fullReservation;

        return this.fetchOfflineReservation(pnrCode);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * fetch offline reservation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pnrCode
     * @return Reservation
     */
    @SneakyThrows
    private FullReservation fetchOfflineReservation(String pnrCode) {

        var response = bookingAction.getItinerary(pnrCode);

        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Reservation reservation = mapper.treeToValue(response.get("reservation"), Reservation.class);
        Itinerary itineraryDetail = mapper.treeToValue(response.get("itineraryDetail"), Itinerary.class);

        FullReservation fullReservation = new FullReservation();
        fullReservation.setReservation(reservation);
        fullReservation.setItineraryDetail(itineraryDetail);

        try {
            if (fullReservation.getReservation().getAccountingLines().getAccountingLine().isEmpty())
                return null;

            saveOfflineItinerary(pnrCode, fullReservation);
            return fullReservation;

        } catch (Exception e) {
            return null;
        }
    }

}
