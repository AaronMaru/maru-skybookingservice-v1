package com.skybooking.skyhistoryservice.v1_0_0.util.general;

import org.springframework.data.domain.Sort;
import java.util.List;

public class SortUtils {

    protected static Sort sortBy(List<String> fields, String by, String order) {

        if (!fields.contains(by)) {
            return Sort.by(Sort.Direction.DESC, "id");
        }

        switch (order) {
            case "ASC":
                return Sort.by(Sort.Direction.ASC, StringUtil.toSnake(by));
            default:
                return Sort.by(Sort.Direction.DESC, StringUtil.toSnake(by));
        }
    }
}
