package com.example.sns.domain.repository;

import com.example.sns.domain.model.follow.Follow;
import com.example.sns.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * フォローリポジトリのインターフェース。
 */
public interface FollowRepository {

    Follow save(Follow follow);

    void delete(UserId followerId, UserId followeeId);

    Optional<Follow> findByFollowerIdAndFolloweeId(UserId followerId, UserId followeeId);

    /**
     * 指定ユーザーがフォローしているユーザーの一覧。
     */
    List<Follow> findByFollowerId(UserId followerId);

    /**
     * 指定ユーザーのフォロワー一覧。
     */
    List<Follow> findByFolloweeId(UserId followeeId);

    long countByFollowerId(UserId followerId);

    long countByFolloweeId(UserId followeeId);

    boolean exists(UserId followerId, UserId followeeId);
}
