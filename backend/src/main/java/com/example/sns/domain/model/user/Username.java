package com.example.sns.domain.model.user;

import java.util.Objects;

/**
 * ユーザー名を表す値オブジェクト。
 * 3〜20文字の英数字・アンダースコアのみ許可。
 */
public class Username {

    private static final String PATTERN = "^[a-zA-Z0-9_]{3,20}$";
    private final String value;

    public Username(String value) {
        if (value == null || !value.matches(PATTERN)) {
            throw new IllegalArgumentException(
                    "Username must be 3-20 alphanumeric characters or underscores. Got: " + value);
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
        Username username = (Username) o;
        return Objects.equals(value, username.value);
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
