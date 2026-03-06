package com.example.sns.application.service;

import com.example.sns.domain.model.user.*;
import com.example.sns.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ユーザー管理に関するユースケースを提供するサービス。
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新規ユーザーを登録する。
     */
    public User register(String username, String email, String password) {
        Username usernameVo = new Username(username);
        Email emailVo = new Email(email);

        if (userRepository.existsByUsername(usernameVo)) {
            throw new IllegalStateException("Username already exists: " + username);
        }
        if (userRepository.existsByEmail(emailVo)) {
            throw new IllegalStateException("Email already exists: " + email);
        }

        // 簡易的なパスワードハッシュ（本番では BCrypt 等を使用）
        String passwordHash = Integer.toHexString(password.hashCode());

        User user = new User(
                new UserId(UUID.randomUUID().toString()),
                usernameVo,
                emailVo,
                passwordHash,
                username,
                null,
                LocalDateTime.now()
        );

        return userRepository.save(user);
    }

    /**
     * ユーザーIDでユーザーを取得する。
     */
    public User findById(String userId) {
        return userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    /**
     * プロフィールを更新する。
     */
    public User updateProfile(String userId, String displayName, String bio) {
        User user = findById(userId);
        user.updateProfile(displayName, bio);
        return userRepository.save(user);
    }

    /**
     * ログイン（簡易認証）。
     */
    public User login(String email, String password) {
        Email emailVo = new Email(email);
        User user = userRepository.findByEmail(emailVo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String passwordHash = Integer.toHexString(password.hashCode());
        if (!user.getPasswordHash().equals(passwordHash)) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }
}
