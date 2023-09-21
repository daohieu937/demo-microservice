package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.statics.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = {"api/demo"})
public class Controller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping()
    public void test() {
        System.out.println("test demo user");
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping ("user")
    public User createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        List<Role> roles = new ArrayList<>();

        if (CollectionUtils.isEmpty(userDto.getRoleDto())) {
            roles.add(roleRepository.findByRoleType(RoleType.USER).get());
        }
        else {
            userDto.getRoleDto().forEach(roleType -> {
                Optional<Role> roleOptional = roleRepository.findByRoleType(roleType);
                roleOptional.ifPresent(roles::add);
            });
        }
        user.setRoles(roles);

        userRepository.save(user);
        return user;
    }


}
