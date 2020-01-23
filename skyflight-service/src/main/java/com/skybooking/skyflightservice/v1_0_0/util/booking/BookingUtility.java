package com.skybooking.skyflightservice.v1_0_0.util.booking;

import com.skybooking.skyflightservice.config.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupTO;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class BookingUtility {

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private MarkupNQ markupNQ;

    @Autowired
    private QuerySV querySV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get trip type for insert into database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param type
     * @return String
     */
    public String getTripType(String type) {

        if (type.equalsIgnoreCase("one")) return "OneWay";
        if (type.equalsIgnoreCase("round")) return "Round";
        if (type.equalsIgnoreCase("multiple")) return "Other";

        return "Other";
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get passenger quantity code number
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerRQList
     * @return Number
     */

    public Integer getPassengerQuantityCodeNumber(List<BPassengerRQ> passengerRQList) {

        var types = new HashMap<String, String>();

        var codeNumber = 0;

        for (BPassengerRQ bPassengerRQ : passengerRQList) {

            var type = PassengerUtil.type(bPassengerRQ.getBirthDate());
            types.put(type, type);
        }

        if (types.containsKey(PassengerCode.ADULT)) {
            codeNumber = 1;
        }

        if (types.containsKey(PassengerCode.ADULT) && types.containsKey(PassengerCode.CHILD) || types.containsKey(PassengerCode.ADULT) && types.containsKey(PassengerCode.INFANT)) {
            codeNumber = 2;
        }

        if (types.containsKey(PassengerCode.ADULT) && types.containsKey(PassengerCode.CHILD) && types.containsKey(PassengerCode.INFANT)) {
            codeNumber = 3;
        }

        return codeNumber;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get markup percentage
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param userId
     * @param userType
     * @param cabinType
     * @return double
     */
    public double getMarkupPercentage(Integer userId, String userType, String cabinType) {

        // get markup percentage
        var markUpTO = new MarkupTO();

        if (userType.equalsIgnoreCase("skyuser")) {
            markUpTO = markupNQ.getMarkupPriceSkyUser(userId, cabinType.toUpperCase());
        } else if (userType.equalsIgnoreCase("skyowner")) {
            markUpTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, cabinType.toUpperCase());
        }

        return markUpTO.getMarkup().doubleValue();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking total amount
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @param legIds
     * @return double
     */
    public double getBookingTotalAmount(String requestId, String[] legIds) {

        var totalAmount = 0.0;

        for (String legId : legIds) {
            var leg = detailSV.getLegDetail(requestId, legId);
            var price = detailSV.getPriceDetail(requestId, leg.getPrice());

            totalAmount += price.getTotal();
        }

        return NumberFormatter.amount(totalAmount);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking total markup amount
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param totalAmount
     * @param percent
     * @return double
     */
    public double getBookingTotalMarkupAmount(double totalAmount, double percent) {
        return NumberFormatter.amount(totalAmount * percent);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking departure date time
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @param legIds
     * @return String
     */
    public String getBookingDepartureDateTime(String requestId, String[] legIds) {

        if (legIds.length > 0) {
            var leg = detailSV.getLegDetail(requestId, legIds[0]);
            if (leg != null) {
                return leg.getDepartureTime();
            }
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking arrival date time
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @param legIds
     * @return String
     */
    public String getBookingArrivalDateTime(String requestId, String[] legIds) {

        if (legIds.length > 0) {
            var leg = detailSV.getLegDetail(requestId, legIds[legIds.length - 1]);
            if (leg != null) {
                return leg.getArrivalTime();
            }
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking trip type
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @return String
     */
    public String getBookingTripType(String requestId) {

        var query = querySV.flightShoppingById(requestId);

        if (query != null) {
            return query.getQuery().getTripType();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking class type
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @return String
     */
    public String getBookingClassType(String requestId) {

        var query = querySV.flightShoppingById(requestId);

        if (query != null) {
            return query.getQuery().getClassType();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * checking the shopping contain in cached.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @return boolean
     */
    public boolean isBookingCached(String requestId) {
        var query = querySV.flightShoppingById(requestId);
        if (query == null) return false;

        return true;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking metadata information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param metadataTA
     * @param request
     * @return BookingMetadataTA
     */
    public BookingMetadataTA getBookingMetadata(BookingRequestTA request, BookingMetadataTA metadataTA) {

        var cached = this.isBookingCached(request.getRequest().getRequestId());

        if (cached) {

            // set trip type
            metadataTA.setTripType(this.getBookingTripType(request.getRequest().getRequestId()));
            metadataTA.setClassType(this.getBookingClassType(request.getRequest().getRequestId()));

            // set departure and arrival
            metadataTA.setDepartureDate(this.getBookingDepartureDateTime(request.getRequest().getRequestId(), request.getRequest().getLegIds()));
            metadataTA.setArrivalDate(this.getBookingArrivalDateTime(request.getRequest().getRequestId(), request.getRequest().getLegIds()));

            // get markup percentage
            if (metadataTA.getUserType().equalsIgnoreCase("skyowner")) {
                metadataTA.setMarkupPercentage(this.getMarkupPercentage(metadataTA.getCompanyId(), "skyowner", metadataTA.getClassType()));
            } else {
                metadataTA.setMarkupPercentage(this.getMarkupPercentage(metadataTA.getStakeholderId(), "skyuser", metadataTA.getClassType()));
            }

            // set total amount detail
            metadataTA.setTotalAmount(this.getBookingTotalAmount(request.getRequest().getRequestId(), request.getRequest().getLegIds()));
            metadataTA.setMarkupAmount(this.getBookingTotalMarkupAmount(metadataTA.getTotalAmount(), metadataTA.getMarkupPercentage()));

        }

        metadataTA.setCached(cached);

        return metadataTA;

    }

}
