package com.invokegs.dbcoursework.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
    @NotEmpty
    @Size(
            min = 4,
            max = 25,
            message = "Username name must be between 2 and 25 characters"
    )
    private String username;

    @NotEmpty
    @Size(min = 1, max = 50, message = "Email name must be between 1 and 50 characters")
    @Email(message = "Not correct email")
    private String email;

    @NotEmpty
    @Size(
            min = 5,
            max = 32,
            message = "Password name must be between 5 and 32 characters"
    )
    private String password;

    @NotEmpty

    @Size(
            min = 5,
            max = 32,
            message = "Password name must be between 5 and 32 characters"
    )
    private String repeatPassword;

    private String firstName;
    private String lastName;

    public UserDto(String username, String email, String password, String repeatPassword, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
