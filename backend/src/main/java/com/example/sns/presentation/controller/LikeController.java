package com.example.sns.presentation.controller;

import com.example.sns.application.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Void> like(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId) {
        likeService.like(userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<Void> unlike(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId) {
        likeService.unlike(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
