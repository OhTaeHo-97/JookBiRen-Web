package com.ablez.jookbiren.episode1.user.controller;

import com.ablez.jookbiren.episode1.user.dto.UserDto;
import com.ablez.jookbiren.episode1.user.service.Ep1UserService;
import javax.validation.Valid;
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
@RequestMapping("/ep1/users")
@Validated
public class Ep1UserController {
    private final Ep1UserService userService;

    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Valid CodeDto codeInfo, HttpServletResponse response) {
    public ResponseEntity login(@RequestBody @Valid UserDto.CodeDto codeInfo) {
        return new ResponseEntity(userService.login(codeInfo), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity canPickSuspect(@RequestHeader("Authorization") String accessToken) {
        userService.findCurrentUser(accessToken);
        return new ResponseEntity(userService.canPickSuspect(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(@RequestHeader("Authorization") String accessToken) {
        userService.findCurrentUser(accessToken);
        return new ResponseEntity(userService.getInfo(), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity getAllUsers() {
//        return new ResponseEntity(userService.findAllUsers(), HttpStatus.OK);
//    }
}
