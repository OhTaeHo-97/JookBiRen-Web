package com.ablez.jookbiren.episode2.answer.controller;

import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.CheckAnswerResponseDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.SuspectResponseDto;
import com.ablez.jookbiren.episode2.answer.service.Ep2AnswerService;
import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import com.ablez.jookbiren.episode2.user.service.Ep2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Ep2AnswerController {
    private final Ep2AnswerService answerService;
    private final Ep2UserService userService;

    @GetMapping
    public ResponseEntity findAnswer(@RequestHeader("Authorization") String accessToken, String quiz) {
        log.info("에피소드2 정답 보기 API 시작");
        FindAnswerResponseDto result = answerService.findAnswer(new Quiz(quiz), userService.findCurrentUser(accessToken));
        log.info("에피소드2 정답 보기 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity checkAnswer(@RequestHeader("Authorization") String accessToken,
                                      @RequestBody CheckAnswerDto dto) {
        log.info("에피소드2 정답 확인 API 시작");
        CheckAnswerResponseDto result = answerService.checkAnswer(dto, userService.findCurrentUser(accessToken));
        log.info("에피소드2 정답 확인 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/pick1")
    public ResponseEntity pickSuspect1(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody SuspectDto suspectInfo) {
        log.info("에피소드2 첫번째 용의자 지목 API 시작");
        answerService.pickSuspect1(userService.findCurrentUser(accessToken), suspectInfo);
        log.info("에피소드2 첫번째 용의자 지목 API 끝");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/pick2")
    public ResponseEntity pickSuspect2(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody SuspectDto suspectInfo) {
        log.info("에피소드2 두번째 용의자 지목 API 시작");
        SuspectResponseDto result = answerService.pickSuspect2(userService.findCurrentUser(accessToken), suspectInfo);
        log.info("에피소드2 두번째 용의자 지목 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
