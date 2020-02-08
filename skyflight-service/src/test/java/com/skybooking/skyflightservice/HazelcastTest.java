package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HazelcastTest {

    @Autowired
    private DetailSV detailSV;


    @Test
    public void getDetail() {

        var shoppingId = "01748be0-5679-4af1-99ac-713c45e819c9";

        var airlineId = "K6";
        System.out.println(detailSV.getAirlineDetail(shoppingId, airlineId));

        var aircraftId = "319";
        System.out.println(detailSV.getAircraftDetail(shoppingId, aircraftId));

        var locationId = "DMK";
        System.out.println(detailSV.getLocationDetail(shoppingId, locationId));

        var priceId = "P-0-13-0-24";
        System.out.println(detailSV.getPriceDetail(shoppingId, priceId));

        var baggageId = "B-0-8-0-18";
        System.out.println(detailSV.getBaggageDetail(shoppingId, baggageId));

        var segmentId = "0-KUL-BKK-12";
        System.out.println(detailSV.getSegmentDetail(shoppingId, segmentId));

        var legId = "0-PNH-BKK-50";
        System.out.println(detailSV.getLegDetail(shoppingId, legId));

    }


    @Test
    public void getExpired() {

        final String shopping = "3c712e5c-67e4-4c15-a228-7f66db8bbdff";
        System.out.println("Key will expired in " + detailSV.getMinutesTimeToLive(shopping) + " minutes.");

    }

}
