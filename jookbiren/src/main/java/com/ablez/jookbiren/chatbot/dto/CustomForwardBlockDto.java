package com.ablez.jookbiren.chatbot.dto;

import java.util.Objects;

public class CustomForwardBlockDto {
    private String category;
    private boolean isPossible;

    public CustomForwardBlockDto(String category, boolean isPossible) {
        this.category = category;
        this.isPossible = isPossible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomForwardBlockDto that = (CustomForwardBlockDto) o;
        return isPossible == that.isPossible && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, isPossible);
    }
}
