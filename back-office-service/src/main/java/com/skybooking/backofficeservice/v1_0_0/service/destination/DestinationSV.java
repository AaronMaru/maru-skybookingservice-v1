package com.skybooking.backofficeservice.v1_0_0.service.destination;

import com.skybooking.backofficeservice.v1_0_0.ui.model.response.destination.DestinationRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DestinationSV {

    List<DestinationRS> listing();
}
