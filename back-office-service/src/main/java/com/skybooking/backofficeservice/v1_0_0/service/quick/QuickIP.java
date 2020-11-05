package com.skybooking.backofficeservice.v1_0_0.service.quick;

import com.skybooking.backofficeservice.constant.VariableConstant;
import com.skybooking.backofficeservice.v1_0_0.io.entity.QuickEntity;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.quick.QuickNQ;
import com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.quick.QuickTO;
import com.skybooking.backofficeservice.v1_0_0.io.repository.QuickRP;
import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.quick.QuickRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickIP implements QuickSV{

    @Autowired
    private QuickRP quickRP;

    @Autowired
    private QuickNQ quickNQ;

    @Override
    public List<QuickRS> listing()
    {
        List<QuickTO> quickCodes = quickNQ.getDestinationCode(VariableConstant.QUICK_TYPE);

        return null;
    }

    @Override
    public void created(QuickRQ quickRQ)
    {
        QuickEntity quick = new QuickEntity();

        quick.setDestinationCode(quickRQ.getDestinationCode());
        quick.setDestinationType(quickRQ.getDestinationType());
        quick.setType(VariableConstant.QUICK_TYPE);

        quickRP.save(quick);
    }

    @Override
    public Boolean deleted(Integer id)
    {
        try {
            var quick = quickRP.findQuickById(id);
                if(quick == null){
                    return  false;
                }
            quickRP.delete(quick);

            return  true;
        }catch (Exception ex){
            return false;
        }

    }
}
