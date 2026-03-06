package com.example.sns.infrastructure.repository;

import com.example.sns.domain.model.user.Email;
import com.example.sns.domain.model.user.User;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.model.user.Username;
import com.example.sns.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ユーザーリポジトリのインメモリ実装。
 * 将来的にDB実装に差し替え可能。
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<UserId, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return store.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void deleteById(UserId id) {
        store.remove(id);
    }

    @Override
    public boolean existsByUsername(Username username) {
        return store.values().stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(Email email) {
        return store.values().stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }
}
