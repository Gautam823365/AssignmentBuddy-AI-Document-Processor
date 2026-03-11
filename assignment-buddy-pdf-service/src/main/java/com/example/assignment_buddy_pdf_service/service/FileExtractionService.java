package com.example.assignment_buddy_pdf_service.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileExtractionService {

    public String extractText(String filePath, String fileType) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be empty");
        }

        switch (fileType.toUpperCase()) {
            case "PDF":
                return extractFromPdf(filePath);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }

    private String extractFromPdf(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            if (text == null || text.isBlank()) {
                throw new RuntimeException("PDF appears to be empty or contains no extractable text: " + filePath);
            }

            return text.trim();
        } catch (RuntimeException e) {
            throw e; // re-throw our own exceptions as-is
        } catch (Exception e) {
            throw new RuntimeException("PDF extraction failed for: " + filePath, e);
        }
    }
}