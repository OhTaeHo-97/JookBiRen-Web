package com.ablez.jookbiren.episode2.answer.controller;

import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode2.answer.service.AnswerService;
import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import com.ablez.jookbiren.episode2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ep2/answers")
@Validated
public class AnswerController {
    private final AnswerService answerService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity findAnswer(@RequestHeader("Authorization") String accessToken, String quiz) {
        return new ResponseEntity(answerService.findAnswer(new Quiz(quiz), userService.findCurrentUser(accessToken)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity checkAnswer(@RequestHeader("Authorization") String accessToken,
                                      @RequestBody CheckAnswerDto dto) {
        return new ResponseEntity(answerService.checkAnswer(dto, userService.findCurrentUser(accessToken)),
                HttpStatus.OK);
    }

    @PostMapping("/pick1")
    public ResponseEntity pickSuspect1(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody SuspectDto suspectInfo) {
        answerService.pickSuspect1(userService.findCurrentUser(accessToken), suspectInfo);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/pick2")
    public ResponseEntity pickSuspect2(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody SuspectDto suspectInfo) {
        return new ResponseEntity(answerService.pickSuspect2(userService.findCurrentUser(accessToken), suspectInfo),
                HttpStatus.OK);
    }
}
