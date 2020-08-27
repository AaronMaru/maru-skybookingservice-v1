package com.skybooking.skypointservice.v1_0_0.service.extrarate;

import com.skybooking.skypointservice.constant.ConfigTopUpTypeConstant;
import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.extrarate.ExtraRateRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraRateIP implements ExtraRateSV {

    @Autowired
    private ConfigTopUpRP configTopupRP;

    @Override
    public StructureRS extraSkyUser() {
        StructureRS structureRS = new StructureRS();
        return this.getExtraRateByUserType(structureRS, UserTypeConstant.SKYUSER);
    }

    @Override
    public StructureRS extraSkyOwner() {
        StructureRS structureRS = new StructureRS();
        return this.getExtraRateByUserType(structureRS, UserTypeConstant.SKYOWNER);
    }

    @Override
    public StructureRS editSkyUser() {
        StructureRS structureRS = new StructureRS();
        return structureRS;
    }

    @Override
    public StructureRS editSkyOwner() {
        StructureRS structureRS = new StructureRS();
        return structureRS;
    }

    private StructureRS getExtraRateByUserType(StructureRS structureRS, String userType) {
        //======== Get topUp extra rate configuration
        ConfigTopUpEntity configTopUp = configTopupRP.findByTypeAndTopupKeyAndStatus(userType,
                ConfigTopUpTypeConstant.EXTRA_RATE, true);

        if (configTopUp == null) {
            throw new BadRequestException("No configuration extra rate.", null);
        }

        //========= Set up response
        ExtraRateRS extraRateRS = new ExtraRateRS();
        extraRateRS.setValue(configTopUp.getValue());
        structureRS.setMessage(ResponseConstant.SUCCESS);
        structureRS.setData(extraRateRS);
        return structureRS;
    }

}
