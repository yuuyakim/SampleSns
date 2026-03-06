package com.example.sns.domain.repository;

import com.example.sns.domain.model.like.Like;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;

import java.util.Optional;

/**
 * いいねリポジトリのインターフェース。
 */
public interface LikeRepository {

    Like save(Like like);

    void delete(UserId userId, PostId postId);

    Optional<Like> findByUserIdAndPostId(UserId userId, PostId postId);

    long countByPostId(PostId postId);

    boolean exists(UserId userId, PostId postId);
}
