package com.ablez.jookbiren.userInfo.service;

import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import com.ablez.jookbiren.userInfo.repository.UserInfoQuerydslRepository;
import com.ablez.jookbiren.userInfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoQuerydslRepository userInfoQuerydslRepository;

    public UserInfo findByCodeEp1(String code) {
        return userInfoQuerydslRepository.findByCodeEp1(code)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_INFO_NOT_FOUND));
    }

    public UserInfo findByCodeEp2(String code) {
        return userInfoQuerydslRepository.findByCodeEp2(code)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_INFO_NOT_FOUND));
    }
}
