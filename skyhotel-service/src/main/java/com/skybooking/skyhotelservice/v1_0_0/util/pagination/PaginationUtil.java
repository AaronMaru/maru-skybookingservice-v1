package com.skybooking.skyhotelservice.v1_0_0.util.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationUtil {

    public static <T> List<T> of(List<T> list)
    {
        return of(list, 1, 10);
    }

    public static <T> List<T> of(List<T> list, int page, int size)
    {
        if (list == null)
            return new ArrayList<>();

        if (page < 1)
            page = 1;

        if (size < 1)
            size = 10;

        if (list.size() < page * size - size)
            return new ArrayList<>();

        return list.stream().skip(page * size - size).limit(size).collect(Collectors.toList());
    }
}
