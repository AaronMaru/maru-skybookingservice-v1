package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.constant.MessageConstant;
import com.skybooking.skyflightservice.constant.RevalidateConstant;
import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.OriginDestination;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.RevalidateRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RevalidateFlight {

    @Autowired
    private ShoppingAction shoppingAction;

    @Autowired
    private DetailSV detailSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Revalidate flight shopping
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingRQ
     * @return Boolean
     */
    public RevalidateM revalidate(BookingCreateRQ bookingRQ) {

        var adult = 0;
        var child = 0;
        var infant = 0;
        var requestId = bookingRQ.getRequestId();

        for (BookingPassengerRQ passenger: bookingRQ.getPassengers()) {
            if (PassengerUtil.type(passenger.getBirthDate()) == PassengerCode.ADULT) {
                adult++;
            } else if (PassengerUtil.type(passenger.getBirthDate()) == PassengerCode.CHILD) {
                child++;
            } else {
                infant++;
            }
        }

        var seats = adult + child;

        for (String leg: bookingRQ.getLegIds()) {
            List<OriginDestination> originDestinations = new ArrayList<>();
            List<BookingSegmentDRQ> segments = new ArrayList<>();
            Leg legDetail = detailSV.getLegDetail(requestId, leg);
            for (LegSegmentDetail legSegment: legDetail.getSegments()) {
                segments.add(
                        this.setDSegment(
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

            RevalidateRQ request = new RevalidateRQ();
            request.setAdult(adult);
            request.setChild(child);
            request.setInfant(infant);
            request.setOriginDestinations(originDestinations);

            JsonNode airItineraryPriceInfo = shoppingAction.revalidate(request);
            System.out.println(airItineraryPriceInfo);
            if (!this.checkPrice(airItineraryPriceInfo, detailSV.getPriceDetail(requestId, legDetail.getPrice()), adult, child, infant)) {
                return new RevalidateM(RevalidateConstant.PRICE_CHANGED, MessageConstant.PRICE_CHANGED);
            }
            if (!this.checkSeats(airItineraryPriceInfo, seats)) {
                return new RevalidateM(RevalidateConstant.UNAVAILABLE_SEATS, MessageConstant.UNAVAILABLE_SEATS);
            }
        }

        return new RevalidateM(RevalidateConstant.SUCCESS);
    }


    public BookingSegmentDRQ setDSegment(Segment segmentDetail, int dateAdjustment, String resBookDesigCode, int seats) {

        var segment = this.setDistSegment(segmentDetail, dateAdjustment, resBookDesigCode);
        segment.setNumInParty(seats + "");
        segment.setStatus("NN");

        return segment;
    }


    public BookingSegmentDRQ setDSegment(Segment segmentDetail, int dateAdjustment, String resBookDesigCode) {

        var segment = this.setDistSegment(segmentDetail, dateAdjustment, resBookDesigCode);
        segment.setType("A");

        return segment;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set Segment for Supplier
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param segmentDetail
     * @param dateAdjustment
     * @param resBookDesigCode
     * @return
     */
    private BookingSegmentDRQ setDistSegment(Segment segmentDetail, int dateAdjustment, String resBookDesigCode) {

        var segment = new BookingSegmentDRQ();
        segment.setDepDateTime(DateUtility.plusDays(segmentDetail.getDepartureTime(), dateAdjustment));
        segment.setArrDateTime(DateUtility.plusDays(segmentDetail.getArrivalTime(), dateAdjustment));
        segment.setDepCode(segmentDetail.getDeparture());
        segment.setArrCode(segmentDetail.getArrival());
        segment.setFlightNum(segmentDetail.getFlightNumber());
        segment.setAirline(segmentDetail.getAirline());
        segment.setOptAirline(segmentDetail.getOperatingAirline());
        segment.setResBookDesignCode(resBookDesigCode);

        return segment;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Air itinerary pricing information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param response
     * @return
     */
    private JsonNode getAirItineraryPricingInfo(JsonNode response) {
        return response
                .get("OTA_AirLowFareSearchRS")
                .get("PricedItineraries")
                .get("PricedItinerary")
                .get(0)
                .get("AirItineraryPricingInfo");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param response
     * @param priceDetail
     * @param adult
     * @param child
     * @param infant
     * @return
     */
    private Boolean checkPrice(JsonNode response, PriceDetail priceDetail, int adult, int child, int infant) {
        BigDecimal amount = BigDecimal.valueOf(0);
        Double priceValid = this.getAirItineraryPricingInfo(response)
                .get(0)
                .get("ItinTotalFare")
                .get("TotalFare")
                .get("Amount")
                .doubleValue();

        /**
         * calculate total amount of passengers
         */
        for (Price price: priceDetail.getDetails()) {
            BigDecimal pricePsg = price.getBaseFare().add(price.getTax());

            if (adult > 0 && price.getType().equals(PassengerCode.ADULT)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(adult)));
            } else if (child > 0 && price.getType().equals(PassengerCode.CHILD)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(child)));
            } else if (infant > 0 && price.getType().equals(PassengerCode.INFANT)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(infant)));
            }
        }
        return priceValid.equals(NumberFormatter.amount(amount.doubleValue()));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check seats available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param response
     * @param seats
     * @return
     */
    private Boolean checkSeats(JsonNode response, int seats) {

        var segs = this.getAirItineraryPricingInfo(response).get(0).get("FareInfos").get("FareInfo");

        for (var seg: segs) {
            if (seats > seg.get("TPA_Extensions").get("SeatsRemaining").get("Number").intValue()) {
                return false;
            }
        }

        return true;
    }
}