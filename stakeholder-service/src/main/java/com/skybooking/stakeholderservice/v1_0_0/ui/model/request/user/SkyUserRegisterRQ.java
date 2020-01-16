package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.stakeholderservice.exception.anotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@Code(first = "username", second = "code", message = "Please provide country code")
@UsernameUnique(first = "username", second = "code", message = "Username already exists")
public class SkyUserRegisterRQ {

    @Phone
    @Email
    @NotEmpty(message = "Please provide a email or phone number")
    private String username;

    @NotEmpty(message = "Please provide a first name")
    private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    private String code;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "Please provide a confirm password")
    private String confirmPassword;

    @NotEmpty(message = "Please provide a sky type")
    @Include(contains = "personal|bussiness", delimiter = "\\|")
    private String typeSky;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTypeSky() {
        return typeSky;
    }

    public void setTypeSky(String typeSky) {
        this.typeSky = typeSky;
    }

}
