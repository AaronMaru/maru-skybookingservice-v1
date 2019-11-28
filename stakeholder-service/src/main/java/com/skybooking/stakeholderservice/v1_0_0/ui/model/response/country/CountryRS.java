package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.country;

public class CountryRS {
    Long id;
    String name;
    String nameLocale;

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

    public String getNameLocale() {
        return nameLocale;
    }

    public void setNameLocale(String nameLocale) {
        this.nameLocale = nameLocale;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() + " Name:" + this.getName() + " LocaleEntity: " + this.getNameLocale();
    }
}
