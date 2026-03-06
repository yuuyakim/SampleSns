package com.example.sns.infrastructure.repository;

import com.example.sns.domain.model.follow.Follow;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.FollowRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * フォローリポジトリのインメモリ実装。
 */
@Repository
public class InMemoryFollowRepository implements FollowRepository {

    // Key: "followerId:followeeId"
    private final Map<String, Follow> store = new ConcurrentHashMap<>();

    private String toKey(UserId followerId, UserId followeeId) {
        return followerId.getValue() + ":" + followeeId.getValue();
    }

    @Override
    public Follow save(Follow follow) {
        store.put(toKey(follow.getFollowerId(), follow.getFolloweeId()), follow);
        return follow;
    }

    @Override
    public void delete(UserId followerId, UserId followeeId) {
        store.remove(toKey(followerId, followeeId));
    }

    @Override
    public Optional<Follow> findByFollowerIdAndFolloweeId(UserId followerId, UserId followeeId) {
        return Optional.ofNullable(store.get(toKey(followerId, followeeId)));
    }

    @Override
    public List<Follow> findByFollowerId(UserId followerId) {
        return store.values().stream()
                .filter(f -> f.getFollowerId().equals(followerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Follow> findByFolloweeId(UserId followeeId) {
        return store.values().stream()
                .filter(f -> f.getFolloweeId().equals(followeeId))
                .collect(Collectors.toList());
    }

    @Override
    public long countByFollowerId(UserId followerId) {
        return store.values().stream()
                .filter(f -> f.getFollowerId().equals(followerId))
                .count();
    }

    @Override
    public long countByFolloweeId(UserId followeeId) {
        return store.values().stream()
                .filter(f -> f.getFolloweeId().equals(followeeId))
                .count();
    }

    @Override
    public boolean exists(UserId followerId, UserId followeeId) {
        return store.containsKey(toKey(followerId, followeeId));
    }
}
