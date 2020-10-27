package com.skybooking.skyhotelservice.v1_0_0.service.similarhotel;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.similarhotel.SimilarHotelRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface SimilarHotelSV {
    StructureRS getSimilarHotel(SimilarHotelRQ similarHotelRQ);
}
