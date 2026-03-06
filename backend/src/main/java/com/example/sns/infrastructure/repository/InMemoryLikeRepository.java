package com.example.sns.infrastructure.repository;

import com.example.sns.domain.model.like.Like;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.LikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * いいねリポジトリのインメモリ実装。
 */
@Repository
public class InMemoryLikeRepository implements LikeRepository {

    // Key: "userId:postId"
    private final Map<String, Like> store = new ConcurrentHashMap<>();

    private String toKey(UserId userId, PostId postId) {
        return userId.getValue() + ":" + postId.getValue();
    }

    @Override
    public Like save(Like like) {
        store.put(toKey(like.getUserId(), like.getPostId()), like);
        return like;
    }

    @Override
    public void delete(UserId userId, PostId postId) {
        store.remove(toKey(userId, postId));
    }

    @Override
    public Optional<Like> findByUserIdAndPostId(UserId userId, PostId postId) {
        return Optional.ofNullable(store.get(toKey(userId, postId)));
    }

    @Override
    public long countByPostId(PostId postId) {
        return store.values().stream()
                .filter(l -> l.getPostId().equals(postId))
                .count();
    }

    @Override
    public boolean exists(UserId userId, PostId postId) {
        return store.containsKey(toKey(userId, postId));
    }
}
