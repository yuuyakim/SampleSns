package com.example.sns.domain.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UserId userId;
    private Username username;
    private Email email;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        userId = new UserId("user-001");
        username = new Username("testuser");
        email = new Email("test@example.com");
        now = LocalDateTime.of(2026, 1, 1, 0, 0);
    }

    @Nested
    @DisplayName("ユーザー生成テスト")
    class CreationTest {
        @Test
        @DisplayName("有効なパラメータでユーザーを生成できる")
        void shouldCreateUser() {
            User user = new User(userId, username, email, "hashedpw", "Test User", "Hello!", now);

            assertEquals(userId, user.getId());
            assertEquals(username, user.getUsername());
            assertEquals(email, user.getEmail());
            assertEquals("hashedpw", user.getPasswordHash());
            assertEquals("Test User", user.getDisplayName());
            assertEquals("Hello!", user.getBio());
            assertEquals(now, user.getCreatedAt());
        }

        @Test
        @DisplayName("必須パラメータがnullの場合にNullPointerExceptionが発生")
        void shouldThrowWhenRequiredFieldsAreNull() {
            assertThrows(NullPointerException.class,
                    () -> new User(null, username, email, "pw", "name", "bio", now));
            assertThrows(NullPointerException.class,
                    () -> new User(userId, null, email, "pw", "name", "bio", now));
            assertThrows(NullPointerException.class,
                    () -> new User(userId, username, null, "pw", "name", "bio", now));
            assertThrows(NullPointerException.class,
                    () -> new User(userId, username, email, null, "name", "bio", now));
            assertThrows(NullPointerException.class,
                    () -> new User(userId, username, email, "pw", "name", "bio", null));
        }

        @Test
        @DisplayName("bioが160文字を超える場合にIllegalArgumentExceptionが発生")
        void shouldThrowWhenBioTooLong() {
            String longBio = "a".repeat(161);
            assertThrows(IllegalArgumentException.class,
                    () -> new User(userId, username, email, "pw", "name", longBio, now));
        }

        @Test
        @DisplayName("bioが160文字以内の場合は正常に生成できる")
        void shouldAcceptBioWithin160Chars() {
            String bio = "a".repeat(160);
            assertDoesNotThrow(
                    () -> new User(userId, username, email, "pw", "name", bio, now));
        }
    }

    @Nested
    @DisplayName("プロフィール更新テスト")
    class UpdateProfileTest {
        @Test
        @DisplayName("プロフィールを正常に更新できる")
        void shouldUpdateProfile() {
            User user = new User(userId, username, email, "pw", "Old Name", "Old Bio", now);
            user.updateProfile("New Name", "New Bio");

            assertEquals("New Name", user.getDisplayName());
            assertEquals("New Bio", user.getBio());
        }

        @Test
        @DisplayName("更新時にbioが160文字を超える場合にエラー")
        void shouldThrowWhenUpdatedBioTooLong() {
            User user = new User(userId, username, email, "pw", "Name", "Bio", now);
            assertThrows(IllegalArgumentException.class,
                    () -> user.updateProfile("Name", "a".repeat(161)));
        }
    }

    @Nested
    @DisplayName("等価性テスト")
    class EqualityTest {
        @Test
        @DisplayName("同じIDのユーザーは等しい")
        void shouldBeEqualWithSameId() {
            User u1 = new User(userId, username, email, "pw1", "Name1", "Bio1", now);
            User u2 = new User(userId, new Username("other"), new Email("other@test.com"), "pw2", "Name2", "Bio2", now);
            assertEquals(u1, u2);
        }

        @Test
        @DisplayName("異なるIDのユーザーは等しくない")
        void shouldNotBeEqualWithDifferentId() {
            User u1 = new User(new UserId("id-1"), username, email, "pw", "Name", "Bio", now);
            User u2 = new User(new UserId("id-2"), username, email, "pw", "Name", "Bio", now);
            assertNotEquals(u1, u2);
        }
    }
}
