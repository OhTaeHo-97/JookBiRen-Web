package com.ablez.jookbiren.chatbot.quiz.entity;

import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Quiz {
    private int episode;
    private int quizNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quiz quiz = (Quiz) o;
        return episode == quiz.episode && quizNumber == quiz.quizNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(episode, quizNumber);
    }
}
