package com.ablez.jookbiren.episode2.quiz.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class QuizDto {
    @Getter
    public static class PageDto {
        private int solved;
        private int answer;
        private List<Integer> solvedProblems;

        public PageDto(int solved, int answer, List<Integer> solvedProblems) {
            this.solved = solved;
            this.answer = answer;
            this.solvedProblems = solvedProblems;
        }
    }

    @Getter
    public static class QuizPageDto {
        private String answer;

        public QuizPageDto() {
            answer = "";
        }

        public QuizPageDto(String answer) {
            this.answer = answer;
        }
    }

    @Getter
    public static class HintDto {
        private String hint;
        private String image;

        public HintDto(String hint, String image) {
            this.hint = hint;
            this.image = image;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class QuizInfoDto {
        // 이름, 코드, 장소 번호, 퀴즈 번호, 처음 접속 시간, 처음 정답 맞춘 시간, 첫 힌트 본 시간, 두 번째 힌트 본 시간, 정답 본 시간
        private String name;
        private String code;
        private int placeNumber;
        private int quizNumber;
        private LocalDateTime firstAccessTime;
        private LocalDateTime firstAnswerTime;
        private LocalDateTime getFirstHintTime;
        private LocalDateTime getSecondHintTime;
        private LocalDateTime getAnswerTime;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class WrongAnswerDto {
        // 이름, 코드, 장소 번호, 퀴즈 번호, 잘못된 정답, 시간
        private String name;
        private String code;
        private int placeNumber;
        private int quizNumber;
        private String answer;
        private LocalDateTime time;
    }
}
