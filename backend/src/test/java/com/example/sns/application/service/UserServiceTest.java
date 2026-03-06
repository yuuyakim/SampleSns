package com.example.sns.application.service;

import com.example.sns.domain.model.user.Email;
import com.example.sns.domain.model.user.User;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.model.user.Username;
import com.example.sns.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("register")
    class RegisterTest {

        @Test
        @DisplayName("正常にユーザーを登録できる")
        void shouldRegisterUser() {
            when(userRepository.existsByUsername(any())).thenReturn(false);
            when(userRepository.existsByEmail(any())).thenReturn(false);
            when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            User user = userService.register("testuser", "test@example.com", "password123");

            assertNotNull(user);
            assertEquals("testuser", user.getUsername().getValue());
            assertEquals("test@example.com", user.getEmail().getValue());
            verify(userRepository).save(any(User.class));
        }

        @Test
        @DisplayName("既存ユーザー名の場合はIllegalStateExceptionが発生")
        void shouldThrowWhenUsernameExists() {
            when(userRepository.existsByUsername(any())).thenReturn(true);

            assertThrows(IllegalStateException.class,
                    () -> userService.register("existing", "new@example.com", "password123"));
        }

        @Test
        @DisplayName("既存メールアドレスの場合はIllegalStateExceptionが発生")
        void shouldThrowWhenEmailExists() {
            when(userRepository.existsByUsername(any())).thenReturn(false);
            when(userRepository.existsByEmail(any())).thenReturn(true);

            assertThrows(IllegalStateException.class,
                    () -> userService.register("newuser", "existing@example.com", "password123"));
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTest {
        @Test
        @DisplayName("存在するユーザーを取得できる")
        void shouldFindUser() {
            User mockUser = new User(new UserId("user-1"), new Username("testuser"),
                    new Email("test@example.com"), "pw", "Test", null, LocalDateTime.now());
            when(userRepository.findById(new UserId("user-1"))).thenReturn(Optional.of(mockUser));

            User found = userService.findById("user-1");
            assertEquals("testuser", found.getUsername().getValue());
        }

        @Test
        @DisplayName("存在しないユーザーの場合はIllegalArgumentExceptionが発生")
        void shouldThrowWhenNotFound() {
            when(userRepository.findById(any())).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> userService.findById("nonexistent"));
        }
    }

    @Nested
    @DisplayName("login")
    class LoginTest {
        @Test
        @DisplayName("正しい認証情報でログインできる")
        void shouldLoginWithValidCredentials() {
            String password = "password123";
            String hash = Integer.toHexString(password.hashCode());
            User mockUser = new User(new UserId("user-1"), new Username("testuser"),
                    new Email("test@example.com"), hash, "Test", null, LocalDateTime.now());
            when(userRepository.findByEmail(new Email("test@example.com")))
                    .thenReturn(Optional.of(mockUser));

            User loggedIn = userService.login("test@example.com", "password123");
            assertNotNull(loggedIn);
        }

        @Test
        @DisplayName("不正なパスワードでログインするとエラー")
        void shouldThrowWithInvalidPassword() {
            User mockUser = new User(new UserId("user-1"), new Username("testuser"),
                    new Email("test@example.com"), "wronghash", "Test", null, LocalDateTime.now());
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(mockUser));

            assertThrows(IllegalArgumentException.class,
                    () -> userService.login("test@example.com", "password123"));
        }
    }
}
