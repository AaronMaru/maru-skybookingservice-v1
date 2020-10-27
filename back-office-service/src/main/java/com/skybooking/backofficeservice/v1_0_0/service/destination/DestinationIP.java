package com.skybooking.backofficeservice.v1_0_0.service.destination;

import com.skybooking.backofficeservice.v1_0_0.ui.model.request.destination.DestinationRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.destination.DestinationRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DestinationIP implements DestinationSV{

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<DestinationRS> listing()
    {
        DestinationRQ destinationRQ = new DestinationRQ(request);

        return null;
    }
}
