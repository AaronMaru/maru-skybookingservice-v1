package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("user")
@Component
public interface UserNQ extends NativeQuery {

    List<PermissionTO> listPermission(@NativeQueryParam(value = "role") String role);

}
