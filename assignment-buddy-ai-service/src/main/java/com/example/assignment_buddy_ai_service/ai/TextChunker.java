package com.example.assignment_buddy_ai_service.ai;

import java.util.Arrays;
import java.util.List;

public class TextChunker {

    public static List<String> chunkText(String text) {
        if (text == null || text.isBlank()) return List.of();

        return Arrays.stream(text.split("\\n\\n+"))
                .map(String::trim)
                .filter(p -> p.length() > 50)
                .toList();
    }
}
