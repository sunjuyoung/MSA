package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "email cannot be null")
    @Size(min=10,message = "size")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min=4,message = "size")
    private String password;

    @NotNull(message = "name cannot be null")
    @Size(min=2,message = "size")
    private String name;
}
