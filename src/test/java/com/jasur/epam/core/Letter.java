package com.jasur.epam.core;

import java.util.Objects;

public record Letter(String receiver, String subject, String message, String sender) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter = (Letter) o;
        return Objects.equals(receiver, letter.receiver) && Objects.equals(subject, letter.subject) && Objects.equals(message, letter.message) && Objects.equals(sender, letter.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, subject, message, sender);
    }
}