package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.config.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.OriginDestination;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.RevalidateRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
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
    public Boolean revalidate(BCreateRQ bookingRQ) {

        var adult = 0;
        var child = 0;
        var infant = 0;
        var requestId = bookingRQ.getRequestId();

        for (BPassengerRQ passenger: bookingRQ.getPassengers()) {
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
            List<BSegmentDRQ> segments = new ArrayList<>();
            Leg legDetail = detailSV.getLegDetail(requestId, leg);
            for (LegSegmentDetail legSegment: legDetail.getSegments()) {
                segments.add(this.setDSegment(detailSV.getSegmentDetail(requestId, legSegment.getSegment()), null));
            }

            originDestinations.add(new OriginDestination(segments, legDetail.getDepartureTime(), legDetail.getDeparture(), legDetail.getArrival()));

            RevalidateRQ request = new RevalidateRQ();
            request.setAdult(adult);
            request.setChild(child);
            request.setInfant(infant);
            request.setOriginDestinations(originDestinations);

            JsonNode airItineraryPriceInfo = shoppingAction.revalidate(request);
            if (!this.checkPrice(airItineraryPriceInfo, detailSV.getPriceDetail(requestId, legDetail.getPrice()), adult, child, infant)) {
                return false;
            }
            if (!this.checkSeats(airItineraryPriceInfo, seats)) {
                return false;
            }
        }

        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set Segment for Supplier
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param segmentDetail
     * @param seats
     * @return BSegmentDRQ
     */
    public BSegmentDRQ setDSegment(Segment segmentDetail, String seats) {
        var segment = new BSegmentDRQ();

        segment.setDepDateTime(segmentDetail.getDepartureTime());
        segment.setArrDateTime(segmentDetail.getArrivalTime());
        segment.setDepCode(segmentDetail.getDeparture());
        segment.setArrCode(segmentDetail.getArrival());
        segment.setFlightNum(segmentDetail.getFlightNumber());
        segment.setAirline(segmentDetail.getAirline());
        segment.setOptAirline(segmentDetail.getOperatingAirline());
        segment.setResBookDesignCode(segmentDetail.getBookingCode());
        segment.setStatus("NN");
        segment.setType("A");
        if (!(seats == null)) {
            segment.setNumInParty(seats);
        }

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

            if (adult > 1 && price.getType().equals(PassengerCode.ADULT)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(adult)));
            } else if (child > 1 && price.getType().equals(PassengerCode.CHILD)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(child)));
            } else if (infant > 1 && price.getType().equals(PassengerCode.INFANT)) {
                amount = amount.add(pricePsg.multiply(BigDecimal.valueOf(infant)));
            } else {
                amount = amount.add(pricePsg);
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
