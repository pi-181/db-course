package com.invokegs.dbcoursework.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
    @NotEmpty
    @Size(
            min = 4,
            max = 25
    )
    private String username;

    @NotEmpty
    @Size(min = 1, max = 50)
    @Email
    private String email;

    @NotEmpty
    @Size(
            min = 5,
            max = 32
    )
    private String password;

    @NotEmpty

    @Size(
            min = 5,
            max = 32
    )
    private String confirmPassword;

    private String firstName;
    private String lastName;

    public UserDto(String username, String email, String password, String confirmPassword, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}
