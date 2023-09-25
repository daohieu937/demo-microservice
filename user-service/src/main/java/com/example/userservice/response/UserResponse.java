package com.example.userservice.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Data
@Setter
@Getter
public class UserResponse {
    private String id;
    private String username;
    private Collection<RoleResponse> roles;
}
