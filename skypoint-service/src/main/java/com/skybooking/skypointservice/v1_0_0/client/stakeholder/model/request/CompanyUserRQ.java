package com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompanyUserRQ {

    private List<String> keycode;

}
