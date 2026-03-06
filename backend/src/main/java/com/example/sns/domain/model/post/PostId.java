package com.example.sns.domain.model.post;

import java.util.Objects;

/**
 * 投稿IDを表す値オブジェクト。
 */
public class PostId {

    private final String value;

    public PostId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("PostId must not be null or blank");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostId postId = (PostId) o;
        return Objects.equals(value, postId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
