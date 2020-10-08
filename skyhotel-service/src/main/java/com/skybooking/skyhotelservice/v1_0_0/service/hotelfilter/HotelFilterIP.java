package com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter;

import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import com.skybooking.skyhotelservice.constant.PromotionTypeConstant;
import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.constant.TopHotelTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterPriceRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.BoardRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.SegmentRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HotelFilterIP implements HotelFilterSV {

    private final ModelMapper modelMapper;

    @Override
    public FilterRS available(List<HotelRS> list, FilterRQ filterRQ) {
        FilterRS filterRS = new FilterRS();

        if (list.isEmpty())
            return filterRS;

        // Price Range
        HotelRS minHotel = list
            .stream()
            .min(Comparator.comparing(hotel -> hotel.getPriceUnit().getAmount()))
            .orElseThrow(NoSuchElementException::new);

        HotelRS maxHotel = list
            .stream()
            .max(Comparator.comparing(hotel -> hotel.getPriceUnit().getAmount()))
            .orElseThrow(NoSuchElementException::new);

        filterRS.setPriceRange(
            new FilterPriceRS(
                minHotel.getPriceUnit().getAmount(),
                maxHotel.getPriceUnit().getAmount()));

        // Boards
        List<FilterCodeRS> boards = new ArrayList<>();
        List<FilterCodeRS> accommodationTypes = new ArrayList<>();
        List<FilterCodeRS> zones = new ArrayList<>();
        List<FilterCodeRS> chains = new ArrayList<>();
        List<FilterNumberRS> segments = new ArrayList<>();
        list.forEach(hotel -> {
            for (BoardRS boardRS : hotel.getBoards())
                if (boards.stream().noneMatch(filterBoardRS -> filterBoardRS.getValue().equals(boardRS.getCode())))
                    boards.add(new FilterCodeRS(boardRS.getCode(), boardRS.getDescription()));
            if (accommodationTypes.stream()
                .noneMatch(accommodationTypeRS ->
                    accommodationTypeRS.getValue().equals(hotel.getBasic().getAccommodationTypeCode())))
                accommodationTypes.add(new FilterCodeRS(
                    hotel.getBasic().getAccommodationTypeCode(),
                    hotel.getBasic().getAccommodationTypeName()));
            if (zones.stream()
                .noneMatch(zoneRS ->
                    zoneRS.getValue() != null && zoneRS.getValue().equals(hotel.getLocation().getAreaCode())))
                zones.add(new FilterCodeRS(
                    hotel.getLocation().getAreaCode(),
                    hotel.getLocation().getAreaName()));
            if (chains.stream()
                .noneMatch(chainRS ->
                    chainRS.getValue() != null && chainRS.getValue().equals(hotel.getBasic().getChainCode())))
                if (hotel.getBasic().getChainCode() != null)
                    chains.add(new FilterCodeRS(
                        hotel.getBasic().getChainCode(),
                        hotel.getBasic().getChainName()));
            for (SegmentRS segment : hotel.getSegments())
                if (segments.stream().noneMatch(filterSegmentRS -> filterSegmentRS.getValue().equals(segment.getCode())))
                    segments.add(new FilterNumberRS(segment.getCode(), segment.getDescription()));
        });

        // Set filter status
        filterRS.setBoards(boards.stream()
            .peek(board -> board
                .setStatus(filterRQ != null && filterRQ.getBoards().contains(board.getValue())))
            .collect(Collectors.toList()));
        filterRS.setAccommodationTypes(accommodationTypes.stream()
            .peek(accommodation -> accommodation
                .setStatus(filterRQ != null && filterRQ.getAccommodationTypes().contains(accommodation.getValue())))
            .collect(Collectors.toList()));
        filterRS.setZones(zones.stream()
            .peek(zone -> zone
                .setStatus(filterRQ != null && filterRQ.getZones().contains(zone.getValue())))
            .collect(Collectors.toList()));
        filterRS.setChains(chains.stream()
            .peek(chain -> chain
                .setStatus(filterRQ != null && filterRQ.getChainNames().contains(chain.getValue())))
            .collect(Collectors.toList()));
        filterRS.setEstablishmentProfile(segments.stream()
            .peek(segment -> segment
                .setStatus(filterRQ != null && filterRQ.getEstablishmentProfile().contains(segment.getValue())))
            .collect(Collectors.toList()));

        // Cancellation Policy
        List<FilterCodeRS> cancellationPolicies = List.of(
            new FilterCodeRS(CancellationTypeConstant.FREE.name(), "Free Cancellation"),
            new FilterCodeRS(CancellationTypeConstant.PARTIAL.name(), "Partial cancellation fees"),
            new FilterCodeRS(CancellationTypeConstant.NON_REFUNDABLE.name(), "Non refundable")
        );
        filterRS.setCancellations(cancellationPolicies.stream()
            .peek(cancellationPolicy -> cancellationPolicy
                .setStatus(filterRQ != null && filterRQ.getCancellations().contains(cancellationPolicy.getValue())))
            .collect(Collectors.toList()));

        // Promotion
        List<FilterCodeRS> promotions = List.of(
            new FilterCodeRS(PromotionTypeConstant.SPECIAL.name(), "Special Offers")
        );
        filterRS.setPromotions(promotions.stream()
            .peek(promotion -> promotion.setStatus(filterRQ != null && filterRQ.getPromotions().contains(promotion.getValue())))
            .collect(Collectors.toList()));

        // Top Hotel
        List<FilterCodeRS> topHotels = List.of(
            new FilterCodeRS(TopHotelTypeConstant.TOP_ONLY.name(), "Top Hotel Only")
        );
        filterRS.setTopHotels(topHotels.stream()
            .peek(topHotel -> topHotel.setStatus(filterRQ != null && filterRQ.getTopHotels().contains(topHotel.getValue())))
            .collect(Collectors.toList()));

        // Star Hotel
        List<FilterNumberRS> stars = List.of(
            new FilterNumberRS(2, "<=2"),
            new FilterNumberRS(3, "3"),
            new FilterNumberRS(4, "4"),
            new FilterNumberRS(5, "5")
        );
        filterRS.setStars(stars.stream()
            .peek(star -> star.setStatus(filterRQ != null && filterRQ.getStars().contains(star.getValue())))
            .collect(Collectors.toList()));

        // Score Hotel
        List<FilterNumberRS> scores = List.of(
            new FilterNumberRS(3.0, "3+"),
            new FilterNumberRS(3.5, "3.5+"),
            new FilterNumberRS(4.0, "4.0+"),
            new FilterNumberRS(4.5, "4.5+")
        );
        filterRS.setScores(scores.stream()
            .peek(score -> score.setStatus(filterRQ != null && filterRQ.getScores().contains(score.getValue())))
            .collect(Collectors.toList()));

        return filterRS;
    }

    @Override
    public List<HotelRS> filter(List<HotelRS> list, FilterRQ filter) {

        if (filter != null)
            return list.stream()
                .filter(hotelRS -> {
                    boolean isFiltered = true;

                    // Comparing Price range
                    FilterPriceRQ filterPriceRQ = filter.getPriceRange();
                    if (filterPriceRQ != null) {
                        boolean isMin = true;
                        boolean isMax = true;
                        if (filterPriceRQ.getMin() != null)
                            isMin = hotelRS.getPriceUnit().getAmount().compareTo(filterPriceRQ.getMin()) >= 0;
                        if (filterPriceRQ.getMax() != null)
                            isMax = hotelRS.getPriceUnit().getAmount().compareTo(filterPriceRQ.getMax()) <= 0;
                        isFiltered = isMin && isMax;
                    }

                    // Filter AccommodationType
                    if (!filter.getAccommodationTypes().isEmpty())
                        isFiltered &= filter.getAccommodationTypes()
                            .contains(hotelRS.getBasic().getAccommodationTypeCode());

                    // Filter if matched one of hotel boards
                    if (!filter.getBoards().isEmpty()) {
                        boolean inBoard = false;
                        for (BoardRS boardRS : hotelRS.getBoards())
                            inBoard |= filter.getBoards().contains(boardRS.getCode());
                        isFiltered &= inBoard;
                    }

                    // Filter hotel zone
                    if (!filter.getZones().isEmpty())
                        isFiltered &= filter.getZones()
                            .contains(hotelRS.getLocation().getAreaCode());

                    // Filter hotel star
                    if (!filter.getStars().isEmpty()) {
                        boolean isStared = false;
                        for (Number star : filter.getStars()) {
                            if (star.equals(2))
                                isStared |= hotelRS.getBasic().getStar() <= star.intValue();
                            else
                                isStared |= hotelRS.getBasic().getStar() == star.intValue();
                        }
                        isFiltered &= isStared;
                    }

                    // Filter hotel scores
                    if (!filter.getScores().isEmpty()) {
                        boolean isScored = false;
                        for (Number score : filter.getScores()) {
                            isScored |= hotelRS.getScore().getNumber() >= score.floatValue();
                        }
                        isFiltered &= isScored;
                    }

                    // Filter hotel chain
                    if (!filter.getChainNames().isEmpty())
                        isFiltered &= filter.getChainNames()
                            .contains(hotelRS.getBasic().getChainCode());

                    // Filter Cancellation Policies
                    if (!filter.getCancellations().isEmpty()) {
                        isFiltered &= filter.getCancellations()
                            .contains(hotelRS.getCancellation().getType().toString());
                    }

                    // Filter establishment profile
                    if (!filter.getEstablishmentProfile().isEmpty()) {
                        boolean isProfiled = false;
                        for (SegmentRS segment : hotelRS.getSegments()) {
                            isProfiled |= filter.getEstablishmentProfile().contains(segment.getCode());
                        }
                        isFiltered &= isProfiled;
                    }

                    return isFiltered;
                })
                .collect(Collectors.toList());

        return list;

    }

    @Override
    public List<HotelRS> filterByOccupancy(List<HotelRS> hotelRS, AvailabilityRQ availabilityRQ) {

        if (hotelRS.isEmpty())
            return hotelRS;

        return hotelRS.stream()
            .filter(hotelRS1 -> hotelRS1
                .getRooms()
                .stream()
                // filter only match adult and children rooms
                .filter(roomRS -> roomRS.getMinAdults() <= availabilityRQ.getAdult() &&
                    roomRS.getMaxAdults() >= availabilityRQ.getAdult() &&
                    roomRS.getMaxChildren() >= availabilityRQ.getChildren().size())
                .anyMatch(roomRS -> roomRS
                    .getRates()
                    .stream()
                    // filter only room have enough allotment
                    .anyMatch(rateRS -> rateRS.getAllotment() >= availabilityRQ.getRoom())))
            // filter only hotels that have available rooms
            .filter(hotelRS1 -> !hotelRS1.getRooms().isEmpty())
            .collect(Collectors.toList());

    }

}
