package com.example.assignment_buddy_ai_service.ai;

import com.example.assignment_buddy_ai_service.model.ScoredChunk;
import com.example.assignment_buddy_ai_service.service.EmbeddingService;

import java.util.*;
import java.util.stream.Collectors;

public class KeywordAISearch {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "the", "is", "are", "was", "were", "and", "or", "but", "in", "on",
            "at", "to", "for", "of", "a", "an", "that", "this", "it", "with",
            "as", "be", "been", "being", "have", "has", "had", "do", "does",
            "did", "will", "would", "could", "should", "may", "might", "can",
            "what", "who", "when", "where", "how", "why", "which", "i", "you",
            "he", "she", "we", "they", "me", "him", "her", "us", "them", "tell",
            "give", "explain", "describe", "list", "show", "find", "get", "about"
    ));


                // THIS IS NORMAL RAG USED

//    public static String findBestAnswer(List<String> chunks, String question) {
//
//        if (chunks == null || chunks.isEmpty() || question == null || question.isBlank()) {
//            return "";
//        }
//
//        // Step 1: Split chunks into sentences
//        List<String> sentences = splitIntoSentences(chunks);
//
//        // Step 2: Extract keywords
//        List<String> keywords = extractKeywords(question);
//
//        if (keywords.isEmpty()) {
//            return "";
//        }
//
//        // Step 3: Score sentences
//        List<ScoredChunk> scored = new ArrayList<>();
//
//        for (String sentence : sentences) {
//            double score = scoreSentence(sentence, keywords, question);
//            if (score > 0) {
//                scored.add(new ScoredChunk(sentence, score));
//            }
//        }
//
//        if (scored.isEmpty()) {
//            return "";
//        }
//
//        // Step 4: Sort by score (highest first)
//        scored.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
//
//        // Step 5: Take top sentences as context
//        StringBuilder context = new StringBuilder();
//        int maxSentences = 8;
//
//        for (int i = 0; i < Math.min(maxSentences, scored.size()); i++) {
//            context.append(scored.get(i).getChunk()).append(" ");
//        }
//
//        return context.toString().trim();
//    }

    // ── Split all chunks into clean sentences ────────────────────────────────
    private static List<String> splitIntoSentences(List<String> chunks) {
        List<String> sentences = new ArrayList<>();
        for (String chunk : chunks) {
            // Split on . ! ? followed by space or newline
            String[] parts = chunk.split("(?<=[.!?])\\s+|\\n+|•|-");
            for (String part : parts) {
                String clean = part.trim();
                if (clean.length() > 20) { // ignore tiny fragments
                    sentences.add(clean);
                }
                if(clean.endsWith("?")){
                    continue;
                }
            }
        }
        return sentences;
    }

    // ── Extract meaningful words from question ───────────────────────────────
    private static List<String> extractKeywords(String question) {
        return Arrays.stream(
                        question.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+"))
                .filter(w -> w.length() > 2 && !STOP_WORDS.contains(w))
                .distinct()
                .collect(Collectors.toList());
    }

    // ── Score a single sentence against keywords ─────────────────────────────
    private static double scoreSentence(String sentence, List<String> keywords, String question) {
        String lower = sentence.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        double score = 0;

        for (String keyword : keywords) {
            if (lower.matches(".*\\b" + keyword + "\\b.*")) {
                score += 3.0; // exact word match
            } else if (lower.contains(keyword)) {
                score += 1.0; // partial match
            }
        }
        if(sentence.contains(";") || sentence.contains("{") || sentence.contains("}")){
            return 0;
        }
        if(!sentence.toLowerCase().contains(keywords.get(0))){
            return 0;
        }

        // Bonus for consecutive phrase match (2 words in a row from question)
        String[] qWords = question.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        for (int i = 0; i < qWords.length - 1; i++) {
            if (lower.contains(qWords[i] + " " + qWords[i + 1])) {
                score += 2.0;
            }
        }

        // Prefer medium-length sentences (not too short, not a wall of text)
        int len = sentence.trim().length();
        if (len < 30)  score *= 0.4;
        if (len > 300) score *= 0.7;

        return score;
    }

    // ── Build a concise, formatted answer from top sentences ─────────────────
    private static String buildAnswer(List<ScoredChunk> scored, List<String> keywords) {
        // Take top 4 sentences max, stop if we exceed ~400 chars
        List<String> selected = new ArrayList<>();
        int totalLength = 0;

        for (ScoredChunk sc : scored) {
            if (selected.size() >= 4) break;
            if (totalLength > 400) break;
            selected.add(sc.getChunk());
            totalLength += sc.getChunk().length();
        }

        String body = String.join(" ", selected).trim();

        // Capitalize first letter, ensure ends with punctuation
        if (!body.isEmpty()) {
            body = Character.toUpperCase(body.charAt(0)) + body.substring(1);
            if (!body.endsWith(".") && !body.endsWith("!") && !body.endsWith("?")) {
                body += ".";
            }
        }

        return body;
    }

    // ── Internal model ───────────────────────────────────────────────────────

}