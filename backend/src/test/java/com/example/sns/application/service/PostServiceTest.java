package com.example.sns.application.service;

import com.example.sns.domain.model.follow.Follow;
import com.example.sns.domain.model.post.Post;
import com.example.sns.domain.model.post.PostContent;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.FollowRepository;
import com.example.sns.domain.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private PostService postService;

    @Nested
    @DisplayName("createPost")
    class CreatePostTest {
        @Test
        @DisplayName("正常に投稿を作成できる")
        void shouldCreatePost() {
            when(postRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            Post post = postService.createPost("user-1", "Hello, world!");

            assertNotNull(post);
            assertEquals("Hello, world!", post.getContent().getValue());
            assertEquals("user-1", post.getUserId().getValue());
            verify(postRepository).save(any(Post.class));
        }
    }

    @Nested
    @DisplayName("deletePost")
    class DeletePostTest {
        @Test
        @DisplayName("自分の投稿を削除できる")
        void shouldDeleteOwnPost() {
            Post mockPost = new Post(new PostId("post-1"), new UserId("user-1"),
                    new PostContent("Hello"), LocalDateTime.now());
            when(postRepository.findById(new PostId("post-1"))).thenReturn(Optional.of(mockPost));

            postService.deletePost("post-1", "user-1");
            verify(postRepository).deleteById(new PostId("post-1"));
        }

        @Test
        @DisplayName("他人の投稿を削除しようとするとエラー")
        void shouldThrowWhenDeletingOthersPost() {
            Post mockPost = new Post(new PostId("post-1"), new UserId("user-1"),
                    new PostContent("Hello"), LocalDateTime.now());
            when(postRepository.findById(any())).thenReturn(Optional.of(mockPost));

            assertThrows(IllegalStateException.class,
                    () -> postService.deletePost("post-1", "user-2"));
        }
    }

    @Nested
    @DisplayName("getHomeTimeline")
    class HomeTimelineTest {
        @Test
        @DisplayName("フォロー中ユーザーと自分の投稿がタイムラインに含まれる")
        void shouldIncludeFollowingAndOwnPosts() {
            UserId currentUser = new UserId("user-1");
            Follow follow = new Follow(currentUser, new UserId("user-2"), LocalDateTime.now());
            when(followRepository.findByFollowerId(currentUser)).thenReturn(List.of(follow));

            Post p1 = new Post(new PostId("p1"), currentUser, new PostContent("My post"), LocalDateTime.now());
            Post p2 = new Post(new PostId("p2"), new UserId("user-2"), new PostContent("Friend's post"), LocalDateTime.now());
            when(postRepository.findByUserIdIn(any())).thenReturn(Arrays.asList(p2, p1));

            List<Post> timeline = postService.getHomeTimeline("user-1");
            assertEquals(2, timeline.size());
        }
    }
}
