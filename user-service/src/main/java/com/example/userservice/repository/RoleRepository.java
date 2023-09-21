package com.example.userservice.repository;

import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.statics.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleType(RoleType roleType);
}
