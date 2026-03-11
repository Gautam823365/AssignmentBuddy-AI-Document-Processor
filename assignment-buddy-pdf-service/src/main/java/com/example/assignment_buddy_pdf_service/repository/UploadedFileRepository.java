package com.example.assignment_buddy_pdf_service.repository;

import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UploadedFileRepository
        extends MongoRepository<UploadedFileDocument, String> {
    List<UploadedFileDocument> findByUploadedBy(String uploadedBy);
}
