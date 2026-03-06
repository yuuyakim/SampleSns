package com.example.sns.domain.model.post;

import com.example.sns.domain.model.user.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("有効なパラメータで投稿を生成できる")
    void shouldCreatePost() {
        PostId postId = new PostId("post-001");
        UserId userId = new UserId("user-001");
        PostContent content = new PostContent("Hello, SNS!");
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);

        Post post = new Post(postId, userId, content, now);

        assertEquals(postId, post.getId());
        assertEquals(userId, post.getUserId());
        assertEquals(content, post.getContent());
        assertEquals(now, post.getCreatedAt());
    }

    @Test
    @DisplayName("必須パラメータがnullの場合にNullPointerExceptionが発生")
    void shouldThrowWhenRequiredFieldsAreNull() {
        PostId postId = new PostId("post-001");
        UserId userId = new UserId("user-001");
        PostContent content = new PostContent("Hello");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(NullPointerException.class, () -> new Post(null, userId, content, now));
        assertThrows(NullPointerException.class, () -> new Post(postId, null, content, now));
        assertThrows(NullPointerException.class, () -> new Post(postId, userId, null, now));
        assertThrows(NullPointerException.class, () -> new Post(postId, userId, content, null));
    }

    @Test
    @DisplayName("同じIDの投稿は等しい")
    void shouldBeEqualWithSameId() {
        PostId id = new PostId("post-001");
        Post p1 = new Post(id, new UserId("u1"), new PostContent("A"), LocalDateTime.now());
        Post p2 = new Post(id, new UserId("u2"), new PostContent("B"), LocalDateTime.now());
        assertEquals(p1, p2);
    }
}
