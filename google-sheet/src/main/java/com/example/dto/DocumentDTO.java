package com.example.dto;

import com.example.model.Document;

import java.time.LocalDateTime;

public class DocumentDTO {

    private Long id;
    private String name;
    private String jsonContent;
    private LocalDateTime uploadDate;
    private String type;

    // Optionally include user info, if needed
    private Long createdById;
    private String createdByEmail;

    public DocumentDTO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.jsonContent = document.getJsonContent();
        this.uploadDate = document.getUploadDate();
        this.type = document.getType();
        if (document.getCreatedBy() != null) {
            this.createdById = document.getCreatedBy().getId();
            this.createdByEmail = document.getCreatedBy().getEmail();
        }
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }
}
