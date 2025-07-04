package com.filevault.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String role; // "ROLE_USER" or "ROLE_ADMIN"

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FileMetadata> files;

    // Constructors
    public User() {}
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters...
}
