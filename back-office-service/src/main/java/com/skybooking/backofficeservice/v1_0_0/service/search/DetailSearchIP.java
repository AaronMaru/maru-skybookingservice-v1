package com.skybooking.backofficeservice.v1_0_0.service.search;

import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.search.SearchDataNQ;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.search.SearchDataTO;
import com.skybooking.backofficeservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailSearchIP extends BaseServiceIP implements DetailSearchSV {

    @Autowired
    private SearchDataNQ searchDataNQ;

    @Override
    public StructureRS searchData(String keyword)
    {
        List<SearchDataTO> searchDataTOList = new ArrayList<>();
        if (!keyword.equals("")) {
            searchDataTOList = searchDataNQ.search(keyword);
        }

        return responseBodyWithSuccessMessage(searchDataTOList);
    }
}