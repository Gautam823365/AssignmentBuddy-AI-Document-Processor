package com.example.assignment_buddy_pdf_service.repository;

import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UploadedFileRepository
        extends MongoRepository<UploadedFileDocument, String> {
}
