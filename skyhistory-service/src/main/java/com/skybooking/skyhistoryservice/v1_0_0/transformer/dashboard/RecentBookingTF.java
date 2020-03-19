package com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.RecentBookingTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.RecentBookingRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RecentBookingTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param booking
     * @return RecentBookingRS
     */
    public static RecentBookingRS getResponse(RecentBookingTO booking) {

        DateTimeBean dateTimeBean = new DateTimeBean();

        var response = new RecentBookingRS();
        BeanUtils.copyProperties(booking, response);

        dateTimeBean.convertDateTime(booking.getCreatedAt());
        response.setCreatedAt(dateTimeBean.convertDateTime(booking.getCreatedAt()));

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
    public static List<RecentBookingRS> getResponseList(List<RecentBookingTO> bookings) {

        var responses = new ArrayList<RecentBookingRS>();

        for (RecentBookingTO booking : bookings) {
            responses.add(getResponse(booking));
        }

        return responses;
    }

}
