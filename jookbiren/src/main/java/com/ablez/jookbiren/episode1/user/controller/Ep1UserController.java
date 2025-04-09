package com.ablez.jookbiren.episode1.user.controller;

import com.ablez.jookbiren.episode1.user.dto.UserDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.InfoDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.LoginDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.StatusDto;
import com.ablez.jookbiren.episode1.user.service.Ep1UserService;
import javax.validation.Valid;
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
@RequestMapping("/ep1/users")
@Validated
@Slf4j
public class Ep1UserController {
    private final Ep1UserService userService;

    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Valid CodeDto codeInfo, HttpServletResponse response) {
    public ResponseEntity login(@RequestBody @Valid UserDto.CodeDto codeInfo) {
        log.info("에피소드1 로그인 API 시작");
        LoginDto result = userService.login(codeInfo);
        log.info("에피소드1 로그인 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity canPickSuspect(@RequestHeader("Authorization") String accessToken) {
        log.info("에피소드1 별표 문제 전부 해결 여부 조회 API 시작");
        userService.findCurrentUser(accessToken);
        StatusDto result = userService.canPickSuspect();
        log.info("에피소드1 별표 문제 전부 해결 여부 조회 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(@RequestHeader("Authorization") String accessToken) {
        log.info("에피소드1 최종 유저 정보 조회 API 시작");
        userService.findCurrentUser(accessToken);
        InfoDto result = userService.getInfo();
        log.info("에피소드1 최종 유저 정보 조회 API 끝");
        return new ResponseEntity(result, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity getAllUsers() {
//        return new ResponseEntity(userService.findAllUsers(), HttpStatus.OK);
//    }
}
