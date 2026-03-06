package com.example.sns.presentation.dto;

import com.example.sns.domain.model.post.Post;

import java.time.LocalDateTime;

/**
 * 投稿情報のレスポンスDTO。
 */
public class PostResponse {

    private String id;
    private String userId;
    private String content;
    private LocalDateTime createdAt;
    private long likeCount;
    private boolean liked;

    public PostResponse() {}

    public static PostResponse fromDomain(Post post) {
        PostResponse response = new PostResponse();
        response.id = post.getId().getValue();
        response.userId = post.getUserId().getValue();
        response.content = post.getContent().getValue();
        response.createdAt = post.getCreatedAt();
        response.likeCount = 0;
        response.liked = false;
        return response;
    }

    public static PostResponse fromDomain(Post post, long likeCount, boolean liked) {
        PostResponse response = fromDomain(post);
        response.likeCount = likeCount;
        response.liked = liked;
        return response;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
}
