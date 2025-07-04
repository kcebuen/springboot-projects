package com.filevault.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FileMetadata {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public FileMetadata() {}

    public FileMetadata(String fileName, String fileType, Long fileSize, LocalDateTime uploadTime, User user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.user = user;
    }

    // Getters and Setters...
}
