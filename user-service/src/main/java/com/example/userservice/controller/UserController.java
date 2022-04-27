package com.example.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome user-service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request")String header){
            log.info(header);
        return "hello user-service";
    }
    @GetMapping("/check")
    public String check(){
        return "hi check";
    }
}
