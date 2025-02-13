package com.ablez.jookbiren.chatbot.dto;

import java.util.Objects;

public class QuizBlockDto {
    private int episode;
    private int quizNumber;
    private boolean isCorrect;
    private boolean isAnswer;

    public QuizBlockDto(int episode, int quizNumber, boolean isCorrect, boolean isAnswer) {
        this.episode = episode;
        this.quizNumber = quizNumber;
        this.isCorrect = isCorrect;
        this.isAnswer = isAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuizBlockDto that = (QuizBlockDto) o;
        return episode == that.episode && quizNumber == that.quizNumber && isCorrect == that.isCorrect
                && isAnswer == that.isAnswer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(episode, quizNumber, isCorrect, isAnswer);
    }
}
