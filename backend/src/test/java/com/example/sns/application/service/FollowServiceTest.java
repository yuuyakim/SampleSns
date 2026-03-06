package com.example.sns.application.service;

import com.example.sns.domain.model.follow.Follow;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.FollowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowService followService;

    @Nested
    @DisplayName("follow")
    class FollowTest {
        @Test
        @DisplayName("正常にフォローできる")
        void shouldFollow() {
            when(followRepository.exists(any(), any())).thenReturn(false);
            when(followRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            Follow follow = followService.follow("user-1", "user-2");

            assertNotNull(follow);
            verify(followRepository).save(any(Follow.class));
        }

        @Test
        @DisplayName("既にフォロー済みの場合はエラー")
        void shouldThrowWhenAlreadyFollowing() {
            when(followRepository.exists(any(), any())).thenReturn(true);

            assertThrows(IllegalStateException.class,
                    () -> followService.follow("user-1", "user-2"));
        }
    }

    @Nested
    @DisplayName("unfollow")
    class UnfollowTest {
        @Test
        @DisplayName("正常にアンフォローできる")
        void shouldUnfollow() {
            when(followRepository.exists(any(), any())).thenReturn(true);

            followService.unfollow("user-1", "user-2");

            verify(followRepository).delete(new UserId("user-1"), new UserId("user-2"));
        }

        @Test
        @DisplayName("フォローしていない場合はエラー")
        void shouldThrowWhenNotFollowing() {
            when(followRepository.exists(any(), any())).thenReturn(false);

            assertThrows(IllegalStateException.class,
                    () -> followService.unfollow("user-1", "user-2"));
        }
    }
}
