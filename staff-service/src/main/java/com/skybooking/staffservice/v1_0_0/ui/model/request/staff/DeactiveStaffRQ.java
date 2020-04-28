package com.skybooking.staffservice.v1_0_0.ui.model.request.staff;

import com.skybooking.staffservice.exception.anotation.Include;
import lombok.Data;

@Data
public class DeactiveStaffRQ {

    private Long skyuserId;

    @Include(contains = "admin|editor", delimiter = "\\|")
    private String role;

    @Include(contains = "0|1", delimiter = "\\|")
    private Integer status;
}
