package com.ablez.jookbiren.episode2.user.controller;

import com.ablez.jookbiren.episode2.user.dto.UserDto;
import com.ablez.jookbiren.episode2.user.service.Ep2UserService;
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
@RequestMapping("/ep2/users")
@Validated
public class Ep2UserController {
    private final Ep2UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDto.CodeDto codeInfo) {
        return new ResponseEntity(userService.login(codeInfo), HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public ResponseEntity logout(@RequestHeader("Authorization") String accessToken) {
//        userService.logout(accessToken);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PostMapping("/reissue")
//    public ResponseEntity reissue(@RequestHeader("Refresh") String refreshToken, HttpServletResponse response) {
//        TokenDto token = userService.reissue(refreshToken);
//        response.setHeader("Authorization", token.getAccessToken());
//        response.setHeader("Refresh", token.getRefreshToken());
//        return new ResponseEntity(HttpStatus.OK);
//    }

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

//    @PostMapping(path = "/register")
//    public ResponseEntity registerExcel(@RequestParam("file") MultipartFile file) {
//        userService.generateBuyerAndOrderInfo(file);
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @GetMapping
//    public ResponseEntity getAllUsers(int episode, int page, int size) {
//        return new ResponseEntity(userService.findAllUsers(episode, page, size), HttpStatus.OK);
//    }
}
