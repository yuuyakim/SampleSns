package com.example.sns.domain.repository;

import com.example.sns.domain.model.user.Email;
import com.example.sns.domain.model.user.User;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.model.user.Username;

import java.util.Optional;

/**
 * ユーザーリポジトリのインターフェース。
 * Infrastructure層で実装される。
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByUsername(Username username);

    Optional<User> findByEmail(Email email);

    void deleteById(UserId id);

    boolean existsByUsername(Username username);

    boolean existsByEmail(Email email);
}
