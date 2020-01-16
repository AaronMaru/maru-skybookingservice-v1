package com.skybooking.skyflightservice.v1_0_0.transformer.shopping;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.ScheduleComponent;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ScheduleEntity;
import org.joda.time.format.DateTimeFormat;

public class ScheduleTF {

    public static ScheduleEntity getEntity(String shoppingId, int directionNumber, String departureDate, ScheduleComponent data, int departureAdjustment) {

        var departureDateTime = departureDate.concat(" ").concat(data.getDeparture().getTime());
        var arrivalDateTime = departureDate.concat(" ").concat(data.getArrival().getTime());

        var fromPattern = "yyyy-MM-dd HH:mm:ssZ";
        var toPattern = "C-MM-dd HH:mm:ssZ";
        var keyPattern = "CMMddHHmmss";
        var fromDatePattern = DateTimeFormat.forPattern(fromPattern);
        var toDatePattern = DateTimeFormat.forPattern(toPattern);
        var keyDatePattern = DateTimeFormat.forPattern(keyPattern);

        var departure = fromDatePattern.parseDateTime(departureDateTime);
        var arrival = fromDatePattern.parseDateTime(arrivalDateTime);

        var id = directionNumber + "-" + data.getDeparture().getAirport() + "-" + keyDatePattern.print(departure) + "-" + data.getStopCount() + "-" + data.getArrival().getAirport() + "-" + keyDatePattern.print(arrival);

        var schedule = new ScheduleEntity();
        schedule.setId(id);
        schedule.setExternalId(shoppingId.concat(":").concat(id));
        schedule.setDeparture(toDatePattern.print(departure));
        schedule.setArrival(toDatePattern.print(arrival));
        schedule.setDeparturePlaceId(data.getDeparture().getAirport());
        schedule.setArrivalPlaceId(data.getArrival().getAirport());

        return schedule;
    }
}
