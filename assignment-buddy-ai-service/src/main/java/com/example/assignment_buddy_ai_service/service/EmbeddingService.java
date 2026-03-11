package com.example.assignment_buddy_ai_service.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmbeddingService {

    public static double[] getEmbedding(String text) throws Exception {

        String json = """
        {
          "model": "nomic-embed-text",
          "prompt": "%s"
        }
        """.formatted(text.replace("\"", "\\\""));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/embeddings"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.body());

        JsonNode arr = node.get("embedding");

        double[] vector = new double[arr.size()];

        for(int i=0;i<arr.size();i++){
            vector[i] = arr.get(i).asDouble();
        }

        return vector;
    }
}