package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.search.DetailSearchSV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1.0.0/search")
public class SearchController extends BaseController{
    @Autowired
    private DetailSearchSV detailSearchSV;

    @GetMapping()
    public ResponseEntity<StructureRS> searchData(@Param("keyword") String keyword)
    {
        return response(detailSearchSV.searchData(keyword));
    }
}