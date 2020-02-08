package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Airline;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegAirline;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AircraftNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
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
import java.util.Arrays;
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
        System.out.println(DateUtility.convertZonedDateTimeToDateTime(dateTime));

        var airline1 = new LegAirline();
        var airline2 = new LegAirline();
        var airline3 = new LegAirline();
        airline1.setAirline("A");
        airline2.setAirline("B");
        airline3.setAirline("C");

        var data = Arrays.asList(airline1, airline2, airline3);
        var count = data.stream().map(airline -> airline.getAirline()).distinct().count();

        var firstSegment = data.stream().findFirst();
        var lastSegment = data.stream().reduce((previous, next) -> next).get();

        var loop = data.listIterator();

        while (loop.hasNext()) {
            var current = loop.next();
            var previous = loop.previous();
            current.setFlightNumber("Changed");
        }


        System.out.println(data);

//        System.out.println(firstSegment);
//        System.out.println(lastSegment);

//        System.out.println(Date.from(ZonedDateTime.parse(dateTime).toInstant()));


    }

    @Test
    public void testEC() {
        MutableSortedMap<String, Airline> airlines = TreeSortedMap.newMap();

        var airline1 = new Airline();
        airline1.setCode("Z");

        var airline2 = new Airline();
        airline2.setCode("T");

        var airline3 = new Airline();
        airline3.setCode("B");

        var list = FastList.newListWith(1, 4, 3, 5);

        list
            .toSortedList(Comparators.byIntFunction(integer -> integer))
            .stream()
            .forEach(integer -> System.out.println(integer));

//        airlines.put(airline1.getCode(), airline1);
//        airlines.put(airline2.getCode(), airline2);
//        airlines.put(airline3.getCode(), airline3);



//        airlines.values().forEach(airline -> System.out.println(airline));
//        airlines.select(airline -> airline.getCode().equalsIgnoreCase("b")).getFirstOptional().ifPresent(airline -> System.out.println(airline));
//        System.out.println(airlines.get("Z"));
    }


}
