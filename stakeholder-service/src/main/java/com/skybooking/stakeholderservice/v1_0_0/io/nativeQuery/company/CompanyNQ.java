package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("company")
@Component
public interface CompanyNQ extends NativeQuery {
    
    List<CompanyTO> companyInfoTopUp(@NativeQueryParam(value = "keyword") String keyword);

    CompanyDetailsTO companyInfoDetailsTopUp(@NativeQueryParam(value = "companyCode") String companyCode);

}
