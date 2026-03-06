package com.example.sns.domain.model.post;

import com.example.sns.domain.model.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 投稿エンティティ。
 */
public class Post {

    private final PostId id;
    private final UserId userId;
    private final PostContent content;
    private final LocalDateTime createdAt;

    public Post(PostId id, UserId userId, PostContent content, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.content = Objects.requireNonNull(content, "content must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    // Getters
    public PostId getId() { return id; }
    public UserId getUserId() { return userId; }
    public PostContent getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
