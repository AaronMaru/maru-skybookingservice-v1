package com.skybooking.skyhotelservice.v1_0_0.service.hotelCached;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyhotelservice.config.AppConfig;
import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.exception.RequestExpiredException;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.*;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.ImageCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterFacilityCached;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter.HotelFilterSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelsort.HotelSortSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ResourceRS;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import com.skybooking.skyhotelservice.v1_0_0.util.mapper.HotelMapper;
import jdk.jshell.spi.ExecutionControl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.skybooking.skyhotelservice.constant.HeaderConstants.X_LOCALIZATION;

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

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HeaderCM headerCM;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel wrapper cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @param resource
     * @return HotelWrapperCached
     */
    @Override
    public HotelWrapperCached getHotelWrapperCached(HotelWrapperRS<HotelRS> source, ResourceRSDS resource) {

        HotelWrapperCached hotelWrapperCached = hotelMapper.toHotelWrapperCached(source);

        // Add filter and sort object into Hotel Cached Object
        List<HotelCached> hotelCachedList = source.getHotelList()
            .stream()
            .filter(it -> !it.getRooms().isEmpty())
            .map(hotelRS -> {
                HotelCached hotelCached = hotelMapper.toHotelCached(hotelRS);
                hotelCached.setFilterInfo(new FilterInfoCached(hotelCached));
//                mapFilterFacilities(facilityCachedMap, hotelCached);
                return hotelCached;
            })
            .collect(Collectors.toList());

        hotelWrapperCached.setId(source.getRequestId());
        hotelWrapperCached.setHotelList(hotelCachedList);
        hotelWrapperCached.setResource(hotelMapper.toResourceCached(resource));

        String locale = headerCM.getLocalization();
        hotelWrapperCached.setLocale(locale);

        return hotelWrapperCached;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * store hotel wrapper cached async
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelWrapperCached
     */
    @Override
    public void saveCacheHotelWrapperAsync(HotelWrapperCached hotelWrapperCached) {
        hazelcastInstance
            .getMap(HOTEL_LIST_CACHED)
            .putAsync(hotelWrapperCached.getId(), hotelWrapperCached, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * store hotel wrapper cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelWrapperCached
     */
    @Override
    public void saveCacheHotelWrapper(HotelWrapperCached hotelWrapperCached) {
        hazelcastInstance
            .getMap(HOTEL_LIST_CACHED)
            .put(hotelWrapperCached.getId(), hotelWrapperCached, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel wrapper response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelWrapperCached
     * @return HotelWrapperRS
     */
    @Override
    public HotelWrapperRS<HotelRS> getHotelWrapper(HotelWrapperCached hotelWrapperCached) {

        final ResourceRS resourceRS = hotelMapper.toResourceRS(hotelWrapperCached.getResource());

        final List<HotelRS> hotels = hotelWrapperCached.getHotelList()
            .stream()
            .map(hotel -> {
                final HotelRS hotelRS = hotelMapper.toHotelRS(hotel);
                hotelRS.setResource(resourceRS);
                return hotelRS;
            })
            .collect(Collectors.toList());

        HotelWrapperRS<HotelRS> hotelRSHotelWrapperRS = new HotelWrapperRS<>();
        hotelRSHotelWrapperRS.setRequestId(hotelWrapperCached.getId());
        hotelRSHotelWrapperRS.setResource(resourceRS);
        hotelRSHotelWrapperRS.setHotelList(hotels);

        return hotelRSHotelWrapperRS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply filter and get hotel response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCached
     * @param availabilityRQ
     * @return List
     */
    @Override
    public List<HotelRS> getHotelList(List<HotelCached> hotelCached, AvailabilityRQ availabilityRQ) {
        return this.filterOccupancy(hotelCached, availabilityRQ);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply filter, sort and get hotel response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCached
     * @param sortBy
     * @param filterRQ
     * @return
     */
    @Override
    public List<HotelRS> getHotelList(List<HotelCached> hotelCached, String sortBy, FilterRQ filterRQ) {
        return this.sortAndFilter(hotelCached, sortBy, filterRQ);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply filter and retrieve hotel response list from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return List
     */
    @Override
    public List<HotelRS> retrieveHotelList(String id) {

        HotelWrapperRS<HotelRS> hotelRSHotelWrapperRS = retrieveHotelWrapper(id);
        if (hotelRSHotelWrapperRS == null)
            return Collections.emptyList();

        return hotelRSHotelWrapperRS.getHotelList();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply filter and retrieve hotel response from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param availabilityRQ
     * @return List
     */
    @Override
    public List<HotelRS> retrieveHotelList(String id, AvailabilityRQ availabilityRQ) {
        return this.filterOccupancy(retrieveHotelListCached(id), availabilityRQ);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply filter, sort and retrieve hotel response list from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param sortBy
     * @param filterRQ
     * @return List
     */
    @Override
    public List<HotelRS> retrieveHotelList(String id, String sortBy, FilterRQ filterRQ) {
        return this.sortAndFilter(retrieveHotelListCached(id), sortBy, filterRQ);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel response detail by hotel code from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param hotelCode
     * @return
     */
    @Override
    public Optional<HotelRS> retrieveHotel(String id, Integer hotelCode) {
        return retrieveHotelList(id)
            .stream()
            .filter(hotelRS -> hotelRS.getCode().equals(hotelCode))
            .findFirst();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel wrapper response from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return HotelWrapperRS
     */
    @Override
    public HotelWrapperRS<HotelRS> retrieveHotelWrapper(String id) {

        HotelWrapperRS<HotelRS> hotelRSHotelWrapperRS = new HotelWrapperRS<>();
        Optional<HotelWrapperCached> hotelWrapperCached = retrieveHotelWrapperCached(id, false);

        String locale = headerCM.getLocalization();

        if (hotelWrapperCached.isEmpty() ||
            !hotelWrapperCached.get()
                .getLocale().equalsIgnoreCase(locale))
            return hotelRSHotelWrapperRS;

        final ResourceRS resourceRS = modelMapper.map(hotelWrapperCached.get().getResource(), ResourceRS.class);
        final List<HotelRS> hotelList = hotelWrapperCached.get()
            .getHotelList()
            .stream()
            .map(hotelCached -> {
                HotelRS hotelRS = modelMapper.map(hotelCached, HotelRS.class);
                hotelRS.setResource(resourceRS);
                return hotelRS;
            })
            .collect(Collectors.toList());

        hotelRSHotelWrapperRS.setRequestId(hotelWrapperCached.get().getId());
        hotelRSHotelWrapperRS.setResource(resourceRS);
        hotelRSHotelWrapperRS.setHotelList(hotelList);

        return hotelRSHotelWrapperRS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel cached from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return List
     */
    @Override
    public List<HotelCached> retrieveHotelListCached(String id) {
        Optional<HotelWrapperCached> hotelWrapperCached = retrieveHotelWrapperCached(id, true);
        if (hotelWrapperCached.isEmpty())
            return null;
        return hotelWrapperCached.get().getHotelList();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * filter Occupancy
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCaches
     * @param availabilityRQ
     * @return
     */
    private List<HotelRS> filterOccupancy(List<HotelCached> hotelCaches, AvailabilityRQ availabilityRQ) {

        if (hotelCaches == null)
            return Collections.emptyList();

        if (hotelCaches.isEmpty())
            return Collections.emptyList();

        return hotelFilterSV.filter(
            hotelFilterSV.filterByOccupancy(
                hotelSortSV
                    .sortInfo(SortTypeConstant.getName(availabilityRQ.getSortBy()), hotelCaches),
                availabilityRQ),
            availabilityRQ.getFilter());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * sort and filter hotel response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCaches
     * @param sortBy
     * @param filterRQ
     * @return List
     */
    private List<HotelRS> sortAndFilter(List<HotelCached> hotelCaches, String sortBy, FilterRQ filterRQ) {

        if (hotelCaches == null)
            return new ArrayList<>();

        return hotelFilterSV.filter(
            hotelSortSV.sortInfo(SortTypeConstant.getName(sortBy), hotelCaches),
            filterRQ);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel wrapper from cache
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param isList
     * @return Optional
     */
    private Optional<HotelWrapperCached> retrieveHotelWrapperCached(String id, boolean isList) {
        HotelWrapperCached hotelWrapperCached = (HotelWrapperCached) hazelcastInstance
            .getMap(HOTEL_LIST_CACHED)
            .getOrDefault(id, null);

        String locale = headerCM.getLocalization();
        if (hotelWrapperCached == null || !hotelWrapperCached.getLocale().equalsIgnoreCase(locale))
            return Optional.empty();

        hotelWrapperCached.setHotelList(hotelWrapperCached.getHotelList()
            .stream()
            .peek(hotelCached -> hotelCached.setImages(hotelCached.getImages()
                .stream()
                .sorted(Comparator.comparingInt(ImageCached::getSortOrder))
                .limit(isList ? 4 : hotelCached.getImages().size())
                .collect(Collectors.toList())))
            .collect(Collectors.toList()));

        return Optional.of(hotelWrapperCached);
    }


    private void mapFilterFacilities(Map<String, FilterFacilityCached> facilityCachedMap, HotelCached hotelCached) {
    }

    private void mapFilterBoards(Map<String, List<Integer>> map, HotelCached hotelCached) {
    }

}
