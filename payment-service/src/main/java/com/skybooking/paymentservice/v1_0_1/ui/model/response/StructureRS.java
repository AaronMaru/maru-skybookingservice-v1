package com.skybooking.paymentservice.v1_0_1.ui.model.response;

import com.skybooking.paymentservice.constant.ResponseConstant;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class StructureRS {
    private int status = HttpStatus.OK.value();
    private String message = ResponseConstant.SUCCESS;
    private Map<String, Object> data;
    private PagingRS paging;
}