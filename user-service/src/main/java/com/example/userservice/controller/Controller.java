package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.response.UserResponse;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"api/demo"})
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public void test() {
        System.out.println("test demo user");
    }

    @GetMapping("user/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping ("user")
    public UserResponse createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping ("user/register")
    public UserResponse register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}
