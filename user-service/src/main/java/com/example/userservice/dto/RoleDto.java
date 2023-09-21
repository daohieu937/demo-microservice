package com.example.userservice.dto;

import com.example.userservice.statics.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RoleDto {
    private RoleType roleType;
}
