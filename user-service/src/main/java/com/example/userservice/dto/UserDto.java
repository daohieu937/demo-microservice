package com.example.userservice.dto;

import com.example.userservice.statics.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UserDto {
//    private UUID id = UUID.randomUUID();
    private String username;
    private String password;
    private List<RoleType> roleDto;
}
