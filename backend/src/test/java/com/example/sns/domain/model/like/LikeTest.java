package com.example.sns.domain.model.like;

import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LikeTest {

    @Test
    @DisplayName("有効なパラメータでいいねを生成できる")
    void shouldCreateLike() {
        UserId userId = new UserId("user-001");
        PostId postId = new PostId("post-001");
        LocalDateTime now = LocalDateTime.now();

        Like like = new Like(userId, postId, now);

        assertEquals(userId, like.getUserId());
        assertEquals(postId, like.getPostId());
        assertEquals(now, like.getCreatedAt());
    }

    @Test
    @DisplayName("必須パラメータがnullの場合にNullPointerExceptionが発生")
    void shouldThrowWhenRequiredFieldsAreNull() {
        UserId userId = new UserId("user-001");
        PostId postId = new PostId("post-001");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(NullPointerException.class, () -> new Like(null, postId, now));
        assertThrows(NullPointerException.class, () -> new Like(userId, null, now));
        assertThrows(NullPointerException.class, () -> new Like(userId, postId, null));
    }

    @Test
    @DisplayName("同じuserId-postIdの組み合わせは等しい")
    void shouldBeEqualWithSamePair() {
        UserId userId = new UserId("user-001");
        PostId postId = new PostId("post-001");
        Like like1 = new Like(userId, postId, LocalDateTime.now());
        Like like2 = new Like(userId, postId, LocalDateTime.now().plusHours(1));
        assertEquals(like1, like2);
    }
}
