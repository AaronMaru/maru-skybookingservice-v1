package com.skybooking.skyflightservice.v1_0_0.util.booking;

import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupTO;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.classes.booking.PriceInfo;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        var tripType = switch (type.toLowerCase()) {
            case "one" -> "OneWay";
            case "round" -> "Return";
            case "multiple" -> "Other";
            default -> throw new IllegalArgumentException("Invalid trip type: " + type);
        };

        return tripType;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get passenger type for insert into database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param type
     * @return String
     */
    public String getPassengerType(String type) {

        var passengerType = switch (type.toUpperCase()) {
            case PassengerCode.ADULT -> "Adult";
            case PassengerCode.CHILD -> "Child";
            case PassengerCode.INFANT -> "Infant";
            default -> throw new IllegalArgumentException("Invalid passenger type: " + type);
        };


        return passengerType;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get passenger quantity code number
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerRQList
     * @return Number
     */

    public Integer getPassengerQuantityCodeNumber(List<BookingPassengerRQ> passengerRQList) {

        var types = new HashMap<String, String>();

        var codeNumber = 0;

        for (BookingPassengerRQ bookingPassengerRQ : passengerRQList) {

            var type = PassengerUtil.type(bookingPassengerRQ.getBirthDate());
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
        } else {
            markUpTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, cabinType.toUpperCase());
        }

        if (markUpTO == null || markUpTO.getMarkup() == null) {
            markUpTO = markupNQ.getMarkupPriceAnonymousUser(cabinType.toUpperCase(), "skyuser");
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

            totalAmount += price.getTotal().doubleValue();
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
     * get passenger booking total amount by passenger type
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestId
     * @param legId
     * @param passengerType
     * @return double
     */
    public double getBookingPassengerTotalAmount(String requestId, String legId, String passengerType) {

        var legDetail = detailSV.getLegDetail(requestId, legId);
        var priceDetail = detailSV.getPriceDetail(requestId, legDetail.getPrice());

        return priceDetail
            .getDetails()
            .stream()
            .filter(price -> price.getType().equalsIgnoreCase(passengerType))
            .mapToDouble(price -> NumberFormatter.amount(price.getBaseFare().add(price.getTax()).doubleValue()))
            .findFirst()
            .getAsDouble();
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
            if (metadataTA.getUser().getUserType().equalsIgnoreCase("skyowner")) {
                metadataTA.setMarkupPercentage(this.getMarkupPercentage(metadataTA.getUser().getCompanyId(), "skyowner", metadataTA.getClassType()));
            } else {
                metadataTA.setMarkupPercentage(this.getMarkupPercentage(metadataTA.getUser().getStakeholderId(), "skyuser", metadataTA.getClassType()));
            }

            // set total amount detail
            metadataTA.setTotalAmount(this.getBookingTotalAmount(request.getRequest().getRequestId(), request.getRequest().getLegIds()));
            metadataTA.setMarkupAmount(this.getBookingTotalMarkupAmount(metadataTA.getTotalAmount(), metadataTA.getMarkupPercentage()));

        }

        metadataTA.setCached(cached);

        return metadataTA;

    }


    public PriceInfo getPriceInfo(BookingEntity booking, PaymentMandatoryRQ paymentMandatoryRQ) {

        PriceInfo priceInfo = new PriceInfo();
        BigDecimal grossAmount = NumberFormatter.trimAmount(booking.getTotalAmount().add(booking.getMarkupAmount()).add(booking.getMarkupPayAmount()));
        BigDecimal discountPaymentMethodPercentage = paymentMandatoryRQ.getPercentage();
        BigDecimal discountPaymentMethodAmount = NumberFormatter.trimAmount(grossAmount.multiply(discountPaymentMethodPercentage.divide(BigDecimal.valueOf(100))));
        BigDecimal finalAmount = grossAmount.subtract(discountPaymentMethodAmount);
        BigDecimal paymentMethodFeePercentage = paymentMandatoryRQ.getPercentageBase();
        BigDecimal paymentMethodFeeAmount = NumberFormatter.trimAmount(finalAmount.multiply(paymentMethodFeePercentage));

        priceInfo.setGrossAmount(grossAmount);
        priceInfo.setMarkupPaymentMethodPercentage(booking.getMarkupPayPercentage());
        priceInfo.setMarkupPaymentMethodAmount(booking.getMarkupPayAmount());
        priceInfo.setDiscountPaymentMethodPercentage(discountPaymentMethodPercentage);
        priceInfo.setDiscountPaymentMethodAmount(discountPaymentMethodAmount);

        /**
         * update payment method fee for back office
         */
        priceInfo.setPaymentMethodFeePercentage(paymentMethodFeePercentage);
        priceInfo.setPaymentMethodFeeAmount(paymentMethodFeeAmount);

        /**
         * final amount: discount already
         */
        priceInfo.setFinalAmount(finalAmount);

        return priceInfo;
    }

}
