package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import com.skybooking.skyhotelservice.constant.PaxTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.PaxRQ;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AvailabilityRQDS {

    private StayRQDS stay = new StayRQDS();
    private List<OccupancyRQDS> occupancies = new ArrayList<>();
    private HotelRQDS hotels = new HotelRQDS();
    private List<ReviewRQDS> reviews = new ArrayList<>();

    public AvailabilityRQDS() {
    }

    public AvailabilityRQDS(AvailabilityRQ availabilityRQ, List<Integer> hotelCodes) {

        stay.setCheckIn(availabilityRQ.getCheckIn());
        stay.setCheckOut(availabilityRQ.getCheckOut());
        stay.setShiftDays("" + DatetimeUtil.night(availabilityRQ.getCheckIn(), availabilityRQ.getCheckOut()));

        OccupancyRQDS occupancy = new OccupancyRQDS();
        List<PaxRQDS> paxes = new ArrayList<>();
        var childrenNum = 0;

        if (availabilityRQ.getChildren().size() > 0) {

            childrenNum = 1;
            for (PaxRQ child : availabilityRQ
                .getChildren()
                .stream()
                .limit(1)
                .collect(Collectors.toList())) {
                PaxRQDS pax = new PaxRQDS();
                pax.setAge(child.getAge());
                pax.setType(PaxTypeConstant.CHILD);
                paxes.add(pax);
            }

        }

        occupancy.setPaxes(paxes);
        occupancy.setRooms(1);
        occupancy.setAdults((int) Math.ceil((double) availabilityRQ.getAdult() / (double) availabilityRQ.getRoom()));
        occupancy.setChildren(childrenNum);
        occupancies.add(occupancy);

        hotels.setHotel(hotelCodes);

        reviews.add(new ReviewRQDS());

    }
}
