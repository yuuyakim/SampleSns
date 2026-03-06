package com.example.sns.domain.model.follow;

import com.example.sns.domain.model.user.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FollowTest {

    @Test
    @DisplayName("有効なパラメータでフォローを生成できる")
    void shouldCreateFollow() {
        UserId follower = new UserId("user-001");
        UserId followee = new UserId("user-002");
        LocalDateTime now = LocalDateTime.now();

        Follow follow = new Follow(follower, followee, now);

        assertEquals(follower, follow.getFollowerId());
        assertEquals(followee, follow.getFolloweeId());
        assertEquals(now, follow.getCreatedAt());
    }

    @Test
    @DisplayName("自分自身をフォローするとIllegalArgumentExceptionが発生")
    void shouldThrowWhenFollowSelf() {
        UserId userId = new UserId("user-001");
        assertThrows(IllegalArgumentException.class,
                () -> new Follow(userId, userId, LocalDateTime.now()));
    }

    @Test
    @DisplayName("必須パラメータがnullの場合にNullPointerExceptionが発生")
    void shouldThrowWhenRequiredFieldsAreNull() {
        UserId id = new UserId("user-001");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(NullPointerException.class, () -> new Follow(null, id, now));
        assertThrows(NullPointerException.class, () -> new Follow(id, null, now));
        assertThrows(NullPointerException.class, () -> new Follow(id, new UserId("user-002"), null));
    }

    @Test
    @DisplayName("同じfollowerId-followeeIdの組み合わせは等しい")
    void shouldBeEqualWithSamePair() {
        UserId f1 = new UserId("user-001");
        UserId f2 = new UserId("user-002");
        Follow follow1 = new Follow(f1, f2, LocalDateTime.now());
        Follow follow2 = new Follow(f1, f2, LocalDateTime.now().plusHours(1));
        assertEquals(follow1, follow2);
    }
}
