package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.skybooking.stakeholderservice.exception.anotation.Include;

import javax.validation.constraints.NotEmpty;

public class LoginSocialRQ {

    @NotEmpty(message = "Please provide username")
    private String username;

    @NotEmpty(message = "Please provide provider id")
    private String providerId;

    @NotEmpty(message = "Please provide provider")
    @Include(contains = "facebook|google", delimiter = "\\|")
    private String provider;

    @NotEmpty(message = "Please provide first name")
    private String firstName;

    @NotEmpty(message = "Please provide last name")
    private String lastName;

    private String photoUrl;
    private String password;
    private String typeSky;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeSky() {
        return typeSky;
    }

    public void setTypeSky(String typeSky) {
        this.typeSky = typeSky;
    }

}
