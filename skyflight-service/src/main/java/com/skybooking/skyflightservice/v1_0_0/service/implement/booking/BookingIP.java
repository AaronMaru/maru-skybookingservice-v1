package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.BookingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.booking.DBookingRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingOriginDestinationEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingSpecialServiceEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingTravelItineraryEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingOriginDestinationRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingSpecialServiceRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingTravelItineraryRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZonedDateTime;

@Service
public class BookingIP implements BookingSV {

    @Autowired
    private QuerySV querySV;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingAction bookingAction;

    @Autowired
    private BookingSpecialServiceRP bookingSpecialServiceRP;

    @Autowired
    private BookingTravelItineraryRP bookingTravelItineraryRP;

    @Autowired
    private BookingOriginDestinationRP bookingOriginDestinationRP;

    @Override
    public PNRCreateRS create(BCreateRQ request, BookingMetadataTA metadataTA) {

        if (!bookingUtility.isBookingCached(request.getRequestId())) {
            return new PNRCreateRS();
        }

        // action booking request
        DBookingRS booking = bookingAction.create(request);

        var requestTA = new BookingRequestTA();
        requestTA.setRequest(request);
        requestTA.setItineraryCode(booking.getBookingRef());

        metadataTA = bookingUtility.getBookingMetadata(requestTA, metadataTA);


        if (!booking.getStatus()) {
            return new PNRCreateRS();
        }

        // store booking records
        try {
            this.save(requestTA, metadataTA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PNRCreateRS("", booking.getBookingRef());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * save booking transaction to data store
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestTA
     * @param metadataTA
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public void save(BookingRequestTA requestTA, BookingMetadataTA metadataTA) throws Exception {

        var booking = this.insertBooking(requestTA, metadataTA);
        this.insertBookingOriginDestination(requestTA, booking.getId());

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking record to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestTA
     * @param metadataTA
     */
    public BookingEntity insertBooking(BookingRequestTA requestTA, BookingMetadataTA metadataTA) {

        // prepare booking information
        var booking = new BookingEntity();

        // set owner information
        booking.setStakeholderUserId(metadataTA.getStakeholderId());
        booking.setStakeholderCompanyId(metadataTA.getCompanyId());

        // set customer information
        booking.setCustName("");
        booking.setCustAddress("");
        booking.setCustCity("");
        booking.setCustZip("");

        // set contact information
        booking.setContFullname(requestTA.getRequest().getContact().getName());
        booking.setContPhonecode(requestTA.getRequest().getContact().getPhoneCode());
        booking.setContPhone(requestTA.getRequest().getContact().getPhoneNumber());
        booking.setContEmail(requestTA.getRequest().getContact().getEmail());
        booking.setContLocationCode("PNH");

        // set booking information
        booking.setSlug("");
        booking.setBookingCode("");
        booking.setBookingType(requestTA.getBookingType());
        booking.setRecieptFile("");
        booking.setRecieptPath("");
        booking.setItineraryRef(requestTA.getItineraryCode());
        booking.setItineraryFile("");
        booking.setItineraryPath("");

        // TODO: checking and verify time zone
        booking.setDepDate(Date.from(ZonedDateTime.parse(metadataTA.getDepartureDate()).toInstant()));

        booking.setTripType(bookingUtility.getTripType(metadataTA.getTripType()));
        booking.setPq(bookingUtility.getPassengerQuantityCodeNumber(requestTA.getRequest().getPassengers()));

        booking.setTotalAmount(BigDecimal.valueOf(metadataTA.getTotalAmount()));

        booking.setCurrencyConvert(metadataTA.getCurrencyLocaleCode());
        booking.setCurrencyCode(metadataTA.getCurrencyCode());
        booking.setCurrencyConRate(metadataTA.getExchangeRate());

        booking.setMarkupAmount(BigDecimal.valueOf(metadataTA.getMarkupAmount()));
        booking.setMarkupPercentage(Double.toString(metadataTA.getMarkupPercentage()));

        //TODO: update on payment process
        booking.setMarkupPayAmount(BigDecimal.ZERO);
        booking.setMarkupPayPercentage(BigDecimal.ZERO);

        booking.setDisPayMetAmount(BigDecimal.ZERO);
        booking.setDisPayMetPercentage(BigDecimal.ZERO);
        booking.setPayMetFeeAmount(BigDecimal.ZERO);
        booking.setPayMetFeePercentage(BigDecimal.ZERO);
        booking.setLogPaymentRes("");

        // default 10 %
        booking.setVatPercentage(10.0);

        booking.setIsCheck(1);
        booking.setRemark("");
        booking.setSeen("");

        // insert success status: 0
        booking.setStatus(0);

        // user's logged in id
        booking.setCreatedBy(metadataTA.getUserId().toString());
        booking.setUpdatedBy("");

        return bookingRP.save(booking);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking special service information
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void insertBookingSpecialService(Integer bookingId) {

        var bookingSSR = new BookingSpecialServiceEntity();

        bookingSSR.setBookingId(bookingId);
        bookingSSR.setRph(1);
        bookingSSR.setType("");
        bookingSSR.setSsrCode("");
        bookingSSR.setAirlineCode("");
        bookingSSR.setRemark("");

        // bookingSpecialServiceRP.save(bookingSSR);
        System.out.println(bookingSSR);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking travel itinerary information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     */
    public void insertBookingTravelItinerary(Integer bookingId) {

        var bookingTI = new BookingTravelItineraryEntity();

        bookingTI.setBookingId(bookingId);
        bookingTI.setPassType("");
        bookingTI.setPassQty(1);

        bookingTI.setPrivateFare("");
        bookingTI.setBaseFare(BigDecimal.ZERO);
        bookingTI.setTotalAmount(BigDecimal.ZERO);
        bookingTI.setTotalTax(BigDecimal.ZERO);

        bookingTI.setCurrencyCode("");
        bookingTI.setNonRefundableInd("");
        bookingTI.setBaggageInfo("");
        bookingTI.setFareCalculation("");
        bookingTI.setEndorsements("");
        bookingTI.setNoted("");
        bookingTI.setPieceStatus(1);

        bookingTI.setBagAirline("");
        bookingTI.setBagPiece(1);
        bookingTI.setBagWeight(1);
        bookingTI.setBagUnit("");

        bookingTI.setComAmount(BigDecimal.ZERO);
        bookingTI.setComMkamount(BigDecimal.ZERO);
        bookingTI.setComSource("");

        // bookingTravelItineraryRP.save(bookingTI);
        System.out.println(bookingTI);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking origin destination information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     */
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
                bookingSegmentOD.setSeatRemaining(segmentDetail.getSeatsRemain());
                bookingSegmentOD.setCabinCode(segmentDetail.getCabin());
                bookingSegmentOD.setCabinName(segmentDetail.getCabinName());
                bookingSegmentOD.setMeal(segmentDetail.getMeal());
                bookingSegmentOD.setStopQty(segmentDetail.getStopCount());
                bookingSegmentOD.setBookingCode(segmentDetail.getBookingCode());

                bookingSegmentOD.setAirlineCode(segmentDetail.getAirline());
                bookingSegmentOD.setAirlineName(detailSV.getAirlineDetail(requestId, segmentDetail.getAirline()).getName());
                bookingSegmentOD.setEquipType(segmentDetail.getAircraft());
                bookingSegmentOD.setAirCraftName(detailSV.getAircraftDetail(requestId, segmentDetail.getAircraft()).getName());
                bookingSegmentOD.setFlightNumber(Integer.valueOf(segmentDetail.getFlightNumber()));

                var departureLocation = detailSV.getLocationDetail(requestId, segmentDetail.getDeparture());
                bookingSegmentOD.setDepLocationCode(departureLocation.getCode());
                bookingSegmentOD.setDepCity(departureLocation.getCity());
                bookingSegmentOD.setDepAirportName(departureLocation.getAirport());
                bookingSegmentOD.setDepLatitude(Double.toString(departureLocation.getLatitude()));
                bookingSegmentOD.setDepLongitude(Double.toString(departureLocation.getLongitude()));

                var arrivalLocation = detailSV.getLocationDetail(requestId, segmentDetail.getArrival());
                bookingSegmentOD.setArrLocationCode(arrivalLocation.getCode());
                bookingSegmentOD.setArrCity(arrivalLocation.getCity());
                bookingSegmentOD.setArrAirportName(arrivalLocation.getAirport());
                bookingSegmentOD.setArrLatitude(Double.toString(arrivalLocation.getLatitude()));
                bookingSegmentOD.setArrLongitude(Double.toString(arrivalLocation.getLongitude()));

                bookingSegmentOD.setLayover(Math.toIntExact(segment.getLayOverDuration()));

                totalStop += segment.getStop();

                // store segment
                bookingOriginDestinationRP.save(bookingSegmentOD);
            }

            bookingOD.setStop(totalStop);

            bookingOriginDestinationRP.save(bookingOD);
        }
    }


    @Override
    public Boolean cancel(String pnr) {
        return null;
    }


}
