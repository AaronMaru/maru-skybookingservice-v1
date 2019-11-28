package com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingTopSellerTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingTopSellerRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingTopSellerTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param seller
     * @return BookingTopSellerRS
     */
    public static BookingTopSellerRS getResponse(BookingTopSellerTO seller) {
        var response = new BookingTopSellerRS();
        BeanUtils.copyProperties(seller, response);
        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param sellers
     * @return List
     */
    public static List<BookingTopSellerRS> getResponseList(List<BookingTopSellerTO> sellers) {

        var responses = new ArrayList<BookingTopSellerRS>();

        for (BookingTopSellerTO seller : sellers) {
            responses.add(getResponse(seller));
        }

        return responses;
    }
}
