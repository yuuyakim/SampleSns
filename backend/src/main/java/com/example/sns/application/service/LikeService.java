package com.example.sns.application.service;

import com.example.sns.domain.model.like.Like;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * いいねに関するユースケースを提供するサービス。
 */
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * 投稿にいいねする。
     */
    public Like like(String userId, String postId) {
        UserId userIdVo = new UserId(userId);
        PostId postIdVo = new PostId(postId);

        if (likeRepository.exists(userIdVo, postIdVo)) {
            throw new IllegalStateException("Already liked this post");
        }

        Like likeEntity = new Like(userIdVo, postIdVo, LocalDateTime.now());
        return likeRepository.save(likeEntity);
    }

    /**
     * いいねを解除する。
     */
    public void unlike(String userId, String postId) {
        UserId userIdVo = new UserId(userId);
        PostId postIdVo = new PostId(postId);

        if (!likeRepository.exists(userIdVo, postIdVo)) {
            throw new IllegalStateException("Not liked this post");
        }

        likeRepository.delete(userIdVo, postIdVo);
    }

    /**
     * 投稿のいいね数を取得する。
     */
    public long getLikeCount(String postId) {
        return likeRepository.countByPostId(new PostId(postId));
    }

    /**
     * いいね済みかチェック。
     */
    public boolean isLiked(String userId, String postId) {
        return likeRepository.exists(new UserId(userId), new PostId(postId));
    }
}
