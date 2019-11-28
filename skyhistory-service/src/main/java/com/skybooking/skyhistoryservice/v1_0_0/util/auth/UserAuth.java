package com.skybooking.skyhistoryservice.v1_0_0.util.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAuth {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String code;
    private Integer changedPassword;
    private String photoMedium;
    private String photoSmall;
    private Short status;
    private String address;
    private List<Object> companies = new ArrayList<>();
    private String role;
    private List<Object> permission = new ArrayList<>();
    private Short skyowner;

    public UserAuth(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            var v = entry.getValue();
            switch (k) {
                case "firstName":
                    this.firstName = v.toString();
                    break;
                case "lastName":
                    this.lastName = v.toString();
                    break;
                case "email":
                    this.email = v.toString();
                    break;
                case "phone":
                    this.phone = v.toString();
                    break;
                case "code":
                    this.code = v.toString();
                    break;
                case "changedPassword":
                    this.changedPassword = Integer.parseInt(v.toString());
                    break;
                case "photoMedium":
                    this.photoMedium = v.toString();
                    break;
                case "photoSmall":
                    this.photoSmall = v.toString();
                    break;
                case "status":
                    this.status = Short.parseShort(v.toString());
                    break;
                case "address":
                    this.address = v.toString();
                    break;
                case "companies":
                    this.companies = (List<Object>) v;
                    break;
                case "role":
                    this.role = v.toString();
                    break;
                case "permission":
                    this.permission = (List<Object>) v;
                    break;
                case "skyowner":
                    this.skyowner = Short.parseShort(v.toString());
                    break;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public Integer getChangedPassword() {
        return changedPassword;
    }

    public String getPhotoMedium() {
        return photoMedium;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public Short getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public List<Object> getCompanies() {
        return companies;
    }

    public String getRole() {
        return role;
    }

    public List<Object> getPermission() {
        return permission;
    }

    public Short getSkyowner() {
        return skyowner;
    }
}
