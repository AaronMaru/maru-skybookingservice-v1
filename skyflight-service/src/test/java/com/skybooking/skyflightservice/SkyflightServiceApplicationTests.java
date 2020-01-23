package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AircraftNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyflightServiceApplicationTests {


    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private AirlineNQ airlineNQ;

    @Autowired
    private AircraftNQ aircraftNQ;

    @Test
    public void flightLocation() {
        var location = flightLocationNQ.getFlightLocationInformation("PNH");
        System.out.println(location);
    }

    @Test
    public void airlineInformation() {
        var airline = airlineNQ.getAirlineInformation("JC");
        System.out.println(airline);
    }

    @Test
    public void aircraftInformation() {
        var aircraft = aircraftNQ.getAircraftInformation("M81");
        System.out.println(aircraft);
    }

    @Test
    public void markUp() {
        var num = new BigDecimal("1234.56780", new MathContext(2, RoundingMode.DOWN));
        System.out.println(num);
    }

    @Test
    public void dateTimeCalculatorMinutes() {

        var start = "2020-01-07T21:30:00+08:00";
        var end = "2020-01-08T00:20:00+08:00";

        var result = Duration.between(ZonedDateTime.parse(start), ZonedDateTime.parse(end));

        System.out.println(ZonedDateTime.parse(start).plusDays(1).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        System.out.println(result.toMinutes());
    }

    @Test
    public void dateTimeZonedPrint() {

        var dateTime = "2020-01-02T17:30:00+07:00";

        var date = new Date();
        var result = ZonedDateTime.parse(dateTime).toLocalDateTime();
        System.out.println(result);
        System.out.println(date.toLocaleString());

    }

    @Test
    public void mapCollectionTest() {

        var data = List.of("A", "B", "C");

        data = data.stream().map(str -> str.toLowerCase()).collect(Collectors.toList());

        System.out.println(data);
    }

    @Test
    public void zonedDateTimeStringtoDateTime() {
        var dateTime = "2020-01-25T21:00:00+11:00";

        System.out.println(Date.from(ZonedDateTime.parse(dateTime).toInstant()));

    }

}
