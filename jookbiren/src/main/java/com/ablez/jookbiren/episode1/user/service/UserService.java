package com.ablez.jookbiren.episode1.user.service;

import static com.ablez.jookbiren.episode1.answer.utils.AnswerConstant.SUSPECT;
import static com.ablez.jookbiren.episode1.utils.JookbirenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode1.user.dto.UserDto.CodeDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.EndingDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.InfoDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.LoginDto;
import com.ablez.jookbiren.episode1.user.dto.UserDto.StatusDto;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.episode1.user.repository.UserQuerydslRepository;
import com.ablez.jookbiren.episode1.user.repository.UserRepository;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import com.ablez.jookbiren.security.interceptor.JwtParseInterceptor;
import com.ablez.jookbiren.security.jwt.JwtDto.TokenDto;
import com.ablez.jookbiren.security.jwt.JwtTokenizer;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import com.ablez.jookbiren.userInfo.service.UserInfoService;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserQuerydslRepository userQuerydslRepository;
    private final UserInfoService userInfoService;
    private final JwtTokenizer jwtTokenizer;
    private final JwtParseInterceptor jwtParseInterceptor;

    public LoginDto login(CodeDto codeInfo) {
        UserInfo userInfo = userInfoService.findByCodeEp1(codeInfo.getCode());
        UserEp01 user = userInfo.getUserEp01();

        user.updateFirstLoginTime();

//        String userId = String.valueOf(user.getUserId());
        String accessToken = jwtTokenizer.generateAccessToken(String.valueOf(userInfo.getUserInfoId()),
                String.valueOf(user.getUserId()));
//        RefreshToken refreshToken = saveRefreshToken(userId);

        user.setAccessToken(accessToken);

        return new LoginDto(new TokenDto(accessToken), new EndingDto(user.getAnswerTime() != null));
    }

    public InfoDto getInfo() {
//        String code = jwtParseInterceptor.getAuthenticatedUsername();
        UserEp01 user = findByUserId(Long.parseLong(jwtParseInterceptor.getAuthenticatedUsername()));

        LocalDateTime firstLoginTime = user.getFirstLoginTime();
        LocalDateTime answerTime = user.getAnswerTime();
        Duration duration = Duration.between(firstLoginTime, answerTime);

        return new InfoDto(user.getScore(), duration.toSeconds(), user.getAnswerCount(), user.getSolvedQuizCount(),
                SUSPECT.get(user.getCriminal()));
    }

    public UserEp01 findByUserId(long userId) {
        return userQuerydslRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));
    }

    public UserEp01 findCurrentUser(String accessToken) {
        accessToken = accessToken.substring(7);
        UserEp01 user = findByUserId(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()));
//        UserEp01 user = userRepository.findById(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()))
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));

        if (!user.getAccessToken().equals(accessToken)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_LOGIN_USER);
        }
        return user;
    }

    public StatusDto canPickSuspect() {
        UserEp01 user = findByUserId(Long.parseLong(jwtParseInterceptor.getAuthenticatedUsername()));

        int answerStatus = user.getAnswerStatusCode();
        return new StatusDto(answerStatus == (Math.pow(2, STAR_QUIZ_COUNT) - 1));
    }

//    public UserInfoListDto findAllUsers() {
//        return new UserInfoListDto(userInfoService.findUserInfos());
//    }
}
