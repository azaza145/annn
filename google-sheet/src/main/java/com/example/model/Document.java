package com.example.model;

import lombok.Data;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // Document name (sheet name)

    // Store JSON content as a string (could be handled by a converter if needed)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String jsonContent;

    @Column(nullable = false)
    private LocalDateTime uploadDate; // Date of upload

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @JsonIgnore // Prevents serialization issues when returning Document as JSON
    private User createdBy;  // The user who created the document

    @Column(nullable = false)
    private String type = "JSON"; // Default type is JSON

    // No need for custom getter/setter unless logic is needed
}
