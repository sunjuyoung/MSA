package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
//@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

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
        StringBuilder sb = new StringBuilder();
        sb.append("local.port : "+env.getProperty("local.server.port"));
        sb.append(" server.port : "+env.getProperty("server.port"));
        sb.append(" token.secret : "+env.getProperty("token.secret"));
        sb.append(" token expiration time : "+env.getProperty("token.expiration_time"));
        return sb.toString();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody RequestUser user){
        UserDTO newUser = userService.createUser(modelMapper.map(user, UserDTO.class));
        return ResponseEntity.ok().body(modelMapper.map(newUser, ResponseUser.class));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        Iterable<UserEntity> userByAll = userService.getUserByAll();
        List<ResponseUser> responseUsers =  new ArrayList<>();
        userByAll.forEach(userEntity ->  responseUsers.add(modelMapper.map(userEntity,ResponseUser.class)));
        return ResponseEntity.ok().body(responseUsers);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId")String userId){
        UserDTO user = userService.getUserByUserId(userId);
        return ResponseEntity.ok().body(modelMapper.map(user,ResponseUser.class));
    }
}
