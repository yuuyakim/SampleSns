package com.example.sns.presentation.dto;

import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {

    private String displayName;

    @Size(max = 160, message = "Bio must be 160 characters or less")
    private String bio;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String displayName, String bio) {
        this.displayName = displayName;
        this.bio = bio;
    }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
