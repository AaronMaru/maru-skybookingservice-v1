package com.skybooking.skyhotelservice.v1_0_0.client.model.response.recommend;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StructureRSDS {
    private int status;
    private String message = "";
    private Object data;
    private PagingRS paging;
}