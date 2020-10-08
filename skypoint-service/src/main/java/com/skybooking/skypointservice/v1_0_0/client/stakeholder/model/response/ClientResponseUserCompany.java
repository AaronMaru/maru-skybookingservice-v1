package com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponseUserCompany {
    private String message;
    private Integer status;
    private List<CompanyUserTO> data;
}
