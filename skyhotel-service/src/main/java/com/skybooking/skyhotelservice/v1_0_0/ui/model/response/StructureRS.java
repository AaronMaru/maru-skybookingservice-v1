package com.skybooking.skyhotelservice.v1_0_0.ui.model.response;

import com.skybooking.skyhotelservice.constant.ResponseConstant;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class StructureRS {

    private int status;
    private String message;
    private Object data;
    private PagingRS paging;

    public StructureRS() {}
    public StructureRS(Object data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
        this.message = ResponseConstant.SUCCESS;
    }

}