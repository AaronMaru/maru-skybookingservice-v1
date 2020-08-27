package com.skybooking.skyhotelservice.v1_0_0.ui.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StructureRS {

    private int status;
    private String message;
    private Object data;
    private PagingRS paging;

}