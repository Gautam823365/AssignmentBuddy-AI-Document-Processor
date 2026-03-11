package com.example.assignment_buddy_ai_service.model;


public class ScoredChunk {
    private String chunk;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getChunk() {
        return chunk;
    }

    public void setChunk(String chunk) {
        this.chunk = chunk;
    }

    private double score;

    public ScoredChunk(String chunk, double score) {
        this.chunk = chunk;
        this.score = score;
    }
}