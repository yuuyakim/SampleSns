package com.example.sns.presentation.dto;

import com.example.sns.domain.model.user.User;

import java.time.LocalDateTime;

/**
 * ユーザー情報のレスポンスDTO。
 */
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String displayName;
    private String bio;
    private LocalDateTime createdAt;

    public UserResponse() {}

    public static UserResponse fromDomain(User user) {
        UserResponse response = new UserResponse();
        response.id = user.getId().getValue();
        response.username = user.getUsername().getValue();
        response.email = user.getEmail().getValue();
        response.displayName = user.getDisplayName();
        response.bio = user.getBio();
        response.createdAt = user.getCreatedAt();
        return response;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
