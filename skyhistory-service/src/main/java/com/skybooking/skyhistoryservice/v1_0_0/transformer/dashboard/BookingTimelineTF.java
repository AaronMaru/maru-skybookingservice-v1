package com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingTimeLineTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingTimelineRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingTimelineTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param booking
     * @return RecentBookingRS
     */
    public static BookingTimelineRS getResponse(BookingTimeLineTO booking) {
        var response = new BookingTimelineRS();
        BeanUtils.copyProperties(booking, response);
        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookings
     * @return List
     */
    public static List<BookingTimelineRS> getResponseList(List<BookingTimeLineTO> bookings) {

        var responses = new ArrayList<BookingTimelineRS>();

        for (BookingTimeLineTO booking : bookings) {
            responses.add(getResponse(booking));
        }

        return responses;
    }
}
