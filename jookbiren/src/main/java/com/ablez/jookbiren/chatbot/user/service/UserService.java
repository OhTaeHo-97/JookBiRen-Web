package com.ablez.jookbiren.chatbot.user.service;

import com.ablez.jookbiren.chatbot.user.dto.UserDto.ResponseDto;
import com.ablez.jookbiren.chatbot.user.entity.UserEp00;
import com.ablez.jookbiren.chatbot.user.repository.UserQuerydslRepository;
import com.ablez.jookbiren.chatbot.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserQuerydslRepository userQuerydslRepository;
    private final UserRepository userRepository;

    public ResponseDto login(UserEp00 userInfo) {
        String code = userInfo.getCode();

        // 코드를 통해 존재하는 유저인지 확인
        UserEp00 user = validateCode(code);
        // 존재하지 않는 유저라면 잘못된 코드임을 반환
        if (user == null) {
            return unsuccessfulLogin(userInfo);
        }

        // 밴 당한 유저라면 밴 당한 사실을 반환
        if (user.getIsBanned()) {
            return UserEp00.userToResponseDto(user, 2, null);
        }

        // 아직 한 번도 로그인되지 않은 유저라면 카카오아이디를 저장하고 성공적인 로그인이 되었음을 반환
        if (user.getFirstId() == null) {
            user.setFirstId(userInfo.getFirstId());
            userRepository.save(user);
            return successfulLogin(user);
        }

        // 로그인을 했었는데 같은 아이디로 로그인했다면 성공적인 로그인이 되있음을 반환
        if (user.getFirstId().equals(userInfo.getFirstId())) {
            return successfulLogin(user);
        }

        // 로그인을 했었는데 다른 아이디로 로그인했다면 밴 시키고 중복 로그인을 반환
        if (!user.getFirstId().equals(userInfo.getFirstId())) {
            user.setSecondId(userInfo.getFirstId());
            user.setIsBanned(true);
            userRepository.save(user);
            return UserEp00.userToResponseDto(UserEp00.of(userInfo.getCode(), userInfo.getFirstId()), 3, null);
        }

        // 위 경우 모두 아니라면 잘못된 코드임을 반환
        return unsuccessfulLogin(userInfo);
    }

    private ResponseDto successfulLogin(UserEp00 user) {
        List<String> blockIds = new ArrayList<>();
        blockIds.add("6762dd757c842c4d39423b2c");
        return UserEp00.userToResponseDto(user, 1, blockIds.get(0));
    }

    private static ResponseDto unsuccessfulLogin(UserEp00 userInfo) {
        List<String> blockIds = new ArrayList<>();
        blockIds.add("64fee1bc6a34bd19e09017f1");
        return UserEp00.userToResponseDto(UserEp00.of(userInfo.getCode(), null), 4, blockIds.get(0));
    }

    private UserEp00 validateCode(String code) {
        return userQuerydslRepository.findByCode(code).orElse(null);
    }

    public UserEp00 findUserByIdOrderByCustom(String kakaoId) {
        List<UserEp00> users = userQuerydslRepository.findAllByFirstIdOrderByCustomDesc(kakaoId);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public void updateUser(UserEp00 user) {
        userRepository.save(user);
    }
}
