package com.skybooking.skyhotelservice.v1_0_0.service.availability;

import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.*;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel.DestinationHotelCodeRSDS;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelCached.HotelCachedSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor.HotelConverterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter.HotelFilterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelsort.HotelSortSV;
import com.skybooking.skyhotelservice.v1_0_0.service.recentsearch.RecentSearchSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.GeolocationRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelDetailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location.DestinationRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ListHotelItemRS;
import com.skybooking.skyhotelservice.v1_0_0.util.pagination.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityIP extends BaseServiceIP implements AvailabilitySV {

    private final AvailabilityAction availabilityAction;
    private final HotelConverterSV hotelConverterSV;
    private final HotelCachedSV hotelCachedSV;
    private final RecentSearchSV recentSearchSV;
    private final HotelFilterSV hotelFilterSV;
    private final HotelSortSV hotelSortSV;
    private final ModelMapper modelMapper;

    public StructureRS availability(AvailabilityRQ availabilityRQ, Map<String, Object> paramRQ) {
        int page = 1;
        int size = 10;
        if (paramRQ.containsKey("page"))
            page = Integer.parseInt(paramRQ.get("page").toString());

        if (paramRQ.containsKey("size"))
            size = Integer.parseInt(paramRQ.get("size").toString());

        if (paramRQ.containsKey("requestId"))
            return availabilityWithCached(availabilityRQ,
                String.valueOf(paramRQ.get("requestId")), page, size);

        DestinationRQ destinationRQ = availabilityRQ.getDestination();
        GeolocationRQ geolocationRQ = availabilityRQ.getGeolocation();

        List<DestinationRQDS> destinationRQDS = null;
        GeolocationRQDS geolocationRQDS = null;

        if (destinationRQ != null) {
            destinationRQDS = List
                .of(new DestinationRQDS(
                    destinationRQ.getCode(),
                    destinationRQ.getGroup().name(),
                    destinationRQ.getHotelCode())
                );
            recentSearchSV.saveOrUpdate(availabilityRQ);
        } else if (geolocationRQ != null) {
            geolocationRQDS = new GeolocationRQDS(
                geolocationRQ.getLatitude(),
                geolocationRQ.getLongitude(),
                geolocationRQ.getRadius()
            );
        }

        DestinationHotelCodeRSDS hotelCodes = new DestinationHotelCodeRSDS();
        if (availabilityRQ.getDestination().getHotelCode() == null)
            hotelCodes = availabilityAction
                .fetchHotelCodes(new HotelCodeRQDS(destinationRQDS, geolocationRQDS));
        else {
            DestinationHotelCodeRSDS.Hotel hotel = new DestinationHotelCodeRSDS.Hotel();
            hotel.setCodes(List.of(Integer.valueOf(availabilityRQ.getDestination().getHotelCode())));
            hotelCodes.setData(hotel);
        }

        AvailabilityRQDS availabilityRQDS =
            new AvailabilityRQDS(availabilityRQ, hotelCodes.getData().getCodes());

        AvailabilityRSDS availabilityRSDS = availabilityAction.search(availabilityRQDS);
        List<HotelRS> hotelRS = hotelConverterSV.availabilities(availabilityRSDS);
        HotelWrapperRS<HotelRS> hotelWrapperRS =
            new HotelWrapperRS<>(UUID.randomUUID().toString(), hotelRS);

        hotelCachedSV.saveHotelList(hotelWrapperRS.getRequestId(),
            hotelWrapperRS, availabilityRSDS.getData().getResource());

        List<HotelRS> hotelRSList = hotelCachedSV.retrieveHotelList(
            hotelWrapperRS.getRequestId(),
            availabilityRQ.getSortBy(),
            availabilityRQ.getFilter()
        );

        return responseBodyWithSuccessMessage(
            new HotelWrapperRS<>(
                hotelWrapperRS.getRequestId(),
                hotelFilterSV.available(hotelCachedSV
                    .retrieveHotelList(hotelWrapperRS.getRequestId()), availabilityRQ.getFilter()),
                hotelSortSV.sortInfo(SortTypeConstant.getName(availabilityRQ.getSortBy())),
                mapCachedToListHotel(hotelRSList, page, size)),
            new PagingRS(page, size, hotelRSList.size()));
    }

    private StructureRS availabilityWithCached(AvailabilityRQ availabilityRQ, String requestId, int page, int size) {
        List<HotelRS> hotelRS = hotelCachedSV.retrieveHotelList(
            requestId,
            availabilityRQ.getSortBy(),
            availabilityRQ.getFilter()
        );

        return responseBodyWithSuccessMessage(
            new HotelWrapperRS<>(
                requestId,
                hotelFilterSV.available(hotelCachedSV.retrieveHotelList(requestId), availabilityRQ.getFilter()),
                hotelSortSV.sortInfo(SortTypeConstant.getName(availabilityRQ.getSortBy())),
                mapCachedToListHotel(hotelRS, page, size)),
            new PagingRS(page, size, hotelRS.size()));
    }

    private List<ListHotelItemRS> mapCachedToListHotel(List<HotelRS> list, int page, int size) {

        List<ListHotelItemRS> hotelItemRSList = list.stream()
            .map(hotelRS -> modelMapper.map(hotelRS, ListHotelItemRS.class))
            .collect(Collectors.toList());

        return PaginationUtil.of(hotelItemRSList, page, size);
    }

    @Override
    public List<HotelRS> getAvailabilityByDesCodes(List<String> destinationCodes) {
        List<DestinationRQDS> destinationRQDS = destinationCodes
            .stream()
            .map(DestinationRQDS::new)
            .collect(Collectors.toList());

        DestinationHotelCodeRSDS hotelCodes = availabilityAction
            .fetchHotelCodes(new HotelCodeRQDS(destinationRQDS));

        AvailabilityRSDS availabilityRSDS = availabilityAction
            .search(this.getSampleRequest(hotelCodes.getData().getCodes()));

        return hotelConverterSV.availabilities(availabilityRSDS);
    }

    @Override
    public AvailabilityRQDS getSampleRequest(List<Integer> hotelCodes) {
        AvailabilityRQDS availabilityRQDS = new AvailabilityRQDS();

        OccupancyRQDS occupancy = new OccupancyRQDS();
        occupancy.setRooms(1);
        occupancy.setAdults(1);
        occupancy.setChildren(0);

        StayRQDS stay = new StayRQDS();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        stay.setCheckIn(format.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        stay.setCheckOut(format.format(calendar.getTime()));

        availabilityRQDS.setHotels(new HotelRQDS(hotelCodes));
        availabilityRQDS.setOccupancies(List.of(occupancy));
        availabilityRQDS.setStay(stay);
        availabilityRQDS.setReviews(List.of(new ReviewRQDS()));

        return availabilityRQDS;
    }

    public StructureRS detail(HotelDetailRQ hotelDetailRQ) {
        Optional<HotelRS> data = Optional.empty();
        HotelWrapperRS<HotelRS> hotelWrapperRS = hotelCachedSV.retrieveHotelWrapper(hotelDetailRQ.getRequestId());

        if (hotelWrapperRS != null)
            data = hotelWrapperRS
                .getHotelList()
                .stream()
                .filter(item -> item.getCode().equals(hotelDetailRQ.getHotelCode()))
                .findFirst();
        if (data.isEmpty())
            throw new NotFoundException("Hotel Not Found", null);

        data.get().setResource(hotelWrapperRS.getResource());

        return responseBodyWithSuccessMessage(data.get());
    }
}
