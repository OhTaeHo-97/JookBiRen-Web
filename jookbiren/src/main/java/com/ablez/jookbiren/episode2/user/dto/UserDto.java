package com.ablez.jookbiren.episode2.user.dto;

import com.ablez.jookbiren.security.jwt.JwtDto.TokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDto {
    @Getter
    public static class CodeDto {
        @NotNull
        private String code;
    }

    @Getter
    public static class LoginDto {
        private TokenDto token;
        private EndingDto ending;

        public LoginDto(TokenDto token, EndingDto ending) {
            this.token = token;
            this.ending = ending;
        }
    }

    @Getter
    public static class EndingDto {
        private boolean ending;

        public EndingDto(boolean ending) {
            this.ending = ending;
        }
    }

    @Getter
    public static class StatusDto {
        private boolean status;

        public StatusDto(boolean status) {
            this.status = status;
        }
    }

    @Getter
    public static class InfoDto {
        private int score;
        private long playtime;
        private int answerCount;
        private int solvedCount;
        private String criminal1;
        private String criminal2;

        public InfoDto(int score, long playtime, int answerCount, int solvedCount, String criminal1, String criminal2) {
            this.score = score;
            this.playtime = playtime;
            this.answerCount = answerCount;
            this.solvedCount = solvedCount;
            this.criminal1 = criminal1;
            this.criminal2 = criminal2;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfoListDto {
        private List<UserInfoDto> userInfos;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserInfoDto {
        private long id;
        private String phone;
        private String name;
        private String platform;
        private String orderId;
        private String nickname;
        private String code;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserBriefInfoDto {
        // 이름, 코드, 전화번호, 플랫폼, 닉네임, 주문번호, 금액, 에피소드, 주소, 생성일, 정답 맞춘 시간, 첫 로그인 시간, 푼 문제 수, 탐정 점수
        private String name;
        private String code;
        private String phone;
        private String platform;
        private String nickname;
        private String orderNumber;
        private int amount;
        private int episode;
        private String address;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime answeredTime;
        private String criminal1;
        private String criminal2;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime firstLoginTime;
        private int solvedQuizCount;
        private int score;
    }
}
