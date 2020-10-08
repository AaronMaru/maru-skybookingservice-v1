package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.info;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company.CompanyDetailsTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company.CompanyTO;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("info")
@Component
public interface InfoCompanyUserNQ extends NativeQuery {
    
    List<CompanyUserTO> searchCompanyOrUser(@NativeQueryParam(value = "keyword") String keyword);
    CompanyUserDetailsTO companyOrUserDetails(@NativeQueryParam(value = "companyCode") String companyCode);
    List<CompanyUserTO> companyOrUserList(@NativeQueryParam(value = "keycode") List<String> keycode);
}
