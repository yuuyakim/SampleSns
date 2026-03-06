package com.example.sns.domain.repository;

import com.example.sns.domain.model.post.Post;
import com.example.sns.domain.model.post.PostId;
import com.example.sns.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * 投稿リポジトリのインターフェース。
 */
public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(PostId id);

    List<Post> findByUserId(UserId userId);

    List<Post> findAll();

    /**
     * 指定されたユーザーIDリストの投稿を時系列降順で取得。
     */
    List<Post> findByUserIdIn(List<UserId> userIds);

    void deleteById(PostId id);

    long countByUserId(UserId userId);
}
