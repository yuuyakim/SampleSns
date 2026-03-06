package com.example.sns.domain.model.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostContentTest {

    @Test
    @DisplayName("有効な本文でPostContentを生成できる")
    void shouldCreateWithValidContent() {
        PostContent content = new PostContent("Hello, world!");
        assertEquals("Hello, world!", content.getValue());
    }

    @Test
    @DisplayName("140文字ちょうどの本文を受け付ける")
    void shouldAccept140Chars() {
        String text = "a".repeat(140);
        assertDoesNotThrow(() -> new PostContent(text));
    }

    @Test
    @DisplayName("141文字以上の本文を拒否する")
    void shouldReject141Chars() {
        String text = "a".repeat(141);
        assertThrows(IllegalArgumentException.class, () -> new PostContent(text));
    }

    @Test
    @DisplayName("空文字の本文を拒否する")
    void shouldRejectBlank() {
        assertThrows(IllegalArgumentException.class, () -> new PostContent("   "));
    }

    @Test
    @DisplayName("nullの本文を拒否する")
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> new PostContent(null));
    }

    @Test
    @DisplayName("同じ値のPostContentは等しい")
    void shouldBeEqualWithSameValue() {
        PostContent c1 = new PostContent("Hello");
        PostContent c2 = new PostContent("Hello");
        assertEquals(c1, c2);
    }
}
