package com.skybooking.backofficeservice.v1_0_0.service.popularCity;

import com.skybooking.backofficeservice.constant.VariableConstant;
import com.skybooking.backofficeservice.v1_0_0.io.entity.QuickEntity;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.popularCity.PopularCityNQ;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.popularCity.PopularCityTO;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.quick.QuickNQ;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.quick.QuickTO;
import com.skybooking.backofficeservice.v1_0_0.io.repository.QuickRP;
import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.PopularCityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularCityIP implements PopularCitySV {

    @Autowired private QuickNQ quickNQ;

    @Autowired private QuickRP quickRP;

    @Override
    public List<PopularCityRS> listing() {

       List<QuickTO> popularCode = quickNQ.getDestinationCode(VariableConstant.POPULAR_TYPE);

        return null;
    }

    @Override
    public void created(QuickRQ quickRQ)
    {
        QuickEntity popular = new QuickEntity();
        popular.setDestinationCode(quickRQ.getDestinationCode());
        popular.setDestinationType(quickRQ.getDestinationType());
        popular.setType(VariableConstant.POPULAR_TYPE);

        quickRP.save(popular);
    }

    @Override
    public  Boolean  deleted(Integer id)
    {
        try {
            var popular = quickRP.findQuickById(id);
                if(popular == null){
                    return  false;
                }
            quickRP.delete(popular);

            return true;
        }catch (Exception ex){
            ex.getMessage();
            return false;
        }
    }

}
