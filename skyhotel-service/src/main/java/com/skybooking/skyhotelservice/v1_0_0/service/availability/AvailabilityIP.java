package com.skybooking.skyhotelservice.v1_0_0.service.availability;

import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.constant.WishlistConstant;
import com.skybooking.skyhotelservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.*;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel.DestinationHotelCodeRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelWrapperCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.wishlist.WishlistHotelEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist.WishlistHotelRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelCached.HotelCachedSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor.HotelConverterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter.HotelFilterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelsort.HotelSortSV;
import com.skybooking.skyhotelservice.v1_0_0.service.recentsearch.RecentSearchSV;
import com.skybooking.skyhotelservice.v1_0_0.service.savedhotel.SavedHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.service.viewedhotel.ViewedHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.GeolocationRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelDetailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location.DestinationRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ListHotelItemRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter.FilterRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import com.skybooking.skyhotelservice.v1_0_0.util.mapper.HotelMapper;
import com.skybooking.skyhotelservice.v1_0_0.util.pagination.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvailabilityIP extends BaseServiceIP implements AvailabilitySV {

    private final AvailabilityAction availabilityAction;
    private final HotelConverterSV hotelConverterSV;
    private final HotelCachedSV hotelCachedSV;
    private final RecentSearchSV recentSearchSV;
    private final HotelFilterSV hotelFilterSV;
    private final HotelSortSV hotelSortSV;
    private final HotelMapper hotelMapper;
    private final ViewedHotelSV viewedHotelSV;
    private final WishlistHotelRP wishlistRP;
    private final HttpServletRequest httpServletRequest;
    private final HeaderCM headerCM;
    private final JwtUtils jwt;

    private final int HOTELBED_MAX_AVAILABILITY = 2000;


    public StructureRS availability(AvailabilityRQ availabilityRQ, Map<String, Object> paramRQ) {
        String requestId = String.valueOf(paramRQ.getOrDefault("requestId", ""));
        int page = paramRQ.containsKey("page") ? Integer.parseInt(paramRQ.get("page").toString()) : 1;
        int size = paramRQ.containsKey("size") ? Integer.parseInt(paramRQ.get("size").toString()) : 10;

        if (availabilityRQ.getMeta() != null && availabilityRQ.getMeta().getPage() != null)
            page = availabilityRQ.getMeta().getPage();

        if (availabilityRQ.getMeta() != null && availabilityRQ.getMeta().getSize() != null)
            size = availabilityRQ.getMeta().getSize();

        if (availabilityRQ.getMeta() != null && availabilityRQ.getMeta().getRequestId() != null)
            requestId = availabilityRQ.getMeta().getRequestId();

        if (!requestId.isEmpty())
            return availabilityWithCached(availabilityRQ, requestId, page, size);

        DestinationRQ destinationRQ = availabilityRQ.getDestination();
        GeolocationRQ geolocationRQ = availabilityRQ.getGeolocation();

        List<DestinationRQDS> destinationRQDS = null;
        GeolocationRQDS geolocationRQDS = null;

        if (destinationRQ != null) {
            destinationRQDS = List
                .of(new DestinationRQDS(
                    destinationRQ.getCode(),
                    destinationRQ.getGroup(),
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

        // search by hotels code of destination.
        if (destinationRQ != null) {
            if (destinationRQ.getHotelCode() != null) {
                DestinationHotelCodeRSDS.Hotel hotel = new DestinationHotelCodeRSDS.Hotel();
                hotel.setCodes(List.of(Integer.valueOf(destinationRQ.getHotelCode())));
                hotelCodes.setData(hotel);
            }
        }

        // get hotels code with destination or geo location.
        if (hotelCodes.getData() == null)
            hotelCodes = availabilityAction
                .fetchHotelCodes(new HotelCodeRQDS(destinationRQDS, geolocationRQDS))
                .block();

        List<Integer> hotels = Objects.requireNonNull(hotelCodes)
            .getData()
            .getCodes()
            .stream()
            .sorted()
            .limit(HOTELBED_MAX_AVAILABILITY)
            .collect(Collectors.toList());

        log.info("Searching {} hotels.", hotels.size());
        log.info("Searching availability: {}", hotels);

        AvailabilityRQDS availabilityRQDS = new AvailabilityRQDS(availabilityRQ, hotels);

        // search availability by hotels code
        final long startSearch = System.currentTimeMillis();
        AvailabilityRSDS availabilityRSDS = availabilityAction.search(availabilityRQDS);
        final long stopSearch = System.currentTimeMillis();
        log.info("Response time: {} ms", stopSearch - startSearch);

        final long startConvert = System.currentTimeMillis();
        List<HotelRS> hotelRS = hotelConverterSV.availabilities(availabilityRSDS, availabilityRQ);
        final long stopConvert = System.currentTimeMillis();
        log.info("Convert time: {} ms", stopConvert - startConvert);
        HotelWrapperRS<HotelRS> hotelWrapperRS = new HotelWrapperRS<>(UUID.randomUUID().toString(), hotelRS);
        final HotelWrapperCached hotelWrapperCached = hotelCachedSV.getHotelWrapperCached(hotelWrapperRS, availabilityRSDS.getData().getResource());

        // store cached
        hotelCachedSV.saveCacheHotelWrapperAsync(hotelWrapperCached);

        // prepare response body
        final List<HotelRS> hotelList = hotelCachedSV.getHotelList(hotelWrapperCached.getHotelList(), availabilityRQ);
        final FilterRS filterInfo = hotelFilterSV.available(hotelList, availabilityRQ.getFilter());
        final SortRS sortInfo = hotelSortSV.sortInfo(SortTypeConstant.getName(availabilityRQ.getSortBy()));

        final HotelWrapperRS<ListHotelItemRS> response = new HotelWrapperRS<>(
            hotelWrapperRS.getRequestId(),
            filterInfo,
            sortInfo,
            mapCachedToListHotel(hotelList, page, size));

        final PagingRS paging = new PagingRS(page, size, hotelList.size());

        return responseBodyWithSuccessMessage(response, paging);
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

        List<Integer> wishlistHotelCodes = getSavedHotels();
        List<ListHotelItemRS> hotelItemRSList = list.stream()
            .peek(hotelRS -> {
                hotelRS.setImages(
                    hotelRS
                        .getImages()
                        .stream()
                        .limit(4)
                        .collect(Collectors.toList())
                );
                hotelRS.setIsFavorite(wishlistHotelCodes.contains(hotelRS.getCode()));
            })
            .map(hotelMapper::toListHotelItemRS)
            .collect(Collectors.toList());

        return PaginationUtil.of(hotelItemRSList, page, size);
    }

    private List<Integer> getSavedHotels() {
        List<Integer> wishlistHotelCodes = new ArrayList<>();
        if (httpServletRequest.getHeader("Authorization") != null) {
            var companyId = headerCM.getCompanyId();
            var skyUserId = jwt.userToken().getSkyuserId();
            List<WishlistHotelEntity> wishlistHotelEntities = wishlistRP
                .findBySkyuserIdAndCompanyId(skyUserId, companyId, WishlistConstant.SAVED_HOTEL);

            wishlistHotelCodes = wishlistHotelEntities
                .stream()
                .map(WishlistHotelEntity::getHotelCode)
                .collect(Collectors.toList());
        }
        return wishlistHotelCodes;
    }

    @Override
    public List<HotelRS> getAvailabilityByDesCodes(List<String> destinationCodes, GeolocationRQDS geolocation) {

        List<DestinationRQDS> destinationRQDS = destinationCodes
            .stream()
            .map(DestinationRQDS::new)
            .collect(Collectors.toList());
        DestinationHotelCodeRSDS hotelCodes = availabilityAction
            .fetchHotelCodes(new HotelCodeRQDS(destinationRQDS, geolocation)).block();

        AvailabilityRQDS availabilityRQDS = this.getSampleRequest(hotelCodes.getData().getCodes());

        AvailabilityRSDS availabilityRSDS = availabilityAction
            .search(availabilityRQDS);

        AvailabilityRQ availabilityRQ = new AvailabilityRQ();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        availabilityRQ.setCheckIn(format.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        availabilityRQ.setCheckOut(format.format(calendar.getTime()));

        return hotelConverterSV.availabilities(availabilityRSDS, availabilityRQ);
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
                .peek(item -> item.setRooms(
                    item.getRooms()
                        .stream()
                        // filter only match adult and children rooms
                        .filter(roomRS -> roomRS.getMinAdults() <= hotelDetailRQ.getAdult() &&
                            roomRS.getMaxAdults() >= hotelDetailRQ.getAdult() &&
                            roomRS.getMaxChildren() >= hotelDetailRQ.getChildren().size())
                        // filter only room have enough allotment
                        .filter(roomRS -> roomRS
                            .getRates()
                            .stream()
                            .anyMatch(rateRS -> rateRS.getAllotment() >= hotelDetailRQ.getRoom()))
                        // set rooms and adults to room's rate
//                        .peek(roomRS -> roomRS
//                            .setRates(roomRS
//                                .getRates()
//                                .stream()
//                                .peek(rateRS -> {
//                                    rateRS.setAdults(hotelDetailRQ.getAdult());
//                                    rateRS.setRooms(hotelDetailRQ.getRoom());
//                                }).collect(Collectors.toList())
//                            ))
                        .collect(Collectors.toList())
                ))
                .findFirst();
        if (data.isEmpty()) {
            throw new NotFoundException("Hotel Not Found", null);
        }
        viewedHotelSV.saveOrUpdate(data.get());

        data.get().setResource(hotelWrapperRS.getResource());

        return responseBodyWithSuccessMessage(data.get());
    }
}
