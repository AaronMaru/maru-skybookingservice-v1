package com.skybooking.skyhotelservice.v1_0_0.service.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.client.action.popularcity.PopularCityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.DestinationRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.popularcity.PopularCityRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.popularCity.PopularCityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.popularcity.HotelPopularCityEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.popularcity.PopularCityRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularCityIP extends BaseServiceIP implements PopularcitySV {

    private final PopularCityRP popularCityRP;
    private final PopularCityAction popularCityAction;

    public StructureRS listing() {

        List<HotelPopularCityEntity> popularCities = popularCityRP.findAll();

        List<DestinationRQDS> destinationCodes = popularCities
            .stream()
            .map(hotelPopularCity -> new DestinationRQDS(hotelPopularCity.getDestinationCode()))
            .collect(Collectors.toList());

        PopularCityRQDS popularCityRQDS = new PopularCityRQDS();
        popularCityRQDS.setDestinations(destinationCodes);

        PopularCityRSDS popularCityRSDS = popularCityAction.getPopularCity(popularCityRQDS);

        Map<String, HotelPopularCityEntity> popularCityMap = popularCities.stream()
            .collect(Collectors
                .toMap(HotelPopularCityEntity::getDestinationCode,
                    hotelPopularCity -> hotelPopularCity));

        return responseBodyWithSuccessMessage(
            popularCityRSDS.getData().stream()
                .peek(popularCity -> popularCity
                    .setThumbnail(popularCityMap.getOrDefault(popularCity.getDestinationCode(),
                        new HotelPopularCityEntity()).getThumbnail())).collect(Collectors.toList())
        );

    }

}
