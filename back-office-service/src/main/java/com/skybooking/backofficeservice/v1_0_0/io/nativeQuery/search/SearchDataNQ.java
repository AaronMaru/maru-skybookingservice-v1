package com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.search;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("search")
@Component
public interface SearchDataNQ extends NativeQuery {

    List<SearchDataTO> search(@NativeQueryParam(value = "keyword") String keyword);
}