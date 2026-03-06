package com.example.sns.infrastructure.repository;

import com.example.sns.domain.model.post.Post;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 投稿リポジトリのインメモリ実装。
 */
@Repository
public class InMemoryPostRepository implements PostRepository {

    private final Map<PostId, Post> store = new ConcurrentHashMap<>();

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Post> findByUserId(UserId userId) {
        return store.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findByUserIdIn(List<UserId> userIds) {
        return store.values().stream()
                .filter(p -> userIds.contains(p.getUserId()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(PostId id) {
        store.remove(id);
    }

    @Override
    public long countByUserId(UserId userId) {
        return store.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .count();
    }
}
