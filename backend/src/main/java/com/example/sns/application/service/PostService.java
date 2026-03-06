package com.example.sns.application.service;

import com.example.sns.domain.model.post.*;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.PostRepository;
import com.example.sns.domain.repository.FollowRepository;
import com.example.sns.domain.model.follow.Follow;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 投稿に関するユースケースを提供するサービス。
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    public PostService(PostRepository postRepository, FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.followRepository = followRepository;
    }

    /**
     * 新規投稿を作成する。
     */
    public Post createPost(String userId, String content) {
        Post post = new Post(
                new PostId(UUID.randomUUID().toString()),
                new UserId(userId),
                new PostContent(content),
                LocalDateTime.now()
        );
        return postRepository.save(post);
    }

    /**
     * 投稿を削除する。削除権限のチェック付き。
     */
    public void deletePost(String postId, String userId) {
        Post post = postRepository.findById(new PostId(postId))
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + postId));

        if (!post.getUserId().equals(new UserId(userId))) {
            throw new IllegalStateException("You can only delete your own posts");
        }

        postRepository.deleteById(new PostId(postId));
    }

    /**
     * 指定ユーザーの投稿一覧を取得する。
     */
    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(new UserId(userId));
    }

    /**
     * 全体タイムラインを取得する。
     */
    public List<Post> getGlobalTimeline() {
        return postRepository.findAll();
    }

    /**
     * ホームタイムラインを取得する（フォロー中ユーザー + 自分の投稿）。
     */
    public List<Post> getHomeTimeline(String userId) {
        UserId currentUserId = new UserId(userId);
        List<UserId> followingIds = followRepository.findByFollowerId(currentUserId)
                .stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toList());

        // 自分の投稿も含める
        followingIds.add(currentUserId);

        return postRepository.findByUserIdIn(followingIds);
    }
}
