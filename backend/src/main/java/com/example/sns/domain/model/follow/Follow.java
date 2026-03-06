package com.example.sns.domain.model.follow;

import com.example.sns.domain.model.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * フォロー関係を表すエンティティ。
 */
public class Follow {

    private final UserId followerId;
    private final UserId followeeId;
    private final LocalDateTime createdAt;

    public Follow(UserId followerId, UserId followeeId, LocalDateTime createdAt) {
        this.followerId = Objects.requireNonNull(followerId, "followerId must not be null");
        this.followeeId = Objects.requireNonNull(followeeId, "followeeId must not be null");
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    // Getters
    public UserId getFollowerId() { return followerId; }
    public UserId getFolloweeId() { return followeeId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(followerId, follow.followerId) &&
                Objects.equals(followeeId, follow.followeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followeeId);
    }
}
