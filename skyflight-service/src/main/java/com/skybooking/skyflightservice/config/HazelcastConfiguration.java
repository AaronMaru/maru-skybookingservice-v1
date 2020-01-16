package com.skybooking.skyflightservice.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientUserCodeDeploymentConfig;
import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public ClientConfig clientConfig() {
        var config = new ClientConfig();

        // deploy user code
        ClientUserCodeDeploymentConfig clientUserCodeDeploymentConfig = new ClientUserCodeDeploymentConfig();
        clientUserCodeDeploymentConfig.setEnabled(true);
        clientUserCodeDeploymentConfig.addClass(ShoppingTransformEntity.class);
        clientUserCodeDeploymentConfig.addClass(Aircraft.class);
        clientUserCodeDeploymentConfig.addClass(Airline.class);
        clientUserCodeDeploymentConfig.addClass(Baggage.class);
        clientUserCodeDeploymentConfig.addClass(BaggageDetail.class);
        clientUserCodeDeploymentConfig.addClass(FlightLeg.class);
        clientUserCodeDeploymentConfig.addClass(FlightShopping.class);
        clientUserCodeDeploymentConfig.addClass(Itinerary.class);
        clientUserCodeDeploymentConfig.addClass(Leg.class);
        clientUserCodeDeploymentConfig.addClass(LegAirline.class);
        clientUserCodeDeploymentConfig.addClass(LegDescription.class);
        clientUserCodeDeploymentConfig.addClass(LegGroup.class);
        clientUserCodeDeploymentConfig.addClass(LegSegmentDetail.class);
        clientUserCodeDeploymentConfig.addClass(Location.class);
        clientUserCodeDeploymentConfig.addClass(Price.class);
        clientUserCodeDeploymentConfig.addClass(PriceDetail.class);
        clientUserCodeDeploymentConfig.addClass(ScheduleEntity.class);
        clientUserCodeDeploymentConfig.addClass(Segment.class);
        clientUserCodeDeploymentConfig.addClass(ShoppingQueryEntity.class);
        clientUserCodeDeploymentConfig.addClass(ShoppingResponseEntity.class);

        clientUserCodeDeploymentConfig.setEnabled(true);
        config.setUserCodeDeploymentConfig(clientUserCodeDeploymentConfig);
        config.getGroupConfig().setName("dev");

        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig config) {
        return HazelcastClient.newHazelcastClient(config);
    }
}
