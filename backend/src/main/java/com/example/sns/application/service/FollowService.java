package com.example.sns.application.service;

import com.example.sns.domain.model.follow.Follow;
import com.example.sns.domain.model.user.UserId;
import com.example.sns.domain.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * フォローに関するユースケースを提供するサービス。
 */
@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /**
     * ユーザーをフォローする。
     */
    public Follow follow(String followerId, String followeeId) {
        UserId followerVo = new UserId(followerId);
        UserId followeeVo = new UserId(followeeId);

        if (followRepository.exists(followerVo, followeeVo)) {
            throw new IllegalStateException("Already following this user");
        }

        Follow follow = new Follow(followerVo, followeeVo, LocalDateTime.now());
        return followRepository.save(follow);
    }

    /**
     * フォローを解除する。
     */
    public void unfollow(String followerId, String followeeId) {
        UserId followerVo = new UserId(followerId);
        UserId followeeVo = new UserId(followeeId);

        if (!followRepository.exists(followerVo, followeeVo)) {
            throw new IllegalStateException("Not following this user");
        }

        followRepository.delete(followerVo, followeeVo);
    }

    /**
     * フォロー中のユーザー一覧を取得。
     */
    public List<Follow> getFollowing(String userId) {
        return followRepository.findByFollowerId(new UserId(userId));
    }

    /**
     * フォロワー一覧を取得。
     */
    public List<Follow> getFollowers(String userId) {
        return followRepository.findByFolloweeId(new UserId(userId));
    }

    /**
     * フォロー中の数。
     */
    public long getFollowingCount(String userId) {
        return followRepository.countByFollowerId(new UserId(userId));
    }

    /**
     * フォロワーの数。
     */
    public long getFollowerCount(String userId) {
        return followRepository.countByFolloweeId(new UserId(userId));
    }
}
