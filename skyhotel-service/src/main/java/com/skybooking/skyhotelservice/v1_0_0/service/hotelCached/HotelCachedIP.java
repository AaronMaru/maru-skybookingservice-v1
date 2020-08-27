package com.skybooking.skyhotelservice.v1_0_0.service.hotelCached;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyhotelservice.config.AppConfig;
import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.*;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.ImageCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility.HotelBusinessCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterFacilityCached;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter.HotelFilterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelsort.HotelSortSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ResourceRS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HotelCachedIP implements HotelCachedSV {

    private static final String HOTEL_LIST_CACHED = "HOTEL_LIST_CACHED";

    @Autowired
    private AppConfig appConfig;

    @Qualifier("hazelcastInstance")
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelFilterSV hotelFilterSV;

    @Autowired
    private HotelSortSV hotelSortSV;

    @Override
    public void saveHotelList(String id, HotelWrapperRS<HotelRS> source, ResourceRSDS resource) {
        HotelWrapperCached hotelWrapperCached = modelMapper.map(source, HotelWrapperCached.class);

//        Map<String, FilterFacilityCached> facilityCachedMap = new HashMap<>();
//        Map<String, List<Integer>> boards = new HashMap<>();

        // Add filter and sort object into Hotel Cached Object
        List<HotelCached> hotelCachedList = source.getHotelList()
            .stream()
            .map(hotelRS -> {
                HotelCached hotelCached = modelMapper.map(hotelRS, HotelCached.class);
                hotelCached.setFilterInfo(new FilterInfoCached(hotelCached));
//                mapFilterFacilities(facilityCachedMap, hotelCached);
                return hotelCached;
            })
            .collect(Collectors.toList());

//        FilterIndexCached indexCached = new FilterIndexCached();
//        indexCached.setFacilities(facilityCachedMap);

//        hotelWrapperCached.setFilterIndex(indexCached);
        hotelWrapperCached.setHotelList(hotelCachedList);
        hotelWrapperCached.setResource(modelMapper.map(resource, ResourceCached.class));

        hazelcastInstance.getMap(HOTEL_LIST_CACHED).put(id, hotelWrapperCached, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }

    private void mapFilterFacilities(Map<String, FilterFacilityCached> facilityCachedMap, HotelCached hotelCached) {
//        for (HotelBusinessCached business : hotelCached.getFacility().getBusinesses()) { }
    }

    private void mapFilterBoards(Map<String, List<Integer>> map, HotelCached hotelCached) {

    }

    @Override
    public HotelWrapperRS<HotelRS> retrieveHotelWrapper(String id) {
        HotelWrapperRS<HotelRS> hotelRSHotelWrapperRS = new HotelWrapperRS<>();

        Optional<HotelWrapperCached> hotelWrapperCached = retrieveHotelWrapperCached(id, false);

        if (hotelWrapperCached.isEmpty()) return hotelRSHotelWrapperRS;

        hotelRSHotelWrapperRS.setRequestId(hotelWrapperCached.get().getId());
        hotelRSHotelWrapperRS.setResource(
            modelMapper.map(hotelWrapperCached.get().getResource(), ResourceRS.class));
        hotelRSHotelWrapperRS.setHotelList(hotelWrapperCached.get()
            .getHotelList()
            .stream()
            .map(hotelCached -> modelMapper.map(hotelCached, HotelRS.class))
            .collect(Collectors.toList()));

        return hotelRSHotelWrapperRS;
    }

    @Override
    public List<HotelRS> retrieveHotelList(String id) {
        HotelWrapperRS<HotelRS> hotelRSHotelWrapperRS = retrieveHotelWrapper(id);
        if (hotelRSHotelWrapperRS == null) return new ArrayList<>();
        return hotelRSHotelWrapperRS.getHotelList();
    }

    @Override
    public List<HotelRS> retrieveHotelList(String id, String sortBy, FilterRQ filterRQ) {
        return this.sortAndFilter(retrieveHotelListCached(id), sortBy, filterRQ);
    }

    private List<HotelRS> sortAndFilter(List<HotelCached> hotelCaches, String sortBy, FilterRQ filterRQ) {

        if (hotelCaches == null)
            return new ArrayList<>();

        return hotelFilterSV.filter(
            hotelSortSV.sortInfo(SortTypeConstant.getName(sortBy), hotelCaches),
            filterRQ);

    }

    @Override
    public List<HotelCached> retrieveHotelListCached(String id) {
        Optional<HotelWrapperCached> hotelWrapperCached = retrieveHotelWrapperCached(id, true);
        if (hotelWrapperCached.isEmpty()) return null;
        return hotelWrapperCached.get().getHotelList();
    }

    private Optional<HotelWrapperCached> retrieveHotelWrapperCached(String id, boolean isList) {
        HotelWrapperCached hotelWrapperCached = (HotelWrapperCached) hazelcastInstance
            .getMap(HOTEL_LIST_CACHED)
            .getOrDefault(id, null);

        if (hotelWrapperCached != null)
            hotelWrapperCached.setHotelList(hotelWrapperCached.getHotelList()
                .stream()
                .peek(hotelCached -> hotelCached.setImages(hotelCached.getImages()
                    .stream()
                    .sorted(Comparator.comparingInt(ImageCached::getSortOrder))
                    .limit(isList ? 4 : hotelCached.getImages().size())
                    .collect(Collectors.toList())))
                .collect(Collectors.toList()));

        return Optional.ofNullable(hotelWrapperCached);
    }

}
