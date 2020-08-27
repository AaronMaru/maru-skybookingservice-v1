package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo.AddressTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo.ContactNumbersTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo.PaymentInfoTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo.PersonNameTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CustomerInfoTA implements Serializable {

    private List<PersonNameTA> personNames;
    private PaymentInfoTA paymentInfo;
    private ContactNumbersTA contactNumbers;
    private AddressTA address;

}
