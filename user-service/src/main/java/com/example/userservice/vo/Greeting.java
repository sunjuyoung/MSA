package com.example.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Greeting {

    @Value("${greeting.message}")
    private String message;
}
