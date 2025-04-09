package com.ablez.jookbiren.episode3.answer.controller;

import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.CheckAnswerResponseDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.SuspectResponseDto;
import com.ablez.jookbiren.episode3.answer.service.Ep3AnswerService;
import com.ablez.jookbiren.episode3.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode3.user.service.Ep3UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ep3/answers")
@Slf4j
public class Ep3AnswerController {
    private final Ep3AnswerService answerService;
    private final Ep3UserService userService;

    @GetMapping
    public ResponseEntity findAnswer(@RequestHeader("Authorization") String accessToken, String quiz) {
        log.info("에피소드3 정답 보기 API 시작");
        FindAnswerResponseDto result = answerService.findAnswer(new Quiz(quiz), userService.findCurrentUser(accessToken));
        log.info("에피소드3 정답 보기 API 끝");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity checkAnswer(@RequestHeader("Authorization") String accessToken,
                                      @RequestBody CheckAnswerDto dto) {
        log.info("에피소드3 정답 확인 API 시작");
        CheckAnswerResponseDto result = answerService.checkAnswer(dto, userService.findCurrentUser(accessToken));
        log.info("에피소드3 정답 확인 API 끝");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/pick")
    public ResponseEntity pickSuspect(@RequestHeader("Authorization") String accessToken,
                                      @RequestBody SuspectDto suspectInfo) {
        log.info("에피소드3 용의자 지목 API 시작");
        SuspectResponseDto result = answerService.pickSuspect(userService.findCurrentUser(accessToken), suspectInfo);
        log.info("에피소드3 용의자 지목 API 시작");
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
