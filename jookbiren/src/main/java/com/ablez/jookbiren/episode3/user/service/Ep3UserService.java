package com.ablez.jookbiren.episode3.user.service;

import static com.ablez.jookbiren.episode3.answer.utils.AnswerConstant.SUSPECT;
import static com.ablez.jookbiren.episode3.utils.JookbirenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode3.user.dto.UserDto.CodeDto;
import com.ablez.jookbiren.episode3.user.dto.UserDto.EndingDto;
import com.ablez.jookbiren.episode3.user.dto.UserDto.InfoDto;
import com.ablez.jookbiren.episode3.user.dto.UserDto.LoginDto;
import com.ablez.jookbiren.episode3.user.dto.UserDto.StatusDto;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.episode3.user.repository.Ep3UserQuerydslRepository;
import com.ablez.jookbiren.episode3.user.repository.Ep3UserRepository;
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
public class Ep3UserService {
    private final Ep3UserRepository userRepository;
    private final Ep3UserQuerydslRepository userQuerydslRepository;
    private final UserInfoService userInfoService;
    private final JwtTokenizer jwtTokenizer;
    private final JwtParseInterceptor jwtParseInterceptor;

    public LoginDto login(CodeDto codeInfo) {
        UserInfo userInfo = userInfoService.findByCodeEp3(codeInfo.getCode());
        UserEp03 user = userInfo.getUserEp03();

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
        UserEp03 user = findByUserId(Long.parseLong(jwtParseInterceptor.getAuthenticatedUsername()));

        LocalDateTime firstLoginTime = user.getFirstLoginTime();
        LocalDateTime answerTime = user.getAnswerTime();
        Duration duration = Duration.between(firstLoginTime, answerTime);

        return new InfoDto(user.getScore(), duration.toSeconds(), user.getAnswerCount(), user.getSolvedQuizCount(),
                SUSPECT.get(user.getCriminal()));
    }

    public UserEp03 findByUserId(long userId) {
        return userQuerydslRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));
    }

    public UserEp03 findCurrentUser(String accessToken) {
        accessToken = accessToken.substring(7);
        UserEp03 user = findByUserId(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()));
//        UserEp01 user = userRepository.findById(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()))
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));

        if (!user.getAccessToken().equals(accessToken)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_LOGIN_USER);
        }
        return user;
    }

    public StatusDto canPickSuspect() {
        UserEp03 user = findByUserId(Long.parseLong(jwtParseInterceptor.getAuthenticatedUsername()));

        int answerStatus = user.getAnswerStatusCode();
        return new StatusDto(answerStatus == (Math.pow(2, STAR_QUIZ_COUNT) - 1));
    }

//    public UserInfoListDto findAllUsers() {
//        return new UserInfoListDto(userInfoService.findUserInfos());
//    }
}
