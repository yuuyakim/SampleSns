package com.example.sns.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {

    @NotBlank(message = "Content is required")
    @Size(max = 140, message = "Content must be 140 characters or less")
    private String content;

    public CreatePostRequest() {}

    public CreatePostRequest(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
