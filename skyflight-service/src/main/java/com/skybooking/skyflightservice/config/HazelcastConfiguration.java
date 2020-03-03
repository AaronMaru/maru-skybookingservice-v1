package com.skybooking.skyflightservice.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientUserCodeDeploymentConfig;
import com.hazelcast.core.HazelcastInstance;
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
        config.setUserCodeDeploymentConfig(clientUserCodeDeploymentConfig);

        config.getNetworkConfig().addAddress("hazelcast-server:5701");

        config.getGroupConfig().setName("dev");

        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig config) {
        return HazelcastClient.newHazelcastClient(config);
    }
}
