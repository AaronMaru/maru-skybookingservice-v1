package com.skybooking.skyflightservice.v1_0_0.ui.controller.backoffice;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice.AdditionalServiceSV;
import com.skybooking.skyflightservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.CreateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.UpdateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/back-office/service")
public class AdditionalServiceController extends BaseController {

    @Autowired private AdditionalServiceSV additionalServiceSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * add additional service booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request CreateAdditionalServiceRQ
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('create-additional-service-skyflight')")
    @PostMapping()
    public ResponseEntity<StructureRS> addService(@Valid @RequestBody CreateAdditionalServiceRQ request)
    {
        return response(additionalServiceSV.addService(request));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * update additional service booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id Integer
     * @param request UpdateAdditionalServiceRQ
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('update-additional-service-skyflight')")
    @PostMapping("/{id}")
    public ResponseEntity<StructureRS> updateService(@PathVariable Integer id,
                                                     @Valid @RequestBody UpdateAdditionalServiceRQ request)
    {
        return response(additionalServiceSV.updateService(id, request));
    }

    @PreAuthorize("#oauth2.hasScope('delete-additional-service-skyflight')")
    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> delete(@PathVariable Integer id)
    {
        return response(additionalServiceSV.delete(id));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve additional services of booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('list-additional-service-skyflight')")
    @GetMapping("/{bookingId}")
    public ResponseEntity<StructureRS> listing(@PathVariable Integer bookingId)
    {
        return response(additionalServiceSV.listing(bookingId));
    }
}
