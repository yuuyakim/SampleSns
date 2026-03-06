package com.example.sns.domain.model.post;

import java.util.Objects;

/**
 * 投稿本文を表す値オブジェクト。
 * 1〜140文字のテキストを保持。
 */
public class PostContent {

    private static final int MAX_LENGTH = 140;
    private final String value;

    public PostContent(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Post content must not be null or blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Post content must be " + MAX_LENGTH + " characters or less. Got: " + value.length());
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
        PostContent that = (PostContent) o;
        return Objects.equals(value, that.value);
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
