package com.example.assignment_buddy_pdf_service.service;



import com.example.assignment_buddy_pdf_service.model.UploadedFileDocument;
import com.example.assignment_buddy_pdf_service.repository.UploadedFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UploadedFileServiceDetail {

    private final UploadedFileRepository repository;
    private final FileExtractionService fileExtractionService;

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads";

    public UploadedFileServiceDetail(UploadedFileRepository repository,
                                     FileExtractionService fileExtractionService) {
        this.repository = repository;
        this.fileExtractionService = fileExtractionService;
    }

    private String detectType(String contentType) {
        if (contentType == null) return "UNKNOWN";
        if (contentType.contains("pdf")) return "PDF";
      //  if (contentType.contains("audio")) return "AUDIO";
        //if (contentType.contains("video")) return "VIDEO";
        return "UNKNOWN";
    }

    public UploadedFileDocument storeFile(MultipartFile file) throws IOException {

        // ✅ create absolute upload dir
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // ✅ absolute file path
        String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        String type = detectType(file.getContentType());

        String extractedText =
                fileExtractionService.extractText(filePath, type);

        UploadedFileDocument document = new UploadedFileDocument();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(type);
        document.setFilePath(filePath);
        document.setExtractedText(extractedText);

        return repository.save(document);
    }
    public String getExtractedTextById(String id) {
        UploadedFileDocument doc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return doc.getExtractedText();
    }

    public List<UploadedFileDocument> getAllFiles() {
        return repository.findAll();
    }


}
