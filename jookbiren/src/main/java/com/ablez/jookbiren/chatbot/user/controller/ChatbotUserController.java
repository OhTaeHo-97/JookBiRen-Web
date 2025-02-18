package com.ablez.jookbiren.chatbot.user.controller;

import com.ablez.jookbiren.chatbot.user.dto.UserDto.ResponseDto;
import com.ablez.jookbiren.chatbot.user.dto.UserDto.UserPostDto;
import com.ablez.jookbiren.chatbot.user.entity.UserEp00;
import com.ablez.jookbiren.chatbot.user.service.ChatbotUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot/users")
public class ChatbotUserController {
    private final ChatbotUserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserPostDto userInfo) {
        ResponseDto response = userService.login(UserEp00.userPostDtoToUser(userInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
