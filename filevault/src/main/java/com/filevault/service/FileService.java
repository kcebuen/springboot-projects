package com.filevault.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.filevault.model.FileMetadata;
import com.filevault.model.User;
import com.filevault.repository.FileMetadataRepository;

@Service
public class FileService {
    private final FileMetadataRepository fileRepo;

    public FileService(FileMetadataRepository fileRepo) {
        this.fileRepo = fileRepo;
    }

    public FileMetadata saveFile(String name, String type, long size, User user) {
        FileMetadata file = new FileMetadata(name, type, size, LocalDateTime.now(), user);
        return fileRepo.save(file);
    }

    public List<FileMetadata> getFilesByUser(User user) {
        return fileRepo.findByUser(user);
    }

    public List<FileMetadata> getAllFiles() {
        return fileRepo.findAll();
    }

    public void deleteFile(Long id) {
        fileRepo.deleteById(id);
    }
}