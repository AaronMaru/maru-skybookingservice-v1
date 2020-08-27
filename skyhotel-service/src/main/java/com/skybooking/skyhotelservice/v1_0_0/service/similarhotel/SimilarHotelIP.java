package com.skybooking.skyhotelservice.v1_0_0.service.similarhotel;

import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public class SimilarHotelIP extends BaseServiceIP implements SimilarHotelSV {

    @Override
    public StructureRS retrieve(Integer hotelCode) {
        return responseBodyWithSuccessMessage(null);
    }

}
