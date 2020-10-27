package com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.quick;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("quick")
@Component
public interface QuickNQ  extends NativeQuery {

    List<QuickTO> getDestinationCode(@NativeQueryParam(value = "type") String type);
}
