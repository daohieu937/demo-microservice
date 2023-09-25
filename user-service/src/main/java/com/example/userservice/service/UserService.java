package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.response.RoleResponse;
import com.example.userservice.response.UserResponse;
import com.example.userservice.statics.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserDto userDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(userDto.getUsername());
        String password = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(password);
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

        return responseUser(user);
    }

    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        List<RoleResponse> roles = new ArrayList<>();

        user.getRoles().forEach(role -> {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleType(role.getRoleType());
            roles.add(roleResponse);
        });

        userResponse.setRoles(roles);
        return userResponse;
    }


    public UserResponse responseUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        List<RoleResponse> roles = new ArrayList<>();

        user.getRoles().forEach(role -> {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleType(role.getRoleType());
            roles.add(roleResponse);
        });

        userResponse.setRoles(roles);
        return userResponse;
    }
}
