package com.example.sns.domain.model.user;

import java.util.Objects;

/**
 * メールアドレスを表す値オブジェクト。
 */
public class Email {

    private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String value;

    public Email(String value) {
        if (value == null || !value.matches(PATTERN)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
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
        Email email = (Email) o;
        return Objects.equals(value, email.value);
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
