package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    private String email;
    private String password;
    private String encryptedPassword;
    private String name;
    private String userId;
    private Date createdAt;


    private List<ResponseOrder> orders = new ArrayList<>();
}
