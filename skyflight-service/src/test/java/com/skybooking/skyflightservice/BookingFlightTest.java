package com.skybooking.skyflightservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingFlightTest {

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingAirTicketRP bookingAirTicketRP;

    @Autowired
    private BookingFareBreakdownRP bookingFareBreakdownRP;

    @Autowired
    private BookingOriginDestinationRP bookingOriginDestinationRP;

    @Autowired
    private BookingPassengerRP bookingPassengerRP;

    @Autowired
    private BookingPaymentTransactionRP bookingPaymentTransactionRP;

    @Autowired
    private BookingSpecialServiceRP bookingSpecialServiceRP;

    @Autowired
    private BookingStopAirportRP bookingStopAirportRP;

    @Autowired
    private BookingTravelItineraryRP bookingTravelItineraryRP;

    private ObjectMapper mapper;

    @Before
    public void init() {

        mapper = new ObjectMapper();
    }

    @Test
    public void getBooking() throws JsonProcessingException {

        var booking = bookingRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }

    @Test
    public void getBookingAirTicket() throws JsonProcessingException {

        var booking = bookingAirTicketRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }


    @Test
    public void getBookingFareBreakdown() throws JsonProcessingException {

        var booking = bookingFareBreakdownRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }


    @Test
    public void getBookingOriginDestination() throws JsonProcessingException {

        var booking = bookingOriginDestinationRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }

    @Test
    public void getBookingPassengerRP() throws JsonProcessingException {

        var booking = bookingPassengerRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }


    @Test
    public void getBookingPaymentTransaction() throws JsonProcessingException {

        var booking = bookingPaymentTransactionRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }

    @Test
    public void getBookingSpecialService() throws JsonProcessingException {

        var booking = bookingSpecialServiceRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

    }

    @Test
    public void getBookingStopAirport() throws JsonProcessingException {
        var booking = bookingStopAirportRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));
    }

    @Test
    public void getBookingTravelItinerary() throws JsonProcessingException {
        var booking = bookingTravelItineraryRP.findAll().stream().findFirst().orElse(null);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));
    }


}
