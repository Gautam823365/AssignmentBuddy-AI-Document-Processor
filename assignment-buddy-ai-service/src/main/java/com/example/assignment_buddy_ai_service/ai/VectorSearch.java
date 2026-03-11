package com.example.assignment_buddy_ai_service.ai;


import com.example.assignment_buddy_ai_service.service.EmbeddingService;

import java.util.*;

public class VectorSearch {

    public static double cosineSimilarity(double[] a, double[] b) {

        double dot = 0;
        double normA = 0;
        double normB = 0;

        for(int i=0;i<a.length;i++){
            dot += a[i]*b[i];
            normA += a[i]*a[i];
            normB += b[i]*b[i];
        }

        return dot/(Math.sqrt(normA)*Math.sqrt(normB));
    }

    public static List<String> getBestChunks(List<String> chunks, String question) throws Exception {

        double[] qVec = EmbeddingService.getEmbedding(question);

        Map<String,Double> scores = new HashMap<>();

        for(String chunk:chunks){

            double[] cVec = EmbeddingService.getEmbedding(chunk);

            double sim = VectorSearch.cosineSimilarity(qVec,cVec);

            scores.put(chunk,sim);
        }

        return scores.entrySet()
                .stream()
                .sorted((a,b)->Double.compare(b.getValue(),a.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
}