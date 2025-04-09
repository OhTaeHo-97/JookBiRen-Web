package com.ablez.jookbiren.episode1.quiz.controller;

import com.ablez.jookbiren.episode1.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.HintDto;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.PageDto;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.QuizPageDto;
import com.ablez.jookbiren.episode1.quiz.service.Ep1QuizService;
import com.ablez.jookbiren.episode1.user.service.Ep1UserService;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ep1/quizzes")
@Slf4j
public class Ep1QuizController {
    private final Ep1QuizService quizService;
    private final Ep1UserService userService;

    @GetMapping
    public ResponseEntity getCurrentSituationAndSolvedProblems(@RequestHeader("Authorization") String accessToken,
                                                               int place) {
        log.info("에피소드1 해결한 문제 및 정답 본 횟수, 해결한 문제 개수 조회 API 시작");
        PageDto result = quizService.getCurrentSituationAndSolvedProblems(place, userService.findCurrentUser(accessToken));
        log.info("에피소드1 해결한 문제 및 정답 본 횟수, 해결한 문제 개수 조회 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/answer")
    public ResponseEntity checkAlreadySolvedQuiz(@RequestHeader("Authorization") String accessToken, String quiz) {
        log.info("에피소드1 이전에 해결한 문제 여부 조회 API 시작");
        QuizPageDto result = quizService.checkAlreadySolvedQuiz(userService.findCurrentUser(accessToken), new Quiz(quiz));
        log.info("에피소드1 이전에 해결한 문제 여부 조회 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/hint")
    public ResponseEntity getHint(@RequestHeader("Authorization") String accessToken, String quiz,
                                  @Positive int hintNumber) {
        log.info("에피소드1 힌트 보기 API 시작");
        HintDto result = quizService.findHint(hintNumber, userService.findCurrentUser(accessToken), new Quiz(quiz));
        log.info("에피소드1 힌트 보기 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
