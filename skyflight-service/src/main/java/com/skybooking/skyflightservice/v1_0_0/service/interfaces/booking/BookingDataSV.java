package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingTravelItineraryEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.HiddenStop;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.TravelItineraryTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface BookingDataSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * save booking transaction to data store
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestTA
     * @param metadataTA
     * @param pnrRS
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    BookingEntity save(BookingRequestTA requestTA, BookingMetadataTA metadataTA, JsonNode pnrRS, BookingCreateRQ request);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking record to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestTA
     * @param metadataTA
     * @param pnrRS
     * @return
     */
    BookingEntity insertBooking(BookingRequestTA requestTA, BookingMetadataTA metadataTA, JsonNode pnrRS);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking origin destination information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param requestTA
     * @param bookingId
     */
    void insertBookingOriginDestination(BookingRequestTA requestTA, Integer bookingId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking stop airport information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingOdId
     * @param hiddenStops
     * @param dateAdjustment
     */
    void insertBookingStopAirport(Integer bookingOdId, List<HiddenStop> hiddenStops, int dateAdjustment);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking special service information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param pnrRS
     */
    void insertBookingSpecialService(Integer bookingId, JsonNode pnrRS);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking travel itinerary information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param pnrRS
     * @param request
     * @return
     */
    List<TravelItineraryTA> insertBookingTravelItinerary(Integer bookingId, JsonNode pnrRS, BookingCreateRQ request);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking fare breakdown information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingTravelItineraryEntity
     * @param fareBreakdowns
     */
    void insertFareBreakdown(BookingTravelItineraryEntity bookingTravelItineraryEntity, JsonNode fareBreakdowns);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * insert booking air ticket records
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param travelItineraryTAS
     * @param request
     */
    void insertBookingAirTicket(Integer bookingId, List<TravelItineraryTA> travelItineraryTAS, BookingCreateRQ request);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update Ticket information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param ticket
     * @param bookingEntity
     */
    void updateTicket(JsonNode ticket, BookingEntity bookingEntity);

}
