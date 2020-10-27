package com.skybooking.backofficeservice.v1_0_0.service.quick;

import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.quick.QuickRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuickSV {

    void created(QuickRQ quickRQ);

    List<QuickRS> listing();

    Boolean deleted(Integer id);
}
