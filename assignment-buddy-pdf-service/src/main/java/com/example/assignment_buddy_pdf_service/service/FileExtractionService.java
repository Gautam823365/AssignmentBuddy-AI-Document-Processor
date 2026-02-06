package com.example.assignment_buddy_pdf_service.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class FileExtractionService {
   // private final WhisperTranscriptionService whisperService;

//    public FileExtractionService(WhisperTranscriptionService whisperService) {
//        this.whisperService = whisperService;
//    }

    public String extractText(String filePath, String fileType) {

        switch (fileType) {
            case "PDF":
                return extractFromPdf(filePath);

//            case "AUDIO":
//                return whisperService.transcribe(new File(filePath));
//
//            case "VIDEO":
//                File audio = extractAudioFromVideo(filePath);
//                return whisperService.transcribe(audio);

            default:
                throw new RuntimeException("Unsupported file type");
        }
    }

    private String extractFromPdf(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException("PDF extraction failed", e);
        }
    }

//    private File extractAudioFromVideo(String videoPath) {
//
//        try {
//            String audioPath = videoPath + ".wav";
//
//            ProcessBuilder pb = new ProcessBuilder(
//                    "ffmpeg",
//                    "-i", videoPath,
//                    "-vn",
//                    "-acodec", "pcm_s16le",
//                    "-ar", "16000",
//                    audioPath
//            );
//
//            pb.redirectErrorStream(true);
//            pb.start().waitFor();
//
//            return new File(audioPath);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Video to audio conversion failed", e);
//        }
//    }

}
