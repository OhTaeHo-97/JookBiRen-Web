package com.ablez.jookbiren.episode2.answer.dto;

import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import lombok.Getter;

public class AnswerDto {
    @Getter
    public static class FindAnswerResponseDto {
        private String answer = "";

        public FindAnswerResponseDto(String answer) {
            this.answer = answer;
        }
    }

    @Getter
    public static class CheckAnswerDto {
        private Quiz quizInfo;
        private String answer;

        public CheckAnswerDto(String quiz, String answer) {
            this.quizInfo = new Quiz(quiz);
            this.answer = answer.toUpperCase();
        }
    }

    @Getter
    public static class CheckAnswerResponseDto {
        private boolean isCorrect;

        public CheckAnswerResponseDto(boolean isCorrect) {
            this.isCorrect = isCorrect;
        }
    }

    @Getter
    public static class SuspectDto {
        private int suspect;
    }

    @Getter
    public static class SuspectResponseDto {
        private boolean criminal;

        public SuspectResponseDto(boolean criminal) {
            this.criminal = criminal;
        }
    }
}
