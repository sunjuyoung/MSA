package com.example.userservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class UserDTO {

    private String email;
    private String password;
    private String encryptedPwd;
    private String name;
    private String userId;
    private Date createdAt;


    //private List<ResponseOrder> orders = new ArrayList<>();
}
