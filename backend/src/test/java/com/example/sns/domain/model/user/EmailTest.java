package com.example.sns.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("有効なメールアドレスでEmailを生成できる")
    void shouldCreateWithValidEmail() {
        Email email = new Email("test@example.com");
        assertEquals("test@example.com", email.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"user@domain.com", "user.name@domain.co.jp", "user+tag@domain.org"})
    @DisplayName("有効なメール形式を受け付ける")
    void shouldAcceptValidEmails(String value) {
        assertDoesNotThrow(() -> new Email(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "@domain.com", "user@", "user@.com", ""})
    @DisplayName("無効なメール形式を拒否する")
    void shouldRejectInvalidEmails(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Email(value));
    }

    @Test
    @DisplayName("nullのメールアドレスを拒否する")
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    @DisplayName("同じ値のEmailは等しい")
    void shouldBeEqualWithSameValue() {
        Email e1 = new Email("test@example.com");
        Email e2 = new Email("test@example.com");
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
