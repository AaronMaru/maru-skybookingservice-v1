package com.skybooking.skyhotelservice.v1_0_0.service.similarhotel;

import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.action.similarhotel.SimilarHotelAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.AvailabilityRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.similarhotel.SimilarHotelDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.similarhotel.SimilarRSDS;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor.HotelConverterSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.PaxRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.similarhotel.SimilarHotelRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ListHotelItemRS;
import com.skybooking.skyhotelservice.v1_0_0.util.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimilarHotelIP extends BaseServiceIP implements SimilarHotelSV {

    private final SimilarHotelAction similarHotelAction;
    private final ModelMapper modelMapper;
    private final AvailabilityAction availabilityAction;
    private final AvailabilitySV availabilitySV;
    private final HotelConverterSV hotelConverterSV;
    private final HotelMapper hotelMapper;

    @Override
    public StructureRS getSimilarHotel(SimilarHotelRQ similarHotelRQ) {

        SimilarHotelDSRQ similarHotelDSRQ = modelMapper.map(similarHotelRQ, SimilarHotelDSRQ.class);

        SimilarRSDS structureRSDS = similarHotelAction.getSimilarHotel(similarHotelDSRQ);

        AvailabilityRQDS availabilityRQDS = availabilitySV.getSampleRequest(structureRSDS.getData());

        AvailabilityRSDS availabilityRSDS = availabilityAction.search(availabilityRQDS);

        AvailabilityRQ availabilityRQ = new AvailabilityRQ();
        availabilityRQ.setCheckIn(availabilityRQDS.getStay().getCheckIn());
        availabilityRQ.setCheckOut(availabilityRQDS.getStay().getCheckOut());
        availabilityRQ.setRoom(availabilityRQDS.getOccupancies().get(0).getRooms());
        availabilityRQ.setAdult(availabilityRQDS.getOccupancies().get(0).getAdults());
        availabilityRQ.setChildren(List.of(new PaxRQ()));

        List<HotelRS> hotelRS = hotelConverterSV.availabilities(availabilityRSDS, availabilityRQ);

        HotelWrapperRS<HotelRS> hotelWrapperRS = new HotelWrapperRS<>(UUID.randomUUID().toString(), hotelRS);
        return mapCachedToListHotel(hotelWrapperRS.getHotelList());

    }

    private StructureRS mapCachedToListHotel(List<HotelRS> hotelList) {
        List<ListHotelItemRS> hotelItemRSList = hotelList.stream()
                .peek(hotelRS -> {
                    hotelRS.setImages(
                            hotelRS
                                    .getImages()
                                    .stream()
                                    .limit(4)
                                    .collect(Collectors.toList())
                    );
                })
                .map(hotelMapper::toListHotelItemRS)
                .limit(15)
                .collect(Collectors.toList());
        return responseBodyWithSuccessMessage(hotelItemRSList);
    }

}
