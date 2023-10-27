package com.example.userservice.media.repository;

import com.example.userservice.media.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findByIdIn(List<UUID> ids);
}
