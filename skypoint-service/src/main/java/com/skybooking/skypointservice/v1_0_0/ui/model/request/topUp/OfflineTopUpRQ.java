package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OfflineTopUpRQ {
    private BigDecimal amount;
    private String userCode;
    private String email;
    private String name;
    private String phoneNumber;
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String referenceCode;
    private String createdBy;
    private String remark;
    private List<MultipartFile> file;

}
