package com.example.assignment_buddy_ai_service.service;


import com.example.assignment_buddy_ai_service.ai.KeywordAISearch;
import com.example.assignment_buddy_ai_service.ai.TextChunker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AIDocumentService {

    private final RestTemplate restTemplate;

    public AIDocumentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String askQuestion(String fileId, String question) {

        // 🔥 Call PDF-SERVICE via Eureka
        String extractedText = restTemplate.getForObject(
                "http://PDF-SERVICE/pdf/text/" + fileId,
                String.class
        );


        List<String> chunks = TextChunker.chunkText(extractedText);

        return KeywordAISearch.findBestAnswer(chunks, question);
    }
}
