package com.example.controller;

import com.example.dto.DocumentDTO;
import com.example.model.Document;
import com.example.model.User;
import com.example.service.DocumentService;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;

    public DocumentController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    // Upload a document (Super Admin)
    @PostMapping("/add")
    public ResponseEntity<DocumentDTO> addDocument(@RequestBody Document document, @RequestParam Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        document.setCreatedBy(user);  // Set the creator of the document
        document.setUploadDate(LocalDateTime.now());  // Set the upload date
        document.setType("JSON");  // Default type is JSON
        Document savedDocument = documentService.saveDocument(document);
        return ResponseEntity.ok(new DocumentDTO(savedDocument));
    }

    // Get all documents
    @GetMapping("/all")
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        List<DocumentDTO> documentDTOs = documents.stream()
                .map(DocumentDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documentDTOs);
    }

    // Get a single document by ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentService.getDocumentById(id);
        return document.map(d -> ResponseEntity.ok(new DocumentDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing document
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable Long id, @RequestBody Document documentDetails) {
        Optional<Document> documentOptional = documentService.getDocumentById(id);
        if (documentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Document document = documentOptional.get();
        document.setName(documentDetails.getName());
        document.setJsonContent(documentDetails.getJsonContent());
        document.setType(documentDetails.getType());  // Update the type if necessary
        Document updatedDocument = documentService.saveDocument(document);
        return ResponseEntity.ok(new DocumentDTO(updatedDocument));
    }

    // Delete a document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // Get the content of the document (Consult content)
    @GetMapping("/{id}/content")
    public ResponseEntity<String> getDocumentContent(@PathVariable Long id) {
        Optional<Document> document = documentService.getDocumentById(id);
        return document.map(d -> ResponseEntity.ok(d.getJsonContent()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
