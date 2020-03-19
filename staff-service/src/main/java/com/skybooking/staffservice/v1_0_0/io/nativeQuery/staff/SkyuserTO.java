package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;
import lombok.Data;

import java.util.Date;


@Data
public class SkyuserTO {

    private Integer id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userCode;
    private Date dob;
    private String photo;
    private String gender;
    private String contPhone;
    private String contAddress;
    private String contEmail;
    private String profile;
    private String createdFrom;
    private Integer currencyId;
    private Integer languageId;
    private String deviceName;
    private int status;
    private Date createdAt;

}
