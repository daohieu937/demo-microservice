package com.example.userservice.response;

import com.example.userservice.statics.RoleType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class RoleResponse {
    private RoleType roleType;
}
