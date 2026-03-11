package com.example.assignment_buddy_ai_service.service;

import com.example.assignment_buddy_ai_service.ai.VectorSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LLMService {

    public static String askLLM(List<String> chunks, String question) throws Exception {
        // Step 1: Get best chunks
        List<String> bestChunks = VectorSearch.getBestChunks(chunks, question);
        String context = String.join("\n", bestChunks);

        // Step 2: Build prompt
        String prompt = """
                You are a Java interview expert.

                Using the context below, explain the answer clearly in 4-5 sentences.
                Do not copy the context text directly. Rewrite it as a clear explanation.
                Only give Answer of question.
                Context:
                %s

                Question:
                %s

                Answer:
                """.formatted(context, question);

        // Step 3: Build JSON payload safely using ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();
        payload.put("model", "phi3:mini");
        payload.put("prompt", prompt); // handles quotes/newlines automatically
        payload.put("stream", false);

        String jsonPayload = mapper.writeValueAsString(payload);

        // Step 4: Send HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Step 5: Parse JSON response safely
        JsonNode node = mapper.readTree(response.body());

        // Step 6: Check for error
        if (node.has("error")) {
            throw new RuntimeException("LLM returned error: " + node.get("error").asText());
        }

        JsonNode respNode = node.get("response");
        if (respNode == null || respNode.isNull()) {
            throw new RuntimeException("Invalid LLM response: " + response.body());
        }

        // Step 7: Return text safely
        return respNode.asText();
    }
}