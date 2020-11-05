package com.skybooking.backofficeservice.v1_0_0.service.hotel;

import com.skybooking.backofficeservice.v1_0_0.client.action.hotel.HotelAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.hotel.HotelDetailServiceRS;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.HotelDetailRS;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelIP implements HotelSV{
    private final ModelMapper modelMapper;

    @Autowired
    private HotelAction hotelAction;

    @Override
    public HotelDetailRS hotelDetail(String hotelBookingCode)
    {
        try{
            HotelDetailServiceRS hotelDetailServiceRS = hotelAction.getHotelDetail(hotelBookingCode);
            HotelDetailRS hotelDetailRS = modelMapper.map(hotelDetailServiceRS, HotelDetailRS.class);

            return hotelDetailRS;

        }catch (Exception exception){
            throw  exception;
        }
    }
}