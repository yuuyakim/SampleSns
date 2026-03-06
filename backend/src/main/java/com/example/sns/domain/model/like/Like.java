package com.example.sns.domain.model.like;

import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * いいねを表すエンティティ。
 */
public class Like {

    private final UserId userId;
    private final PostId postId;
    private final LocalDateTime createdAt;

    public Like(UserId userId, PostId postId, LocalDateTime createdAt) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.postId = Objects.requireNonNull(postId, "postId must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    // Getters
    public UserId getUserId() { return userId; }
    public PostId getPostId() { return postId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(userId, like.userId) &&
                Objects.equals(postId, like.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
