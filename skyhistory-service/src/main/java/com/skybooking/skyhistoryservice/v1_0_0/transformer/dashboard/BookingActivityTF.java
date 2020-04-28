package com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingActivityFlightSegmentTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingActivityTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingActivityFlightSegmentRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingActivityRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class BookingActivityTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param booking
     * @return RecentBookingRS
     */
    public static BookingActivityRS getResponse(BookingActivityTO booking) {
        var response = new BookingActivityRS();

        BeanUtils.copyProperties(booking, response);

        DateTimeBean dateTimeBean = new DateTimeBean();
        response.setCreatedAt(dateTimeBean.convertDateTime(booking.getCreatedAt()));

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param segment
     * @return
     */
    public static BookingActivityFlightSegmentRS getResponse(BookingActivityFlightSegmentTO segment) {
        var response = new BookingActivityFlightSegmentRS();
        BeanUtils.copyProperties(segment, response);
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
    public static List<BookingActivityRS> getResponseList(List<BookingActivityTO> bookings) {

        var responses = new ArrayList<BookingActivityRS>();

        for (BookingActivityTO booking : bookings) {
            responses.add(getResponse(booking));
        }

        return responses;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookings
     * @return List
     */
    public static List<BookingActivityRS> getResponsePage(Page<BookingActivityTO> bookings) {

        var responses = new ArrayList<BookingActivityRS>();

        for (BookingActivityTO booking : bookings) {
            responses.add(getResponse(booking));
        }

        return responses;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param segments
     * @return List
     */
    public static List<BookingActivityFlightSegmentRS> getResponseSegmentList(List<BookingActivityFlightSegmentTO> segments) {

        var responses = new ArrayList<BookingActivityFlightSegmentRS>();

        for (BookingActivityFlightSegmentTO segment : segments) {
            responses.add(getResponse(segment));
        }

        return responses;
    }


}
