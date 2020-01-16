package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingQueryEntity;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.QueryTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class QueryIP implements QuerySV {

    private final static String QUERY_CACHED_NAME = "shopping-query";
    @Autowired
    private HazelcastInstance instance;
    @Autowired
    private AppConfig appConfig;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create shopping request and store cached.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return ShoppingQueryEntity
     */
    @Override
    public ShoppingQueryEntity flightShoppingCreate(FlightShoppingRQ request) {

        var shoppingQuery = QueryTF.getRequestEntity(request);

        instance.getMap(QUERY_CACHED_NAME).put(shoppingQuery.getId(), shoppingQuery, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);

        return shoppingQuery;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check shopping request in cached
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return ShoppingQueryEntity
     */
    @Override
    public ShoppingQueryEntity flightShoppingExist(FlightShoppingRQ request) {

        Javers javers = JaversBuilder.javers().build();
        var query = QueryTF.getRequestEntity(request);

        IMap<String, ShoppingQueryEntity> queries = instance.getMap(QUERY_CACHED_NAME);

        for (ShoppingQueryEntity cached : queries.values()) {
            Diff diff = javers.compare(query.getQuery(), cached.getQuery());
            if (diff.getChanges().isEmpty()) return cached;
        }

        return null;
    }


}
