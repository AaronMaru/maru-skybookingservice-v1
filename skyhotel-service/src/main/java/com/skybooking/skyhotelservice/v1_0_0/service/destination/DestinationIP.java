package com.skybooking.skyhotelservice.v1_0_0.service.destination;

import com.skybooking.skyhotelservice.constant.DestinationGroupByConstant;
import com.skybooking.skyhotelservice.constant.DestinationTypeGroupConstant;
import com.skybooking.skyhotelservice.constant.PopularcityConstant;
import com.skybooking.skyhotelservice.constant.SearchByConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.destination.DestinationAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.destination.Destination;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.destination.DestinationRQDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.popularcity.HotelPopularCityEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.popularcity.PopularCityRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.destination.DestinationRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.destination.GroupDestinationRS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DestinationIP extends BaseServiceIP implements DestinationSV {

    @Autowired
    private DestinationAction destinationAction;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PopularCityRP popularCityRP;

    public StructureRS quickSearch(String groupByKey) {

        List<HotelPopularCityEntity> quickSearch = popularCityRP.findByType(PopularcityConstant.QUICK_SEARCH);
        List<String> desCode = quickSearch
                .stream().map(HotelPopularCityEntity::getDestinationCode)
                .collect(Collectors.toList());

        List<Destination> destinations = destinationAction.quickSearch(new DestinationRQDS(desCode)).getData();

        if (!groupByKey.isEmpty()) {
            List<GroupDestinationRS> rs = new ArrayList<>();

            if (groupByKey.toUpperCase().equals(DestinationGroupByConstant.CONTINENT.getValue())) {
                Map<String, List<DestinationRS>> groupDestinationMaps = destinations.stream()
                    .collect(Collectors.groupingBy(Destination::getContinent,
                        Collectors.mapping(this::mapToDestinationRS,
                            Collectors.toList())));

                groupDestinationMaps.forEach((s, destinations1) ->
                    rs.add(new GroupDestinationRS(DestinationGroupByConstant.CONTINENT.getValue(), s, destinations1)));
            } else {
                Map<String, List<DestinationRS>> groupDestinationMaps = destinations.stream()
                    .collect(Collectors.groupingBy(Destination::getCountryName,
                        Collectors.mapping(this::mapToDestinationRS,
                            Collectors.toList())));

                groupDestinationMaps.forEach((s, destinations1) ->
                    rs.add(new GroupDestinationRS(DestinationGroupByConstant.COUNTRY.getValue(), s, destinations1)));
            }

            return responseBodyWithSuccessMessage(rs);
        }

        return responseBodyWithSuccessMessage(destinations);
    }

    private DestinationRS mapToDestinationRS(Destination destination) {
        DestinationRS destinationRS = modelMapper.map(destination, DestinationRS.class);
        destinationRS.setSearchBy(SearchByConstant.getSearchBy(destination.getType()).name());
        destinationRS.setTypeGroup(DestinationTypeGroupConstant.getGroup(destination.getType()).name());
        return destinationRS;
    }

    public StructureRS autocompleteSearch(String keyword) {
        List<Destination> destinations = destinationAction.autocompleteSearch(keyword).getData();
        return responseBodyWithSuccessMessage(destinations.stream()
            .map(this::mapToDestinationRS)
            .collect(Collectors.toList()));
    }

}
