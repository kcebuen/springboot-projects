package com.filevault.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filevault.model.FileMetadata;
import com.filevault.model.User;
import com.filevault.repository.FileMetadataRepository;

@Service
public class FileService {

    private final FileMetadataRepository fileRepo;

    public FileService(FileMetadataRepository fileRepo) {
        this.fileRepo = fileRepo;
    }

    public void uploadFile(MultipartFile file, User user) throws IOException {
        FileMetadata meta = new FileMetadata();
        meta.setFileName(file.getOriginalFilename());
        meta.setContentType(file.getContentType());
        meta.setFileSize(file.getSize());
        meta.setUploadedAt(LocalDateTime.now());
        meta.setData(file.getBytes());
        meta.setOwner(user);
        
        fileRepo.save(meta);
    }

    public List<FileMetadata> getUserFiles(User user) {
        return fileRepo.findByOwner(user);
    }

    public Optional<FileMetadata> getFileById(Long id) {
        return fileRepo.findById(id);
    }

    public List<FileMetadata> getAllFiles() {
    return fileRepo.findAll();
    }

    public void deleteFileById(Long id) {
        fileRepo.deleteById(id);
    }
}
