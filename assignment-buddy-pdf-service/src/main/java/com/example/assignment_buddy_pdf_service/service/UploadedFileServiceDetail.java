package com.example.assignment_buddy_pdf_service.service;

import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import com.example.assignment_buddy_pdf_service.repository.UploadedFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UploadedFileServiceDetail {

    private final UploadedFileRepository fileRepository;
    private final FileExtractionService extractionService;

    // Folder where uploaded PDFs are saved on disk
    private static final String UPLOAD_DIR = "uploads/";

    public UploadedFileServiceDetail(UploadedFileRepository fileRepository,
                                     FileExtractionService extractionService) {
        this.fileRepository    = fileRepository;
        this.extractionService = extractionService;
    }

    // ── Save uploaded file to disk + MongoDB ─────────────────────────────────
    public UploadedFileDocument storeFile(MultipartFile file, String userId) throws IOException {

        // Create uploads folder if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Save file to disk
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());

        // Save metadata to MongoDB
        UploadedFileDocument doc = new UploadedFileDocument();
        doc.setFileName(file.getOriginalFilename());
        doc.setFilePath(filePath.toString());
        doc.setFileType("PDF");
        doc.setFileSize(file.getSize());
        doc.setUploadedBy(userId);
        doc.setUploadedAt(LocalDateTime.now());

        return fileRepository.save(doc);
    }

    // ── Get all files for a specific user ────────────────────────────────────
    public List<UploadedFileDocument> getAllFilesByUser(String userId) {
        return fileRepository.findByUploadedBy(userId);
    }

    // ── Extract and return text from a PDF by file ID ────────────────────────
    // Called by UploadedFileController GET /api/pdf/text/{id}
    // AI-SERVICE calls this endpoint via Eureka
    public String getExtractedTextById(String fileId) {

        UploadedFileDocument fileDoc = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileId));

        return extractionService.extractText(
                fileDoc.getFilePath(),
                fileDoc.getFileType()
        );
    }
}