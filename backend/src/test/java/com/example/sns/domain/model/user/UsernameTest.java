package com.example.sns.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    @DisplayName("有効なユーザー名でUsernameを生成できる")
    void shouldCreateWithValidValue() {
        Username username = new Username("john_doe");
        assertEquals("john_doe", username.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "ABC", "a1b2c3", "user_name_12345678"})
    @DisplayName("有効なパターンのユーザー名を受け付ける")
    void shouldAcceptValidPatterns(String value) {
        assertDoesNotThrow(() -> new Username(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "a", "ab@cd", "user name", "aaaaaaaaaaaaaaaaaaaaa"})
    @DisplayName("無効なパターンのユーザー名を拒否する")
    void shouldRejectInvalidPatterns(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Username(value));
    }

    @Test
    @DisplayName("nullのユーザー名を拒否する")
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> new Username(null));
    }

    @Test
    @DisplayName("同じ値のUsernameは等しい")
    void shouldBeEqualWithSameValue() {
        Username u1 = new Username("john_doe");
        Username u2 = new Username("john_doe");
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
