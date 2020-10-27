package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OfflineTopUpRQ {
    @Digits(integer = 10, fraction = 0, message = "decimal_not_allow")
    private BigDecimal amount;
    private String userCode;
    private String referenceCode;
    private String createdBy;
    private String remark;
    private List<MultipartFile> file = null;

}
