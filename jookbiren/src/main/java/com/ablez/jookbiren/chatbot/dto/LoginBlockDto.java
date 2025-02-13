package com.ablez.jookbiren.chatbot.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginBlockDto {
    private boolean isSuccessfulLogin;
    private boolean isTutorial;
    private boolean isStory;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginBlockDto that = (LoginBlockDto) o;
        return isSuccessfulLogin == that.isSuccessfulLogin && isTutorial == that.isTutorial && isStory == that.isStory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccessfulLogin, isTutorial, isStory);
    }
}
