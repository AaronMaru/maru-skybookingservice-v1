package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Location implements Serializable {

    @Id
    private String code;
    private String airport;
    private String city;
    private double latitude;
    private double longitude;

}
