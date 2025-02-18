package com.ablez.jookbiren.episode2.quiz.controller;

import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import com.ablez.jookbiren.episode2.quiz.service.Ep2QuizService;
import com.ablez.jookbiren.episode2.user.service.Ep2UserService;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ep2/quizzes")
public class Ep2QuizController {
    private final Ep2QuizService quizService;
    private final Ep2UserService userService;

    @GetMapping
    public ResponseEntity getCurrentSituationAndSolvedProblems(@RequestHeader("Authorization") String accessToken,
                                                               int place) {
        return new ResponseEntity(
                quizService.getCurrentSituationAndSolvedProblems(place, userService.findCurrentUser(accessToken)),
                HttpStatus.OK);
    }

    @GetMapping("/answer")
    public ResponseEntity checkAlreadySolvedQuiz(@RequestHeader("Authorization") String accessToken, String quiz) {
        return new ResponseEntity(
                quizService.checkAlreadySolvedQuiz(userService.findCurrentUser(accessToken), new Quiz(quiz)),
                HttpStatus.OK);
    }

    @GetMapping("/hint")
    public ResponseEntity getHint(@RequestHeader("Authorization") String accessToken, String quiz,
                                  @Positive int hintNumber) {
        return new ResponseEntity(
                quizService.findHint(hintNumber, userService.findCurrentUser(accessToken), new Quiz(quiz)),
                HttpStatus.OK);
    }
}
