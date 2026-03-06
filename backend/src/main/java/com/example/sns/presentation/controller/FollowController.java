package com.example.sns.presentation.controller;

import com.example.sns.application.service.FollowService;
import com.example.sns.domain.model.follow.Follow;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/users/{userId}/follow")
    public ResponseEntity<Void> follow(
            @PathVariable String userId,
            @RequestHeader("X-User-Id") String currentUserId) {
        followService.follow(currentUserId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/users/{userId}/follow")
    public ResponseEntity<Void> unfollow(
            @PathVariable String userId,
            @RequestHeader("X-User-Id") String currentUserId) {
        followService.unfollow(currentUserId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<Map<String, String>>> getFollowers(@PathVariable String userId) {
        List<Follow> followers = followService.getFollowers(userId);
        List<Map<String, String>> result = followers.stream()
                .map(f -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", f.getFollowerId().getValue());
                    map.put("createdAt", f.getCreatedAt().toString());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<Map<String, String>>> getFollowing(@PathVariable String userId) {
        List<Follow> following = followService.getFollowing(userId);
        List<Map<String, String>> result = following.stream()
                .map(f -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", f.getFolloweeId().getValue());
                    map.put("createdAt", f.getCreatedAt().toString());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
