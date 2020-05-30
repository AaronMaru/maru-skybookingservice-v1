package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company;

public class BussinessDocRS {

    private Long id;
    private String name = "";
    private Integer isRequired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

}
