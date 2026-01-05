package com.BenjaminPark.controller;

import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateUserDTO;
import com.BenjaminPark.dto.UserResponse;
import com.BenjaminPark.mapper.UserMapper;
import com.BenjaminPark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userMapper.fromCreateUserDTO(createUserDTO);
        UserResponse userResponse = userMapper.toUserResponse(userService.createUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
