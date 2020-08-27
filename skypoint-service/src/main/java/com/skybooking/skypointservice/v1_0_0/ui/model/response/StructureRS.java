package com.skybooking.skypointservice.v1_0_0.ui.model.response;

import com.skybooking.skypointservice.constant.ResponseConstant;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class StructureRS {
    private int status = HttpStatus.OK.value();
    private String message = ResponseConstant.SUCCESS;
    private Object data;
    private PagingRS paging;
}