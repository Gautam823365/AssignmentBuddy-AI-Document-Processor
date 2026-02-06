package com.example.assignment_buddy_pdf_service.controller;


import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import com.example.assignment_buddy_pdf_service.service.UploadedFileServiceDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class UploadedFileController {

    private final UploadedFileServiceDetail uploadedFileService;

    public UploadedFileController(UploadedFileServiceDetail uploadedFileService) {
        this.uploadedFileService = uploadedFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {

        UploadedFileDocument savedFile = uploadedFileService.storeFile(file);

        return ResponseEntity.ok(savedFile);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UploadedFileDocument>> getAllFiles() {
        List<UploadedFileDocument> files = uploadedFileService.getAllFiles();
        return ResponseEntity.ok(files);
    }
    @GetMapping("/text/{id}")
    public ResponseEntity<String> getText(@PathVariable String id) {
        String text = uploadedFileService.getExtractedTextById(id);
        return ResponseEntity.ok(text);
    }


}
