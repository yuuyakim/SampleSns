package com.example.sns.application.service;

import com.example.sns.domain.model.like.Like;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.LikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @Nested
    @DisplayName("like")
    class LikeTest {
        @Test
        @DisplayName("正常にいいねできる")
        void shouldLike() {
            when(likeRepository.exists(any(), any())).thenReturn(false);
            when(likeRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            Like like = likeService.like("user-1", "post-1");

            assertNotNull(like);
            verify(likeRepository).save(any(Like.class));
        }

        @Test
        @DisplayName("既にいいね済みの場合はエラー")
        void shouldThrowWhenAlreadyLiked() {
            when(likeRepository.exists(any(), any())).thenReturn(true);

            assertThrows(IllegalStateException.class,
                    () -> likeService.like("user-1", "post-1"));
        }
    }

    @Nested
    @DisplayName("unlike")
    class UnlikeTest {
        @Test
        @DisplayName("正常にいいね解除できる")
        void shouldUnlike() {
            when(likeRepository.exists(any(), any())).thenReturn(true);

            likeService.unlike("user-1", "post-1");

            verify(likeRepository).delete(new UserId("user-1"), new PostId("post-1"));
        }

        @Test
        @DisplayName("いいねしていない場合はエラー")
        void shouldThrowWhenNotLiked() {
            when(likeRepository.exists(any(), any())).thenReturn(false);

            assertThrows(IllegalStateException.class,
                    () -> likeService.unlike("user-1", "post-1"));
        }
    }

    @Test
    @DisplayName("いいね数を取得できる")
    void shouldGetLikeCount() {
        when(likeRepository.countByPostId(new PostId("post-1"))).thenReturn(5L);

        assertEquals(5L, likeService.getLikeCount("post-1"));
    }
}
