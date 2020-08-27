package com.skybooking.skyhotelservice.v1_0_0.service.hotelsort;

import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortListRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortValueRS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HotelSortIP implements HotelSortSV {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SortRS sortInfo(SortTypeConstant type)
    {
        int recommendStatus = 0;
        int priceStatus = 0;
        int priceLowHighStatus = 0;
        int priceHighLowStatus = 0;
        int starStatus = 0;
        int starLowHighStatus = 0;
        int starHighLowStatus = 0;
        int guestRatingStatus = 0;
        int distanceStatus = 0;

        switch (type) {
            case SORT_RECOMMENDED:
                recommendStatus = 1; break;
            case SORT_PRICE_LOW_HIGH:
                priceLowHighStatus = 1;
                priceStatus = 1;
                break;
            case SORT_PRICE_HIGH_LOW:
                priceHighLowStatus = 1;
                priceStatus = 1;
                break;
            case SORT_STAR_LOW_HIGH:
                starLowHighStatus = 1;
                starStatus = 1;
                break;
            case SORT_STAR_HIGH_LOW:
                starHighLowStatus = 1;
                starStatus = 1;
                break;
            case SORT_GUEST_RATING:
                guestRatingStatus = 1; break;
            case SORT_DISTANCE:
                distanceStatus = 1; break;
            default:
                recommendStatus = 1;
        }

        SortValueRS priceLowHigh = setSortValue("Low to High", priceLowHighStatus, SortTypeConstant.SORT_PRICE_LOW_HIGH);
        SortValueRS priceHighLow = setSortValue("High to Low", priceHighLowStatus, SortTypeConstant.SORT_PRICE_HIGH_LOW);
        List<SortValueRS> priceList = new ArrayList<>();
        priceList.add(priceLowHigh);
        priceList.add(priceHighLow);

        SortValueRS starLowHigh = setSortValue("Low to High", starLowHighStatus, SortTypeConstant.SORT_STAR_LOW_HIGH);
        SortValueRS starHighLow = setSortValue("High to Low", starHighLowStatus, SortTypeConstant.SORT_STAR_HIGH_LOW);
        List<SortValueRS> starList = new ArrayList<>();
        starList.add(starLowHigh);
        starList.add(starHighLow);

        SortRS sortRS = new SortRS();
        sortRS.setRecommended(setSortValue("Recommended", recommendStatus, SortTypeConstant.SORT_RECOMMENDED));
        sortRS.setPrice(setSortList("Price", priceStatus, priceList));
        sortRS.setStar(setSortList("Star", starStatus, starList));
        sortRS.setGuestRating(setSortValue("Guest Rating", guestRatingStatus, SortTypeConstant.SORT_GUEST_RATING));
        sortRS.setDistance(setSortValue("Distance", distanceStatus, SortTypeConstant.SORT_DISTANCE));

        return sortRS;
    }

    @Override
    public List<HotelRS> sortInfo(SortTypeConstant sortBy, List<HotelCached> hotelCaches) {
        Stream<HotelCached> hotelCachedStream = hotelCaches.stream();

        if (sortBy.name().contains(SortTypeConstant.SORT_STAR_LOW_HIGH.getValue())) {

            Comparator<HotelCached> comparator = Comparator
                .comparing(hotelCached -> hotelCached.getFilterInfo().getStar());

            if (sortBy == SortTypeConstant.SORT_STAR_HIGH_LOW)
                comparator = comparator.reversed();

            hotelCachedStream = hotelCachedStream.sorted(comparator);

        } else if (sortBy.name().contains(SortTypeConstant.SORT_PRICE_LOW_HIGH.getValue())) {

            Comparator<HotelCached> comparator = Comparator
                .comparing(hotelCached -> hotelCached.getFilterInfo().getPrice());

            if (sortBy == SortTypeConstant.SORT_PRICE_HIGH_LOW)
                comparator = comparator.reversed();

            hotelCachedStream = hotelCachedStream.sorted(comparator);

        } else if (sortBy == SortTypeConstant.SORT_GUEST_RATING) {
            Comparator<HotelCached> comparator = Comparator
                .comparingDouble(hotelCached -> hotelCached.getScore().getNumber());

            hotelCachedStream = hotelCachedStream.sorted(comparator.reversed());
        } else if (sortBy == SortTypeConstant.SORT_DISTANCE) {
            Comparator<HotelCached> comparator = Comparator
                .comparingDouble(hotelCached ->
                    hotelCached.getLocation().getDistance() != null ?
                        Double.parseDouble(hotelCached.getLocation().getDistance()) : 1);

            hotelCachedStream = hotelCachedStream.sorted(comparator);
        }

        return hotelCachedStream
            .collect(Collectors.toList())
            .stream()
            .map(hotelCached -> modelMapper.map(hotelCached, HotelRS.class))
            .collect(Collectors.toList());
    }

    private SortValueRS setSortValue(String title, int status, SortTypeConstant value)
    {
        SortValueRS sortValueRS = new SortValueRS();
        sortValueRS.setTitle(title);
        sortValueRS.setStatus(status);
        sortValueRS.setValue(value);

        return sortValueRS;
    }

    private SortListRS setSortList(String title, int status, List<SortValueRS> list)
    {
        SortListRS sortListRS = new SortListRS();
        sortListRS.setTitle(title);
        sortListRS.setStatus(status);
        sortListRS.setList(list);

        return sortListRS;
    }

}
