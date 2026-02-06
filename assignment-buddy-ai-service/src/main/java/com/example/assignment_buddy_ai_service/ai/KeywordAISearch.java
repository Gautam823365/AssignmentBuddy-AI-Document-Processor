package com.example.assignment_buddy_ai_service.ai;

import java.util.List;

public class KeywordAISearch {

    public static String findBestAnswer(List<String> chunks, String question) {

        String[] keywords = question.toLowerCase().split("\\s+");

        String bestMatch = "";
        int maxScore = 0;

        for (String chunk : chunks) {
            int score = 0;
            String lowerChunk = chunk.toLowerCase();

            for (String word : keywords) {
                if (word.length() > 3 && lowerChunk.contains(word)) {
                    score++;
                }
            }

            if (score > maxScore) {
                maxScore = score;
                bestMatch = chunk;
            }
        }

        return maxScore == 0
                ? "Sorry, answer not found in the document."
                : bestMatch;
    }
}
