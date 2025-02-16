package com.ablez.jookbiren.episode2.quiz.mapper;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.WrongAnswerEp01;
import com.ablez.jookbiren.episode2.quiz.dto.QuizDto.QuizInfoDto;
import com.ablez.jookbiren.episode2.quiz.dto.QuizDto.WrongAnswerDto;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import org.springframework.stereotype.Component;

@Component
public class QuizInfoMapper {
    public QuizInfoDto makeQuizInfoDto(Quiz0Ep01 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz1Ep01 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz2Ep01 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz3Ep01 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz4Ep01 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz0Ep02 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getFirstGetHintTime())
                .getSecondHintTime(quiz.getSecondGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz1Ep02 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getFirstGetHintTime())
                .getSecondHintTime(quiz.getSecondGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz2Ep02 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getFirstHintTime())
                .getSecondHintTime(quiz.getSecondHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public QuizInfoDto makeQuizInfoDto(Quiz3Ep02 quiz) {
        return QuizInfoDto.builder()
                .code(quiz.getUserId().getCode())
                .placeNumber(quiz.getQuiz().getPlaceCode())
                .quizNumber(quiz.getQuizNumber())
                .firstAccessTime(quiz.getFirstAccessTime())
                .firstAnswerTime(quiz.getFirstAnswerTime())
                .getFirstHintTime(quiz.getGetHintTime())
                .getAnswerTime(quiz.getGetAnswerTime())
                .build();
    }

    public WrongAnswerDto makeWrongAnswerDto(WrongAnswerEp01 wrongAnswer) {
        return WrongAnswerDto.builder()
                .code(wrongAnswer.getUser().getCode())
                .placeNumber(wrongAnswer.getQuiz().getPlaceCode())
                .quizNumber(wrongAnswer.getQuiz().getQuizNumber())
                .answer(wrongAnswer.getAnswer())
                .time(wrongAnswer.getTime())
                .build();
    }

    public WrongAnswerDto makeWrongAnswerDto(WrongAnswerEp02 wrongAnswer) {
        return WrongAnswerDto.builder()
                .code(wrongAnswer.getUser().getCode())
                .placeNumber(wrongAnswer.getQuiz().getPlaceCode())
                .quizNumber(wrongAnswer.getQuiz().getQuizNumber())
                .answer(wrongAnswer.getAnswer())
                .time(wrongAnswer.getTime())
                .build();
    }
}
