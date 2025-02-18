package com.ablez.jookbiren.episode1.quiz.controller;

import com.ablez.jookbiren.episode1.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode1.quiz.service.Ep1QuizService;
import com.ablez.jookbiren.episode1.user.service.Ep1UserService;
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
@RequestMapping("/ep1/quizzes")
public class Ep1QuizController {
    private final Ep1QuizService quizService;
    private final Ep1UserService userService;

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
