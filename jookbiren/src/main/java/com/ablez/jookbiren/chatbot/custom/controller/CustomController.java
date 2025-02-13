package com.ablez.jookbiren.chatbot.custom.controller;

import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom1To2Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom3To4Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom4To5Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomAToBResponseDto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomImageDto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomImageResponseDto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomOpenDto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.ResponseDto;
import com.ablez.jookbiren.chatbot.custom.entity.Custom;
import com.ablez.jookbiren.chatbot.custom.service.CustomService;
import com.ablez.jookbiren.chatbot.user.entity.UserEp00;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatbot/customs")
public class CustomController {
    private final CustomService customService;

    @PostMapping("/custom")
    public ResponseEntity customImage(@RequestBody CustomImageDto customOpenInfo) {
        CustomImageResponseDto response = customService.makeCustomImage(Custom.customImageDtoToCustoms(customOpenInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/custom1")
    public ResponseEntity customOpen1(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(UserEp00.customOpenDtoToUser(customOpenInfo), 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom2")
    public ResponseEntity customOpen2(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(UserEp00.customOpenDtoToUser(customOpenInfo), 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom3")
    public ResponseEntity customOpen3(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(UserEp00.customOpenDtoToUser(customOpenInfo), 2);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom1to2")
    public ResponseEntity custom1To2(@RequestBody Custom1To2Dto custom1To2Info) {
        CustomAToBResponseDto response = customService.makeEachCategoryImage(
                UserEp00.custom1To2DtoToUser(custom1To2Info),
                Custom.custom1To2DtoToCustom(custom1To2Info));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom3to4")
    public ResponseEntity custom3To4(@RequestBody Custom3To4Dto custom3To4Info) {
        CustomAToBResponseDto response = customService.makeEachCategoryImage(
                UserEp00.custom3To4DtoToUser(custom3To4Info),
                Custom.custom3To4DtoToCustom(custom3To4Info));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom4to5")
    public ResponseEntity custom3To4(@RequestBody Custom4To5Dto custom4To5Info) {
        CustomAToBResponseDto response = customService.makeEachCategoryImage(
                UserEp00.custom4To5DtoToUser(custom4To5Info),
                Custom.custom4To5DtoToCustom(custom4To5Info));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
