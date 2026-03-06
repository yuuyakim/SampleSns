package com.example.sns.presentation.controller;

import com.example.sns.application.service.PostService;
import com.example.sns.application.service.LikeService;
import com.example.sns.domain.model.post.Post;
import com.example.sns.presentation.dto.CreatePostRequest;
import com.example.sns.presentation.dto.PostResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    public PostController(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreatePostRequest request) {
        Post post = postService.createPost(userId, request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.fromDomain(post));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostResponse>> getUserPosts(
            @PathVariable String userId,
            @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        List<PostResponse> responses = posts.stream()
                .map(p -> toPostResponse(p, currentUserId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/timeline/home")
    public ResponseEntity<List<PostResponse>> getHomeTimeline(
            @RequestHeader("X-User-Id") String userId) {
        List<Post> posts = postService.getHomeTimeline(userId);
        List<PostResponse> responses = posts.stream()
                .map(p -> toPostResponse(p, userId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/timeline/global")
    public ResponseEntity<List<PostResponse>> getGlobalTimeline(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        List<Post> posts = postService.getGlobalTimeline();
        List<PostResponse> responses = posts.stream()
                .map(p -> toPostResponse(p, userId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private PostResponse toPostResponse(Post post, String currentUserId) {
        long likeCount = likeService.getLikeCount(post.getId().getValue());
        boolean liked = currentUserId != null && likeService.isLiked(currentUserId, post.getId().getValue());
        return PostResponse.fromDomain(post, likeCount, liked);
    }
}
