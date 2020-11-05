package com.skybooking.backofficeservice.v1_0_0.ui.model.response;

import com.skybooking.backofficeservice.v1_0_0.ui.model.PagingRS;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@Service
public class StructureRS {
    private int status;
    private String message;
    private Object data;
    private PagingRS paging;

    public StructureRS() {}

    public StructureRS(Object data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
    }

}

