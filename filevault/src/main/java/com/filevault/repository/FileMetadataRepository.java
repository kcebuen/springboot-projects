package com.filevault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filevault.model.FileMetadata;
import com.filevault.model.User;
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByOwner(User user);
}
