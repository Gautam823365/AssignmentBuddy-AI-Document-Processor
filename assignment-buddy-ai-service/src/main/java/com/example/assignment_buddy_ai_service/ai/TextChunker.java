package com.example.assignment_buddy_ai_service.ai;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextChunker {

    /**
     * Splits raw PDF text into sentence-level chunks.
     * Sentence-level = precise focused answers.
     * Paragraph-level = dumps entire sections (too much text).
     */
    public static List<String> chunkText(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        // Split on . ! ? followed by space, OR on newlines
        String[] parts = text.split("(?<=[.!?])\\s+|\\n+");

        return Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> s.length() > 20) // skip tiny fragments/headers
                .collect(Collectors.toList());
    }
}