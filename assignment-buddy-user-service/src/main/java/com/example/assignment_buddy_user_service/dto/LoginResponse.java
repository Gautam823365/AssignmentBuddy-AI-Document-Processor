package com.example.assignment_buddy_user_service.dto;



public class LoginResponse {

    private String accessToken;
    private String id;
    private String name;
    private String email;

    public LoginResponse(String accessToken, String id, String name, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
