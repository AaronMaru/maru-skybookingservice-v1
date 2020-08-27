package com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.CreateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.UpdateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface AdditionalServiceSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * add additional service booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request CreateAdditionalServiceRQ
     * @return
     */
    StructureRS addService(CreateAdditionalServiceRQ request);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * update additional service booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id Integer
     * @param request UpdateAdditionalServiceRQ
     * @return
     */
    StructureRS updateService(Integer id, UpdateAdditionalServiceRQ request);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete additional service booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id Integer
     * @return
     */
    StructureRS delete(Integer id);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve additional services of booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId Integer
     * @return
     */
    StructureRS listing(Integer bookingId);

}
