package com.example.assignment_buddy_pdf_service.controller;


import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import com.example.assignment_buddy_pdf_service.service.UploadedFileServiceDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class UploadedFileController {

    private final UploadedFileServiceDetail uploadedFileService;

    public UploadedFileController(UploadedFileServiceDetail uploadedFileService) {
        this.uploadedFileService = uploadedFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam String userId) throws IOException {
        UploadedFileDocument savedFile = uploadedFileService.storeFile(file, userId);
        return ResponseEntity.ok(savedFile);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<UploadedFileDocument>> getAllFiles(
            @PathVariable String userId) {
        return ResponseEntity.ok(uploadedFileService.getAllFilesByUser(userId));
    }

    // AI-SERVICE calls this via Eureka: GET http://PDF-SERVICE/api/pdf/text/{id}
    @GetMapping("/text/{id}")
    public ResponseEntity<String> getText(@PathVariable String id) {
        return ResponseEntity.ok(uploadedFileService.getExtractedTextById(id));
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteFile(@PathVariable String id) {
//        uploadedFileService.deleteFile(id);
//        return ResponseEntity.ok("File deleted successfully");
//    }
}