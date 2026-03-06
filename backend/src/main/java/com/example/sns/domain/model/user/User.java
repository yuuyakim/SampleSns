package com.example.sns.domain.model.user;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ユーザーエンティティ。
 */
public class User {

    private final UserId id;
    private final Username username;
    private final Email email;
    private final String passwordHash;
    private String displayName;
    private String bio;
    private final LocalDateTime createdAt;

    public User(UserId id, Username username, Email email, String passwordHash,
                String displayName, String bio, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash must not be null");
        this.displayName = displayName;
        this.bio = validateBio(bio);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    private String validateBio(String bio) {
        if (bio != null && bio.length() > 160) {
            throw new IllegalArgumentException("Bio must be 160 characters or less");
        }
        return bio;
    }

    public void updateProfile(String displayName, String bio) {
        this.displayName = displayName;
        this.bio = validateBio(bio);
    }

    // Getters
    public UserId getId() { return id; }
    public Username getUsername() { return username; }
    public Email getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
