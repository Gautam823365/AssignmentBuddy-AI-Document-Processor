package com.example.assignment_buddy_ai_service.controller;


import com.example.assignment_buddy_ai_service.dto.AskRequest;
import com.example.assignment_buddy_ai_service.service.AIDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIDocumentService aiService;

    public AIController(AIDocumentService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody AskRequest request) {
        String answer = aiService.askQuestion(
                request.getId(),
                request.getQuestion()
        );
        return ResponseEntity.ok(Map.of("answer", answer));
    }

}
