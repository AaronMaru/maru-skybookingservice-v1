package com.skybooking.skyhistoryservice.v1_0_0.util.cls;

import com.skybooking.skyhistoryservice.v1_0_0.util.cls.SortUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class FlightSaveODSUtils extends SortUtils {

    public static Sort sort(String by, String order) {
        return sortBy(Arrays.asList("oLocation", "dLocation", "dDateTime", "aDateTime"), by, order);
    }
}
