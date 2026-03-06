package com.example.sns.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    @DisplayName("有効な値でUserIdを生成できる")
    void shouldCreateWithValidValue() {
        UserId userId = new UserId("user-123");
        assertEquals("user-123", userId.getValue());
    }

    @Test
    @DisplayName("nullでUserIdを生成するとIllegalArgumentExceptionが発生")
    void shouldThrowExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> new UserId(null));
    }

    @Test
    @DisplayName("空文字でUserIdを生成するとIllegalArgumentExceptionが発生")
    void shouldThrowExceptionWhenBlank() {
        assertThrows(IllegalArgumentException.class, () -> new UserId("  "));
    }

    @Nested
    @DisplayName("等価性テスト")
    class EqualityTest {
        @Test
        @DisplayName("同じ値のUserIdは等しい")
        void shouldBeEqualWithSameValue() {
            UserId id1 = new UserId("user-123");
            UserId id2 = new UserId("user-123");
            assertEquals(id1, id2);
            assertEquals(id1.hashCode(), id2.hashCode());
        }

        @Test
        @DisplayName("異なる値のUserIdは等しくない")
        void shouldNotBeEqualWithDifferentValue() {
            UserId id1 = new UserId("user-123");
            UserId id2 = new UserId("user-456");
            assertNotEquals(id1, id2);
        }
    }
}
