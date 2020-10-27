package com.skybooking.skypointservice.v1_0_0.ui.model.response.limitPoint;

import com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit.SkyOwnerLimitPointEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkyOwnerLimitPointListRS {
    List<SkyOwnerLimitPointEntity> skyOwnerLimitPointList;
}
